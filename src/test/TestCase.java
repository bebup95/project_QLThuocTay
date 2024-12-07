/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;
import utils.BCrypt;

/**
 *
 * @author nhat
 */
public class TestCase {
    public static void main(String[] args) {
        String password = "123123"; // Mật khẩu mới
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt()); // Băm mật khẩu

        System.out.println("Mật khẩu đã băm: " + hashedPassword); // In ra mật khẩu đã băm
    }
}
