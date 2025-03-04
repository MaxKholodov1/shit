package com.idk.shit.utils;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ScoreManager {
    private static final String  Best_attamp= "best_attamp.txt";
    public static int Load() {
        File file = new File(Best_attamp);

        if (!file.exists()) {
            savebest_attamp(0); 
            return 0;
        }

        try (Scanner scanner = new Scanner(file)) {
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static void savebest_attamp(int score) {
        try (FileWriter writer = new FileWriter(Best_attamp)) {
            writer.write(String.valueOf(score));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 }

