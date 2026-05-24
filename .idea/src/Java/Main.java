import java.util.List;

public class Main {
    public static void main(String[] args) {
        String[] mockLogFile = {
                "192.168.1.10 - [24/May/2026:17:33:27 +0300] \"GET /index.html HTTP/1.1\"",
                "10.0.0.5 - [24/May/2026:17:34:10 +0300] \"POST /api/login HTTP/1.1\"",     // Пройдет, POST -> TSOP
                "192.168.0.101 - [24/May/2026:17:35:00 +0300] \"PUT /data HTTP/1.1\"",
                "172.16.0.2 - [24/May/2026:17:36:20 +0300] \"DELETE /user/1 HTTP/1.1\"",   // Пройдет, DELETE -> ETELED
                "   8.8.8.8   -   [24/May/2026:17:40:12 +0300]   \"GET /dns HTTP/1.1\"   ",// Пройдет (пробелы очистятся), GET -> TEG
                "НЕКОРРЕКТНАЯ_СТРОКА_ЛОГА"
        };

        System.out.println("Запуск программы обработки логов (Вариант 9)...");

        LogProcessor processor = new LogProcessor();

        List<ServerLogRecord> processedData = processor.processRawLogs(mockLogFile);

        String finalReport = processor.generateReport(processedData);

        System.out.println("\n" + finalReport);
    }
}
