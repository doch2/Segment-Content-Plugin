package com.dochsoft.fullmoon.segment.item;

import com.dochsoft.fullmoon.segment.Reference;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class IceItem {

    public static void stopAllPlayerBehavior(Player player) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!onlinePlayer.isOp() && onlinePlayer != player) {
                StopItem.playerStopItem.put(onlinePlayer.getUniqueId(), true);
                StopItem.playerStopTime.put(onlinePlayer.getUniqueId(), 10);
                onlinePlayer.sendMessage(Reference.prefix_normal + player.getName() + "님이 아이스 아이템을 사용하셔서 10초간 행동이 정지되었습니다. 10초 후 다시 움직임이 가능합니다.");
            }
        }

        Reference.removeInventoryItems(player.getInventory(), Material.getMaterial(Reference.ICE_ITEM_CODE), (byte) 0, 1);
        player.sendMessage(Reference.prefix_normal + "아이스 아이템이 사용되었습니다.");
    }
}
