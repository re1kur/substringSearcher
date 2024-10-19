import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Searcher class
 */
public class Searcher {
    private static final Logger logger = Logger.getLogger(Searcher.class.getName());

    /**
     * @param string        value to parse
     * @param subStrings    list of substrings
     * @param caseSensitive boolean value of case-sensitive
     * @param count         required count of founded matches
     * @param reverse       boolean value
     * @return List<Integer> indexes in content of matches
     */
    @LogExecutionTime
    public List<Integer> search(String string, List<String> subStrings, boolean caseSensitive, int count, boolean reverse) {
        long startTime = System.nanoTime() / 10000;
        List<Integer> results = new ArrayList<>();
        if (!caseSensitive) {
            string = string.toLowerCase();
            List<String> lowerSubStrings = new ArrayList<>();
            for (String subString : subStrings) {
                lowerSubStrings.add(subString.toLowerCase());
            }
            subStrings = lowerSubStrings;
        }
        for (String subString : subStrings) {
            if (reverse) {
                results.addAll(reverseKmpSearch(string, subString, count));  // Используем обратный поиск
            } else {
                results.addAll(kmpSearch(string, subString, count));  // Прямой поиск
            }
        }
        long executionTime = System.nanoTime() / 10000 - startTime;  // Конец замера времени
        logger.info("Method search executed in " + executionTime + " ms");
        return results;
    }

    /**
     * @param string    content value to parse
     * @param subString substring
     * @param count     required count of founded matches
     * @return List<Integer> indexes
     */
    private static List<Integer> kmpSearch(String string, String subString, int count) {
        List<Integer> result = new ArrayList<>();
        int[] lps = computeLPSArray(subString);
        int i = 0;  // Индекс строки
        int j = 0;  // Индекс подстроки
        if (subString.isEmpty()) return result;
        while (i < string.length()) {
            if (subString.charAt(j) == string.charAt(i)) {
                i++;
                j++;
            }
            if (j == subString.length()) {
                result.add(i - j);
                j = lps[j - 1];
                if (count > 0 && result.size() == count) {
                    break;
                }
            } else if (i < string.length() && subString.charAt(j) != string.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        return result;
    }

    /**
     * @param string    value
     * @param subString substring
     * @param count     required count of founded matches
     */
    private List<Integer> reverseKmpSearch(String string, String subString, int count) {
        String reversedString = new StringBuilder(string).reverse().toString();
        String reversedSubString = new StringBuilder(subString).reverse().toString();
        List<Integer> reversedResults = kmpSearch(reversedString, reversedSubString, count);
        List<Integer> result = new ArrayList<>();
        for (int index : reversedResults) {
            result.add(string.length() - index - subString.length());  // Преобразуем индекс для оригинальной строки
        }
        return result;
    }

    /**
     * method for creating prefix-functions of content which must parsed
     *
     * @param pat content to parse
     * @return int[] LPS prefix-function
     */
    private static int[] computeLPSArray(String pat) {
        int[] lps = new int[pat.length()];
        int length = 0;
        int i = 1;

        while (i < pat.length()) {
            if (pat.charAt(i) == pat.charAt(length)) {
                length++;
                lps[i] = length;
                i++;
            } else {
                if (length != 0) {
                    length = lps[length - 1];
                } else {
                    lps[i] = length;
                    i++;
                }
            }
        }
        return lps;
    }
}
