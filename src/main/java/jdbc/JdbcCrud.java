package jdbc;

import entity.Product;
import entity.ProductsWithSales;
import entity.Sale;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcCrud {

    static final String DB_URL = "jdbc:mysql://localhost:3306/ipr_db" ;
    static final String USER = "ipruser" ;
    static final String PWD = "iprpwd" ;

    public static void main(String[] args) throws SQLException {

        createAndFillInTables();
        //createNewProduct();
        //updateProduct();
        //deleteProduct();
        selectProduct();
        selectProductsWithSales();
    }

    private static void createNewProduct() {
        Product product = new Product("Pear", "Fruits", 9);

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PWD);
             PreparedStatement ps =
                     connection.prepareStatement("INSERT INTO products (product_name, category, in_stock) VALUES (?, ?, ?)")) {

            ps.setString(1, product.getProductName());
            ps.setString(2, product.getCategory());
            ps.setInt(3, product.getInStock());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateProduct() {

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PWD);
             PreparedStatement ps =
                     connection.prepareStatement("UPDATE products SET in_stock = 10 WHERE product_name = ?")) {
            String productName = "pear" ;
            ps.setString(1, productName);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteProduct() {

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PWD);
             PreparedStatement ps =
                     connection.prepareStatement("DELETE FROM products WHERE product_name = ?")) {
            String productName = "apple" ;
            ps.setString(1, productName);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void selectProduct() {

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PWD);
             PreparedStatement ps =
                     connection.prepareStatement("SELECT * FROM products WHERE product_name = ?")) {
            String productName = "carrot" ;
            ps.setString(1, productName);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getLong("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setCategory(rs.getString("category"));
                product.setInStock(rs.getInt("in_stock"));

                System.out.println(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void selectProductsWithSales() {

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
                productsWithSales.setProductId(rs.getInt("product_id"));
                productsWithSales.setProductName(rs.getString("product_name"));
                productsWithSales.setCategory(rs.getString("category"));
                productsWithSales.setQuantity(rs.getInt("quantity"));
                productsWithSales.setPrice(rs.getDouble("price"));

                System.out.println(productsWithSales);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createAndFillInTables() throws SQLException {

        String createProductsTable = " CREATE TABLE IF NOT EXISTS products (" +
                "product_id   BIGINT NOT NULL AUTO_INCREMENT, " +
                "product_name VARCHAR(50) NOT NULL, " +
                "category     VARCHAR(50), " +
                "in_stock     INT," +
                "PRIMARY KEY (product_id))" ;
        String createSalesTable = "CREATE TABLE IF NOT EXISTS sales (" +
                "sale_id     BIGINT NOT NULL AUTO_INCREMENT," +
                "product_id  BIGINT NOT NULL," +
                "quantity    INT NOT NULL, " +
                "price       DECIMAL(10,2) NOT NULL," +
                "CONSTRAINT FOREIGN KEY (product_id) REFERENCES products(product_id)" +
                "ON DELETE CASCADE," +
                "PRIMARY KEY (sale_id))" ;

        List<Product> products = new ArrayList<>();
        products.add(new Product("Apple", "Fruits", 87));
        products.add(new Product("Orange", "Fruits", 102));
        products.add(new Product("Banana", "Fruits", 99));
        products.add(new Product("Carrot", "Vegetables", 65));
        products.add(new Product("Cucumber", "Vegetables", 65));

        List<Sale> sales = new ArrayList<>();
        sales.add(new Sale(1, 12, new BigDecimal("10.5")));
        sales.add(new Sale(2, 15, new BigDecimal("11.75")));
        sales.add(new Sale(3, 16, new BigDecimal("7.1")));
        sales.add(new Sale(4, 8, new BigDecimal("2.43")));
        sales.add(new Sale(5, 9, new BigDecimal("4.5")));
        sales.add(new Sale(1, 3, new BigDecimal("14.5")));

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PWD)) {

            try (PreparedStatement ps = connection.prepareStatement(createProductsTable)) {
                ps.executeUpdate();
            }
            try (PreparedStatement ps = connection.prepareStatement(createSalesTable)) {
                ps.executeUpdate();
            }

            try (PreparedStatement ps =
                         connection.prepareStatement("INSERT INTO products (product_name, category, in_stock) VALUES (?, ?, ?)")) {

                for (int i = 0; i < products.size(); i++) {
                    ps.setString(1, products.get(i).getProductName());
                    ps.setString(2, products.get(i).getCategory());
                    ps.setInt(3, products.get(i).getInStock());

                    ps.executeUpdate();
                }
            }
            try (PreparedStatement ps =
                         connection.prepareStatement("INSERT INTO sales (product_id, quantity, price) VALUES (?, ?, ?)")) {

                for (int i = 0; i < sales.size(); i++) {
                    ps.setInt(1, sales.get(i).getProductId());
                    ps.setInt(2, sales.get(i).getQuantity());
                    ps.setBigDecimal(3, sales.get(i).getPrice());

                    ps.executeUpdate();
                }
            }
        }
    }
}
