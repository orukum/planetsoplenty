package foodisgood_orukum.mods.pop.space;

import java.util.Vector;

import micdoodle8.mods.galacticraft.api.world.*;
/**
 * Don't forget to add suns in this galaxy to the member suns[]. so they can be rendered properly in the sky
 * @author foodisgoodyesiam
 *
 */
public abstract class POPGalaxy implements IGalaxy {
	public Vector<POPStar> suns;
	
	public POPGalaxy() {
		suns = new Vector<POPStar>();
	}
	
	public POPGalaxy(POPStar[] sunsArg) {
		suns = new Vector<POPStar>();
		for (POPStar sun : sunsArg)
			suns.add(sun);
	}
	
	public POPStar[] getSuns() {
		return (POPStar[]) suns.toArray();
	}
}
