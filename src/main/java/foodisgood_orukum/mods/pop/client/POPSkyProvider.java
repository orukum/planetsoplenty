package foodisgood_orukum.mods.pop.client;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import micdoodle8.mods.galacticraft.api.GalacticraftRegistry;
import micdoodle8.mods.galacticraft.api.world.*;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.mars.GalacticraftMars;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraftforge.client.IRenderHandler;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import foodisgood_orukum.mods.pop.POPLog;
import foodisgood_orukum.mods.pop.space.*;

public class POPSkyProvider extends IRenderHandler {
	private static final ResourceLocation galaxyTexture = new ResourceLocation(GalacticraftMars.TEXTURE_DOMAIN, "textures/gui/planets/galaxy.png");
	private final ResourceLocation[] sunTextures;
	private final ResourceLocation[] moonTextures;
	private final ResourceLocation[] planetTextures;
	private final ResourceLocation parentTexture;
	private final POPGalaxy galaxy;
	private final POPStar[] suns;
	private final IMoon[] moons;
	private final IPlanet[] otherPlanets;
	private final IPlanet parent;
	
	private final long starSeed;

	public int starGLCallList = GLAllocation.generateDisplayLists(3);
	public int glSkyList;
	public int glSkyList2;
	
	/**
	 * Stored here for efficiency, rather than use instanceof every frame. This is distance of popWorld from distance of galaxy, regardless of whether it is a moon or not
	 */
	public final float popWorldDistance;
	
	public final POPWorld popWorld;
	
	/*/**
	 * Returns angle of specified star from center of galaxy
	 * @param starIndex index of star 
	 * @return angle in degrees, counterclockwise from directly at right...
	 * /
	public final float centerAngleS(int starIndex) {
		return (MathHelper.floor_float(suns[starIndex].getPhaseShift() + Sys.getTime() / (720F * 1 * suns[starIndex].getStretchValue())) % 2880)*360F/2880F;
		//TODO: test
	}*/
	
	/*/**
	 * Returns angle of specified planet from center of galaxy
	 * @param planetIndex index of planet
	 * @return angle in degrees
	 * /
	public final float centerAngleP(int planetIndex) {
		return 0F;
		//TODO: temp
	}*/
	
	/*/**
	 * Returns angle of this planet from center of galaxy
	 * @return angle in degrees
	 * /
	public final float centerAngle() {
		return (MathHelper.floor_float(popWorld.getPhaseShift() + Sys.getTime() / (720F * 1 * popWorld.getStretchValue())) % 2880)*360F/2880F;
		//TODO: temp
	}*/
	
	/**
	 * Calculates current angle from center of given object, in radians
	 * @param phaseShift result of getPhaseShift
	 * @param stretchValue result of getStretchValue
	 * @return the angle, in radians
	 */
	public final float centerAngleRad(float phaseShift, float stretchValue) {
		return (MathHelper.floor_float((phaseShift + Sys.getTime() / (720F * 1 * stretchValue))*100F) % 288000)*(360F/288000F)*((float)Math.PI)/180F;
	}
	
	/**
	 * Calculates current angle from center of given object, in degrees
	 * @param phaseShift result of getPhaseShift
	 * @param stretchValue result of getStretchValue
	 * @return the angle, in degrees
	 */
	public final float centerAngleDegree(float phaseShift, float stretchValue) {
		return (MathHelper.floor_float((phaseShift + Sys.getTime() / (720F * 1 * stretchValue))*100F) % 288000)*360F/288000F;
	}
	
	/*/**
	 * Returns angle of specified moon from planet it is orbiting
	 * @param moonIndex index of moon
	 * @return angle in degrees
	 * /
	public final float centerAngleM(int moonIndex) {
		return 0F;
		//TODO: temp
	}*/
	
	/**
	 * Calculates distance to given star
	 * @param starIndex index of star
	 * @param worldAngle current angle of popWorld about the galactic center in radians, passed in for efficiency
	 * @return
	 */
	public final float distanceToStar(int starIndex, float worldAngle) {
		final float starD = suns[starIndex].getDistanceFromCenter();
		return MathHelper.sqrt_float(popWorldDistance*popWorldDistance+starD*starD-2F*popWorldDistance*starD*MathHelper.cos(worldAngle-centerAngleRad(suns[starIndex].getPhaseShift(), suns[starIndex].getStretchValue())));
		//TODO: test
	}
	
	/**
	 * Calculates distance to given planet
	 * @param planetIndex index of planet
	 * @param worldAngle current angle of popWorld (or parent planet) about the galactic center in radians, passed in for efficiency
	 * @return
	 */
	public final float distanceToPlanet(int planetIndex, float worldAngle) {
		final float starD = otherPlanets[planetIndex].getMapObject().getDistanceFromCenter();
		return MathHelper.sqrt_float(popWorldDistance*popWorldDistance+starD*starD-2F*popWorldDistance*starD*MathHelper.cos(worldAngle-centerAngleRad(otherPlanets[planetIndex].getMapObject().getPhaseShift(), otherPlanets[planetIndex].getMapObject().getStretchValue())));
		//TODO: test
	}
	
	
	
