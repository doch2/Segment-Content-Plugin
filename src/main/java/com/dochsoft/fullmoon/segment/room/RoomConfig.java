package com.dochsoft.fullmoon.segment.room;

import com.dochsoft.fullmoon.segment.Reference;
import com.dochsoft.fullmoon.segment.room.setRoomArea.RoomArea;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.io.File;
import java.io.IOException;

public class RoomConfig {
    public static FileConfiguration data;

    public static void createRoomConfig(String roomName, Block pos1, Block pos2, int doorNumber) {
        File file = new File(Reference.ConfigFolder + "roomArea/" + roomName + ".yml");
        File folder = new File(Reference.ConfigFolder);
        File folder2 = new File(Reference.ConfigFolder + "roomArea/");
        data = YamlConfiguration.loadConfiguration(file);

        try {
            if (!file.exists()) {
                folder.mkdir();
                folder2.mkdir();
                file.createNewFile();
            }
            data.set("Name", roomName);
            data.set("Owner", "null");
            data.set("Price", RoomReference.defaultRoomPrice);
            data.set("DoorPrice", 0);
            data.set("DoorAllow", true);
            data.set("Addon.Item", null);
            data.set("DoorNumber", doorNumber);
            data.set("Locate.worldName", pos1.getWorld().getName());
            data.set("Locate.pos1X", pos1.getX());
            data.set("Locate.pos1Y", pos1.getY());
            data.set("Locate.pos1Z", pos1.getZ());
            data.set("Locate.pos2X", pos2.getX());
            data.set("Locate.pos2Y", pos2.getY());
            data.set("Locate.pos2Z", pos2.getZ());
            data.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeRoomConfig(String roomName) {
        File file = new File(Reference.ConfigFolder + "roomArea/" + roomName + ".yml");
        file.delete();
    }

    public static void rollbackRoom() throws InvalidConfigurationException {
        File file;
        File folderPath = new File(Reference.ConfigFolder + "roomArea/");
        if (!folderPath.exists()) {
            folderPath.mkdir();
        }
        File[] AreaList = folderPath.listFiles();
        if(AreaList.length > 0) {
            for(int i=0; i < AreaList.length; i++) {
                int pos = AreaList[i].toString().lastIndexOf(".");
                String _FileName = AreaList[i].toString().substring(0, pos);
                String RealFileName = _FileName.substring(25, pos);

                file = new File(Reference.ConfigFolder + "roomArea/" + RealFileName + ".yml");
                data = YamlConfiguration.loadConfiguration(file);
                try {
                    if (!file.exists()) {
                        folderPath.mkdir();
                        file.createNewFile();
                    }
                    data.set("Owner", "null");
                    data.set("Price", RoomReference.defaultRoomPrice);
                    data.set("DoorPrice", 0);
                    data.set("DoorAllow", true);
                    data.set("Addon.Item", null);
                    data.set("Addon.NeedItem", null);
                    data.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        ItemStack itemAddon = new MaterialData(Reference.ITEM_ADDON_CODE, (byte)0).toItemStack(1);
        ItemStack jumpmapSuccess = new MaterialData(Reference.JUMPMAP_SUCCESS_ITEM, (byte)0).toItemStack(1);
        ItemStack miroSuccess = new MaterialData(Reference.MIRO_SUCCESS_ITEM, (byte)0).toItemStack(1);

        file = new File(Reference.ConfigFolder + "roomArea/1층-7.yml");
        data = YamlConfiguration.loadConfiguration(file);
        try {
            if (!file.exists()) {
                folderPath.mkdir();
                file.createNewFile();
            }
            data.set("Addon.Item", itemAddon);
            data.set("Addon.NeedItem", jumpmapSuccess);
            data.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        file = new File(Reference.ConfigFolder + "roomArea/2층-10.yml");
        data = YamlConfiguration.loadConfiguration(file);
        try {
            if (!file.exists()) {
                folderPath.mkdir();
                file.createNewFile();
            }
            data.set("Addon.Item", itemAddon);
            data.set("Addon.NeedItem", miroSuccess);
            data.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void loadRoomArea() throws InvalidConfigurationException {
        File file;
        File folderPath = new File(Reference.ConfigFolder + "roomArea/");
        if (!folderPath.exists()) {
            folderPath.mkdir();
        }
        File[] AreaList = folderPath.listFiles();

        RoomArea.roomArea.clear();
        RoomReference.roomAddon.clear();
        RoomArea.roomAreaList.clear();

        if(AreaList.length > 0) {
            for(int i=0; i < AreaList.length; i++) {
                int pos = AreaList[i].toString().lastIndexOf(".");
                String _FileName = AreaList[i].toString().substring(0, pos);
                String RealFileName = _FileName.substring(25, pos);

                file = new File(Reference.ConfigFolder + "roomArea/" + RealFileName + ".yml");
                data = YamlConfiguration.loadConfiguration(file);
                try {
                    if (!file.exists()) {
                        folderPath.mkdir();
                        file.createNewFile();
                    }
                    RoomReference.roomAddon.put(RealFileName, (ItemStack) data.get("Addon.Item"));
                    RoomReference.roomAddon.put(RealFileName + ".NeedItem", (ItemStack) data.get("Addon.NeedItem"));
                    RoomArea.roomArea.put(RealFileName + ".Price", data.get("Price").toString());
                    RoomArea.roomArea.put(RealFileName + ".Owner", data.get("Owner").toString());
                    RoomArea.roomArea.put(RealFileName + ".DoorPrice", data.get("DoorPrice").toString());
                    RoomArea.roomArea.put(RealFileName + ".DoorAllow", data.get("DoorAllow").toString());
                    RoomArea.roomArea.put(RealFileName + ".DoorNumber", data.get("DoorNumber").toString());
                    RoomArea.roomArea.put(RealFileName + ".locateWorld", (String) data.get("Locate.worldName"));
                    RoomArea.roomArea.put(RealFileName + ".locatePos1X", data.get("Locate.pos1X").toString());
                    RoomArea.roomArea.put(RealFileName + ".locatePos1Y", data.get("Locate.pos1Y").toString());
                    RoomArea.roomArea.put(RealFileName + ".locatePos1Z", data.get("Locate.pos1Z").toString());
                    RoomArea.roomArea.put(RealFileName + ".locatePos2X", data.get("Locate.pos2X").toString());
                    RoomArea.roomArea.put(RealFileName + ".locatePos2Y", data.get("Locate.pos2Y").toString());
                    RoomArea.roomArea.put(RealFileName + ".locatePos2Z", data.get("Locate.pos2Z").toString());
                    RoomArea.roomAreaList.add(RealFileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void loadDoorArea() throws InvalidConfigurationException {
        File file;
        File folderPath = new File(Reference.ConfigFolder + "doorArea/");
        if (!folderPath.exists()) {
            folderPath.mkdir();
        }
        File[] AreaList = folderPath.listFiles();
        if(AreaList.length > 0) {
            for(int i=0; i < AreaList.length; i++){
                int pos = AreaList[i].toString().lastIndexOf(".");
                String _FileName = AreaList[i].toString().substring(0, pos);
                String RealFileName = _FileName.substring(25, pos);

                file = new File(Reference.ConfigFolder + "doorArea/" + RealFileName + ".yml");
                data = YamlConfiguration.loadConfiguration(file);
                try {
                    if (!file.exists()) {
                        folderPath.mkdir();
                        file.createNewFile();
                    }
                    RoomArea.doorArea.put(RealFileName + ".locateWorld", (String) data.get("Locate.worldName"));
                    RoomArea.doorArea.put(RealFileName + ".locatePos1X", data.get("Locate.pos1X").toString());
                    RoomArea.doorArea.put(RealFileName + ".locatePos1Y", data.get("Locate.pos1Y").toString());
                    RoomArea.doorArea.put(RealFileName + ".locatePos1Z", data.get("Locate.pos1Z").toString());
                    RoomArea.doorArea.put(RealFileName + ".locatePos2X", data.get("Locate.pos2X").toString());
                    RoomArea.doorArea.put(RealFileName + ".locatePos2Y", data.get("Locate.pos2Y").toString());
                    RoomArea.doorArea.put(RealFileName + ".locatePos2Z", data.get("Locate.pos2Z").toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void loadHallwayArea() throws InvalidConfigurationException {
        File file;
        File folderPath = new File(Reference.ConfigFolder + "hallwayArea/");
        if (!folderPath.exists()) {
            folderPath.mkdir();
        }
        File[] AreaList = folderPath.listFiles();
        if(AreaList.length > 0) {
            for(int i=0; i < AreaList.length; i++){
                int pos = AreaList[i].toString().lastIndexOf(".");
                String _FileName = AreaList[i].toString().substring(0, pos);
                String RealFileName = _FileName.substring(28, pos);

                file = new File(Reference.ConfigFolder + "hallwayArea/" + RealFileName + ".yml");
                data = YamlConfiguration.loadConfiguration(file);
                try {
                    if (!file.exists()) {
                        folderPath.mkdir();
                        file.createNewFile();
                    }
                    RoomArea.hallwayArea.put(RealFileName + ".locateWorld", (String) data.get("Locate.worldName"));
                    RoomArea.hallwayArea.put(RealFileName + ".locatePos1X", data.get("Locate.pos1X").toString());
                    RoomArea.hallwayArea.put(RealFileName + ".locatePos1Y", data.get("Locate.pos1Y").toString());
                    RoomArea.hallwayArea.put(RealFileName + ".locatePos1Z", data.get("Locate.pos1Z").toString());
                    RoomArea.hallwayArea.put(RealFileName + ".locatePos2X", data.get("Locate.pos2X").toString());
                    RoomArea.hallwayArea.put(RealFileName + ".locatePos2Y", data.get("Locate.pos2Y").toString());
                    RoomArea.hallwayArea.put(RealFileName + ".locatePos2Z", data.get("Locate.pos2Z").toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void setRoomOwner(String roomName, Player player) {
        File file = new File(Reference.ConfigFolder + "roomArea/" + roomName + ".yml");
        data = YamlConfiguration.loadConfiguration(file);

        try {
            data.set("Owner", player.getName());
            data.save(file);
        } catch (IOException e) { e.printStackTrace(); }

    }

    public static void setRoomPrice(String roomName, int roomPrice) {
        File file = new File(Reference.ConfigFolder + "roomArea/" + roomName + ".yml");
        data = YamlConfiguration.loadConfiguration(file);

        try {
            data.set("Price", roomPrice);
            data.save(file);
        } catch (IOException e) { e.printStackTrace(); }

    }

    public static void setDoorPrice(String roomName, int doorPrice) {
        File file = new File(Reference.ConfigFolder + "roomArea/" + roomName + ".yml");
        data = YamlConfiguration.loadConfiguration(file);

        try {
            data.set("DoorPrice", doorPrice);
            data.save(file);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static void setDoorAllow(String roomName, Boolean allow) {
        File file = new File(Reference.ConfigFolder + "roomArea/" + roomName + ".yml");
        data = YamlConfiguration.loadConfiguration(file);

        try {
            data.set("DoorAllow", allow);
            data.save(file);
        } catch (IOException e) { e.printStackTrace(); }

    }

    public static void setRoomAddon(String roomName, ItemStack addonItem, ItemStack itemAddon_needItem) {
        File file = new File(Reference.ConfigFolder + "roomArea/" + roomName + ".yml");
        data = YamlConfiguration.loadConfiguration(file);

        try {
            data.set("Addon.Item", addonItem);
            if (addonItem.getTypeId() == Reference.ITEM_ADDON_CODE) {
                data.set("Addon.NeedItem", itemAddon_needItem);
            }
            data.save(file);
        } catch (IOException e) { e.printStackTrace(); }

    }

    public static void createDoorConfig(String roomName, int doorNumber, Block pos1, Block pos2, String direction, String inandout) {
        File file = new File(Reference.ConfigFolder + "doorArea/" + roomName + "_" + doorNumber + "_" + inandout + ".yml");
        File folder = new File(Reference.ConfigFolder);
        File folder2 = new File(Reference.ConfigFolder + "doorArea/");
        data = YamlConfiguration.loadConfiguration(file);

        try {
            if (!file.exists()) {
                folder.mkdir();
                folder2.mkdir();
                file.createNewFile();
            }
            data.set("Direction", direction);
            data.set("Locate.worldName", pos1.getWorld().getName());
            data.set("Locate.pos1X", pos1.getX());
            data.set("Locate.pos1Y", pos1.getY());
            data.set("Locate.pos1Z", pos1.getZ());
            data.set("Locate.pos2X", pos2.getX());
            data.set("Locate.pos2Y", pos2.getY());
            data.set("Locate.pos2Z", pos2.getZ());
            data.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createHallwayConfig(String roomName, int doorNumber, Block pos1, Block pos2) {
        File file = new File(Reference.ConfigFolder + "hallwayArea/" + roomName + "_" + doorNumber + ".yml");
        File folder = new File(Reference.ConfigFolder);
        File folder2 = new File(Reference.ConfigFolder + "hallwayArea/");
        data = YamlConfiguration.loadConfiguration(file);

        try {
            if (!file.exists()) {
                folder.mkdir();
                folder2.mkdir();
                file.createNewFile();
            }

            data.set("Locate.worldName", pos1.getWorld().getName());
            data.set("Locate.pos1X", pos1.getX());
            data.set("Locate.pos1Y", pos1.getY());
            data.set("Locate.pos1Z", pos1.getZ());
            data.set("Locate.pos2X", pos2.getX());
            data.set("Locate.pos2Y", pos2.getY());
            data.set("Locate.pos2Z", pos2.getZ());
            data.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
