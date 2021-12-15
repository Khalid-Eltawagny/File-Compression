public class part2_decompression {
    
}
// part2_huffman hf = new part2_huffman();
// int n = 4;
// byte[] b = null;
// try {
//     File file = new File("book1.pdf");
//     FileInputStream fs = new FileInputStream(file);
//     byte[] arr = new byte[(int) file.length()];
//     String byts[] = new String[arr.length];
//     fs.read(arr);
//     fs.close();
//     int i = 0;
//     for (byte bb : arr) {
//         byts[i] = Byte.toString(bb);
//         i++;
//     }
//     String[] fbyts = new String[(int) Math.ceil((double) byts.length / n)];
//     System.out.println("Data size " + byts.length + " bytes.");
//     int j = 0;
//     for (i = 0; i < fbyts.length; i++) {
//         String s = "";
//         for (int c = 0; c < n && j < byts.length; j++, c++) {
//             s += byts[j];
//         }
//         fbyts[i] = s;
//     }
//     // for (String hh : byts)
//     // System.out.println(hh) ;
//     // System.out.println(fbyts[fbyts.length-1]);
//     HashMap<String, String> res = hf.RunHuffman(fbyts);
//     String all = "";
//     for (String bb : fbyts) {
//         all += res.get(bb);
//     }
//     // System.out.println(all);
//     b = new byte[(int) (Math.ceil((double) all.length() / 8))];
//     // System.out.println(all.length() + " " + b.length);
//     for (int ii = 0, p = 0; p < b.length; ii += 8, p++) {
//         String ss = all.substring(ii, ii + 8 >= all.length() ? all.length()
//                 : ii + 8);
//         while (ss.length() < 8)
//             ss += "0";
//         b[p] = (byte) (Integer.parseInt(ss, 2));
//     }

//     System.out.println("Compressed data size " + b.length + " bytes.");
//     // System.out.println(all);
//     // for (byte hh : b)
//     // System.out.println(hh);

// } catch (IOException e) {
//     e.printStackTrace();
// }

// try {

//     // Initialize a pointer
//     // in file using OutputStream
//     OutputStream os = new FileOutputStream("book1.pdf.hc");

//     // Starts writing the bytes in it
//     os.write(b);

//     // Close the file
//     os.close();
// }

// catch (Exception e) {
//     System.out.println("Exception: " + e);
// }



// import java.io.File;
// import java.io.FileInputStream;
// import java.io.FileOutputStream;
// import java.io.IOException;
// import java.io.OutputStream;
// import java.util.HashMap;

// public class part2_compression {
//     private final part2_huffman huffman = new part2_huffman();
//     private HashMap<String, String> codeWord = new HashMap<>();
//     private StringBuilder bitsToBeWritten = new StringBuilder("");

//     public void compress(String filePath, int n) throws IOException {
//         File file = new File(filePath);
//         FileInputStream fs = new FileInputStream(file);
//         byte[] fileBytes = new byte[(int) file.length()];
//         byte[] bytesToBeWritten = null;
//         String[] NbytesAsString = new String[(int) Math.ceil(((double) fileBytes.length) / n)];
//         fs.read(fileBytes);
//         fs.close();
//         for (int i = 0, j = 0; i < NbytesAsString.length; i++) {
//             StringBuilder combinedBytes = new StringBuilder("");
//             for (int c = 0; c < n && j < fileBytes.length; j++, c++) {
//                 String asBinary = Integer.toBinaryString(fileBytes[j]);
//                 if (asBinary.length() > 8)
//                     asBinary = asBinary.substring(asBinary.length() - 8);
//                 else if (asBinary.length() < 8) {
//                     StringBuilder tmp = new StringBuilder("");
//                     tmp.append(asBinary);
//                     String toAdd = "0".repeat(8 - asBinary.length());
//                     tmp.append(toAdd);
//                     asBinary = tmp.toString();
//                 }
//                 combinedBytes.append(asBinary);
//             }
//             NbytesAsString[i] = combinedBytes.toString();
//         }
//         codeWord = huffman.RunHuffman(NbytesAsString);
//         String EOF = codeWord.get("EOF");
//         for (String s : NbytesAsString)
//             bitsToBeWritten.append(codeWord.get(s));
//         bytesToBeWritten = new byte[(int) (Math.ceil((double) bitsToBeWritten.length() / 8))];
//         for (int i = 0, j = 0; j < bytesToBeWritten.length; i += 8, j++) {
//             String _8bits = bitsToBeWritten.substring(i,
//                     i + 8 >= bitsToBeWritten.length() ? bitsToBeWritten.length() : i + 8);
//             while (_8bits.length() < 8)
//                 _8bits += EOF.substring(0, 8 - _8bits.length() >= EOF.length() ? EOF.length() : 8 - _8bits.length());
//             bytesToBeWritten[j] = (byte) (Integer.parseInt(_8bits, 2));
//         }
//         System.out.println("Data size " + fileBytes.length + " bytes.");
//         System.out.println("Compressed data size " + bytesToBeWritten.length + " bytes.");
//         OutputStream os = new FileOutputStream(filePath + ".hc");
//         os.write(bytesToBeWritten);
//         os.close();
//     }

//     public HashMap<String, String> getMap() {
//         return this.codeWord;
//     }
// }
