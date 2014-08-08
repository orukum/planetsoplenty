package foodisgood_orukum.mods.pop.block;

import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SkyBoundingBlock extends Block {
	/* canBurn
	 * replaceable
	 * requiresNoTool
	 * materialMapColor
	 * mobilityFlag (2 for immovable)
	 * isAdventureModeExempt
	 * isLiquid()
	 * isSolid()
	 * getCanBlockGrass()
	 * blocksMovement
	 * isOpaque() (default: return this.isTranslucent ? false : this.blocksMovement();)
	 */

	/*public BlockSky() {
		super(Material.iron);
	}*/

	/*@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IconRegister registry) {
		blockIcon = registry.registerIcon("openblocks:sky_inactive");
	}*/
	public SkyBoundingBlock(int id) {
		super(id, Material.air);
		setBlockUnbreakable();
		setLightOpacity(0);
		setResistance(6000000.0F);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	/*@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		int meta = world.getBlockMetadata(x, y, z);
		int isPowered = world.isBlockIndirectlyGettingPowered(x, y, z)? 2 : 0;
		int isInverted = meta & 1;
		world.setBlockMetadataWithNotify(x, y, z, isPowered | isInverted, BlockNotifyFlags.ALL);
	}*/

	/*@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		return isActive(meta)? AxisAlignedBB.getBoundingBox(0, 0, 0, 0, 0, 0) : super.getSelectedBoundingBoxFromPool(world, x, y, z);
	}*/

	@Override
	public int getRenderType() {
		return 0;
		// TODO
	}

	/*@Override
	public boolean hasTileEntity(int metadata) {
		return teClass != null;
	}*/

	/*public final static boolean isNeighborBlockSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
		x += side.offsetX;
		y += side.offsetY;
		z += side.offsetZ;
		return world.isSideSolid(x, y, z, side.getOpposite(), false);
	}*/

	/*public final static boolean areNeighborBlocksSolid(World world, int x, int y, int z, ForgeDirection... sides) {
		for (ForgeDirection side : sides) {
			if (isNeighborBlockSolid(world, x, y, z, side)) { return true; }
		}
		return false;
	}*/

	/*@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileEntity te = world.getTileEntity(x, y, z);

		if (te instanceof IHasGui && ((IHasGui)te).canOpenGui(player) && !player.isSneaking()) {
			if (!world.isRemote) openGui(player, world, x, y, z);
			return true;
		}

		if (te instanceof IActivateAwareTile) return ((IActivateAwareTile)te).onBlockActivated(player, side, hitX, hitY, hitZ);
		return false;
	}*/

	/*@Override
	public boolean onBlockEventReceived(World world, int x, int y, int z, int eventId, int eventParam) {
		super.onBlockEventReceived(world, x, y, z, eventId, eventParam);
		TileEntity te = getTileEntity(world, x, y, z, TileEntity.class);
		if (te != null) { return te.receiveClientEvent(eventId, eventParam); }
		return false;
	}*/

	/*@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return super.getSelectedBoundingBoxFromPool(world, x, y, z);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		setBlockBoundsBasedOnState(world, x, y, z);
		return super.getCollisionBoundingBoxFromPool(world, x, y, z);
	}*/

	/*@SuppressWarnings("unchecked")
	public static <U> U getTileEntity(IBlockAccess world, int x, int y, int z, Class<U> T) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te != null && T.isAssignableFrom(te.getClass())) { return (U)te; }
		return null;
	}*/

	@Override
	public final boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
		//return canPlaceBlockOnSide(world, x, y, z, ForgeDirection.getOrientation(side).getOpposite());
		return false;
	}
}
