package com.fooddelivery.foodservice;

import com.fooddelivery.foodservice.model.Food;
import com.fooddelivery.foodservice.repository.FoodRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private final FoodRepository foodRepository;

    public DataInitializer(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Xóa dữ liệu cũ (tuỳ chọn)
        foodRepository.deleteAll();

        // Tạo dữ liệu test
        Food food1 = new Food("Phở Bò", new BigDecimal("50000"), "Phở bò ngon, nước dùi đậm đà");
        Food food2 = new Food("Cơm Tấm", new BigDecimal("45000"), "Cơm tấm sườn, trứng, dưa leo");
        Food food3 = new Food("Bánh Mì", new BigDecimal("25000"), "Bánh mì thơm, nhân phong phú");
        Food food4 = new Food("Bún Chả", new BigDecimal("55000"), "Bún chả Hà Nội chuẩn chính");
        Food food5 = new Food("Cơm Chiên", new BigDecimal("40000"), "Cơm chiên dương châu, tôm tươi");

        // Lưu vào database
        foodRepository.saveAll(Arrays.asList(food1, food2, food3, food4, food5));

        System.out.println("✅ 5 món ăn đã được thêm vào database!");
    }
}

