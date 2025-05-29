package hello.spring.event.event;

import hello.spring.event.model.Order;
import org.springframework.context.ApplicationEvent;

/**
 * 注文完了イベント
 * 注文が完了した時に発行されるイベント
 */
public class OrderCompletedEvent extends ApplicationEvent {
    private final Order order;

    /**
     * 注文完了イベントを作成
     * @param source イベントのソース
     * @param order 完了した注文
     */
    public OrderCompletedEvent(Object source, Order order) {
        super(source);
        this.order = order;
    }

    /**
     * 完了した注文を取得
     * @return 注文オブジェクト
     */
    public Order getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return "OrderCompletedEvent{" +
                "order=" + order +
                '}';
    }
}