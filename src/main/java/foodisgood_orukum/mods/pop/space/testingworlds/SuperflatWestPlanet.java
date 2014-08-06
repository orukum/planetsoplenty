package foodisgood_orukum.mods.pop.space.testingworlds;

import foodisgood_orukum.mods.pop.space.*;
import foodisgood_orukum.mods.pop.PlanetsOPlenty;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraftforge.common.DimensionManager;
import micdoodle8.mods.galacticraft.api.world.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import micdoodle8.mods.galacticraft.core.GalacticraftCore;
import micdoodle8.mods.galacticraft.core.entities.GCCoreEntityCreeper;
import micdoodle8.mods.galacticraft.core.entities.GCCoreEntitySkeleton;
import micdoodle8.mods.galacticraft.core.entities.GCCoreEntitySpider;
import micdoodle8.mods.galacticraft.core.entities.GCCoreEntityZombie;
import micdoodle8.mods.galacticraft.core.perlin.NoiseModule;
import micdoodle8.mods.galacticraft.core.perlin.generator.Gradient;
import micdoodle8.mods.galacticraft.core.world.gen.GCCoreCraterSize;
import micdoodle8.mods.galacticraft.core.world.gen.GCCoreMapGenBaseMeta;
import micdoodle8.mods.galacticraft.core.world.gen.dungeon.GCCoreMapGenDungeon;
import micdoodle8.mods.galacticraft.moon.GCMoonConfigManager;
import micdoodle8.mods.galacticraft.moon.blocks.GCMoonBlock;
import micdoodle8.mods.galacticraft.moon.world.gen.GCMoonGenCaves;
import micdoodle8.mods.galacticraft.moon.world.gen.dungeon.GCMoonRoomBoss;
import micdoodle8.mods.galacticraft.moon.world.gen.dungeon.GCMoonRoomChests;
import micdoodle8.mods.galacticraft.moon.world.gen.dungeon.GCMoonRoomEmpty;
import micdoodle8.mods.galacticraft.moon.world.gen.dungeon.GCMoonRoomSpawner;
import micdoodle8.mods.galacticraft.moon.world.gen.dungeon.GCMoonRoomTreasure;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSand;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraftforge.common.ForgeDirection;    

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.world.ChunkPosition;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;

public class SuperflatWestPlanet extends POPPlanet {
	public SuperflatWestPlanet(int dimension) {
		super();
		dimensionId = dimension;
	}
	
	public SuperflatWestPlanet() {}
	
	@Override
	public String getName() {
		return "Superflat West Test Planet";
	}

	@Override
	public boolean addToList() {
		return true;
	}

	@Override
	public boolean autoRegister() {
		return true;
	}

	@Override
	public Class<? extends WorldProvider> getWorldProvider() {
		return SuperflatEastPlanet.class;
	}

	@Override
	public IGalaxy getParentGalaxy() {
		return POPCelestials.westGalaxy;
		//return GalacticraftCore.galaxyMilkyWay;
	}

	@Override
	public float getPlanetSize() {
		return .373F;
	}

	@Override
	public float getDistanceFromCenter() {
		return .27F;
	}

	@Override
	public float getPhaseShift() {
		return 7F;
	}

	@Override
	public float getStretchValue() {
		return 1F/.6F;
	}

	@Override
	public ResourceLocation getPlanetSprite() {
		return new ResourceLocation(PlanetsOPlenty.TEXTURE_DOMAIN, "textures/planets/westsuperflat.png");
	}
	
	@Override
	public float getGravity() {
		return POPPlanet.getGCGravityFactor(.0008F);
	}
	
	@Override
	public double getMeteorFrequency() {
		return 1D;
	}
	
	@Override
	public boolean canSpaceshipTierPass(int tier) {
		return tier>3;
	}
	
	@Override
	public float getSoundVolReductionAmount() {
		return 9.3F;
	}
	
