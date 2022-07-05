import org.jetbrains.annotations.NotNull;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class FileSplitSortUtil {
    public static void mergeSort(String[] strArr, int start, int end) {
        if (start>=end) {
            return;
        }
        int half = start+(end-start)/2;
        mergeSort(strArr, start, half);
        mergeSort(strArr, half+1, end);
        merge(strArr, start, half, end);
    }

    private static void merge(String[] strArr, int left, int middle, int right) {
        int lLength = middle-left+1;
        int rLength = right-middle;
        String[] lArray = new String[lLength];
        String[] rArray = new String[rLength];
        for (int i = 0; i < lLength; i++) {
            lArray[i] = strArr[left+i];
        }
        for (int i = 0; i < rLength; i++) {
            rArray[i] = strArr[middle+1+i];
        }
        int lc = 0, rc = 0;
        while (lc < lLength && rc < rLength) {
            if (lArray[lc].compareTo(rArray[rc]) < 0) {
                strArr[left++] = lArray[lc++];
            } else {
                strArr[left++] = rArray[rc++];
            }
        }
        while (lc < lLength) {
            strArr[left++] = lArray[lc++];
        }
        while (rc < rLength) {
            strArr[left++] = rArray[rc++];
        }
    }

    /**
     * метод для чтения информации о количестве строк или длине каждой строки
     * в уже сгенерированном неотсортированном файле
     * создан для условия повторного запуска программы
     * с уже созданным неотсортированным большим файлом
     * @param nameFiles название читаемого файла
     * @return intValue возвращает прочтенное целое число из файла
     * @throws IOException выдает ошибку при проблемах с чтением файла
     */
    public static int readInteger(String nameFiles) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(nameFiles));
        String intLine;
        int intValue = 0;
        while ((intLine = reader.readLine()) != null) {
            intValue = Integer.parseInt(intLine);
        }
        return intValue;
    }

    /**
     * метод для формирования запроса в консоле по
     * получению количества строк и длины каджой строки
     * от пользователя
     * @param strValue строка с информацией передаваемой в консоль
     * @param intValue переменная, получаемая из консоли
     * @param scanner сканер для четния информации из консоли
     */
    public static int gettingInt(String strValue, int intValue, @NotNull Scanner scanner) {
        System.out.println("Set " + strValue + " in new file");
        try {
            intValue = Integer.parseInt(scanner.nextLine());
            if (intValue <= 0) {
                intValue = 100;
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            System.out.println("Wrong " + strValue + ". " + strValue + "will be set '100'");
        }
        return intValue;
    }

    /**
     * метод для разбиения, сортировки и записи на диск временных файлов
     * @param file большой неотсортированный файл
     * @param rowCount количество строк в передаваемом файле
     * @param maxRowLength длина каждой строки в передаваемом файле
     */
    public static void splitAndSortTempFiles(File file, int rowCount, int maxRowLength) throws IOException {

        long size = file.length();
        // определяется количество временных файлов по ~250MB
        long numberOfCuts = size / 250_000_000;
        // условие запуска метода разбиения большого файла на временные
        // при количестве строк кратном количеству временных файлов
        if (rowCount % numberOfCuts == 0) {
            // рассчитывается количество строк в каждом из временных файлов
            int countRowInCuts = (int) (rowCount / numberOfCuts);
            // запуск цикла для создания, отсортировки и записи на диск временных файлов
            for (int i = 0; i < numberOfCuts; i++) {
                File temporaryFile = new File("tmp_" + (i + 1) + ".temp");
                // countOfTempFiles += 1;
                try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
                    // определяется размер байтового массива, которая будет считана с основного
                    // неотсортированного файла
                    byte[] partOfHugeFile = new byte[countRowInCuts * maxRowLength + countRowInCuts - 1];
                    // находится позиция, с которой будет прочитана часть файла
                    randomAccessFile.seek((long) i * partOfHugeFile.length + i);
                    int count = randomAccessFile.read(partOfHugeFile);
                    // байтовый массив преобразовывается в одну большую строку
                    String strPartOfHugeFile = new String(partOfHugeFile, 0, count);
                    // большая строка разбивается на отдельные строки
                    // формируется массив строк для последующей сортировки
                    String[] strArray = strPartOfHugeFile.split("\n");
                    // массив строк сортируется с помощью алгоритма MergeSort
                    FileSplitSortUtil.mergeSort(strArray, 0, strArray.length - 1);
                    // отсортированный массив строк записывается во временный файл
                    try (OutputStream outputStream = new FileOutputStream(temporaryFile);
                         BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream))) {
                        for (String s : strArray) {
                            bw.write(s);
                            bw.newLine();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // запуск метода для создания большого отсортированного файла из временных файлов
            FileSplitSortUtil.makeSortedFile((int) numberOfCuts);
        } else {
            System.out.println("Количество строк некратно количеству временных файлов");
            // тут должно быть описано условие запуска метода разбиения большого файла на временные
            // при количестве строк некратному количеству временных файлов
        }
    }

    /**
     * логика по сортировке и записи на диск временных файлов
     * @param numberOfCuts количество временных файлов
     */
    private static void makeSortedFile(int numberOfCuts) throws IOException {
        // название формируемого большого отсортированного файла
        File sortedHugeFile = new File("sortedHugeFile.txt");
        // 131-133 фрагмент кода для очистки отсортированного файла
        // при повторном запуске программы
        PrintWriter writer = new PrintWriter(sortedHugeFile);
        writer.print("");
        writer.close();
        // создание массива строк размером совпадающим с количеством временных файлов
        String[] strArrayForComparing = new String[numberOfCuts];
        // создание массива потоков чтения временных файлов
        BufferedReader[] readers = new BufferedReader[numberOfCuts];
        for (int i = 0; i < numberOfCuts; i++) {
            readers[i] = new BufferedReader(new FileReader("tmp_" + (i + 1) + ".temp"));
        }
        // переменная для остановки цикла чтения временных файлов
        boolean noMoreLine = false;
        while (!noMoreLine) {
            // переменная для запуска условия по сортировке и записи строк в большой отсортированный файл
            int counterOfFiles = 0;
            // цикл внутри массива потоков чтения временных файлов
            for (BufferedReader reader : readers) {
                // переменная для последующей записи прочтенной строки в массив строк
                String line = reader.readLine();
                counterOfFiles += 1;
                // запись строки из потока чтения временного файла в массив
                strArrayForComparing[counterOfFiles-1] = line;
                // условие по сортировке и записи строк в большой отсортированный файл
                if (counterOfFiles == numberOfCuts) {
                    // сортировка строк в массиве методов MergeSort
                    FileSplitSortUtil.mergeSort(strArrayForComparing, 0, strArrayForComparing.length - 1);
                    // отсортированный массив строк записывается в большой отсортированный файл
                    try (OutputStream outputStream = new FileOutputStream(sortedHugeFile, true);
                         BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream))) {
                        for (String s : strArrayForComparing) {
                            bw.write(s);
                            bw.newLine();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                // условие для остановки цикла чтения временных файлов
                if (line == null) {
                    noMoreLine = true;
                    break;
                }
                // удаление временных файлов
                try {
                    for (int i = 0; i < numberOfCuts; i++) {
                        File temporaryFile = new File("tmp_" + (i + 1) + ".temp");
                        temporaryFile.delete();
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
