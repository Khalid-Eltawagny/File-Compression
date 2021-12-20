import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class part2_compression {

    private part2_huffman huffman;
    private HashMap<BitSet, String> codeWord;
    HashMap<String, BitSet> COPY;
    private StringBuilder bitsToBeWritten;
    private int lastLength = 0;

    private String getNewPath(String inputPath) {
        String p[] = inputPath.split("/");

        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < p.length - 1; i++) {
            output.append(p[i]);
            output.append("/");
        }

        String filename = p[p.length - 1].split("[.]")[0] + "_18010594." + p[p.length - 1].split("[.]")[1];
        output.append(filename);
        return output.toString();
    }

    public void compress(String filePath, int n) throws IOException, InterruptedException {
        String Out = getNewPath(filePath);
        huffman = new part2_huffman();
        codeWord = new HashMap<>();
        bitsToBeWritten = new StringBuilder("");
        File file = new File(filePath);
        lastLength = (int) file.length() % n;
        byte[] bytesToBeWritten = null;
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream fs = new BufferedInputStream(fis);

        codeWord = huffman.RunHuffmanV2(filePath, n);

        String EOF = huffman.getEOF();

        COPY = new HashMap<>();
        for (BitSet K : codeWord.keySet())
            COPY.put(codeWord.get(K), K);

        File f = new File(Out + ".hc");
        BufferedWriter bf = new BufferedWriter(new FileWriter(f));
        bf.write("L:" + String.valueOf(lastLength));
        bf.write(" ");
        bf.write("N:" + String.valueOf(n));
        bf.write(" ");
        bf.write("EOF" + ":" + EOF);
        bf.append(" ");
        int sz = 0;
        int len = 0;
        int mpsz = COPY.size();
        StringBuilder bytes__ = new StringBuilder("");
        for (Map.Entry<String, BitSet> E : COPY.entrySet()) {
            sz++;
            byte[] bytes = E.getValue().toByteArray();
            int actual = bytes.length;

            for (int i = 0; i < bytes.length; i++) {
                bytes__.append(Byte.toString(bytes[i]));
                if (i != bytes.length - 1)
                    bytes__.append(",");
            }

            if (actual == 0) {
                actual++;
                bytes__.append("0");
            }

            while (actual < n) {
                actual++;
                bytes__.append(",0");
            }

            bf.write(bytes__.toString() + ":" + E.getKey());
            if (sz < mpsz)
                bf.append(" ");
            bytes__ = new StringBuilder("");
        }
        bf.append('\n');
        bf.close();

        BitSet bs = new BitSet();
        byte[] subB = new byte[n];
        Queue<Byte> q = new LinkedList<Byte>();

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
                    bitsToBeWritten.append(codeWord.get(bs));
                }
                ArrayList<Byte> arr = new ArrayList<>();
                while (q.size() > 0)
                    arr.add((byte) q.poll());
                byte[] tmp = new byte[arr.size()];
                for (int i = 0; i < arr.size(); i++)
                    tmp[i] = arr.get(i);
                lastLength = tmp.length;
                bs = BitSet.valueOf(tmp);
                bitsToBeWritten.append(codeWord.get(bs));
            } else {
                for (int m = 0; m < bytes.length / n; m++) {
                    for (int c = 0; c < n; c++)
                        subB[c] = (byte) q.poll();
                    bs = BitSet.valueOf(subB);
                    bitsToBeWritten.append(codeWord.get(bs));
                    if (bitsToBeWritten.length() > 200000) {
                        len += 25000;
                        bytesToBeWritten = new byte[25000];
                        String pruning = bitsToBeWritten.substring(200000);
                        StringBuilder _8bits;
                        for (int i = 0, j = 0; j < bytesToBeWritten.length; i += 8, j++) {
                            _8bits = new StringBuilder("");
                            _8bits.append(bitsToBeWritten.substring(i,
                                    i + 8 >= bitsToBeWritten.length() ? bitsToBeWritten.length() : i + 8));
                            while (_8bits.length() < 8)
                                _8bits.append(
                                        EOF.substring(0,
                                                8 - _8bits.length() >= EOF.length() ? EOF.length()
                                                        : 8 - _8bits.length()));
                            bytesToBeWritten[j] = (byte) (Integer.parseInt(_8bits.toString(), 2));
                        }
                        bitsToBeWritten = new StringBuilder("");
                        bitsToBeWritten.append(pruning);
                        OutputStream os = new FileOutputStream(Out + ".hc", true);
                        os.write(bytesToBeWritten);
                        os.close();
                    }

                }

            }
        }
        fs.close();

        // for (BitSet s : Nbytes) {
        // bitsToBeWritten.append(codeWord.get(s));
        // if (bitsToBeWritten.length() > 200000) {
        // len += 25000;
        // bytesToBeWritten = new byte[25000];
        // String pruning = bitsToBeWritten.substring(200000);
        // StringBuilder _8bits;
        // for (int i = 0, j = 0; j < bytesToBeWritten.length; i += 8, j++) {
        // _8bits = new StringBuilder("");
        // _8bits.append(bitsToBeWritten.substring(i,
        // i + 8 >= bitsToBeWritten.length() ? bitsToBeWritten.length() : i + 8));
        // while (_8bits.length() < 8)
        // _8bits.append(
        // EOF.substring(0,
        // 8 - _8bits.length() >= EOF.length() ? EOF.length() : 8 -
        // _8bits.length()));
        // bytesToBeWritten[j] = (byte) (Integer.parseInt(_8bits.toString(), 2));
        // }
        // bitsToBeWritten = new StringBuilder("");
        // bitsToBeWritten.append(pruning);
        // OutputStream os = new FileOutputStream(filePath + ".hc", true);
        // os.write(bytesToBeWritten);
        // os.close();
        // }
        // }

        codeWord = null;
        bytesToBeWritten = new byte[(int) (Math.ceil((double) bitsToBeWritten.length() / 8))];
        StringBuilder _8bits;
        for (int i = 0, j = 0; j < bytesToBeWritten.length; i += 8, j++) {
            _8bits = new StringBuilder("");
            _8bits.append(bitsToBeWritten.substring(i,
                    i + 8 >= bitsToBeWritten.length() ? bitsToBeWritten.length() : i + 8));
            while (_8bits.length() < 8)
                _8bits.append(
                        EOF.substring(0, 8 - _8bits.length() >= EOF.length() ? EOF.length()
                                : 8 -
                                        _8bits.length()));
            bytesToBeWritten[j] = (byte) (Integer.parseInt(_8bits.toString(), 2));
        }
        len += bytesToBeWritten.length;
        System.out.println("N  = " + n);
        System.out.println("Original data size " + file.length() + " bytes.");
        System.out.println("Compressed data size " + len + " bytes.");
        System.out.println("Ratio : " + ((double) len / file.length()));

        OutputStream os = new FileOutputStream(Out + ".hc", true);
        os.write(bytesToBeWritten);
        os.close();
    }

}
