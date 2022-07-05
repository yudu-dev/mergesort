import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to create a file? (Y/N)");
        String next = scanner.nextLine();
        int rowCount = 0;
        int maxRowLength = 0;
        if (next.equals("Y") || next.equals("y")) {
            rowCount = FileSplitSortUtil.gettingInt("number of lines", rowCount, scanner);
            maxRowLength = FileSplitSortUtil.gettingInt("length of each lines", maxRowLength, scanner);
            Generator generator = new Generator(rowCount, maxRowLength);
            generator.makeFile("hugeFileForTest.txt");
            generator.makeRowsLengthFiles();
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
        long size = file.length();
        long approximateNumberOfCuts = size / 250_000_000;
        int countOfTempFiles = 0;

        if (rowCount % 2 == 0) {
            int countRowInCuts = (int) (rowCount / approximateNumberOfCuts);
            for (int i = 0; i < approximateNumberOfCuts; i++) {
                File temporaryFile = new File("tmp_" + (i + 1) + ".temp");
                countOfTempFiles += 1;
                try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
                    byte[] partOfHugeFile = new byte[countRowInCuts * maxRowLength + countRowInCuts - 1];
                    randomAccessFile.seek((long) i * partOfHugeFile.length + i);
                    int count = randomAccessFile.read(partOfHugeFile);
                    String strPartOfHugeFile = new String(partOfHugeFile, 0, count);
                    String[] strArray = strPartOfHugeFile.split("\n");
                    FileSplitSortUtil.mergeSort(strArray, 0, strArray.length - 1);
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
            PrintWriter writer = new PrintWriter(sortedHugeFile);
            writer.print("");
            writer.close();
            String[] strArrayForComparing = new String[(int) approximateNumberOfCuts];
            BufferedReader[] readers = new BufferedReader[(int) approximateNumberOfCuts];
            for (int i = 0; i < approximateNumberOfCuts; i++) {
                readers[i] = new BufferedReader(new FileReader("tmp_" + (i + 1) + ".temp"));
            }
            boolean noMoreLine = false;
            while (!noMoreLine) {
                int counterOfFiles = 0;
                for (BufferedReader reader : readers) {
                    String line = reader.readLine();
                    counterOfFiles += 1;
                    strArrayForComparing[counterOfFiles-1] = line;
                    if (counterOfFiles == countOfTempFiles) {
                        FileSplitSortUtil.mergeSort(strArrayForComparing, 0, strArrayForComparing.length - 1);
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
            try {
                for (int i = 0; i < approximateNumberOfCuts; i++) {
                    File temporaryFile = new File("tmp_" + (i + 1) + ".temp");
                    temporaryFile.delete();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
