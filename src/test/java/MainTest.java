import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    public void testFindSubstrings_emptyString() {
        // Тест поиска в пустой строке
        String content = "";
        List<String> subString = List.of("a");
        boolean caseSensitive = true;
        int count = 3;
        boolean fromEnd = false;

        Searcher searcher = new Searcher();
        List<Integer> result = searcher.search(content, subString, caseSensitive, count, fromEnd);
        assertTrue(result.isEmpty(), "Поиск в пустой строке должен возвращать пустой список.");
    }

    @Test
    public void testFindSubstrings_emptySubString() {
        // Тест пустой подстроки
        String content = "abcde";
        List<String> subString = List.of("");
        boolean caseSensitive = true;
        int count = 3;
        boolean fromEnd = false;

        Searcher searcher = new Searcher();
        List<Integer> result = searcher.search(content, subString, caseSensitive, count, fromEnd);
        assertTrue(result.isEmpty(), "Поиск пустой подстроки должен возвращать пустой список.");
    }

    @Test
    public void testFindSubstrings_noMatch() {
        // Тест случая, когда подстрока не найдена
        String content = "abcde";
        List<String> subString = List.of("f");
        boolean caseSensitive = true;
        int count = 3;
        boolean fromEnd = false;

        Searcher searcher = new Searcher();
        List<Integer> result = searcher.search(content, subString, caseSensitive, count, fromEnd);
        assertTrue(result.isEmpty(), "Подстрока 'f' не должна быть найдена в строке 'abcde'.");
    }

    @Test
    public void testFindSubstrings_singleCharacter() {
        // Тест поиска одиночного символа
        String content = "aaaa";
        List<String> subString = List.of("a");
        boolean caseSensitive = true;
        int count = 3;
        boolean fromEnd = false;

        List<Integer> expected = List.of(0, 1, 2);  // Ожидаем три первых совпадения
        Searcher searcher = new Searcher();
        List<Integer> result = searcher.search(content, subString, caseSensitive, count, fromEnd);
        assertEquals(expected, result, "Три первых 'a' должны быть найдены в строке 'aaaa'.");
    }

    @Test
    public void testFindSubstrings_multipleMatches() {
        // Тест поиска нескольких подстрок
        String content = "ababcabc";
        List<String> subString = List.of("abc");
        boolean caseSensitive = true;
        int count = 2;  // Ищем только два совпадения
        boolean fromEnd = false;

        List<Integer> expected = List.of(2, 5);  // Ожидаем два первых совпадения
        Searcher searcher = new Searcher();
        List<Integer> result = searcher.search(content, subString, caseSensitive, count, fromEnd);
        assertEquals(expected, result, "Два первых 'abc' должны быть найдены на позициях 2 и 5.");
    }

    @Test
    public void testFindSubstrings_caseInsensitive() {
        // Тест нечувствительности к регистру
        String content = "aBcDEabcde";
        List<String> subString = List.of("abc");
        boolean caseSensitive = false;  // Нечувствительность к регистру
        int count = 2;
        boolean fromEnd = false;

        List<Integer> expected = List.of(0, 5);
        Searcher searcher = new Searcher();
        List<Integer> result = searcher.search(content, subString, caseSensitive, count, fromEnd);
        assertEquals(expected, result, "Нечувствительный поиск 'abc' должен вернуть позиции 0 и 5.");
    }

    @Test
    public void testFindSubstrings_searchFromEnd() {
        // Тест поиска с конца строки
        String content = "abcabcabc";
        List<String> subString = List.of("abc");
        boolean caseSensitive = true;
        int count = 2;
        boolean fromEnd = true;  // Ищем с конца

        List<Integer> expected = List.of(6, 3);  // Два последних совпадения
        Searcher searcher = new Searcher();
        List<Integer> result = searcher.search(content, subString, caseSensitive, count, fromEnd);
        assertEquals(expected, result, "Два последних 'abc' должны быть найдены на позициях 6 и 3.");
    }

    @Test
    public void testFindSubstrings_fullMatch() {
        // Тест, когда подстрока совпадает со всей строкой
        String content = "abc";
        List<String> subString = List.of("abc");
        boolean caseSensitive = true;
        int count = 1;
        boolean fromEnd = false;

        List<Integer> expected = List.of(0);  // Полное совпадение
        Searcher searcher = new Searcher();
        List<Integer> result = searcher.search(content, subString, caseSensitive, count, fromEnd);
        assertEquals(expected, result, "Полное совпадение подстроки должно вернуть позицию 0.");
    }
}
