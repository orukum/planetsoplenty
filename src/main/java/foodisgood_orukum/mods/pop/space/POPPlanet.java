package foodisgood_orukum.mods.pop.space;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.WorldProvider;
import micdoodle8.mods.galacticraft.api.world.*;

/**
 * Be sure to call initSolar whenever the solar system this is in has been set up completely, in particular when all stars have been added to it. Also be sure to register any moons here so they can be rendered in the sky.
 * Don't forget to fill moons!
 * @author foodisgoodyesiam
 *
 */
public abstract class POPPlanet extends WorldProvider implements IPlanet, IMapObject, ICelestialBodyRenderer, IGalacticraftWorldProvider, ISolarLevel, IExitHeight {
	public double solarMultiplier;
	double specialMultiplier;
	
	public static final float ZERO_G = .0656F,
			GC_RATIO = -.02F; //Multiply GC_RATIO by number of Gs, add to ZERO_G
	
	public static final float getGCGravityFactor(float numGs) {
		return ZERO_G+GC_RATIO*numGs;
	}
	
	public IMoon[] moons = {};
	
	public POPPlanet() {
		solarMultiplier = 1;
		specialMultiplier = 1;
	}
	
	/**
	 * Multiplies the solar multiplier for this planet by a special provided factor
	 * @param solarSpecialFactor the special multiplier for this planet
	 */
	protected POPPlanet(double solarSpecialFactor) {
		solarMultiplier = 1;
		specialMultiplier = 1;
	}
	
	/**
	 * Inits solar multiplier for this planet. Note that this can be called more than once if conditions change for osme reason.
	 * @param specialFactor The special multiplier for this planet.
	 */
	public void initSolar(double specialFactor) {
		if (getParentGalaxy() instanceof POPGalaxy) {
			POPGalaxy galaxy = (POPGalaxy) getParentGalaxy();
			if (galaxy.suns.length == 0)
				solarMultiplier = .01;
			else {
				solarMultiplier = 0;
				for (POPStar star : galaxy.suns)
					solarMultiplier+=(star.getBrightness());
				solarMultiplier*=(this.getDistanceFromCenter()*this.getDistanceFromCenter());
			}
			solarMultiplier*=specialFactor;
		} else {
			solarMultiplier = specialFactor;
		}
		
		//TODO: Add section here accounting for atmosphere type
	}
	/* Interesting functions to implement:
	 * isBlockHighHumidity
	 * canDoLightning
	 * canBlockFreeze
	 * isSkyColored
	 * canDoRainSnowIce
	 * getFuelUsageMultiplier
	 * canSpaceshipTierPass
	 * canMineBlock
	 * canSnowHere
	 * doesXZShowFog
	 * getFallDamageMultiplier
	 * canRespawnHere
	 * drawClouds?
	 * getMeteorFrequency
	 * setCloudRenderer
	 * 
	 */
	
	@Override
	public boolean canCoordinateBeSpawn(int par1, int par2) {
		return true;
	}
	
	@Override
	public double getMovementFactor() {
		return getPlanetSize()==0 ? 1 : 1/getPlanetSize();
	}
	
	@Override
	public double getSolarEnergyMultiplier() {
		return solarMultiplier;
	}
	
	@Override
	public double getFuelUsageMultiplier() {
		return Math.sqrt(getGravity());
	}
	
	@Override
	public float getFallDamageModifier() {
		return (float) Math.pow(getGravity(), 1.32);
	}
	
	@Override
	public boolean isReachable() {
		return true;
		//TODO: Hmm, here we might eventually want to handle whether players have discovered this planet yet... 
	}
	
	@Override
	public boolean isSurfaceWorld() {
		return true; 
	}
	
	@Override
	public IMapObject getMapObject() {
		return this;
	}
	
	@Override
	public float getStretchValue() {
		return 1F;
		//TODO: Calculate this based on stars sizes and distance... (perhaps eventually implement mass of stars, as well? and possibly of planets)
	}

	@Override
	public ICelestialBodyRenderer getSlotRenderer() {
		return this;
	}
	
	@Override
	public String getDimensionName() {
		return getName();
	}

    @Override
    public String getSaveFolder()
    {
        return "POP_DIM_" + dimensionId;
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
