package foodisgood_orukum.mods.pop;

import java.io.File;

import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.recipe.CompressorRecipes;
import micdoodle8.mods.galacticraft.api.recipe.SchematicRegistry;
import micdoodle8.mods.galacticraft.core.GCLog;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.items.GCCoreItems;
import micdoodle8.mods.galacticraft.core.network.GCCoreConnectionHandler;
import micdoodle8.mods.galacticraft.core.network.GCCorePacketManager;
import micdoodle8.mods.galacticraft.core.util.GCCoreUtil;
import micdoodle8.mods.galacticraft.mars.blocks.GCMarsBlocks;
import micdoodle8.mods.galacticraft.mars.dimension.GCMarsTeleportType;
import micdoodle8.mods.galacticraft.mars.dimension.GCMarsWorldProvider;
import micdoodle8.mods.galacticraft.mars.entities.GCMarsEntityCargoRocket;
import micdoodle8.mods.galacticraft.mars.entities.GCMarsEntityCreeperBoss;
import micdoodle8.mods.galacticraft.mars.entities.GCMarsEntityLandingBalloons;
import micdoodle8.mods.galacticraft.mars.entities.GCMarsEntityProjectileTNT;
import micdoodle8.mods.galacticraft.mars.entities.GCMarsEntityRocketT2;
import micdoodle8.mods.galacticraft.mars.entities.GCMarsEntitySlimeling;
import micdoodle8.mods.galacticraft.mars.entities.GCMarsEntitySludgeling;
import micdoodle8.mods.galacticraft.mars.entities.GCMarsEntityTerraformBubble;
import micdoodle8.mods.galacticraft.mars.items.GCMarsItems;
import micdoodle8.mods.galacticraft.mars.network.GCMarsPacketHandlerServer;
import micdoodle8.mods.galacticraft.mars.recipe.GCMarsRecipeManager;
import micdoodle8.mods.galacticraft.mars.schematic.GCMarsSchematicCargoRocket;
import micdoodle8.mods.galacticraft.mars.schematic.GCMarsSchematicRocketT2;
import micdoodle8.mods.galacticraft.mars.tile.GCMarsTileEntityCryogenicChamber;
import micdoodle8.mods.galacticraft.mars.tile.GCMarsTileEntityDungeonSpawner;
import micdoodle8.mods.galacticraft.mars.tile.GCMarsTileEntityLaunchController;
import micdoodle8.mods.galacticraft.mars.tile.GCMarsTileEntitySlimelingEgg;
import micdoodle8.mods.galacticraft.mars.tile.GCMarsTileEntityTerraformer;
import micdoodle8.mods.galacticraft.mars.tile.GCMarsTileEntityTreasureChest;
import micdoodle8.mods.galacticraft.mars.GalacticraftMars;
import micdoodle8.mods.galacticraft.moon.items.GCMoonItems;
import net.minecraft.block.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import foodisgood_orukum.mods.pop.items.*;
import foodisgood_orukum.mods.pop.entities.*;

/**
 * Copyright 2014, foodisgoodyesiam and orukum
 * 
 * All rights reserved.
 * 
 */
@Mod(name = PlanetsOPlenty.NAME, version = PlanetsOPlenty.VERSION/* + "." + PlanetsOPlenty.LOCALMINVERSION + "." + PlanetsOPlenty.LOCALBUILDVERSION*/, useMetadata = true, modid = PlanetsOPlenty.MODID, dependencies = "required-after:" + GalacticraftCore.MODID + ";required-after:" + GalacticraftMars.MODID + ";")//";after:ICBM|Explosion; after:IC2; after:BuildCraft|Core; after:BuildCraft|Energy;")
//@NetworkMod(channels = { GalacticraftMars.CHANNEL }, clientSideRequired = true, serverSideRequired = false, connectionHandler = GCCoreConnectionHandler.class, packetHandler = GCCorePacketManager.class)
public class PlanetsOPlenty {
    public static final String NAME = "Planets O' Plenty";
    public static final String MODID = "PlanetsOPlenty";
    public static final String CHANNEL = "PlanetsOPlenty";
    public static final String CHANNELENTITIES = "PlanetsOPlentyEntities";
    public static final String VERSION = "-1.0000000000000000000000000023";
    public static final boolean debug = true;

    public static final String LANGUAGE_PATH = "/assets/planetsoplenty/lang/";//Hmm, I wonder

    @SidedProxy(clientSide = "foodisgood_orukum.mods.pop.client.POPClientProxy", serverSide = "foodisgood_orukum.mods.pop.CommonPOPProxy")
    public static CommonPOPProxy proxy;

    @Instance(PlanetsOPlenty.MODID)
    public static PlanetsOPlenty instance;

    public static POPCreativeTab planetsOPlentyTab;

    public static final String TEXTURE_DOMAIN = "planetsoplenty";
    public static final String TEXTURE_PREFIX = GalacticraftMars.TEXTURE_DOMAIN + ":";

    public static Fluid SLUDGE;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
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
    }

    @EventHandler
    public void load(FMLInitializationEvent event)
    {
    	PlanetsOPlenty.planetsOPlentyTab = new POPCreativeTab(CreativeTabs.getNextID(), PlanetsOPlenty.MODID, POPItems.spaceshipT4.itemID, 5);
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
    	registerGalacticraftNonMobEntity(POPEntityRocketT4.class, "SpaceshipT4", 20000001, 150, 1, false);
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
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
    public void postLoad(FMLPostInitializationEvent event)
    {
        PlanetsOPlenty.proxy.postInit(event);
        PlanetsOPlenty.proxy.registerRenderInformation();
        //GCMarsRecipeManager.loadRecipes();
    }
    
    /**
     * Here we should just use the methods as already provided in GC or GCMars, not make our own, I think. Hmm, maybe not, though
     */

    public void registerGalacticraftCreature(Class<? extends Entity> var0, String var1, int id, int back, int fore)
    {
        EntityRegistry.registerGlobalEntityID(var0, var1, id, back, fore);
        EntityRegistry.registerModEntity(var0, var1, id, PlanetsOPlenty.instance, 80, 3, true);
    }

    public void registerGalacticraftNonMobEntity(Class<? extends Entity> var0, String var1, int id, int trackingDistance, int updateFreq, boolean sendVel)
    {
        EntityList.addMapping(var0, var1, id);
        EntityRegistry.registerModEntity(var0, var1, id, /*this*//**Just to see if it works...*/GalacticraftMars.instance, trackingDistance, updateFreq, sendVel);
    }
}
