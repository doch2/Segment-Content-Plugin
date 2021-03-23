package com.dochsoft.fullmoon.segment;

import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.io.File;
import java.util.*;

public class Reference {
    public static final String prefix_normal = "§6[세그먼트] §r";
    public static final String prefix_opMessage = "§b[세그먼트-OP메세지] §r";
    public static final String prefix_error = "§c§l[세그먼트] §7";
    public static final String prefix_shop = "§c§l[상점] §r";
    public static String ENABLE_MESSAGE =  "플러그인이 §a활성화§r되었습니다. §r| 도치(doch1)";
    public static String DISABLE_MESSAGE = "플러그인이 §c비활성화§r되었습니다. §r| 도치(doch1)";
    public static String COMMAND_CMD_NOT_PRACTICE_MESSAGE = "버킷창에서는 실행되지 않으니, 게임에서 명령어를 실행해주시기 바랍니다";
    public static String COMMAND_NOTALLOW_NOOP_MESSAGE = "관리자 명령어입니다. 명령어를 이용하실려면 관리자 권한을 취득해주세요";
    public static String COMMAND_NOT_CORRECT = "잘못된 명령어입니다";

    public static String SHOP_CREATE_ALREADY_ERROR = "상점 %s가 이미 만들어져 있습니다. 상점 이름을 다시 확인해주세요.";
    public static String SHOP_CREATE_SUCCESSFUL = "상점 §e%s§f가 생성되었습니다.";
    public static String SHOP_ITEM_ADD_SUCCESSFUL = "상점 %s의 페이지 %s에 아이템 %s이(가) 정상적으로 등록되었습니다.";
    public static String SHOP_ADD_ITEM_ITEMPOSITION_OVERFLOW_ERROR = "상점에 등록될 아이템의 위치가 상점 페이지를 넘어갑니다. 위치가 36을 넘어가지 않게 설정하여 주시기 바랍니다.";
    public static String SHOP_OPENSHOP_NOTHAVE_ERROR = "상점이 존재하지 않습니다. 상점 이름을 확인해주세요.";
    public static String SHOP_REMOVE_SUCCESSFUL = "상점 %s가 성공적으로 삭제되었습니다.";
    public static String SHOP_REMOVE_NOTHAVE_ERROR = "삭제하려는 상점이 존재하지 않습니다. 상점 이름을 확인해주세요.";
    public static String SHOP_CREATE_COMMAND_DESCRIPTION1 = "§6/shop create §b<shopName> §7- 상점을 새로 만듭니다.";
    public static String SHOP_OPEN_COMMAND_DESCRIPTION1 = "§6/shop open §b<shopName> §7- 상점을 엽니다.";
    public static String SHOP_LIST_COMMAND_DESCRIPTION1 = "§6/shop list §7- 상점의 리스트를 봅니다.";
    public static String SHOP_REMOVE_COMMAND_DESCRIPTION1 = "§6/shop remove §7- 만들어진 상점을 삭제합니다.";
    public static String SHOP_ADD_BUYITEM_COMMAND_DESCRIPTION1 = "§6/shop additem buy §b<shopName> <shopPage> <itemPosition> <Price> §7- 상점의 살 아이템을 설정합니다.";
    public static String SHOP_ADD_SELLITEM_COMMAND_DESCRIPTION1 = "§6/shop additem sell §b<shopName> <shopPage> <itemPosition> <Price>  §7- 상점의 팔 아이템을 설정합니다.";

    public static final String ConfigFolder = "plugins/Segment/";

    public static int STOP_ITEM_CODE = 4462;
    public static int ICE_ITEM_CODE = 4460;
    public static int CHANGE_LOCATION_ITEM_CODE = 4459;

    public static int DEFAULT_ADDON_CODE = 4457;
    public static int ITEM_ADDON_CODE = 4458;

    public static int JUMPMAP_SUCCESS_ITEM = 4379;
    public static int MIRO_SUCCESS_ITEM = 4378;

    public static int DOOR_OPEN_COUPON = 4464;
    public static int DOOR_CHOOSE_LOCK_COUPON = 4461;

    public static int SHOP_MAIN_ITEM = 4367;
    public static int LEFT_ARROW_ITEM = 4368;
    public static int RIGHT_ARROW_ITEM = 4369;

    public static int INFORMATION_TABLET_ITEM = 4370;

    public static int WORLD_CHANGE_ITEM = 4380;