	/**
	 * Calculates distance to given other moon (used only if this world is a moon)
	 * @param moonIndex index of other moon
	 * @param worldAngle current angle of popWorld about parent planet in radians, passed in for efficiency
	 * @return
	 */
	public final float distanceToMoon(int moonIndex, float worldAngle) {
		final float starD = moons[moonIndex].getMapObject().getDistanceFromCenter()/30F;
		final float myD = popWorld.getDistanceFromCenter();
		return MathHelper.sqrt_float(myD*myD+starD*starD-2F*myD*starD*MathHelper.cos(worldAngle-centerAngleRad(moons[moonIndex].getMapObject().getPhaseShift(), moons[moonIndex].getMapObject().getStretchValue())));
		//TODO: test
	}
	
	/**
	 * The angle from the center of the galaxy at which the parent planet of this moon appears.
	 * @param galaxyAngle the current angle of popWorld's parent from center of galaxy, in radians
	 * @return the angle, in degrees
	 */
	public final float angleParent(float galaxyAngle) {
		final float sAngle = centerAngleRad(parent.getMapObject().getPhaseShift(), parent.getMapObject().getStretchValue());
		final float y = popWorldDistance*MathHelper.sin(galaxyAngle-sAngle);
		final float hyp = popWorld.getDistanceFromCenter();
		float ret = ((float) Math.asin( y/hyp ))*180F/((float)Math.PI);
		if (MathHelper.abs(galaxyAngle-sAngle)<Math.asin(popWorld.getDistanceFromCenter()/popWorldDistance))
			ret+=180F;
		return ret;
		//TODO: test this
	}
	
	/**
	 * The angle from the center of the galaxy at which a given star appears.
	 * @param starIndex the index of the sun whose angle should be compared
	 * @param galaxyAngle the current angle of popWorld from center of galaxy, in radians
	 * @return the angle, in degrees
	 */
	public final float angleS(int starIndex, float galaxyAngle) {
		final float sAngle = centerAngleRad(suns[starIndex].getPhaseShift(), suns[starIndex].getStretchValue());
		final float y = suns[starIndex].getDistanceFromCenter()*MathHelper.sin(galaxyAngle-sAngle);
		final float hyp = distanceToStar(starIndex, galaxyAngle);
		float ret = ((float) Math.asin( y/hyp ))*180F/((float)Math.PI);
		/*if (angle<-180) {
			do {
				angle+=180F;
			} while (angle<-180F);
		} else if (angle>180F) {
			do {
				angle-=180F;
			} while (angle>180F);
		}*/
		if (suns[starIndex].getDistanceFromCenter()>popWorldDistance && MathHelper.abs(galaxyAngle-sAngle)<Math.asin(popWorldDistance/(suns[starIndex].getDistanceFromCenter())) || false)
			ret+=180F;
		return ret;
		//TODO: test this
	}
	
	/**
	 * The angle from the center of the galaxy at which a given planet appears.
	 * @param planetIndex the index of the planet whose angle should be returned
	 * @param galaxyAngle the current angle of popWorld from center of galaxy, in radians
	 */
	public final float angleP(int planetIndex, float galaxyAngle) {
		final float pAngle = centerAngleRad(otherPlanets[planetIndex].getMapObject().getPhaseShift(), otherPlanets[planetIndex].getMapObject().getStretchValue());
		final float y = otherPlanets[planetIndex].getMapObject().getDistanceFromCenter()*MathHelper.sin(galaxyAngle-pAngle);
		final float hyp = distanceToPlanet(planetIndex, galaxyAngle);
		float ret = ((float) Math.asin( y/hyp ))*180F/((float)Math.PI);
		if (otherPlanets[planetIndex].getMapObject().getDistanceFromCenter()>popWorldDistance && (MathHelper.abs(galaxyAngle-pAngle)<Math.asin(popWorldDistance/(otherPlanets[planetIndex].getMapObject().getDistanceFromCenter()))))
			ret+=180F;
		return ret;
		//TODO: test this
	}
	
	/**
	 * The angle from the center of the galaxy at which another given moon appears. (Only for use if popWorld is a moon)
	 * @param moonIndex the index of the moon whose angle should be returned
	 * @param parentGalaxyAngle the current angle of popWorld's parent from center of galaxy, in radians
	 * @return the angle, in degrees
	 */
	public final float angleM(int moonIndex, float parentGalaxyAngle) {
		final float worldAngle = centerAngleRad(popWorld.getPhaseShift(), popWorld.getStretchValue());
		final float mAngle = centerAngleRad(moons[moonIndex].getMapObject().getPhaseShift(), moons[moonIndex].getMapObject().getStretchValue());
		final float y = moons[moonIndex].getMapObject().getDistanceFromCenter()*MathHelper.sin(worldAngle-mAngle)/30F;
		final float hyp = distanceToMoon(moonIndex, centerAngleRad(popWorld.getPhaseShift(), popWorld.getStretchValue()));
		float ret = ((float) Math.asin( y/hyp ))*180F/((float)Math.PI);
		if (moons[moonIndex].getMapObject().getDistanceFromCenter()>popWorld.getDistanceFromCenter() && (MathHelper.abs(worldAngle-mAngle)<Math.asin(popWorld.getDistanceFromCenter()/(moons[moonIndex].getMapObject().getDistanceFromCenter()))))
			ret+=180F;
		return ret+parentGalaxyAngle-angleParent(parentGalaxyAngle);
		//TODO: test this
	}
	
