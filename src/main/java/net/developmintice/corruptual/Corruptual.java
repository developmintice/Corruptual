package net.developmintice.corruptual;

import net.developmintice.corruptual.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Corruptual implements ModInitializer {
	public static final String MOD_ID = "corruptual";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.initialize();
	}
}