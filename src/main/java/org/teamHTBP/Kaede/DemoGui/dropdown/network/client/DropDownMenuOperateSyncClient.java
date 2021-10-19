package org.teamHTBP.Kaede.DemoGui.dropdown.network.client;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import org.teamHTBP.Kaede.DemoGui.dropdown.capability.DropDownPlayerCap;
import org.teamHTBP.Kaede.DemoGui.dropdown.network.server.IServerMessage;

import java.util.function.Supplier;

/**
 * @author DustW
 */
public class DropDownMenuOperateSyncClient implements IClientMessage {
    public int slotId;
    public int playerSlotId;

    public DropDownMenuOperateSyncClient(int slotId, int playerSlotId) {
        this.slotId = slotId;
        this.playerSlotId = playerSlotId;
    }

    public static void encode(DropDownMenuOperateSyncClient msg, PacketBuffer packetBuffer) {
        packetBuffer.writeInt(msg.slotId);
        packetBuffer.writeInt(msg.playerSlotId);
    }

    public static DropDownMenuOperateSyncClient decode(PacketBuffer packetBuffer) {
        return new DropDownMenuOperateSyncClient(packetBuffer.readInt(), packetBuffer.readInt());
    }

    public static void handle(DropDownMenuOperateSyncClient msg, Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            DropDownPlayerCap.get(contextSupplier.get().getSender()).handlePackage(contextSupplier.get().getSender(), msg);
        });

        contextSupplier.get().setPacketHandled(true);
    }
}
