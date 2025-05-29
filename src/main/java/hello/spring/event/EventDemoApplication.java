package hello.spring.event;

import hello.spring.event.model.Order;
import hello.spring.event.model.Product;
import hello.spring.event.service.InventoryService;
import hello.spring.event.service.OrderService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Eventのデモアプリケーション
 * 注文完了イベント発行と在庫更新を実演する
 */
@Configuration
@ComponentScan("hello.spring.event")
public class EventDemoApplication {

    public static void main(String[] args) {
        // Spring コンテキストを初期化
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(EventDemoApplication.class);
        
        // サービスのBeanを取得
        InventoryService inventoryService = context.getBean(InventoryService.class);
        OrderService orderService = context.getBean(OrderService.class);
        
        System.out.println("===== Spring Event デモ開始 =====");
        
        // 1. 商品を登録
        System.out.println("\n----- 商品登録 -----");
        Product laptop = new Product(1L, "ノートパソコン", 10, 80000);
        Product smartphone = new Product(2L, "スマートフォン", 20, 60000);
        Product tablet = new Product(3L, "タブレット", 15, 40000);
        
        inventoryService.registerProduct(laptop);
        inventoryService.registerProduct(smartphone);
        inventoryService.registerProduct(tablet);
        
        // 2. 注文を作成
        System.out.println("\n----- 注文作成 -----");
        Order order = orderService.createOrder(1001L, "山田太郎");
        
        // 3. 注文に商品を追加
        System.out.println("\n----- 注文に商品を追加 -----");
        orderService.addOrderItem(order.getId(), laptop, 2);
        orderService.addOrderItem(order.getId(), smartphone, 1);
        
        // 現在の在庫状況を表示
        System.out.println("\n----- 注文完了前の在庫状況 -----");
        System.out.println("ノートパソコンの在庫: " + inventoryService.getProduct(1L).getStock());
        System.out.println("スマートフォンの在庫: " + inventoryService.getProduct(2L).getStock());
        
        // 4. 注文を完了する（イベント発行）
        System.out.println("\n----- 注文完了（イベント発行） -----");
        orderService.completeOrder(order.getId());
        
        // 5. 在庫が更新されたことを確認
        System.out.println("\n----- 注文完了後の在庫状況 -----");
        System.out.println("ノートパソコンの在庫: " + inventoryService.getProduct(1L).getStock());
        System.out.println("スマートフォンの在庫: " + inventoryService.getProduct(2L).getStock());
        
        // 別の注文でも同様の処理を実演
        System.out.println("\n----- 別の注文での実演 -----");
        Order order2 = orderService.createOrder(1002L, "鈴木花子");
        orderService.addOrderItem(order2.getId(), tablet, 3);
        
        System.out.println("タブレットの在庫（注文前）: " + inventoryService.getProduct(3L).getStock());
        orderService.completeOrder(order2.getId());
        System.out.println("タブレットの在庫（注文後）: " + inventoryService.getProduct(3L).getStock());
        
        // コンテキストを閉じる
        context.close();
        
        System.out.println("\n===== Spring Event デモ終了 =====");
    }
}