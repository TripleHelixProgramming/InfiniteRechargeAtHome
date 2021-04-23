package lib.gui.io;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class FileUtil {
    /**
     * Writes <code>String</code> data to a <code>File</code>.
     * Accepts a <code>File</code> and a <code>String</code> for the file name.
     * The extension of the <code>File</code> should be included in the name.
     * 
     * @param filePath target <code>File</code> where <code>data</code> is saved
     * @param fileName name of file
     * @param data <code>String</code> data to be written to <code>filePath</code>
     */
    public static void write(File filePath, String fileName, String data) {
        if (filePath == null) {
            System.out.println("File provided is null.");
            return;
        }
        filePath = new File(filePath, fileName);
        write(filePath, data);
    }

    /**
     * Writes <code>String</code> data to a <code>File</code>.
     * Accpets a single <code>File</code> that represents one file,
     * not a directory.
     * 
     * @param file target directory <code>File</code> where <code>data</code> is saved
     * @param data <code>String</code> data to be written to <code>filePath</code>
     */
    public static void write(File file, String data) {
        if (file == null) {
            System.out.println("File provided is null");
            return;
        }
        try {
            System.out.println("Saving to file: " + file.getCanonicalPath());
            file.getParentFile().mkdirs();
            // if file doesn't exist, then create it
            if (!file.exists()) {
                if(!file.createNewFile()) {
                    System.out.println("Could not create output file " + file.getCanonicalPath());
                    return;
                }
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
        } catch (Exception e) {
            System.out.println("Problem printing file: " + e);
        }
    }
    
    /**
     * Compares the names of two <code>File</code> types.
     * 
     * @param file1 first <code>File</code> to check
     * @param file2 second <code>File</code> to check
     * @return <code>true</code> if both <code>File</code> names are equal, <code>false</code> otherwise.
     */
    public static boolean compareFileNames(File file1, File file2) {
        if ((file1 == null || !file1.exists()) || (file2 == null || !file2.exists())) {
            return false;
        }
        return file1.getName().equals(file2.getName());
    }

    /**
     * Compares two files: returns <code>true</code> if the two files have
     * equal contents, and returns <code>false</code> otherwise.
     * 
     * @param file1 first <code>File</code> to check
     * @param file2 second <code>File</code> to check
     * @return <code>true</code> if the contents are equal and <code>false</code> otherwise
     */
    public static boolean compareFileContents(File file1, File file2) {
        if ((file1 == null || !file1.exists()) || (file2 == null || !file2.exists())) {
            return false;
        }
        try {
            String cleanedFile1 = Files.readString(Paths.get(file1.getAbsolutePath())).trim();
            String cleanedFile2 = Files.readString(Paths.get(file2.getAbsolutePath())).trim();
            return cleanedFile1.equals(cleanedFile2);
        }
        catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Parses a CSV <code>File</code> to a <code>List</code>.
     * CSV Headers are included in the parsed <code>List</code>
     * 
     * @param file <code>File</code> to parse
     */
    public static List<List<String>> parseCSV(File file) {
        if (file == null || !file.exists()) {
            return null;
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<List<String>> list = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                list.add(Arrays.asList(values));
            }
            if (list.size() > 0) {
                while (list.get(list.size() - 1).size() == 1 && list.get(list.size() - 1).get(0).trim().equals("")) {
                    list.remove(list.size() - 1);
                }
            }
            reader.close();
            return list;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public static List<List<Double>> parseDoubleCSV(File file) {
        if (file == null || !file.exists()) {
            return null;
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            List<List<Double>> list = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                List<Double> newValues = new ArrayList<Double>();
                for (String str : values) {
                    newValues.add(Double.parseDouble(str.trim()));
                }
                list.add(newValues);
            }
            reader.close();
            return list;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
