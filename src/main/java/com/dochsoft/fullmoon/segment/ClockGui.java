package com.dochsoft.fullmoon.segment;

import com.dochsoft.fullmoon.segment.room.setRoomArea.RoomArea;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ClockGui {

    public static void openClockGui(Player player) {

        Inventory inv = Bukkit.createInventory(null, 9, Reference.prefix_normal + "시계 메뉴");

        for (int i=0; i < 9; i++) {
            Reference.setGuiItemNoLore(" ", Reference.GUI_BACKGROUND_ITEM, 0, 1, i, inv); //배경 유리판
        }

        Reference.setGuiItemNoLore("§f야생상점 - 광물", 278, 0, 1, 1, inv);
        Reference.setGuiItemNoLore("§f야생상점 - 채집", 364, 0, 1, 2, inv);
        Reference.setGuiItemNoLore("§f야생상점 - 낚시", 346, 0, 1, 3, inv);
        Reference.setGuiItemNoLore("§f애드온상점", Reference.DEFAULT_ADDON_CODE, 0, 1, 4, inv);
        Reference.setGuiItemNoLore("§f아이템상점", Reference.CHANGE_LOCATION_ITEM_CODE, 0, 1, 5, inv);

        Reference.setGuiItemNoLore("§f휴지통", 339, 0, 1, 7, inv);

        player.openInventory(inv);

    }

    public static void openRecycleBinGui(Player player) {

        Inventory inv = Bukkit.createInventory(null, 36, Reference.prefix_normal + "휴지통");

        Reference.setGuiItemNoLore("§f삭제하기", Reference.GUI_DOIT_ITEM, 0, 1, 35, inv);

        player.openInventory(inv);

    }
}
