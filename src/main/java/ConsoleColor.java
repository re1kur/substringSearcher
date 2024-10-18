public class ConsoleColor {
    public static final String RESET = "\033[0m";
    public static final String[] COLORS = {
            "\033[0;31m", // Red
            "\033[0;32m", // Green
            "\033[0;33m", // Yellow
            "\033[0;34m", // Blue
            "\033[0;35m", // Purple
            "\033[0;36m"  // Cyan
    };

    public static String colorize(String text, int index) {
        return COLORS[index % COLORS.length] + text + RESET;
    }
}
