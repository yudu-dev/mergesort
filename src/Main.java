import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    private static int readInteger(String nameFiles) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(nameFiles));
        String intLine;
        int intValue = 0;
        while ((intLine = reader.readLine()) != null) {
            intValue = Integer.parseInt(intLine);
        }
        return intValue;
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to make a file? (Y/N)");
        String next = scanner.nextLine();
        int rowCount = 100;
        int maxRowLength = 100;
        if (next.equals("Y") || next.equals("y")) {
            System.out.println("Set number of lines in new file");
            try {
                rowCount = Integer.parseInt(scanner.nextLine());
                if (rowCount <= 0) {
                    rowCount = 100;
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                System.out.println("Wrong number of lines. Number of lines will be set '100'");
            }
            System.out.println("Set length of each lines in new file");
            try {
                maxRowLength = Integer.parseInt(scanner.nextLine());
                if (maxRowLength <= 0) {
                    maxRowLength = 100;
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                System.out.println("Wrong length of line. Length of each lines will be set '100'");
            }
            Generator generator = new Generator(rowCount, maxRowLength);
            generator.makeFile("hugeFileForTest.txt");
            generator.makeRowsLengthFiles();
        }

        rowCount = Main.readInteger("rowCount.txt");
        maxRowLength = Main.readInteger("lengthOfLine.txt");

        File file = new File("hugeFileForTest.txt");
        long size = file.length();
        System.out.println("The file size is " + size + " bytes");
        File temporaryFile = new File("tmp_1.txt");
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
            // 200_000 rows and 5_000 chars in file 250_000_000 bytes ~250 MB
            byte[] partOfHugeFile = new byte[rowCount/4*maxRowLength+rowCount/4-1];
            randomAccessFile.seek(0);
            int count = randomAccessFile.read(partOfHugeFile);
            try (OutputStream outputStream = new FileOutputStream(temporaryFile)) {
                outputStream.write(partOfHugeFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String strPartOfHugeFile = new String(partOfHugeFile, 0, count);
            String[] strArray = strPartOfHugeFile.split("\n");
            System.out.println(strPartOfHugeFile + "\n" + "конец");
            for (int i = 0; i< strArray.length; i++) {
                System.out.println(strArray[i]);
            }
            System.out.println(strArray.length);
            Algorithm.mergeSort(strArray, 0, strArray.length-1);
            for (int i = 0; i< strArray.length; i++) {
                System.out.println(strArray[i]);
            }
            } catch (Exception e) {
            e.printStackTrace();
        }

        int[] intArr = {23,45,2,6,8,1,33,89,0};
        Algorithm.mergeSort(intArr, 0, intArr.length-1);
        System.out.println(Arrays.toString(intArr));

    }

}