    /*@Override
    public void setDimension(int var1)
    {
        this.dimensionId = var1;
        super.setDimension(var1);
    }

    @Override
    protected void generateLightBrightnessTable()
    {
        final float var1 = 0.0F;

        for (int var2 = 0; var2 <= 15; ++var2)
        {
            final float var3 = 1.0F - var2 / 15.0F;
            this.lightBrightnessTable[var2] = (1.0F - var3) / (var3 * 3.0F + 1.0F) * (1.0F - var1) + var1;
        }
    }

    @Override
    public float[] calcSunriseSunsetColors(float var1, float var2)
    {
        return null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Vec3 getFogColor(float var1, float var2)
    {
        return this.worldObj.getWorldVec3Pool().getVecFromPool((double) 0F / 255F, (double) 0F / 255F, (double) 0F / 255F);
    }

    @Override
    public Vec3 getSkyColor(Entity cameraEntity, float partialTicks)
    {
        return this.worldObj.getWorldVec3Pool().getVecFromPool(0, 0, 0);
    }

    @Override
    public float calculateCelestialAngle(long par1, float par3)
    {
        final int var4 = (int) (par1 % 192000L);
        float var5 = (var4 + par3) / 192000.0F - 0.25F;

        if (var5 < 0.0F)
        {
            ++var5;
        }

        if (var5 > 1.0F)
        {
            --var5;
        }

        final float var6 = var5;
        var5 = 1.0F - (float) ((Math.cos(var5 * Math.PI) + 1.0D) / 2.0D);
        var5 = var6 + (var5 - var6) / 3.0F;
        return var5;
    }

    @Override
    public double getHorizon()
    {
        return 44.0D;
    }

    @Override
    public int getAverageGroundLevel()
    {
        return 44;
    }

    @Override
    public boolean canRespawnHere()
    {
        return !GCCoreConfigManager.forceOverworldRespawn;
    }

    @Override
    public boolean canDoLightning(Chunk chunk)
    {
        return false;
    }

    @Override
    public boolean canDoRainSnowIce(Chunk chunk)
    {
        return false;
    }*/
	
	@Override
    @SideOnly(Side.CLIENT)
    public float getStarBrightness(float par1)
    {
        final float var2 = this.worldObj.getCelestialAngle(par1);
        float var3 = 1.0F - (MathHelper.cos(var2 * (float) Math.PI * 2.0F) * 2.0F + 0.25F);

        if (var3 < 0.0F)
            var3 = 0.0F;
        else if (var3 > 1.0F)
            var3 = 1.0F;

        return var3 * var3 * 0.5F + 0.3F;
    }

    @Override
    public void registerWorldChunkManager()
    {
        this.worldChunkMgr = new POPSuperflatWestChunkManager();
    }

    @Override
    public IChunkProvider createChunkGenerator()
    {
        return new POPSuperflatWestChunkProvider(this.worldObj, this.worldObj.getSeed(), true);
    }

    @Override
    public boolean isSkyColored()
    {
        return false;
    }

    @Override
    public String getWelcomeMessage()
    {
        return "Welcome to West Superflat Test Planet!";
    }

    @Override
    public String getDepartMessage()
    {
        return "Goodbye!";
    }

    @Override
    public boolean canSnowAt(int x, int y, int z)
    {
        return y>170 || x<-40;
    }

    @Override
    public boolean canBlockFreeze(int x, int y, int z, boolean byWater)
    {
    	int id = worldObj.getBlockId(x, y, z);
        return id==Block.waterMoving.blockID || id==Block.waterStill.blockID || (byWater && (id==Block.lavaMoving.blockID || id==Block.lavaStill.blockID));
    }

    @Override
    public int getHeight()
    {
        return 1234;
    }
    
    public class POPSuperflatWestChunkManager extends WorldChunkManager
    {

        private double randFromPoint(int x, int z)
        {
            int n;
            n = x + z * 57;
            n = n << 13 ^ n;
            return 1.0 - (n * (n * n * 15731 + 789221) + 1376312589 & 0x7fffffff) / 1073741824.0;
        }
        @Override
        public BiomeGenBase getBiomeGenAt(int par1, int par2)
        {
        	return BiomeGenBase.desert;
        }
		
        @Override
        public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5)
        {
            /*if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < par4 * par5)
            {
                par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
            }

            Arrays.fill(par1ArrayOfBiomeGenBase, 0, par4 * par5, GCMoonBiomeGenBase.moonFlat);*/
            return par1ArrayOfBiomeGenBase;
        }

