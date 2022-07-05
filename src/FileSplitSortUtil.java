import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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


}
