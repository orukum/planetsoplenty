package foodisgood_orukum.mods.pop;

import java.util.logging.Level;
import cpw.mods.fml.relauncher.FMLRelaunchLog;

public class POPLog
{
    public static void info(String message) {
        FMLRelaunchLog.log("Planets O Plenty", Level.INFO, message);
    }

    public static void severe(String message) {
        FMLRelaunchLog.log("Planets O Plenty", Level.SEVERE, message);
    }
}
