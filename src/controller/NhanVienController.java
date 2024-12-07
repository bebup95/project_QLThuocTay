package controller;

import dao.NhanVienDAO;
import entities.NhanVien;
import gui.page.NhanVienPage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import utils.MessageDialog;
import utils.Validation;
import static utils.Validation.isPhoneNumber;


public class NhanVienController extends InterfaceController<NhanVien, String> {

    public NhanVienDAO NV_DAO = new NhanVienDAO();
    public NhanVienPage NV_GUI;

    public NhanVienController() {
    }

    public NhanVienController(NhanVienPage NV_GUI) {
        this.NV_GUI = NV_GUI;
    }

    @Override
    public void create(NhanVien e) {
        NV_DAO.create(e);
    }

    @Override
    public void update(NhanVien e) {
        NV_DAO.update(e);
    }

    @Override
    public void deleteById(String id) {
        NV_DAO.deleteById(id);
    }

    @Override
    public List<NhanVien> getAllList() {
        return NV_DAO.selectAll();
    }

    public int getSoLuongNV() {
        return this.getAllList().size();
    }

    @Override
    public NhanVien selectById(String id) {
        return NV_DAO.selectById(id);
    }

    public List<NhanVien> getSearchTable(String text, String searchType) {
        text = text.toLowerCase();
        List result = new ArrayList<NhanVien>();

        switch (searchType) {
            case "Tất cả" -> {
                for (NhanVien e : this.getAllList()) {
                    if (e.getId().toLowerCase().contains(text)
                            || e.getHoTen().toLowerCase().contains(text)
                            || e.getSdt().toLowerCase().contains(text)
                            || String.valueOf(e.getNamSinh()).toLowerCase().contains(text)) {
                        result.add(e);
                    }
                }
            }
            case "Mã" -> {
                for (NhanVien e : this.getAllList()) {
                    if (e.getId().toLowerCase().contains(text)) {
                        result.add(e);
                    }
                }
            }
            case "Tên" -> {
                for (NhanVien e : this.getAllList()) {
                    if (e.getHoTen().toLowerCase().contains(text)) {
                        result.add(e);
                    }
                }
            }
            case "Số điện thoại" -> {
                for (NhanVien e : this.getAllList()) {
                    if (e.getGioiTinh().toLowerCase().contains(text)) {
                        result.add(e);
                    }
                }
            }
            case "Năm sinh" -> {
                for (NhanVien e : this.getAllList()) {
                    if (String.valueOf(e.getNamSinh()).toLowerCase().contains(text)) {
                        result.add(e);
                    }
                }
            }
            default ->
                throw new AssertionError();
        }

        return result;
    }

    

}
