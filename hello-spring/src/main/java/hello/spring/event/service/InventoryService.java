package hello.spring.event.service;

import hello.spring.event.model.Order;
import hello.spring.event.model.OrderItem;
import hello.spring.event.model.Product;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 在庫管理サービス
 */
@Service
public class InventoryService {
    // 商品IDをキーとした商品マップ（実際のアプリケーションではデータベースを使用）
    private final Map<Long, Product> productMap = new HashMap<>();

    /**
     * 商品を登録
     * @param product 商品
     */
    public void registerProduct(Product product) {
        productMap.put(product.getId(), product);
        System.out.println("商品を登録しました: " + product);
    }

    /**
     * 商品を取得
     * @param productId 商品ID
     * @return 商品
     */
    public Product getProduct(Long productId) {
        return productMap.get(productId);
    }

    /**
     * 在庫を更新
     * @param productId 商品ID
     * @param quantity 数量変更（正: 増加、負: 減少）
     * @return 更新成功の場合true
     */
    public boolean updateStock(Long productId, int quantity) {
        Product product = productMap.get(productId);
        if (product == null) {
            return false;
        }
        
        if (quantity < 0 && product.getStock() < Math.abs(quantity)) {
            return false; // 在庫不足
        }
        
        product.setStock(product.getStock() + quantity);
        System.out.println("在庫を更新しました: " + product.getName() + ", 新しい在庫: " + product.getStock());
        return true;
    }

    /**
     * 注文完了イベントのリスナー
     * 注文が完了したら在庫を減らす
     * @param event 注文完了イベント
     */
    @EventListener
    public void handleOrderCompletedEvent(hello.spring.event.event.OrderCompletedEvent event) {
        Order order = event.getOrder();
        System.out.println("在庫管理サービス: 注文完了イベントを受信しました - 注文ID: " + order.getId());
        
        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            int quantity = item.getQuantity();
            
            boolean updated = updateStock(product.getId(), -quantity);
            if (updated) {
                System.out.println("商品 " + product.getName() + " の在庫を " + quantity + " 減少させました");
            } else {
                System.err.println("商品 " + product.getName() + " の在庫更新に失敗しました");
            }
        }
        
        System.out.println("在庫管理サービス: 注文の全商品の在庫を更新しました");
    }
}