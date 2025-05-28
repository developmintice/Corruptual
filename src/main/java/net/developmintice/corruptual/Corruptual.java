package net.developmintice.corruptual;

import net.developmintice.corruptual.item.ModItems;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Corruptual implements ModInitializer {
	public static final String MOD_ID = "corruptual";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.initialize();
		ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
			if (!itemStack.isOf(ModItems.DORMANT_VESSEL)) {
				return;
			}
			list.add(Text.translatable("item.corruptual.dormant_vessel.tooltip"));
		});
	}
}