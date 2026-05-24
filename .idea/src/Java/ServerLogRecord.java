public class ServerLogRecord {
    private final String ip;
    private final String timestamp;
    private final String method;
    private final String path;

    public ServerLogRecord(String ip, String timestamp, String method, String path) {
        this.ip = ip;
        this.timestamp = timestamp;
        this.method = method;
        this.path = path;
    }

    public String getIp() {
        return ip;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }
}