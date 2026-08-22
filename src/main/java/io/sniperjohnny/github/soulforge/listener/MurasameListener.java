package io.sniperjohnny.github.soulforge.listener;

import io.sniperjohnny.github.soulforge.SoulForge;
import io.sniperjohnny.github.soulforge.customItem.ToolKeys;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class MurasameListener implements Listener {

    private static final int MAX_CURSE_STACKS = 5;
    private static final int CURSE_INTERVAL_TICKS = 10;

    @EventHandler
    public void murasameDamageListener(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player p)) {
            return;
        }
        if (!isHoldingMurasame(p)) {
            return;
        }

        Entity hit = e.getEntity();

        if (hit instanceof Player) {
            return;
        }

        if (hit instanceof LivingEntity living) {
            startCurseSpread(living);
        }
    }

    private void startCurseSpread(LivingEntity entity) {
        if (entity.getPersistentDataContainer().has(ToolKeys.MURASAME_CURSE_ACTIVE, PersistentDataType.BYTE)) {
            return;
        }

        entity.getPersistentDataContainer().set(ToolKeys.MURASAME_CURSE_ACTIVE, PersistentDataType.BYTE, (byte) 1);
        beginCurseTask(entity);
    }

    private void beginCurseTask(LivingEntity entity) {
        Bukkit.getScheduler().runTaskTimer(SoulForge.getInstance(), task -> {
            if (!entity.isValid() || entity.isDead()) {
                task.cancel();
                return;
            }

            int stacks = entity.getPersistentDataContainer().getOrDefault(ToolKeys.MURASAME_CURSE_STACKS, PersistentDataType.INTEGER, 0);

            spawnInscription(entity);

            stacks++;
            entity.getPersistentDataContainer().set(ToolKeys.MURASAME_CURSE_STACKS, PersistentDataType.INTEGER, stacks);

            if (stacks >= MAX_CURSE_STACKS) {
                killCursedEntity(entity);
                task.cancel();
            }
        }, 0, CURSE_INTERVAL_TICKS);
    }

    private void spawnInscription(LivingEntity entity) {
        Location base = entity.getLocation().add(0, 1.0, 0);

        double x = (Math.random() - 0.5) * 0.6;
        double y = Math.random() * 1.4;
        double z = (Math.random() - 0.5) * 0.6;

        Location loc = base.clone().add(x, y, z);

        ArmorStand stand = loc.getWorld().spawn(loc, ArmorStand.class, as -> {
            as.setInvisible(true);
            as.setMarker(true);
            as.setCustomNameVisible(true);
            as.customName(Component.text("\u2726", NamedTextColor.DARK_RED).decoration(TextDecoration.BOLD, true));
            as.setGravity(false);
            as.setSmall(true);
        });

        Bukkit.getScheduler().runTaskTimer(SoulForge.getInstance(), task -> {
            if (!stand.isValid()) {
                task.cancel();
                return;
            }
            stand.teleport(stand.getLocation().add(0, 0.01, 0));
        }, 1, 1);

        Bukkit.getScheduler().runTaskLater(SoulForge.getInstance(), stand::remove, 30);
    }

    private void killCursedEntity(LivingEntity entity) {
        World world = entity.getWorld();

        world.spawnParticle(Particle.SOUL_FIRE_FLAME, entity.getLocation(), 40, 0.5, 1, 0.5, 0.02);
        world.spawnParticle(Particle.SOUL, entity.getLocation(), 40, 0.5, 1, 0.5, 0.02);

        entity.setHealth(0);
    }

    private boolean isHoldingMurasame(Player p) {
        ItemStack main = p.getInventory().getItemInMainHand();
        return main != null && main.hasItemMeta()
                && main.getItemMeta().getPersistentDataContainer().has(ToolKeys.MURASAME, PersistentDataType.BYTE);
    }
}
