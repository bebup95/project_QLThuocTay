package dao;

// Nhập các lớp cần thiết cho việc kết nối cơ sở dữ liệu, xử lý kết quả và đại diện cho thực thể.
import connectDB.JDBCConnection; // Xử lý kết nối JDBC đến cơ sở dữ liệu.
import entities.NhanVien; // Đại diện cho thực thể nhân viên.
import entities.TaiKhoan; // Đại diện cho thực thể tài khoản người dùng.
import entities.VaiTro; // Đại diện cho thực thể vai trò.
import java.sql.ResultSet; // Để xử lý các tập kết quả SQL.
import java.util.ArrayList; // Để sử dụng mảng động.
import java.util.List; // Để sử dụng các bộ sưu tập danh sách.

public class TaiKhoanDAO extends InterfaceDAO<TaiKhoan, String> {
    // Các câu lệnh SQL cho các thao tác khác nhau
    private final String INSERT_SQL = "INSERT INTO TaiKhoan values (?,?,?,?,?)"; // SQL để chèn một tài khoản mới
    private final String UPDATE_SQL = "UPDATE TaiKhoan SET username=?, password=?, idNV=?, idVT=? where idTK=?"; // SQL để cập nhật một tài khoản
    private final String DELETE_BY_ID = "DELETE from TaiKhoan where idTK = ?"; // SQL để xóa một tài khoản theo ID

    // SQL để chọn tất cả tài khoản với thông tin nhân viên và vai trò liên quan
    private final String SELECT_ALL_SQL
            = "SELECT tk.*, nv.hoTen, nv.sdt, nv.gioiTinh, nv.namSinh, nv.ngayVaoLam, vt.ten as tenVT "
            + "FROM TaiKhoan tk "
            + "INNER JOIN NhanVien nv ON tk.idNV = nv.idNV "
            + "INNER JOIN VaiTro vt ON tk.idVT = vt.idVT";
    
    // SQL để chọn một tài khoản theo ID với thông tin liên quan
    private final String SELECT_BY_ID = "SELECT tk.*, nv.hoTen, nv.sdt, nv.gioiTinh, nv.namSinh, nv.ngayVaoLam, vt.ten as tenVT "
            + "FROM TaiKhoan tk "
            + "INNER JOIN NhanVien nv ON tk.idNV = nv.idNV "
            + "INNER JOIN VaiTro vt ON tk.idVT = vt.idVT "
            + "WHERE tk.idTK = ?";
    
    // SQL để chọn một tài khoản theo tên người dùng với thông tin liên quan
    private final String SELECT_BY_USERNAME = "SELECT tk.*, nv.hoTen, nv.sdt, nv.gioiTinh, nv.namSinh, nv.ngayVaoLam, vt.ten as tenVT "
            + "FROM TaiKhoan tk "
            + "INNER JOIN NhanVien nv ON tk.idNV = nv.idNV "
            + "INNER JOIN VaiTro vt ON tk.idVT = vt.idVT "
            + "WHERE tk.username = ?";

    @Override
    public void create(TaiKhoan e) {
        // Chèn một bản ghi TaiKhoan mới vào cơ sở dữ liệu bằng dữ liệu từ thực thể
        JDBCConnection.update(INSERT_SQL, e.getId(), e.getUsername(), e.getPassword(), e.getNhanVien().getId(), e.getVaiTro().getId());
    }

    @Override
    public void update(TaiKhoan e) {
        // Cập nhật một bản ghi TaiKhoan hiện có trong cơ sở dữ liệu
        JDBCConnection.update(UPDATE_SQL, e.getUsername(), e.getPassword(), e.getNhanVien().getId(), e.getVaiTro().getId(), e.getId());
    }

    @Override
    public void deleteById(String id) {
        // Xóa một bản ghi TaiKhoan hiện có theo ID
        JDBCConnection.update(DELETE_BY_ID, id);
    }

    @Override
    protected List<TaiKhoan> selectBySql(String sql, Object... args) {
        List<TaiKhoan> listE = new ArrayList<>(); // Danh sách để chứa các thực thể TaiKhoan
        try {
            // Thực thi câu lệnh SQL và lấy tập kết quả
            ResultSet rs = JDBCConnection.query(sql, args);
            while (rs.next()) { // Duyệt qua tập kết quả
                TaiKhoan taiKhoan = new TaiKhoan(); // Tạo một thể hiện TaiKhoan mới
                taiKhoan.setId(rs.getString("idTK")); // Thiết lập ID từ tập kết quả
                taiKhoan.setUsername(rs.getString("username")); // Thiết lập tên người dùng
                taiKhoan.setPassword(rs.getString("password")); // Thiết lập mật khẩu

                // Tạo và cập nhật đối tượng NhanVien
                NhanVien nhanVien = new NhanVien();
                nhanVien.setId(rs.getString("idNV")); // Thiết lập ID nhân viên
                nhanVien.setHoTen(rs.getString("hoTen")); // Thiết lập họ tên
                nhanVien.setSdt(rs.getString("sdt")); // Thiết lập số điện thoại
                nhanVien.setGioiTinh(rs.getString("gioiTinh")); // Thiết lập giới tính
                nhanVien.setNamSinh(rs.getInt("namSinh")); // Thiết lập năm sinh
                nhanVien.setNgayVaoLam(rs.getDate("ngayVaoLam")); // Thiết lập ngày vào làm
                taiKhoan.setNhanVien(nhanVien); // Gán nhân viên cho tài khoản

                // Tạo và cập nhật đối tượng VaiTro
                VaiTro vaiTro = new VaiTro();
                vaiTro.setId(rs.getString("idVT")); // Thiết lập ID vai trò
                vaiTro.setTen(rs.getString("tenVT")); // Thiết lập tên vai trò
                taiKhoan.setVaiTro(vaiTro); // Gán vai trò cho tài khoản

                listE.add(taiKhoan); // Thêm TaiKhoan đã được cập nhật vào danh sách
            }
            rs.getStatement().getConnection().close(); // Đóng kết nối cơ sở dữ liệu
            return listE; // Trả về danh sách các thực thể TaiKhoan
        } catch (Exception e) {
            throw new RuntimeException(e); // Xử lý ngoại lệ bằng cách ném ra ngoại lệ runtime
        }
    }

    @Override
    public List<TaiKhoan> selectAll() {
        // Chọn tất cả bản ghi TaiKhoan và trả về chúng
        return this.selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public TaiKhoan selectById(String id) {
        // Chọn một TaiKhoan theo ID
        List<TaiKhoan> list = selectBySql(SELECT_BY_ID, id);
        if (list.isEmpty()) { // Kiểm tra xem có bản ghi nào không
            return null; // Trả về null nếu không tìm thấy tài khoản
        }
        return list.get(0); // Trả về TaiKhoan đã tìm thấy
    }

    public TaiKhoan selectByUsername(String username) {
        // Chọn một TaiKhoan theo tên người dùng
        List<TaiKhoan> list = selectBySql(SELECT_BY_USERNAME, username);
        if (list.isEmpty()) { // Kiểm tra xem có bản ghi nào không
            return null; // Trả về null nếu không tìm thấy tài khoản
        }
        return list.get(0); // Trả về TaiKhoan đã tìm thấy
    }
}