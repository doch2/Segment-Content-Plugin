package com.dochsoft.fullmoon.segment.addon;

import com.dochsoft.fullmoon.segment.Reference;
import com.dochsoft.fullmoon.segment.room.RoomConfig;
import com.dochsoft.fullmoon.segment.room.RoomReference;
import com.dochsoft.fullmoon.segment.room.setRoomArea.RoomArea;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class AddonReference {

    public static void applyAddon(String roomName, ItemStack addonItem, ItemStack itemAddon_needItem) {
        if (addonItem.getTypeId() == Reference.ITEM_ADDON_CODE) {
            addonItem.getItemMeta().setLore(Arrays.asList("", "방 구매에 필요한 아이템은 옆 칸에 표시되어있습니다."));
            RoomReference.roomAddon.put(roomName + ".NeedItem", itemAddon_needItem);
        }

        RoomReference.roomAddon.put(roomName, addonItem);
        RoomConfig.setRoomAddon(roomName, addonItem, itemAddon_needItem);
    }
}
