import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Scanner;

/*
 * Classe permettant de lire et écrire dans le fichier de sauvegarde
 */

public class SaveSystem {

    public static void initiateSave(boolean override) {
        if (!isThereSaveFile() || override) {
            try {
                new File("src/save/").mkdirs();
                File file = new File("src/save/saveFile");
                PrintWriter writer = new PrintWriter(file);
                String date = LocalDateTime.now().toString();

                writer.println("+======== RetroJump's save file ========+");
                writer.println("|   PLEASE DO NOT WRITE IN THIS FILE.   |");
                writer.println("+=======================================+");
                writer.println();
                writer.println("last_launch:");
                writer.println("   ├── date: " + date.substring(0, 10));
                writer.println("   └── time: " + date.substring(11, 19));
                writer.println();
                writer.println("save:");
                writer.println("   ├── levelID: 1");
                writer.println("   ├── posX: 100");
                writer.println("   ├── posY: 400");
                writer.println("   ├── chrono: 0s");
                writer.println("   └── score: 0");
                writer.close();

            } catch (FileNotFoundException ignored) {}
        }
    }

    public static boolean isThereSaveFile(){
        try {
            new Scanner(new File("src/save/saveFile"));
            return true;
        } catch (FileNotFoundException ignored) {}
        return false;
    }

    public static int getLevelFromSave(){
        try {
            Scanner sc = new Scanner(new File("src/save/saveFile"));
            for (int i = 0; i < 9; i++) {
                sc.nextLine();
            }
            String s = sc.nextLine();
            return Integer.valueOf(s.substring(16));
        } catch (FileNotFoundException ignored) {}
        return -1;
    }

    public static float getPosXFromSave(){
        try {
            Scanner sc = new Scanner(new File("src/save/saveFile"));
            for (int i = 0; i < 10; i++) {
                sc.nextLine();
            }
            String s = sc.nextLine();
            return Float.valueOf(s.substring(13));
        } catch (NumberFormatException | FileNotFoundException e){}
        return -1;
    }

    public static float getPosYFromSave(){
        try {
            Scanner sc = new Scanner(new File("src/save/saveFile"));
            for (int i = 0; i < 11; i++) {
                sc.nextLine();
            }
            String s = sc.nextLine();
            return Float.valueOf(s.substring(13));
        } catch (NumberFormatException | FileNotFoundException e){}
        return -1;
    }

    public static int getChrono(){
        try {
            Scanner sc = new Scanner(new File("src/save/saveFile"));
            for (int i = 0; i < 12; i++) {
                sc.nextLine();
            }
            String s = sc.nextLine();
            return Integer.valueOf(s.substring(15, s.length() - 1));
        } catch (NumberFormatException | FileNotFoundException e){}
        return -1;
    }

    public static int getScore() {
        try {
            Scanner sc = new Scanner(new File("src/save/saveFile"));
            for (int i = 0; i < 13; i++) {
                sc.nextLine();
            }
            String s = sc.nextLine();
            return Integer.valueOf(s.substring(14));
        } catch (NumberFormatException | FileNotFoundException e){}
        return -1;
    }

    public static void saveLevel(int levelID){
        try {
            Scanner sc = new Scanner(new File("src/save/saveFile"));
            String s = "";
            for (int i = 0; i < 9; i++) {
                s += sc.nextLine();
                s += "\n";
            }
            s += "   ├── levelID: " + levelID + "\n";
            sc.nextLine();
            while (sc.hasNext()) {
                s += sc.nextLine();
                s += "\n";
            }
            PrintWriter writer = new PrintWriter(new File("src/save/saveFile"));
            writer.print(s);
            writer.close();
        } catch (FileNotFoundException ignored) {}
    }

    public static void savePosition(float posX, float posY){
        try {
            Scanner sc = new Scanner(new File("src/save/saveFile"));
            String s = "";
            for (int i = 0; i < 10; i++) {
                s += sc.nextLine();
                s += "\n";
            }
            s += "   ├── posX: " + posX + "\n";
            s += "   ├── posY: " + posY + "\n";
            sc.nextLine();
            sc.nextLine();
            while (sc.hasNext()) {
                s += sc.nextLine();
                s += "\n";
            }
            PrintWriter writer = new PrintWriter(new File("src/save/saveFile"));
            writer.print(s);
            writer.close();
        } catch (FileNotFoundException ignored) {}
    }

    public static void saveChrono(int time){
        try {
            Scanner sc = new Scanner(new File("src/save/saveFile"));
            String s = "";
            for (int i = 0; i < 12; i++) {
                s += sc.nextLine();
                s += "\n";
            }
            s += "   ├── chrono: " + time + "s\n";
            sc.nextLine();
            while (sc.hasNext()) {
                s += sc.nextLine();
                s += "\n";
            }
            PrintWriter writer = new PrintWriter(new File("src/save/saveFile"));
            writer.print(s);
            writer.close();
        } catch (FileNotFoundException ignored) {}
    }

    public static void saveSaveTime(){
        try {
            Scanner sc = new Scanner(new File("src/save/saveFile"));
            String date = LocalDateTime.now().toString();

            String s = "";
            for (int i = 0; i < 16; i++) {
                s += sc.nextLine();
                s += "\n";
            }
            s += "   ├── date: " + date.substring(0, 10) + "\n";
            s += "   └── time: " + date.substring(11, 19) + "\n";
            sc.nextLine();
            sc.nextLine();
            while (sc.hasNext()) {
                s += sc.nextLine();
                s += "\n";
            }
            PrintWriter writer = new PrintWriter(new File("src/save/saveFile"));
            writer.print(s);
            writer.close();
        } catch (FileNotFoundException ignored) {}
    }

    public static void saveScore(int id) {
        try {
            Scanner sc = new Scanner(new File("src/save/saveFile"));
            String s = "";
            for (int i = 0; i < 13; i++) {
                s += sc.nextLine();
                s += "\n";
            }
            s += "   └── score: "+ id + "\n";
            sc.nextLine();
            while (sc.hasNext()) {
                s += sc.nextLine();
                s += "\n";
            }
            PrintWriter writer = new PrintWriter(new File("src/save/saveFile"));
            writer.print(s);
            writer.close();
        } catch (FileNotFoundException ignored) {}
    }
}
