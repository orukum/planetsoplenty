package foodisgood_orukum.mods.pop.space;

import micdoodle8.mods.galacticraft.api.vector.Vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.*;

public class EastGalaxy extends POPGalaxy {
	@Override
	public String getGalaxyName() {
		return "East Galaxy";
	}

	@Override
	public int getXCoord() {
		return 4;
	}

	@Override
	public int getYCoord() {
		return 6;
	}

	@Override
	public Vector3 getRGBRingColors() {
        return new Vector3(240.0D / 256.0D, 256.0D / 256.0D, 6.0D / 256.0D);
	}
}