	//protected static final strictfp synchronized void foo() {}

	/**
	 * Note this should only be created after the galaxy itself, and all its contents, are properly configured. (It retrieves the parent galaxy and makes a list of moons and planets on construction.)
	 * @param worldObj The POPWorld whose sky is being provided
	 */
	public POPSkyProvider(POPWorld worldObj) {
		popWorld = worldObj;
		popWorld.rendererLoaded = true;
		POPLog.info("POP Sky provider initialized for " + popWorld.getName() + ", with ID " + popWorld.dimensionId);
		starSeed = popWorld.getSeed() ^ popWorld.getDimensionID();
		if (popWorld.getParentGalaxy() instanceof POPGalaxy) {
			galaxy = (POPGalaxy) popWorld.getParentGalaxy();
			if (galaxy.suns!=null) {
				suns = galaxy.suns;
				sunTextures = new ResourceLocation[suns.length];
				for (int i=0; i<suns.length; i++)
					sunTextures[i] = suns[i].getPlanetSprite();
			} else {
				sunTextures = null;
				suns = null;
			}
			List<ICelestialBody> bodies = GalacticraftRegistry.getCelestialBodies();
			ArrayList<IPlanet> planetsTemp = new ArrayList<IPlanet>();
			ArrayList<ICelestialBody> moonsTemp = new ArrayList<ICelestialBody>();
			if (popWorld instanceof POPMoon) {
				parent = ((POPMoon)popWorld).getParentPlanet();
				parentTexture = parent.getMapObject().getSlotRenderer().getPlanetSprite();
				popWorldDistance = parent.getMapObject().getDistanceFromCenter();
			} else {
				parent = null;
				parentTexture = null;
				popWorldDistance = popWorld.getDistanceFromCenter();
			}
			for (int i=0; i<bodies.size(); i++)
				if (bodies.get(i).getMapObject().getParentGalaxy().getGalaxyName()==galaxy.getGalaxyName() && bodies.get(i).getName()!=popWorld.getName()  && (parent==null || bodies.get(i).getName()!=parent.getName()) && !(bodies.get(i) instanceof POPStar)) {
					if (!(popWorld instanceof POPPlanet && ((POPPlanet)popWorld).moons!=null) && bodies.get(i) instanceof IMoon) {
						if (popWorld instanceof POPMoon) {
							if (((IMoon) bodies.get(i)).getParentPlanet().getName() == parent.getName())
								moonsTemp.add((IMoon)bodies.get(i));
						} else {
							if (((IMoon) bodies.get(i)).getParentPlanet().getName() == popWorld.getName())
								moonsTemp.add((IMoon)bodies.get(i));
						}
					} else if (bodies.get(i) instanceof IPlanet)
						planetsTemp.add((IPlanet)bodies.get(i));
				}
			if (planetsTemp.size()>0) {
				IPlanet planetTemp[] = new IPlanet[0];
				otherPlanets = planetsTemp.toArray(planetTemp);
				planetTextures = new ResourceLocation[otherPlanets.length];
				for (int i=0; i<otherPlanets.length; i++)
					planetTextures[i] = otherPlanets[i].getMapObject().getSlotRenderer().getPlanetSprite();
			} else {
				otherPlanets = null;
				planetTextures = null;
			}
			boolean moonsAlready = false;
			/*if (popWorld instanceof POPPlanet) {
				if (((POPPlanet)popWorld).moons!=null && ((POPPlanet)popWorld).moons.length>0) {
					moons = ((POPPlanet)popWorld).moons;
					moonsAlready = true;
				}
			} else if (parent!=null && parent instanceof POPPlanet) {
				final POPPlanet parentP = (POPPlanet)parent;
				if (parentP.moons!=null && parentP.moons.length>0) {
					moons = new IMoon[parentP.moons.length-1];
					int i2 = 0;
					for (int i=0; i<parentP.moons.length; i++)
						if (popWorld.getName()!=parentP.moons[i].getName())
							moons[i2++] = parentP.moons[i];
					moonsAlready = true;
				}
			}*/
			if (moonsTemp.size()>0 && !moonsAlready) {
				IMoon moonTemp[] = new IMoon[0];
				moons = moonsTemp.toArray(moonTemp);
				moonTextures = new ResourceLocation[moons.length];
				for (int i=0; i<moons.length; i++)
					moonTextures[i] = moons[i].getMapObject().getSlotRenderer().getPlanetSprite();
			} else {
				moons = null;
				moonTextures = null;
			}
		} else {
			galaxy = null;
			suns = null;
			sunTextures = new ResourceLocation[1];
			sunTextures[0] = new ResourceLocation("textures/environment/sun.png");
			otherPlanets = null;
			planetTextures = null;
			moons = null;
			moonTextures = null;
			parent = null;
			parentTexture = null;
			popWorldDistance = popWorld.getDistanceFromCenter();
		}
		GL11.glPushMatrix();
		GL11.glNewList(this.starGLCallList, GL11.GL_COMPILE);
		this.renderStars();
		GL11.glEndList();
		GL11.glPopMatrix();
		final Tessellator tessellator = Tessellator.instance;
		this.glSkyList = this.starGLCallList + 1;
		GL11.glNewList(this.glSkyList, GL11.GL_COMPILE);
		final byte byte2 = 64;
		final int i = 256 / byte2 + 2;
		float f = 16F;
		for (int j = -byte2 * i; j <= byte2 * i; j += byte2)
			for (int l = -byte2 * i; l <= byte2 * i; l += byte2) {
				tessellator.startDrawingQuads();
				tessellator.addVertex(j + 0, f, l + 0);
				tessellator.addVertex(j + byte2, f, l + 0);
				tessellator.addVertex(j + byte2, f, l + byte2);
				tessellator.addVertex(j + 0, f, l + byte2);
				tessellator.draw();
			}
		GL11.glEndList();
		this.glSkyList2 = this.starGLCallList + 2;//Hmm. Perhaps I should change this somehow? It is apparently called last in the procedure 
		GL11.glNewList(this.glSkyList2, GL11.GL_COMPILE);
		f = -16F;
		tessellator.startDrawingQuads();
		for (int k = -byte2 * i; k <= byte2 * i; k += byte2)
			for (int i1 = -byte2 * i; i1 <= byte2 * i; i1 += byte2) {
				tessellator.addVertex(k + byte2, f, i1 + 0);
				tessellator.addVertex(k + 0, f, i1 + 0);
				tessellator.addVertex(k + 0, f, i1 + byte2);
				tessellator.addVertex(k + byte2, f, i1 + byte2);
			}

		tessellator.draw();
		GL11.glEndList();
	}

