package com.dochsoft.fullmoon.segment;

import com.dochsoft.fullmoon.segment.room.RoomReference;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PlayerConfig {
    public static FileConfiguration data;

    public static void createPlayerConfig(Player player, List<String> roomList) {
        File file = new File(Reference.ConfigFolder + "player/" + player.getName() + ".yml");
        File folder = new File(Reference.ConfigFolder);
        File folder2 = new File(Reference.ConfigFolder + "player/");
        data = YamlConfiguration.loadConfiguration(file);

        try {
            if (!file.exists()) {
                folder.mkdir();
                folder2.mkdir();
                file.createNewFile();
                data.set("Room", roomList);
                data.set("BeforeDeath", false);
                data.set("EarnMoney", 0);
                data.save(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setPlayerBeforeLocation(Player player, Location location) {
        File file = new File(Reference.ConfigFolder + "player/" + player.getName() + ".yml");
        File folder = new File(Reference.ConfigFolder);
        data = YamlConfiguration.loadConfiguration(file);

        try {
            if (!file.exists()) {
                folder.mkdir();
                file.createNewFile();
            }
            data.set("BeforeLoc", location);
            data.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setPlayerDeath(Player player, Boolean isDeath) {
        File file = new File(Reference.ConfigFolder + "player/" + player.getName() + ".yml");
        File folder = new File(Reference.ConfigFolder);
        data = YamlConfiguration.loadConfiguration(file);

        try {
            if (!file.exists()) {
                folder.mkdir();
                file.createNewFile();
            }
            data.set("BeforeDeath", isDeath);
            data.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setEarnMoney(Player player, int earnMoney) {
        File file = new File(Reference.ConfigFolder + "player/" + player.getName() + ".yml");
        File folder = new File(Reference.ConfigFolder);
        data = YamlConfiguration.loadConfiguration(file);

        try {
            if (!file.exists()) {
                folder.mkdir();
                file.createNewFile();
            }
            data.set("EarnMoney", (int) data.get("EarnMoney") + earnMoney);
            data.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
