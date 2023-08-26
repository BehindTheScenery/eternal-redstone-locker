package com.vorono4ka.nomoreredstone.mixin.common;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(RedstoneBlock.class)
public abstract class RedstoneBlockMixin extends Block {
    public RedstoneBlockMixin(Settings settings) {
        super(settings);
    }

    /**
     * @author Vorono4ka
     * @reason disable redstone block
     */
    @SuppressWarnings("deprecation")
    @Overwrite
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        return 0;
    }
}
