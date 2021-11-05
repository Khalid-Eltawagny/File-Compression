import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

public class RandomizedDivideAndConquer {

    private Integer Randomized_Partition(ArrayList<Integer> arr, Integer l, Integer r) {

        Integer randIndx = l + new Random().nextInt(Math.abs(r - l) + 1);
        Integer rand = arr.get(randIndx);
        Collections.swap(arr, randIndx, r);
        int i = l - 1;
        for (int j = l; j < r; j++) {
            if (arr.get(j) <= rand) {
                i++;
                Collections.swap(arr, i, j);
            }
        }
        Collections.swap(arr, i + 1, r);

        return i + 1;
    }

    private Integer solve(ArrayList<Integer> arr, Integer l, Integer r, Integer i) {
        if (l == r)
            return arr.get(l);
        Integer q = Randomized_Partition(arr, l, r);
        Integer k = q - l + 1;
        if (k == i)
            return arr.get(q);
        else if (k > i)
            return solve(arr, l, q - 1, i);
        else
            return solve(arr, q + 1, r, i - k);
    }

    public Integer getMedian(ArrayList<Integer> arr) {
        if (arr.size() % 2 != 0)
            return solve(arr, 0, arr.size() - 1, arr.size() / 2 + 1);
        else
            return (solve(arr, 0, arr.size() - 1, arr.size() / 2) + solve(arr, 0, arr.size() - 1, arr.size() / 2 + 1))/2;
    }
}