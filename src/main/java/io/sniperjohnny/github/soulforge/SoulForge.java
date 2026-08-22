package io.sniperjohnny.github.soulforge;

import io.papermc.paper.connection.PlayerConfigurationConnection;
import io.papermc.paper.event.connection.configuration.AsyncPlayerConnectionConfigureEvent;
import io.sniperjohnny.github.soulforge.commands.FortuneCommand;
import io.sniperjohnny.github.soulforge.commands.GivesoulsCommand;
import io.sniperjohnny.github.soulforge.commands.OpCustomItemGiver;
import io.sniperjohnny.github.soulforge.commands.ShowRecipesCommand;
import io.sniperjohnny.github.soulforge.commands.ShowRecipe3x3;
import io.sniperjohnny.github.soulforge.commands.ShowRecipe5x5;
import io.sniperjohnny.github.soulforge.commands.ShowRecipeTntPickaxe;
import io.sniperjohnny.github.soulforge.commands.SilktouchCommand;
import io.sniperjohnny.github.soulforge.commands.SoulsCommand;
import io.sniperjohnny.github.soulforge.commands.SoulsGiveCommand;
import io.sniperjohnny.github.soulforge.commands.SoulsShopCommand;
import io.sniperjohnny.github.soulforge.craftingRecipe.Recipe3x3;
import io.sniperjohnny.github.soulforge.craftingRecipe.Recipe5x5;
import io.sniperjohnny.github.soulforge.craftingRecipe.RecipeMurasame;
import io.sniperjohnny.github.soulforge.craftingRecipe.RecipeScythe;
import io.sniperjohnny.github.soulforge.craftingRecipe.RecipeTimberAxe;
import io.sniperjohnny.github.soulforge.craftingRecipe.RecipeTntPickaxe;
import io.sniperjohnny.github.soulforge.listener.BlockBreakListener;
import io.sniperjohnny.github.soulforge.listener.CraftListener;
import io.sniperjohnny.github.soulforge.listener.InventoryListener;
import io.sniperjohnny.github.soulforge.listener.MurasameListener;
import io.sniperjohnny.github.soulforge.listener.SoulListener;
import io.sniperjohnny.github.soulforge.listener.TimberAxeListener;
import io.sniperjohnny.github.soulforge.souls.SoulManager;
import net.kyori.adventure.resource.ResourcePackInfo;
import net.kyori.adventure.resource.ResourcePackRequest;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.net.URI;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public final class SoulForge extends JavaPlugin implements Listener {

    private static SoulForge instance;
    private static volatile ResourcePackInfo packInfo;

    /** Players whose configuration phase finished before the pack hash was computed. */
    private final Set<UUID> pendingPackPlayers = ConcurrentHashMap.newKeySet();

    @Override
    public void onLoad() {
        instance = this;
        saveDefaultConfig();
        // Merge any new default keys (e.g. the texturepack section) into an existing
        // config.yml from an older plugin version. saveDefaultConfig() never overwrites
        // a file that already exists, so without this an old config silently keeps
        // missing the texturepack keys and the pack stays disabled.
        getConfig().options().copyDefaults(true);
        saveConfig();
        getLogger().info("Reading config from " + new File(getDataFolder(), "config.yml").getAbsolutePath());
        initTexturePack();
    }

    private void initTexturePack() {
        packInfo = null;
        final boolean wanted = getConfig().getBoolean("texturepack.wanted", false);
        getLogger().info("texturepack.wanted = " + wanted);
        if (!wanted) {
            getLogger().info("Texture pack disabled. Set texturepack.wanted to true in the config.yml listed above, then restart the server.");
            return;
        }
        final String url = getConfig().getString("texturepack.url");
        final String id = getConfig().getString("texturepack.id");
        if (url == null || url.isBlank() || id == null || id.isBlank()) {
            getLogger().warning("Texture pack is enabled but no url or id is set in config.yml");
            return;
        }
        try {
            final UUID packId = UUID.fromString(id);
            final URI packUri = URI.create(url);
            getLogger().info("Loading texture pack from " + url);
            final CompletableFuture<ResourcePackInfo> future = ResourcePackInfo.resourcePackInfo()
                    .id(packId)
                    .uri(packUri)
                    .computeHashAndBuild();
            future.whenComplete((pack, error) -> {
                if (error != null) {
                    // Hash computation downloads the whole pack; if that fails, fall back to a
                    // pack without a hash so the client can still download and apply it.
                    getLogger().warning("Could not compute texture pack hash for " + url + ": "
                            + error.getMessage() + " - sending without hash.");
                    packInfo = ResourcePackInfo.resourcePackInfo().id(packId).uri(packUri).build();
                } else {
                    packInfo = pack;
                    getLogger().info("Texture pack ready, applying to " + pendingPackPlayers.size() + " waiting player(s).");
                }
                // Players who finished configuring before the pack was ready never received
                // it during the config phase, so send it to them once they are in-game.
                // (If the plugin was disabled in the meantime there is nobody to send to.)
                if (!isEnabled()) {
                    return;
                }
                Bukkit.getScheduler().runTask(this, () -> {
                    for (final UUID playerId : pendingPackPlayers) {
                        final Player player = Bukkit.getPlayer(playerId);
                        if (player != null && player.isOnline()) {
                            player.sendResourcePacks(ResourcePackRequest.resourcePackRequest().packs(packInfo));
                        }
                    }
                    pendingPackPlayers.clear();
                });
            });
        } catch (IllegalArgumentException e) {
            getLogger().warning("Invalid texture pack url or id in config.yml: " + e.getMessage());
        }
    }

    @EventHandler
    public void onConfigure(AsyncPlayerConnectionConfigureEvent e) {
        if (!isTexturePackWanted()) {
            return;
        }
        final PlayerConfigurationConnection connection = e.getConnection();
        if (packInfo == null) {
            // Pack hash is still being computed; it will be sent once it is ready.
            pendingPackPlayers.add(connection.getProfile().getId());
            return;
        }
        connection.getAudience().sendResourcePacks(ResourcePackRequest.resourcePackRequest().packs(packInfo));
    }


    @Override
    public void onEnable() {
        instance = this;
        SoulManager.load();

        getServer().addRecipe(Recipe3x3.register3x3recipe());
        getServer().addRecipe(Recipe5x5.register5x5recipe());
        getServer().addRecipe(RecipeTntPickaxe.registerTntPickaxeRecipe());
        getServer().addRecipe(RecipeScythe.registerScytheRecipe());
        getServer().addRecipe(RecipeTimberAxe.registerTimberAxeRecipe());
        getServer().addRecipe(RecipeMurasame.registerMurasameRecipe());

        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new SoulListener(), this);
        getServer().getPluginManager().registerEvents(new MurasameListener(), this);
        getServer().getPluginManager().registerEvents(new TimberAxeListener(), this);
        getServer().getPluginManager().registerEvents(new CraftListener(), this);

        getCommand("recipe").setExecutor(new ShowRecipesCommand());
        getCommand("opci").setExecutor(new OpCustomItemGiver());
        getCommand("5x5recipe").setExecutor(new ShowRecipe5x5());
        getCommand("3x3recipe").setExecutor(new ShowRecipe3x3());
        getCommand("tntrecipe").setExecutor(new ShowRecipeTntPickaxe());
        getCommand("silktouch").setExecutor(new SilktouchCommand());
        getCommand("fortune").setExecutor(new FortuneCommand());
        getCommand("souls").setExecutor(new SoulsCommand());
        getCommand("soulsshop").setExecutor(new SoulsShopCommand());
        getCommand("givesouls").setExecutor(new GivesoulsCommand());
        getCommand("soulsgive").setExecutor(new SoulsGiveCommand());
    }

    @Override
    public void onDisable() {
        SoulManager.save();
    }

    public static SoulForge getInstance() {
        return instance;
    }

    public static boolean isTexturePackWanted() {
        return instance != null && instance.getConfig().getBoolean("texturepack.wanted");
    }

    public static double getTntChance() {
        return instance != null ? instance.getConfig().getDouble("tntpickaxe.chance", 10.0) : 10.0;
    }

    public static double getScytheChance() {
        return instance != null ? instance.getConfig().getDouble("scythe.soul-chance", 2.0) : 2.0;
    }

    public static double getScytheSoulBoost() {
        return instance != null ? instance.getConfig().getDouble("scythe.soul-harvest-bonus", 3.0) : 3.0;
    }

    public static double getTimberCooldownSeconds() {
        return instance != null ? instance.getConfig().getDouble("timberaxe.cooldown-seconds", 10.0) : 10.0;
    }

    public static double getTimberMaxLogs() {
        return instance != null ? instance.getConfig().getDouble("timberaxe.max-tree-logs", 100.0) : 100.0;
    }

    public static int getShopCost(String path, int def) {
        return instance != null ? instance.getConfig().getInt(path, def) : def;
    }
}
