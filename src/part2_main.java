import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class part2_main {
    public static void main(String[] args) throws IOException {
        part2_compression compression = new part2_compression();
        System.out.println("Compressing ... ");
        compression.compress("a.pdf", 10);
        System.out.println("Compressed Successfully !!");
        System.out.println("Decompressing ... ");
        File file = new File("a.pdf.hc");
        FileInputStream fs = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        fs.read(bytes);
        fs.close();
        StringBuilder all = new StringBuilder("");
        for (byte b : bytes) {
            String asBinary = Integer.toBinaryString(b);
            if (asBinary.length() > 8)
                asBinary = asBinary.substring(asBinary.length() - 8);
            if (asBinary.length() < 8)
                asBinary = "0".repeat(8 - asBinary.length()) + asBinary;
            all.append(asBinary);
        }
        // System.out.println(all);
        HashMap<String, String> map = compression.getMap();
        HashMap<String, String> COPY = new HashMap<>();
        for (String K : map.keySet()) {
            COPY.put(map.get(K), K);
        }
        ArrayList<Byte> arr = new ArrayList<>();
        StringBuilder s = new StringBuilder("");
        for (int i = 0; i < all.length(); i++) {
            s.append(all.charAt(i));
            if (COPY.containsKey(s.toString())) {
                String bytes_ = COPY.get(s.toString());
                if (bytes_.compareTo("EOF") == 0)
                    break;
                for (int j = 0; j < bytes_.length(); j += 8) {
                    String B = bytes_.substring(j, j + 8);
                    arr.add((byte) Integer.parseInt(B, 2));
                }
                s = new StringBuilder("");
            }
        }
        byte[] BB = new byte[arr.size()];
        for (int i = 0; i < arr.size(); i++)
            BB[i] = arr.get(i);
        FileOutputStream os = new FileOutputStream("yarab3.pdf");
        os.write(BB);
        os.close();
        System.out.println("Decompressed Successfully.");
    }
}
