package part2.problem2_strategy;

// Main Demo
public class TaxStrategyDemo {
    public static void main(String[] args) {
        System.out.println("=== Demo Strategy Pattern (Tính thuế) ===");

        // Sản phẩm bình thường áp dụng VAT
        Product nuocNgot = new Product("Nước Ngọt", 10000, new VATStrategy());
        nuocNgot.displayInfo();

        // Sản phẩm thiết yếu áp dụng Thuế tiêu thụ
        Product gao = new Product("Gạo ST25", 200000, new ConsumptionTaxStrategy());
        gao.displayInfo();

        // Sản phẩm xa xỉ
        Product ruouVang = new Product("Rượu Vang Pháp", 1500000, new LuxuryTaxStrategy());
        ruouVang.displayInfo();
    }
}
