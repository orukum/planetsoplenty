package foodisgood_orukum.mods.pop.network;

import java.util.Vector;

import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.entities.player.GCCorePlayerMP;
import micdoodle8.mods.galacticraft.core.network.GCCorePacketHandlerClient.EnumPacketClient;
import micdoodle8.mods.galacticraft.core.util.PacketUtil;
import micdoodle8.mods.galacticraft.core.util.WorldUtil;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import foodisgood_orukum.mods.pop.POPExtendedPlayer;

public class POPConnectionHandler implements IConnectionHandler {
    private static boolean clientConnected = false;
    public static Vector<POPExtendedPlayer> players = new Vector<POPExtendedPlayer>(); 

    @Override
    public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager){
        /*if (player instanceof GCCorePlayerMP) {
            final GCCorePlayerMP playerMP = (GCCorePlayerMP) player;
            //PacketDispatcher.sendPacketToPlayer(GCCorePacketSchematicList.buildSchematicListPacket(playerMP.getUnlockedSchematics()), player);
            //PacketDispatcher.sendPacketToPlayer(PacketUtil.createPacket(GalacticraftCore.CHANNEL, EnumPacketClient.UPDATE_SPACESTATION_CLIENT_ID, new Object[] { ((GCCorePlayerMP) player).getSpaceStationDimensionID() }), player);
        }*/
    	// TODO: Handle IExtendedEntityProperties here?
    }

    @Override
    public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager) {
        //manager.addToSendQueue(GCCorePacketDimensionListSpaceStations.buildDimensionListPacket(WorldUtil.registeredSpaceStations));
        //manager.addToSendQueue(GCCorePacketDimensionListPlanets.buildDimensionListPacket(WorldUtil.registeredPlanets));
        return null;
    }

    @Override
    public void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager manager) {
        POPConnectionHandler.clientConnected = true;
    }

    @Override
    public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager)
    {
    }

    @Override
    public void connectionClosed(INetworkManager manager) {
        if (POPConnectionHandler.clientConnected) {
            POPConnectionHandler.clientConnected = false;
            //WorldUtil.unregisterPlanets();
            //WorldUtil.unregisterSpaceStations();
            //TODO: What is it doing here, and why is it doing it? Is a player's space station dimension only registered with Forge while they're on the server?
            //Or does this run when the server shuts down? This merits some exploring.
        }
    }

    @Override
    public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login)
    {
    }
}
