import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        int[] arr = {23,45,2,6,8,1,33,89,0};
        mergeSort(arr, 0, arr.length-1);
        Generator generator = new Generator();
        generator.makeFile(2, 3);
        System.out.println(Arrays.toString(arr));

    }

    public static void mergeSort(int[] arr, int start, int end) {
        if (start>=end) {
            return;
        }
        int half = start+(end-start)/2;
        mergeSort(arr, start, half);
        mergeSort(arr, half+1, end);
        merge(arr, start, half, end);

    }

    private static void merge(int[] arr, int left, int middle, int right) {
        int lLength = middle-left+1;
        int rLength = right-middle;
        int[] lArray=new int[lLength];
        int[] rArray=new int[rLength];
        for (int i = 0; i < lLength; i++) {
            lArray[i] = arr[left+i];
        }
        for (int i = 0; i < rLength; i++) {
            rArray[i] = arr[middle+1+i];
        }
        int lc = 0, rc = 0;
        while (lc < lLength && rc < rLength) {
            if (lArray[lc] < rArray[rc]) {
                arr[left++] = lArray[lc++];
            } else {
                arr[left++] = rArray[rc++];
            }
        }
        while (lc < lLength) {
            arr[left++] = lArray[lc++];
        }
        while (rc < rLength) {
            arr[left++] = rArray[rc++];
        }

    }
}
