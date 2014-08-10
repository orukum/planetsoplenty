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

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
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
	
	public final POPWorld popWorld;
	
	public final float distanceToStar(int starIndex) {
		return 1F;//TODO: temp
	}
	
	/**
	 * The angle from the center of the galaxy at which a given star appears.
	 * @param starIndex the index of the sun whose angle should be compared
	 * @param galaxyAngle the result of getCelestialAngle for popWorld
	 */
	public final float angleS(int starIndex, float galaxyAngle) {
		return 0F; //TODO: temp
	}
	
	/**
	 * The angle from the center of the galaxy at which a given planet appears.
	 * @param planetIndex the index of the planet whose angle should be returned
	 * @param galaxyAngle the result of getCelestialAngle for popWorld
	 */
	public final float angleP(int planetIndex, float galaxyAngle) {
		return 0F; //TODO: temp
	}
	
	/**
	 * The angle from the center of the galaxy at which a given moon appears
	 * @param planetIndex the index of the moon whose angle should be returned
	 * @param galaxyAngle the result of getCelestialAngle for popWorld
	 */
	public final float angleM(int planetIndex, float galaxyAngle) {
		return 0F; //TODO: temp
	}
	
	//protected static final strictfp synchronized void foo() {}

	/**
	 * Note this should only be created after the galaxy itself, and all its contents, are properly configured. (It retrieves the parent galaxy and makes a list of moons and planets on construction.)
	 * @param worldObj The POPWorld whose sky is being provided
	 */
	public POPSkyProvider(POPWorld worldObj) {
		popWorld = worldObj;
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
			} else {
				parent = null;
				parentTexture = null;
			}
			for (int i=0; i<bodies.size(); i++)
				if (bodies.get(i).getMapObject().getParentGalaxy()==galaxy && bodies.get(i)!=popWorld) {
					if (bodies.get(i) instanceof IMoon) {
						if (popWorld instanceof POPMoon) {
							if (((IMoon) bodies.get(i)).getParentPlanet() == parent)
								moonsTemp.add((IMoon)bodies.get(i));
						} else {
							if (((IMoon) bodies.get(i)).getParentPlanet() == popWorld)
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
			if (moonsTemp.size()>0) {
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

		final float skyColor0 = popWorld.getStarBrightness(partialTicks);

		if (skyColor0 > 0.0F) {
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, skyColor0);
			GL11.glCallList(this.starGLCallList);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
		}

		GL11.glPushMatrix();

		GL11.glDisable(GL11.GL_BLEND);

		GL11.glEnable(GL11.GL_TEXTURE_2D);

		// Distant Galaxy:
		var12 = 10.5F;
		GL11.glRotatef(0.0F, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(150F, 1.0F, 0.0F, 0.0F);
		FMLClientHandler.instance().getClient().renderEngine.bindTexture(POPSkyProvider.galaxyTexture);
		GL11.glColor4f(0.4F, 0.4F, 0.4F, 1.0F);
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

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

		GL11.glPushMatrix();

		// Suns:
		if (suns==null || suns.length==1)
			if (sunTextures!=null) {
				GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
				var12 = ((suns[0].getDistanceFromCenter()==0) ? 30.0F : 30.0F*((float)Math.atan(suns[0].getSize()/(distanceToStar(0)*19.8F)))/(3F*((float)Math.PI)/180F));//Treating it like the normal sun is 30 degrees across, not sure if that's true
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
		else if (suns.length>1) {
			int order[] = new int[suns.length];//TODO: Hmm, allocating memory might be a bit slow. I'll want to try other methods
			float[] distances = new float[suns.length];
			for (int i=0; i<suns.length; i++)
				distances[i] = distanceToStar(i);
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
				for (int i=0; i<suns.length; i++)
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
			for (int i=0; i<suns.length; x=order[++i]) {
				GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glRotatef(world.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);//TODO: Adjust angle
				//var12 = 30.0F; //Looks like this is half of the size.
				var12 = ((suns[x].getDistanceFromCenter()==0) ? 30.0F : 30.0F*((float)Math.atan(suns[x].getSize()/(distances[x]*19.8F)))/(3F*((float)Math.PI)/180F));//Treating it like the normal sun is 3 degrees across, not sure if that's true
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
		GL11.glDisable(GL11.GL_BLEND);
		// HOME:
		if (parent!=null && parentTexture!=null) {
			GL11.glScalef(0.6F, 0.6F, 0.6F);
			GL11.glRotatef(40.0F, 0.0F, 0.0F, 1.0F);
			GL11.glRotatef(200F, 1.0F, 0.0F, 0.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1F);
			var12 = 0.5F;
			FMLClientHandler.instance().getClient().renderEngine.bindTexture(parentTexture);
			tesselator.startDrawingQuads();
			tesselator.addVertexWithUV(-var12, -100.0D, var12, 0, 1);
			tesselator.addVertexWithUV(var12, -100.0D, var12, 1, 1);
			tesselator.addVertexWithUV(var12, -100.0D, -var12, 1, 0);
			tesselator.addVertexWithUV(-var12, -100.0D, -var12, 0, 0);
			tesselator.draw();
		
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_ALPHA_TEST);//TODO: I have to figure out if these should be in the if statement or not. If second sun renders funny, they probably should be outside
			GL11.glEnable(GL11.GL_FOG);
			GL11.glPopMatrix();
		}

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3f(0.0F, 0.0F, 0.0F);
		final double skyColor5 = mc.thePlayer.getPosition(partialTicks).yCoord/*/popWorld.getPlanetSize()*/ - world.getHorizon();
		//TODO: What in the world is this doing? I need to figure out what's part of drawing the Earth, and what isn't (perhaps I hsould compare the Moon and Mars sky providers...) (Also, my guess is this is the skybox...)
		//TODO: Once I've figured out that, add in rendering of moons. Make sure they're after all planet-related stuff is done
		//TODO: Hmm, figure out if I can use display lists anywhere to make this more efficient? (after all, drawing the planet this is orbiting, if this is a moon, won't vary by much
		if (skyColor5 < 0.0D)
		{
			GL11.glPushMatrix();
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
		}

		GL11.glColor3f(70F / 256F, 70F / 256F, 70F / 256F);

		GL11.glPushMatrix();
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

		for (int var3 = 0; var3 < (true ? 35000 : 6000); ++var3) //TODO: Change number of stars based on thickness of atmosphere, also fix below, there are a few more references to this condition
		{
			double var4 = rand.nextFloat() * 2.0F - 1.0F;
			double var6 = rand.nextFloat() * 2.0F - 1.0F;
			double var8 = rand.nextFloat() * 2.0F - 1.0F;
			final double var10 = 0.15F + rand.nextFloat() * 0.1F;
			double var12 = var4 * var4 + var6 * var6 + var8 * var8;

			if (var12 < 1.0D && var12 > 0.01D)
			{
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

				for (int var38 = 0; var38 < 4; ++var38)
				{
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
		return Vec3.fakePool.getVecFromPool(0.26796875D, 0.1796875D, 0.0D);
	}

	public float getSkyBrightness(float par1) {
		final float var2 = FMLClientHandler.instance().getClient().theWorld.getCelestialAngle(par1);
		float var3 = 1.0F - (MathHelper.sin(var2 * (float) Math.PI * 2.0F) * 2.0F + 0.25F);

		if (var3 < 0.0F)
		{
			var3 = 0.0F;
		}

		if (var3 > 1.0F)
		{
			var3 = 1.0F;
		}

		return var3 * var3 * 1F;
	}
}
