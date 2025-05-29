package hello.spring.ioc.concept.service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * TimeServiceインターフェースのシンプルな実装。
 */
public class SimpleTimeService implements TimeService {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * HH:mm:ss形式でフォーマットされた現在の時刻を返します。
     */
    @Override
    public String getCurrentTime() {
        LocalTime now = LocalTime.now();
        return formatter.format(now);
    }
}
