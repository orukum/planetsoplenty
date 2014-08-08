package foodisgood_orukum.mods.pop.block;

import foodisgood_orukum.mods.pop.POPConfigManager;

public final class POPBlocks {
	public static MercuryBlockFlowing mercuryFlowing;
	public static SkyBoundingBlock skyBoundingBlock;
	public static SpecialLavaBlockStill specialLavaStill;
	public static SpecialLavaBlockFlowing specialLavaFlow;
	
	public static void initBlocks() {
		mercuryFlowing = new MercuryBlockFlowing(POPConfigManager.idMercuryFlowing);
		skyBoundingBlock = new SkyBoundingBlock(POPConfigManager.idSkyBoundingBlock);
		specialLavaStill = new SpecialLavaBlockStill(POPConfigManager.idSpecialLavaStill);
		specialLavaFlow = new SpecialLavaBlockFlowing(POPConfigManager.idSpecialLavaFlow);
	}
	
	public static void registerBlocks() {
		//TODO
	}
}
