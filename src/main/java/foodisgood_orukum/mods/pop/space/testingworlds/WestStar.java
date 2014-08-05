package foodisgood_orukum.mods.pop.space.testingworlds;

import foodisgood_orukum.mods.pop.space.*;
import foodisgood_orukum.mods.pop.PlanetsOPlenty;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldProvider;
import micdoodle8.mods.galacticraft.api.world.*;

public class WestStar extends POPStar {
	@Override
	public String getName() {
		return "West Star";
	}

	@Override
	public IGalaxy getParentGalaxy() {
		return POPCelestials.westGalaxy;
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
	public ResourceLocation getPlanetSprite() {
		return new ResourceLocation(PlanetsOPlenty.TEXTURE_DOMAIN, "textures/planets/westsun.png");
	}

	@Override
	public String getPlanetName() {
		return "West Star";
	}

	@Override
	public float getSize() {
		return 1;
	}

	@Override
	public float getBrightness() {
		return 1.2F;
	}
}
