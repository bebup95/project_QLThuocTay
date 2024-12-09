/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;
import java.util.Random;
/**
 *
 * @author nhat
 */
public class ResetCodeGenerator {
    public static String generateResetCode() {
        Random rand = new Random();
        int code = rand.nextInt(900000) + 100000;  // Tạo mã 6 chữ số ngẫu nhiên
        return String.valueOf(code);
    }
}