package foodisgood_orukum.mods.pop.block.material;

import net.minecraft.block.material.*;

public class MercuryMaterial extends MaterialLiquid {
	public static MercuryMaterial mercury = new MercuryMaterial();
	
	private MercuryMaterial() {
		super(MapColor.ironColor);
		//canBurn = false;
		//setReplaceable();
		//setNoPushMobility();
		//isTranslucent = false;
		
	}

	@Override
    public boolean isSolid() {
        return true;
    }
    
    /*public boolean getCanBlockGrass() {
        return true;
    }*/
    
    @Override
    public boolean blocksMovement() {
        return true;
    }
    
    /*public boolean isOpaque()
    {
        return this.isTranslucent ? false : this.blocksMovement();
    }*/
}
