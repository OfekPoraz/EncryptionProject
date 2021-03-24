package utils;

import eventslogger.EncryptionLogger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Scanner;

public class FileOperations {

    private File myFile;
    private String pathToFile;
    private String parentPathToFile;
    private String fileName;
    private EncryptionLogger logger =  new EncryptionLogger();
    private static final EncryptionLogger encryptionLogger = new EncryptionLogger();


    public FileOperations(String pathToDirOrFile, String nameOfDirOrFile, Boolean isDir) throws IOException {
        if (Boolean.TRUE.equals(isDir)){
            myFile = createDirectory(pathToDirOrFile, nameOfDirOrFile);
        } else {
            myFile = createFile(pathToDirOrFile, nameOfDirOrFile);
        }
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

    public String getFileName() {
        return fileName;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public String getParentPathToFile() {
        return parentPathToFile;
    }

    public File createFile(String pathToFile, String nameOfFile) throws IOException, NullPointerException {
        String suffix = nameOfFile.toLowerCase(Locale.ROOT).contains("json") ? ".json" : ".txt";
        File newFile = new File(pathToFile + File.separator + nameOfFile + suffix);
        if (newFile.createNewFile())
        {
            encryptionLogger.writeDebugLog("New File Created: " + getFileName());
        } else {
            encryptionLogger.writeDebugLog("File already exist, overriding file");
        }
        return newFile;
    }


    public synchronized void writeToFile(String writeToFile) throws NullPointerException {
        try(FileWriter fileWriter = new FileWriter(getPathToFile())){
            fileWriter.write(writeToFile);
        } catch (NullPointerException | IOException e){
            logger.writeErrorLog(e.getMessage());
        }
    }


    public synchronized String readFromFile() throws FileNotFoundException, NullPointerException {
        StringBuffer stringBuffer = new StringBuffer();
        try (Scanner myScanner = new Scanner(myFile)) {
            while (myScanner.hasNextLine()) {
                stringBuffer.append(myScanner.nextLine());
                if (myScanner.hasNextLine()) {
                    stringBuffer.append(System.lineSeparator());
                }
            }
            return stringBuffer.toString();
        } catch (FileNotFoundException e) {
            logger.writeErrorLog(e.getMessage());
            throw new FileNotFoundException();
        } catch (NullPointerException e){
            logger.writeErrorLog(e.getMessage());
            throw new NullPointerException();
        }

    }

    public synchronized boolean compareFilesByString(String pathToFileOne, String pathToFileTwo) throws IOException, NullPointerException {
        byte[] file1Bytes = Files.readAllBytes(Paths.get(pathToFileOne));
        byte[] file2Bytes = Files.readAllBytes(Paths.get(pathToFileTwo));

        String file1 = new String(file1Bytes, StandardCharsets.UTF_8);
        String file2 = new String(file2Bytes, StandardCharsets.UTF_8);

        return file1.equals(file2);
    }

    public synchronized File createDirectory(String pathToDirectory, String nameOfDirectory){
        File newDir = new File(pathToDirectory + File.separator + nameOfDirectory);
        if (newDir.mkdir()){
            encryptionLogger.writeDebugLog("Creating new directory: " + getFileName());
        } else {
            encryptionLogger.writeDebugLog("Directory already exist, overriding directory");
        }
        return newDir;
    }


    public String[] getFilesFromDirectoryTxt(){
        FilenameFilter filter = (dir, name) -> (name.endsWith(".txt"));
        return this.myFile.list(filter);
    }

}