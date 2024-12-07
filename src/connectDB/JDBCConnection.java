package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCConnection {

    static String url = "jdbc:sqlserver://localhost:1433;databaseName=QLTHUOC;encrypt=true;trustServerCertificate=true";
    static String user = "sa";
    static String password = "YourPassword123";

    // Phương thức này trả về một đối tượng Connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    // Phương thức này trả về một PreparedStatement với các tham số đầu vào
    public static PreparedStatement getStmt(String sql, Object... args) throws SQLException {
        Connection con = getConnection();
        PreparedStatement stmt;
        if (sql.trim().startsWith("{")) {
            stmt = con.prepareCall(sql); // Gọi thủ tục lưu trữ
        } else {
            stmt = con.prepareStatement(sql); // Thực hiện câu lệnh SQL thông thường
        }

        // Đặt các tham số cho PreparedStatement
        for (int i = 0; i < args.length; i++) {
            stmt.setObject(i + 1, args[i]);
        }
        return stmt;
    }

    // Phương thức này thực hiện các câu lệnh cập nhật (INSERT, UPDATE, DELETE)
    public static int update(String sql, Object... args) {
        try (PreparedStatement stmt = getStmt(sql, args)) {
            return stmt.executeUpdate(); // Thực thi câu lệnh SQL
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thực hiện update", e); // Xử lý lỗi và in ra
        }
    }

    // Phương thức này thực hiện các câu lệnh truy vấn (SELECT) và trả về ResultSet
    public static ResultSet query(String sql, Object... args) throws SQLException {
        PreparedStatement stmt = getStmt(sql, args);
        return stmt.executeQuery();
    }

}
