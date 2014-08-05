package foodisgood_orukum.mods.pop.space.testingworlds;

import foodisgood_orukum.mods.pop.space.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.api.world.*;

public class WestGalaxy extends POPGalaxy {
	@Override
	public String getGalaxyName() {
		return "West Galaxy";
	}

	@Override
	public int getXCoord() {
		return -1;
	}

	@Override
	public int getYCoord() {
		return 2;
	}

	@Override
	public final Vector3 getRGBRingColors() {
        /*Class<?> clazz;
		try {
			clazz = Class.forName("micdoodle8.mods.galacticraft.api.vector.Vector.Vector3");
	        Constructor<?> c;
			try {
				c = clazz.getConstructor(Double.class, Double.class, Double.class);
		        try {
					return (Vector3) c.newInstance(2.0D / 256.0D, 256.0D / 256.0D, 6.0D / 256.0D);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			} catch (NoSuchMethodException | SecurityException e1) {
				e1.printStackTrace();
			}
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		}*/
		return new Vector3(2.0D / 256.0D, 256.0D / 256.0D, 6.0D / 256.0D);
	}
}
