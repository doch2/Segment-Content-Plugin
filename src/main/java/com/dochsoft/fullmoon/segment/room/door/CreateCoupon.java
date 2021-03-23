package com.dochsoft.fullmoon.segment.room.door;

import com.dochsoft.fullmoon.segment.Reference;
import com.dochsoft.fullmoon.segment.room.buyRoom.BuyRoomGui;
import com.dochsoft.fullmoon.segment.room.buyRoom.SetSkullInGuiThread;
import com.dochsoft.fullmoon.segment.room.setRoomArea.RoomArea;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.Arrays;

public class CreateCoupon {

    public static void getCouponGui(Player player) {

        Inventory inv = Bukkit.createInventory(null, 45, Reference.prefix_normal + "문 카드키 생성");

        for (int i=0; i < 45; i++) {
            if (i != 14 && i != 15 && i != 16) {
                Reference.setGuiItemNoLore(" ", Reference.GUI_BACKGROUND_ITEM, 0, 1, i, inv); //배경 유리판
            }
        }

        Reference.setGuiItemNoLore("", BuyRoomGui.NumberOneItem, 0, 1, 10, inv);
        Reference.setGuiItemNoLore("", BuyRoomGui.NumberTwoItem, 0, 1, 11, inv);
        Reference.setGuiItemNoLore("", BuyRoomGui.NumberThreeItem, 0, 1, 12, inv);
        Reference.setGuiItemNoLore("", BuyRoomGui.NumberFourItem, 0, 1, 19, inv);
        Reference.setGuiItemNoLore("", BuyRoomGui.NumberFiveItem, 0, 1, 20, inv);
        Reference.setGuiItemNoLore("", BuyRoomGui.NumberSixItem, 0, 1, 21, inv);
        Reference.setGuiItemNoLore("", BuyRoomGui.NumberSevenItem, 0, 1, 28, inv);
        Reference.setGuiItemNoLore("", BuyRoomGui.NumberEightItem, 0, 1, 29, inv);
        Reference.setGuiItemNoLore("", BuyRoomGui.NumberNineItem, 0, 1, 30, inv);
        Reference.setGuiItemNoLore("", BuyRoomGui.NumberZeroItem, 0, 1, 38, inv);


        Reference.setGuiItemNoLore("§f가격 초기화", Reference.GUI_DISAGREE_ITEM, 0, 1, 32, inv);
        Reference.setGuiItemNoLore("§f카드키 만들기", Reference.GUI_AGREE_ITEM, 0, 1, 34, inv);

        player.openInventory(inv);

    }

    public static void playerGiveDoorCoupon(Player player, String roomName, int doorPrice) {
        ItemStack item = new MaterialData(Reference.DOOR_CHOOSE_LOCK_COUPON, (byte)0).toItemStack(1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§6" + roomName + "§f방 잠금 카드키 - " + doorPrice + "코인");
        item.setItemMeta(itemMeta);
        player.getInventory().addItem(item);

        item = new MaterialData(Reference.DOOR_OPEN_COUPON, (byte)0).toItemStack(1);
        itemMeta.setDisplayName("§6" + roomName + "§f방 잠금해제 카드키 - " + doorPrice + "코인");
        item.setItemMeta(itemMeta);
        player.getInventory().addItem(item);

        Reference.removePlayerMoney(player, doorPrice);
    }
}
