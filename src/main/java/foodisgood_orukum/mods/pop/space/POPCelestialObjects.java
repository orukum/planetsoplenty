package foodisgood_orukum.mods.pop.space;

import net.minecraftforge.common.DimensionManager;
import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.moon.dimension.GCMoonTeleportType;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
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
	
	public static void preInit(FMLPreInitializationEvent event) {
		eastGalaxy = new EastGalaxy();
		westGalaxy = new WestGalaxy();
		northGalaxy = new NetherGalaxy();
		westStar = new WestStar();
		westGalaxy.suns = new POPStar[1];
		westGalaxy.suns[0] = westStar;
		//westGalaxy.suns.add(westStar);
		eastStarA = new EastStarA();
		eastGalaxy.suns = new POPStar[2];
		//eastGalaxy.suns.add(eastStarA);
		eastGalaxy.suns[0] = eastStarA;
		eastStarB = new EastStarB();
		eastGalaxy.suns[1] = eastStarB;
		//eastGalaxy.suns.add(eastStarB);
		superflatEastPlanet = new SuperflatEastPlanet(10000);
		netherPlanet = new NetherPlanet();
		
		superflatEastPlanet.initSolar(1);

		GalacticraftRegistry.registerGalaxy(westGalaxy);
		GalacticraftRegistry.registerGalaxy(eastGalaxy);
		GalacticraftRegistry.registerGalaxy(northGalaxy);
		GalacticraftRegistry.registerCelestialBody(westStar);
		GalacticraftRegistry.registerCelestialBody(eastStarA);
		GalacticraftRegistry.registerCelestialBody(eastStarB);
		GalacticraftRegistry.registerCelestialBody(superflatEastPlanet);
		GalacticraftRegistry.registerCelestialBody(netherPlanet);
		GalacticraftRegistry.registerTeleportType(SuperflatEastPlanet.class, new GCMoonTeleportType());
	}
	
	public static void init(FMLInitializationEvent event) {
		if (PlanetsOPlenty.debug) {
			DimensionManager.registerProviderType(superflatEastPlanet.getDimensionID(), superflatEastPlanet.getWorldProvider(), false);
			DimensionManager.registerDimension(superflatEastPlanet.getDimensionID(), superflatEastPlanet.getDimensionID());
		}
	}
}
