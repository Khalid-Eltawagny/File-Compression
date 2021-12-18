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
import java.util.Map;

public class part2_compression {

    private part2_huffman huffman;
    private HashMap<BitSet, String> codeWord;
    HashMap<String, BitSet> COPY;
    private StringBuilder bitsToBeWritten;
    private int lastLength = 0;

    public void compress(String filePath, int n) throws IOException, InterruptedException {
        huffman = new part2_huffman();
        codeWord = new HashMap<>();
        bitsToBeWritten = new StringBuilder("");
        File file = new File(filePath);
        lastLength = (int) file.length() % n;
        byte[] bytesToBeWritten = null;
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream fs = new BufferedInputStream(fis);
        byte[] fileBytes = new byte[(int) file.length()];
        BitSet[] Nbytes = new BitSet[(int) Math.ceil(((double) fileBytes.length) / n)];
        fs.read(fileBytes);
        fs.close();
        for (int i = 0, j = 0; i < Nbytes.length; i++) {
            ArrayList<Byte> arr = new ArrayList<>();
            for (int c = 0; c < n && j < fileBytes.length; j++, c++)
                arr.add(fileBytes[j]);
            if (i == Nbytes.length - 1)
                lastLength = arr.size();
            byte[] combinedBytes = new byte[arr.size()];
            for (int l = 0; l < arr.size(); l++)
                combinedBytes[l] = arr.get(l);
            Nbytes[i] = BitSet.valueOf(combinedBytes);
        }
        fileBytes = null;
        System.out.println("DONE HUFFMAN");
        codeWord = huffman.RunHuffman(Nbytes);

        String EOF = huffman.getEOF();

        COPY = new HashMap<>();
        for (BitSet K : codeWord.keySet())
            COPY.put(codeWord.get(K), K);

        fs.close();

        for (BitSet s : Nbytes)
            bitsToBeWritten.append(codeWord.get(s));

        Nbytes = null;
        codeWord = null;
        bytesToBeWritten = new byte[(int) (Math.ceil((double) bitsToBeWritten.length() / 8))];
        StringBuilder _8bits;
        for (int i = 0, j = 0; j < bytesToBeWritten.length; i += 8, j++) {
            _8bits = new StringBuilder("");
            _8bits.append(bitsToBeWritten.substring(i,
                    i + 8 >= bitsToBeWritten.length() ? bitsToBeWritten.length() : i + 8));
            while (_8bits.length() < 8)
                _8bits.append(
                        EOF.substring(0, 8 - _8bits.length() >= EOF.length() ? EOF.length() : 8 - _8bits.length()));
            bytesToBeWritten[j] = (byte) (Integer.parseInt(_8bits.toString(), 2));
        }

        System.out.println("Original data size " + file.length() + " bytes.");
        System.out.println("Compressed data size " + bytesToBeWritten.length + " bytes.");
        System.out.println("Ratio : " + ((double) bytesToBeWritten.length / file.length()));

        File ff = new File(filePath + ".hc");
        BufferedWriter bf = new BufferedWriter(new FileWriter(ff));
        bf.write("L:" + String.valueOf(lastLength));
        bf.write(" ");
        bf.write("N:" + String.valueOf(n));
        bf.write(" ");
        bf.write("EOF" + ":" + EOF);
        bf.append(" ");
        int sz = 0;
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
        OutputStream os = new FileOutputStream(filePath + ".hc", true);
        os.write(bytesToBeWritten);
        os.close();
    }

    // public HashMap<BitSet, String> getMap() {
    // return this.codeWord;
    // }

    // public HashMap<BitSet, Integer> getFreq() {
    // // return this.freq;
    // return null;
    // }
}
