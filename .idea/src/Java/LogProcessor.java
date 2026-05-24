import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс отвечает за обработку, фильтрацию и трансформацию текстовых логов.
 */
public class LogProcessor {

    private static final Pattern EXCLUDE_IP_PATTERN = Pattern.compile("^192\\.168\\..*");

    private static final Pattern LOG_LINE_PATTERN = Pattern.compile(
            "^([\\d\\.]+) - \\[([^\\]]+)\\] \"([A-Z]+) ([^ \"]+) HTTP/1\\.[0-9]\"$"
    );

    /**
     * Основной метод-конвейер для обработки массива сырых строк лога.
     */
    public List<ServerLogRecord> processRawLogs(String[] lines) {
        List<ServerLogRecord> resultList = new ArrayList<>();

        for (String line : lines) {
            if (line == null || line.isBlank()) {
                continue;
            }

            String normalizedLine = line.trim().replaceAll("\\s+", " ");

            if (EXCLUDE_IP_PATTERN.matcher(normalizedLine).matches()) {
                continue;
            }

            Matcher matcher = LOG_LINE_PATTERN.matcher(normalizedLine);
            if (matcher.matches()) {
                String ip = matcher.group(1);
                String timestamp = matcher.group(2);
                String originalMethod = matcher.group(3);
                String path = matcher.group(4);

                String reversedMethod = new StringBuilder(originalMethod).reverse().toString();

                ServerLogRecord record = new ServerLogRecord(ip, timestamp, reversedMethod, path);
                resultList.add(record);
            } else {
                System.err.println("Пропущена некорректная запись лога: " + normalizedLine);
            }
        }
        return resultList;
    }

    /**
     * Крок 7 (StringBuilder): Сборка красивой текстовой таблицы-отчета.
     */
    public String generateReport(List<ServerLogRecord> records) {
        StringBuilder sb = new StringBuilder();
        sb.append("=================================================================\n");
        sb.append("                 ОБРАБОТАННЫЙ ЖУРНАЛ СЕРВЕРА                    \n");
        sb.append("=================================================================\n");
        sb.append(String.format("%-15s | %-22s | %-7s | %s\n", "IP-Адрес", "Дата/Время", "Метод", "Путь запроса"));
        sb.append("-----------------------------------------------------------------\n");

        for (ServerLogRecord record : records) {
            sb.append(String.format("%-15s | %-22s | %-7s | %s\n",
                    record.getIp(), record.getTimestamp(), record.getMethod(), record.getPath()));
        }

        sb.append("-----------------------------------------------------------------\n");
        sb.append("Всего успешно обработано записей: ").append(records.size()).append("\n");

        return sb.toString();
    }
}