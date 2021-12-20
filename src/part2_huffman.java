import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

class Node implements Comparable<Node> {
    BitSet byte_;
    int freq;
    Node left;
    Node right;

    Node(BitSet b, int freq) {
        this.byte_ = b;
        this.freq = freq;
    }

    @Override
    public int compareTo(Node x) {

        return this.freq - x.freq;
    }
}

public class part2_huffman {

    private Queue<Byte> q = new LinkedList<Byte>();
    private PriorityQueue<Node> pq = new PriorityQueue<>();
    private HashMap<BitSet, Integer> cnt = new HashMap<>();
    private HashMap<BitSet, String> map = new HashMap<>();
    private String EOF = "";

    private void populatingMap(Node root, String path) {
        if (root.left == null && root.right == null) {
            if (root.byte_ == null) {
                EOF = path;
                return;
            }
            map.put(root.byte_, path);
            return;
        }

        populatingMap(root.right, path + "1");
        populatingMap(root.left, path + "0");

        return;
    }

    public HashMap<BitSet, String> RunHuffmanV2(String filePath, int n) throws IOException, InterruptedException {
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream fs = new BufferedInputStream(fis);
        BitSet bs = new BitSet();
        byte[] subB = new byte[n];
        while (fs.available() > 0) {
            byte[] bytes = fs.readNBytes(30000000);

            for (byte B : bytes)
                q.add(B);

            if (fs.available() == 0) {
                int lim = q.size() / n;
                for (int i = 0; i < lim; i++) {
                    for (int c = 0; c < n; c++)
                        subB[c] = (byte) q.poll();

                    bs = BitSet.valueOf(subB);
                    if (!cnt.containsKey(bs))
                        cnt.put(bs, 1);
                    else
                        cnt.put(bs, cnt.get(bs) + 1);
                }
                ArrayList<Byte> arr = new ArrayList<>();

                while (q.size() > 0)
                    arr.add((byte) q.poll());

                byte[] tmp = new byte[arr.size()];
                for (int i = 0; i < arr.size(); i++)
                    tmp[i] = arr.get(i);
                bs = BitSet.valueOf(tmp);

                if (!cnt.containsKey(bs))
                    cnt.put(bs, 1);
                else
                    cnt.put(bs, cnt.get(bs) + 1);

            } else {
                for (int i = 0; i < bytes.length / n; i++) {
                    for (int c = 0; c < n; c++)
                        subB[c] = (byte) q.poll();
                    bs = BitSet.valueOf(subB);
                    if (!cnt.containsKey(bs))
                        cnt.put(bs, 1);
                    else
                        cnt.put(bs, cnt.get(bs) + 1);
                }

            }
        }
        subB = null ; 
        bs = null ; 

        fs.close();
        q = null;
        for (BitSet key : cnt.keySet()) {
            int freq = cnt.get(key);
            Node node = new Node(key, freq);
            node.left = null;
            node.right = null;
            pq.add(node);
        }

        cnt = null;
        Node root = null;
        pq.add(new Node(null, 1));

        while (pq.size() > 1) {

            Node n1 = pq.poll();
            Node n2 = pq.poll();

            Node toPush = new Node(null, n1.freq + n2.freq);
            toPush.left = n1;
            toPush.right = n2;

            root = toPush;

            pq.add(toPush);
        }

        populatingMap(root, "");

        return map;
    }

    public HashMap<BitSet, Integer> getFreq() {
        return this.cnt;
    }

    public String getEOF() {
        return this.EOF;
    }

}
