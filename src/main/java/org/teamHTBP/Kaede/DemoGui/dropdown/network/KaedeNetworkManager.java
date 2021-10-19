package org.teamHTBP.Kaede.DemoGui.dropdown.network;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.teamHTBP.Kaede.DemoGui.dropdown.network.client.DropDownMenuOperateSyncClient;
import org.teamHTBP.Kaede.DemoGui.dropdown.network.client.IClientMessage;
import org.teamHTBP.Kaede.DemoGui.dropdown.network.server.DropDownMenuOperateSyncServer;
import org.teamHTBP.Kaede.DemoGui.dropdown.network.server.IServerMessage;
import org.teamHTBP.Kaede.Kaede;

import java.util.Optional;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class KaedeNetworkManager {
    private static final String PROTOCOL_VERSION = "1.0";
    private static int id = 0;

    public static SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel (
            new ResourceLocation(Kaede.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    /** 不允许产生该类的实例 */
    private KaedeNetworkManager() {
        throw new UnsupportedOperationException("No instance");
    }

    public static <T extends IServerMessage> void serverSendToPlayer(T packet, ServerPlayerEntity player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public static <T extends IClientMessage> void clientSendToServer(T packet) {
        INSTANCE.sendToServer(packet);
    }

    @SubscribeEvent
    public static void networkRegistryEvent(FMLCommonSetupEvent event) {
        registerPackets();
    }

    private static final Optional<NetworkDirection> SERVER = Optional.of(NetworkDirection.PLAY_TO_CLIENT);
    private static final Optional<NetworkDirection> CLIENT = Optional.of(NetworkDirection.PLAY_TO_SERVER);

    public static void registerPackets() {
        INSTANCE.registerMessage(id++,
                DropDownMenuOperateSyncClient.class,
                DropDownMenuOperateSyncClient::encode,
                DropDownMenuOperateSyncClient::decode,
                DropDownMenuOperateSyncClient::handle, CLIENT);

        INSTANCE.registerMessage(id++,
                DropDownMenuOperateSyncServer.class,
                DropDownMenuOperateSyncServer::encode,
                DropDownMenuOperateSyncServer::decode,
                DropDownMenuOperateSyncServer::handle, SERVER);
    }
}
