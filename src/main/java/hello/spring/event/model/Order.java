package hello.spring.event.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 注文クラス
 */
public class Order {
    private Long id;
    private String customerName;
    private List<OrderItem> items;
    private LocalDateTime orderDate;
    private OrderStatus status;

    /**
     * 注文ステータス
     */
    public enum OrderStatus {
        CREATED,    // 作成済み
        CONFIRMED,  // 確認済み
        COMPLETED,  // 完了
        CANCELLED   // キャンセル
    }

    public Order(Long id, String customerName) {
        this.id = id;
        this.customerName = customerName;
        this.items = new ArrayList<>();
        this.orderDate = LocalDateTime.now();
        this.status = OrderStatus.CREATED;
    }

    public Long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    /**
     * 注文に商品を追加
     * @param item 注文明細
     */
    public void addItem(OrderItem item) {
        items.add(item);
    }

    /**
     * 合計金額を計算
     * @return 合計金額
     */
    public double getTotal() {
        return items.stream()
                .mapToDouble(OrderItem::getSubtotal)
                .sum();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", items=" + items +
                ", orderDate=" + orderDate +
                ", status=" + status +
                ", total=" + getTotal() +
                '}';
    }
}