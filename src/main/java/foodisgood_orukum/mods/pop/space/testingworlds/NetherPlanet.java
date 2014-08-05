package foodisgood_orukum.mods.pop.space.testingworlds;

import foodisgood_orukum.mods.pop.space.*;
import foodisgood_orukum.mods.pop.PlanetsOPlenty;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldProviderHell;
import net.minecraftforge.common.DimensionManager;
import micdoodle8.mods.galacticraft.api.world.*;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;

public class NetherPlanet implements IPlanet, IMapObject, ICelestialBodyRenderer {
	@Override
	public String getName() {
		return DimensionManager.getProvider(-1).getDimensionName();
	}

	@Override
	public boolean isReachable() {
		return true;
	}

	@Override
	public IMapObject getMapObject() {
		return this;
	}

	@Override
	public boolean addToList() {
		return true;
	}

	@Override
	public boolean autoRegister() {
		return false;
	}

	@Override
	public Class<? extends WorldProvider> getWorldProvider() {
		return NetherProviderMine.class;
	}
	
	public class NetherProviderMine extends WorldProviderHell implements IGalacticraftWorldProvider {
		@Override
		public float getGravity() {
			return 1;
		}

		@Override
		public double getMeteorFrequency() {
			return 0;
		}

		@Override
		public double getFuelUsageMultiplier() {
			return 1;
		}

		@Override
		public boolean canSpaceshipTierPass(int tier) {
			return tier>3;
		}

		@Override
		public float getFallDamageModifier() {
			return 1;
		}

		@Override
		public float getSoundVolReductionAmount() {
			return 1;
		}
	}

	@Override
	public int getDimensionID() {
		return -1;
	}

	@Override
	public IGalaxy getParentGalaxy() {
		return POPCelestials.northGalaxy;
		//return GalacticraftCore.galaxyMilkyWay;
	}

	@Override
	public ResourceLocation getPlanetSprite() {
		return new ResourceLocation(PlanetsOPlenty.TEXTURE_DOMAIN, "textures/planets/nether.png");
	}

	@Override
	public String getPlanetName() {
		return getName();
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

	@Override
	public float getPlanetSize() {
		return 8;
	}

	@Override
	public float getDistanceFromCenter() {
		return 0;
	}

	@Override
	public float getPhaseShift() {
		return 0;
	}

	@Override
	public float getStretchValue() {
		return 0;
	}

	@Override
	public ICelestialBodyRenderer getSlotRenderer() {
		return this;
	}

	@Override
	public boolean forceStaticLoad() {
		return false;
	}
}

/*
public class GCCorePlanetOverworld implements IPlanet
{

	@Override
	public boolean isReachable()
	{
		return true;
	}

	@Override
	public IMapObject getMapObject()
	{
		return this.overworld;
	}

	@Override
	public IGalaxy getParentGalaxy()
	{
		return GalacticraftCore.galaxyMilkyWay;
	}

	@Override
	public boolean addToList()
	{
		return true;
	}

	@Override
	public boolean autoRegister()
	{
		return false;
	}

	@Override
	public Class<? extends WorldProvider> getWorldProvider()
	{
		return null;
	}

	@Override
	public int getDimensionID()
	{
		return 0;
	}

	@Override
	public boolean forceStaticLoad()
	{
		return false;
	}
}*/
