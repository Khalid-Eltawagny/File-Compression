import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ClosestPair {

    private double distance(Point p1, Point p2) {
        return Math.sqrt(Math.pow((p1.x - p2.x), 2) + Math.pow((p1.y - p2.y), 2));
    }

    private node bruteForce(ArrayList<Point> arr) {
        node res = new node();
        int n = arr.size();
        double min_val = Double.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (distance(arr.get(i), arr.get(j)) < min_val) {
                    min_val = distance(arr.get(i), arr.get(j));
                    res.p1 = arr.get(i);
                    res.p2 = arr.get(j);
                    res.distance = min_val;
                }
            }
        }
        return res;
    }

    private node solveStrip(ArrayList<Point> arr, double d) {
        node res = new node();
        res.distance = Double.MAX_VALUE;
        Collections.sort(arr, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                return p2.y - p1.y;
            }
        });

        int n = arr.size();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n && distance(arr.get(i), arr.get(j)) <= d; j++) {
                d = distance(arr.get(i), arr.get(j));
                res.p1 = arr.get(i);
                res.p2 = arr.get(j);
                res.distance = d;
            }
        }

        return res;
    }

    private node ClosestPair(ArrayList<Point> arrX) {

        int n = arrX.size();

        if (n <= 3)
            return bruteForce(arrX);

        int mid = n / 2;
        Point midPoint = arrX.get(mid);

        ArrayList<Point> left = new ArrayList<>();
        ArrayList<Point> right = new ArrayList<>();

        for (int i = 0; i < mid; i++)
            left.add(arrX.get(i));

        for (int i = mid; i < arrX.size(); i++)
            right.add(arrX.get(i));

        node p1 = ClosestPair(left);
        node p2 = ClosestPair(right);

        node overAll = p1.distance < p2.distance ? p1 : p2;

        double d = overAll.distance;

        // generating strip array ;
        ArrayList<Point> strip = new ArrayList<>();

        for (int i = 0; i < arrX.size(); i++) {
            if (Math.abs(arrX.get(i).x - midPoint.x) <= d) {
                strip.add(arrX.get(i));
            }
        }

        node stripRes = solveStrip(strip, d);

        overAll = overAll.distance < stripRes.distance ? overAll : stripRes;

        return overAll;
    }

    public double getClosestPair(ArrayList<Point> arr) {
        Collections.sort(arr, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                return p1.x - p2.x;
            }
        });
        node res = ClosestPair(arr);

        double maxSideLength = Math.max(Math.abs(res.p1.x - res.p2.x), Math.abs(res.p1.y - res.p2.y));
        return maxSideLength / 2 ; 
    }

}
