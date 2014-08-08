package foodisgood_orukum.mods.pop.block;

import cpw.mods.fml.common.registry.GameRegistry;
import foodisgood_orukum.mods.pop.POPConfigManager;
import foodisgood_orukum.mods.pop.PlanetsOPlenty;
import foodisgood_orukum.mods.pop.items.POPItemBlock;

public final class POPBlocks {
	public static MercuryBlockFlowing mercuryFlowing;
	public static SkyBoundingBlock skyBoundingBlock;
	public static SpecialLavaBlockStill specialLavaStill;
	public static SpecialLavaBlockFlowing specialLavaFlow;
	public static InvisibleBlock invisible;
	public static SpecialStone specialStone;
	
	public static void initBlocks() {
		mercuryFlowing = new MercuryBlockFlowing(POPConfigManager.idMercuryFlowing);
		skyBoundingBlock = new SkyBoundingBlock(POPConfigManager.idSkyBoundingBlock);
		specialLavaStill = new SpecialLavaBlockStill(POPConfigManager.idSpecialLavaStill);
		specialLavaFlow = new SpecialLavaBlockFlowing(POPConfigManager.idSpecialLavaFlow);
		invisible = new InvisibleBlock(POPConfigManager.idInvisibleBlock);
		specialStone = new SpecialStone(POPConfigManager.idSpecialStone);
	}
	
	public static void registerBlocks() {
		GameRegistry.registerBlock(mercuryFlowing, POPItemBlock.class, mercuryFlowing.getUnlocalizedName(), PlanetsOPlenty.MODID);
		GameRegistry.registerBlock(skyBoundingBlock, POPItemBlock.class, skyBoundingBlock.getUnlocalizedName(), PlanetsOPlenty.MODID);
		GameRegistry.registerBlock(specialLavaStill, POPItemBlock.class, specialLavaStill.getUnlocalizedName(), PlanetsOPlenty.MODID);
		GameRegistry.registerBlock(specialLavaFlow, POPItemBlock.class, specialLavaFlow.getUnlocalizedName(), PlanetsOPlenty.MODID);
		GameRegistry.registerBlock(invisible, POPItemBlock.class, invisible.getUnlocalizedName(), PlanetsOPlenty.MODID);
		GameRegistry.registerBlock(specialStone, POPItemBlock.class, specialStone.getUnlocalizedName(), PlanetsOPlenty.MODID);
	}
}
