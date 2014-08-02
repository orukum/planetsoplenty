package foodisgood_orukum.mods.pop.client;

import java.io.ByteArrayInputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import micdoodle8.mods.galacticraft.core.client.render.entities.GCCoreRenderSpaceship;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.*;
import foodisgood_orukum.mods.pop.*;
import foodisgood_orukum.mods.pop.client.model.POPModelTier4Rocket;
import foodisgood_orukum.mods.pop.client.render.items.POPItemRenderSpaceshipT4;
import foodisgood_orukum.mods.pop.entities.POPEntityRocketT4;
import foodisgood_orukum.mods.pop.items.POPItems;

/**
 * Copyright 2014, foodisgoodyesiam and orukum
 * 
 * All rights reserved.
 * 
 */
public class POPClientProxy extends CommonPOPProxy
{
    /*private static int vineRenderID;
    private static int eggRenderID;
    private static int treasureRenderID;
    private static int machineRenderID;
    private static int tintedGlassRenderID;*/
	
	@Override
    public void preInit(FMLPreInitializationEvent event)
    {
        //MinecraftForge.EVENT_BUS.register(new GCMarsSounds());
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        /*TickRegistry.registerTickHandler(new TickHandlerClient(), Side.CLIENT);
        NetworkRegistry.instance().registerChannel(new ClientPacketHandler(), GalacticraftMars.CHANNEL, Side.CLIENT);
        ClientProxyMars.vineRenderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new GCMarsBlockRendererVine(ClientProxyMars.vineRenderID));
        ClientProxyMars.eggRenderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new GCMarsBlockRendererRock(ClientProxyMars.eggRenderID));
        ClientProxyMars.treasureRenderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new GCMarsBlockRendererTreasureChest(ClientProxyMars.treasureRenderID));
        ClientProxyMars.machineRenderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new GCMarsBlockRendererMachine(ClientProxyMars.machineRenderID));
        ClientProxyMars.tintedGlassRenderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new GCMarsBlockRendererTintedGlassPane(ClientProxyMars.tintedGlassRenderID));*/
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
    }

    @Override
    public void registerRenderInformation()
    {
        /*IModelCustom chamberModel = AdvancedModelLoader.loadModel("/assets/galacticraftmars/models/chamber.obj");
        IModelCustom cargoRocketModel = AdvancedModelLoader.loadModel("/assets/galacticraftmars/models/cargoRocket.obj");
        ClientRegistry.bindTileEntitySpecialRenderer(GCMarsTileEntityTreasureChest.class, new GCMarsTileEntityTreasureChestRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(GCMarsTileEntityCryogenicChamber.class, new GCMarsTileEntityCryogenicChamberRenderer(chamberModel));
        RenderingRegistry.registerEntityRenderingHandler(GCMarsEntitySludgeling.class, new GCMarsRenderSludgeling());
        RenderingRegistry.registerEntityRenderingHandler(GCMarsEntitySlimeling.class, new GCMarsRenderSlimeling());
        RenderingRegistry.registerEntityRenderingHandler(GCMarsEntityCreeperBoss.class, new GCMarsRenderCreeperBoss());
        RenderingRegistry.registerEntityRenderingHandler(GCMarsEntityRocketT2.class, new GCCoreRenderSpaceship(new GCMarsModelSpaceshipTier2(), GalacticraftMars.TEXTURE_DOMAIN, "rocketT2"));
        RenderingRegistry.registerEntityRenderingHandler(GCMarsEntityTerraformBubble.class, new GCCoreRenderOxygenBubble(0.25F, 1.0F, 0.25F));
        RenderingRegistry.registerEntityRenderingHandler(GCMarsEntityProjectileTNT.class, new GCMarsRenderProjectileTNT());
        RenderingRegistry.registerEntityRenderingHandler(GCMarsEntityLandingBalloons.class, new GCMarsRenderLandingBalloons());
        RenderingRegistry.registerEntityRenderingHandler(GCMarsEntityLandingBalloons.class, new GCMarsRenderLandingBalloons());
        RenderingRegistry.registerEntityRenderingHandler(GCMarsEntityCargoRocket.class, new GCMarsRenderCargoRocket(cargoRocketModel));
        RenderingRegistry.addNewArmourRendererPrefix("desh");
        MinecraftForgeClient.registerItemRenderer(GCMarsItems.spaceship.itemID, new GCMarsItemRendererSpaceshipT2(cargoRocketModel));
        MinecraftForgeClient.registerItemRenderer(GCMarsItems.key.itemID, new GCCoreItemRendererKey(new ResourceLocation(GalacticraftMars.TEXTURE_DOMAIN, "textures/model/treasure.png")));
        MinecraftForgeClient.registerItemRenderer(GCMarsBlocks.machine.blockID, new GCMarsItemRendererMachine(chamberModel));*/
    	POPModelTier4Rocket model = new POPModelTier4Rocket();
    	//If it happens that the two renderers really do need separate model objects for some reason, we can set it up that way...
    	RenderingRegistry.registerEntityRenderingHandler(POPEntityRocketT4.class, new GCCoreRenderSpaceship(model, PlanetsOPlenty.TEXTURE_DOMAIN, "textures/rocketT4"));
        MinecraftForgeClient.registerItemRenderer(POPItems.spaceshipT4.itemID, new POPItemRenderSpaceshipT4(model));
    }

