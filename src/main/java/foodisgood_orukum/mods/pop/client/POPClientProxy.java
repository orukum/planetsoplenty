package foodisgood_orukum.mods.pop.client;

import java.io.ByteArrayInputStream;
import java.util.EnumSet;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.*;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.*;
import net.minecraftforge.common.MinecraftForge;
import micdoodle8.mods.galacticraft.core.client.fx.*;
import micdoodle8.mods.galacticraft.core.client.render.entities.GCCoreRenderSpaceship;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.*;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.*;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import foodisgood_orukum.mods.pop.*;
import foodisgood_orukum.mods.pop.client.model.POPModelTier4Rocket;
import foodisgood_orukum.mods.pop.client.render.items.POPItemRenderSpaceshipT4;
import foodisgood_orukum.mods.pop.entities.POPEntityRocketT4;
import foodisgood_orukum.mods.pop.items.POPItems;
import foodisgood_orukum.mods.pop.network.*;
import foodisgood_orukum.mods.pop.network.POPPacketHandlerServer.EnumPacketServer;
import foodisgood_orukum.mods.pop.space.POPWorld;

/**
 * Copyright 2014, foodisgoodyesiam and orukum
 * 
 * All rights reserved.
 * 
 */
public class POPClientProxy extends CommonPOPProxy {
    /*private static int vineRenderID;
    private static int eggRenderID;
    private static int treasureRenderID;
    private static int machineRenderID;
    private static int tintedGlassRenderID;*/
	public static EnumRarity popItemRarity = EnumHelperClient.addRarity("POPRarity", 7, "Exoplanetary");
	
	public KeyBinding explode = null, incCounter = null, sendServerChat = null, changeWorldGen = null, arrowCount = null;
	
	public class DebugKeyHandler extends KeyBindingRegistry.KeyHandler {
		boolean stillPressed = false; 
		public DebugKeyHandler(KeyBinding[] keyBindings, boolean[] repeat) {
			super(keyBindings, repeat);
			POPLog.info("POP: new DebugKeyHandler()");
		}

		@Override
		public final String getLabel() {
			return "POP Debug Keys";
		}

		@Override
		public final void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat) {
			//if (isRepeat)
			//	return;
			POPLog.info("Planets O Plenty: debug key has been pressed, sending packet to server");
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			if (explode.isPressed()) {
				PacketDispatcher.sendPacketToServer(POPPacketUtils.createPacket(PlanetsOPlenty.CHANNEL, EnumPacketServer.DEBUG_EXPLODE.index, player.posX-20*MathHelper.sin(player.rotationYawHead*((float)Math.PI)/180F)*MathHelper.cos(player.rotationPitch*((float)Math.PI)/180F), player.posY-20*MathHelper.sin(player.rotationPitch*((float)Math.PI)/180F), player.posZ+20*MathHelper.cos(player.rotationYawHead*((float)Math.PI)/180F)*MathHelper.cos(player.rotationPitch*((float)Math.PI)/180F)));
			} else if (incCounter.isPressed()) {
				PacketDispatcher.sendPacketToServer(POPPacketUtils.createPacket(PlanetsOPlenty.CHANNEL, EnumPacketServer.DEBUG_INC_COUNTER.index));
			} else if (sendServerChat.isPressed()) {
				PacketDispatcher.sendPacketToServer(POPPacketUtils.createPacket(PlanetsOPlenty.CHANNEL, EnumPacketServer.DEBUG_SEND_SERVER_CHAT.index));
			} else if (changeWorldGen.isPressed()) {
				PacketDispatcher.sendPacketToServer(POPPacketUtils.createPacket(PlanetsOPlenty.CHANNEL, EnumPacketServer.DEBUG_WEST_WORLD_GEN.index));
			} else if (arrowCount.isPressed())
				PacketDispatcher.sendPacketToServer(POPPacketUtils.createPacket(PlanetsOPlenty.CHANNEL, EnumPacketServer.DEBUG_ADD_ARROW.index));
		}

