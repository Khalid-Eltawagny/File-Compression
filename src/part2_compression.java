import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class part2_compression {

    private final part2_huffman huffman = new part2_huffman();
    private HashMap<String, String> codeWord = new HashMap<>();
    private StringBuilder bitsToBeWritten = new StringBuilder("");
    private HashMap<String, Integer> freq;

    public void compress(String filePath, int n) throws IOException {
        File file = new File(filePath);
        FileInputStream fs = new FileInputStream(file);
        byte[] fileBytes = new byte[(int) file.length()];
        byte[] bytesToBeWritten = null;
        String[] NbytesAsString = new String[(int) Math.ceil(((double) fileBytes.length) / n)];
        fs.read(fileBytes);
        fs.close();
        for (int i = 0, j = 0; i < NbytesAsString.length; i++) {
            StringBuilder combinedBytes = new StringBuilder("");
            for (int c = 0; c < n && j < fileBytes.length; j++, c++) {
                String asBinary = Integer.toBinaryString(fileBytes[j]);
                if (asBinary.length() > 8)
                    asBinary = asBinary.substring(asBinary.length() - 8);

                if (asBinary.length() < 8) {
                    asBinary = "0".repeat(8 - asBinary.length()) + asBinary;
                }

                combinedBytes.append(asBinary);
            }
            NbytesAsString[i] = combinedBytes.toString();
        }
        codeWord = huffman.RunHuffman(NbytesAsString);
        freq = huffman.getFreq();
        String EOF = codeWord.get("EOF");
        for (String s : NbytesAsString)
            bitsToBeWritten.append(codeWord.get(s));
        bytesToBeWritten = new byte[(int) (Math.ceil((double) bitsToBeWritten.length() / 8))];
        for (int i = 0, j = 0; j < bytesToBeWritten.length; i += 8, j++) {
            String _8bits = bitsToBeWritten.substring(i,
                    i + 8 >= bitsToBeWritten.length() ? bitsToBeWritten.length() : i + 8);
            while (_8bits.length() < 8)
                _8bits += EOF.substring(0, 8 - _8bits.length() >= EOF.length() ? EOF.length() : 8 - _8bits.length());
            bytesToBeWritten[j] = (byte) (Integer.parseInt(_8bits, 2));
        }
        System.out.println("Original data size " + fileBytes.length + " bytes.");
        System.out.println("Compressed data size " + bytesToBeWritten.length + " bytes.");
        System.out.println("Ratio : " + ((double) bytesToBeWritten.length / fileBytes.length));

        HashMap<String, String> COPY = new HashMap<>();
        for (String K : codeWord.keySet())
            COPY.put(codeWord.get(K), K);

        File ff = new File(filePath + ".hc");
        BufferedWriter bf = new BufferedWriter(new FileWriter(ff));
        for (Map.Entry<String, String> E : COPY.entrySet()) {
            StringBuilder bytes__ = new StringBuilder("");
            String bits = E.getValue();
            if (bits == "EOF") {
                bf.write("EOF" + ":" + E.getKey());
                bf.append(" ");
                continue;
            }
            for (int i = 0; i < bits.length(); i += 8) {
                byte b = (byte) Integer.parseInt(bits.substring(i, i + 8), 2);
                bytes__.append(Byte.toString(b));
                if (i != bits.length() - 8)
                    bytes__.append(",");
            }
            bf.write(bytes__.toString() + ":" + E.getKey());
            bf.append(" ");
        }
        bf.newLine();
        bf.close();
        OutputStream os = new FileOutputStream(filePath + ".hc", true);
        os.write(bytesToBeWritten);
        os.close();
    }

    public HashMap<String, String> getMap() {
        return this.codeWord;
    }

    public HashMap<String, Integer> getFreq() {
        return this.freq;
    }
}
