package io.sniperjohnny.github.soulforge.souls;

import io.sniperjohnny.github.soulforge.SoulForge;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public final class SoulManager {

    public static final String TNT_FORTUNE = "tnt_fortune";
    public static final String TNT_SILKTOUCH = "tnt_silktouch";
    public static final String TNT_UNBREAKABLE = "tnt_unbreakable";
    public static final String TIMBER_COOLDOWN = "timber_cooldown";
    public static final String TIMBER_EFFICIENCY = "timber_efficiency";
    public static final String TIMBER_FORTUNE = "timber_fortune";
    public static final String TIMBER_SILKTOUCH = "timber_silktouch";
    public static final String SCYTHE_EFFICIENCY = "scythe_efficiency";
    public static final String SCYTHE_LOOTING = "scythe_looting";
    public static final String SCYTHE_SOUL_HARVEST = "scythe_soul_harvest";
    public static final String SCYTHE_SHARPNESS = "scythe_sharpness";
    public static final String MURASAME_CRAFT = "murasame_craft";

    private static File file;
    private static YamlConfiguration config;

    private SoulManager() {
    }

    public static void load() {
        file = new File(SoulForge.getInstance().getDataFolder(), "souls.yml");
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                SoulForge.getInstance().getLogger().severe("Could not create souls.yml: " + e.getMessage());
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static void save() {
        if (config == null || file == null) {
            return;
        }
        try {
            config.save(file);
        } catch (IOException e) {
            SoulForge.getInstance().getLogger().severe("Could not save souls.yml: " + e.getMessage());
        }
    }

    private static String playerPath(OfflinePlayer player) {
        return "souls." + player.getUniqueId();
    }

    public static int getSouls(OfflinePlayer player) {
        return config.getInt(playerPath(player) + ".souls");
    }

    public static void addSouls(OfflinePlayer player, int amount) {
        config.set(playerPath(player) + ".souls", getSouls(player) + amount);
        save();
    }

    public static boolean spendSouls(OfflinePlayer player, int amount) {
        int souls = getSouls(player);
        if (souls < amount) {
            return false;
        }
        config.set(playerPath(player) + ".souls", souls - amount);
        save();
        return true;
    }

    public static int getUnlockLevel(OfflinePlayer player, String key) {
        Object value = config.get(playerPath(player) + ".unlocks." + key);
        if (value instanceof Boolean b) {
            return b ? 1 : 0;
        }
        if (value instanceof Number n) {
            return n.intValue();
        }
        return 0;
    }

    public static boolean hasUnlock(OfflinePlayer player, String key) {
        return getUnlockLevel(player, key) > 0;
    }

    public static void setUnlockLevel(Player player, String key, int level) {
        config.set(playerPath(player) + ".unlocks." + key, level);
        save();
    }
}
