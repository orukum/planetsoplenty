package foodisgood_orukum.mods.pop;

import java.io.File;
import net.minecraft.block.Block;
import net.minecraftforge.common.Configuration;

public final class POPConfigManager {
	public static final String CATEGORY_ENTITIES = "ENTITIES",
			CATEGORY_DIMENSIONS = "DIMENSIONS";
	
    public static boolean loaded = false;
    static Configuration config;
    
    // DIMENSIONS
    public static int idDimensionRangeStart;
    
    // BLOCKS

    // ITEMS
    public static int idItemT4Rocket;

    // SCHEMATICS
    //public static int idSchematicRocketT1;

    // ARMOR
    //public static int idArmorOxygenMask;

    // TOOLS
    //public static int idToolSteelSword;

    // GUI
    //public static int idGuiTankRefill;

    // ACHIEVEMENTS
    //public static int idAchievBase;
    
    // ENTITIES
    public static int idEntityT4Rocket;

    // GENERAL
    //public static String[] sealableIDs;
    public static boolean forceOverworldRespawn;
    //public static boolean enableDebug;
    //public static boolean enableCopperOreGen;
    //public static boolean enableTinOreGen;
    //public static boolean enableAluminumOreGen;
    //public static boolean enableSiliconOreGen;

    public static void setDefaultValues(File file) {
        if (!POPConfigManager.loaded)
            POPConfigManager.config = new Configuration(file);
        try {
        	POPConfigManager.config.load();
        	// GENERAL
        	POPConfigManager.forceOverworldRespawn = POPConfigManager.config.get(Configuration.CATEGORY_GENERAL, "Force Overworld Spawn", false, "By default, if you die you'll respawn on the POP dimension you died on. If this is set to true, you will respawn in the overworld. Equivalent to Galacticraft config option of same name.").getBoolean(false);
        	
        	// DIMENSIONS
        	POPConfigManager.idDimensionRangeStart = POPConfigManager.config.get(CATEGORY_DIMENSIONS, "idDimensionRangeStart", 10000, "The beginning of range of dimension IDs to reserve for POP.").getInt();//TODO: Somehow make this handle changes in IDs? If feasible. Also look into dynamically handling dimension registering, like I assume Mystcraft does.
        	
        	// ENTITIES
        	POPConfigManager.idEntityT4Rocket = POPConfigManager.config.get(CATEGORY_ENTITIES, "idEntityT4Rocket", 20000).getInt(20000);
        	
        	// ITEMS
        	POPConfigManager.idItemT4Rocket = POPConfigManager.config.get(Configuration.CATEGORY_ITEM, "idItemT4Rocket", 16500).getInt(16500);
            /*
            GCCoreConfigManager.idDimensionOverworldOrbit = GCCoreConfigManager.configuration.get("DIMENSIONS", "idDimensionOverworldOrbit", -27).getInt(-27);

            GCCoreConfigManager.idBlockBreatheableAir = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockCoreBreatheableAir", 3350).getInt(3350);
            GCCoreConfigManager.idBlockLandingPad = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockLandingPad", 3351).getInt(3351);
            GCCoreConfigManager.idBlockLandingPadFull = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockLandingPadFull", 3352).getInt(3352);
            GCCoreConfigManager.idBlockUnlitTorch = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockUnlitTorch", 3353).getInt(3353);
            GCCoreConfigManager.idBlockUnlitTorchLit = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockUnlitTorchLit", 3354).getInt(3354);
            GCCoreConfigManager.idBlockAirDistributor = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockAirDistributor", 3355).getInt(3355);
            GCCoreConfigManager.idBlockAirCompressor = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockAirCompressor", 3356).getInt(3356);
            GCCoreConfigManager.idBlockAirCollector = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockAirCollector", 3357).getInt(3357);
            GCCoreConfigManager.idBlockRefinery = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockRefinery", 3358).getInt(3358);
            GCCoreConfigManager.idBlockFuelLoader = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockFuelLoader", 3359).getInt(3359);
            GCCoreConfigManager.idBlockOxygenSealer = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockOxygenSealer", 3360).getInt(3360);
            GCCoreConfigManager.idBlockOxygenDetector = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockOxygenDetector", 3361).getInt(3361);
            GCCoreConfigManager.idBlockCargoLoader = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockCargoLoader", 3362).getInt(3362);
            GCCoreConfigManager.idBlockAirPipe = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockAirPipe", 3368).getInt(3368);
            GCCoreConfigManager.idBlockSapling2 = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockSapling2", 3369).getInt(3369);
            GCCoreConfigManager.idBlockRocketBench = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockRocketBench", 3370).getInt(3370);
            GCCoreConfigManager.idBlockFallenMeteor = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockFallenMeteor", 3371).getInt(3371);
            GCCoreConfigManager.idBlockDecorationBlock = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockDecorationBlock", 3372).getInt(3372);
            GCCoreConfigManager.idBlockAirLockFrame = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockAirLockFrame", 3373).getInt(3373);
            GCCoreConfigManager.idBlockAirLockSeal = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockAirLockSeal", 3374).getInt(3374);
            GCCoreConfigManager.idBlockCrudeOilStill = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockCrudeOilStill", 3377).getInt(3377);
            GCCoreConfigManager.idBlockDummy = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockDummy", 3378).getInt(3378);
            GCCoreConfigManager.idBlockEnclosedWire = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockEnclosedWire", 3379).getInt(3379);
            GCCoreConfigManager.idBlockSpaceStationBase = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockSpaceStationBase", 3380, "Even though this will be generated, it can use block IDs greater than 256").getInt(3380);
            GCCoreConfigManager.idBlockTreasureChest = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockTieredTreasureChest", 3381, "Even though this will be generated, it can use block IDs greater than 256").getInt(3381);
            GCCoreConfigManager.idBlockParachest = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockParachest", 3382).getInt(3382);
            GCCoreConfigManager.idBlockSolarPanel = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockSolarPanel", 3383).getInt(3383);
            GCCoreConfigManager.idBlockCopperWire = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockCopperWire", 3972).getInt(3972);
            GCCoreConfigManager.idBlockBasicMachine = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockMachineB", 3363).getInt(3363);
            GCCoreConfigManager.idBlockBasicMachine2 = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockMachineB2", 3364).getInt(3364);
            GCCoreConfigManager.idBlockGlowstoneTorch = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_BLOCK, "idBlockGlowstoneTorch", 3384).getInt(3384);
            // 3390 - Mars Base Block

            GCCoreConfigManager.idItemSpaceship = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemSpaceship", 9855).getInt(9855);
            GCCoreConfigManager.idItemLightOxygenTank = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemLightOxygenTank", 9856).getInt(9856);
            GCCoreConfigManager.idItemMedOxygenTank = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemMedOxygenTank", 9857).getInt(9857);
            GCCoreConfigManager.idItemHeavyOxygenTank = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemHeavyOxygenTank", 9858).getInt(9858);
            GCCoreConfigManager.idArmorOxygenMask = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idArmorOxygenMask", 9859).getInt(9859);
            GCCoreConfigManager.idArmorSensorGlasses = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idArmorSensorGlasses", 9860).getInt(9860);
            GCCoreConfigManager.idItemTinCanister = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemCanister", 9861).getInt(9861);
            GCCoreConfigManager.idItemAirVent = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemAirVent", 9862).getInt(9862);
            GCCoreConfigManager.idItemOxygenConcentrator = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemOxygenConcentrator", 9863).getInt(9863);
            GCCoreConfigManager.idItemFan = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemFan", 9864).getInt(9864);
            GCCoreConfigManager.idItemGravityBow = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemGravityBow", 9865).getInt(9865);
            GCCoreConfigManager.idItemRocketEngine = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemRocketEngine", 9866).getInt(9866);
            GCCoreConfigManager.idItemHeavyPlate = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemHeavyPlate", 9867).getInt(9867);
            GCCoreConfigManager.idItemRocketNoseCone = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemRocketNoseCone", 9868).getInt(9868);
            GCCoreConfigManager.idItemRocketFins = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemRocketFins", 9869).getInt(9869);
            GCCoreConfigManager.idItemSensorLens = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemSensorLens", 9870).getInt(9870);
            GCCoreConfigManager.idItemBuggy = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemBuggyPlacer", 9871).getInt(9871);
            GCCoreConfigManager.idItemFlagPole = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemFlagPole", 9872).getInt(9872);
            GCCoreConfigManager.idItemFlag = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemFlagPlacer", 9873).getInt(9873);
            GCCoreConfigManager.idItemOxygenGear = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemOxygenGear", 9874).getInt(9874);
            GCCoreConfigManager.idItemCanvas = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemCanvas", 9875).getInt(9875);
            GCCoreConfigManager.idItemParachute = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemParachute", 9876).getInt(9876);
            GCCoreConfigManager.idItemOilExtractor = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemOilExtractor", 9877).getInt(9877);
            GCCoreConfigManager.idItemOilCanister = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemOilCanister", 9878).getInt(9878);
            GCCoreConfigManager.idItemRocketFuelBucket = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemRocketFuelCanister", 9879).getInt(9879);
            GCCoreConfigManager.idItemFuel = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "iditemFuel", 9880).getInt(9880);
            GCCoreConfigManager.idItemKnowledgeBook = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemKnowledgeBook", 9881).getInt(9881);
            GCCoreConfigManager.idItemSchematic = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemSchematic", 9882).getInt(9882);
            GCCoreConfigManager.idItemKey = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemKey", 9883).getInt(9883);
            GCCoreConfigManager.idItemBuggyMaterial = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemBuggyMaterial", 9884).getInt(9884);
            GCCoreConfigManager.idItemBasic = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemBasic", 9894).getInt(9894);
            GCCoreConfigManager.idItemBattery = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemBattery", 13970).getInt(13970);
            GCCoreConfigManager.idItemInfiniteBattery = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemInfiniteBattery", 13971).getInt(13971);
            GCCoreConfigManager.idItemMeteorChunk = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemMeteorChunk", 9895).getInt(9895);
            GCCoreConfigManager.idItemStandardWrench = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idItemStandardWrench", 9896).getInt(9896);

            GCCoreConfigManager.idToolSteelSword = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idToolSteelSword", 9885).getInt(9885);
            GCCoreConfigManager.idToolSteelPickaxe = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idToolSteelPickaxe", 9886).getInt(9886);
            GCCoreConfigManager.idToolSteelSpade = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idToolSteelSpade", 9887).getInt(9887);
            GCCoreConfigManager.idToolSteelHoe = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idToolSteelHoe", 9888).getInt(9888);
            GCCoreConfigManager.idToolSteelAxe = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idToolSteelAxe", 9889).getInt(9889);

            GCCoreConfigManager.idArmorSteelHelmet = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idArmorSteelHelmet", 9890).getInt(9890);
            GCCoreConfigManager.idArmorSteelChestplate = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idArmorSteelChestplate", 9891).getInt(9891);
            GCCoreConfigManager.idArmorSteelLeggings = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idArmorSteelLeggings", 9892).getInt(9892);
            GCCoreConfigManager.idArmorSteelBoots = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_ITEM, "idArmorSteelBoots", 9893).getInt(9893);

            GCCoreConfigManager.idGuiTankRefill = GCCoreConfigManager.configuration.get("GUI", "idGuiTankRefill", 128).getInt(128);
            GCCoreConfigManager.idGuiAirCompressor = GCCoreConfigManager.configuration.get("GUI", "idGuiAirCompressor", 129).getInt(129);
            GCCoreConfigManager.idGuiRocketCraftingBench = GCCoreConfigManager.configuration.get("GUI", "idGuiRocketCraftingBench", 130).getInt(130);
            GCCoreConfigManager.idGuiBuggyCraftingBench = GCCoreConfigManager.configuration.get("GUI", "idGuiBuggyCraftingBench", 131).getInt(131);
            GCCoreConfigManager.idGuiGalaxyMap = GCCoreConfigManager.configuration.get("GUI", "idGuiGalaxyMap", 132).getInt(132);
            GCCoreConfigManager.idGuiSpaceshipInventory = GCCoreConfigManager.configuration.get("GUI", "idGuiSpaceshipInventory", 133).getInt(133);
            GCCoreConfigManager.idGuiRefinery = GCCoreConfigManager.configuration.get("GUI", "idGuiRefinery", 134).getInt(134);
            GCCoreConfigManager.idGuiAirCollector = GCCoreConfigManager.configuration.get("GUI", "idGuiAirCollector", 135).getInt(135);
            GCCoreConfigManager.idGuiAirDistributor = GCCoreConfigManager.configuration.get("GUI", "idGuiAirDistributor", 136).getInt(136);
            GCCoreConfigManager.idGuiFuelLoader = GCCoreConfigManager.configuration.get("GUI", "idGuiFuelLoader", 137).getInt(137);
            GCCoreConfigManager.idGuiAddSchematic = GCCoreConfigManager.configuration.get("GUI", "idGuiAddSchematic", 138).getInt(138);
            GCCoreConfigManager.idGuiAirSealer = GCCoreConfigManager.configuration.get("GUI", "idGuiAirSealer", 139).getInt(139);
            GCCoreConfigManager.idGuiKnowledgeBook = GCCoreConfigManager.configuration.get("GUI", "idGuiKnowledgeBook", 140).getInt(140);
            GCCoreConfigManager.idGuiCargoLoader = GCCoreConfigManager.configuration.get("GUI", "idGuiCargoLoader", 141).getInt(141);
            GCCoreConfigManager.idGuiParachest = GCCoreConfigManager.configuration.get("GUI", "idGuiParachest", 142).getInt(142);
            GCCoreConfigManager.idGuiSolarPanel = GCCoreConfigManager.configuration.get("GUI", "idGuiSolarPanel", 144).getInt(144);
            GCCoreConfigManager.idGuiExtendedInventory = GCCoreConfigManager.configuration.get("GUI", "idGuiExtendedInventory", 145).getInt(145);
            GCCoreConfigManager.idGuiAirLockController = GCCoreConfigManager.configuration.get("GUI", "idGuiAirLockController", 148).getInt(148);

            GCCoreConfigManager.idSchematicRocketT1 = GCCoreConfigManager.configuration.get("Schematic", "idSchematicRocketT1", 0).getInt(0);
            GCCoreConfigManager.idSchematicMoonBuggy = GCCoreConfigManager.configuration.get("Schematic", "idSchematicMoonBuggy", 1).getInt(1);
            GCCoreConfigManager.idSchematicAddSchematic = GCCoreConfigManager.configuration.get("Schematic", "idSchematicAddSchematic", Integer.MAX_VALUE).getInt(Integer.MAX_VALUE);

            GCCoreConfigManager.idAchievBase = GCCoreConfigManager.configuration.get("Achievements", "idAchievBase", 1784).getInt(1784);

            GCCoreConfigManager.idEntityEvolvedSpider = GCCoreConfigManager.configuration.get("Entities", "idEntityEvolvedSpider", 155).getInt(155);
            GCCoreConfigManager.idEntityEvolvedCreeper = GCCoreConfigManager.configuration.get("Entities", "idEntityEvolvedCreeper", 156).getInt(156);
            GCCoreConfigManager.idEntityEvolvedZombie = GCCoreConfigManager.configuration.get("Entities", "idEntityEvolvedZombie", 157).getInt(157);
            GCCoreConfigManager.idEntityEvolvedSkeleton = GCCoreConfigManager.configuration.get("Entities", "idEntityEvolvedSkeleton", 158).getInt(158);
            GCCoreConfigManager.idEntitySpaceship = GCCoreConfigManager.configuration.get("Entities", "idEntitySpaceship", 159).getInt(159);
            GCCoreConfigManager.idEntityAntiGravityArrow = GCCoreConfigManager.configuration.get("Entities", "idEntityAntiGravityArrow", 160).getInt(160);
            GCCoreConfigManager.idEntityMeteor = GCCoreConfigManager.configuration.get("Entities", "idEntityMeteor", 161).getInt(161);
            GCCoreConfigManager.idEntityBuggy = GCCoreConfigManager.configuration.get("Entities", "idEntityBuggy", 162).getInt(162);
            GCCoreConfigManager.idEntityFlag = GCCoreConfigManager.configuration.get("Entities", "idEntityFlag", 163).getInt(163);
            GCCoreConfigManager.idEntityAstroOrb = GCCoreConfigManager.configuration.get("Entities", "idEntityAstroOrb", 164).getInt(164);
            GCCoreConfigManager.idEntityParaChest = GCCoreConfigManager.configuration.get("Entities", "idEntityParaChest", 165).getInt(165);
            GCCoreConfigManager.idEntityAlienVillager = GCCoreConfigManager.configuration.get("Entities", "idEntityAlienVillager", 166).getInt(166);
            GCCoreConfigManager.idEntityOxygenBubble = GCCoreConfigManager.configuration.get("Entities", "idEntityOxygenBubble", 167).getInt(167);
            GCCoreConfigManager.idEntityLander = GCCoreConfigManager.configuration.get("Entities", "idEntityLander", 168).getInt(168);
            GCCoreConfigManager.idEntityLanderChest = GCCoreConfigManager.configuration.get("Entities", "idEntityLanderChest", 169).getInt(169);
            GCCoreConfigManager.idEntityEvolvedSkeletonBoss = GCCoreConfigManager.configuration.get("Entities", "idEntityEvolvedSkeletonBoss", 170).getInt(170);
            GCCoreConfigManager.idEntityMeteorChunk = GCCoreConfigManager.configuration.get("Entities", "idEntityMeteorChunk", 179).getInt(179);

            GCCoreConfigManager.moreStars = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_GENERAL, "More Stars", true, "Setting this to false will revert night skies back to default minecraft star count").getBoolean(true);
            GCCoreConfigManager.wasdMapMovement = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_GENERAL, "WASD Map Movement", true, "If you prefer to move the Galaxy map with your mouse, set to false").getBoolean(true);
            GCCoreConfigManager.oilGenFactor = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_GENERAL, "Oil Generation Factor", 1.8, "Increasing this will increase amount of oil that will generate in each chunk.").getDouble(1.8);
            GCCoreConfigManager.disableSpaceshipParticles = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_GENERAL, "Disable Spaceship Particles", false, "If you have FPS problems, setting this to true will help if spaceship particles are in your sights").getBoolean(false);
            GCCoreConfigManager.disableSpaceshipGrief = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_GENERAL, "Disable Spaceship Explosion", false, "Spaceships will not explode on contact if set to true").getBoolean(false);
            GCCoreConfigManager.oxygenIndicatorLeftSide = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_GENERAL, "Using Minimap Mod", false, "If true, this will move the Oxygen Indicator to the left side.").getBoolean(false);
            GCCoreConfigManager.disableLeafDecay = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_GENERAL, "Disable Oxygen Collector Leaf Decay", false, "If set to true, Oxygen Collectors will not consume leaf blocks.").getBoolean(false);
            GCCoreConfigManager.spaceStationsRequirePermission = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_GENERAL, "Space Stations Require Permission", true, "While true, space stations require you to invite other players using /ssinvite <playername>").getBoolean(true);
            GCCoreConfigManager.overrideCapes = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_GENERAL, "Override Capes", true, "By default, Galacticraft will override capes with the mod's donor cape. Set to false to disable.").getBoolean(true);
            GCCoreConfigManager.spaceStationEnergyScalar = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_GENERAL, "Space Station Solar Energy Multiplier", 2.0, "If Mekanism is installed, solar panels will work (default 2x) more effective on space stations.").getDouble(2.0);
            GCCoreConfigManager.sealableIDs = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_GENERAL, "External Sealable IDs", new String[] { String.valueOf(Block.glass.blockID + ":0"), String.valueOf(Block.thinGlass.blockID + ":0") }, "List IDs from other mods that the Oxygen Sealer should recognize as solid blocks. Format is ID:METADATA").getStringList();
            GCCoreConfigManager.detectableIDs = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_GENERAL, "External Detectable IDs", new String[] { String.valueOf(Block.oreCoal.blockID + ":0"), String.valueOf(Block.oreDiamond.blockID + ":0"), String.valueOf(Block.oreGold.blockID + ":0"), String.valueOf(Block.oreIron.blockID + ":0"), String.valueOf(Block.oreLapis.blockID + ":0"), String.valueOf(Block.oreRedstone.blockID + ":0"), String.valueOf(Block.oreRedstoneGlowing.blockID + ":0") }, "List IDs from other mods that the Sensor Glasses should recognize as solid blocks. Format is ID:METADATA").getStringList();
            GCCoreConfigManager.dungeonBossHealthMod = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_GENERAL, "Dungeon Boss Health Modifier", 1.0D, "Change this is you wish to balance the mod (if you have more powerful weapon mods)").getDouble(1.0D);
            GCCoreConfigManager.suffocationCooldown = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_GENERAL, "Suffocation Cooldown", 100, "Lower/Raise this value to change time between suffocation damage ticks").getInt(100);
            GCCoreConfigManager.suffocationDamage = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_GENERAL, "Suffocation Damage", 2, "Change this value to modify the damage taken per suffocation tick").getInt(2);
            GCCoreConfigManager.externalOilGen = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_GENERAL, "Oil gen in external dimensions", new int[] { 0 }, "List of non-galacticraft dimension IDs to generate oil in").getIntList();
            GCCoreConfigManager.forceOverworldRespawn = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_GENERAL, "Force Overworld Spawn", false, "By default, you will respawn on galacticraft dimensions if you die. If you set this to true, you will respawn back on earth.").getBoolean(false);
            GCCoreConfigManager.enableDebug = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_GENERAL, "Enable Debug Messages", false, "If this is enabled, debug messages will appear in the console. This is useful for finding bugs in the mod.").getBoolean(false);
            GCCoreConfigManager.enableCopperOreGen = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_GENERAL, "Enable Copper Ore Gen", true, "If this is enabled, copper ore will generate on the overworld.").getBoolean(true);
            GCCoreConfigManager.enableTinOreGen = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_GENERAL, "Enable Tin Ore Gen", true, "If this is enabled, tin ore will generate on the overworld.").getBoolean(true);
            GCCoreConfigManager.enableAluminumOreGen = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_GENERAL, "Enable Aluminum Ore Gen", true, "If this is enabled, aluminum ore will generate on the overworld.").getBoolean(true);
            GCCoreConfigManager.enableSiliconOreGen = GCCoreConfigManager.configuration.get(Configuration.CATEGORY_GENERAL, "Enable Silicon Ore Gen", true, "If this is enabled, silicon ore will generate on the overworld.").getBoolean(true);*/
        }
        catch (final Exception e) {
            POPLog.severe("Problem loading Planets O' Plenty config (" + file.getName() + "): " + e.getMessage()); 
        } finally {
            if (POPConfigManager.config.hasChanged())
                POPConfigManager.config.save();
            POPConfigManager.loaded = true;
        }
    }
}
