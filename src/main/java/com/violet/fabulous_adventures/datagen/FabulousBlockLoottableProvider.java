package com.violet.fabulous_adventures.datagen;

import com.violet.fabulous_adventures.block.FabulousBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.SingleRegistryBootstrap;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class FabulousBlockLoottableProvider extends BlockLootSubProvider {
    public FabulousBlockLoottableProvider(Context context) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, context);
    }

    public static SingleRegistryBootstrap<LootTable> create() {
        return new LootTableProvider(Collections.emptySet(), List.of(
                new LootTableProvider.SubProviderEntry(FabulousBlockLoottableProvider::new, LootContextParamSets.BLOCK)
        ));
    }

    @Override
    protected void generate() {
        dropSelf(FabulousBlocks.ROPE.get());
        dropSelf(FabulousBlocks.MAP_DISPLAY.get());
        this.add(FabulousBlocks.OXYGEN_TANK.get(), LootTable.lootTable().withPool(
                        LootPool.lootPool()
                                .setRolls(ContextIntProviders.exactly(1))
                                .add(LootItem.lootTableItem(FabulousBlocks.OXYGEN_TANK.get())
                                        .apply(CopyComponentsFunction.copyComponentsFromBlockEntity(LootContextParams.BLOCK_ENTITY)
                                        )
                                )
                )
        );
    }
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return FabulousBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
