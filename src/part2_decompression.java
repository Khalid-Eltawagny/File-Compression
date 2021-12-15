import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class part2_decompression {

    private HashMap<String, String> map = new HashMap<>();
    private HashMap<String, String> reversedMap = new HashMap<>();

    private void handleLine(String Line) {
        String parts[] = Line.split(" ");
        for (String part : parts) {
            String bytesCode[] = part.split("[:]");
            String bytes[] = bytesCode[0].split("[,]");
            StringBuilder sb = new StringBuilder();
            for (String b : bytes) {
                if (b.compareTo("EOF") == 0) {
                    map.put("EOF", bytesCode[1]);
                    continue;
                }
                byte byte_ = Byte.valueOf(b);
                String asBinary = Integer.toBinaryString(byte_);
                if (asBinary.length() > 8)
                    asBinary = asBinary.substring(asBinary.length() - 8);
                if (asBinary.length() < 8) {
                    asBinary = "0".repeat(8 - asBinary.length()) + asBinary;
                }
                sb.append(asBinary);
            }
            map.put(sb.toString(), bytesCode[1]);
        }
    }

    public void decompress(String filePath) throws IOException {
        File file = new File(filePath);
        byte[] allBytes = new byte[(int) file.length()];
        FileInputStream fs = new FileInputStream(file);
        fs.read(allBytes);
        fs.close();

        StringBuilder Line = new StringBuilder();
        int i = 0;
        for (byte a : allBytes) {
            i++;
            if (((char) a) == '\n')
                break;
            Line.append((char) a);
        }
        byte[] fileBytes = new byte[(int) file.length() - i];
        for (int c = 0; i < allBytes.length; i++, c++)
            fileBytes[c] = allBytes[i];

        handleLine(Line.toString());

        StringBuilder bitsToBeWritten = new StringBuilder();
        for (byte b : fileBytes) {
            String asBinary = Integer.toBinaryString(b);
            if (asBinary.length() > 8)
                asBinary = asBinary.substring(asBinary.length() - 8);
            if (asBinary.length() < 8)
                asBinary = "0".repeat(8 - asBinary.length()) + asBinary;
            bitsToBeWritten.append(asBinary);
        }

        for (String K : map.keySet())
            reversedMap.put(map.get(K), K);

        ArrayList<Byte> originalBytes = new ArrayList<>();
        StringBuilder s = new StringBuilder("");
        System.out.println("123");
        for (i = 0; i < bitsToBeWritten.length(); i++) {
            s.append(bitsToBeWritten.charAt(i));
            if (reversedMap.containsKey(s.toString())) {
                String bytes_ = reversedMap.get(s.toString());
                if (bytes_.compareTo("EOF") == 0)
                    break;
                for (int j = 0; j < bytes_.length(); j += 8) {
                    String B = bytes_.substring(j, j + 8);
                    originalBytes.add((byte) Integer.parseInt(B, 2));
                }
                s = new StringBuilder("");
            }
        }
        System.out.println("123");

        fileBytes = new byte[originalBytes.size()];
        for (i = 0; i < originalBytes.size(); i++)
            fileBytes[i] = originalBytes.get(i);

        FileOutputStream os = new FileOutputStream(filePath.substring(0, filePath.length() - 3));
        os.write(fileBytes);
        os.close();
    }
}
