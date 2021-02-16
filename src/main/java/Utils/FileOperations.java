package Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileOperations {

    private File myFile;
    private String pathToFile;
    private String parentPathToFile;

    public FileOperations(String pathToFile, String nameOfFile) throws IOException {
        myFile = createFile(pathToFile, nameOfFile);
        this.pathToFile = myFile.getPath();
        this.parentPathToFile = myFile.getAbsoluteFile().getParent();
    }

    public FileOperations(String pathToFile){
        this.myFile = new File(pathToFile);
        this.pathToFile = myFile.getPath();
        this.parentPathToFile = myFile.getAbsoluteFile().getParent();
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public String getParentPathToFile() {
        return parentPathToFile;
    }

    public File createFile(String pathToFile, String nameOfFile) throws IOException {
        File newFile = new File(pathToFile + File.separator + nameOfFile + ".txt");
        if (newFile.createNewFile()){
            System.out.println("New file created : " + newFile.getName());
        } else {
            System.out.println("File already exist, overriding the file");
        }
        return newFile;
    }


    public void writeToFile(String writeToFile) throws IOException {
        FileWriter fileWriter = new FileWriter(getPathToFile());
        fileWriter.write(writeToFile);
        fileWriter.close();
    }

    public String readFromFile() throws FileNotFoundException {
        StringBuffer stringBuffer = new StringBuffer();
        Scanner myScanner = new Scanner(myFile);
        while (myScanner.hasNextLine()){
            stringBuffer.append(myScanner.nextLine());
            stringBuffer.append(System.getProperty("line.separator"));
        }
        return stringBuffer.toString();
    }


}
