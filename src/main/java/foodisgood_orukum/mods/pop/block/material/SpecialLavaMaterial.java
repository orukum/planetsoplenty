package foodisgood_orukum.mods.pop.block.material;

import net.minecraft.block.material.*;

public class SpecialLavaMaterial extends MaterialLiquid {
	public static final SpecialLavaMaterial specialLava = new SpecialLavaMaterial();
	
	private SpecialLavaMaterial() {
		super(MapColor.tntColor);
	}
    
	@Override
    public boolean getCanBlockGrass() {
        return false;
    }
}
