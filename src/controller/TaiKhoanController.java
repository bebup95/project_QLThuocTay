package controller;

import dao.TaiKhoanDAO;
import entities.NhanVien;
import entities.TaiKhoan;
import entities.VaiTro;
import gui.page.TaiKhoanPage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.MessageDialog;
import utils.Validation;

public class TaiKhoanController extends InterfaceController<TaiKhoan, String> {

    public TaiKhoanDAO TK_DAO = new TaiKhoanDAO();
    public TaiKhoanPage TK_GUI;

    public TaiKhoanController() {
    }

    public TaiKhoanController(TaiKhoanPage TK_GUI) {
        this.TK_GUI = TK_GUI;
    }

    @Override
    public void create(TaiKhoan e) {
        TK_DAO.create(e);
    }

    @Override
    public void update(TaiKhoan e) {
        TK_DAO.update(e);
    }

    @Override
    public void deleteById(String id) {
        TK_DAO.deleteById(id);
    }

    @Override
    public List<TaiKhoan> getAllList() {
        return TK_DAO.selectAll();
    }

    public List<NhanVien> getListNV() {
        List<NhanVien> result = new ArrayList<>();
        this.getAllList().forEach(e -> {
            result.add(e.getNhanVien());
        });
        return result;
    }

    @Override
    public TaiKhoan selectById(String id) {
        return TK_DAO.selectById(id);
    }

    public TaiKhoan selectByUsername(String username) {
        return TK_DAO.selectByUsername(username);
    }

    public List<TaiKhoan> getSearchTable(String text, String searchType) {
        text = text.toLowerCase();
        List<TaiKhoan> result = new ArrayList<>();

        switch (searchType) {
            case "Tất cả" -> {
                for (TaiKhoan e : this.getAllList()) {
                    if (e.getId().toLowerCase().contains(text)
                            || e.getUsername().toLowerCase().contains(text)
                            || e.getNhanVien().getHoTen().toLowerCase().contains(text)) {
                        result.add(e);
                    }
                }
            }
            case "Username" -> {
                for (TaiKhoan e : this.getAllList()) {
                    if (e.getUsername().toLowerCase().contains(text)) {
                        result.add(e);
                    }
                }
            }
            case "Tên nhân viên" -> {
                for (TaiKhoan e : this.getAllList()) {
                    if (e.getNhanVien().getHoTen().toLowerCase().contains(text)) {
                        result.add(e);
                    }
                }
            }
            default -> throw new AssertionError();
        }

        return result;
    }

    public List<TaiKhoan> getFilterTable(String tenVT) {
        List<TaiKhoan> result = new ArrayList<>();

        for (TaiKhoan e : this.getAllList()) {
            if (e.getVaiTro().getTen().equals(tenVT)) {
                result.add(e);
            }
        }

        return result;
    }


}
