package org.teamHTBP.Kaede.DemoGui.dropdown.network.server;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import org.teamHTBP.Kaede.DemoGui.dropdown.capability.DropDownPlayerCap;
import org.teamHTBP.Kaede.DemoGui.dropdown.network.client.DropDownMenuOperateSyncClient;

import java.util.function.Supplier;

/**
 * @author DustW
 */
public class DropDownMenuOperateSyncServer implements IServerMessage {
    CompoundNBT nbt;

    public DropDownMenuOperateSyncServer(CompoundNBT nbt) {
        this.nbt = nbt;
    }

    public static void encode(DropDownMenuOperateSyncServer msg, PacketBuffer packetBuffer) {
        packetBuffer.writeCompoundTag(msg.nbt);
    }

    public static DropDownMenuOperateSyncServer decode(PacketBuffer packetBuffer) {
        return new DropDownMenuOperateSyncServer(packetBuffer.readCompoundTag());
    }

    public static void handle(DropDownMenuOperateSyncServer msg, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            DropDownPlayerCap.get(IServerMessage.getPlayer()).deserializeNBT(msg.nbt);
        });

        contextSupplier.get().setPacketHandled(true);
    }
}
