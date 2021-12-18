import java.io.IOException;

public class part2_main implements Runnable {

    part2_compression compression;
    part2_decompression decompression;

    void RUN(String op, String filePath) throws IOException, InterruptedException {
        if (op.compareTo("c") == 0) {
            compression = new part2_compression();
            System.out.println("Compressing ... ");
            compression.compress(filePath, 1);
            System.out.println("Compressed Successfully !!");
        } else if (op.compareTo("d") == 0) {
            decompression = new part2_decompression();
            System.out.println("Decompressing ... ");
            decompression.decompress(filePath);
            System.out.println("Decompressed Successfully !!");
        }
    }

    public static void main(String[] args) throws IOException {
        new Thread(null, new part2_main(), "", 1 << 120).start();
    }

    @Override
    public void run() {
        try {
            long s = System.currentTimeMillis();
            RUN("d", "hh.pdf.hc");
            System.out.println("Time taken : " + (System.currentTimeMillis() - s) /
                    (double) 1000 + " seconds.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
