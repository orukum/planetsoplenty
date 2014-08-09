package foodisgood_orukum.mods.pop;

import java.io.File;

import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.recipe.*;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.mars.GalacticraftMars;
import net.minecraft.block.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.*;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.*;
import cpw.mods.fml.common.registry.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import foodisgood_orukum.mods.pop.items.*;
import foodisgood_orukum.mods.pop.space.*;
import foodisgood_orukum.mods.pop.block.POPBlocks;
import foodisgood_orukum.mods.pop.entities.*;
import foodisgood_orukum.mods.pop.network.*;

/**
 * Copyright 2014, foodisgoodyesiam and orukum
 * 
 * All rights reserved.
 * 
 */
@Mod(name = PlanetsOPlenty.NAME, version = PlanetsOPlenty.VERSION/* + "." + PlanetsOPlenty.LOCALMINVERSION + "." + PlanetsOPlenty.LOCALBUILDVERSION*/, useMetadata = true, modid = PlanetsOPlenty.MODID, dependencies = "required-after:" + GalacticraftCore.MODID + ";required-after:" + GalacticraftMars.MODID + ";after:ICBM|Explosion;after:IC2;after:BuildCraft|Core;after:BuildCraft|Energy;after:BiomesOPlenty;")
@NetworkMod(channels = { PlanetsOPlenty.CHANNELENTITIES, PlanetsOPlenty.CHANNEL }, clientSideRequired = true, serverSideRequired = true, connectionHandler = POPConnectionHandler.class, packetHandler = POPEntityPacketManager.class)
public final class PlanetsOPlenty {
    public static final String NAME = "Planets O' Plenty";
    public static final String MODID = "PlanetsOPlenty";
    public static final String CHANNEL = "PlanetsOPlenty";
    public static final String CHANNELENTITIES = "POPEntities";
    public static final String VERSION = "-1.0000000000000000000000000068";
    public static final boolean debug = true;

    public static final String LANGUAGE_PATH = "/assets/planetsoplenty/lang/";//Hmm, I wonder

    @SidedProxy(clientSide = "foodisgood_orukum.mods.pop.client.POPClientProxy", serverSide = "foodisgood_orukum.mods.pop.CommonPOPProxy")
    public static CommonPOPProxy proxy;

    @Instance(PlanetsOPlenty.MODID)
    public static PlanetsOPlenty instance;

    public static POPCreativeTab planetsOPlentyTab;

