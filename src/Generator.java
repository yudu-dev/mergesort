import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class Generator {

    // принимающий в качестве параметров количество строк и их максимальную длину.
    private int rowCount;
    private int maxRowLength;

    Generator(int rowCount, int maxRowLength) {
        this.rowCount = rowCount;
        this.maxRowLength = maxRowLength;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getMaxRowLength() {
        return maxRowLength;
    }

    public void setMaxRowLength(int maxRowLength) {
        this.maxRowLength = maxRowLength;
    }

    public void makeFile (String nameOfFile) {
        File file = new File(nameOfFile);
        try (OutputStream outputStream = new FileOutputStream(file)){
            for (int i = 0; i<rowCount; i++) {
                String randomString = getRandomString(maxRowLength);
                if (i < rowCount-1) {
                    outputStream.write(randomString.getBytes());
                    outputStream.write("\n".getBytes());
                } else {
                    outputStream.write(randomString.getBytes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeRowsLengthFiles () {
        File fileForRow = new File("rowCount.txt");
        try (OutputStream outputStream = new FileOutputStream(fileForRow)){
            outputStream.write(String.valueOf(rowCount).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        File fileForLength = new File("lengthOfLine.txt");
        try (OutputStream outputStream = new FileOutputStream(fileForLength)){
            outputStream.write(String.valueOf(maxRowLength).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getRandomString(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < length; i++){
            int number = random.nextInt(3);
            int countOfRepeatedChars;
            long result = 0;
            switch(number){
                case 0:
                    // возвращает случайный символ A-Z
                    result = Math.round(Math.random()*25+65);
                    countOfRepeatedChars = random.nextInt(4)+1;
                    if (i + countOfRepeatedChars > length) {
                        //i = length-1;
                        countOfRepeatedChars = length-i;
                        sb.append(String.valueOf((char)result).repeat(countOfRepeatedChars));
                        i = length-1;
                    } else {
                        i += countOfRepeatedChars-1;
                        sb.append(String.valueOf((char)result).repeat(countOfRepeatedChars));
                    }
                    break;
                case 1:
                    // возвращает случайный тсимвол a-z
                    result = Math.round(Math.random()*25+97);
                    countOfRepeatedChars = random.nextInt(4)+1;
                    if (i + countOfRepeatedChars > length) {
                        //i = length-1;
                        countOfRepeatedChars = length-i;
                        sb.append(String.valueOf((char)result).repeat(countOfRepeatedChars));
                        i = length-1;
                    } else {
                        i += countOfRepeatedChars-1;
                        sb.append(String.valueOf((char)result).repeat(countOfRepeatedChars));
                    }
                    break;
                case 2:
                    countOfRepeatedChars = random.nextInt(4)+1;
                    // возвращает случайное число 0-9
                    if (i + countOfRepeatedChars > length) {
                        //i = length-1;
                        countOfRepeatedChars = length-i;
                        sb.append(String.valueOf(new Random().nextInt(10)).repeat(countOfRepeatedChars));
                        i = length-1;
                    } else {
                        i += countOfRepeatedChars-1;
                        sb.append(String.valueOf(new Random().nextInt(10)).repeat(countOfRepeatedChars));
                    }
                    break;
            }
        }
        return sb.toString();
    }

}
