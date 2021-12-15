import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

public class part2_compression {
    private final part2_huffman huffman = new part2_huffman();
    private HashMap<String, String> codeWord = new HashMap<>();
    private StringBuilder bitsToBeWritten = new StringBuilder("");

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
        System.out.println("Ratio : " + ((double) fileBytes.length / bytesToBeWritten.length));
        OutputStream os = new FileOutputStream(filePath + ".hc");
        os.write(bytesToBeWritten);
        os.close();
    }

    public HashMap<String, String> getMap() {
        return this.codeWord;
    }
}
