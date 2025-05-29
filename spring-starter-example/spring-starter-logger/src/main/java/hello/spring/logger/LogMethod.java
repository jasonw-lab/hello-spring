/**
 * Created on 2018/3/22 22:02:00
 */
package hello.spring.logger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * メソッドのロギングを有効にするためのアノテーション
 * このアノテーションが付与されたメソッドは、実行時に自動的にログが記録される
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogMethod {
    
    /**
     * ログメッセージのプレフィックス
     * @return ログメッセージのプレフィックス
     */
    String value() default "";
    
    /**
     * パラメータをログに含めるかどうか
     * @return パラメータをログに含める場合はtrue
     */
    boolean parameters() default true;
    
    /**
     * 戻り値をログに含めるかどうか
     * @return 戻り値をログに含める場合はtrue
     */
    boolean returnValue() default true;
    
    /**
     * 実行時間をログに含めるかどうか
     * @return 実行時間をログに含める場合はtrue
     */
    boolean executionTime() default true;
}