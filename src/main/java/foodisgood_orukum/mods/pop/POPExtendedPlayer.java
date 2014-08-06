package foodisgood_orukum.mods.pop;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class POPExtendedPlayer implements IExtendedEntityProperties {
	public static final String EXT_PROP_NAME = "POP Extended Player Tags";
	
	public final EntityPlayer player;
	
	public int example;
	//Store info here about what planets this player has discovered, etc
	
	public POPExtendedPlayer(EntityPlayer playerArg) {
		this.player = playerArg;
		//Initialize other variables here
	}
	
	public static final void register(EntityPlayer playerArg) {
		playerArg.registerExtendedProperties(EXT_PROP_NAME, new POPExtendedPlayer(playerArg));
	}
	
	public static final POPExtendedPlayer get(EntityPlayer player) {
		return (POPExtendedPlayer) player.getExtendedProperties(EXT_PROP_NAME);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		example = compound.getInteger("Example");
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		compound.setInteger("Example", example);
	}

	@Override
	public void init(Entity entity, World world) {
		// TODO Auto-generated method stub
	}
}
