package foodisgood_orukum.mods.pop.space;

import foodisgood_orukum.mods.pop.PlanetsOPlenty;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldProvider;
import micdoodle8.mods.galacticraft.api.world.*;

public class NetherPlanet implements IPlanet, IMapObject, ICelestialBodyRenderer {
	@Override
	public String getName() {
		return "The Nether";
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
		return null;
	}

	@Override
	public int getDimensionID() {
		return 1;
	}

	@Override
	public IGalaxy getParentGalaxy() {
		return POPCelestialObjects.northGalaxy;
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
