package com.vorono4ka.nomoreredstone.mixin.common;

import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(AbstractRedstoneGateBlock.class)
public abstract class AbstractRedstoneGateBlockMixin extends HorizontalFacingBlock {
    protected AbstractRedstoneGateBlockMixin(Settings settings) {
        super(settings);
    }

    /**
     * @author Vorono4ka
     * @reason disable redstone comparator and repeater connection with redstone
     */
    @SuppressWarnings("deprecation")
    @Overwrite
    public boolean emitsRedstonePower(BlockState state) {
        return false;
    }

    /**
     * @author Vorono4ka
     * @reason disable redstone comparator and repeater
     */
    @Overwrite
    public boolean isValidInput(BlockState state) {
        return false;
    }

    /**
     * @author Vorono4ka
     * @reason disable redstone comparator and repeater
     */
    @Overwrite
    public int getPower(World world, BlockPos pos, BlockState state) {
        return 0;
    }
}
