package gui.dialog;

import controller.NhanVienController;
import controller.TaiKhoanController;
import controller.VaiTroController;
import entities.NhanVien;
import entities.TaiKhoan;
import entities.VaiTro;
import gui.MainLayout;
import gui.dialog.InfoDialog;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import utils.MessageDialog;

public class InfoChangePasswordDialog extends javax.swing.JDialog {

    private final TaiKhoanController TK_CON = new TaiKhoanController();
    private MainLayout main;
    private TaiKhoan tk;

    public InfoChangePasswordDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public InfoChangePasswordDialog(java.awt.Frame parent, boolean modal, MainLayout main, TaiKhoan tk) {
        super(parent, modal);
        initComponents();
        this.main = main;
        this.tk = tk;
    }

    // Mã hóa mật khẩu bằng SHA-256
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] salt = generateSalt();
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Tạo salt ngẫu nhiên
    private byte[] generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    // Xác minh mật khẩu bằng cách kiểm tra với giá trị đã mã hóa
    private boolean verifyPassword(String enteredPassword, String storedPassword) {
        String[] parts = storedPassword.split(":");
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        byte[] hashedPassword = Base64.getDecoder().decode(parts[1]);

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] enteredHashedPassword = md.digest(enteredPassword.getBytes());

            return MessageDigest.isEqual(hashedPassword, enteredHashedPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isValidateFields() {
        if (txtCurrentPassword.getText().trim().equals("") || txtCurrentPassword.getText().length() < 6) {
            MessageDialog.warring(this, "Mật khẩu hiện tại không được rỗng và có ít nhất 6 kí tự!");
            txtCurrentPassword.requestFocus();
            return false;
        } else {
            if (!verifyPassword(txtCurrentPassword.getText(), tk.getPassword())) {
                MessageDialog.warring(this, "Mật khẩu hiện tại không chính xác!");
                txtCurrentPassword.requestFocus();
                return false;
            }
        }

        if (txtNewPassword.getText().trim().equals("") || txtNewPassword.getText().length() < 6) {
            MessageDialog.warring(this, "Mật khẩu mới không được rỗng và có ít nhất 6 kí tự!");
            txtNewPassword.requestFocus();
            return false;
        }

        if (txtReNewPassword.getText().trim().equals("") || txtReNewPassword.getText().length() < 6) {
            MessageDialog.warring(this, "Nhập lại mật khẩu mới không được rỗng và có ít nhất 6 kí tự!");
            txtReNewPassword.requestFocus();
            return false;
        } else {
            if (!txtNewPassword.getText().equals(txtReNewPassword.getText())) {
                MessageDialog.warring(this, "Nhập lại mật khẩu mới không chính xác!");
                txtReNewPassword.requestFocus();
                return false;
            }
        }

        return true;
    }

    private TaiKhoan getInputFields() {
        String id = tk.getId();
        String username = tk.getUsername();
        String password = hashPassword(txtReNewPassword.getText());
        String idNV = tk.getNhanVien().getId();
        NhanVien nhanVien = new NhanVienController().selectById(idNV);
        String idVT = tk.getVaiTro().getId();
        VaiTro vaiTro = new VaiTroController().selectById(idVT);

        return new TaiKhoan(id, username, password, nhanVien, vaiTro);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel15 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        lblHoTen = new javax.swing.JLabel();
        txtCurrentPassword = new javax.swing.JPasswordField();
        jPanel19 = new javax.swing.JPanel();
        lblHoTen1 = new javax.swing.JLabel();
        txtNewPassword = new javax.swing.JPasswordField();
        jPanel20 = new javax.swing.JPanel();
        lblHoTen2 = new javax.swing.JLabel();
        txtReNewPassword = new javax.swing.JPasswordField();
        jPanel8 = new javax.swing.JPanel();
        btnHuy = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel15.setBackground(new java.awt.Color(135, 206, 250));
        jPanel15.setMinimumSize(new java.awt.Dimension(100, 60));
        jPanel15.setPreferredSize(new java.awt.Dimension(500, 50));
        jPanel15.setLayout(new java.awt.BorderLayout());

        jLabel8.setFont(new java.awt.Font("Roboto Medium", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("ĐỔI MẬT KHẨU");
        jPanel15.add(jLabel8, java.awt.BorderLayout.CENTER);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 16));

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setPreferredSize(new java.awt.Dimension(500, 40));
        jPanel18.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 0));

        lblHoTen.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblHoTen.setText("Mật khẩu hiện tại");
        lblHoTen.setMaximumSize(new java.awt.Dimension(44, 40));
        lblHoTen.setPreferredSize(new java.awt.Dimension(150, 40));
        jPanel18.add(lblHoTen);

        txtCurrentPassword.setPreferredSize(new java.awt.Dimension(330, 40));
        jPanel18.add(txtCurrentPassword);

        jPanel1.add(jPanel18);

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setPreferredSize(new java.awt.Dimension(500, 40));
        jPanel19.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 0));

        lblHoTen1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblHoTen1.setText("Mật khẩu mới");
        lblHoTen1.setMaximumSize(new java.awt.Dimension(44, 40));
        lblHoTen1.setPreferredSize(new java.awt.Dimension(150, 40));
        jPanel19.add(lblHoTen1);

        txtNewPassword.setPreferredSize(new java.awt.Dimension(330, 40));
        jPanel19.add(txtNewPassword);

        jPanel1.add(jPanel19);

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setPreferredSize(new java.awt.Dimension(500, 40));
        jPanel20.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 0));

        lblHoTen2.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblHoTen2.setText("Nhập lại mật khẩu mới");
        lblHoTen2.setMaximumSize(new java.awt.Dimension(44, 40));
        lblHoTen2.setPreferredSize(new java.awt.Dimension(150, 40));
        jPanel20.add(lblHoTen2);

        txtReNewPassword.setPreferredSize(new java.awt.Dimension(330, 40));
        jPanel20.add(txtReNewPassword);

        jPanel1.add(jPanel20);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 8, 5));

        btnHuy.setBackground(new java.awt.Color(255, 127, 80));
        btnHuy.setFont(new java.awt.Font("Roboto Mono Medium", 0, 16)); // NOI18N
        btnHuy.setForeground(new java.awt.Color(255, 255, 255));
        btnHuy.setText("HỦY BỎ");
        btnHuy.setBorderPainted(false);
        btnHuy.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHuy.setFocusPainted(false);
        btnHuy.setFocusable(false);
        btnHuy.setPreferredSize(new java.awt.Dimension(200, 40));
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });
        jPanel8.add(btnHuy);

        btnUpdate.setBackground(new java.awt.Color(0, 204, 102));
        btnUpdate.setFont(new java.awt.Font("Roboto Mono Medium", 0, 16)); // NOI18N
        btnUpdate.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdate.setText("LƯU");
        btnUpdate.setBorderPainted(false);
        btnUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUpdate.setFocusPainted(false);
        btnUpdate.setFocusable(false);
        btnUpdate.setPreferredSize(new java.awt.Dimension(200, 40));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        jPanel8.add(btnUpdate);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        this.dispose();
        InfoDialog dialog = new InfoDialog(null, true, main, tk);
        dialog.setVisible(true);
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (isValidateFields()) {
            TaiKhoan e = getInputFields();
            MessageDialog.info(this, "Cập nhập thành công!");
            TK_CON.update(e);
            this.dispose();
        }
    }//GEN-LAST:event_btnUpdateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel lblHoTen;
    private javax.swing.JLabel lblHoTen1;
    private javax.swing.JLabel lblHoTen2;
    private javax.swing.JPasswordField txtCurrentPassword;
    private javax.swing.JPasswordField txtNewPassword;
    private javax.swing.JPasswordField txtReNewPassword;
    // End of variables declaration//GEN-END:variables
}
