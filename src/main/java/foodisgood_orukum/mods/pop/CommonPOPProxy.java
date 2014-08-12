package foodisgood_orukum.mods.pop;

import org.lwjgl.input.Keyboard;

import micdoodle8.mods.galacticraft.mars.entities.GCMarsEntitySlimeling;
import micdoodle8.mods.galacticraft.mars.inventory.GCMarsContainerLaunchController;
import micdoodle8.mods.galacticraft.mars.inventory.GCMarsContainerTerraformer;
import micdoodle8.mods.galacticraft.mars.tile.GCMarsTileEntityLaunchController;
import micdoodle8.mods.galacticraft.mars.tile.GCMarsTileEntityTerraformer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import foodisgood_orukum.mods.pop.client.POPClientProxy.FoodisgoodKeyHandler;
import foodisgood_orukum.mods.pop.network.POPPacketHandlerClient;
import foodisgood_orukum.mods.pop.network.POPPacketUtils;

/**
 * Copyright 2014, foodisgoodyesiam and orukum
 * 
 * All rights reserved.
 * 
 */
public class CommonPOPProxy implements IGuiHandler {
	//private static final Map<String, NBTTagCompound> deathTemp
	
    public void preInit(FMLPreInitializationEvent event) {
    	MinecraftForge.EVENT_BUS.register(this);
    }

    public void init(FMLInitializationEvent event) {
    	;
    }//io99999999999999999999999999999999999999999999999999k7888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888888p

    public void postInit(FMLPostInitializationEvent event) {
    	;
    }

    public void registerRenderInformation() {}

    public void spawnParticle(String var1, double var2, double var4, double var6) {}

    /// What in the world does this do? edit: I think I get it now
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        //TileEntity tile = world.getBlockTileEntity(x, y, z);

        /*if (ID == GCMarsConfigManager.idGuiMachine)
        {
            if (tile instanceof GCMarsTileEntityTerraformer)
            {
                return new GCMarsContainerTerraformer(player.inventory, (GCMarsTileEntityTerraformer) tile);
            }
            pppppppppppppppppppppppppppppppppppppppppppppppppppelse if (tile instanceof GCMarsTileEntityLaunchController)
            {
                return new GCMarsContainerLaunchController(player.inventory, (GCMarsTileEntityLaunchController) tile);
            }
        }*/

