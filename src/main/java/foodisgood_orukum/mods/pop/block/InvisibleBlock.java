package foodisgood_orukum.mods.pop.block;

import foodisgood_orukum.mods.pop.PlanetsOPlenty;
import foodisgood_orukum.mods.pop.block.material.MaterialBoundary;
import net.minecraft.block.*;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class InvisibleBlock extends Block {
    protected InvisibleBlock(int id) {
		super(id, new MaterialBoundary());
		setBlockUnbreakable();
		setUnlocalizedName("Invisible Boundary");
		setLightOpacity(0);
		setResistance(9000000F);
	}
    
    /*@Override
    public void onBlockAdded(World world, int x, int y, int z) {
    	
    }*/
    
    /*@Override
    public void velocityToAddToEntity(World par1World, int x, int y, int z, Entity entity, Vec3 vec) {
    	
    }*/
    
    @Override
    public boolean getBlocksMovement(IBlockAccess access, int x, int y, int z) {
    	return true;
    }
    
    @Override
    public boolean isAirBlock(World world, int x, int y, int z) {
    	return true;
    }

    @Override
    public boolean canCreatureSpawn(EnumCreatureType type, World world, int x, int y, int z) {
    	return false;
    }
    
    @Override
    public boolean canEntityDestroy(World world, int x, int y, int z, Entity entity) {
    	return false;
    }
    
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    @Override
    public boolean canSilkHarvest() {
    	return false;
    }
    
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    /*@Override
    public ItemStack createStackedBlock(int par1) {
    	
    }*/
    
    /*@Override
    public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 par5, Vec3 par6) {
    	
    }*/
    
    /*@Override
    public void fillWithRain(World par1World, int x, int y, int z) {
    	
    }*/
    
    /*@Override
    public float getAmbientOcclusionLightValue(IBlockAccess access, int par2, int par3, int par4) {
    	return 0F;
    }*/
    
    /*@Override
    public float getBlockBrightness(IBlockAccess access, int x, int y, int z) {
    
    }*/
    
    @Override
    public CreativeTabs getCreativeTabToDisplayOn() {
    	return PlanetsOPlenty.planetsOPlentyTab;
    }
    
    /*@Override
    public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
    	
    }*/
    
    /*@Override
    public float getExplosionResistance(Entity par1Entity) {
    	return 0F;
    }*/
    
    /*@Override
    public Icon getIcon(int par1, int par2) {
    	
    }*/
    
    /*@Override
    public int getRenderBlockPass() {
    	
    }*/
    
    /*@Override
    public String getTextureName() {
    	
    }*/
    
    /*@Override
    public boolean isBlockFoliage(World world, int x, int y, int z) {
    	return true;
    }*/
    
    /*@Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
    	
    }*/
    
    /*@Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity par5Entity) {
    	
    }*/
    
    /*@Override
    public void onFallenUpon(World world, int x, int y, int z, Entity entity, float par6) {
    	
    }*/
    
    /*@Override
    public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
    	
    }*/
}
