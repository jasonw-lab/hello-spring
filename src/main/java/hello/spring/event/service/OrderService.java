package hello.spring.event.service;

import hello.spring.event.event.OrderCompletedEvent;
import hello.spring.event.model.Order;
import hello.spring.event.model.OrderItem;
import hello.spring.event.model.Product;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 注文サービス
 */
@Service
public class OrderService {
    private final ApplicationEventPublisher eventPublisher;
    private final Map<Long, Order> orderMap = new HashMap<>();

    /**
     * コンストラクタ
     * @param eventPublisher イベント発行者
     */
    public OrderService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * 注文を作成
     * @param id 注文ID
     * @param customerName 顧客名
     * @return 作成された注文
     */
    public Order createOrder(Long id, String customerName) {
        Order order = new Order(id, customerName);
        orderMap.put(id, order);
        System.out.println("注文を作成しました: " + order);
        return order;
    }

    /**
     * 注文に商品を追加
     * @param orderId 注文ID
     * @param product 商品
     * @param quantity 数量
     * @return 更新された注文
     */
    public Order addOrderItem(Long orderId, Product product, int quantity) {
        Order order = orderMap.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("注文が見つかりません: " + orderId);
        }
        
        OrderItem item = new OrderItem(System.currentTimeMillis(), product, quantity);
        order.addItem(item);
        System.out.println("注文に商品を追加しました: " + item);
        return order;
    }

    /**
     * 注文を完了する
     * @param orderId 注文ID
     * @return 完了した注文
     */
    public Order completeOrder(Long orderId) {
        Order order = orderMap.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("注文が見つかりません: " + orderId);
        }
        
        // 注文のステータスを完了に変更
        order.setStatus(Order.OrderStatus.COMPLETED);
        System.out.println("注文を完了しました: " + order);
        
        // 注文完了イベントを発行
        OrderCompletedEvent event = new OrderCompletedEvent(this, order);
        eventPublisher.publishEvent(event);
        System.out.println("注文完了イベントを発行しました: " + event);
        
        return order;
    }

    /**
     * 注文を取得
     * @param orderId 注文ID
     * @return 注文
     */
    public Order getOrder(Long orderId) {
        return orderMap.get(orderId);
    }
}