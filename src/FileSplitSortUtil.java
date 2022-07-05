import org.jetbrains.annotations.NotNull;
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

public class FileSplitSortUtil {
    public static void mergeSort(String[] strArr, int start, int end) {
        if (start>=end) {
            return;
        }
        int half = start+(end-start)/2;
        mergeSort(strArr, start, half);
        mergeSort(strArr, half+1, end);
        merge(strArr, start, half, end);
    }

    private static void merge(String[] strArr, int left, int middle, int right) {
        int lLength = middle-left+1;
        int rLength = right-middle;
        String[] lArray = new String[lLength];
        String[] rArray = new String[rLength];
        for (int i = 0; i < lLength; i++) {
            lArray[i] = strArr[left+i];
        }
        for (int i = 0; i < rLength; i++) {
            rArray[i] = strArr[middle+1+i];
        }
        int lc = 0, rc = 0;
        while (lc < lLength && rc < rLength) {
            if (lArray[lc].compareTo(rArray[rc]) < 0) {
                strArr[left++] = lArray[lc++];
            } else {
                strArr[left++] = rArray[rc++];
            }
        }
        while (lc < lLength) {
            strArr[left++] = lArray[lc++];
        }
        while (rc < rLength) {
            strArr[left++] = rArray[rc++];
        }
    }

    public static int readInteger(String nameFiles) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(nameFiles));
        String intLine;
        int intValue = 0;
        while ((intLine = reader.readLine()) != null) {
            intValue = Integer.parseInt(intLine);
        }
        return intValue;
    }

    public static int gettingInt(String strValue, int intValue, @NotNull Scanner scanner) {
        System.out.println("Set " + strValue + " in new file");
        try {
            intValue = Integer.parseInt(scanner.nextLine());
            if (intValue <= 0) {
                intValue = 100;
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            System.out.println("Wrong " + strValue + ". " + strValue + "will be set '100'");
        }
        return intValue;
    }

    public static void splitAndSortTempFiles(File file, int rowCount, int maxRowLength) throws IOException {

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
            FileSplitSortUtil.makeSortedFile((int) approximateNumberOfCuts, countOfTempFiles);
            // function of maksorted
        }

    }

    private static void makeSortedFile(int approximateNumberOfCuts, int countOfTempFiles) throws IOException {
        File sortedHugeFile = new File("sortedHugeFile.txt");
        PrintWriter writer = new PrintWriter(sortedHugeFile);
        writer.print("");
        writer.close();
        String[] strArrayForComparing = new String[approximateNumberOfCuts];
        BufferedReader[] readers = new BufferedReader[approximateNumberOfCuts];
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
                    break;
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

}
