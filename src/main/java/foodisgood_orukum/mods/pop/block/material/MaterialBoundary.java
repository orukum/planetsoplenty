package foodisgood_orukum.mods.pop.block.material;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialBoundary extends Material {
    public MaterialBoundary() {
        super(MapColor.airColor);
    }
    
    public final boolean getCanBlockGrass() {
        return false;
    }
    
    public final boolean blocksMovement() {
        return true;
	}
}
