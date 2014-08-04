package foodisgood_orukum.mods.pop.space;

import micdoodle8.mods.galacticraft.api.vector.Vector3;

public class NetherGalaxy extends POPGalaxy {
	@Override
	public String getGalaxyName() {
		return "North Galaxy";
	}

	@Override
	public int getXCoord() {
		return 0;
	}

	@Override
	public int getYCoord() {
		return 20;
	}

	@Override
	public final Vector3 getRGBRingColors() {
		return new Vector3(1D, 0D, 0D);
	}
}
