package com.dochsoft.fullmoon.segment.room.setRoomArea;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class RoomArea {
    public static HashMap<UUID, String> playerNowAreaName = new HashMap();

    public static HashMap<UUID, Block> areaSetLeftClick = new HashMap();
    public static HashMap<UUID, Block> areaSetRightClick = new HashMap();
    public static ArrayList roomAreaList = new ArrayList();
    public static HashMap<String, String> roomArea = new HashMap();
    public static HashMap<String, String> doorArea = new HashMap();
    public static HashMap<String, String> hallwayArea = new HashMap();

    private static int minLoc;
    private static int maxLoc;

    public static ArrayList correctAreaListX = new ArrayList();
    public static ArrayList correctAreaListY = new ArrayList();
    public static ArrayList correctAreaListZ = new ArrayList();

    public static String inAreaCheck(Location playerLoc) {
        ArrayList XCheck = inAreaCalculation(playerLoc.getBlockX(), "X");
        ArrayList YCheck = inAreaCalculation(playerLoc.getBlockY(), "Y");
        ArrayList ZCheck = inAreaCalculation(playerLoc.getBlockZ(), "Z");

        for(int i=0; i < XCheck.size(); i++) {
            if (YCheck.contains(XCheck.get(i))) {
                for(int i2=0; i2 < XCheck.size(); i2++) {
                    if (ZCheck.contains(XCheck.get(i))) {
                        if (!XCheck.get(i).equals("notHere")) {
                            return String.valueOf(XCheck.get(i));
                        }
                    }
                }
            }
        }
        return "false";
    }

    public static ArrayList inAreaCalculation(int playerLoc, String XYZKind) {
        if (XYZKind.equals("X")) {
            correctAreaListX.clear();
        } else if (XYZKind.equals("Y")) {
            correctAreaListY.clear();
        } else if (XYZKind.equals("Z")) {
            correctAreaListZ.clear();
        }
        for (int i=0; i < roomAreaList.size(); i++) {
            String areaLoc1 = roomArea.get(roomAreaList.get(i) + ".locatePos1" + XYZKind); //기본적으로 계산을 첫 번째가 높은 것으로 가졍하고 계산함
            String areaLoc2 = roomArea.get(roomAreaList.get(i) + ".locatePos2" + XYZKind);
            locateMinMaxCal(Integer.parseInt(areaLoc1), Integer.parseInt(areaLoc2));
            //System.out.println("maxLoc: " + maxLoc + ", minLoc: " + minLoc + ", playerLoc: " + playerLoc + ", XYZKind: " + XYZKind);
            if (maxLoc + 1 >= playerLoc && playerLoc >= minLoc) {
                if (XYZKind.equals("Y")) {
                    correctAreaListY.add(roomAreaList.get(i).toString());
                }
            }
            if (maxLoc >= playerLoc && playerLoc >= minLoc) {
                if (XYZKind.equals("X")) {
                    correctAreaListX.add(roomAreaList.get(i).toString());
                } else if (XYZKind.equals("Z")) {
                    correctAreaListZ.add(roomAreaList.get(i).toString());
                }
            }
        }

        if (XYZKind.equals("X")) {
            ArrayList sizeCkeckCompleteX = listSizeCheck(correctAreaListX, XYZKind);
            return sizeCkeckCompleteX;
        } else if (XYZKind.equals("Y")) {
            ArrayList sizeCkeckCompleteY = listSizeCheck(correctAreaListY, XYZKind);
            return sizeCkeckCompleteY;
        } else if (XYZKind.equals("Z")) {
            ArrayList sizeCkeckCompleteZ = listSizeCheck(correctAreaListZ, XYZKind);
            return sizeCkeckCompleteZ;
        }

        ArrayList temp = new ArrayList();
        return temp;
    }

    private static ArrayList listSizeCheck(ArrayList arrayList, String XYZKind) {
        if (arrayList.size() > 0) {
            if (XYZKind.equals("X")) {
                return correctAreaListX;
            } else if (XYZKind.equals("Y")) {
                return correctAreaListY;
            } else if (XYZKind.equals("Z")) {
                return correctAreaListZ;
            }
        } else {
            if (XYZKind.equals("X")) {
                correctAreaListX.clear();
            } else if (XYZKind.equals("Y")) {
                correctAreaListY.clear();
            } else if (XYZKind.equals("Z")) {
                correctAreaListZ.clear();
            }
            if (XYZKind.equals("X")) {
                correctAreaListX.add("notHere"); return correctAreaListX;
            } else if (XYZKind.equals("Y")) {
                correctAreaListY.add("notHere"); return correctAreaListY;
            } else if (XYZKind.equals("Z")) {
                correctAreaListZ.add("notHere"); return correctAreaListZ;
            }
        }
        return arrayList;
    }

    private static void locateMinMaxCal(int a, int b) {
        if (a < b) { //a가 높아야하는데 낮을경우 계산
            maxLoc = b;
            minLoc = a;
        } else if (a > b) {
            maxLoc = a;
            minLoc = b;
        }
    }

}
