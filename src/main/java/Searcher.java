import java.util.ArrayList;
import java.util.List;

public class Searcher {
    public static List<Integer> search(String string, List<String> subStrings, boolean caseSensitive, int count, boolean reverse) {
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
            results.addAll(kmpSearch(string, subString, count, reverse));
        }
        return results;
    }

    // Основной метод КМП для поиска одной подстроки
    private static List<Integer> kmpSearch(String string, String subString, int count, boolean reverse) {
        List<Integer> result = new ArrayList<>();
        int[] lps = computeLPSArray(subString);

        // Для прямого поиска i=0, для обратного поиска i начинается с конца строки
        int i = reverse ? string.length() - 1 : 0;
        int j = 0;  // Индекс подстроки
        int step = reverse ? -1 : 1;  // Шаг: назад для обратного поиска и вперёд для прямого

        if (subString.isEmpty()) return result;

        while (reverse ? i >= 0 : i < string.length()) {
            // Если текущие символы строки и подстроки совпадают
            if (subString.charAt(j) == string.charAt(i)) {
                i += step;  // Сдвигаем индекс строки
                j++;        // Сдвигаем индекс подстроки

                // Если мы нашли всю подстроку
                if (j == subString.length()) {
                    // Для обратного поиска сохраняем позицию конца совпадения
                    result.add(reverse ? i + subString.length() : i - j);
                    j = lps[j - 1];  // Используем LPS для продолжения поиска

                    // Прерываем, если достигли нужного количества совпадений
                    if (count > 0 && result.size() == count) {
                        break;
                    }
                }
            } else {
                // Если символы не совпадают
                if (j != 0) {
                    // Сдвигаем j согласно префикс-функции
                    j = lps[j - 1];
                } else {
                    // Сдвигаем индекс строки только если j == 0
                    i += step;
                }
            }
        }

        return result;
    }

    // Метод для вычисления префикс-функции
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
