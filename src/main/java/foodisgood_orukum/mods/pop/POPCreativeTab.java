package foodisgood_orukum.mods.pop;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class POPCreativeTab extends CreativeTabs {
    private final int itemForTab;
    private final int metaForTab;

    public POPCreativeTab(int par1, String par2Str, int itemForTab, int metaForTab)
    {
        super(par1, par2Str);
        this.itemForTab = itemForTab;
        this.metaForTab = metaForTab;
    }

    @Override
    public ItemStack getIconItemStack()
    {
        return new ItemStack(this.itemForTab, 1, this.metaForTab);
    }
}
