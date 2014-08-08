package foodisgood_orukum.mods.pop.block;

import net.minecraft.block.BlockStationary;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class SpecialLavaBlockStill extends BlockStationary {
	protected SpecialLavaBlockStill(int id) {
		super(id, Material.lava);
		setHardness(100F);
		setLightValue(1F);
		setUnlocalizedName("lava2");
		disableStats();
		setTextureName("lava_still");
	}
	
	public int getLightOpacity(World world, int x, int y, int z) {
		return 0;
	}
}

//public static final Block lavaStill = (new BlockStationary(11, Material.lava)).setHardness(100.0F).setLightValue(1.0F).setUnlocalizedName("lava").disableStats().setTextureName("lava_still");