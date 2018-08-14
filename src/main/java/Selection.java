public class Selection {

    public void sort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (less(arr, j, min)) {
                    min = j;
                }
            }
            exch(arr, i, min);
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