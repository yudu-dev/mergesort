import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        int rowCount = 0;
        int maxRowLength = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to create a file? (Y/N)");
        String next = scanner.nextLine();
        if (next.equals("Y") || next.equals("y")) {
            rowCount = FileSplitSortUtil.gettingInt("number of lines", rowCount, scanner);
            maxRowLength = FileSplitSortUtil.gettingInt("length of each lines", maxRowLength, scanner);
            Generator generator = new Generator();
            generator.makeFile("hugeFileForTest.txt", rowCount, maxRowLength);
            generator.makeRowsLengthFiles("rowCount.txt", rowCount);
            generator.makeRowsLengthFiles("lengthOfLine.txt", maxRowLength);
        } else {
            File file = new File("hugeFileForTest.txt");
            if (file.exists()) {
            } else {
                System.out.println("First you need to create a file.\nThe program will be terminated.");
                System.exit(0);
            }
        }
        rowCount = FileSplitSortUtil.readInteger("rowCount.txt");
        maxRowLength = FileSplitSortUtil.readInteger("lengthOfLine.txt");
        File file = new File("hugeFileForTest.txt");
        FileSplitSortUtil.splitAndSortTempFiles(file, rowCount, maxRowLength);
    }
}
