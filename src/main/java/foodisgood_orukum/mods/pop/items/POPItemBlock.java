package foodisgood_orukum.mods.pop.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.*;
import foodisgood_orukum.mods.pop.client.POPClientProxy;

public class POPItemBlock extends ItemBlock {
	public POPItemBlock(int id) {
		super(id);
	}

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack stack)
    {
        return POPClientProxy.popItemRarity;
    }
}
