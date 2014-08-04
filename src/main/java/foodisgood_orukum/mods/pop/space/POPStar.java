package foodisgood_orukum.mods.pop.space;

import micdoodle8.mods.galacticraft.api.world.*;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.WorldProvider;

public abstract class POPStar implements IPlanet, IMapObject, ICelestialBodyRenderer {
	/**
	 * Returns the size of this star, as a multiple of the Sun's size
	 * @return the size of this star
	 */
	public abstract float getSize();
	
	/**
	 * Returns the brightness of this star, used for calculating light levels and solar energy multipliers
	 * @return the brightness of this star, as a multiple of that of the overworld sun
	 */
	public abstract float getBrightness();
	
	@Override
	public float getPlanetSize() {
		return (float) (Math.sqrt(getSize())*108F);
	}
	
	@Override
	public boolean isReachable() {
		return false;
	}

	@Override
	public IMapObject getMapObject() {
		return this;
	}

	@Override
	public boolean autoRegister() {
		return false;
	}

	@Override
	public Class<? extends WorldProvider> getWorldProvider() {
		return null;
	}

	@Override
	public int getDimensionID() {
		return -1;
	}

	@Override
	public String getPlanetName() {
		return getName();
	}

	@Override
	public void renderSlot(int index, int x, int y, float slotHeight, Tessellator tessellator) {
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x - slotHeight * 0.9, y + slotHeight * 0.9, -90.0D, 0.0, 1.0);
        tessellator.addVertexWithUV(x + slotHeight * 0.9, y + slotHeight * 0.9, -90.0D, 1.0, 1.0);
        tessellator.addVertexWithUV(x + slotHeight * 0.9, y - slotHeight * 0.9, -90.0D, 1.0, 0.0);
        tessellator.addVertexWithUV(x - slotHeight * 0.9, y - slotHeight * 0.9, -90.0D, 0.0, 0.0);
        tessellator.draw();
	}

	@Override
	public ICelestialBodyRenderer getSlotRenderer() {
		return this;
	}
	
	@Override
	public boolean addToList() {
		return false;
	}
}
