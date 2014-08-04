package foodisgood_orukum.mods.pop.space;

import foodisgood_orukum.mods.pop.PlanetsOPlenty;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldProvider;
import micdoodle8.mods.galacticraft.api.world.*;

public class EastStarB extends POPStar {
	@Override
	public String getName() {
		return "East Star B";
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
		return 1F;
	}

	@Override
	public float getStretchValue() {
		return .6F;
	}

	@Override
	public ResourceLocation getPlanetSprite() {
		return new ResourceLocation(PlanetsOPlenty.TEXTURE_DOMAIN, "textures/planets/eastsunB.png");
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
	public float getSize() {
		return .4F;
	}

	@Override
	public float getBrightness() {
		return .7F;
	}
}
