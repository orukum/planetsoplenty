package foodisgood_orukum.mods.pop;

import java.util.logging.Level;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.FMLRelaunchLog;
import foodisgood_orukum.mods.pop.network.POPPacketHandlerClient;
import foodisgood_orukum.mods.pop.network.POPPacketUtils;

public final class POPLog {
    public static void info(String message) {
        FMLRelaunchLog.log("Planets O Plenty", Level.INFO, message);
    }

    public static void severe(String message) {
        FMLRelaunchLog.log("Planets O Plenty", Level.SEVERE, message);
    }
    
    public static void info(String message, EntityPlayer player) {
        FMLRelaunchLog.log("Planets O Plenty", Level.INFO, "Sent also to client: " + message);
        if (!player.worldObj.isRemote)
        	PacketDispatcher.sendPacketToPlayer(POPPacketUtils.createPacket(PlanetsOPlenty.CHANNEL, POPPacketHandlerClient.EnumPacketClient.POPLOG.index, false, message), (Player) player);
    }

    public static void severe(String message, EntityPlayer player) {
        FMLRelaunchLog.log("Planets O Plenty", Level.SEVERE, "Sent also to client: " + message);
        if (!player.worldObj.isRemote)
        	PacketDispatcher.sendPacketToPlayer(POPPacketUtils.createPacket(PlanetsOPlenty.CHANNEL, POPPacketHandlerClient.EnumPacketClient.POPLOG.index, true, message), (Player) player);
    }
}
