import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class pair {
    int value;
    int indx;

    public pair(int v, int i) {
        this.value = v;
        this.indx = i;
    }
}

public class MedianOfMedians {

    private Integer solve(ArrayList<Integer> arr, int l, int r, int k) {
        // if the current size of array is at most 5 then return its median immediately
        if ((r - l + 1) <= 5) {
            List<Integer> sub = arr.subList(l, r + 1);
            Collections.sort(sub);
            return sub.get(k - 1);
        }

        // generating sublists and extract the median of each of them.
        int p = l;
        ArrayList<Integer> medians = new ArrayList<>();
        for (int i = 0; i < (r - l + 1) / 5; i++) {
            List<Integer> sub = arr.subList(p, p + 5);
            Collections.sort(sub);
            medians.add(sub.get(sub.size() / 2));
            p += 5;
        }
        Integer pivot;
        // if size of medians is at most 5 then return the median immediately ,
        // otherwise recurse
        if (medians.size() <= 5) {
            Collections.sort(medians);
            pivot = medians.get(medians.size() / 2);
        } else {
            pivot = solve(medians, 0, medians.size() - 1, medians.size() / 2 + 1);
        }
        // get the index of the pivot , bad ?

        ArrayList<Integer> low = new ArrayList<>() ; 
        ArrayList<Integer> high = new ArrayList<>() ; 
        
        for (int i=l;i<=r;i++){
            if (arr.get(i) < pivot) low.add(arr.get(i)) ;
            else if (arr.get(i) > pivot) high.add(arr.get(i)) ; 
        }

        // int indx = -1;
        // // System.out.println(indx);
        // for (int i = l; i <= r; i++) {
        //     if (arr.get(i) == pivot) {
        //         indx = i;
        //         break;
        //     }
        // }
        // // partitioning
        // Collections.swap(arr, indx, r);
        // int i = l - 1;
        // for (int j = l; j < r; j++) {
        //     if (arr.get(j) <= pivot) {
        //         i++;
        //         Collections.swap(arr, i, j);
        //     }
        // }
        // i++;
        // Collections.swap(arr, i, r);

        // Integer s = i - l + 1;
        Integer s = low.size()+1 ; 
        // see where to go
        if (s == k)
            // return arr.get(i);
            return pivot ; 
        else if (k < s)
            // return solve(arr, l, i - 1, k);
            return solve(low,0,low.size()-1,k) ; 
        else
            // return solve(arr, i + 1, r, k - s);
            return solve(high,0,high.size()-1,k-s) ; 
    }

    public Integer getMedian(ArrayList<Integer> arr) {
        if (arr.size() % 2 != 0)
            return solve(arr, 0, arr.size() - 1, arr.size() / 2 + 1) ;
        else{
            int x = (solve(arr, 0, arr.size() - 1, arr.size() / 2)) ; 
            int y = solve(arr, 0, arr.size() - 1, arr.size() / 2 + 1) ; 
            return (x+y)/2 ; 
        }
    }
}