    /*@Override
    public void spawnParticle(String var1, double var2, double var4, double var6)
    {
        final Minecraft var14 = FMLClientHandler.instance().getClient();

        if (var14 != null && var14.renderViewEntity != null && var14.effectRenderer != null)
        {
            final double var15 = var14.renderViewEntity.posX - var2;
            final double var17 = var14.renderViewEntity.posY - var4;
            final double var19 = var14.renderViewEntity.posZ - var6;
            Object var21 = null;
            final double var22 = 64.0D;

            if (var15 * var15 + var17 * var17 + var19 * var19 < var22 * var22)
            {
                if (var1.equals("sludgeDrip"))
                {
                    var21 = new GCMarsEntityDropParticleFX(var14.theWorld, var2, var4, var6, GCMarsBlocks.bacterialSludge);
                }
            }

            if (var21 != null)
            {
                ((EntityFX) var21).prevPosX = ((EntityFX) var21).posX;
                ((EntityFX) var21).prevPosY = ((EntityFX) var21).posY;
                ((EntityFX) var21).prevPosZ = ((EntityFX) var21).posZ;
                var14.effectRenderer.addEffect((EntityFX) var21);
            }
        }
    }*/

    /*public class ClientPacketHandler implements IPacketHandler
    {
        @Override
        public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player p)
        {
            final DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
            final int packetType = PacketUtil.readPacketID(data);
            EntityPlayer player = (EntityPlayer) p;

            if (packetType == 0)
            {
                final Class<?>[] decodeAs = { Integer.class, Integer.class, Integer.class };
                final Object[] packetReadout = PacketUtil.readPacketData(data, decodeAs);

                int entityID = 0;
                Entity entity = null;

                switch ((Integer) packetReadout[1])
                {
                case 0:
                    entityID = (Integer) packetReadout[2];
                    entity = player.worldObj.getEntityByID(entityID);

                    if (entity != null && entity instanceof GCMarsEntitySlimeling)
                    {
                        FMLClientHandler.instance().getClient().displayGuiScreen(new GCMarsGuiSlimelingInventory(player, (GCMarsEntitySlimeling) entity));
                    }

                    player.openContainer.windowId = (Integer) packetReadout[0];
                    break;
                case 1:
                    entityID = (Integer) packetReadout[2];
                    entity = player.worldObj.getEntityByID(entityID);

                    if (entity != null && entity instanceof GCMarsEntityCargoRocket)
                    {
                        FMLClientHandler.instance().getClient().displayGuiScreen(new GCMarsGuiCargoRocket(player.inventory, (GCMarsEntityCargoRocket) entity));
                    }

                    player.openContainer.windowId = (Integer) packetReadout[0];
                    break;
                }
            }
        }
    }*/

