package net.developmintice.corruptual.item;

import net.developmintice.corruptual.Corruptual;
import net.developmintice.corruptual.item.custom.*;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.function.Function;

public final class ModItems {
    public static final Item DORMANT_VESSEL = register("dormant_vessel", DormantVesselItem::new, new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON));
    public static final Item DAMNED_VESSEL = register("damned_vessel", DamnedVesselItem::new, new Item.Settings().maxCount(1).useCooldown(5).rarity(Rarity.EPIC));

    public static final RegistryKey<ItemGroup> CORRUPTUAL_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(Corruptual.MOD_ID, "item_group"));
    public static final ItemGroup CORRUPTUAL_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.DAMNED_VESSEL))
            .displayName(Text.translatable("itemGroup.corruptual"))
            .build();

    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        // Create the item key.
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Corruptual.MOD_ID, name));

        // Create the item instance.
        Item item = itemFactory.apply(settings.registryKey(itemKey));

        // Register the item.
        Registry.register(Registries.ITEM, itemKey, item);

        return item;
    }

    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, CORRUPTUAL_ITEM_GROUP_KEY, CORRUPTUAL_ITEM_GROUP);

        ItemGroupEvents.modifyEntriesEvent(CORRUPTUAL_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(ModItems.DORMANT_VESSEL);
            itemGroup.add(ModItems.DAMNED_VESSEL);
        });

        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
            if (!itemStack.isOf(ModItems.DORMANT_VESSEL)) {
                return;
            }
            list.add(Text.translatable("item.corruptual.dormant_vessel.tooltip"));
        });
    }
}