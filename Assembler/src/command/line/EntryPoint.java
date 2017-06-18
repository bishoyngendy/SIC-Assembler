package command.line;

import com.sun.media.sound.InvalidFormatException;
import engine.GlobalDataSingleton;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * Faculty of Engineering, Alexandria University
 * Computer and Systems Engineering Department
 * CS 372 : Systems and Components Programming
 * Project Phase I : SIC Assembler
 * Created by Marc Magdi on 4/27/17.
 * This is a cmd entry point for the assembler
 */
public class EntryPoint {

    private static String outputFolder = "outPremium";
    private static String inputFolder = "premiumTests";

    public static void main(String[] args) throws FileNotFoundException {
//        testAllData();
//        compareFiles();phase_2_testCases
        Assembler assembler = new AssemblerImp();
        System.out.println("Welcome to our assembler :)");
        Scanner scanner = new Scanner(System.in);
        String fileName;
        while (true) {
            System.out.println("Please choose a relative file to the current directory for the source assembly file");
            String relativePath = scanner.next();
//            String filePath = System.getProperty("user.dir") + File.separator + relativePath;
//            System.out.println(filePath);
            File file = new File(relativePath);
            try {
                assembler.saveData(file);
                fileName = getFile(scanner, "Please choose a relative file to the current directory for the object code file");
                assembler.createObjectFile(fileName);
                break;
            } catch (FileNotFoundException e) {
                System.out.println("Failed to load file data, try again later.");
            } catch (InvalidFormatException e) {
//                System.out.println("Invalid Data !!");
                System.out.println(e.getMessage());
            }
            GlobalDataSingleton.getInstance().resets();
        }

        fileName = getFile(scanner,"Please choose a relative file to the current directory for the listing file");
        assembler.createListingFile(fileName);

        scanner.close();
    }

    private static String getFile(Scanner scanner, String message) {
        System.out.println(message);
        String relativePath = scanner.next();
        return relativePath;
    }

    private static void testAllData() {
//        String filePath = System.getProperty("user.dir")
//                + File.separator + "Assembler" + File.separator + "test" + File.separator;
        String filePath = System.getProperty("user.dir")
                + File.separator + inputFolder + File.separator;

        File folder = new File(filePath);
        Assembler assembler = new AssemblerImp();
        for (File fileLocal : folder.listFiles()) {
            if (fileLocal.isFile()) {
                GlobalDataSingleton.getInstance().resets();
                String fileName = fileLocal.getName();
                File file = new File(inputFolder + File.separator + fileName);
                try {
                    assembler.saveData(file);
                    assembler.createObjectFile(outputFolder + File.separator + fileName.substring(0, fileName.length() - 4));
                    assembler.createListingFile(outputFolder + File.separator + fileName.substring(0, fileName.length() - 4) + "listing");
                } catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Failed to load file data, try again later.");
                } catch (InvalidFormatException e) {
//                System.out.println("Invalid Data !!");
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private static void compareFiles() throws FileNotFoundException {
        String filePathMico = System.getProperty("user.dir")
                + File.separator + inputFolder + File.separator;

        File folder = new File(filePathMico);
        Hashtable<String, File> hashtable = new Hashtable<>();
        for (File fileLocal : folder.listFiles()) {
            if (fileLocal.isFile() && isObjectFile(fileLocal.getName())) {
                hashtable.put(fileLocal.getName().substring(0, fileLocal.getName().length()-4), fileLocal);
            }
        }

        String filePathOurs = System.getProperty("user.dir")
                + File.separator + outputFolder + File.separator;

        File ours = new File(filePathOurs);
        for (File fileLocal : ours.listFiles()) {
            if (fileLocal.isFile()) {
                File micoFile = hashtable.get(fileLocal.getName().substring(0, fileLocal.getName().length()-4));
                StringBuilder str1 = new StringBuilder();
                StringBuilder str2 = new StringBuilder();
                Scanner ourScanner = new Scanner(fileLocal);
                while (ourScanner.hasNext()) {
                    str1.append(ourScanner.next());
                }

                Scanner micoScanner = new Scanner(micoFile);
                while (micoScanner.hasNext()) {
                    str2.append(micoScanner.next());
                }

                System.out.println(str1.toString().equals(str2.toString()) + " " + fileLocal.getName());
            }
        }

        System.out.println(hashtable.size());
    }

    private static boolean isObjectFile(String fileName) {
        return fileName.matches(".+\\.obj");
    }
}
