package io.github.jasonsimpart.createutilitiesj.blocks.voidtypes;

import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.motor.VoidMotorNetworkHandler.NetworkKey;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.saveddata.SavedData;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class VoidStorageData<T> extends SavedData {

    protected final Map<NetworkKey, T> storages = new HashMap<>();

    public T computeStorageIfAbsent(NetworkKey key, Function<NetworkKey, T> function) {
        return storages.computeIfAbsent(key, function);
    }

    public @NotNull CompoundTag save(@NotNull CompoundTag tag,
                                     HolderLookup.Provider registries,
                                     Function<T, Boolean> isEmpty,
                                     BiFunction<T, HolderLookup.Provider, CompoundTag> serializeNBT) {
        ListTag entries = new ListTag();
        storages.forEach( (key, inventory) -> {
            if (!isEmpty.apply(inventory)) {
                CompoundTag entry = new CompoundTag();
                entry.put("Key", key.serialize(registries));
                entry.put("Value", serializeNBT.apply(inventory, registries));
                entries.add(entry);
            }
        } );
        tag.put("Entries", entries);
        return tag;
    }

    public static <T, S extends VoidStorageData<T>> S load(CompoundTag tag,
                                                           HolderLookup.Provider registries,
                                                           Supplier<S> storageDataSupplier,
                                                           Function<NetworkKey, T> storageSupplier,
                                                           BiConsumer<T, CompoundTag> deserializeNBT) {
        return load(tag, registries, storageDataSupplier, storageSupplier, (storage, provider, storageTag) -> deserializeNBT.accept(storage, storageTag));
    }

    public static <T, S extends VoidStorageData<T>> S load(CompoundTag tag,
                                                           HolderLookup.Provider registries,
                                                           Supplier<S> storageDataSupplier,
                                                           Function<NetworkKey, T> storageSupplier,
                                                           StorageDeserializer<T> deserializeNBT) {
        S data = storageDataSupplier.get();
        tag.getList("Entries", 10).forEach(entryTag -> {
            CompoundTag entry = (CompoundTag) entryTag;
            NetworkKey key = NetworkKey.deserialize(entry.getCompound("Key"), registries);
            T inventory = storageSupplier.apply(key);
            deserializeNBT.accept(inventory, registries, entry.getCompound("Value"));
            data.storages.put(key, inventory);
        });
        return data;
    }

    @FunctionalInterface
    public interface StorageDeserializer<T> {
        void accept(T storage, HolderLookup.Provider registries, CompoundTag tag);
    }

}
