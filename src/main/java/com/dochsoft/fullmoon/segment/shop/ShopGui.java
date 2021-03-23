package com.dochsoft.fullmoon.segment.shop;

import com.dochsoft.fullmoon.segment.Reference;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ShopGui {
    public static FileConfiguration data;
    public static HashMap<UUID, String> playerShopMode = new HashMap();
    public static HashMap<UUID, Integer> playerShopPageNumber = new HashMap();

    public static void setGuiItem(String itemName, int itemId, int data, int stack, List<String> lore, int loc, Inventory inv) {
        ItemStack item = new MaterialData(itemId, (byte)data).toItemStack(stack);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(itemName);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        inv.setItem(loc, item);
    }

    public static void setGuiItemContainItemStack(String itemName, ItemStack itemStack, List<String> lore, int loc, Inventory inv) {
        ItemStack item = new ItemStack(itemStack);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(itemName);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        inv.setItem(loc, item);
    }

    public static void setGuiItemNoLore(String itemName, int itemId, int data, int stack, int loc, Inventory inv) {
        ItemStack item = new MaterialData(itemId, (byte)data).toItemStack(stack);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(itemName);
        item.setItemMeta(itemMeta);
        inv.setItem(loc, item);
    }

    public static void openShopGui(Player player, String shopName, int shopPageNumber, List<Integer> shopItemList, String shopMode) { //sell이면 파는 gui, buy면 사는 gui
        playerShopPageNumber.put(player.getUniqueId(), shopPageNumber);
        playerShopMode.put(player.getUniqueId(), shopMode);

        String shopTitle;
        if (ShopGui.playerShopMode.get(player.getUniqueId()).equalsIgnoreCase("buy")) {
            shopTitle = " - 구매 페이지";
        } else {
            shopTitle = " - 판매 페이지";
        }

        Inventory inv = Bukkit.createInventory(null, 54, Reference.prefix_shop + shopName + shopTitle);
        File file = new File(Reference.ConfigFolder + "shop/" + shopName + ".yml");
        data = YamlConfiguration.loadConfiguration(file);

        for (int i=36; i < 45; i++) {
            setGuiItemNoLore(" ", Reference.GUI_BACKGROUND_ITEM, 0, 1, i, inv); //상점 메뉴 구분선 - 유리판
        }
        setGuiItemNoLore("§r이전 페이지로", Reference.LEFT_ARROW_ITEM, 0, 1, 48, inv);
        setGuiItem("§r현재 페이지: §e" + shopPageNumber, Reference.SHOP_MAIN_ITEM, 0, 1, Arrays.asList(" ", "§r최대 상점 페이지: §e" + data.get("Shop.BuyPageAmount")), 49, inv);
        setGuiItemNoLore("§r다음 페이지로", Reference.RIGHT_ARROW_ITEM, 0, 1, 50, inv);
        if (shopMode.equalsIgnoreCase("buy")) {
            setGuiItemNoLore("§r판매 페이지로 이동", Reference.SHOP_MAIN_ITEM, 0, 1, 53, inv);
        } else if (shopMode.equalsIgnoreCase("sell")) {
            setGuiItemNoLore("§r구매 페이지로 이동", Reference.SHOP_MAIN_ITEM, 0, 1, 53, inv);
        }


        String itemName;
        for (int i=0; i < shopItemList.size(); i++) {
            ItemStack itemStack = ShopReference.getItemStack(shopName, shopItemList.get(i), ShopGui.playerShopPageNumber.get(player.getUniqueId()), shopMode);
            List<String> itemLore = itemStack.getItemMeta().getLore();

            if (itemStack.getItemMeta().getDisplayName() == null) {
                itemName = "";
            } else {
                itemName = ShopReference.getItemStack(shopName, shopItemList.get(i), ShopGui.playerShopPageNumber.get(player.getUniqueId()), shopMode).getItemMeta().getDisplayName();
            }

            if (itemLore == null) {
                itemLore = Arrays.asList("", "§7--------------------------", ShopReference.getItemPriceLore(shopName, shopItemList.get(i), player), "§7--------------------------");
            } else {
                itemLore.add("§7--------------------------");
                itemLore.add(ShopReference.getItemPriceLore(shopName, shopItemList.get(i), player));
                itemLore.add("§7--------------------------");
                if (shopMode.equals("Buy")) {
                    itemLore.add("§l§6[좌클릭] §f§r물건 구매");
                } else if (shopMode.equals("Sell")) {
                    itemLore.add("§l§6[좌클릭] §f§r물건 판매");
                    itemLore.add("§l§6[쉬프트 + 좌클릭] §f§r물건 전부 판메");
                }
            }


            setGuiItemContainItemStack(itemName, itemStack, itemLore, shopItemList.get(i), inv);
        }
        player.openInventory(inv);
    }
}
