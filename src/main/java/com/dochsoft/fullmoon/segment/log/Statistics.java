package com.dochsoft.fullmoon.segment.log;

import com.dochsoft.fullmoon.segment.PlayerConfig;
import com.dochsoft.fullmoon.segment.Reference;
import com.dochsoft.fullmoon.segment.shop.ShopGui;
import com.dochsoft.fullmoon.segment.shop.ShopReference;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.time.LocalDate;

public class Statistics {
    public static FileConfiguration data;


    /*
    상점 통계 YML 형식
    Shop:
      <ShopName>:
        <ShopMode>:
          <itemName(Type)>:
            itemAmount:
            totalIncome:
     */
    public static void writeShopStatistics(Player player, String shopName, int shopPageNumber, int itemPosition, ItemStack itemStack, int itemAmount) { //buymode: Buy, Sell
        LocalDate nowDate = LocalDate.now();
        String shopMode = ShopGui.playerShopMode.get(player.getUniqueId());
        String prefix = "Shop." + shopName + "." + shopMode + ".";

        File file = new File(Reference.ConfigFolder + "log/statistics/" + nowDate + ".yml");
        File folder = new File(Reference.ConfigFolder + "log/statistics/");
        data = YamlConfiguration.loadConfiguration(file);
        try {
            if (!file.exists() || data.get(prefix + itemStack.getType().toString() + ".ItemAmount") == null) {
                folder.mkdir();
                file.createNewFile();
                if (itemStack.getItemMeta().getDisplayName() == null) {
                    data.set(prefix + itemStack.getType().toString() + ".ItemAmount", itemAmount);
                    data.set(prefix + itemStack.getType().toString() + ".totalIncome", ShopReference.getShopItemPrice(shopName, itemPosition, shopPageNumber, player) * itemAmount);
                } else {
                    data.set(prefix + itemStack.getItemMeta().getDisplayName() + ".ItemAmount", itemAmount);
                    data.set(prefix + itemStack.getItemMeta().getDisplayName() + ".totalIncome", ShopReference.getShopItemPrice(shopName, itemPosition, shopPageNumber, player) * itemAmount);
                }
                data.save(file);
            } else { //파일이 있을경우
                if (itemStack.getItemMeta().getDisplayName() == null) {
                    data.set(prefix + itemStack.getType() + ".ItemAmount", (Integer) data.get(prefix + itemStack.getType() + ".ItemAmount") + itemAmount);
                    data.set(prefix + itemStack.getType() + ".totalIncome",  (Integer) data.get(prefix + itemStack.getType() + ".totalIncome") + ShopReference.getShopItemPrice(shopName, itemPosition, shopPageNumber, player) * itemAmount);
                } else {
                    data.set(prefix + itemStack.getItemMeta().getDisplayName() + ".ItemAmount", (Integer) data.get(prefix + itemStack.getItemMeta().getDisplayName() + ".ItemAmount") + itemAmount);
                    data.set(prefix + itemStack.getItemMeta().getDisplayName() + ".totalIncome", (Integer) data.get(prefix + itemStack.getItemMeta().getDisplayName() + ".totalIncome") + ShopReference.getShopItemPrice(shopName, itemPosition, shopPageNumber, player) * itemAmount);
                }
                data.save(file);
            }
        }
        catch (Exception localException) {}

        PlayerConfig.setEarnMoney(player,
                ShopReference.getShopItemPrice(shopName, itemPosition, shopPageNumber, player) * (itemAmount / ShopReference.getItemStack(shopName, itemPosition, shopPageNumber, ShopGui.playerShopMode.get(player.getUniqueId())).getAmount()));
    }
}
