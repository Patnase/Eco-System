package de.patnase.ecosystem;

import de.patnase.ecosystem.commands.MoneyCommand;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

public final class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onLoad(){
        instance = this;
    }
    @Override
    public void onEnable() {
        getCommand("money").setExecutor(new MoneyCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void run(){
        new BukkitRunnable(){
            @Override
            public void run(){
                Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage("§8§l--------§7{ §a§lLegendNetwork §7}§8§l--------\n\n§7[§6§lP§c§lP§3§lG§8 - §2Economy§7] §fDu hast soeben §21500§6$ §ferhalten"));
                Bukkit.getOnlinePlayers().forEach(player -> addMoney(player.getName(), 1500));
            }
        }.runTaskTimer(Main.getInstance(), 6000, 6000);
    }

    public static Main getInstance() {
        return instance;
    }

    public void addMoney(String name, Integer amount){
        File file = new File("plugins/Exo-System", "money.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        Integer current = config.getInt(name + ".money");
        config.set(name + ".money", current + amount);
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