		@Override
		public final void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
			stillPressed = false;
		}

		@Override
		public final EnumSet<TickType> ticks() {
			return EnumSet.of(TickType.CLIENT);
		}
	}
	
	@Override
    public final void preInit(FMLPreInitializationEvent event) {
        //MinecraftForge.EVENT_BUS.register(new GCMarsSounds());
		if (PlanetsOPlenty.debug) {
			//MinecraftForge.EVENT_BUS.register(new POPClientDebugListener());
			explode = new KeyBinding("Explode", Keyboard.KEY_P);
			incCounter = new KeyBinding("Increment Tick", Keyboard.KEY_KANA);
			sendServerChat = new KeyBinding("Send Server Chat", Keyboard.KEY_KANJI);
			changeWorldGen = new KeyBinding("Change world gen for West test planet", Keyboard.KEY_YEN);
			arrowCount = new KeyBinding("Add arrow", Keyboard.KEY_Z);
			KeyBinding[] keys = {explode, incCounter, sendServerChat, changeWorldGen, arrowCount};
			boolean[] repeat = {true, true, true, true, true};
			KeyBindingRegistry.registerKeyBinding(new DebugKeyHandler(keys, repeat));
		}
    }

    @Override
    public final void init(FMLInitializationEvent event) {
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
        NetworkRegistry.instance().registerChannel(new POPPacketHandlerClient(), PlanetsOPlenty.CHANNEL, Side.CLIENT);
		TickRegistry.registerTickHandler(new TickHandlerClient(), Side.CLIENT);
    }

    @Override
    public final void postInit(FMLPostInitializationEvent event) {
    	;
    }

    @Override
    public final void registerRenderInformation() {
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
    	//POPModelTier4Rocket model = new POPModelTier4Rocket();
    	//If it happens that the two renderers really do need separate model objects for some reason, we can set it up that way...
    	RenderingRegistry.registerEntityRenderingHandler(POPEntityRocketT4.class, new GCCoreRenderSpaceship(new POPModelTier4Rocket(), PlanetsOPlenty.TEXTURE_DOMAIN, "rocketT4"));
        MinecraftForgeClient.registerItemRenderer(POPItems.spaceshipT4.itemID, new POPItemRenderSpaceshipT4(new POPModelTier4Rocket()));
    }

    @Override
    public final void spawnParticle(String var1, double var2, double var4, double var6)
    {
        /*final Minecraft var14 = FMLClientHandler.instance().getClient();

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
        }*/
    }

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
    public final Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
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

	public static class TickHandlerClient implements ITickHandler { //TODO: This is just temp, I think, there's got to be a less laggy way to do this...
		@Override
		public void tickStart(EnumSet<TickType> type, Object... tickData) {
			final Minecraft minecraft = FMLClientHandler.instance().getClient();

			final WorldClient world = minecraft.theWorld;

			if (type.equals(EnumSet.of(TickType.CLIENT))) {
				if (world != null && world.provider instanceof POPWorld) {
					if (world.provider.getSkyRenderer() == null) {
						world.provider.setSkyRenderer(new POPSkyProvider((POPWorld)world.provider));
						if (PlanetsOPlenty.debug)
							POPPacketHandlerServer.tellAllPlayers("POPSkyRenderer registered for " + Minecraft.getMinecraft().thePlayer.username);
					}
					if (world.provider.getCloudRenderer() == null) {//Is a second if really needed?
						//world.provider.setCloudRenderer(new GCCoreCloudRenderer());
						//TODO
					}
				}
			}
		}

		@Override
		public void tickEnd(EnumSet<TickType> type, Object... tickData) {
			//
		}

		@Override
		public String getLabel() {
			return "POP Client";
		}

		@Override
		public EnumSet<TickType> ticks() {
			return EnumSet.of(TickType.CLIENT);
		}
	}
}
