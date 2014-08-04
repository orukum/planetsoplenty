package foodisgood_orukum.mods.pop.space;

import micdoodle8.mods.galacticraft.api.vector.Vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.*;

public class WestGalaxy extends POPGalaxy {
	@Override
	public String getGalaxyName() {
		return "West Galaxy";
	}

	@Override
	public int getXCoord() {
		return -1;
	}

	@Override
	public int getYCoord() {
		return 2;
	}

	@Override
	public Vector3 getRGBRingColors() {
        return new Vector3(2.0D / 256.0D, 256.0D / 256.0D, 6.0D / 256.0D);
	}
}
