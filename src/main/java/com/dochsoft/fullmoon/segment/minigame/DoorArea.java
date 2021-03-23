package com.dochsoft.fullmoon.segment.minigame;

import com.dochsoft.fullmoon.segment.Reference;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DoorArea {
    public static FileConfiguration data;

    public static HashMap<String, String> doorArea = new HashMap();
    public static ArrayList doorList = new ArrayList();

    public static void createDoorConfig(String chestName, Block pos1, Block pos2, int password) {
        File file = new File(Reference.ConfigFolder + "minigameDoorArea/" + chestName + ".yml");
        File folder = new File(Reference.ConfigFolder);
        File folder2 = new File(Reference.ConfigFolder + "minigameDoorArea/");
        data = YamlConfiguration.loadConfiguration(file);

        try {
            if (!file.exists()) {
                folder.mkdir();
                folder2.mkdir();
                file.createNewFile();
            }
            data.set("Name", chestName);
            data.set("Password", password);
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

    public static void loadChestArea() throws InvalidConfigurationException {
        File file;
        File folderPath = new File(Reference.ConfigFolder + "minigameDoorArea/");
        if (!folderPath.exists()) {
            folderPath.mkdir();
        }
        File[] AreaList = folderPath.listFiles();
        if(AreaList.length > 0) {
            for(int i=0; i < AreaList.length; i++){
                int pos = AreaList[i].toString().lastIndexOf(".");
                String _FileName = AreaList[i].toString().substring(0, pos);
                String RealFileName = _FileName.substring(33, pos);
                System.out.println(RealFileName);

                file = new File(Reference.ConfigFolder + "minigameDoorArea/" + RealFileName + ".yml");
                data = YamlConfiguration.loadConfiguration(file);
                System.out.println(RealFileName);
                try {
                    if (!file.exists()) {
                        folderPath.mkdir();
                        file.createNewFile();
                    }

                    DoorArea.doorList.add(RealFileName);
                    DoorArea.doorArea.put(RealFileName + ".Password", data.get("Password").toString());
                    DoorArea.doorArea.put(RealFileName + ".locateWorld", (String) data.get("Locate.worldName"));
                    DoorArea.doorArea.put(RealFileName + ".locatePos1X", data.get("Locate.pos1X").toString());
                    DoorArea.doorArea.put(RealFileName + ".locatePos1Y", data.get("Locate.pos1Y").toString());
                    DoorArea.doorArea.put(RealFileName + ".locatePos1Z", data.get("Locate.pos1Z").toString());
                    DoorArea.doorArea.put(RealFileName + ".locatePos2X", data.get("Locate.pos2X").toString());
                    DoorArea.doorArea.put(RealFileName + ".locatePos2Y", data.get("Locate.pos2Y").toString());
                    DoorArea.doorArea.put(RealFileName + ".locatePos2Z", data.get("Locate.pos2Z").toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean isPlayerClickChest(String chestName, Location block, String XYZKind) {
        int pos1Loc = 0;
        int pos2Loc = 0;
        int blockLoc = 0;

        if (DoorArea.doorArea.get(chestName + ".locatePos1" + XYZKind) != null) {
            pos1Loc = Integer.parseInt(DoorArea.doorArea.get(chestName + ".locatePos1" + XYZKind));
        }

        if (DoorArea.doorArea.get(chestName + ".locatePos2" + XYZKind) != null) {
            pos2Loc = Integer.parseInt(DoorArea.doorArea.get(chestName + ".locatePos2" + XYZKind));
        }

        if (XYZKind.equalsIgnoreCase("X")) {
            blockLoc = block.getBlockX();
        } else if (XYZKind.equalsIgnoreCase("Y")) {
            blockLoc = block.getBlockY();
        } else if (XYZKind.equalsIgnoreCase("Z")) {
            blockLoc = block.getBlockZ();
        }

        int maxLoc = 0;
        int minLoc = 0;
        if (pos1Loc < pos2Loc) { //a가 높아야하는데 낮을경우 계산
            maxLoc = pos2Loc;
            minLoc = pos1Loc;
        } else if (pos1Loc > pos2Loc) {
            maxLoc = pos1Loc;
            minLoc = pos2Loc;
        } else {
            minLoc = pos1Loc;
            maxLoc = pos1Loc;
        }

        if (minLoc <= blockLoc && blockLoc <= maxLoc) {
            return true;
        }

        return false;
    }
}
