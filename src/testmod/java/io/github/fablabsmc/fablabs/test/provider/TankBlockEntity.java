package io.github.fablabsmc.fablabs.test.provider;

import alexiil.mc.lib.attributes.Simulation;
import alexiil.mc.lib.attributes.fluid.*;
import alexiil.mc.lib.attributes.fluid.amount.FluidAmount;
import alexiil.mc.lib.attributes.fluid.filter.FluidFilter;
import alexiil.mc.lib.attributes.fluid.volume.FluidKey;
import alexiil.mc.lib.attributes.fluid.volume.FluidKeys;
import alexiil.mc.lib.attributes.fluid.volume.FluidVolume;
import io.github.fablabsmc.fablabs.test.provider.mixin.ClientWorldMixin;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.fabricmc.fabric.api.rendering.data.v1.RenderAttachmentBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;

import java.math.RoundingMode;

public class TankBlockEntity extends BlockEntity implements BlockEntityClientSerializable, RenderAttachmentBlockEntity, FluidTransferable, Tickable {
    private FluidKey fluid = FluidKeys.EMPTY;
    private int amount = 0;
    private int capacity = 10000;

    public TankBlockEntity() {
        super(TestMod.TANK_BLOCK_ENTITY_TYPE);
    }

    public boolean isEmpty() {
        return amount == 0;
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        fluid = FluidKey.fromTag(tag.getCompound("fluid"));
        amount = tag.getInt("amount");
        capacity = tag.getInt("capacity");

        if(world != null && world.isClient) {
            ClientWorld clientWorld = (ClientWorld) world;
            ((ClientWorldMixin) clientWorld).getWorldRenderer().updateBlock(null, this.pos, null, null, 0);
        }
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        tag.put("fluid", fluid.toTag());
        tag.putInt("amount", amount);
        tag.putInt("capacity", capacity);
        return tag;
    }

    public void onChanged() {
        markDirty();
        if(!world.isClient) sync();
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        toClientTag(tag);
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        fromClientTag(tag);
        super.fromTag(state, tag);
    }

    /**
     * PROVIDER-API
     * LBA fluid insertion code
     */
    @Override
    public FluidVolume attemptInsertion(FluidVolume fluid, Simulation simulation) {
        int ins = 0;
        if(this.fluid.isEmpty()) {
            ins = Math.min(capacity, fluid.amount().asInt(1000, RoundingMode.FLOOR));
            if(ins > 0 && simulation.isAction()) {
                this.fluid = fluid.getFluidKey();
                this.amount += ins;
                onChanged();
            }
        } else if(this.fluid == fluid.getFluidKey()) {
            ins = Math.min(capacity - amount, fluid.amount().asInt(1000, RoundingMode.FLOOR));
            if(ins > 0 && simulation.isAction()) {
                this.amount += ins;
                onChanged();
            }
        }
        return fluid.getFluidKey().withAmount(fluid.amount().sub(FluidAmount.of(ins, 1000)));
    }

    /**
     * PROVIDER-API
     * LBA fluid extraction code
     */
    @Override
    public FluidVolume attemptExtraction(FluidFilter filter, FluidAmount maxAmount, Simulation simulation) {
        if(!this.fluid.isEmpty() && filter.matches(this.fluid)) {
            int ext = Math.min(amount, maxAmount.asInt(1000, RoundingMode.FLOOR));
            FluidKey key = this.fluid;
            if(simulation.isAction()) {
                amount -= ext;
                if(amount == 0) this.fluid = FluidKeys.EMPTY;
                onChanged();
            }
            return key.withAmount(FluidAmount.of(ext, 1000));
        }
        return FluidVolumeUtil.EMPTY;
    }

    @Override
    public Object getRenderAttachmentData() {
        return new RenderAttachment(fluid.getRawFluid(), (float) amount / capacity);
    }

    public static class RenderAttachment {
        public final Fluid fluid;
        public final float fillFraction;

        public RenderAttachment(Fluid fluid, float fillFraction) {
            this.fluid = fluid;
            this.fillFraction = fillFraction;
        }
    }

    /**
     * PROVIDER-API
     * Getting hold of a FluidInsertable using the provider API, and then moving some fluids from this (which implements FluidExtractable) to the FluidInsertable
     * using LBA's FluidVolumeUtil#move.
     */
    @Override
    public void tick() {
        if(!world.isClient) {
            // Try to move down 1 bucket of fluid per second.
            FluidInsertable target = ApiKeys.SIDED_FLUID_INSERTABLE.get(world, pos.offset(Direction.DOWN), Direction.UP);
            if(target != null) {
                FluidVolumeUtil.move(this, target, FluidAmount.of(1, 20));
            }
        }
    }
}
