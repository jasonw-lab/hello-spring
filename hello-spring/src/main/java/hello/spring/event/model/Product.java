package hello.spring.event.model;

/**
 * 商品クラス
 */
public class Product {
    private Long id;
    private String name;
    private int stock;
    private double price;

    public Product(Long id, String name, int stock, double price) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    /**
     * 在庫を減らす
     * @param quantity 減らす数量
     * @return 在庫が足りない場合はfalse
     */
    public boolean decreaseStock(int quantity) {
        if (stock >= quantity) {
            stock -= quantity;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                '}';
    }
}