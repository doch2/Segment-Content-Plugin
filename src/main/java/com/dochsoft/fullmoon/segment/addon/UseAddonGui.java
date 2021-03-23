package com.dochsoft.fullmoon.segment.addon;

import com.dochsoft.fullmoon.segment.Reference;
import com.dochsoft.fullmoon.segment.room.RoomReference;
import com.dochsoft.fullmoon.segment.room.buyRoom.SetSkullInGuiThread;
import com.dochsoft.fullmoon.segment.room.setRoomArea.RoomArea;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class UseAddonGui {

    public static void openChoosePlayerRoomGui(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, Reference.prefix_normal + "애드온 사용할 방 선택 - " + player.getName());

        int itemLoc = 0;
        for (int i=0; i < RoomArea.roomAreaList.size(); i++) {
            if (RoomArea.roomArea.get(RoomArea.roomAreaList.get(i) + ".Owner").equalsIgnoreCase(player.getName())) {
                Reference.setGuiItem("§f방 이름: §6" + (String) RoomArea.roomAreaList.get(i), 324, 0, 1, Arrays.asList("", ""), itemLoc, inv);
                itemLoc = itemLoc + 1;
            }
        }

        player.openInventory(inv);
    }

    public static void openUseAddonGui(Player player, String roomName) {
        Inventory inv = Bukkit.createInventory(null, 27, Reference.prefix_normal + "애드온 사용 - " + roomName);

        for (int i=0; i < 27; i++) {
            if (i != 10) {
                Reference.setGuiItemNoLore(" ", Reference.GUI_BACKGROUND_ITEM, 0, 1, i, inv); //배경 유리판
            }
        }

        Runnable runnable = new SetSkullInGuiThread(inv, roomName, 13);
        new Thread(runnable).start();

        Reference.setGuiItemNoLore("§f적용할 애드온", Reference.GUI_USEADDON_ITEM, 0, 1, 1, inv);

        Reference.setGuiItemNoLore("§f애드온 적용하기", Reference.GUI_APPLY_ITEM, 0, 1, 22, inv);

        player.openInventory(inv);

    }
}
