import java.util.ArrayList;
import java.util.Collections;

public class App {
    public static void main(String[] args) throws Exception {
        // int ll = 0 ;
        // while (ll != 10){
        // ArrayList<Integer> arr1 = new ArrayList<>();
        // ArrayList<Integer> arr2 = new ArrayList<>();
        // RandomizedDivideAndConquer solver1 = new RandomizedDivideAndConquer();
        // MedianOfMedians solver2 = new MedianOfMedians();
        // long start = System.currentTimeMillis();
        // for (int i = 1; i <= 1000000 ; i++) {
        // arr1.add(i);
        // arr2.add(i);
        // }
        // Collections.shuffle(arr1);
        // Collections.shuffle(arr2);
        // start = System.currentTimeMillis();
        // System.out.println("Result method 2: " + solver1.getMedian(arr1));
        // System.out.println(System.currentTimeMillis() - start);
        // start = System.currentTimeMillis();
        // System.out.println("Result method 3: " + solver2.getMedian(arr2));
        // long total = System.currentTimeMillis() - start;
        // System.out.println(total);
        // ll++;
        // }
        ClosestPair solver = new ClosestPair();

        Point p1 = new Point(1, 1);
        Point p2 = new Point(3,3);
        Point p3 = new Point(5,5);
        Point p4 = new Point(6, 6);
        Point p5 = new Point(12, 10);
        Point p6 = new Point(3, 4);

        ArrayList<Point> arr = new ArrayList<>();
        arr.add(p1);
        arr.add(p2);
        arr.add(p3);
        arr.add(p4);
        // arr.add(p5);
        // arr.add(p6);

        double res = solver.getClosestPair(arr);
        

    }
}
