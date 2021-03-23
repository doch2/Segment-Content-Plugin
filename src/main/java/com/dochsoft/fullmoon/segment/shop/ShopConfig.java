package com.dochsoft.fullmoon.segment.shop;

import com.dochsoft.fullmoon.segment.Reference;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class ShopConfig {
    public static FileConfiguration data;

    public static void removeShopConfig(Player player, String shopName) {
        File folder = new File(Reference.ConfigFolder + "shop/");
        ArrayList folderList = new ArrayList(Arrays.asList(folder.list()));
        Boolean hasFile = false;
        for (int i=0; i < folderList.size(); i++) {
            if (folderList.get(i).toString().substring(0, folderList.get(i).toString().length() - 4).equalsIgnoreCase(shopName)) {
                hasFile = true;
            }
        }

        if (hasFile) {
            File file = new File(Reference.ConfigFolder + "shop/" + shopName + ".yml");
            file.delete();
            player.sendMessage(Reference.prefix_normal + String.format(Reference.SHOP_REMOVE_SUCCESSFUL, shopName));
        } else {
            player.sendMessage(Reference.prefix_error + Reference.SHOP_REMOVE_NOTHAVE_ERROR);
        }
   }

    public static void createShopConfig(Player player, String shopName) {
        File file = new File(Reference.ConfigFolder + "shop/" + shopName + ".yml");
        File folder = new File(Reference.ConfigFolder + "shop/");
        data = YamlConfiguration.loadConfiguration(file);
        try {
            if (!file.exists()) {
                player.sendMessage(Reference.prefix_normal + String.format(Reference.SHOP_CREATE_SUCCESSFUL, shopName));
                folder.mkdir();
                file.createNewFile();
                data.set("Shop.BuyPageAmount", 1);
                data.set("Shop.SellPageAmount", 1);
                data.set("Shop.BuyList.Page1", null);
                data.set("Shop.SellList.Page1", null);
                data.save(file);
                ShopReference.shopList.add(shopName);
            } else {
                player.sendMessage(Reference.prefix_error + String.format(Reference.SHOP_CREATE_ALREADY_ERROR, shopName));
            }
        }
        catch (Exception localException) {}
    }

    public static void addShopBuyItem(Player player, String shopName, int shopPage, int itemPosition, int price) {
        File file = new File(Reference.ConfigFolder + "shop/" + shopName + ".yml");
        data = YamlConfiguration.loadConfiguration(file);
        List<Integer> buyList;
        List<Integer> sellList;
        try {
            if (shopPage > (Integer) data.get("Shop.BuyPageAmount")) {
                data.set("Shop.BuyPageAmount", shopPage);
            }

            for (int i=1; i < (Integer) data.get("Shop.BuyPageAmount")+1; i++) {
                if (data.get("Shop.BuyList.Page" + i) == null) {
                    buyList = new Vector<>();
                } else {
                    buyList = (List<Integer>) data.get("Shop.BuyList.Page" + i);
                }
                buyList.remove((Object) itemPosition);//이미 중복된 아이템이 있을경우
                buyList.remove((Object) 9999);
                if (shopPage == i) {
                    buyList.add(itemPosition - 1);
                }
                data.set("Shop.BuyList.Page" + i, buyList);
            }
            for (int i=1; i < (Integer) data.get("Shop.SellPageAmount")+1; i++) {
                if (data.get("Shop.SellList.Page" + i) == null) {
                    sellList = new Vector<>();
                } else {
                    sellList = (List<Integer>) data.get("Shop.SellList.Page" + i);
                }
                sellList.remove((Object) 9999);
                data.set("Shop.SellList.Page" + i, sellList);
            }
            data.set("BuyList.Page" + shopPage + "." + (itemPosition - 1) + ".itemStack", player.getItemInHand());
            data.set("BuyList.Page" + shopPage + "." + (itemPosition - 1) + ".price", price);
            data.save(file);
            if (player.getItemInHand().getItemMeta().getDisplayName() != null) {
                player.sendMessage(Reference.prefix_normal + String.format(Reference.SHOP_ITEM_ADD_SUCCESSFUL, shopName, shopPage, player.getItemInHand().getItemMeta().getDisplayName()));
            } else {
                player.sendMessage(Reference.prefix_normal + String.format(Reference.SHOP_ITEM_ADD_SUCCESSFUL, shopName, shopPage, player.getItemInHand().getType()));
            }
        } catch (Exception localException) { System.out.println(Reference.prefix_error + localException); }
    }

    public static void addShopSellItem(Player player, String shopName, int shopPage, int itemPosition, int price) {
        File file = new File(Reference.ConfigFolder + "shop/" + shopName + ".yml");
        data = YamlConfiguration.loadConfiguration(file);
        List<Integer> buyList;
        List<Integer> sellList;
        try {
            if (shopPage > (Integer) data.get("Shop.SellPageAmount")) {
                data.set("Shop.SellPageAmount", shopPage);
            }

            for (int i=1; i < (Integer) data.get("Shop.BuyPageAmount")+1; i++) {
                if (data.get("Shop.BuyList.Page" + i) == null) {
                    buyList = new Vector<>();
                } else {
                    buyList = (List<Integer>) data.get("Shop.BuyList.Page" + i);
                }
                buyList.remove((Object) 9999);
                data.set("Shop.BuyList.Page" + i, buyList);
            }

            for (int i=1; i < (Integer) data.get("Shop.SellPageAmount")+1; i++) {
                if (data.get("Shop.SellList.Page" + i) == null) {
                    sellList = new Vector<>();
                } else {
                    sellList = (List<Integer>) data.get("Shop.SellList.Page" + i);
                }
                sellList.remove((Object) itemPosition); //이미 중복된 아이템이 있을경우
                sellList.remove((Object) 9999);
                if (shopPage == i) {
                    sellList.add(itemPosition - 1); //GUI는 0부터 시작하므로 1을 뺌
                }
                data.set("Shop.SellList.Page" + i, sellList);
            }
            data.set("SellList.Page" + shopPage + "." + (itemPosition - 1) + ".itemStack", player.getItemInHand());
            data.set("SellList.Page" + shopPage + "." + (itemPosition - 1) + ".price", price);
            data.save(file);
            if (player.getItemInHand().getItemMeta().getDisplayName() != null) {
                player.sendMessage(Reference.prefix_normal + String.format(Reference.SHOP_ITEM_ADD_SUCCESSFUL, shopName, shopPage, player.getItemInHand().getItemMeta().getDisplayName()));
            } else {
                player.sendMessage(Reference.prefix_normal + String.format(Reference.SHOP_ITEM_ADD_SUCCESSFUL, shopName, shopPage, player.getItemInHand().getType()));
            }
        } catch (Exception localException) { System.out.println(Reference.prefix_error + localException); }
    }
}
