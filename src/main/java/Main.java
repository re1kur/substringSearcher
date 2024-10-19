import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    /**
     * @param content       content to parse
     * @param subStrings    list of substrings
     * @param caseSensitive boolean value of case-sensitive
     * @param count         required count of founded matches
     * @param fromEnd       boolean value of reverse
     * @return List<Integer>
     */
    public List<Integer> findSubstrings(String content, List<String> subStrings, boolean caseSensitive, int count, boolean fromEnd) {
        Searcher searcher = new Searcher();
        return searcher.search(content, subStrings, caseSensitive, count, fromEnd);
    }

    /**
     * @param filePath String path to file
     * @return content of file
     * @throws IOException if file don't exist
     */
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

    /**
     * @param string    content to parse
     * @param positions int[] of indexes of substrings
     * @param subString substring
     * @param reversed  boolean value. True or false
     */
    public static void printColoredResults(String string, List<Integer> positions, String subString, boolean reversed) {
        if (reversed) positions = positions.stream().sorted((a, b) -> b - a).toList(); // Sorting in reverse order if needed
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
        if (args.length < 5) {
            System.out.println("Usage: java Main <file_path> <substrings> <case_sensitive> <count> <from_end>");
            System.out.println("<file_path> - path to the file");
            System.out.println("<substrings> - comma separated substrings to search");
            System.out.println("<case_sensitive> - 0 for case-sensitive, 1 for not case-insensitive");
            System.out.println("<count> - number of occurrences to find (0 for no limit)");
            System.out.println("<from_end> - 0 for search from start, 1 for search from end");
            return;
        }

        String filePath = args[0];
        List<String> subStrings = Arrays.asList(args[1].split("\\s*,\\s*"));
        boolean caseSensitive = args[2].equals("0");
        int count = Integer.parseInt(args[3]);
        if (count == 0) count = Integer.MAX_VALUE;
        boolean fromEnd = args[4].equals("1");

        Main mainObj = new Main();

        try {
            // Чтение файла и поиск подстроки
            String content = readFile(filePath);

            // Вызов метода для поиска подстрок
            List<Integer> result = mainObj.findSubstrings(content, subStrings, caseSensitive, count, fromEnd);
            logger.info("Result: " + result.toString());
            printColoredResults(content, result, subStrings.get(0), fromEnd); // Assuming first substring for coloring
        } catch (IOException e) {
            logger.severe("Error reading file: " + e.getMessage());
        }
    }
}
