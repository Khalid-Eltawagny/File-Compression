import java.io.IOException;


public class part2_main implements Runnable {

    part2_compression compression ; 
    part2_decompression decompression ;
    // private void handleLine(String Line) {
    // System.out.println("IM " + Line);
    // String parts[] = Line.split(" ");
    // for (String part : parts) {
    // String bytesFreq[] = part.split("[:]");
    // String bytes[] = bytesFreq[0].split("[,]");
    // StringBuilder sb = new StringBuilder();
    // for (String b : bytes) {
    // System.out.println(b + " " + bytesFreq[1]);
    // if (b.compareTo("EOF") == 0) {
    // map.put("EOF", bytesFreq[1]);
    // continue;

    // }
    // byte bb = Byte.valueOf(b);
    // String asBinary = Integer.toBinaryString(bb);
    // if (asBinary.length() > 8)
    // asBinary = asBinary.substring(asBinary.length() - 8);
    // if (asBinary.length() < 8) {
    // asBinary = "0".repeat(8 - asBinary.length()) + asBinary;
    // }
    // sb.append(asBinary);
    // }
    // map.put(sb.toString(), bytesFreq[1]);
    // }

    // // System.out.println("/////////");
    // // for (String K : cnt.keySet()) {
    // // System.out.println(K + " : " + cnt.get(K));
    // // }
    // // System.out.println("///////////////");

    // // for (String K : cnt.keySet()) {
    // // System.out.println(K + " : " + cnt.get(K));
    // // }
    // // System.out.println();

    // // map = huf.RunHuffmanV2(cnt);
    // // for (String K : map.keySet()) {
    // // COPY_.put(map.get(K), K);
    // // }

    // for (String K : map.keySet()) {
    // System.out.println(K + "::: " + map.get(K));
    // }

    // // System.out.println("LOL");
    // // for (String K : COPY_.keySet()) {
    // // System.out.println(K + " : " + COPY_.get(K));
    // // }
    // // System.out.println("LOL");

    // }

