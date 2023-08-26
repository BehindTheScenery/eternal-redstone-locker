package com.vorono4ka.nomoreredstone.mixin.common;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(RedstoneWireBlock.class)
public abstract class RedstoneWireBlockMixin extends Block {
    public RedstoneWireBlockMixin(Settings settings) {
        super(settings);
    }

    /**
     * @author Vorono4ka
     * @reason disable redstone connection
     */
    @SuppressWarnings("deprecation")
    @Overwrite
    public boolean emitsRedstonePower(BlockState state) {
        return false;
    }

    /**
     * @author Vorono4ka
     * @reason disable redstone dust
     */
    @Overwrite
    private int getReceivedRedstonePower(World world, BlockPos pos) {
        return 0;
    }
}
