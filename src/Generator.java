import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;

public class Generator {

    /**
     * метод для создания большого неотсортированного файла
     * @param nameOfFile название неотсортированного файла
     * @param rowCount количество строк в файле
     * @param maxRowLength длина каждой строки в файле
     */
    public void makeFile (String nameOfFile, int rowCount, int maxRowLength) {
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

    /**
     * метод для хранения информации о количестве строк или длине каждой строки
     * в уже сгенерированном неотсортированном файле
     * создан для условия повторного запуска программы
     * с уже созданным неотсортированным большим файлом
     * @param filename название создаваемого файла
     * @param intValue переменная, которая будет в нем сохранена
     */
    public void makeRowsLengthFiles (String filename, int intValue) {
        File file = new File(filename);
        try (OutputStream outputStream = new FileOutputStream(file)){
            outputStream.write(String.valueOf(intValue).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * метод для получения рандомно сгенерированной строки длиной length
     * @param length длина создаваемой строки
     */
    private static String getRandomString(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < length; i++){
            // рандомно определяется какой сивол будет добавлен в строку
            int number = random.nextInt(3);
            int countOfRepeatedChars;
            long result = 0;
            switch(number){
                case 0:
                    // возвращает случайный символ из списка заглавных латинских букв A-Z
                    result = Math.round(Math.random()*25+65);
                    // рандомно определяется количество повторений симовола
                    countOfRepeatedChars = random.nextInt(4)+1;
                    // условие для корретного добавления количества повторений случайного символа
                    if (i + countOfRepeatedChars > length) {
                        countOfRepeatedChars = length-i;
                        sb.append(String.valueOf((char)result).repeat(countOfRepeatedChars));
                        i = length-1;
                    } else {
                        i += countOfRepeatedChars-1;
                        sb.append(String.valueOf((char)result).repeat(countOfRepeatedChars));
                    }
                    break;
                case 1:
                    // возвращает случайный символ из списка строчных латинских букв a-z
                    result = Math.round(Math.random()*25+97);
                    // рандомно определяется количество повторений симовола
                    countOfRepeatedChars = random.nextInt(4)+1;
                    // условие для корретного добавления количества повторений случайного символа
                    if (i + countOfRepeatedChars > length) {
                        countOfRepeatedChars = length-i;
                        sb.append(String.valueOf((char)result).repeat(countOfRepeatedChars));
                        i = length-1;
                    } else {
                        i += countOfRepeatedChars-1;
                        sb.append(String.valueOf((char)result).repeat(countOfRepeatedChars));
                    }
                    break;
                case 2:
                    // рандомно определяется количество повторений симовола
                    countOfRepeatedChars = random.nextInt(4)+1;
                    // возвращает случайное число 0-9
                    // условие для корретного добавления количества повторений случайного символа
                    if (i + countOfRepeatedChars > length) {
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
