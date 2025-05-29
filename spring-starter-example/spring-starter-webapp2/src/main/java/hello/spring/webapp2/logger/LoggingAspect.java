/**
 * Created on 2018/3/22 22:10:00
 */
package hello.spring.webapp2.logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * ロギングを実装するAOPアスペクト
 * @LogMethodアノテーションが付与されたメソッドの実行前後にログを出力する
 * （スターターを使用せず、アプリケーション内で直接定義）
 */
@Aspect
@Component
public class LoggingAspect {

    /**
     * @LogMethodアノテーションが付与されたメソッドをインターセプトし、ログを出力する
     * @param joinPoint AOPジョインポイント
     * @return メソッドの実行結果
     * @throws Throwable メソッド実行時の例外
     */
    @Around("@annotation(hello.spring.webapp2.logger.LogMethod)")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        // メソッドの情報を取得
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogMethod logMethod = method.getAnnotation(LogMethod.class);
        
        // クラス名からロガーを取得
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        
        // メソッド名を取得
        String methodName = method.getName();
        
        // プレフィックスを設定
        String prefix = logMethod.value().isEmpty() ? "" : logMethod.value() + " - ";
        
        // パラメータをログに出力
        if (logMethod.parameters()) {
            logger.info("{}メソッド開始: {}(引数: {})", prefix, methodName, Arrays.toString(joinPoint.getArgs()));
        } else {
            logger.info("{}メソッド開始: {}", prefix, methodName);
        }
        
        // 開始時間を記録
        long startTime = System.currentTimeMillis();
        Object result = null;
        
        try {
            // メソッドを実行
            result = joinPoint.proceed();
            
            // 実行時間を計算
            long executionTime = System.currentTimeMillis() - startTime;
            
            // 戻り値と実行時間をログに出力
            if (logMethod.returnValue() && logMethod.executionTime()) {
                logger.info("{}メソッド終了: {} (戻り値: {}, 実行時間: {}ms)", prefix, methodName, result, executionTime);
            } else if (logMethod.returnValue()) {
                logger.info("{}メソッド終了: {} (戻り値: {})", prefix, methodName, result);
            } else if (logMethod.executionTime()) {
                logger.info("{}メソッド終了: {} (実行時間: {}ms)", prefix, methodName, executionTime);
            } else {
                logger.info("{}メソッド終了: {}", prefix, methodName);
            }
            
            return result;
        } catch (Throwable e) {
            // 例外発生時のログ
            logger.error("{}メソッド例外: {} - {}", prefix, methodName, e.getMessage(), e);
            throw e;
        }
    }
}