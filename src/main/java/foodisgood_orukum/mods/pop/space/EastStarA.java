package foodisgood_orukum.mods.pop.space;

import foodisgood_orukum.mods.pop.PlanetsOPlenty;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldProvider;
import micdoodle8.mods.galacticraft.api.world.*;

public class EastStarA extends POPStar {
	@Override
	public String getName() {
		return "East Star A";
	}

	@Override
	public IGalaxy getParentGalaxy() {
		return POPCelestialObjects.eastGalaxy;
	}

	@Override
	public float getDistanceFromCenter() {
		return 0.2F;
	}

	@Override
	public float getPhaseShift() {
		return 1440;
	}

	@Override
	public float getStretchValue() {
		return .6F;
	}

	@Override
	public ResourceLocation getPlanetSprite() {
		return new ResourceLocation(PlanetsOPlenty.TEXTURE_DOMAIN, "textures/planets/eastsunA.png");
	}

	@Override
	public String getPlanetName() {
		return "East Star A";
	}

	@Override
	public float getSize() {
		return 140F/108F;
	}

	@Override
	public float getBrightness() {
		return 1.89F;
	}
}