    public static int GUI_BACKGROUND_ITEM = 4371;
    public static int GUI_AGREE_ITEM = 4372;
    public static int GUI_DISAGREE_ITEM = 4373;
    public static int GUI_DOIT_ITEM = 4374;
    public static int GUI_APPLY_ITEM = 4375;
    public static int GUI_ROOMPRICE_ITEM = 4376;
    public static int GUI_USEADDON_ITEM = 4377;

    public static HashMap<UUID, Location> playerBeforeWorld = new HashMap();
    public static HashMap<UUID, Boolean> isPlayerDeath = new HashMap();

    public static int fourthTutoRoomAddon = 0;

    public static FileConfiguration data;

    public static void sendOpMessage(String sendMessage) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.isOp()) {
                onlinePlayer.sendMessage(Reference.prefix_opMessage + sendMessage);
            }
        }
    }

    public static void sendMessageAllPlayer(String sendMessage) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.sendMessage(Reference.prefix_normal + sendMessage);
        }
    }

    public static void givePlayerMoney(Player player, double money) {
        EconomyResponse economyResponse = Segment.econ.depositPlayer(player, money);
    }

    public static void removePlayerMoney(Player player, double money) {
        EconomyResponse economyResponse = Segment.econ.withdrawPlayer(player, money);
    }

    public static List<Integer> getNumberList(int Integer) {
        List<Integer> result = new Vector<>();
        result.add(Integer / 1000);
        result.add((Integer - result.get(0) * 1000) / 100);
        result.add((Integer - (result.get(0) * 1000 + result.get(1) * 100)) / 10);
        result.add(Integer - (result.get(0) * 1000 + result.get(1) * 100 + result.get(2) * 10));
        return result;
    }

    public static ItemStack getInfoTabletToPlayer(String tabletKind) {
        ItemStack item = new MaterialData(Reference.INFORMATION_TABLET_ITEM, (byte)0).toItemStack(1);
        ItemMeta itemMeta = item.getItemMeta();
        if (tabletKind.equals("상점")) {
            itemMeta.setDisplayName("§f태블릿");
        } else {
            itemMeta.setDisplayName("§f정보 태블릿 [§6" + tabletKind + "§f]");
        }
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack getHiddenInfoTabletToPlayer(String tabletKind) {
        ItemStack item = new MaterialData(386, (byte)0).toItemStack(1);
        BookMeta bookMeta = (BookMeta) item.getItemMeta();
        bookMeta.setDisplayName("§f정보 태블릿 [§6" + tabletKind + "§f]");
        List<String> page = new ArrayList<>();
        String page1= "[SEGMENT]\n\n히든정보\n\n\n\n";
        if (tabletKind.equals("히든1")) {
            page1 = page1 + "궁금한게 있다면 인터넷 고글에 물어보자";
        } else if (tabletKind.equals("히든2")) {
            page1 = page1 + "나의 오랜 중국 친구에게 이 암호편지를 보내.";
        } else if (tabletKind.equals("히든3")) {
            page1 = page1 + "(네가 나랑 안놀아주잖아 - 넌 비겁해 - 미안해) / 잘자";
        } else if (tabletKind.equals("히든4")) {
            page1 = page1 + "소수점 숫자는 점을 '-'으로 대체한다.";
        } else if (tabletKind.equals("???")) {
            page1 = "\n\n\n\n\n\n\n[.......]";
            page.add(page1);
            page1 = "\n\n\n\n\n\n\n[...............]";
            page.add(page1);
            page1 = "\n\n\n\n\n\n\n[축하합니다.]";
            page.add(page1);
            page1 = "\n\n\n\n\n\n\n[당신은 생존의 기회를\n           획득하셨습니다.]";
            page.add(page1);
            page1 = "\n\n\n\n\n\n\n[모든 플레이어는 세그먼트 종료 후 사망합니다.]";
            page.add(page1);
            page1 = "\n\n\n\n\n\n\n[하지만 특별히 당신에게 기회를 드립니다.]";
            page.add(page1);
            page1 = "\n\n\n\n\n\n\n[지금부터 세그먼트내에 생존해있는 모든 플레이어를 살해하십시오]";
            page.add(page1);
            page1 = "\n\n\n\n\n\n\n[살인은 오로지 세그먼트 시간내에서만 가능하며, 야생시간에는 죽일 수 없습니다.]";
            page.add(page1);
            page1 = "\n\n\n\n\n\n\n[킬로그는 남지 않으니 안심하고 살해하십시오.]";
            page.add(page1);
            page1 = "\n\n\n\n\n\n\n[그럼 행운을 빕니다. 유일한 생존자가 될 수 있길.....]";
            page.add(page1);
            page1 = "\n- 이 사실을 다른 플레이어에게 공유하지 마십시오.\n\n- 살인은 오직 주어진 전용 칼로만 가능합니다.\n\n- 모든 플레이어 살해를 실패할 시 당신도 함께 사망합니다.";
            page.add(page1);
            page1 = "\n\n\n\n\n\n\n[SEGMENT]";
        }
        page.add(page1);
        bookMeta.setPages(page);
        item.setItemMeta(bookMeta);
        return item;
    }

    public static ItemStack getDefaultAddonToPlayer(Integer percentage) {
        ItemStack item = new MaterialData(Reference.DEFAULT_ADDON_CODE, (byte)0).toItemStack(1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§6" + percentage + "§f% 일반 애드온");
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack getItemAddonToPlayer() {
        ItemStack item = new MaterialData(Reference.ITEM_ADDON_CODE, (byte)0).toItemStack(1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("§6§f아이템 애드온");
        item.setItemMeta(itemMeta);
        return item;
    }

    public static Boolean getPlayerBeforeDeath(Player player) {
        File file = new File(Reference.ConfigFolder + "player/" + player.getName() + ".yml");
        data = YamlConfiguration.loadConfiguration(file);

        return (Boolean) data.get("BeforeDeath");
    }

    public static void removeInventoryItems(Inventory playerInv, Material meterial, Byte itemData, int itemAmount) {
        ItemStack[] items = playerInv.getContents();
        int i = 0;
        while (true) {
            if (i >= items.length) {
                break;
            }
            ItemStack itemstack = items[i];
            if (itemstack != null && itemstack.getType() == meterial && itemstack.getData().getData() == itemData) {
                int newAmount = itemstack.getAmount() - itemAmount;
                if (newAmount <= 0) {
                    items[i] = new ItemStack(Material.AIR);
                    itemAmount = -newAmount;
                    if (itemAmount == 0) {
                        break;
                    }
                } else {
                    itemstack.setAmount(newAmount);
                    break;
                }
            }
            i++;
        }
        playerInv.setContents(items);
    }

    public static void removeInventoryItems(Inventory playerInv, ItemStack removeItemStack, int itemAmount) {
        ItemStack[] items = playerInv.getContents();
        int i = 0;
        while (true) {
            if (i >= items.length) {
                break;
            }
            ItemStack itemstack = items[i];
            if (itemstack != null && itemstack.getItemMeta().getDisplayName() != null
                    && itemstack.getItemMeta().getDisplayName().equalsIgnoreCase(removeItemStack.getItemMeta().getDisplayName()) && itemstack.getTypeId() == removeItemStack.getTypeId()) {
                int newAmount = itemstack.getAmount() - itemAmount;
                if (newAmount <= 0) {
                    items[i] = new ItemStack(Material.AIR);
                    itemAmount = -newAmount;
                    if (itemAmount == 0) {
                        break;
                    }
                } else {
                    itemstack.setAmount(newAmount);
                    break;
                }
            }
            i++;
        }
        playerInv.setContents(items);
    }

    public static Integer getItemAmountInInventory(Inventory playerInv, Integer itemId) {
        Integer returnValue = 0;
        Material itemType = Material.AIR;
        Integer itemAmount = 0;

        for (int i=0; i < 36; i++) {
            if (playerInv.getItem(i) != null) {
                itemType = playerInv.getItem(i).getType();
                itemAmount = playerInv.getItem(i).getAmount();
            }
            
            if (itemType == Material.getMaterial(itemId)) {
                returnValue = returnValue + itemAmount;
            }
        }
        return returnValue;
    }

    public static List<Player> getNotOpPlayerList() {
        List<Player> list = new Vector<>();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!onlinePlayer.isOp()) {
                list.add(onlinePlayer);
            }
        }
        return list;
    }

    public static void setGuiItem(String itemName, int itemId, int data, int stack, List<String> lore, int loc, Inventory inv) {
        ItemStack item = new MaterialData(itemId, (byte)data).toItemStack(stack);
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

    public static void setGuiHeadItem(String playerName, Inventory inv, int loc, String itemName, List<String> lore) {
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwner(playerName);
        skullMeta.setDisplayName(itemName);
        skullMeta.setLore(lore);
        itemStack.setItemMeta((ItemMeta) skullMeta);
        inv.setItem(loc, itemStack);
    }
}