        @Override
        public float[] getTemperatures(float[] par1ArrayOfFloat, int x, int y, int width, int length)
        {
            if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < width * length)
            {
                par1ArrayOfFloat = new float[width * length];
            }/*
            for (int x2=x; x2<(x+width); x2++)
            	for (int y2=y; y2<(y+length); y2++)
            			par1ArrayOfFload*/
            if (isDaytime())
            	Arrays.fill(par1ArrayOfFloat, 0, width * length, 100);
            else
            	Arrays.fill(par1ArrayOfFloat, 0, width * length, 20);
            return par1ArrayOfFloat;
        }

        @Override
        public float[] getRainfall(float[] par1ArrayOfFloat, int par2, int par3, int par4, int par5)
        {
            if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < par4 * par5)
            {
                par1ArrayOfFloat = new float[par4 * par5];
            }

            Arrays.fill(par1ArrayOfFloat, 0, par4 * par5, 2);
            return par1ArrayOfFloat;
        }

        @Override
        public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5)
        {
            /*if (par1ArrayOfBiomeGenBase == null || par1ArrayOfBiomeGenBase.length < par4 * par5)
            {
                par1ArrayOfBiomeGenBase = new BiomeGenBase[par4 * par5];
            }

            //Arrays.fill(par1ArrayOfBiomeGenBase, 0, par4 * par5, GCMoonBiomeGenBase.moonFlat);*/
            return par1ArrayOfBiomeGenBase;
        }

        @Override
        public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] par1ArrayOfBiomeGenBase, int par2, int par3, int par4, int par5, boolean par6)
        {
            return this.loadBlockGeneratorData(par1ArrayOfBiomeGenBase, par2, par3, par4, par5);
        }

        /*@SuppressWarnings("rawtypes")
        @Override
        public ChunkPosition findBiomePosition(int par1, int par2, int par3, List par4List, Random par5Random)
        {
            return par4List.contains(GCMoonBiomeGenBase.moonFlat) ? new ChunkPosition(par1 - par3 + par5Random.nextInt(par3 * 2 + 1), 0, par2 - par3 + par5Random.nextInt(par3 * 2 + 1)) : null;
        }*/

        @SuppressWarnings("rawtypes")
        @Override
        public boolean areBiomesViable(int par1, int par2, int par3, List par4List)
        {
        	return true;
            //return par4List.contains(GCMoonBiomeGenBase.moonFlat);
        }
    }
    
    public class POPSuperflatWestChunkProvider extends ChunkProviderGenerate
    {
        /*final short topBlockIDHigh = (short) Block.grass.blockID;
        final byte topBlockMetaHigh = 0;
        final short fillBlockIDHigh = (short) Block.anvil.blockID;
        final byte fillBlockMetaHigh = 0;
        final short lowerBlockIDHigh = (short) Block.glass.blockID;
        final byte lowerBlockMetaHigh = 2;
        final short topBlockIDLow = (short) Block.cake.blockID;
        final byte topBlockMetaLow = 0;
        final short fillBlockIDLow = (short) Block.stone.blockID;
        final byte fillBlockMetaLow = 0;
        final short lowerBlockIDLow = (short) Block.blockEmerald.blockID;
        final byte lowerBlockMetaLow = 3;*/
    	final short blockID = (short) Block.sandStone.blockID;
    	final byte blockMeta = 0;

        private final Random rand;

        private final NoiseModule noiseGen1;
        private final NoiseModule noiseGen2;
        private final NoiseModule noiseGen3;
        private final NoiseModule noiseGen4;

        private final World worldObj;

        //private BiomeGenBase[] biomesForGeneration = { GCMoonBiomeGenBase.moonFlat };

        private final GCCoreMapGenBaseMeta caveGenerator = new GCMoonGenCaves();

        // DO NOT CHANGE
        private static final int MID_HEIGHT = 120;
        private static final int CHUNK_SIZE_X = 16;
        private static final int CHUNK_SIZE_Y = 128;
        private static final int CHUNK_SIZE_Z = 16;
        private static final int CRATER_PROB = 100;

        public POPSuperflatWestChunkProvider(World par1World, long par2, boolean par4)
        {
            super(par1World, par2, par4);
            this.worldObj = par1World;
            this.rand = new Random(par2);
            this.noiseGen1 = new Gradient(this.rand.nextLong(), 4, 0.25);
            this.noiseGen2 = new Gradient(this.rand.nextLong(), 4, 0.25);
            this.noiseGen3 = new Gradient(this.rand.nextLong(), 1, 0.25);
            this.noiseGen4 = new Gradient(this.rand.nextLong(), 1, 0.25);
        }

        public void generateTerrain(int chunkX, int chunkZ, short[] idArray, byte[] metaArray)
        {
            this.noiseGen1.frequency = 0.0125;
            this.noiseGen2.frequency = 0.015;
            this.noiseGen3.frequency = 0.01;
            this.noiseGen4.frequency = 0.02;
            for (int x = 0; x < CHUNK_SIZE_X; x++) {
                for (int z = 0; z < CHUNK_SIZE_Z; z++) {
                    final double d = this.noiseGen1.getNoise(x + chunkX * 16, z + chunkZ * 16) * 8;
                    final double d2 = this.noiseGen2.getNoise(x + chunkX * 16, z + chunkZ * 16) * 24;
                    double d3 = this.noiseGen3.getNoise(x + chunkX * 16, z + chunkZ * 16) - 0.1;
                    d3 *= 4;

                    double yDev = 0;

                    if (d3 < 0.0D)
                    {
                        yDev = d;
                    }
                    else if (d3 > 1.0D)
                    {
                        yDev = d2;
                    }
                    else
                    {
                        yDev = d + (d2 - d) * d3;
                    }
                    yDev*=MID_HEIGHT*.59;
                    if (((((chunkX/2)+(chunkZ/2)+2)%7==4) && (Math.abs(this.randFromPoint(chunkX, chunkZ))%178)<4)) {
	                    for (int y = 0; y < 33; y++)
	                    {
                            idArray[this.getIndex(x, y, z)] = this.blockID;
                            metaArray[this.getIndex(x, y, z)] = this.blockMeta;
	                    }
	                    for (int y = 0; y < CHUNK_SIZE_Y; y++)
	                    {
	                        if (y < MID_HEIGHT + yDev-12 && y<256)
	                        {
		                    	short id;
	                    		id = (short) (Math.abs(rand.nextInt())%175);
	                    		switch (id) {
	                    		case 55: case 76: case 75: case 93: case 94: case 149: case 150: case 151: case 141: case 142: case 152: case 132: case 131: case 157: case 148: case 147: case 146: case 77: case 69: case 28:
	                    			id = 0;
	                    		default:
	                    			if (Block.blocksList[id]==null)
	                    				id = 0;
	                    		}
	                            idArray[this.getIndex(x, y, z)] = id;
	                            metaArray[this.getIndex(x, y, z)] = 0;
	                        } else
	                        	break;
	                    }
                    } else {
	                    for (int y = 0; y < CHUNK_SIZE_Y; y++)
	                    {
	                        if (y < MID_HEIGHT + yDev && y<256)
	                        {
	                            idArray[this.getIndex(x, y, z)] = this.blockID;
	                            metaArray[this.getIndex(x, y, z)] = this.blockMeta;
	                        } else
	                        	break;
	                    }
                    }
                }
            }
        }

        @Override
        public Chunk provideChunk(int par1, int par2)
        {
            this.rand.setSeed(par1 * 341873128712L + par2 * 132897987541L);
            final short[] ids = new short[32768 * 2];
            final byte[] meta = new byte[32768 * 2];
            Arrays.fill(ids, (short)0);
            Arrays.fill(meta, (byte)0);
            this.generateTerrain(par1, par2, ids, meta);
            //this.replaceBlocksForBiome(par1, par2, ids, meta);
            this.createCraters(par1, par2, ids, meta);
            this.caveGenerator.generate(this, this.worldObj, par1, par2, ids, meta);
            if (Math.abs(par1+par2)<5)
            	for (int x=0; x<CHUNK_SIZE_X; x++)
                	for (int z=0; z<CHUNK_SIZE_Z; z++)
                		ids[this.getIndex(x, 1, z)] = (short) Block.bedrock.blockID;
            final Chunk var4 = new Chunk(this.worldObj, ids, meta, par1, par2);

            // if (!var4.isTerrainPopulated &&
            // GCCoreConfigManager.disableExternalModGen)
            // {
            // var4.isTerrainPopulated = true;
            // }

            var4.generateSkylightMap();
            return var4;
        }

        public void createCraters(int chunkX, int chunkZ, short[] chunkArray, byte[] metaArray) {
        	if (chunkX<8)
        		return;
            for (int cx = chunkX - 2; cx <= chunkX + 2; cx++)
            {
                for (int cz = chunkZ - 2; cz <= chunkZ + 2; cz++)
                {
                    for (int x = 0; x < CHUNK_SIZE_X; x++)
                    {
                        for (int z = 0; z < CHUNK_SIZE_Z; z++)
                        {
                            if (Math.abs(this.randFromPoint(cx * 16 + x, (cz * 16 + z) * 1000)) < this.noiseGen4.getNoise(x * CHUNK_SIZE_X + x, cz * CHUNK_SIZE_Z + z) / CRATER_PROB)
                            {
                                final Random random = new Random(cx * 16 + x + (cz * 16 + z) * 5000);
                                final GCCoreCraterSize cSize = GCCoreCraterSize.sizeArray[random.nextInt(GCCoreCraterSize.sizeArray.length)];
                                final int size = random.nextInt(cSize.MAX_SIZE - cSize.MIN_SIZE) + cSize.MIN_SIZE;
                                this.makeCrater(cx * 16 + x, cz * 16 + z, chunkX * 16, chunkZ * 16, size, chunkArray, metaArray);
                            }
                        }
                    }
                }
            }
        }

        public void makeCrater(int craterX, int craterZ, int chunkX, int chunkZ, int size, short[] chunkArray, byte[] metaArray)
        {
            for (int x = 0; x < CHUNK_SIZE_X; x++)
            {
                for (int z = 0; z < CHUNK_SIZE_Z; z++)
                {
                    double xDev = craterX - (chunkX + x);
                    double zDev = craterZ - (chunkZ + z);
                    if (xDev * xDev + zDev * zDev < size * size)
                    {
                        xDev /= size;
                        zDev /= size;
                        final double sqrtY = xDev * xDev + zDev * zDev;
                        double yDev = sqrtY * sqrtY * 6;
                        yDev = 5 - yDev;
                        int helper = 0;
                        for (int y = 127; y > 0; y--)
                        {
                            if (chunkArray[this.getIndex(x, y, z)] != 0 && helper <= yDev)
                            {
                                chunkArray[this.getIndex(x, y, z)] = 0;
                                metaArray[this.getIndex(x, y, z)] = 0;
                                helper++;
                            }
                            if (helper > yDev)
                            {
                                break;
                            }
                        }
                    }
                }
            }
        }

        @Override
        public boolean chunkExists(int par1, int par2)
        {
            return true;
        }

        @Override
        public boolean unloadQueuedChunks()
        {
            return false;
        }

        @Override
        public int getLoadedChunkCount()
        {
            return 0;
        }

        private int getIndex(int x, int y, int z)
        {
            return y << 8 | z << 4 | x;
        }

        private double randFromPoint(int x, int z)
        {
            int n;
            n = x + z * 57;
            n = n << 13 ^ n;
            return 1.0 - (n * (n * n * 15731 + 789221) + 1376312589 & 0x7fffffff) / 1073741824.0;
        }

        @Override
        public void populate(IChunkProvider par1IChunkProvider, int par2, int par3)
        {
            BlockSand.fallInstantly = false;
            /*final int var4 = par2 * 16;
            final int var5 = par3 * 16;
            this.worldObj.getBiomeGenForCoords(var4 + 16, var5 + 16);
            this.rand.setSeed(this.worldObj.getSeed());
            final long var7 = this.rand.nextLong() / 2L * 2L + 1L;
            final long var9 = this.rand.nextLong() / 2L * 2L + 1L;
            this.rand.setSeed(par2 * var7 + par3 * var9 ^ this.worldObj.getSeed());*/

            /*if (!GCMoonConfigManager.disableMoonVillageGen)
            {
                this.villageGenerator.generateStructuresInChunk(this.worldObj, this.rand, par2, par3);
            }*/

            //this.decoratePlanet(this.worldObj, this.rand, var4, var5);
            //BlockSand.fallInstantly = false;
        }

        @Override
        public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate)
        {
            return true;
        }

        @Override
        public boolean canSave()
        {
            return true;
        }

        @Override
        public String makeString()
        {
        	//TODO: What in the world does this do?
        	return "RandomLevelSource";
            //return GCMoonConfigManager.generateOtherMods ? "RandomLevelSource" : "MoonLevelSource";
        }

        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int i, int j, int k)
        {
            if (par1EnumCreatureType == EnumCreatureType.monster)
            {
                final List monsters = new ArrayList();
                monsters.add(new SpawnListEntry(GCCoreEntityZombie.class, 8, 2, 3));
                monsters.add(new SpawnListEntry(GCCoreEntitySpider.class, 8, 2, 3));
                monsters.add(new SpawnListEntry(GCCoreEntitySkeleton.class, 8, 2, 3));
                monsters.add(new SpawnListEntry(GCCoreEntityCreeper.class, 8, 2, 3));
                return monsters;
            }
            else
            {
                return null;
            }
        }

        @Override
        public void recreateStructures(int par1, int par2)
        {
            /*if (!GCMoonConfigManager.disableMoonVillageGen)
            {
                //this.villageGenerator.generate(this, this.worldObj, par1, par2, (byte[]) null);
            }*/
        }
    }

	@Override
	public double getYCoordinateToTeleport() {
		return 2000;
	}

	@Override
	public boolean forceStaticLoad() {
		return false;
	}
}
