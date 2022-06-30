import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Generator {

    // принимающий в качестве параметров количество строк и их максимальную длину.

    public void makeFile (int rowCount, int maxRowLength) {
        File file = new File("hugeFileForTest.txt");
        try (OutputStream outputStream = new FileOutputStream(file)){
            String names = "bcddd36352a\n" + "abcdd33562f\n" + "fgyyyz378\n" + "fghhhh456";
            outputStream.write(names.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
