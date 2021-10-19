package org.teamHTBP.Kaede.DemoGui.dropdown.capability;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.teamHTBP.Kaede.DemoGui.dropdown.DropDownMenuServer;
import org.teamHTBP.Kaede.DemoGui.dropdown.network.KaedeNetworkManager;
import org.teamHTBP.Kaede.DemoGui.dropdown.network.client.DropDownMenuOperateSyncClient;
import org.teamHTBP.Kaede.DemoGui.dropdown.network.server.DropDownMenuOperateSyncServer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

/**
 * @author q2437
 */
@Mod.EventBusSubscriber
public class DropDownPlayerCap implements INBTSerializable<CompoundNBT> {
    public static DropDownPlayerCap get(PlayerEntity player) {
        return player.getCapability(Provider.CAPABILITY).resolve().get();
    }

    DropDownMenuServer dropDownMenuServer = new DropDownMenuServer(new ResourceLocation("test_server"), 2);

    public void handlePackage(ServerPlayerEntity player, DropDownMenuOperateSyncClient pack) {
        ItemStack switchItem = player.inventory.getStackInSlot(pack.playerSlotId);
        player.inventory.setInventorySlotContents(pack.playerSlotId, dropDownMenuServer.getItemFromSlot(pack.slotId));
        dropDownMenuServer.setItem(pack.slotId, switchItem);
        syncToClient(player);
    }

    public void syncToClient(ServerPlayerEntity player) {
        KaedeNetworkManager.serverSendToPlayer(new DropDownMenuOperateSyncServer(serializeNBT()), player);
    }

    public ItemStack getItemFromSlot(int index) {
        return dropDownMenuServer.getItemFromSlot(index);
    }

    @Override
    public CompoundNBT serializeNBT() {
        return dropDownMenuServer.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        dropDownMenuServer.deserializeNBT(nbt);
    }

    @SubscribeEvent
    public static void attach(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation("drop-down"), new Provider());
        }
    }

    @SubscribeEvent
    public static void login(PlayerEvent.PlayerLoggedInEvent event) {
        get(event.getPlayer()).syncToClient((ServerPlayerEntity) event.getPlayer());
    }

    public static class Provider implements ICapabilityProvider, INBTSerializable<CompoundNBT> {
        @CapabilityInject(DropDownPlayerCap.class)
        public static Capability<DropDownPlayerCap> CAPABILITY;

        LazyOptional<DropDownPlayerCap> context = LazyOptional.of(DropDownPlayerCap::new);

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            return cap == CAPABILITY ? context.cast() : LazyOptional.empty();
        }

        @Override
        public CompoundNBT serializeNBT() {
            return context.resolve().get().serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundNBT nbt) {
            context.resolve().get().deserializeNBT(nbt);
        }
    }
}
