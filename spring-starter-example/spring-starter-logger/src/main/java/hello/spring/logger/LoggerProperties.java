/**
 * Created on 2018/3/22 22:01:00
 */
package hello.spring.logger;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ロガーの設定プロパティクラス
 * application.propertiesまたはapplication.ymlで設定可能
 */
@ConfigurationProperties(prefix = "logger")
public class LoggerProperties {

    /**
     * ロギングを有効にするかどうか
     */
    private boolean enabled = true;

    /**
     * ログに含めるパラメータを有効にするかどうか
     */
    private boolean includeParameters = true;

    /**
     * ログに含める戻り値を有効にするかどうか
     */
    private boolean includeReturnValue = true;

    /**
     * ログに含める実行時間を有効にするかどうか
     */
    private boolean includeExecutionTime = true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isIncludeParameters() {
        return includeParameters;
    }

    public void setIncludeParameters(boolean includeParameters) {
        this.includeParameters = includeParameters;
    }

    public boolean isIncludeReturnValue() {
        return includeReturnValue;
    }

    public void setIncludeReturnValue(boolean includeReturnValue) {
        this.includeReturnValue = includeReturnValue;
    }

    public boolean isIncludeExecutionTime() {
        return includeExecutionTime;
    }

    public void setIncludeExecutionTime(boolean includeExecutionTime) {
        this.includeExecutionTime = includeExecutionTime;
    }
}