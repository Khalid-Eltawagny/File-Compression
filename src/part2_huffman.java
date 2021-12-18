import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.PriorityQueue;

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

    public HashMap<BitSet, String> RunHuffmanV2(String filePath, int n) throws IOException {
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream fs = new BufferedInputStream(fis);

        while (fs.available() > 0) {
            byte[] bytes = fs.readNBytes(30000);
            int sz = (int) Math.ceil(((double) bytes.length) / n);
            for (int i = 0, j = 0; i < sz; i++) {
                ArrayList<Byte> arr = new ArrayList<>();
                for (int c = 0; c < n && j < bytes.length; j++, c++)
                    arr.add(bytes[j]);
                byte[] combinedBytes = new byte[arr.size()];
                for (int l = 0; l < arr.size(); l++)
                    combinedBytes[l] = arr.get(l);
                BitSet b = BitSet.valueOf(combinedBytes);
                if (!cnt.containsKey(b))
                    cnt.put(b, 1);
                else
                    cnt.put(b, cnt.get(b) + 1);
            }
        }
        fs.close();
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

    public HashMap<BitSet, String> RunHuffman(BitSet bytes[]) {
        // calculating frequencies .
        for (BitSet s : bytes) {
            if (!cnt.containsKey(s))
                cnt.put(s, 1);
            else
                cnt.put(s, cnt.get(s) + 1);
        }
        // iterating over all bytes , create an information node for each byte and push
        // it into pq .
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
