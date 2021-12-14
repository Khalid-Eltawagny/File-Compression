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