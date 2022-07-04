import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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

        long approximateNumberOfCuts = size / 250_000_000;

        if (rowCount % 2 == 0) {
            int countRowInCuts = (int) (rowCount / approximateNumberOfCuts);
            for (int i = 0; i < approximateNumberOfCuts; i++) {
                File temporaryFile = new File("tmp_" + (i + 1) + ".txt");
                try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
                    byte[] partOfHugeFile = new byte[countRowInCuts * maxRowLength + countRowInCuts - 1];
                    randomAccessFile.seek((long) i * partOfHugeFile.length + i);
                    int count = randomAccessFile.read(partOfHugeFile);
                    String strPartOfHugeFile = new String(partOfHugeFile, 0, count);
                    String[] strArray = strPartOfHugeFile.split("\n");
                    Algorithm.mergeSort(strArray, 0, strArray.length - 1);
                    try (OutputStream outputStream = new FileOutputStream(temporaryFile);
                         BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream))) {
                        for (String s : strArray) {
                            bw.write(s);
                            bw.newLine();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            File sortedHugeFile = new File("sortedHugeFile.txt");
            String[] strArrayForComparing = new String[(int) approximateNumberOfCuts];
            BufferedReader[] readers = new BufferedReader[(int) approximateNumberOfCuts];
            for (int i = 0; i < approximateNumberOfCuts; i++) {
                readers[i] = new BufferedReader(new FileReader("tmp_" + (i + 1) + ".txt"));
            }
            boolean noMoreLine = false;
            while (!noMoreLine) {
                int counterOfFiles = 0;
                for (BufferedReader reader : readers) {
                    String line = reader.readLine();
                    counterOfFiles += 1;
                    strArrayForComparing[counterOfFiles-1] = line;
                    if (counterOfFiles == 4) {
                        Algorithm.mergeSort(strArrayForComparing, 0, strArrayForComparing.length - 1);
                        try (OutputStream outputStream = new FileOutputStream(sortedHugeFile, true);
                             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream))) {
                            for (String s : strArrayForComparing) {
                                bw.write(s);
                                bw.newLine();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    if (line == null) {
                        noMoreLine = true;
                        System.out.println("no more line");
                        break;
                    }

                }
            }

        }

        //int[] intArr = {23,45,2,6,8,1,33,89,0};
        //Algorithm.mergeSort(intArr, 0, intArr.length-1);
        //System.out.println(Arrays.toString(intArr));

    }

}
