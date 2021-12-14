import java.util.HashMap;
import java.util.PriorityQueue;

class Node implements Comparable<Node> {
    String byte_;
    int freq;
    Node left;
    Node right;

    Node(String b, int freq) {
        this.byte_ = b;
        this.freq = freq;
    }

    @Override
    public int compareTo(Node x) {

        return this.freq - x.freq;
    }
}

public class part2_huffman {

    private PriorityQueue<Node> pq = new PriorityQueue<>();
    private HashMap<String, Integer> cnt = new HashMap<>();
    private HashMap<String, String> map = new HashMap<>();

    private void populatingMap(Node root, String path) {
        if (root.left == null && root.right == null) {
            map.put(root.byte_, path);
            return;
        }

        populatingMap(root.right, path + "1");
        populatingMap(root.left, path + "0");

        return;
    }

    public HashMap<String, String> RunHuffman(String bytes[]) {
        // calculating frequencies .
        for (String s : bytes) {
            if (!cnt.containsKey(s))
                cnt.put(s, 1);
            else
                cnt.put(s, cnt.get(s) + 1);
        }
        // iterating over all bytes , create an information node for each byte and push
        // it into pq .
        for (String key : cnt.keySet()) {
            int freq = cnt.get(key);
            Node node = new Node(key, freq);
            node.left = null;
            node.right = null;
            pq.add(node);
        }

        Node root = null;
        pq.add(new Node("EOF", 1));

        while (pq.size() > 1) {

            Node n1 = pq.poll();
            Node n2 = pq.poll();

            Node toPush = new Node("-", n1.freq + n2.freq);
            toPush.left = n1;
            toPush.right = n2;

            root = toPush;

            pq.add(toPush);
        }

        populatingMap(root, "");
        // for (String s : map.keySet())
        //     System.out.println(s + " : " + map.get(s));
        return map;
    }

}
