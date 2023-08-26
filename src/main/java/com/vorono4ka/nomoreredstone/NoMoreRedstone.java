package com.vorono4ka.nomoreredstone;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoMoreRedstone implements ModInitializer {
    public static final String MOD_ID = "eternal-redstone-locker";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static int modifiedSettingsCount = 0;

    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        LOGGER.info("Initialized!");
        LOGGER.info("Modified GenerationSettings: {}", modifiedSettingsCount);
    }
}
