package com.dochsoft.fullmoon.segment.shop;

import com.dochsoft.fullmoon.segment.Reference;
import com.dochsoft.fullmoon.segment.Segment;
import com.dochsoft.fullmoon.segment.log.Statistics;
import com.dochsoft.fullmoon.segment.log.WriteLog;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class ShopReference {
    public static List<String> shopList;
    public static FileConfiguration data;


    public static int getShopBuyPageAmount(String shopName) {
        File file = new File(Reference.ConfigFolder + "shop/" + shopName + ".yml");
        data = YamlConfiguration.loadConfiguration(file);
        return (Integer) data.get("Shop.BuyPageAmount");
    }

    public static List<Integer> getShopItemList(String shopName, int shopPageNumber, String shopMode) {
        File file = new File(Reference.ConfigFolder + "shop/" + shopName + ".yml");
        data = YamlConfiguration.loadConfiguration(file);
        return (List<Integer>) data.get("Shop." + shopMode + "List.Page" + shopPageNumber);
    }

    public static ItemStack getItemStack(String shopName, int itemPosition, int shopPageNumber, String shopMode) {
        File file = new File(Reference.ConfigFolder + "shop/" + shopName + ".yml");
        data = YamlConfiguration.loadConfiguration(file);
        return data.getItemStack(shopMode + "List.Page" + shopPageNumber + "." + (itemPosition) + ".itemStack");
    }

    public static int getShopItemPrice(String shopName, int itemPosition, int shopPageNumber, Player player) {
        File file = new File(Reference.ConfigFolder + "shop/" + shopName + ".yml");
        data = YamlConfiguration.loadConfiguration(file);
        return (int) data.get(ShopGui.playerShopMode.get(player.getUniqueId()) + "List.Page" + shopPageNumber + "." + itemPosition + ".price");
    }

    public static boolean isPlayerInvItemEqualSellItem (String shopName, InventoryClickEvent event, Player player) {
        File file = new File(Reference.ConfigFolder + "shop/" + shopName + ".yml");
        data = YamlConfiguration.loadConfiguration(file);
        ItemStack shopItemStack = null;
        Boolean result = false;
        for (int i=0; i<36; i++) {
            try {
                shopItemStack = (ItemStack) data.get("SellList.Page" + ShopGui.playerShopPageNumber.get(player.getUniqueId()) + "." + i + ".itemStack");
                if (player.getInventory().getItem(event.getSlot()).getType().toString().equalsIgnoreCase(shopItemStack.getType().toString())) {
                    result = true;
                }
            } catch (NullPointerException e) {}
        }
        return result;
    }

    public static int getInPlayerInventoryHaveItemAmount(Player player, ItemStack shopItemStack) {
        int playerItemAmount = 0;
        for (int i=0; i < 36; i++) {
            ItemStack playerItemStack = player.getInventory().getItem(i);
            if (playerItemStack != null) {
                if (playerItemStack.getType() == shopItemStack.getType()) {
                    playerItemAmount = playerItemAmount + playerItemStack.getAmount();
                }
            }
        }
        return playerItemAmount;
    }

    public static String getItemPriceLore(String shopName, int itemPosition, Player player) {
        File file = new File(Reference.ConfigFolder + "shop/" + shopName + ".yml");
        data = YamlConfiguration.loadConfiguration(file);
        int shopPageNumber = ShopGui.playerShopPageNumber.get(player.getUniqueId());
        int priceZeroAmount = 0;
        String priceLore;
        if (ShopGui.playerShopMode.get(player.getUniqueId()).equalsIgnoreCase("buy")) {
            priceLore = "§6가격 §f- ";
        } else {
            priceLore = "§6얻는 금액 §f- ";
        }

        if (getShopItemPrice(shopName, itemPosition, shopPageNumber, player) == 0) {
            priceLore = priceLore + "무료";
        } else {
            priceLore = priceLore + "§e" + getShopItemPrice(shopName, itemPosition, shopPageNumber, player) + "§f코인";
        }

        return priceLore;
    }

    public static void buyItem(Player player, String shopName, InventoryClickEvent event) {
        File file = new File(Reference.ConfigFolder + "shop/" + shopName + ".yml");
        data = YamlConfiguration.loadConfiguration(file);
        ItemStack itemStack = (ItemStack) data.get("BuyList.Page" + ShopGui.playerShopPageNumber.get(player.getUniqueId()) + "." + event.getSlot() + ".itemStack");

        if (player.getInventory().firstEmpty() != -1) { //인벤토리에 빈공간이 있을경우
            if (getShopItemPrice(shopName, event.getSlot(), ShopGui.playerShopPageNumber.get(player.getUniqueId()), player) <= Segment.econ.getBalance(player)) {
                player.getInventory().addItem(itemStack);

                Reference.removePlayerMoney(player, getShopItemPrice(shopName, event.getSlot(), ShopGui.playerShopPageNumber.get(player.getUniqueId()), player));

                String itemPrice = getItemPriceLore(shopName, event.getSlot(), player).substring(8);
                if (itemStack.getItemMeta().getDisplayName() != null) {
                    if (itemPrice.contains("무료")) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.valueOf(Reference.prefix_shop + itemStack.getItemMeta().getDisplayName() + " §e" + itemStack.getAmount() + "개§f를" + itemPrice + "로 샀습니다.")));
                    } else {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.valueOf(Reference.prefix_shop + itemStack.getItemMeta().getDisplayName() + " §e" + itemStack.getAmount() + "개§f를" + itemPrice + "에 구매하였습니다.")));
                    }
                } else {
                    if (itemPrice.contains("무료")) {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.valueOf(Reference.prefix_shop + itemStack.getType() + " §e" + itemStack.getAmount() + "개§f를" + itemPrice + "로 샀습니다.")));
                    } else {
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.valueOf(Reference.prefix_shop + itemStack.getType() + " §e" + itemStack.getAmount() + "개§f를" + itemPrice + "에 구매하였습니다.")));
                    }
                }
                Statistics.writeShopStatistics(player, shopName, ShopGui.playerShopPageNumber.get(player.getUniqueId()), event.getSlot(), itemStack, 1);
                WriteLog.writeLog(player.getName(), "Shop_Buy", shopName + " - " + itemStack.getType() + "x" + itemStack.getAmount() + " (" + itemPrice + ")");

                player.playSound(player.getLocation(), "ui.shop_buy", SoundCategory.PLAYERS, 1.0E8F, 1.0F);
            } else {
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.PLAYERS, 1.0E8F, 1.0F);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.valueOf(Reference.prefix_error + "물건을 사기에 충분한 코인을 가지고 있지 않습니다. 코인을 충분히 가지고 있는지 확인해주세요.")));
            }
        } else {
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.PLAYERS, 1.0E8F, 1.0F);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.valueOf(Reference.prefix_error + "인벤토리가 꽉 차있습니다. 인벤토리에 여유공간을 마련해주세요.")));
        }
    }

    public static void sellItem(Player player, String shopName, InventoryClickEvent event, boolean hasClickedTop) {
        File file = new File(Reference.ConfigFolder + "shop/" + shopName + ".yml");
        data = YamlConfiguration.loadConfiguration(file);
        int clickSlotIndex = 0;
        if (hasClickedTop) {
            clickSlotIndex = event.getSlot();
        } else {
            for (int i=0; i<36; i++) {
                try {
                    ItemStack shopItemStack = (ItemStack) data.get(ShopGui.playerShopMode.get(player.getUniqueId()) + "List.Page" + ShopGui.playerShopPageNumber.get(player.getUniqueId()) + "." + i + ".itemStack");
                    if (player.getInventory().getItem(event.getSlot()).getType().toString().equalsIgnoreCase(shopItemStack.getType().toString())
                            && player.getInventory().getItem(event.getSlot()).getData().getData() == shopItemStack.getData().getData()) {
                        clickSlotIndex = i;
                    }
                } catch (NullPointerException e) {}
            }
        }

        String itemPrice = getItemPriceLore(shopName, clickSlotIndex, player).substring(11);
        ItemStack itemStack = (ItemStack) data.get(ShopGui.playerShopMode.get(player.getUniqueId()) + "List.Page" + ShopGui.playerShopPageNumber.get(player.getUniqueId()) + "." + clickSlotIndex + ".itemStack");
        int itemPriceAmount = 0;
        int playerInvEmpty = 36;
        Boolean playerInvHaveItem = false;

        int shopItemPrice = getShopItemPrice(shopName, clickSlotIndex, ShopGui.playerShopPageNumber.get(player.getUniqueId()), player);

        int playerHaveItemAmount = 0;
        for (int i=0; i < 36; i++) {
            try {
                if (player.getInventory().getItem(i).getType() != null) {
                    if (player.getInventory().getItem(i).getType().toString().equalsIgnoreCase(itemStack.getType().toString()) && player.getInventory().getItem(i).getData().getData() == itemStack.getData().getData()) {
                        playerHaveItemAmount = playerHaveItemAmount + player.getInventory().getItem(i).getAmount();
                    }
                    playerInvEmpty = playerInvEmpty - 1;
                }
            } catch (NullPointerException e) {}
        }

        Boolean playerShiftClick = event.isShiftClick();

        playerInvHaveItem = playerHaveItemAmount >= itemStack.getAmount();
        if (itemPriceAmount <= playerInvEmpty && playerInvHaveItem) {

            if (playerShiftClick) { //쉬프트 클릭 했을 경우
                Reference.removeInventoryItems(player.getInventory(), itemStack.getData().getItemType(), itemStack.getData().getData(), (playerHaveItemAmount / itemStack.getAmount()) * itemStack.getAmount());
                Reference.givePlayerMoney(player, shopItemPrice * (int) (playerHaveItemAmount / itemStack.getAmount()));
                Statistics.writeShopStatistics(player, shopName, ShopGui.playerShopPageNumber.get(player.getUniqueId()), clickSlotIndex, itemStack, playerHaveItemAmount);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.valueOf(Reference.prefix_shop + itemStack.getType() + " §e" + playerHaveItemAmount + "개§f를 아이템 당" + itemPrice + "으로 판매하였습니다.")));
            } else {
                Reference.removeInventoryItems(player.getInventory(), itemStack.getData().getItemType(), itemStack.getData().getData(), itemStack.getAmount());
                Reference.givePlayerMoney(player, shopItemPrice);
                Statistics.writeShopStatistics(player, shopName, ShopGui.playerShopPageNumber.get(player.getUniqueId()), clickSlotIndex, itemStack, 1);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.valueOf(Reference.prefix_shop + itemStack.getType() + " §e" + itemStack.getAmount() + "개§f를 아이템 당" + itemPrice + "으로 판매하였습니다.")));
            }

            player.playSound(player.getLocation(), "ui.shop_buy", SoundCategory.PLAYERS, 1.0E8F, 1.0F);
        } else if (itemPriceAmount >= playerInvEmpty) {
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.PLAYERS, 1.0E8F, 1.0F);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.valueOf(Reference.prefix_error + "인벤토리가 꽉 차있습니다. 인벤토리에 여유공간을 마련해주세요.")));
        } else {
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.PLAYERS, 1.0E8F, 1.0F);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.valueOf(Reference.prefix_error + "팔려는 아이템이 인벤토리에 없거나 양이 부족합니다. 인벤토리를 다시 한 번 확인해주세요.")));
        }
    }

    public static void shopPageChangeButtonClicked(InventoryClickEvent event, Player player, String shopName) {

        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("이전")) {
            player.closeInventory();
            if (ShopGui.playerShopPageNumber.get(player.getUniqueId()) != 1) {
                ShopGui.openShopGui(player, shopName, ShopGui.playerShopPageNumber.get(player.getUniqueId()) - 1, ShopReference.getShopItemList(shopName, ShopGui.playerShopPageNumber.get(player.getUniqueId()) - 1,
                        ShopGui.playerShopMode.get(player.getUniqueId())), ShopGui.playerShopMode.get(player.getUniqueId()));
            } else {
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.PLAYERS, 1.0E8F, 1.0F);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.valueOf(Reference.prefix_error + "현재 상점 페이지가 1페이지입니다. 페이지 수를 확인해주세요.")));
            }
        } else if (event.getCurrentItem().getItemMeta().getDisplayName().contains("다음")) {
            player.closeInventory();
            if (ShopGui.playerShopPageNumber.get(player.getUniqueId()) + 1 <= ShopReference.getShopBuyPageAmount(shopName)) {
                ShopGui.openShopGui(player, shopName, ShopGui.playerShopPageNumber.get(player.getUniqueId()) + 1, ShopReference.getShopItemList(shopName, ShopGui.playerShopPageNumber.get(player.getUniqueId()) + 1,
                        ShopGui.playerShopMode.get(player.getUniqueId())), ShopGui.playerShopMode.get(player.getUniqueId()));
            } else {
                player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.PLAYERS, 1.0E8F, 1.0F);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(String.valueOf(Reference.prefix_error + "현재 상점의 최대 페이지는 §e" + ShopReference.getShopBuyPageAmount(shopName) + "§7페이지 입니다. 페이지 수를 확인해 주세요.")));
            }
        } else if (event.getCurrentItem().getItemMeta().getDisplayName().contains("구매")) {
            ShopGui.openShopGui(player, shopName, 1, ShopReference.getShopItemList(shopName, 1, "Buy"), "Buy");
        } else if (event.getCurrentItem().getItemMeta().getDisplayName().contains("판매")) {
            ShopGui.openShopGui(player, shopName, 1, ShopReference.getShopItemList(shopName, 1, "Sell"), "Sell");
        }
    }

}
