package io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.motor;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.simibubi.create.Create;
import com.simibubi.create.content.redstone.link.RedstoneLinkNetworkHandler.Frequency;
import io.github.jasonsimpart.createutilitiesj.CreateUtilitiesJ;
import net.createmod.catnip.data.Couple;
import net.createmod.catnip.levelWrappers.WorldHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;

import javax.annotation.Nullable;
import java.util.*;

public class VoidMotorNetworkHandler {

	static final Map<LevelAccessor, Map<NetworkKey, Set<BlockPos>>> connections =
			new IdentityHashMap<>();

	public Set<BlockPos> getNetworkOf(LevelAccessor world, VoidMotorLinkBehaviour actor) {
		Map<NetworkKey, Set<BlockPos>> networksInWorld = networksIn(world);
		NetworkKey key = actor.getNetworkKey();
		if (!networksInWorld.containsKey(key))
			networksInWorld.put(key, new LinkedHashSet<>());
		return networksInWorld.get(key);
	}

	public Map<NetworkKey, Set<BlockPos>> networksIn(LevelAccessor world) {
		if (!connections.containsKey(world)) {
			Create.LOGGER.warn("Tried to Access unprepared network space of " + WorldHelper.getDimensionID(world));
			return new HashMap<>();
		}
		return connections.get(world);
	}

	public void onLoadWorld(LevelAccessor world) {
		connections.put(world, new HashMap<>());
		Create.LOGGER.debug("Prepared Void Motor Network Space for " + WorldHelper.getDimensionID(world));
	}

	public void onUnloadWorld(LevelAccessor world) {
		connections.remove(world);
		Create.LOGGER.debug("Removed Void Motor Network Space for " + WorldHelper.getDimensionID(world));
	}

	public void addToNetwork(LevelAccessor world, VoidMotorLinkBehaviour actor) {
		getNetworkOf(world, actor).add(actor.getPos());
		if (actor.blockEntity instanceof VoidMotorTileEntity voidMotor) voidMotor.onConnectToVoidNetwork();
	}

	public void removeFromNetwork(LevelAccessor world, VoidMotorLinkBehaviour actor) {
		if (actor.blockEntity instanceof VoidMotorTileEntity voidMotor) voidMotor.onDisconnectFromVoidNetwork();
		Set<BlockPos> network = getNetworkOf(world, actor);
		network.remove(actor.getPos());
		if (network.isEmpty()) networksIn(world).remove(actor.getNetworkKey());
	}

	public static class NetworkKey {

		@Nullable
		public final GameProfile owner;
		public final Couple<Frequency> frequencies;

		public NetworkKey(@Nullable GameProfile owner, Frequency frequencyFirst, Frequency frequencySecond) {
			this.owner = owner;
			this.frequencies = Couple.create(frequencyFirst, frequencySecond);
		}

		public void writeToBuffer(RegistryFriendlyByteBuf buffer) {
			ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, frequencies.get(true).getStack());
			ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, frequencies.get(false).getStack());
			buffer.writeBoolean(owner != null);
			if (owner != null) {
				buffer.writeUUID(owner.getId());
				buffer.writeUtf(owner.getName());
			}
		}

		public static NetworkKey fromBuffer(RegistryFriendlyByteBuf buffer) {
			ItemStack frequencyFirst = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
			ItemStack frequencyLast = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
			GameProfile owner = null;
			if (buffer.readBoolean()) owner = new GameProfile(buffer.readUUID(), buffer.readUtf());
			return new NetworkKey(owner, Frequency.of(frequencyFirst), Frequency.of(frequencyLast));
		}

		@Override
		public int hashCode() {
			return Objects.hash(owner, frequencies);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null || getClass() != obj.getClass())
				return false;
			NetworkKey other = (NetworkKey) obj;
			return Objects.equals(owner, other.owner) && frequencies.equals(other.frequencies);
		}

		public CompoundTag serialize(HolderLookup.Provider registries) {
			CompoundTag tag = new CompoundTag();
			if (owner != null) {
				CompoundTag tag_ = new CompoundTag();
				tag_.putUUID("Id", owner.getId());
				tag_.putString("Name", owner.getName());
				tag.put("Owner", tag_);
			}
			tag.put("FrequencyFirst", frequencies.get(true).getStack().saveOptional(registries));
			tag.put("FrequencyLast", frequencies.get(false).getStack().saveOptional(registries));
			return tag;
		}

		public static NetworkKey deserialize(CompoundTag tag, HolderLookup.Provider registries) {
			Frequency frequencyFirst = Frequency.of(ItemStack.parseOptional(registries, tag.getCompound("FrequencyFirst")));
			Frequency frequencyLast = Frequency.of(ItemStack.parseOptional(registries, tag.getCompound("FrequencyLast")));
			GameProfile owner = null;
			if (tag.contains("Owner", 10)) {
				CompoundTag ownerTag = tag.getCompound("Owner");
				owner = new GameProfile(ownerTag.getUUID("Id"), ownerTag.getString("Name"));
			}
			return new NetworkKey(owner, frequencyFirst, frequencyLast);
		}

		@Override
		public String toString() {
			return owner + ":" + frequencies;
		}

		public static NetworkKey fromString(String json) {

			CompoundTag tag;
			try {
				tag = TagParser.parseTag(json);
			} catch (CommandSyntaxException e) {
				CreateUtilitiesJ.LOGGER.error("Tried to load invalid NetworkKey '" + json + "'");
				return null;
			}

			return null;
		}

	}

}
