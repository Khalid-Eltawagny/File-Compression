import java.io.IOException;

public class part2_main {
    public static void main(String[] args) throws IOException {
        part2_compression compression = new part2_compression();
        compression.compress("test.txt", 1);
    }
}
