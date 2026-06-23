package com.product_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class SeedScript implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbc;

    private static final String[] CATEGORIES = {
            "Electronics","Clothing","Books","Foods","Sports",
            "Furniture","Toys","Beauty","Automotive","Garden"
    };

    private static final String[] NAME = {
            "Premium","Basic","Ultra","Super","Mega",
            "Pro","Elite","Classic","Modern","Smart"
    };

    @Override
    public void run(String... args) {

        try {
            Long count = jdbc.queryForObject("SELECT COUNT(*) FROM products", Long.class);

            if (count != null && count > 0) {
                System.out.println("Data already exists, skipping seed!");
                return;
            }

            System.out.println("Seeding 200,000 products...");

            Random random = new Random();
            int batchSize = 500;

            String sql = "INSERT INTO products (name, category, price, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";

            List<Object[]> batch = new ArrayList<>();

            for (int i = 0; i < 200000; i++) {

                String name = NAME[random.nextInt(NAME.length)] + " Product " + i;
                String category = CATEGORIES[random.nextInt(CATEGORIES.length)];
                double price = 10 + (random.nextDouble() * 990);

                LocalDateTime createdAt = LocalDateTime.now()
                        .minusDays(random.nextInt(365));

                LocalDateTime updatedAt = createdAt;

                batch.add(new Object[]{name, category, price, createdAt, updatedAt});

                if (batch.size() == batchSize) {
                    jdbc.batchUpdate(sql, batch);
                    batch.clear();
                    System.out.println("Inserted: " + (i + 1));
                }
            }

            if (!batch.isEmpty()) {
                jdbc.batchUpdate(sql, batch);
            }

            System.out.println("Seeding complete!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}