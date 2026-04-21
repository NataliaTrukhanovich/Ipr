package jdbc;


import entity.Product;
import entity.ProductsWithSales;
import entity.Sale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static services.ConfigProvider.config;

public class JdbcCrud {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCrud.class);
    static final String DB_URL = config.getString("db_url");
    static final String USER = config.getString("db_user");
    static final String PWD = config.getString("db_pass");

    public static void main(String[] args) throws SQLException {

        createAndFillInTables();

        createNewProduct();
        updateProduct();
        deleteProduct();
        selectProduct();
        selectProductsWithSales();
    }

    private static void createNewProduct() throws SQLException {
        Product product = new Product("Pear", "Fruits", 9);

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PWD);
             PreparedStatement ps =
                     connection.prepareStatement("INSERT INTO products (product_name, category, in_stock) VALUES (?, ?, ?)")) {

            ps.setString(1, product.getProductName());
            ps.setString(2, product.getCategory());
            ps.setInt(3, product.getInStock());

            ps.executeUpdate();

            logger.info("Создана новая запись в таблице products");

        } catch (SQLException e) {
            logger.error("Не удалось создать новую запись");
            throw new SQLException(e);
        }
    }

    private static void updateProduct() throws SQLException {

        String productName = "Pear";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PWD);
             PreparedStatement ps =
                     connection.prepareStatement("UPDATE products SET in_stock = 10 WHERE product_name = ?")) {

            ps.setString(1, productName);
            ps.executeUpdate();
            logger.info("Изменена запись в таблице products в строке с productName = '{}'", productName);

        } catch (SQLException e) {
            logger.error("Не удалось изменить запись");
            throw new SQLException(e);
        }
    }

    private static void deleteProduct() throws SQLException {

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PWD);
             PreparedStatement ps =
                     connection.prepareStatement("DELETE FROM products WHERE product_name = ?")) {
            String productName = "apple";
            ps.setString(1, productName);

            ps.executeUpdate();

            logger.info("Удалена запись из таблицы products с productName = '{}'", productName);

        } catch (SQLException e) {
            logger.error("Не удалось удалить запись из таблицы");
            throw new SQLException(e);
        }
    }

    private static void selectProduct() throws SQLException {

        String productName = "carrot";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PWD);
             PreparedStatement ps =
                     connection.prepareStatement("SELECT * FROM products WHERE product_name = ?")) {
            ps.setString(1, productName);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getLong("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setCategory(rs.getString("category"));
                product.setInStock(rs.getInt("in_stock"));

                logger.info("Получена запись из таблицы products: {}", product);
            }

        } catch (SQLException e) {
            logger.error("Не удалось получить запись из таблицы");
            throw new SQLException(e);
        }
    }

    private static void selectProductsWithSales() throws SQLException {

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PWD);
             PreparedStatement ps =
                     connection.prepareStatement("SELECT p.product_id, p.product_name, p.category, " +
                             "s.quantity, s.price " +
                             "FROM products p " +
                             "JOIN sales s " +
                             "WHERE s.product_id = p.product_id")) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductsWithSales productsWithSales = new ProductsWithSales();
                productsWithSales.setProductId(rs.getLong("product_id"));
                productsWithSales.setProductName(rs.getString("product_name"));
                productsWithSales.setCategory(rs.getString("category"));
                productsWithSales.setQuantity(rs.getInt("quantity"));
                productsWithSales.setPrice(BigDecimal.valueOf(rs.getDouble("price")));

                logger.info("{}", productsWithSales);
            }

        } catch (SQLException e) {
            logger.error("Не удалось получить записи из БД");
            throw new SQLException(e);
        }
    }

    private static void createAndFillInTables() throws SQLException {

        logger.info("Создаём 2 таблицы: Products и Sales. Наполняем их данными");

        String dropProductsTable = "DROP TABLE IF EXISTS products";
        String dropSalesTable = "DROP TABLE IF EXISTS sales";

        String createProductsTable = " CREATE TABLE IF NOT EXISTS products (" +
                "product_id   BIGINT NOT NULL AUTO_INCREMENT, " +
                "product_name VARCHAR(50) NOT NULL, " +
                "category     VARCHAR(50), " +
                "in_stock     INT," +
                "PRIMARY KEY (product_id))";
        String createSalesTable = "CREATE TABLE IF NOT EXISTS sales (" +
                "sale_id     BIGINT NOT NULL AUTO_INCREMENT," +
                "product_id  BIGINT NOT NULL," +
                "quantity    INT NOT NULL, " +
                "price       DECIMAL(10,2) NOT NULL," +
                "CONSTRAINT FOREIGN KEY (product_id) REFERENCES products(product_id)" +
                "ON DELETE CASCADE," +
                "PRIMARY KEY (sale_id))";

        List<Product> products = new ArrayList<>();
        products.add(new Product("Apple", "Fruits", 87));
        products.add(new Product("Orange", "Fruits", 102));
        products.add(new Product("Banana", "Fruits", 99));
        products.add(new Product("Carrot", "Vegetables", 65));
        products.add(new Product("Cucumber", "Vegetables", 65));

        List<Sale> sales = new ArrayList<>();
        sales.add(new Sale(1L, 12, new BigDecimal("10.5")));
        sales.add(new Sale(2L, 15, new BigDecimal("11.75")));
        sales.add(new Sale(3L, 16, new BigDecimal("7.1")));
        sales.add(new Sale(4L, 8, new BigDecimal("2.43")));
        sales.add(new Sale(5L, 9, new BigDecimal("4.5")));
        sales.add(new Sale(1L, 3, new BigDecimal("14.5")));

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PWD)) {

            connection.setAutoCommit(false);
            try {
                try (Statement stm = connection.createStatement()) {
                    stm.executeUpdate(dropSalesTable);
                    stm.executeUpdate(dropProductsTable);
                    stm.executeUpdate(createProductsTable);
                    stm.executeUpdate(createSalesTable);
                }

                try (PreparedStatement ps =
                             connection.prepareStatement("INSERT INTO products (product_name, category, in_stock) VALUES (?, ?, ?)")) {

                    for (Product product : products) {
                        ps.setString(1, product.getProductName());
                        ps.setString(2, product.getCategory());
                        ps.setInt(3, product.getInStock());

                        ps.addBatch();
                    }
                    ps.executeBatch();
                }
                try (PreparedStatement ps =
                             connection.prepareStatement("INSERT INTO sales (product_id, quantity, price) VALUES (?, ?, ?)")) {

                    for (Sale sale : sales) {
                        ps.setLong(1, sale.getProductId());
                        ps.setInt(2, sale.getQuantity());
                        ps.setBigDecimal(3, sale.getPrice());

                        ps.addBatch();
                    }
                    ps.executeBatch();
                }

                connection.commit();

            } catch (SQLException e) {
                connection.rollback();
                logger.error("Ошибка при создании и/или заполнении таблиц. Выполнен rollback");
                throw new SQLException("Не удалось создать и/или заполнить таблицу", e);
            }
        }
    }
}
