package foodisgood_orukum.mods.pop.block;

import net.minecraft.block.BlockFlowing;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class SpecialLavaBlockFlowing extends BlockFlowing {
	protected SpecialLavaBlockFlowing(int par1) {
		super(par1, Material.lava);
		setHardness(0F);
		setLightValue(1.0F);
		setTextureName("lava_flow");
		setUnlocalizedName("lava2");
		disableStats();
	}
	
	public int getLightOpacity(World world, int x, int y, int z) {
		return 0;
	}
}