    public static final String TEXTURE_DOMAIN = "planetsoplenty";
    public static final String TEXTURE_PREFIX = PlanetsOPlenty.TEXTURE_DOMAIN + ":";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	if (debug)
    		POPLog.info("POP: preInit()");
        POPConfigManager.setDefaultValues(new File(event.getModConfigurationDirectory(), "POP.cfg"));
        POPBlocks.initBlocks();
        POPBlocks.registerBlocks();
        /*MinecraftForge.EVENT_BUS.register(new GCMarsEvents());
        new GCMarsConfigManager(new File(event.getModConfigurationDirectory(), "Galacticraft/mars.conf"));

        GalacticraftMars.SLUDGE = new Fluid("bacterialsludge").setBlockID(GCMarsConfigManager.idBlockBacterialSludge).setViscosity(3000);
        if (!FluidRegistry.registerFluid(GalacticraftMars.SLUDGE))
        {
            GCLog.info("\"bacterialsludge\" has already been registered as a fluid, ignoring...");
        }

        GCMarsBlocks.initBlocks();
        GCMarsBlocks.registerBlocks();
        GCMarsBlocks.setHarvestLevels();

        GCMarsItems.initItems();

        GalacticraftMars.proxy.preInit(event);*/
    	POPItems.initItems();
    	PlanetsOPlenty.proxy.preInit(event);
    	POPCelestials.preInit(event);
    	/*IGalaxy testing = POPCelestialObjects.westGalaxy;
    	System.out.println("Hello" + testing.getRGBRingColors().x);*/
    }

    @EventHandler
    public void load(FMLInitializationEvent event) {
    	if (debug)
    		POPLog.info("POP: load()");
    	PlanetsOPlenty.planetsOPlentyTab = new POPCreativeTab(CreativeTabs.getNextID(), PlanetsOPlenty.MODID, POPItems.spaceshipT4.itemID, 5);
        PlanetsOPlenty.proxy.init(event);
        NetworkRegistry.instance().registerChannel(new POPEntityPacketManager(), PlanetsOPlenty.CHANNELENTITIES, Side.CLIENT);
        /*SchematicRegistry.registerSchematicRecipe(new GCMarsSchematicRocketT2());
        SchematicRegistry.registerSchematicRecipe(new GCMarsSchematicCargoRocket());

        GalacticraftMars.galacticraftMarsTab = new GCCoreCreativeTab(CreativeTabs.getNextID(), GalacticraftMars.MODID, GCMarsItems.spaceship.itemID, 5);
        NetworkRegistry.instance().registerGuiHandler(GalacticraftMars.instance, GalacticraftMars.proxy);
        this.registerTileEntities();
        this.registerCreatures();
        this.registerOtherEntities();
        GalacticraftMars.proxy.init(event);

        GalacticraftRegistry.registerTeleportType(GCMarsWorldProvider.class, new GCMarsTeleportType());
        GalacticraftRegistry.registerCelestialBody(new GCMarsPlanet());
        GalacticraftRegistry.registerRocketGui(GCMarsWorldProvider.class, new ResourceLocation(GalacticraftMars.TEXTURE_DOMAIN, "textures/gui/marsRocketGui.png"));
        GalacticraftRegistry.addDungeonLoot(2, new ItemStack(GCMarsItems.schematic, 1, 0));
        GalacticraftRegistry.addDungeonLoot(2, new ItemStack(GCMarsItems.schematic, 1, 1));

        CompressorRecipes.addShapelessRecipe(new ItemStack(GCMarsItems.marsItemBasic, 1, 3), new ItemStack(GCCoreItems.heavyPlatingTier1), new ItemStack(GCMoonItems.meteoricIronIngot, 1, 1));
        CompressorRecipes.addShapelessRecipe(new ItemStack(GCMarsItems.marsItemBasic, 1, 5), new ItemStack(GCMarsItems.marsItemBasic, 1, 2));*/
    	if (PlanetsOPlenty.debug) {
	    	CompressorRecipes.addShapelessRecipe(new ItemStack(Block.stone, 2), new ItemStack(Block.bedrock, 3));
	    	CompressorRecipes.addShapelessRecipe(new ItemStack(Block.grass, 2), new ItemStack(POPItems.spaceshipT4));
	    	CompressorRecipes.addShapelessRecipe(new ItemStack(POPItems.spaceshipT4), new ItemStack(Block.grass, 2));
	    	CompressorRecipes.addShapelessRecipe(new ItemStack(Block.bedrock, 3), new ItemStack(Block.stone, 2));
	    	GameRegistry.addShapelessRecipe(new ItemStack(Item.diamond, 64), new ItemStack(Block.dirt));//Temp
    	}
    	registerPOPNonMobEntity(POPEntityRocketT4.class, "SpaceshipT4", POPConfigManager.idEntityT4Rocket, 150, 1, false);
    	POPCelestials.init(event);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	if (debug)
    		POPLog.info("POP: postInit()");
    	NetworkRegistry.instance().registerGuiHandler(this, PlanetsOPlenty.proxy);
        NetworkRegistry.instance().registerChannel(new POPPacketHandlerServer(), PlanetsOPlenty.CHANNEL, Side.SERVER);
    }
    
    @EventHandler
    //@SideOnly(value=Side.SERVER)
    public void serverInit(FMLServerStartedEvent event) {
    	if (debug)
    		POPLog.info("POP: serverInit()");
    	//NetworkRegistry.instance().registerChannel(new POPPacketHandlerServer(), PlanetsOPlenty.CHANNEL, Side.SERVER);
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
    	if (debug)
    		POPLog.info("POP: serverStarting()");
        //NetworkRegistry.instance().registerChannel(new GCMarsPacketHandlerServer(), GalacticraftMars.CHANNEL, Side.SERVER);
    }

    /*public void registerTileEntities()
    {
        /*GameRegistry.registerTileEntity(GCMarsTileEntitySlimelingEgg.class, "Slimeling Egg");
        GameRegistry.registerTileEntity(GCMarsTileEntityTreasureChest.class, "Tier 2 Treasure Chest");
        GameRegistry.registerTileEntity(GCMarsTileEntityTerraformer.class, "Planet Terraformer");
        GameRegistry.registerTileEntity(GCMarsTileEntityCryogenicChamber.class, "Cryogenic Chamber");
        GameRegistry.registerTileEntity(GCMarsTileEntityDungeonSpawner.class, "Mars Dungeon Spawner");
        GameRegistry.registerTileEntity(GCMarsTileEntityLaunchController.class, "Launch Controller");* /
    }

    public void registerCreatures()
    {
        /*this.registerGalacticraftCreature(GCMarsEntitySludgeling.class, "Sludgeling", GCMarsConfigManager.idEntitySludgeling, GCCoreUtil.convertTo32BitColor(255, 0, 0, 50), GCCoreUtil.convertTo32BitColor(255, 0, 0, 150));
        this.registerGalacticraftCreature(GCMarsEntitySlimeling.class, "Slimeling", GCMarsConfigManager.idEntitySlimeling, GCCoreUtil.convertTo32BitColor(255, 0, 0, 50), GCCoreUtil.convertTo32BitColor(255, 0, 0, 150));
        this.registerGalacticraftCreature(GCMarsEntityCreeperBoss.class, "CreeperBoss", GCMarsConfigManager.idEntityCreeperBoss, GCCoreUtil.convertTo32BitColor(255, 0, 0, 50), GCCoreUtil.convertTo32BitColor(255, 0, 0, 150));* /
    }

    public void registerOtherEntities()
    {
        /*this.registerGalacticraftNonMobEntity(GCMarsEntityRocketT2.class, "SpaceshipT2", GCMarsConfigManager.idEntitySpaceshipTier2, 150, 1, false);
        this.registerGalacticraftNonMobEntity(GCMarsEntityTerraformBubble.class, "TerraformBubble", GCMarsConfigManager.idEntityTerraformBubble, 150, 20, false);
        this.registerGalacticraftNonMobEntity(GCMarsEntityProjectileTNT.class, "ProjectileTNT", GCMarsConfigManager.idEntityProjectileTNT, 150, 1, true);
        this.registerGalacticraftNonMobEntity(GCMarsEntityLandingBalloons.class, "LandingBalloons", GCMarsConfigManager.idEntityLandingBalloons, 150, 5, true);
        this.registerGalacticraftNonMobEntity(GCMarsEntityCargoRocket.class, "CargoRocket", GCMarsConfigManager.idEntityCargoRocket, 150, 1, false);* /
    }*/

    @EventHandler
    public void postLoad(FMLPostInitializationEvent event) {
        PlanetsOPlenty.proxy.postInit(event);
        PlanetsOPlenty.proxy.registerRenderInformation();
        //GCMarsRecipeManager.loadRecipes();
    }

    public void registerPOPCreature(Class<? extends Entity> var0, String var1, int id, int back, int fore) {
        //if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
         //   LanguageRegistry.instance().addStringLocalization("entity." + var1 + ".name", StatCollector.translateToLocal("entity.GalacticraftCore." + var1 + ".name"));
        EntityRegistry.registerGlobalEntityID(var0, var1, id, back, fore);
        EntityRegistry.registerModEntity(var0, var1, id, PlanetsOPlenty.instance, 80, 3, true);
    }

    public void registerPOPNonMobEntity(Class<? extends Entity> var0, String var1, int id, int trackingDistance, int updateFreq, boolean sendVel) {
        /*if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
            LanguageRegistry.instance().addStringLocalization("entity." + var1 + ".name", StatCollector.translateToLocal("entity.GalacticraftCore." + var1 + ".name"));*/
        EntityList.addMapping(var0, var1, id);
        EntityRegistry.registerModEntity(var0, var1, id, PlanetsOPlenty.instance, trackingDistance, updateFreq, sendVel);
    }
}
