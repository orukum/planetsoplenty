package foodisgood_orukum.mods.pop.client.render.entity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.*;
import net.minecraft.client.renderer.entity.RenderBlaze;
import net.minecraft.entity.EntityLivingBase;

@SideOnly(Side.CLIENT)
public class RenderTinyBlaze extends RenderBlaze {
    public RenderTinyBlaze() {
        this.shadowSize *= 0.0F;
    }

    @Override
	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2) {
        GL11.glScalef(1F/10F, 1F/10F, 1F/10F);
    }
}