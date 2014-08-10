package foodisgood_orukum.mods.pop.space;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import foodisgood_orukum.mods.pop.POPConfigManager;
import micdoodle8.mods.galacticraft.api.world.*;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.WorldProvider;

/**
 * Be sure to call initSolar whenever the solar system this is in has been set up completely, in particular when all stars have been added to it. Also be sure to register any moons here so they can be rendered in the sky.
 * @author foodisgoodyesiam
 *
 */
public abstract class POPWorld extends WorldProvider implements ICelestialBody, IMapObject, ICelestialBodyRenderer, IGalacticraftWorldProvider, ISolarLevel, IExitHeight {
	public double solarMultiplier;
	double specialMultiplier;
	public boolean tidallyLocked;
	public transient boolean rendererLoaded = false;
	
	public static final float ZERO_G = .0656F,
			GC_RATIO = -.02F; //Multiply GC_RATIO by number of Gs, add to ZERO_G
	
	public static final float getGCGravityFactor(float numGs) {
		return ZERO_G+GC_RATIO*numGs;
	}
	
	public POPWorld() {
		solarMultiplier = 1;
		specialMultiplier = 1;
		tidallyLocked = false;
	}
	
	public POPWorld(double specialSolarFactor, int dimensionID) {
		solarMultiplier = 1;
		specialMultiplier = specialSolarFactor;
		dimensionId = dimensionID;
		tidallyLocked = false;
		//setCloudRenderer(null);
		//setSkyRenderer(null);
	}
	
	/**
	 * Provides a pseudorandom number given both a set of coordinates and a seed, useful for terrain generation.
	 * @param x the x coordinate
	 * @param z the z coordinate
	 * @param seed the seed
	 * @return a pseudorandom number
	 */
    public static double randFromPoint(int x, int z, long seed) {
        long n;
        n = x + z * 57;
        n = n << 13 ^ n;
        n^=seed^(seed);
        return 1.0 - (n * (n * n * 15731 + 789221) + 1376312589 & 0x7fffffff) / 1073741824.0;
    }

	@Override
	public Class<? extends WorldProvider> getWorldProvider() {
		return this.getClass();
	}
	
	/**
	 * Provides a pseudorandom number using only a set of coordinates, useful for terrain generation.
	 * @param x the x coordinate
	 * @param z the z coordinate
	 * @return a pseudorandom number
	 */
    public static double randFromPoint(int x, int z) {
        int n;
        n = x + z * 57;
        n = n << 13 ^ n;
        return 1.0 - (n * (n * n * 15731 + 789221) + 1376312589 & 0x7fffffff) / 1073741824.0;
    }
	
	/**
	 * Provides a pseudorandom number using both a set of coordinates and the current world's seed, useful for terrain generation.
	 * @param x the x coordinate
	 * @param z the z coordinate
	 * @return a pseudorandom number
	 */
    public final double randFromPointSeed(int x, int z) {
        long n;
        n = x + z * 57;
        n = n << 13 ^ n;
        n^=this.worldObj.getSeed()^(worldObj.getSeed()%dimensionId)^(dimensionId<<9);
        return 1.0 - (n * (n * n * 15731 + 789221) + 1376312589 & 0x7fffffff) / 1073741824.0;
    }
    
	/**
	 * Inits solar multiplier for this planet or moon. Note that this can be called more than once if conditions change for some reason.
	 * @param specialFactor The special multiplier for this planet.
	 */
    public abstract void initSolar(double specialFactor);
	
	@Override
	public boolean canCoordinateBeSpawn(int par1, int par2) {
		return true;
	}
	
	@Override
	public boolean canRespawnHere() {
		return !POPConfigManager.forceOverworldRespawn;
	}

	@Override
	public final int getDimensionID() {
		return dimensionId;
	}
	
	/**
	 * Returns solar energy multiplier. This should only be called after initSolar() has also been called.
	 * Final for efficiency. If wanting to override for a planet, override initSolar() instead.
	 */
	@Override
	public final double getSolarEnergyMultiplier() {
		return solarMultiplier;
	}
	
	@Override
	public boolean isReachable() {
		return true;
	}
	
	@Override
	public boolean isSurfaceWorld() {
		return true; 
	}
	
	@Override
	@SideOnly(value=Side.CLIENT)
	public float getStarBrightness(float t) {
		return 1F;
	}
	
	/*@Override
	@SideOnly(value=Side.CLIENT)
	public float getStarBrightnessBody(float t) {
		return 1F;
	}*/
	
	@Override
	public IMapObject getMapObject() {
		return this;
	}

	@Override
	public ICelestialBodyRenderer getSlotRenderer() {
		return this;
	}
	
	@Override
	public double getMovementFactor() {
		return getPlanetSize()==0 ? 1 : 1/getPlanetSize();
	}
	
	@Override
	public double getFuelUsageMultiplier() {
		return getGravity();
	}
	
	@Override
	public float getFallDamageModifier() {
		return (float) Math.pow(getGravity(), 1.12F);
	}
	
	@Override
	public abstract float getStretchValue();
	
	/**
	 * May not be overriden as for GC to work properly, the dimension name must match getName().
	 * @return getName()
	 */
	@Override
	public final String getDimensionName() {
		return getName();
	}

    protected static final int getIndex(int x, int y, int z) {
        return y << 8 | z << 4 | x;
    }
    
	@Override
	public final String getPlanetName() {
		return getName();
	}

    @Override
    public String getSaveFolder()
    {
        return "POP_DIM_" + dimensionId;
    }
	
    @Override
    public void setDimension(int var1)
    {
        this.dimensionId = var1;
        super.setDimension(var1);
    }

	@Override
	public void renderSlot(int index, int x, int y, float slotHeight, Tessellator tessellator) {
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + 12 - slotHeight, y - 11 + slotHeight, -90.0D, 0.0, 1.0);
        tessellator.addVertexWithUV(x + 12, y - 11 + slotHeight, -90.0D, 1.0, 1.0);
        tessellator.addVertexWithUV(x + 12, y - 11, -90.0D, 1.0, 0.0);
        tessellator.addVertexWithUV(x + 12 - slotHeight, y - 11, -90.0D, 0.0, 0.0);
        tessellator.draw();
	}
}
