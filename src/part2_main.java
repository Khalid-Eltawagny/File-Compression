import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class part2_main {
    public static void main(String[] args) throws IOException {
        part2_compression compression = new part2_compression();
        compression.compress("src/book.pdf", 10);
        File file = new File("src/book.pdf.hc");
        System.out.println("LENGHTTTT " + (int) file.length());
        FileInputStream fs = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        fs.read(bytes);
        fs.close();
        String all = "";
        for (byte b : bytes) {
            String asBinary = Integer.toBinaryString(b);
            if (asBinary.length() > 8)
                asBinary = asBinary.substring(asBinary.length() - 8);
            while (asBinary.length() < 8)
                asBinary = "0" + asBinary;
            all += asBinary;
        }
        // System.out.println(all);
        HashMap<String, String> map = compression.getMap();
        HashMap<String, String> COPY = new HashMap<>();
        for (String K : map.keySet()) {
            COPY.put(map.get(K), K);
        }
        ArrayList<Byte> arr = new ArrayList<>();
        String s = "";
        for (int i = 0; i < all.length(); i++) {
            s += all.charAt(i);
            if (COPY.containsKey(s)) {
                String bytes_ = COPY.get(s);
                if (bytes_.compareTo("EOF") == 0)
                    break;
                for (int j = 0; j < bytes_.length(); j += 8) {
                    String B = bytes_.substring(j, j + 8);
                    arr.add((byte) Integer.parseInt(B, 2));
                }
                s = "";
            }
        }
        System.out.println("ORIGINAL SIZE WAS " + arr.size());
        byte[] BB = new byte[arr.size()];
        for (int i = 0; i < arr.size(); i++)
            BB[i] = arr.get(i);
        FileOutputStream os = new FileOutputStream("src/booook.pdf");
        os.write(BB);
        os.close();
    }
}
