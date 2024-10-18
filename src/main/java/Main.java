import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {

    // Логгер для информации
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    // Метод для поиска подстроки в содержимом файла
    public List<Integer> findSubstrings(String content, List<String> subStrings, boolean caseSensitive, int count, boolean fromEnd) {
        return Searcher.search(content, subStrings, caseSensitive, count, fromEnd);
    }

    // Метод для чтения содержимого файла
    public static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    public static void printColoredResults(String string, List<Integer> positions, String subString) {
        StringBuilder result = new StringBuilder();
        int lastIndex = 0;
        try {
            for (int pos : positions) {
                result.append(string, lastIndex, pos);
                result.append(ConsoleColor.colorize(string.substring(pos, pos + subString.length()), pos));
                lastIndex = pos + subString.length();
            }
        } catch (IndexOutOfBoundsException _) {
            result.append(string.substring(lastIndex));
            System.out.println(result); // ошибка надо разобраться
            return;
        }
        result.append(string.substring(lastIndex));
        System.out.println(result);
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Main mainObj = new Main();

        // Ввод данных пользователем
        System.out.println("Enter the file path:");
        String filePath = scanner.nextLine();

        System.out.println("Enter the substring or substrings separated by commas to search for:");
        String subString = scanner.nextLine();
        List<String> subStrings = Arrays.asList(subString.split("\\s*,\\s*"));

        System.out.println("Case sensitive? (yes/no):");
        boolean caseSensitive = scanner.nextLine().equalsIgnoreCase("yes");

        System.out.println("Number of occurrences to find (enter 0 for no limit):");
        int count = Integer.parseInt(scanner.nextLine());
        if (count == 0) count = Integer.MAX_VALUE;

        System.out.println("Search from the end? (yes/no):");
        boolean fromEnd = scanner.nextLine().equalsIgnoreCase("yes");

        try {
            // Чтение файла и поиск подстроки
            String content = readFile(filePath);

            // Вызов метода для поиска подстрок
            List<Integer> result = mainObj.findSubstrings(content, subStrings, caseSensitive, count, fromEnd);
            logger.info("Result: " + result.toString());
            printColoredResults(content, result, subString);
        } catch (IOException e) {
            logger.severe("Error reading file: " + e.getMessage());
        }
    }
}