	@Override
	public void render(float partialTicks, WorldClient world, Minecraft mc) {
		float var10;
		float var11;
		float var12;
		final Tessellator tesselator = Tessellator.instance;

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		final Vec3 skyColor = this.getCustomSkyColor();
		float var3 = (float) skyColor.xCoord * (1 - world.getStarBrightness(partialTicks) * 2);
		float var4 = (float) skyColor.yCoord * (1 - world.getStarBrightness(partialTicks) * 2);
		float var5 = (float) skyColor.zCoord * (1 - world.getStarBrightness(partialTicks) * 2);
		float var8;

		if (mc.gameSettings.anaglyph) {
			final float var6 = (var3 * 30.0F + var4 * 59.0F + var5 * 11.0F) / 100.0F;
			final float var7 = (var3 * 30.0F + var4 * 70.0F) / 100.0F;
			var8 = (var3 * 30.0F + var5 * 70.0F) / 100.0F;
			var3 = var6;
			var4 = var7;
			var5 = var8;
		}

		GL11.glColor3f(1, 1, 1);
		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_FOG);
		GL11.glColor3f(0, 0, 0);
		GL11.glCallList(this.glSkyList);
		GL11.glDisable(GL11.GL_FOG);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		RenderHelper.disableStandardItemLighting();

		final float starBrightness = popWorld.getStarBrightness(partialTicks);

