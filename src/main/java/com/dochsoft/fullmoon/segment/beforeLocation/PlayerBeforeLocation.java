package com.dochsoft.fullmoon.segment.beforeLocation;

import com.dochsoft.fullmoon.segment.Reference;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;

public class PlayerBeforeLocation implements CommandExecutor {
    public static FileConfiguration data;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = ((Player) sender).getPlayer();

        if (!(sender instanceof Player)) {
            sender.sendMessage(Reference.prefix_error + Reference.COMMAND_CMD_NOT_PRACTICE_MESSAGE);
            return true;
        }

        if (args.length == 0) {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (!onlinePlayer.isOp()) {
                    onlinePlayer.teleport(getPlayerBeforeLoc(onlinePlayer));
                }
            }
        }

        return false;
    }

    public static Location getPlayerBeforeLoc(Player player) {
        File file = new File(Reference.ConfigFolder + "player/" + player.getName() + ".yml");
        data = YamlConfiguration.loadConfiguration(file);

        if (!file.exists() || data.get("BeforeLoc") == null) {
            return Bukkit.getWorld("flat1").getSpawnLocation();
        } else {
            return (Location) data.get("BeforeLoc");
        }
    }
}
