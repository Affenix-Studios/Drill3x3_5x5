package io.sniperjohnny.github.soulforge.listener;

import io.sniperjohnny.github.soulforge.SoulForge;
import io.sniperjohnny.github.soulforge.customItem.ToolKeys;
import io.sniperjohnny.github.soulforge.souls.SoulManager;
import io.sniperjohnny.github.soulforge.util.SoulChat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.concurrent.ThreadLocalRandom;

public class SoulListener implements Listener {

    @EventHandler
    public void onMobDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Player) {
            return;
        }

        Player killer = entity.getKiller();
        if (killer == null) {
            return;
        }

        ItemStack item = killer.getInventory().getItemInMainHand();
        if (item == null || !item.hasItemMeta()
                || !item.getItemMeta().getPersistentDataContainer().has(ToolKeys.SCYTHE, PersistentDataType.BYTE)) {
            return;
        }

        spawnSkullEffects(entity);

        int boost = item.getItemMeta().getPersistentDataContainer()
                .getOrDefault(ToolKeys.SCYTHE_SOUL_BOOST, PersistentDataType.INTEGER, 0);
        double chance = SoulForge.getScytheChance() + boost;
        if (ThreadLocalRandom.current().nextDouble() * 100 >= chance) {
            return;
        }

        SoulManager.addSouls(killer, 1);
        int souls = SoulManager.getSouls(killer);
        killer.sendMessage(Component.text("You harvested a soul! You now have " + souls + " souls. ",
                        NamedTextColor.AQUA)
                .append(SoulChat.shopLink("Your Souls: " + souls)));
    }

    private void spawnSkullEffects(LivingEntity entity) {
        Location loc = entity.getLocation();
        World world = loc.getWorld();

        world.spawnParticle(Particle.ITEM, loc, 10, 0.4, 0.6, 0.4, 0.05,
                new ItemStack(Material.WITHER_SKELETON_SKULL));
        world.spawnParticle(Particle.SOUL, loc, 12, 0.3, 0.5, 0.3, 0.02);
        world.playSound(loc, Sound.ENTITY_WITHER_SKELETON_AMBIENT, 0.9f, 1.1f);
    }
}