		if (starBrightness > 0.0F) {
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, starBrightness);
			GL11.glPushMatrix();
			GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F+160F, 1.0F, 0.0F, 0.0F);
			GL11.glCallList(this.starGLCallList);
			GL11.glPopMatrix();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		}

		GL11.glPushMatrix();

		//GL11.glDisable(GL11.GL_BLEND);
		//GL11.glEnable(GL11.GL_ALPHA_TEST);
		//GL11.glEnable(GL11.GL_FOG);
		GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);

		GL11.glEnable(GL11.GL_TEXTURE_2D);

		// Distant Galaxy:
		var12 = 10.5F;
		GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F+160F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(70.0F, 20.0F, 14.23F, 1.0F);
		GL11.glRotatef(150F, 1.0F, 0.0F, 0.0F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(POPSkyProvider.galaxyTexture);
		//GL11.glColor4f(0.4F, 0.4F, 0.4F, 1.0F);
		GL11.glColor4f(1F, 1F, 1F, 1.0F);
		//GL11.glColor4f(0F, 0F, 0F, 1.0F);
		tesselator.startDrawingQuads();
		tesselator.addVertexWithUV(-var12, -100.0D, var12, 0, 1);
		tesselator.addVertexWithUV(var12, -100.0D, var12, 1, 1);
		tesselator.addVertexWithUV(var12, -100.0D, -var12, 1, 0);
		tesselator.addVertexWithUV(-var12, -100.0D, -var12, 0, 0);
		tesselator.draw();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glEnable(GL11.GL_FOG);
		GL11.glPopMatrix();
		final float worldAngleRad;
		if (popWorld instanceof POPMoon && parent!=null)
			worldAngleRad = centerAngleRad(parent.getMapObject().getPhaseShift(), parent.getMapObject().getStretchValue());
		else
			worldAngleRad = centerAngleRad(popWorld.getPhaseShift(), popWorld.getStretchValue());
		// OUTER PLANETS:
		if (otherPlanets!=null) {
			for (int i=0; i<otherPlanets.length; i++) {
				if (distanceToPlanet(i, worldAngleRad)>=popWorldDistance) {
					GL11.glPushMatrix();
					GL11.glScalef(0.6F, 0.6F, 0.6F);
					GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F + angleP(i, worldAngleRad), 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(200F, 1.0F, 0.0F, 0.0F);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
					var12 = .5F*((float)Math.atan(otherPlanets[i].getMapObject().getPlanetSize()/(distanceToPlanet(i, worldAngleRad)*19.8F)))*180F/(3F*((float)Math.PI));
					FMLClientHandler.instance().getClient().renderEngine.bindTexture(planetTextures[i]);
					tesselator.startDrawingQuads();
					tesselator.addVertexWithUV(-var12, -100.0D, var12, 0, 1);
					tesselator.addVertexWithUV(var12, -100.0D, var12, 1, 1);
					tesselator.addVertexWithUV(var12, -100.0D, -var12, 1, 0);
					tesselator.addVertexWithUV(-var12, -100.0D, -var12, 0, 0);
					tesselator.draw();
					//That set of four lines below was here originally
					GL11.glPopMatrix();
				}
			}
		}

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_FOG);

		GL11.glPushMatrix();
		//TODO: Drawing order should be: Other galaxy, then other planets, then suns, then moons, then (if applicable) parent planet
		//Compare this, the Mars sky provider, and the Moon sky provider to see differences
		// Suns:
		if (suns==null || suns.length==1) {
			if (sunTextures!=null) {
				GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
				//GL11.glColor4f(.4F, .4F, .4F, 1.0F);
				GL11.glColor4f(1F, 1F, 1F, 1.0F);
				if (suns[0].getDistanceFromCenter()==0) {
					GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
					var12 = 30.0F*((float)Math.atan(suns[0].getSize()/(popWorldDistance*19.8F)))*180F/(3F*((float)Math.PI));					
				} else {
					GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F + angleS(0, worldAngleRad), 1.0F, 0.0F, 0.0F);
					var12 = 30.0F*((float)Math.atan(suns[0].getSize()/(distanceToStar(0, worldAngleRad)*19.8F)))*180F/(3F*((float)Math.PI));//Treating it like the normal sun is 30 degrees across, not sure if that's true
				}
				FMLClientHandler.instance().getClient().renderEngine.bindTexture(sunTextures[0]);//Note: Another option might possibly be to actually vary their heights in the sky... TODO: Consider this
				tesselator.startDrawingQuads();
				tesselator.addVertexWithUV(-var12, 150.0D, -var12, 0.0D, 0.0D);
				tesselator.addVertexWithUV(var12, 150.0D, -var12, 1.0D, 0.0D);
				tesselator.addVertexWithUV(var12, 150.0D, var12, 1.0D, 1.0D);
				tesselator.addVertexWithUV(-var12, 150.0D, var12, 0.0D, 1.0D);
				tesselator.draw();
		
				GL11.glPopMatrix();
		
				GL11.glPushMatrix();
			}
		} else if (suns.length>1) {
			int order[] = {0, 0, 0, 0, 0, 0};
			float[] distances = {0F, 0F, 0F, 0F, 0F, 0F};
			for (int i=0; i<suns.length; i++)
				distances[i] = distanceToStar(i, worldAngleRad);
			switch (suns.length) {
			case 3:
				float d0 = distances[0], d1 = distances[1], d2 = distances[2];
				if (d0>d1) {//TODO: Hmm, I wonder if this method of sorting 3 manually is actually more efficient than using a normal sorting method
					if (d0>d2) {
						order[0] = 0;
						if (d1>d2) {
							order[1] = 1;
							order[2] = 2;
						} else {
							order[1] = 2;
							order[2] = 1;
						}
					} else {
						order[0] = 2;
						order[1] = 0;
						order[2] = 1;
					}
				} else {//d0<d1
					if (d0<d2) {//d0<d1 && d0<d2
						order[2] = 0;
						if (d1<d2) {
							order[0] = 2;
							order[1] = 1;
						} else {
							order[0] = 1;
							order[1] = 2;
						}
					} else {//d0<d1 && d2<d0
						order[0] = 1;
						order[1] = 0;
						order[2] = 2;
					}
				}
				break;
			default:
				//TODO: Some kind of default sorting? This is temporary, most likely (though there aren't likely to be a whole lot of planets with >3 suns anyways)
				for (int i=0; i< (suns.length>6 ? 6 : suns.length); i++)
					order[i] = i;
				break;
			case 2:
				if (distances[0]>distances[1]) {
					order[0] = 0;
					order[1] = 1;
				} else {
					order[0] = 1;
					order[1] = 0;
				}
			}
			int x = order[0];
			for (int i=0; i<(suns.length>6 ? 6 : suns.length); x=order[++i]) {
				GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				if (suns[x].getDistanceFromCenter()==0) {
					GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
					var12 = 30.0F*((float)Math.atan(suns[x].getSize()/(popWorldDistance*19.8F)))*180F/(3F*((float)Math.PI));
				} else {
					GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F + angleS(x, worldAngleRad), 1.0F, 0.0F, 0.0F);
					var12 = 30.0F*((float)Math.atan(suns[x].getSize()/(distanceToStar(x, worldAngleRad)*19.8F)))*180F/(3F*((float)Math.PI));//Treating it like the normal sun is 30 degrees across, not sure if that's true
				}
				FMLClientHandler.instance().getClient().renderEngine.bindTexture(sunTextures[x]);
				tesselator.startDrawingQuads();
				tesselator.addVertexWithUV(-var12, 150.0D, -var12, 0.0D, 0.0D);
				tesselator.addVertexWithUV(var12, 150.0D, -var12, 1.0D, 0.0D);
				tesselator.addVertexWithUV(var12, 150.0D, var12, 1.0D, 1.0D);
				tesselator.addVertexWithUV(-var12, 150.0D, var12, 0.0D, 1.0D);
				tesselator.draw();
		
				GL11.glPopMatrix();
		
				GL11.glPushMatrix();
			}
		}
		final float parentRotation = (float) (world.getSpawnPoint().posZ - mc.thePlayer.posZ) * 0.01F;
		GL11.glDisable(GL11.GL_BLEND);
		final float parentOrbit = parent==null ? 1F : centerAngleRad(parent.getMapObject().getPhaseShift(), parent.getMapObject().getStretchValue());
		// INNER PLANETS:
		if (otherPlanets!=null)
			for (int i=0; i<otherPlanets.length; i++)
				if (distanceToPlanet(i, worldAngleRad)<popWorldDistance) {
					GL11.glPushMatrix();
					GL11.glScalef(0.6F, 0.6F, 0.6F);
					GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F + angleP(i, worldAngleRad), 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(200F, 1.0F, 0.0F, 0.0F);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
					var12 = .5F*((float)Math.atan(otherPlanets[i].getMapObject().getPlanetSize()/(distanceToPlanet(i, worldAngleRad)*19.8F)))*180F/(3F*((float)Math.PI));
					FMLClientHandler.instance().getClient().renderEngine.bindTexture(planetTextures[i]);
					tesselator.startDrawingQuads();
					tesselator.addVertexWithUV(-var12, -100.0D, var12, 0, 1);
					tesselator.addVertexWithUV(var12, -100.0D, var12, 1, 1);
					tesselator.addVertexWithUV(var12, -100.0D, -var12, 1, 0);
					tesselator.addVertexWithUV(-var12, -100.0D, -var12, 0, 0);
					tesselator.draw();
					//That set of four lines below was here originally
					GL11.glPopMatrix();
				}
		// OUTER MOONS:
		if (moons!=null) {
			if (parent==null) {
				for (int i=0; i<moons.length; i++) {
					GL11.glScalef(0.6F, 0.6F, 0.6F);
					GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
					GL11.glRotatef(/*world.getCelestialAngle(partialTicks) * 360.0F +*/ centerAngleDegree(moons[i].getMapObject().getPhaseShift(), moons[i].getMapObject().getStretchValue()), 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(200F, /*1.0F*/20F, 0.0F, 0.0F);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
					var12 = 10.0F*((float)Math.atan(moons[i].getMapObject().getPlanetSize()*30F/(.27*moons[i].getMapObject().getDistanceFromCenter()*19.8F)))*180F/(3F*((float)Math.PI));
					FMLClientHandler.instance().getClient().renderEngine.bindTexture(moonTextures[i]);
					tesselator.startDrawingQuads();
					tesselator.addVertexWithUV(-var12, -100.0D, var12, 0, 1);
					tesselator.addVertexWithUV(var12, -100.0D, var12, 1, 1);
					tesselator.addVertexWithUV(var12, -100.0D, -var12, 1, 0);
					tesselator.addVertexWithUV(-var12, -100.0D, -var12, 0, 0);
					tesselator.draw();
					GL11.glPopMatrix();
					GL11.glPushMatrix();
				}
			} else {
				for (int i=0; i<moons.length; i++) {
					if (distanceToMoon(i, parentOrbit)>=popWorld.getDistanceFromCenter()) {
						GL11.glScalef(0.6F, 0.6F, 0.6F);
						GL11.glRotatef(parentRotation, 0.0F, 0.0F, 1.0F);
						GL11.glRotatef(200F, 1.0F, 0.0F, 0.0F);
						GL11.glRotatef(/*world.getCelestialAngle(partialTicks) * 360.0F + */angleM(i, worldAngleRad), 1.0F, 0.0F, 0.0F);
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
						var12 = 10.0F*((float)Math.atan(moons[i].getMapObject().getPlanetSize()/(.27*distanceToMoon(i, worldAngleRad)*19.8F)))*180F/(3F*((float)Math.PI));
						//var12 = 10.0F;
						FMLClientHandler.instance().getClient().renderEngine.bindTexture(moonTextures[i]);
						tesselator.startDrawingQuads();
						tesselator.addVertexWithUV(-var12, -100.0D, var12, 0, 1);
						tesselator.addVertexWithUV(var12, -100.0D, var12, 1, 1);
						tesselator.addVertexWithUV(var12, -100.0D, -var12, 1, 0);
						tesselator.addVertexWithUV(-var12, -100.0D, -var12, 0, 0);
						tesselator.draw();
						//That set of four lines below was here originally
						GL11.glPopMatrix();
						GL11.glPushMatrix();
					}
				}
			}
		}
		// PARENT:
		if (parent!=null && parentTexture!=null) {
			GL11.glScalef(0.6F, 0.6F, 0.6F);
			//GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(parentRotation, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(200F, 1.0F, 0.0F, 0.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
			var12 = 10.0F*((float)Math.atan(parent.getMapObject().getPlanetSize()*30F/(popWorld.getDistanceFromCenter()*19.8F)))*180F/(3F*((float)Math.PI));
			FMLClientHandler.instance().getClient().renderEngine.bindTexture(parentTexture);
			tesselator.startDrawingQuads();
			tesselator.addVertexWithUV(-var12, -100.0D, var12, 0, 1);
			tesselator.addVertexWithUV(var12, -100.0D, var12, 1, 1);
			tesselator.addVertexWithUV(var12, -100.0D, -var12, 1, 0);
			tesselator.addVertexWithUV(-var12, -100.0D, -var12, 0, 0);
			tesselator.draw();
			//That set of four lines below was here originally
			GL11.glPopMatrix();
			GL11.glPushMatrix();
		}
		// INNER MOONS:
		if (moons!=null && parent!=null)
			for (int i=0; i<moons.length; i++)
				if (distanceToMoon(i, parentOrbit)<popWorld.getDistanceFromCenter()) {
					GL11.glScalef(0.6F, 0.6F, 0.6F);
					GL11.glRotatef(parentRotation, 0.0F, 0.0F, 1.0F);
					GL11.glRotatef(200F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(/*world.getCelestialAngle(partialTicks) * 360.0F + */angleM(i, worldAngleRad), 1.0F, 0.0F, 0.0F);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
					var12 = 10.0F*((float)Math.atan(moons[i].getMapObject().getPlanetSize()/(.27*distanceToMoon(i, worldAngleRad)*19.8F)))*180F/(3F*((float)Math.PI));
					//var12 = 10.0F;
					FMLClientHandler.instance().getClient().renderEngine.bindTexture(moonTextures[i]);
					tesselator.startDrawingQuads();
					tesselator.addVertexWithUV(-var12, -100.0D, var12, 0, 1);
					tesselator.addVertexWithUV(var12, -100.0D, var12, 1, 1);
					tesselator.addVertexWithUV(var12, -100.0D, -var12, 1, 0);
					tesselator.addVertexWithUV(-var12, -100.0D, -var12, 0, 0);
					tesselator.draw();
					//That set of four lines below was here originally
					GL11.glPopMatrix();
					GL11.glPushMatrix();
				}
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_ALPHA_TEST);//TODO: I have to figure out if these should be in the if statement or not. If second sun renders funny, they probably should be outside
		GL11.glEnable(GL11.GL_FOG);

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3f(0.0F, 0.0F, 0.0F);
		final double skyColor5 = mc.thePlayer.getPosition(partialTicks).yCoord/*/popWorld.getPlanetSize()*/ - world.getHorizon();
		//TODO: What in the world is this doing? I need to figure out what's part of drawing the Earth, and what isn't (perhaps I hsould compare the Moon and Mars sky providers...) (Also, my guess is this is the skybox...)
		//TODO: Once I've figured out that, add in rendering of moons. Make sure they're after all planet-related stuff is done
		//TODO: Hmm, figure out if I can use display lists anywhere to make this more efficient? (after all, drawing the planet this is orbiting, if this is a moon, won't vary by much
		if (skyColor5 < 0.0D) {
			GL11.glTranslatef(0.0F, 12.0F, 0.0F);
			GL11.glCallList(this.glSkyList2);
			GL11.glPopMatrix();
			var10 = 1.0F;
			var11 = -((float) (skyColor5 + 65.0D));
			var12 = -var10;
			tesselator.startDrawingQuads();
			tesselator.setColorRGBA_I(0, 255);
			tesselator.addVertex(-var10, var11, var10);
			tesselator.addVertex(var10, var11, var10);
			tesselator.addVertex(var10, var12, var10);
			tesselator.addVertex(-var10, var12, var10);
			tesselator.addVertex(-var10, var12, -var10);
			tesselator.addVertex(var10, var12, -var10);
			tesselator.addVertex(var10, var11, -var10);
			tesselator.addVertex(-var10, var11, -var10);
			tesselator.addVertex(var10, var12, -var10);
			tesselator.addVertex(var10, var12, var10);
			tesselator.addVertex(var10, var11, var10);
			tesselator.addVertex(var10, var11, -var10);
			tesselator.addVertex(-var10, var11, -var10);
			tesselator.addVertex(-var10, var11, var10);
			tesselator.addVertex(-var10, var12, var10);
			tesselator.addVertex(-var10, var12, -var10);
			tesselator.addVertex(-var10, var12, -var10);
			tesselator.addVertex(-var10, var12, var10);
			tesselator.addVertex(var10, var12, var10);
			tesselator.addVertex(var10, var12, -var10);
			tesselator.draw();
			GL11.glPushMatrix();//B
		}

		GL11.glColor3f(70F / 256F, 70F / 256F, 70F / 256F);
		
		//GL11.glPushMatrix(); //Moved to spot marked B
		GL11.glTranslatef(0.0F, -((float) (skyColor5 - 16.0D)), 0.0F);
		GL11.glCallList(this.glSkyList2);
		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDepthMask(true);
	}
	
	private void renderStars() {
		final Random rand = new Random(starSeed);
		final Tessellator tesselator = Tessellator.instance;
		tesselator.startDrawingQuads();

		for (int var3 = 0; var3 < (true ? 35000*3 : 6000); ++var3) {//TODO: Change number of stars based on thickness of atmosphere, also fix below, there are a few more references to this condition
			double var4 = rand.nextFloat() * 2.0F - 1.0F;
			double var6 = rand.nextFloat() * 2.0F - 1.0F;
			double var8 = rand.nextFloat() * 2.0F - 1.0F;
			final double var10 = 0.15F + rand.nextFloat() * 0.1F;
			double var12 = var4 * var4 + var6 * var6 + var8 * var8;

			if (var12 < 1.0D && var12 > 0.01D) {
				var12 = 1.0D / Math.sqrt(var12);
				var4 *= var12;
				var6 *= var12;
				var8 *= var12;
				final double var14 = var4 * (true ? rand.nextDouble() * 150D + 130D : 100.0D);
				final double var16 = var6 * (true ? rand.nextDouble() * 150D + 130D : 100.0D);
				final double var18 = var8 * (true ? rand.nextDouble() * 150D + 130D : 100.0D);
				final double var20 = Math.atan2(var4, var8);
				final double var22 = Math.sin(var20);
				final double var24 = Math.cos(var20);
				final double var26 = Math.atan2(Math.sqrt(var4 * var4 + var8 * var8), var6);
				final double var28 = Math.sin(var26);
				final double var30 = Math.cos(var26);
				final double var32 = rand.nextDouble() * Math.PI * 2.0D;
				final double var34 = Math.sin(var32);
				final double var36 = Math.cos(var32);

				for (int var38 = 0; var38 < 4; ++var38) {
					final double var39 = 0.0D;
					final double var41 = ((var38 & 2) - 1) * var10;
					final double var43 = ((var38 + 1 & 2) - 1) * var10;
					final double var47 = var41 * var36 - var43 * var34;
					final double var49 = var43 * var36 + var41 * var34;
					final double var53 = var47 * var28 + var39 * var30;
					final double var55 = var39 * var28 - var47 * var30;
					final double var57 = var55 * var22 - var49 * var24;
					final double var61 = var49 * var22 + var55 * var24;
					tesselator.addVertex(var14 + var57, var16 + var53, var18 + var61);
				}
			}
		}
		tesselator.draw();
	}

	private Vec3 getCustomSkyColor() {
		//TODO: Put code here to change color based on atmosphere
		return Vec3.fakePool.getVecFromPool(/*0.26796875D*/.56756213D, /*0.1796875D*/.7F, 0.0D);
	}

	public float getSkyBrightness(float par1) {
		final float var2 = FMLClientHandler.instance().getClient().theWorld.getCelestialAngle(par1);
		float var3 = 1.0F - (MathHelper.sin(var2 * (float) Math.PI * 2.0F) * 2.0F + 0.25F);

		if (var3 < 0.0F)
			var3 = 0.0F;

		if (var3 > 1.0F)
			var3 = 1.0F;

		return var3 * var3 * 1F;
	}
}
