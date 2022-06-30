import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;

public class Generator {

    // принимающий в качестве параметров количество строк и их максимальную длину.

    private static String getRandomString(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i<length; i++){
            int number = random.nextInt(3);
            int numberOfRepeatedChar;
            long result = 0;
            switch(number){
                case 0:
                    // возвращает случайный символ A-Z
                    result = Math.round(Math.random()*25+65);
                    numberOfRepeatedChar = random.nextInt(4)+1;
                    if (i + numberOfRepeatedChar > length) {
                        i = length-1;
                        numberOfRepeatedChar = length-i;
                        sb.append(String.valueOf((char)result).repeat(numberOfRepeatedChar));
                    } else {
                        i += numberOfRepeatedChar-1;
                        sb.append(String.valueOf((char)result).repeat(numberOfRepeatedChar));
                    }
                    break;
                case 1:
                    // возвращает случайный символ a-z
                    result = Math.round(Math.random()*25+97);
                    numberOfRepeatedChar = random.nextInt(4)+1;
                    if (i + numberOfRepeatedChar > length) {
                        i = length-1;
                        numberOfRepeatedChar = length-i;
                        sb.append(String.valueOf((char)result).repeat(numberOfRepeatedChar));
                    } else {
                        i += numberOfRepeatedChar-1;
                        sb.append(String.valueOf((char)result).repeat(numberOfRepeatedChar));
                    }
                    break;
                case 2:
                    numberOfRepeatedChar = random.nextInt(4)+1;
                    // возвращает случайный число 0-9
                    if (i + numberOfRepeatedChar > length) {
                        i = length-1;
                        numberOfRepeatedChar = length-i;
                        sb.append(String.valueOf(new Random().nextInt(10)).repeat(numberOfRepeatedChar));
                    } else {
                        i += numberOfRepeatedChar-1;
                        sb.append(String.valueOf(new Random().nextInt(10)).repeat(numberOfRepeatedChar));
                    }
                    break;
            }
        }
        return sb.toString();
    }


    public void makeFile (int rowCount, int maxRowLength) {
        File file = new File("hugeFileForTest.txt");
        try (OutputStream outputStream = new FileOutputStream(file)){
            for (int i = 0; i<rowCount; i++) {
                String randomString = getRandomString(maxRowLength);
                outputStream.write(randomString.getBytes());
                outputStream.write("\n".getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getRandomString2(int length){
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(3);
            long result=0;
            switch(number){
                case 0:
                    result=Math.round(Math.random()*25+65);
                    sb.append(String.valueOf((char)result));
                    break;
                case 1:
                    result=Math.round(Math.random()*25+97);
                    sb.append(String.valueOf((char)result));
                    break;
                case 2:
                    sb.append(String.valueOf(new Random().nextInt(10)));
                    break;
            }


        }
        return sb.toString();
    }

}
