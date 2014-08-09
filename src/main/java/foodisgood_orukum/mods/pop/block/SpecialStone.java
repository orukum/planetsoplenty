package foodisgood_orukum.mods.pop.block;

import java.util.Random;

import net.minecraft.block.*;
import net.minecraft.world.World;

public class SpecialStone extends BlockStone {
	public SpecialStone(int id) {
		super(id);
		setUnlocalizedName("stone2");
		setHardness(1.5F);
		setResistance(10.0F);
		setStepSound(soundStoneFootstep);
		setTextureName("stone");
	}
	
	@Override
	public int getLightOpacity(World world, int x, int y, int z) {
		return 0;
	}
	
	@Override
	public int idDropped(int par1, Random par2, int par3) {
		return Block.stone.idDropped(par1, par2, par3);
	}
	
	@Override
	public int idPicked(World world, int x, int y, int z) {
		return Block.stone.idPicked(world, x, y, z);
	}
}
