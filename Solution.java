import java.util.Scanner;

public class Solution {
    public static void MergeSort(int[] arr) {
        if (arr.length <= 1) return;
        mergeSort(arr, 0, arr.length - 1);
    }

    private static void mergeSort(int[] arr, int x, int y) {
        if (x < y) {
            int z = x + (y - x) / 2;
            mergeSort(arr, x, z);
            mergeSort(arr, z + 1, y);
            merge(arr, x, z, y);
        }
    }

    private static void merge(int[] arr, int x, int z, int y) {
        int n1 = z - x + 1;
        int n2 = y - z;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; ++i)
            L[i] = arr[x + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[z + 1 + j];

        int i = 0, j = 0;
        int k = x;

        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }

        while (i < n1) {
            arr[k++] = L[i++];
        }
        while (j < n2) {
            arr[k++] = R[j++];
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = scanner.nextInt();
        }
        scanner.close();

        MergeSort(arr);

        for (int i = 0; i < n; i++) {
            System.out.println(arr[i]);
        }
    }
}
