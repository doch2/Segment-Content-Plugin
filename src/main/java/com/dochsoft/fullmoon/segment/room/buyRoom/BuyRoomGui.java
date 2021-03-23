package com.dochsoft.fullmoon.segment.room.buyRoom;

import com.dochsoft.fullmoon.segment.Reference;
import com.dochsoft.fullmoon.segment.room.RoomReference;
import com.dochsoft.fullmoon.segment.room.setRoomArea.RoomArea;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.util.Arrays;
import java.util.List;

public class BuyRoomGui {
    public static int NumberOneItem = 4447;
    public static int NumberTwoItem = 4448;
    public static int NumberThreeItem = 4450;
    public static int NumberFourItem = 4454;
    public static int NumberFiveItem = 4453;
    public static int NumberSixItem = 4452;
    public static int NumberSevenItem = 4451;
    public static int NumberEightItem = 4446;
    public static int NumberNineItem = 4455;
    public static int NumberZeroItem = 4449;

    public static void buyRoomGui(Player player) {
        String roomName = RoomArea.playerNowAreaName.get(player.getUniqueId());

        Inventory inv = Bukkit.createInventory(null, 54, Reference.prefix_normal + "방 구매 - " + roomName);

        for (int i=0; i < 54; i++) {
            if (i != 16) {
                Reference.setGuiItemNoLore(" ", Reference.GUI_BACKGROUND_ITEM, 0, 1, i, inv); //배경 유리판
            }
        }

        Runnable runnable = new SetSkullInGuiThread(inv, roomName, 11);
        new Thread(runnable).start();

        Reference.setGuiItemNoLore("§f사용된 애드온", Reference.GUI_USEADDON_ITEM, 0, 1, 15, inv);

        if (RoomReference.roomAddon.get(roomName) != null) {
            ItemStack addonItem = RoomReference.roomAddon.get(roomName);
            inv.setItem(16, addonItem);

            if (addonItem.getTypeId() == Reference.ITEM_ADDON_CODE) {
                ItemStack needItem = RoomReference.roomAddon.get(roomName + ".NeedItem");
                inv.setItem(17, needItem);
            }
        }

        Reference.setGuiItemNoLore("§f방 사기", Reference.GUI_DOIT_ITEM, 0, 1, 43, inv);

        Reference.setGuiItemNoLore("§f방 구매 가격", Reference.GUI_ROOMPRICE_ITEM, 0, 1, 36, inv);

        List<Integer> integerList = Reference.getNumberList(RoomReference.getRoomPurchasePrice(roomName));

        for (int i=0; i < 4; i++) {
            if (integerList.get(i) == 1) {
                Reference.setGuiItemNoLore(" ", NumberOneItem, 0, 1, 37 + i, inv);
            } else if (integerList.get(i) == 2) {
                Reference.setGuiItemNoLore(" ", NumberTwoItem, 0, 1, 37 + i, inv);
            } else if (integerList.get(i) == 3) {
                Reference.setGuiItemNoLore(" ", NumberThreeItem, 0, 1, 37 + i, inv);
            } else if (integerList.get(i) == 4) {
                Reference.setGuiItemNoLore(" ", NumberFourItem, 0, 1, 37 + i, inv);
            } else if (integerList.get(i) == 5) {
                Reference.setGuiItemNoLore(" ", NumberFiveItem, 0, 1, 37 + i, inv);
            } else if (integerList.get(i) == 6) {
                Reference.setGuiItemNoLore(" ", NumberSixItem, 0, 1, 37 + i, inv);
            } else if (integerList.get(i) == 7) {
                Reference.setGuiItemNoLore(" ", NumberSevenItem, 0, 1, 37 + i, inv);
            } else if (integerList.get(i) == 8) {
                Reference.setGuiItemNoLore(" ", NumberEightItem, 0, 1, 37 + i, inv);
            } else if (integerList.get(i) == 9) {
                Reference.setGuiItemNoLore(" ", NumberNineItem, 0, 1, 37 + i, inv);
            } else if (integerList.get(i) == 0) {
                Reference.setGuiItemNoLore(" ", NumberZeroItem, 0, 1, 37 + i, inv);
            }
        }

        player.openInventory(inv);

    }
}
