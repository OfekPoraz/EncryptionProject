package Utils;

import Keys.Key;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileOperations {

    private File myFile;
    private String pathToFile;
    private String parentPathToFile;
    private String fileName;

    public FileOperations(String pathToFile, String nameOfFile) throws IOException, NullPointerException{
        myFile = createFile(pathToFile, nameOfFile);
        this.pathToFile = myFile.getPath();
        this.parentPathToFile = myFile.getAbsoluteFile().getParent();
        this.fileName = myFile.getName();
    }

    public FileOperations(String pathToDir, String nameOfDir, Boolean isDir){
        myFile = createDirectory(pathToDir, nameOfDir);
        this.pathToFile = myFile.getPath();
        this.parentPathToFile = myFile.getAbsoluteFile().getParent();
        this.fileName = myFile.getName();
    }

    public FileOperations(String pathToFile){
        this.myFile = new File(pathToFile);
        this.pathToFile = myFile.getPath();
        this.parentPathToFile = myFile.getAbsoluteFile().getParent();
        this.fileName = myFile.getName();
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public String getParentPathToFile() {
        return parentPathToFile;
    }

    public File createFile(String pathToFile, String nameOfFile) throws IOException, NullPointerException {
        File newFile = new File(pathToFile + File.separator + nameOfFile + ".txt");
        if (newFile.createNewFile()){
            System.out.println("New file created : " + newFile.getName());
        } else {
            System.out.println("File already exist, overriding the file");
        }
        return newFile;
    }


    public void writeToFile(String writeToFile) throws IOException, NullPointerException {
        FileWriter fileWriter = new FileWriter(getPathToFile());
        fileWriter.write(writeToFile);
        fileWriter.close();
    }

    public String readFromFile() throws FileNotFoundException, NullPointerException {
        StringBuffer stringBuffer = new StringBuffer();
        Scanner myScanner = new Scanner(myFile);
        while (myScanner.hasNextLine()){
            stringBuffer.append(myScanner.nextLine());
            if (myScanner.hasNextLine()) {
                stringBuffer.append(System.lineSeparator());
            }
        }
        return stringBuffer.toString();
    }

    public boolean compareFilesByString(String pathToFileOne, String pathToFileTwo) throws IOException, NullPointerException {
        byte[] file1Bytes = Files.readAllBytes(Paths.get(pathToFileOne));
        byte[] file2Bytes = Files.readAllBytes(Paths.get(pathToFileTwo));

        String file1 = new String(file1Bytes, StandardCharsets.UTF_8);
        String file2 = new String(file2Bytes, StandardCharsets.UTF_8);

        return file1.equals(file2);
    }

    public File createDirectory(String pathToDirectory, String nameOfDirectory){
        File newDir = new File(pathToDirectory + File.separator + nameOfDirectory);
        if (newDir.mkdir()){
            System.out.println("New directory Created: " + newDir.getName());
        } else {
            System.out.println("Directory already exist, overriding it");
        }
        return newDir;
    }


    public String[] getFilesFromDirectoryTxt(){
        FilenameFilter filter = (dir, name) -> (name.endsWith(".txt"));
        return this.myFile.list(filter);
    }

}