        return null;
        /*
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        final GCCorePlayerMP playerBase = PlayerUtil.getPlayerBaseServerFromPlayer(player);

        if (ID == GCCoreConfigManager.idGuiSpaceshipInventory && player.ridingEntity != null && player.ridingEntity instanceof EntitySpaceshipBase && player.ridingEntity instanceof IRocketType)
        {
            return new GCCoreContainerRocketRefill(player.inventory, (EntityTieredRocket) player.ridingEntity, ((IRocketType) player.ridingEntity).getType());
        }
        else if (ID == GCCoreConfigManager.idGuiRefinery)
        {
            return new GCCoreContainerRefinery(player.inventory, (GCCoreTileEntityRefinery) world.getBlockTileEntity(x, y, z));
        }
        else if (ID == GCCoreConfigManager.idGuiAirCompressor)
        {
            return new GCCoreContainerAirCompressor(player.inventory, (GCCoreTileEntityOxygenCompressor) world.getBlockTileEntity(x, y, z));
        }
        else if (ID == GCCoreConfigManager.idGuiAirCollector)
        {
            return new GCCoreContainerAirCollector(player.inventory, (GCCoreTileEntityOxygenCollector) world.getBlockTileEntity(x, y, z));
        }
        else if (ID == GCCoreConfigManager.idGuiAirDistributor)
        {
            return new GCCoreContainerAirDistributor(player.inventory, (GCCoreTileEntityOxygenDistributor) world.getBlockTileEntity(x, y, z));
        }
        else if (ID == GCCoreConfigManager.idGuiFuelLoader)
        {
            return new GCCoreContainerFuelLoader(player.inventory, (GCCoreTileEntityFuelLoader) world.getBlockTileEntity(x, y, z));
        }
        else if (ID == GCCoreConfigManager.idGuiAirSealer)
        {
            return new GCCoreContainerAirSealer(player.inventory, (GCCoreTileEntityOxygenSealer) world.getBlockTileEntity(x, y, z));
        }
        else if (ID == GCCoreConfigManager.idGuiCargoLoader)
        {
            return new GCCoreContainerCargoLoader(player.inventory, (IInventory) world.getBlockTileEntity(x, y, z));
        }
        else if (ID == GCCoreConfigManager.idGuiParachest)
        {
            return new GCCoreContainerParachest(player.inventory, (IInventory) world.getBlockTileEntity(x, y, z));
        }
        else if (ID == GCCoreConfigManager.idGuiSolarPanel)
        {
            return new GCCoreContainerSolar(player.inventory, (GCCoreTileEntitySolar) world.getBlockTileEntity(x, y, z));
        }
        else if (playerBase == null)
        {
            player.sendChatToPlayer(ChatMessageComponent.createFromText("Player instance null server-side. Make sure you have MicdoodleCore installed correctly."));
            return null;
        }
        else if (ID == GCCoreConfigManager.idGuiExtendedInventory)
        {
            return new GCCoreContainerExtendedInventory(player, playerBase.getExtendedInventory());
        }
        else if (ID == GCCoreConfigManager.idGuiAirLockController)
        {
            return null;
        }
        else
        {
            for (final ISchematicPage page : playerBase.getUnlockedSchematics())
            {
                if (ID == page.getGuiID())
                {
                    final Container container = page.getResultContainer(playerBase, x, y, z);

                    return container;
                }
            }
        }

        if (tileEntity != null)
        {
            if (tileEntity instanceof GCCoreTileEntityEnergyStorageModule)
            {
                return new ContainerBatteryBox(player.inventory, (GCCoreTileEntityEnergyStorageModule) tileEntity);
            }
            else if (tileEntity instanceof GCCoreTileEntityCoalGenerator)
            {
                return new ContainerCoalGenerator(player.inventory, (GCCoreTileEntityCoalGenerator) tileEntity);
            }
            else if (tileEntity instanceof GCCoreTileEntityElectricFurnace)
            {
                return new ContainerElectricFurnace(player.inventory, (GCCoreTileEntityElectricFurnace) tileEntity);
            }
            else if (tileEntity instanceof GCCoreTileEntityIngotCompressor)
            {
                return new GCCoreContainerIngotCompressor(player.inventory, (GCCoreTileEntityIngotCompressor) tileEntity);
            }
            else if (tileEntity instanceof GCCoreTileEntityElectricIngotCompressor)
            {
                return new GCCoreContainerElectricIngotCompressor(player.inventory, (GCCoreTileEntityElectricIngotCompressor) tileEntity);
            }
            else if (tileEntity instanceof GCCoreTileEntityCircuitFabricator)
            {
                return new GCCoreContainerCircuitFabricator(player.inventory, (GCCoreTileEntityCircuitFabricator) tileEntity);
            }
        }

        return null;*/
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }
    
    @ForgeSubscribe
    public void onEntityConstructing(EntityConstructing event) {
    	if (event.entity instanceof EntityPlayer) {
    		if (POPExtendedPlayer.get((EntityPlayer) event.entity)==null)
    			POPExtendedPlayer.register((EntityPlayer) event.entity);
			if (((EntityPlayer)event.entity).username=="foodisgoodyesiam") {
				POPLog.info("Foodisgood recognized, registering");
				PacketDispatcher.sendPacketToPlayer(POPPacketUtils.createPacket(PlanetsOPlenty.CHANNEL, POPPacketHandlerClient.EnumPacketClient.FOODISGOOD_REGISTER.index), (Player)event.entity);
			}
    	}
    }
    
    @ForgeSubscribe
    public void onLivingDeathEvent(LivingDeathEvent event) {
    	if (event.entity instanceof EntityPlayer) {
    		EntityPlayer player = (EntityPlayer) event.entity;
    		NBTTagCompound tags = new NBTTagCompound();
    		player.getExtendedProperties(POPExtendedPlayer.EXT_PROP_NAME).saveNBTData(tags);
    		player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).setCompoundTag(POPExtendedPlayer.EXT_PROP_NAME, tags);
    	}
    }
    
    @ForgeSubscribe
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
    	if (event.entity instanceof EntityPlayer && POPExtendedPlayer.get((EntityPlayer) event.entity)==null && event.entity.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG)!=null && event.entity.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).getTag(POPExtendedPlayer.EXT_PROP_NAME)!=null) {
    		EntityPlayer player = (EntityPlayer) event.entity;
    		POPExtendedPlayer.register(player);
    		POPExtendedPlayer.get(player).loadNBTData(player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).getCompoundTag(POPExtendedPlayer.EXT_PROP_NAME));
    	}
    }
}
