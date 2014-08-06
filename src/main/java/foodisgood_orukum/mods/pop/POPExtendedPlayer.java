package foodisgood_orukum.mods.pop;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class POPExtendedPlayer implements IExtendedEntityProperties {
	public static final String EXT_PROP_NAME = "POP Extended Player Tags";
	
	public final EntityPlayer player;
	
	public int increment;
	//Store info here about what planets this player has discovered, etc
	
	public POPExtendedPlayer(EntityPlayer playerArg) {
		this.player = playerArg;
		//Initialize other variables here
		increment = 0;
	}
	
	public static final void register(EntityPlayer playerArg) {
		playerArg.registerExtendedProperties(EXT_PROP_NAME, new POPExtendedPlayer(playerArg));
	}
	
	public static final POPExtendedPlayer get(EntityPlayer player) {
		if (player.getExtendedProperties(EXT_PROP_NAME)==null) {
			register(player);
			POPLog.severe("[Planets O Plenty] Player " + player.username + " failed to have extended properties registered when joining server? Registering now in get()");
		}
		return (POPExtendedPlayer) player.getExtendedProperties(EXT_PROP_NAME);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		increment = compound.getInteger("DEBUG_Increment");
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		compound.setInteger("DEBUG_Increment", increment);
	}

	@Override
	public void init(Entity entity, World world) {
		// TODO Auto-generated method stub
	}
}
