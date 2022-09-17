package de.patnase.ecosystem.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class MoneyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender.isOp()){
            if (args.length == 3){
                if (args[0].equalsIgnoreCase("set")){
                    setMoney(args[1], Integer.valueOf(args[2]));
                    sender.sendMessage("§7[§6§lP§c§lP§3§lG§8 - §2Economy§7] §a" + args[1] + "'s §fGeld wurde auf §2" + args[2] + "§6$§f gesetzt");
                }else if (args[0].equalsIgnoreCase("add")){
                    sender.sendMessage("§7[§6§lP§c§lP§3§lG§8 - §2Economy§7] §a" + args[1] + "'s §fGeld wurde um §2" + args[2] + "§6$§f erhöht");
                    addMoney(args[1], Integer.valueOf(args[2]));
                }else if (args[0].equalsIgnoreCase("remove")){
                    sender.sendMessage("§7[§6§lP§c§lP§3§lG§8 - §2Economy§7] §a" + args[1] + "'s §fwurden §2" + args[2] + "§6$§f abgezogen");
                    removeMoney(args[1], Integer.valueOf(args[2]));
                }else {
                    sender.sendMessage("§7[§6§lP§c§lP§3§lG§8 - §2Economy§7] §cUsage: /money <set|get|remove> <player> <amount>");
                }
            }else {
                if (sender instanceof  Player){
                    Player player = ((Player) sender).getPlayer();
                    sender.sendMessage("§7[§6§lP§c§lP§3§lG§8 - §2Economy§7] §fDein aktuelles Geld: §2" + getMoney(player.getName()));
                }
            }
        }else if (sender instanceof Player){
            Player player = ((Player) sender).getPlayer();
            sender.sendMessage("§7[§6§lP§c§lP§3§lG§8 - §2Economy§7] §fDein aktuelles Geld: §2" + getMoney(player.getName()));
        }

        return false;
    }

    public void setMoney(String name, Integer amount){
        File file = new File("plugins/Exo-System", "money.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        config.set(name + ".money", amount);
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public void removeMoney(String name, Integer amount){
        File file = new File("plugins/Exo-System", "money.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        Integer current = config.getInt(name + ".money");
        config.set(name + ".money", current - amount);
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer getMoney(String name){
        File file = new File("plugins/Exo-System", "money.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        return config.getInt(name + ".money");
    }
}
