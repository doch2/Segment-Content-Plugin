package com.dochsoft.fullmoon.segment.addon;

import com.dochsoft.fullmoon.segment.Reference;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

public class UseAddonGuiItemAddonThread extends Thread {
    private InventoryClickEvent event;

    public UseAddonGuiItemAddonThread(InventoryClickEvent event) {
        this.event = event;
    }

    public void run() {
        try {
            Thread.sleep(350);
            setItem(event);
            Thread.sleep(350);
            setItem(event);
            Thread.sleep(350);
            setItem(event);
            Thread.sleep(350);
            setItem(event);
            Thread.sleep(350);
            setItem(event);
            Thread.sleep(350);
            setItem(event);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setItem(InventoryClickEvent event) {
        if (event.getInventory().getItem(10) != null && event.getInventory().getItem(10).getTypeId() == Reference.ITEM_ADDON_CODE
                && event.getInventory().getItem(16) != null) {
            Reference.setGuiItemNoLore("§f적용할 아이템 넣기", Reference.GUI_USEADDON_ITEM, 0, 1, 7, event.getInventory());
            if (event.getInventory().getItem(16).getTypeId() == Reference.GUI_BACKGROUND_ITEM) {
                event.getInventory().clear(16);
            }
        } else if (event.getInventory().getItem(10) != null && event.getInventory().getItem(10).getTypeId() != Reference.ITEM_ADDON_CODE
                && event.getInventory().getItem(16) == null) {
            Reference.setGuiItemNoLore(" ", Reference.GUI_BACKGROUND_ITEM, 0, 1, 7, event.getInventory());
            Reference.setGuiItemNoLore(" ", Reference.GUI_BACKGROUND_ITEM, 0, 1, 16, event.getInventory());
        }
    }
}
