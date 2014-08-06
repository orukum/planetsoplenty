package foodisgood_orukum.mods.pop.network;

import com.google.common.io.ByteArrayDataInput;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;

public interface IPOPPacketReceiver {
	public void handlePacketData(INetworkManager network, int packetTypeID, Packet packet, EntityPlayer player, ByteArrayDataInput data);
}
