import java.io.IOException;

public class part2_main {

    static part2_compression compression;
    static part2_decompression decompression;

    static void RUN(String op, String filePath, int n) throws IOException, InterruptedException {
        if (op.compareTo("c") == 0) {
            compression = new part2_compression();
            System.out.println("Compressing ... ");
            compression.compress(filePath, n);
            System.out.println("Compressed Successfully !!");
        } else if (op.compareTo("d") == 0) {
            decompression = new part2_decompression();
            System.out.println("Decompressing ... ");
            decompression.decompress(filePath);
            System.out.println("Decompressed Successfully !!");
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        // new Thread(null, new part2_main(), "", 1 << 120).start();
        if (args.length < 2) {
            System.out.println("Invalid arguments");
            return;
        }

        String op = args[0];
        String Path = args[1];

        long s = System.currentTimeMillis();
        if (op.compareTo("c") == 0) {
            int n = Integer.valueOf(args[2]);
            RUN("c", Path, n);
        } else if (op.compareTo("d") == 0) {
            RUN("d", Path, 0);
        }
        System.out.println("Time taken : " + (System.currentTimeMillis() - s) / (double) 1000 + " seconds.");
    }

}
