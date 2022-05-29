package ru.netology.data;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;

public class DBUtils {
    static String url = System.getProperty("datasource.url");
    static String user = "user";
    static String password = "password";

    @SneakyThrows
    public static void cleanTable() {
        var runner = new QueryRunner();
        try (var conn = DriverManager.getConnection(url, user, password)) {
            runner.execute(conn, "TRUNCATE credit_request_entity");
            runner.execute(conn, "TRUNCATE payment_entity");
            runner.execute(conn, "TRUNCATE order_entity");
        }
    }

    @SneakyThrows
    public static String getPaymentStatus() {
        String statusSQL = "SELECT status FROM payment_entity";
        var runner = new QueryRunner();
        try (var conn = DriverManager.getConnection(url, user, password)) {
            return runner.query(conn, statusSQL, new ScalarHandler<String>());
        }
    }

    @Data
    public static class PaymentEntity {
        String status;
        String transaction_id;
    }

    @SneakyThrows
    public static PaymentEntity getPayment() {
        var paymentSQL = "SELECT status, transaction_id FROM payment_entity ORDER BY created DESC LIMIT 1;";
        var runner = new QueryRunner();
        try (var conn = DriverManager.getConnection(url, user, password)) {
            return runner.query(conn, paymentSQL, new BeanHandler<>(PaymentEntity.class));
        }
    }


    @SneakyThrows
    private static String getStatus(String query) {
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(url, user, password)) {
            String status = runner.query(conn, query, new ScalarHandler<String>());
            return status;
        }
    }
}
