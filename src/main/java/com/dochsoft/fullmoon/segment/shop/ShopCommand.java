package com.dochsoft.fullmoon.segment.shop;

import com.dochsoft.fullmoon.segment.Reference;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class ShopCommand implements CommandExecutor {
    public static FileConfiguration data;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = ((Player) sender).getPlayer();

        if (!(sender instanceof Player)) {
            sender.sendMessage(Reference.prefix_error + Reference.COMMAND_CMD_NOT_PRACTICE_MESSAGE);
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(Reference.SHOP_OPEN_COMMAND_DESCRIPTION1);
            sender.sendMessage(Reference.SHOP_LIST_COMMAND_DESCRIPTION1);
            sender.sendMessage(Reference.SHOP_CREATE_COMMAND_DESCRIPTION1);
            player.sendMessage(Reference.SHOP_ADD_BUYITEM_COMMAND_DESCRIPTION1);
            player.sendMessage(Reference.SHOP_ADD_SELLITEM_COMMAND_DESCRIPTION1);
            return true;
        }

        if (args[0].equalsIgnoreCase("create")) {
            if (!(args.length == 2)) {
                sender.sendMessage(Reference.SHOP_CREATE_COMMAND_DESCRIPTION1);
            }
            if (args.length == 2) {
                ShopConfig.createShopConfig(player, args[1]);
            }
        } else if (args[0].equalsIgnoreCase("remove")) {
            if (args.length != 2) {

            } else {
                ShopConfig.removeShopConfig(player, args[1]);
            }
        } else if (args[0].equalsIgnoreCase("list")) {
            if (args.length != 1) {
                sender.sendMessage(Reference.SHOP_LIST_COMMAND_DESCRIPTION1);
            } else {
                File folder = new File(Reference.ConfigFolder + "shop/");
                ArrayList folderList = new ArrayList(Arrays.asList(folder.list()));
                String folderString = "";
                for (int i=0; i < folderList.size(); i++) {
                    folderString = folderString + folderList.get(i).toString().substring(0, folderList.get(i).toString().length() - 4) + "§f, §e";
                }
                folderString = folderString.substring(0, folderString.length() - 6);
                sender.sendMessage(Reference.prefix_normal + "상점 리스트: §e" + folderString);
            }
        } else if (args[0].equalsIgnoreCase("open")) {
            if (args.length != 2) {
                sender.sendMessage(Reference.SHOP_OPEN_COMMAND_DESCRIPTION1);
            } else {
                File folder = new File(Reference.ConfigFolder + "shop/");
                ArrayList folderList = new ArrayList(Arrays.asList(folder.list()));
                ArrayList folderRealList = new ArrayList();
                for (int i=0; i < folderList.size(); i++) {
                    folderRealList.add(folderList.get(i).toString().substring(0, folderList.get(i).toString().length() - 4));
                }
                if (folderRealList.contains(args[1])) {
                    ShopGui.playerShopPageNumber.put(player.getUniqueId(), 1);
                    ShopGui.playerShopMode.put(player.getUniqueId(), "Buy");
                    ShopGui.openShopGui(player, args[1], 1, ShopReference.getShopItemList(args[1], ShopGui.playerShopPageNumber.get(player.getUniqueId()), "Buy"), "Buy");
                } else {
                    sender.sendMessage(Reference.prefix_error + Reference.SHOP_OPENSHOP_NOTHAVE_ERROR);
                }
            }
        } else if(args[0].equalsIgnoreCase("additem") && args[1].equalsIgnoreCase("buy")) {
            if (args.length != 6) {
                player.sendMessage(Reference.SHOP_ADD_BUYITEM_COMMAND_DESCRIPTION1);
            }
            if (args.length == 6) {
                if (Integer.parseInt(args[4]) > 36) {
                    sender.sendMessage(Reference.prefix_error + Reference.SHOP_ADD_ITEM_ITEMPOSITION_OVERFLOW_ERROR);
                } else {
                    ShopConfig.addShopBuyItem(player, args[2], Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
                }
            }
        } else if(args[0].equalsIgnoreCase("additem") && args[1].equalsIgnoreCase("sell")) {
            if (args.length != 6) {
                player.sendMessage(Reference.SHOP_ADD_SELLITEM_COMMAND_DESCRIPTION1);
            }
            if (args.length == 6) {
                if (Integer.parseInt(args[4]) > 36) {
                    sender.sendMessage(Reference.prefix_error + Reference.SHOP_ADD_ITEM_ITEMPOSITION_OVERFLOW_ERROR);
                } else {
                    ShopConfig.addShopSellItem(player, args[2], Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]));
                }
            }
        } else {
            sender.sendMessage(Reference.SHOP_OPEN_COMMAND_DESCRIPTION1);
            sender.sendMessage(Reference.SHOP_LIST_COMMAND_DESCRIPTION1);
            sender.sendMessage(Reference.SHOP_CREATE_COMMAND_DESCRIPTION1);
            player.sendMessage(Reference.SHOP_ADD_BUYITEM_COMMAND_DESCRIPTION1);
            player.sendMessage(Reference.SHOP_ADD_SELLITEM_COMMAND_DESCRIPTION1);
            return true;
        }

        return false;
    }
}
