package net.developmintice.corruptual.item.custom;

import net.developmintice.corruptual.item.ModItems;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class DormantVesselItem extends Item {

    public DormantVesselItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        BlockHitResult blockHitResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        if (blockHitResult.getType() != HitResult.Type.MISS) {
            if (blockHitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = blockHitResult.getBlockPos();
                if (!world.canEntityModifyAt(user, blockPos)) {
                    return ActionResult.PASS;
                }

                // Placeholder for blood filling at cauldron
                if (world.getFluidState(blockPos).isIn(FluidTags.WATER)) {
                    world.emitGameEvent(user, GameEvent.FLUID_PICKUP, blockPos);
                    PlayerInventory inventory = user.getInventory();
                    int slot = inventory.getSelectedSlot();
                    inventory.removeStack(slot);
                    awakenVessel(world, user);
                    return ActionResult.SUCCESS;
                }
            }
        }
        return ActionResult.PASS;
    }

        private void awakenVessel(World world, PlayerEntity user) {
            int ticks = 135;
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, ticks, 0));
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, ticks, 9));
                user.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, ticks, 0));
            new Thread(() -> {
                try {
                    for (int i = 0; i < 2; i++) {
                        world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_WARDEN_HEARTBEAT, SoundCategory.NEUTRAL, 1.0F, 0.6F);
                        Thread.sleep(600);
                        world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_WARDEN_HEARTBEAT, SoundCategory.NEUTRAL, 1.0F, 0.6F);
                        Thread.sleep(1200);
                    }
                    Thread.sleep(500);
                    for (int i = 0; i < 5; i++) {
                        world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_WARDEN_HEARTBEAT, SoundCategory.NEUTRAL, 1.0F, 1.4F);
                        Thread.sleep(150);
                        world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_WARDEN_HEARTBEAT, SoundCategory.NEUTRAL, 1.0F, 1.4F);
                        Thread.sleep(400);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_WARDEN_DEATH, SoundCategory.NEUTRAL, 1.0F, 0.5F);
                world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_LIGHTNING_BOLT_IMPACT, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                user.giveItemStack(new ItemStack(ModItems.DAMNED_VESSEL));
            }).start();
    }
}
