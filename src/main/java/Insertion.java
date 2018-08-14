public class Insertion {
    public void sort(int[] arr) {
        int i = 0;
        
        while (i < arr.length - 1) {
            if (less(arr, i+1, i)) {
                exch(arr, i, i + 1);
                if (i > 0) i--;
            } else {
                i++;
            }
        }
    }

    private static boolean less(int[] arr, int x, int y) {
        return arr[x] < arr[y];
    }

    private static void exch(int[] arr, int i, int j) {
        int x = arr[i];
        arr[i] = arr[j];
        arr[j] = x;
    }
}