    /*public static boolean handleBacterialMovement(EntityPlayer player)
    {
        return player.worldObj.isMaterialInBB(player.boundingBox.expand(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D), GCMarsBlocks.bacterialSludge);
    }

    public static boolean handleLavaMovement(EntityPlayer player)
    {
        return player.worldObj.isMaterialInBB(player.boundingBox.expand(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D), Material.lava);
    }

    public static boolean handleWaterMovement(EntityPlayer player)
    {
        return player.worldObj.isMaterialInBB(player.boundingBox.expand(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D), Material.water);
    }

    public static boolean handleLiquidMovement(EntityPlayer player)
    {
        return ClientProxyMars.handleBacterialMovement(player) || ClientProxyMars.handleLavaMovement(player) || ClientProxyMars.handleWaterMovement(player);
    }*/

    /*public static class TickHandlerClient implements ITickHandler
    {
        @Override
        public void tickStart(EnumSet<TickType> type, Object... tickData)
        {
            final Minecraft minecraft = FMLClientHandler.instance().getClient();

            final WorldClient world = minecraft.theWorld;

            if (type.equals(EnumSet.of(TickType.CLIENT)))
            {
                if (world != null)
                {
                    if (world.provider instanceof GCMarsWorldProvider)
                    {
                        if (world.provider.getSkyRenderer() == null)
                        {
                            world.provider.setSkyRenderer(new GCMarsSkyProvider());
                        }

                        if (world.provider.getCloudRenderer() == null)
                        {
                            world.provider.setCloudRenderer(new GCCoreCloudRenderer());
                        }
                    }

                    for (int i = 0; i < world.loadedEntityList.size(); i++)
                    {
                        final Entity e = (Entity) world.loadedEntityList.get(i);

                        if (e != null)
                        {
                            if (e instanceof GCMarsEntityRocketT2)
                            {
                                GCMarsEntityRocketT2 eship = (GCMarsEntityRocketT2) e;

                                if (eship.rocketSoundUpdater == null)
                                {
                                    eship.rocketSoundUpdater = new GCCoreSoundUpdaterSpaceship(FMLClientHandler.instance().getClient().sndManager, eship, FMLClientHandler.instance().getClient().thePlayer);
                                }
                            }
                            else if (e instanceof GCMarsEntityCargoRocket)
                            {
                                GCMarsEntityCargoRocket eship = (GCMarsEntityCargoRocket) e;

                                if (eship.rocketSoundUpdater == null)
                                {
                                    eship.rocketSoundUpdater = new GCCoreSoundUpdaterSpaceship(FMLClientHandler.instance().getClient().sndManager, eship, FMLClientHandler.instance().getClient().thePlayer);
                                }
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void tickEnd(EnumSet<TickType> type, Object... tickData)
        {
        }

        @Override
        public String getLabel()
        {
            return "Galacticraft Mars Client";
        }

        @Override
        public EnumSet<TickType> ticks()
        {
            return EnumSet.of(TickType.CLIENT);
        }
    }*/

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        TileEntity tile = world.getBlockTileEntity(x, y, z);

        /*if (ID == GCMarsConfigManager.idGuiMachine)
        {
            if (tile instanceof GCMarsTileEntityTerraformer)
            {
                return new GCMarsGuiTerraformer(player.inventory, (GCMarsTileEntityTerraformer) tile);
            }
            else if (tile instanceof GCMarsTileEntityLaunchController)
            {
                return new GCMarsGuiLaunchController(player.inventory, (GCMarsTileEntityLaunchController) tile);
            }
        }*/

        return null;
    }
}
