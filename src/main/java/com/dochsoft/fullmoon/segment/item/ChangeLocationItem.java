package com.dochsoft.fullmoon.segment.item;

import com.dochsoft.fullmoon.segment.Reference;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ChangeLocationItem {

    public static void choosePlayerGui(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, Reference.prefix_normal + "위치변환기 아이템 사용");

        int playerSkullLoc = 0;
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!onlinePlayer.isOp() && onlinePlayer != player) {
                Runnable runnable = new GuiSetSkullThread(inv, onlinePlayer, playerSkullLoc, "에게 이동");
                new Thread(runnable).start();

                playerSkullLoc = playerSkullLoc + 1;
            }
        }

        player.openInventory(inv);
    }

    public static void changePlayerLocation(Player player, Player player2) {
        Location playerLoc = player.getLocation();
        Location player2Loc = player2.getLocation();

        player.teleport(player2Loc);
        player2.teleport(playerLoc);
    }
}
