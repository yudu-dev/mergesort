public class Algorithm {
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

    public static void mergeSort(int[] intArr, int start, int end) {
        if (start>=end) {
            return;
        }
        int half = start+(end-start)/2;
        mergeSort(intArr, start, half);
        mergeSort(intArr, half+1, end);
        merge(intArr, start, half, end);
    }

    private static void merge(int[] intArr, int left, int middle, int right) {
        int lLength = middle-left+1;
        int rLength = right-middle;
        int[] lArray = new int[lLength];
        int[] rArray = new int[rLength];
        for (int i = 0; i < lLength; i++) {
            lArray[i] = intArr[left+i];
        }
        for (int i = 0; i < rLength; i++) {
            rArray[i] = intArr[middle+1+i];
        }
        int lc = 0, rc = 0;
        while (lc < lLength && rc < rLength) {
            if (lArray[lc] < rArray[rc]) {
                intArr[left++] = lArray[lc++];
            } else {
                intArr[left++] = rArray[rc++];
            }
        }
        while (lc < lLength) {
            intArr[left++] = lArray[lc++];
        }
        while (rc < rLength) {
            intArr[left++] = rArray[rc++];
        }

    }

}
