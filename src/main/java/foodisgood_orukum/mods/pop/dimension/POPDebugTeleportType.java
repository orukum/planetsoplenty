package foodisgood_orukum.mods.pop.dimension;

import java.util.Random;

import foodisgood_orukum.mods.pop.POPLog;
import foodisgood_orukum.mods.pop.client.POPSkyProvider;
import foodisgood_orukum.mods.pop.space.POPWorld;
import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.ITeleportType;
import micdoodle8.mods.galacticraft.core.GCCoreConfigManager;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.entities.player.GCCorePlayerMP;
import micdoodle8.mods.galacticraft.core.network.GCCorePacketHandlerClient.EnumPacketClient;
import micdoodle8.mods.galacticraft.core.util.PacketUtil;
import micdoodle8.mods.galacticraft.mars.entities.GCMarsEntityLandingBalloons;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class POPDebugTeleportType implements ITeleportType {
	@Override
	public boolean useParachute() {
		return false;
	}

	@Override
	public Vector3 getPlayerSpawnLocation(WorldServer world, EntityPlayerMP player) {
		if (player instanceof GCCorePlayerMP)
			return new Vector3(((GCCorePlayerMP) player).getCoordsTeleportedFromX(), 900.0, ((GCCorePlayerMP) player).getCoordsTeleportedFromZ());

		return null;
	}

	@Override
	public Vector3 getEntitySpawnLocation(WorldServer world, Entity entity) {
		return new Vector3(entity.posX, GCCoreConfigManager.disableLander ? 250.0 : 900.0, entity.posZ);
	}

	@Override
	public Vector3 getParaChestSpawnLocation(WorldServer world, EntityPlayerMP player, Random rand) {
		return null;
	}

	@Override
	public void onSpaceDimensionChanged(World newWorld, EntityPlayerMP player, boolean ridingAutoRocket) {
		if (newWorld.isRemote && newWorld.provider instanceof POPWorld && !((POPWorld)newWorld.provider).rendererLoaded) {
			newWorld.provider.setSkyRenderer(new POPSkyProvider((POPWorld)newWorld.provider));
			((POPWorld)newWorld.provider).rendererLoaded = true;
			POPLog.info("Registered new sky renderer for dimension" + newWorld.provider.dimensionId);
		}
		if (!ridingAutoRocket && player instanceof GCCorePlayerMP && ((GCCorePlayerMP) player).getTeleportCooldown() <= 0) {
			final GCCorePlayerMP gcPlayer = (GCCorePlayerMP) player;

			if (gcPlayer.capabilities.isFlying) {
				gcPlayer.capabilities.isFlying = false;
			}

			GCMarsEntityLandingBalloons lander = new GCMarsEntityLandingBalloons(gcPlayer);
			lander.setPositionAndRotation(player.posX, player.posY, player.posZ, 0, 0);

			if (!newWorld.isRemote)
			{
				newWorld.spawnEntityInWorld(lander);
			}

			final Object[] toSend2 = { 1 };
			gcPlayer.playerNetServerHandler.sendPacketToPlayer(PacketUtil.createPacket(GalacticraftCore.CHANNEL, EnumPacketClient.ZOOM_CAMERA, toSend2));

			gcPlayer.setTeleportCooldown(10);
		}
	}
}
