package foodisgood_orukum.mods.pop.entities;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import foodisgood_orukum.mods.pop.space.testingworlds.EastGalaxy;
import morph.api.Ability;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TinyBlaze extends EntityBlaze {
    public TinyBlaze(World par1World) {
        super(par1World);
        //this.isImmuneToFire = true;
        this.experienceValue = 10;
        setSize(height/14, width/14);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(9.0D);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setAttribute(100D);
        //this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setAttribute(100D);
        //this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(6.0D);
    }
    
    @Override
    public void onLivingUpdate() {
        if (!this.worldObj.isRemote) {
            if (this.isWet())
                this.attackEntityFrom(DamageSource.drown, 1.0F);
            if (this.getEntityToAttack() != null && this.getEntityToAttack().posY + (double)this.getEntityToAttack().getEyeHeight() > this.posY + (double)this.getEyeHeight())
                this.motionY += (0.30000001192092896D - this.motionY) * 0.30000001192092896D;
        }

        if (!this.onGround && this.motionY < 0.0D)
            this.motionY *= 0.7D;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        //this.dataWatcher.addObject(16, new Byte((byte)0));
    }
    
    @Override
    protected final String getLivingSound() {
        return null;
    }

    @Override
    public float getBrightness(float par1) {
        return 2F;//EastGalaxy.hiding ? 0F : 1.0F;
    }
    
    @Override
    public boolean isBurning() {
        return false;
    }

    /*@Override
    protected String getHurtSound() {
        return "mob.blaze.hit";
    }
    
    @Override
    protected final String getDeathSound() {
        return "mob.blaze.death";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float par1) {
        return 18000000;
    }*/

    @Override
    protected void attackEntity(Entity par1Entity, float par2)
    {
        if (this.attackTime <= 0 && par2 < 2.0F && par1Entity.boundingBox.maxY > this.boundingBox.minY && par1Entity.boundingBox.minY < this.boundingBox.maxY) {
            this.attackTime = 20;
            this.attackEntityAsMob(par1Entity);
        } else if (par2 < 30.0F) {
            if (this.attackTime == 0) {
            	EntityPlayer n = worldObj.getClosestVulnerablePlayerToEntity(this, 50D);
            	if (n!=null && !worldObj.isRemote) {
            		if (rand.nextInt(500)>77)
            			worldObj.createExplosion(this, n.posX, n.posY, n.posZ, (rand.nextInt(311)<4 ? .5F : 1F), rand.nextBoolean());
            		else {
            			Explosion e = new Explosion(worldObj, this, n.posX+10*MathHelper.sin(n.rotationYawHead*((float)Math.PI)/180F)*MathHelper.cos(n.rotationPitch*((float)Math.PI)/180F), n.posY+10*MathHelper.sin(n.rotationPitch*((float)Math.PI)/180F), n.posZ-20*MathHelper.cos(n.rotationYawHead*((float)Math.PI)/180F)*MathHelper.cos(n.rotationPitch*((float)Math.PI)/180F), 100);
            			e.isFlaming = rand.nextBoolean();
            			e.isSmoking = true;
            			e.doExplosionB(true);
            		}
            	}
            	this.hasAttacked = true;
            }
        }
    }
    
    /*@Override
    protected final void fall(float par1) {}*/
    
    @Override
    protected void dropFewItems(boolean par1, int par2) {
        if (par1) {
            int j = this.rand.nextInt(4 + par2);

            for (int k = 0; k < j; ++k)
                this.dropItem(Item.blazeRod.itemID, 1);
        }
    }

    /*@Override
    protected int getDropItemId() {
        return Item.blazeRod.itemID;
    }*/
    
    /*@Override
    public boolean func_70845_n() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }//TODO: What in the world do these do?

    public void func_70844_e(boolean par1) {
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);

        if (par1)
        {
            b0 = (byte)(b0 | 1);
        }
        else
        {
            b0 &= -2;
        }

        this.dataWatcher.updateObject(16, Byte.valueOf(b0));
    }*/
    
    /*@Override
    protected boolean isValidLightLevel() {
        return true;
    }*/
}
