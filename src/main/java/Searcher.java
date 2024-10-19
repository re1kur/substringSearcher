import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Searcher {
    private static Logger logger = Logger.getLogger(Searcher.class.getName());
    @LogExecutionTime
    public List<Integer> search(String string, List<String> subStrings, boolean caseSensitive, int count, boolean reverse) {
        long startTime = System.currentTimeMillis();
        List<Integer> results = new ArrayList<>();

        // Если нечувствительность к регистру - делаем все строки нижним регистром
        if (!caseSensitive) {
            string = string.toLowerCase();
            List<String> lowerSubStrings = new ArrayList<>();
            for (String subString : subStrings) {
                lowerSubStrings.add(subString.toLowerCase());
            }
            subStrings = lowerSubStrings;
        }

        // Для каждой подстроки делаем поиск
        for (String subString : subStrings) {
            if (reverse) {
                results.addAll(reverseKmpSearch(string, subString, count));  // Используем обратный поиск
            } else {
                results.addAll(kmpSearch(string, subString, count));  // Прямой поиск
            }
        }
        long executionTime = System.currentTimeMillis() - startTime;  // Конец замера времени
        logger.info("Method search executed in " + executionTime + " ms");
        return results;
    }

    // Основной метод КМП для прямого поиска одной подстроки
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
                result.add(i - j);  // Нашли совпадение
                j = lps[j - 1];     // Используем LPS для продолжения поиска
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

    // Метод для обратного поиска (реверсируем строку и подстроку)
    private List<Integer> reverseKmpSearch(String string, String subString, int count) {
        String reversedString = new StringBuilder(string).reverse().toString();
        String reversedSubString = new StringBuilder(subString).reverse().toString();
        List<Integer> reversedResults = kmpSearch(reversedString, reversedSubString, count);

        // Преобразуем результаты поиска в исходной строке
        List<Integer> result = new ArrayList<>();
        for (int index : reversedResults) {
            result.add(string.length() - index - subString.length());  // Преобразуем индекс для оригинальной строки
        }

        return result;
    }

    // Префикс-функция (LPS)
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
