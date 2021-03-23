package com.dochsoft.fullmoon.segment.log;

import com.dochsoft.fullmoon.segment.Reference;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WriteLog {

    public static void writeLog(String playerName, String eventName, String eventContent) {
        LocalDate nowDate = LocalDate.now();
        LocalDateTime nowTime = LocalDateTime.now();
        String nowTimeString = nowTime.format(DateTimeFormatter.ofPattern("[yyyy-MM-dd HH-mm-ss] "));
        File file = new File(Reference.ConfigFolder + "log/" + nowDate + ".txt");
        File folder = new File(Reference.ConfigFolder + "log/");

        if (!folder.isDirectory()) {
            folder.mkdirs();
        }

        try {
            FileWriter writer = new FileWriter(file, true);
            writer.write(nowTimeString + playerName + " [" + eventName + "] - " + eventContent);
            writer.write("\r\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}