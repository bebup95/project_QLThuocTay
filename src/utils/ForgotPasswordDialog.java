package utils;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author nhat
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import utils.EmailSender;
import utils.ResetCodeGenerator;

public class ForgotPasswordDialog extends JDialog {
    private JTextField emailField;
    private JButton submitButton;

    public ForgotPasswordDialog(Frame parent) {
        super(parent, "Lấy lại mật khẩu", true); // true cho phép là modal dialog
        setLayout(new FlowLayout());
        setSize(400, 150);

        JLabel emailLabel = new JLabel("Nhập email của bạn:");
        emailField = new JTextField(20);
        submitButton = new JButton("Gửi mã reset");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                if (!email.isEmpty()) {
                    // Gửi email với mã code
                    String resetCode = ResetCodeGenerator.generateResetCode();
                    EmailSender.sendPasswordResetCode(email, resetCode);
                    
                    JOptionPane.showMessageDialog(ForgotPasswordDialog.this, "Mã reset đã được gửi đến email của bạn.");
                    ForgotPasswordDialog.this.dispose(); // Đóng cửa sổ sau khi gửi mã
                } else {
                    JOptionPane.showMessageDialog(ForgotPasswordDialog.this, "Vui lòng nhập email hợp lệ.");
                }
            }
        });

        add(emailLabel);
        add(emailField);
        add(submitButton);

        setLocationRelativeTo(parent); // Đặt vị trí của dialog ở giữa màn hình
    }
}
