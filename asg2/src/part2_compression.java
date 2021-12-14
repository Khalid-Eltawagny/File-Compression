import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.BitSet;
import java.util.HashMap;

public class part2_compression {
    private final part2_huffman huffman = new part2_huffman();
    private HashMap<String, String> codeWord = new HashMap<>();
    private String bitsToBeWritten = "";
    BitSet b = new BitSet();

    public void compress(String filePath, int n) throws IOException {
        File file = new File(filePath);
        FileInputStream fs = new FileInputStream(file);
        byte[] fileBytes = new byte[(int) file.length()];
        byte[] bytesToBeWritten = null;
        String[] NbytesAsString = new String[(int) Math.ceil(((double) fileBytes.length) / n)];
        fs.read(fileBytes);
        fs.close();
        b = BitSet.valueOf(fileBytes);
        for (int i = 0, j = 0; i < NbytesAsString.length; i++) {
            String combinedBytes = "";
            for (int c = 0; c < n && j < fileBytes.length; j++, c++) {
                combinedBytes += Byte.toString(fileBytes[j]);
            }
            NbytesAsString[i] = combinedBytes;
        }
        codeWord = huffman.RunHuffman(NbytesAsString);
        for (String s : NbytesAsString)
            bitsToBeWritten += codeWord.get(s);
        bytesToBeWritten = new byte[(int) (Math.ceil((double) bitsToBeWritten.length() / 8))];
        for (int i = 0, j = 0; j < bytesToBeWritten.length; i += 8, j++) {
            String _8bits = bitsToBeWritten.substring(i,
                    i + 8 >= bitsToBeWritten.length() ? bitsToBeWritten.length() : i + 8);
            while (_8bits.length() < 8)
                _8bits += "0";
            bytesToBeWritten[j] = (byte) (Integer.parseInt(_8bits, 2));
        }
        System.out.println("Data size " + fileBytes.length + " bytes.");
        System.out.println("Compressed data size " + bytesToBeWritten.length + " bytes.");
        OutputStream os = new FileOutputStream(filePath + ".hc");
        os.write(bytesToBeWritten);
        os.close();
    }
}
