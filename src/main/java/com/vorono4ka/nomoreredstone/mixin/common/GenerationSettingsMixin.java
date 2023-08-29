package com.vorono4ka.nomoreredstone.mixin.common;

import com.vorono4ka.utilities.ArrayUtils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.feature.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;
import java.util.List;

import static com.vorono4ka.nomoreredstone.NoMoreRedstone.modifiedSettingsCount;

@Mixin(GenerationSettings.Builder.class)
public class GenerationSettingsMixin {
    @Unique
    private static final PlacedFeature[] forbiddenPlacedFeatures = new PlacedFeature[] {
        OrePlacedFeatures.ORE_REDSTONE.value(),
        OrePlacedFeatures.ORE_REDSTONE_LOWER.value(),
    };
    @Unique
    private static final ConfiguredFeature<?, ?>[] forbiddenConfiguredFeatures = new ConfiguredFeature[] {
        OreConfiguredFeatures.ORE_REDSTONE.value(),
    };
    @Unique
    private static final Block[] forbiddenBlocks = new Block[] {
        Blocks.REDSTONE_ORE,
        Blocks.DEEPSLATE_REDSTONE_ORE,
    };

    @Mixin(GenerationSettings.Builder.class)
    public abstract static class Builder {
        @Inject(method = "build", at = @At(value = "HEAD"))
        public void build(CallbackInfoReturnable<GenerationSettings> cir) {
            int removedCount = 0;

            GenerationSettings.Builder builder = (GenerationSettings.Builder) ((Object) this);
            for (List<RegistryEntry<PlacedFeature>> registryEntries : builder.features) {
                Iterator<RegistryEntry<PlacedFeature>> iterator = registryEntries.iterator();
                while (iterator.hasNext()) {
                    RegistryEntry<PlacedFeature> registryEntry = iterator.next();
                    PlacedFeature placedFeature = registryEntry.value();

                    boolean containsForbidden = false;
                    boolean isPlacedFeatureForbidden = ArrayUtils.contains(forbiddenPlacedFeatures, placedFeature);
                    if (!isPlacedFeatureForbidden) {
                        ConfiguredFeature<?, ?> configuredFeature = placedFeature.feature().value();

                        boolean isConfiguredFeatureForbidden = ArrayUtils.contains(forbiddenConfiguredFeatures, configuredFeature);
                        if (!isConfiguredFeatureForbidden) {
                            FeatureConfig config = configuredFeature.config();
                            if (config instanceof OreFeatureConfig oreFeatureConfig) {
                                for (OreFeatureConfig.Target target : oreFeatureConfig.targets) {
                                    boolean isBlockForbidden = ArrayUtils.contains(forbiddenBlocks, target.state.getBlock());
                                    if (isBlockForbidden) {
                                        containsForbidden = true;
                                        break;
                                    }
                                }
                            } else {
                                continue;
                            }
                        } else {
                            containsForbidden = true;
                        }
                    } else {
                        containsForbidden = true;
                    }

                    if (containsForbidden) {
                        iterator.remove();
                        removedCount++;
                    }
                }
            }

            modifiedSettingsCount += removedCount;
        }
    }
}
