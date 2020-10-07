package io.github.fablabsmc.fablabs.test.provider;

import alexiil.mc.lib.attributes.AttributeList;
import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.volume.FluidKeys;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class TankBlock extends Block implements BlockEntityProvider {
    public TankBlock(Settings settings) {
        super(settings.nonOpaque());
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new TankBlockEntity();
    }

    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return 0;
    }

    private ItemStack getStack(BlockEntity entity) {
        TankBlockEntity tankEntity = (TankBlockEntity) entity;
        ItemStack stack = new ItemStack(asItem());
        if (!tankEntity.isEmpty()) {
            CompoundTag tag = new CompoundTag();
            tag.put("BlockEntityTag", tankEntity.toClientTag(new CompoundTag()));
            stack.setTag(tag);
        }
        return stack;
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
        LootContext lootContext = builder.parameter(LootContextParameters.BLOCK_STATE, state).build(LootContextTypes.BLOCK);
        return Arrays.asList(getStack(lootContext.get(LootContextParameters.BLOCK_ENTITY)));
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return getStack(world.getBlockEntity(pos));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        TankBlockEntity be = (TankBlockEntity) world.getBlockEntity(pos);
        FluidVolume waterBucket = FluidKeys.WATER.withAmount(FluidAmount.ofWhole(1));
        be.insert(waterBucket);
        return ActionResult.success(world.isClient);
    }
}