    void RUN(String op, String filePath) throws IOException {
        if (op.compareTo("c") == 0) {
            compression = new part2_compression();
            System.out.println("Compressing ... ");
            compression.compress(filePath, 2);
            System.out.println("Compressed Successfully !!");
        }else if (op.compareTo("d") == 0){
            decompression = new part2_decompression();
            System.out.println("Decompressing ... ");
            decompression.decompress(filePath);
            System.out.println("Decompressed Successfully !!");
        }
        // String compressedName = "out.pdf";
        // String filename = "book.pdf";
        // System.out.println("######################################");

        // System.out.println("Decompressing ... ");
        // File file = new File(filename + ".hc");
        // FileInputStream fs = new FileInputStream(file);
        // // byte[] bytes = new byte[(int) file.length()];
        // // fs.read(bytes);
        // // fs.close();
        // // StringBuilder all = new StringBuilder("");
        // // for (byte b : bytes) {
        // // String asBinary = Integer.toBinaryString(b);
        // // if (asBinary.length() > 8)
        // // asBinary = asBinary.substring(asBinary.length() - 8);
        // // if (asBinary.length() < 8)
        // // asBinary = "0".repeat(8 - asBinary.length()) + asBinary;
        // // all.append(asBinary);
        // // }

        // // HashMap<String, String> map = compression.getMap();

        // // ArrayList<Byte> arr = new ArrayList<>();
        // // StringBuilder s = new StringBuilder("");
        // // for (int i = 0; i < all.length(); i++) {
        // // s.append(all.charAt(i));
        // // if (COPY.containsKey(s.toString())) {
        // // String bytes_ = COPY.get(s.toString());
        // // if (bytes_.compareTo("EOF") == 0)
        // // break;
        // // for (int j = 0; j < bytes_.length(); j += 8) {
        // // String B = bytes_.substring(j, j + 8);
        // // arr.add((byte) Integer.parseInt(B, 2));
        // // }
        // // s = new StringBuilder("");
        // // }
        // // }
        // // System.out.println("RAIS " + all.length());
        // // byte[] BB = new byte[arr.size()];
        // // for (int i = 0; i < arr.size(); i++)
        // // BB[i] = arr.get(i);
        // // FileOutputStream os = new FileOutputStream(compressedName);
        // // os.write(BB);
        // // os.close();
        // // System.out.println("Decompressed Successfully.");
        // // System.out.println("Writing the map ....");
        // // System.out.println("######################################");

        // // for (String K : COPY.keySet()) {
        // // System.out.println(K + " : " + COPY.get(K));
        // // }

        // byte[] Y = new byte[(int) file.length()];
        // fs.close();
        // fs = new FileInputStream(file);
        // fs.read(Y);
        // fs.close();
        // // System.out.println(Y.length + " 171");
        // // System.out.println("Time is : " + (System.currentTimeMillis() - ss) +
        // "ms.");
        // StringBuilder Line = new StringBuilder();
        // int i = 0;
        // for (byte a : Y) {
        // i++;
        // if (((char) a) == '\n')
        // break;
        // Line.append((char) a);
        // }
        // // System.out.println(Line);
        // // System.out.println(Y.length - i + " ASDASDASSAD");
        // byte[] FINAL = new byte[(int) file.length() - i];
        // for (int c = 0; i < Y.length; i++, c++)
        // FINAL[c] = Y[i];
        // // for (byte b : FINAL)
        // // System.out.println(b);
        // // for (String K : compression.getMap().keySet()){
        // // System.out.println("COR " + K + " : " + compression.getMap().get(K));
        // // }
        // // System.out.println(Line.toString());
        // // // for (; i < Y.length; i++)
        // // // System.out.println(Y[i]);
        // handleLine(Line.toString());
        // // byte[] FINAL = new byte[(int) file.length() - i];
        // // for (int c = 0; i < Y.length; i++, c++)
        // // FINAL[c] = Y[i];
        // // System.out.println("LL " + FINAL.length);
        // StringBuilder all2 = new StringBuilder();
        // for (byte b : FINAL) {
        // String asBinary = Integer.toBinaryString(b);
        // if (asBinary.length() > 8)
        // asBinary = asBinary.substring(asBinary.length() - 8);
        // if (asBinary.length() < 8)
        // asBinary = "0".repeat(8 - asBinary.length()) + asBinary;
        // all2.append(asBinary);
        // }
        // HashMap<String, String> COPY = new HashMap<>();
        // for (String K : map.keySet()) {
        // COPY.put(map.get(K), K);
        // }
        // ArrayList<Byte> a2 = new ArrayList<>();
        // StringBuilder s = new StringBuilder("");
        // for (i = 0; i < all2.length(); i++) {
        // s.append(all2.charAt(i));
        // if (COPY.containsKey(s.toString())) {
        // String bytes_ = COPY.get(s.toString());
        // if (bytes_.compareTo("EOF") == 0)
        // break;
        // for (int j = 0; j < bytes_.length(); j += 8) {
        // String B = bytes_.substring(j, j + 8);
        // a2.add((byte) Integer.parseInt(B, 2));
        // }
        // s = new StringBuilder("");
        // }
        // }

        // // System.out.println("/////////////////////////////////");
        // // for (String K : compression.getMap().keySet())
        // // System.out.println(K + " : " + compression.getMap().get(K));

        // // System.out.println("///////////////////////////");
        // // for (String K : cnt.keySet())
        // // System.out.println(K + " : " + cnt.get(K));
        // // System.out.println(" /////////////////////////////");

        // System.out.println("LL " + a2.size());
        // // System.out.println("RAIS " + all2.length());
        // byte[] FINAL2 = new byte[a2.size()];
        // for (i = 0; i < a2.size(); i++)
        // FINAL2[i] = a2.get(i);

        // // for (String K : map.keySet())
        // // System.out.println(K + " : " + map.get(K));
        // FileOutputStream os = new FileOutputStream(compressedName);
        // // for (byte b : FINAL2)
        // // System.out.println(b + "--");
        // os.write(FINAL2);
        // os.close();
    }

    public static void main(String[] args) throws IOException {
        new Thread(null, new part2_main(), "", 1 << 60).start();
    }

    @Override
    public void run() {
        try {
            RUN("d", "m.pdf.hc");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
