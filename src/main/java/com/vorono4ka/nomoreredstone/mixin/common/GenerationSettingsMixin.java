package com.vorono4ka.nomoreredstone.mixin.common;

import com.vorono4ka.utilities.ArrayUtils;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.feature.OrePlacedFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static com.vorono4ka.nomoreredstone.NoMoreRedstone.modifiedSettingsCount;

@Mixin(GenerationSettings.Builder.class)
public class GenerationSettingsMixin {
    @SuppressWarnings("unchecked")
    @Unique
    private static final RegistryEntry<PlacedFeature>[] forbiddenFeatures = new RegistryEntry[] {
        OrePlacedFeatures.ORE_REDSTONE,
        OrePlacedFeatures.ORE_REDSTONE_LOWER,
    };

    @Mixin(GenerationSettings.Builder.class)
    public abstract static class Builder {
        @Inject(method = "build", at = @At(value = "HEAD"))
        public void build(CallbackInfoReturnable<GenerationSettings> cir) {
            int removedCount = 0;

            GenerationSettings.Builder builder = (GenerationSettings.Builder) ((Object) this);
            for (List<RegistryEntry<PlacedFeature>> registryEntries : builder.features) {
                boolean removed = registryEntries.removeIf(featureRegistry -> ArrayUtils.contains(forbiddenFeatures, featureRegistry));
                if (removed) removedCount++;
            }

            modifiedSettingsCount += removedCount;
        }
    }
}
