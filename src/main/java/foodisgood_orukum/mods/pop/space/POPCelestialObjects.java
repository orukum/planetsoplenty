package foodisgood_orukum.mods.pop.space;

import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import foodisgood_orukum.mods.pop.PlanetsOPlenty;

public class POPCelestialObjects {
	public static EastGalaxy eastGalaxy;
	public static WestGalaxy westGalaxy;
	public static NetherGalaxy northGalaxy;
	public static WestStar westStar;
	public static EastStarA eastStarA;
	public static EastStarB eastStarB;
	public static SuperflatEastPlanet superflatEastPlanet;
	public static NetherPlanet netherPlanet;
	
	public static void init(FMLInitializationEvent event) {
		if (PlanetsOPlenty.debug) {
			eastGalaxy = new EastGalaxy();
			westGalaxy = new WestGalaxy();
			northGalaxy = new NetherGalaxy();
			westStar = new WestStar();
			westGalaxy.suns.add(westStar);
			eastStarA = new EastStarA();
			eastGalaxy.suns.add(eastStarA);
			eastStarB = new EastStarB();
			eastGalaxy.suns.add(eastStarB);
			superflatEastPlanet = new SuperflatEastPlanet(10000);
			netherPlanet = new NetherPlanet();

			GalacticraftRegistry.registerGalaxy(westGalaxy);
			GalacticraftRegistry.registerGalaxy(eastGalaxy);
			GalacticraftRegistry.registerCelestialBody(westStar);
			GalacticraftRegistry.registerCelestialBody(eastStarA);
			GalacticraftRegistry.registerCelestialBody(eastStarB);
			GalacticraftRegistry.registerCelestialBody(superflatEastPlanet);
		}
	}
}
