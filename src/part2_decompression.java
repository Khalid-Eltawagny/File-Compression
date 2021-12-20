import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class part2_decompression {

    private HashMap<String, String> map = new HashMap<>();
    private HashMap<String, String> reversedMap = new HashMap<>();
    int l = 0, n = 0;

    private void handleLine(String Line) {
        StringTokenizer st = new StringTokenizer(Line);
        Line = null;
        l = Integer.valueOf(st.nextToken().split("[:]")[1]);
        n = Integer.valueOf(st.nextToken().split("[:]")[1]);

        String part = "";
        StringTokenizer com;
        StringBuilder sb;

        while (st.hasMoreTokens()) {
            part = st.nextToken();
            String bytesCode[] = part.split("[:]");
            com = new StringTokenizer(bytesCode[0], ",");
            sb = new StringBuilder();
            while (com.hasMoreTokens()) {
                String b = com.nextToken();
                if (b.compareTo("EOF") == 0) {
                    sb.append("EOF");
                    break;
                }
                byte byte_ = Byte.valueOf(b);
                String asBinary = Integer.toBinaryString(byte_ & 0xff);
                if (asBinary.length() > 8)
                    asBinary = asBinary.substring(asBinary.length() - 8);
                if (asBinary.length() < 8) {
                    asBinary = "0".repeat(8 - asBinary.length()) + asBinary;
                }
                sb.append(asBinary);
            }
            String s = sb.toString();
            map.put(s, bytesCode[1]);
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
        for (String K : map.keySet())
            reversedMap.put(map.get(K), K);

        StringBuilder bitsToBeWritten = new StringBuilder("");

        for (byte b : fileBytes) {
            String asBinary = Integer.toBinaryString(b & 0xff);
            if (asBinary.length() > 8)
                asBinary = asBinary.substring(asBinary.length() - 8);
            if (asBinary.length() < 8)
                asBinary = "0".repeat(8 - asBinary.length()) + asBinary;

            bitsToBeWritten.append(asBinary);

            if (bitsToBeWritten.length() > 2000000) {
                String s = pruning(bitsToBeWritten, filePath);
                bitsToBeWritten = new StringBuilder("");
                if (s.length() > 0)
                    bitsToBeWritten.append(s);
            }

        }

        fileBytes = null;

        map = new HashMap<>();

        ArrayList<Byte> originalBytes = new ArrayList<>();
        StringBuilder s = new StringBuilder("");
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
        s = new StringBuilder("");
        int actualSize = originalBytes.size() - (n - l);
        fileBytes = new byte[actualSize];
        for (i = 0; i < actualSize; i++)
            fileBytes[i] = originalBytes.get(i);
        FileOutputStream os = new FileOutputStream(filePath.substring(0, filePath.length() - 3), true);
        os.write(fileBytes);
        os.close();
    }

    private String pruning(StringBuilder S, String filePath) throws IOException {
        ArrayList<Byte> originalBytes = new ArrayList<>();
        StringBuilder s = new StringBuilder("");
        byte[] fileBytes;
        int last = 0;

        for (int i = 0; i < S.length(); i++) {
            s.append(S.charAt(i));
            if (reversedMap.containsKey(s.toString())) {
                last = i + 1;
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

        fileBytes = new byte[originalBytes.size()];
        for (int c = 0; c < originalBytes.size(); c++)
            fileBytes[c] = originalBytes.get(c);
        
        FileOutputStream os = new FileOutputStream(filePath.substring(0, filePath.length() - 3), true);
        os.write(fileBytes);
        os.close();

        if (last == S.length())
            return "";
        String rem = S.substring(last);
        return rem;
    }
}
