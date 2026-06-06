package com.adityakost.controllers;

import com.adityakost.entity.CalonPenyewa;
import com.adityakost.entity.Complaint;
import com.adityakost.entity.Kamar;
import com.adityakost.entity.Pemesanan;
import com.adityakost.entity.Penjaga;
import com.adityakost.entity.Pemilik;
import com.adityakost.repo.CalonPenyewaRepo;
import com.adityakost.repo.ComplaintsRepo;
import com.adityakost.service.CalonPenyewaService;
import com.adityakost.service.GambarService;
import com.adityakost.service.KamarService;
import com.adityakost.service.PemesananService;
import com.adityakost.service.PenjagaService;
import com.adityakost.service.PemilikService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("")
public class HomeController {

    @Autowired
    private KamarService kamarService;

    @Autowired
    private GambarService gambarService;

    @Autowired
    private CalonPenyewaRepo calonPenyewaRepo;

    @Autowired
    private PemesananService pemesananService;

    @Autowired
    private PenjagaService penjagaService;

    @Autowired
    private PemilikService pemilikService;

    @Autowired
    private CalonPenyewaService calonPenyewaService;
    @Autowired
    private ComplaintsRepo complaintRepo;

    // Menampilkan halaman utama dengan daftar kamar
    @GetMapping

    public String welcome(Model model) {
        List<Kamar> listKamar = kamarService.getAllKamar();
        System.out.println("List Kamar: " + listKamar); // Tambahkan log ini
        model.addAttribute("listKamar", listKamar);

        // Menyertakan gambar Base64 untuk setiap kamar
        Map<Long, String> gambarMap = new HashMap<>();
        for (Kamar kamar : listKamar) {
            String gambarBase64 = gambarService.getGambarBase64(kamar.getId());
            if (gambarBase64 != null) {
                gambarMap.put(kamar.getId(), gambarBase64);
            }
        }
        model.addAttribute("gambarMap", gambarMap);

        return "index";
    }

    @GetMapping("index")
    public String getIndex() {
        return "index";
    }

    // Menampilkan halaman login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Proses login
    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session, Model model) {
        CalonPenyewa calonPenyewa = calonPenyewaRepo.findByEmailAndPassword(email, password);

        if (calonPenyewa != null) {
            session.setAttribute("user", calonPenyewa);
            return "redirect:/";
        } else {
            model.addAttribute("error", "Email atau password salah");
            return "login";
        }
    }

    @GetMapping("/login-penjaga")
    public String loginPenjaga() {
        return "login-penjaga";
    }

    @GetMapping("/login-pemilik")
    public String loginPemilik() {
        return "login-pemilik";
    }

    // Proses login
    @PostMapping("/login-penjaga")
    public String loginPenjaga(@RequestParam("email-penjaga") String emailPenjaga,
            @RequestParam("password-penjaga") String passwordPenjaga,
            HttpSession session, Model model) {

        Penjaga penjaga = penjagaService.findByEmailPenjagaAndPasswordPenjaga(emailPenjaga, passwordPenjaga);

        if (penjaga != null) {
            session.setAttribute("penjaga", penjaga);
            return "home"; // Redirect ke halaman home jika login berhasil
        } else {
            model.addAttribute("error", "Email atau password salah");
            return "login-penjaga";
        }
    }

    @PostMapping("/login-pemilik")
    public String loginPemilik(@RequestParam("email-pemilik") String emailPemilik,
            @RequestParam("password-pemilik") String passwordPemilik,
            HttpSession session, Model model) {

        Pemilik pemilik = pemilikService.findByEmailPemilikAndPasswordPemilik(emailPemilik, passwordPemilik);

        if (pemilik != null) {
            session.setAttribute("pemilik", pemilik);
            return "homepemilik"; // Redirect ke halaman home jika login berhasil
        } else {
            model.addAttribute("error", "Email atau password salah");
            return "login-pemilik";
        }
    }

    // Menampilkan halaman register
    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("calonPenyewa", new CalonPenyewa());
        return modelAndView;
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute CalonPenyewa calonPenyewa, RedirectAttributes redirectAttributes) {
        if (calonPenyewaRepo.existsByUsername(calonPenyewa.getUsername())) {
            redirectAttributes.addFlashAttribute("error", "Username sudah terdaftar");
            return "redirect:/register";
        }

        if (calonPenyewaRepo.existsByEmail(calonPenyewa.getEmail())) {
            redirectAttributes.addFlashAttribute("error", "Email sudah terdaftar");
            return "redirect:/register";
        }

        if (calonPenyewaRepo.existsByPhoneNumber(calonPenyewa.getPhoneNumber())) {
            redirectAttributes.addFlashAttribute("error", "Nomor HP sudah terdaftar");
            return "redirect:/register";
        }

        calonPenyewaRepo.save(calonPenyewa);
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/login";
    }

    // Menambahkan kamar
    @GetMapping("/AddKamar")
    public String addKamar() {
        return "AddKamar";
    }

    @PostMapping("/AddKamar")
    public String addKamar(
            @RequestParam("nomorKamar") String nomorKamar,
            @RequestParam("type") String type,
            @RequestParam("harga") float harga,
            @RequestParam("gambar") MultipartFile gambarFile,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        Object penjaga = session.getAttribute("penjaga");
        Object pemilik = session.getAttribute("pemilik");

        if (penjaga == null && pemilik == null) {
            return "redirect:/login";
        }

        try {

            kamarService.saveKamarWithGambar(
                    nomorKamar,
                    type,
                    harga,
                    gambarFile);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Kamar berhasil ditambahkan");

            return "redirect:/showKamar";

        } catch (Exception e) {

            e.printStackTrace();

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    e.getMessage());

            return "redirect:/AddKamar";
        }
    }

    @GetMapping("/pesan")
    public String pesan(@RequestParam("idKamar") Long idKamar, Model model, HttpSession session) {
        // Memeriksa apakah user sudah login
        CalonPenyewa user = (CalonPenyewa) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        Kamar kamar = kamarService.getKamarById(idKamar);
        if (kamar == null) {
            return "redirect:/";
        }

        System.out.println("ID Kamar: " + kamar.getId()); // Tambahkan log ini
        model.addAttribute("kamar", kamar);
        model.addAttribute("user", user); // Menambahkan user ke model

        return "pesan";
    }

    @PostMapping("/pesan")
    public String simpanPemesanan(
            @RequestParam("idCalonPenyewa") Long idCalonPenyewa,
            @RequestParam("idKamar") Long idKamar,
            @RequestParam("durasi") int durasi,
            @RequestParam("totalBiaya") float totalBiaya,
            RedirectAttributes redirectAttributes) {
        // Validasi input
        if (idCalonPenyewa == null || idKamar == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Data tidak lengkap. Silakan coba lagi.");
            return "redirect:/pesan";
        }

        // Buat objek Pemesanan
        Pemesanan pemesanan = new Pemesanan();
        pemesanan.setDurasi(durasi);
        pemesanan.setTotalBiaya(totalBiaya);

        // Simpan Pemesanan melalui service
        pemesananService.savePemesanan(pemesanan, idCalonPenyewa, idKamar);

        // Berikan flash message dan redirect
        redirectAttributes.addFlashAttribute("successMessage", "Pemesanan berhasil disimpan!");
        return "redirect:/";
    }

    @GetMapping("/showKamar")
    public String showKamar(Model model) {
        List<Kamar> listKamar = kamarService.getAllKamar();
        model.addAttribute("listKamar", listKamar);

        // Menyertakan gambar Base64 untuk setiap kamar
        Map<Long, String> gambarMap = new HashMap<>();
        for (Kamar kamar : listKamar) {
            String gambarBase64 = gambarService.getGambarBase64(kamar.getId());
            if (gambarBase64 != null) {
                gambarMap.put(kamar.getId(), gambarBase64);
            }
        }
        model.addAttribute("gambarMap", gambarMap);

        return "showKamar";
    }

    // Proses logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // show penhuni
    @GetMapping("/penghuni")
    public String getPenghuni(HttpSession session, Model model) {
        // Periksa apakah penjaga atau pemilik sudah login
        Object penjaga = session.getAttribute("penjaga");
        Object pemilik = session.getAttribute("pemilik");
        if (penjaga == null && pemilik == null) {
            return "redirect:/login"; // Jika tidak ada sesi penjaga atau pemilik, redirect ke halaman login
        }

        // Ambil data pemesanan untuk penghuni dengan kamar
        List<Pemesanan> pemesananList = calonPenyewaService.getPenghuniDenganKamar();
        model.addAttribute("pemesananList", pemesananList);

        return "penghuni"; // Tampilkan halaman penghuni
    }

    @GetMapping("/complain")
    public String showComplain(HttpSession session, Model model) {
        // Periksa apakah penjaga sudah login
        Penjaga penjaga = (Penjaga) session.getAttribute("penjaga");
        if (penjaga == null) {
            return "redirect:/login-penjaga";
        }

        // Ambil semua komplain dari database
        List<Complaint> complaints = complaintRepo.findAll();
        model.addAttribute("complaints", complaints);

        return "complain";
    }

    @GetMapping("/addComplain")
    public String getComplain(HttpSession session, Model model) {
        // Periksa apakah pengguna sudah login
        CalonPenyewa loggedInUser = (CalonPenyewa) session.getAttribute("user");
        if (loggedInUser == null) {
            return "redirect:/login"; // Jika belum login, redirect ke halaman login
        }

        // Tambahkan informasi pengguna ke model jika diperlukan
        model.addAttribute("user", loggedInUser);
        return "addComplain"; // Jika sudah login, tampilkan halaman komplain
    }

    @PostMapping("/addComplain")
    public String addComplain(@RequestParam("complain") String complain,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {
        // Periksa apakah pengguna sudah login
        CalonPenyewa loggedInUser = (CalonPenyewa) session.getAttribute("user");
        if (loggedInUser == null) {
            return "redirect:/login";
        }

        // Validasi komplain
        if (complain == null || complain.trim().isEmpty()) {
            model.addAttribute("errorMessage", "Complaint cannot be empty!");
            return "addComplain";
        }

        // Simpan komplain baru
        Complaint newComplaint = new Complaint();
        newComplaint.setUser(loggedInUser);
        newComplaint.setComplain(complain);
        complaintRepo.save(newComplaint);

        redirectAttributes.addFlashAttribute("successMessage", "Complaint submitted successfully!");
        return "redirect:/index";
    }

    @GetMapping("/homepemilik")
    public String getHomepemilik() {
        return "homepemilik";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/edit/{id}")
    public String editKamar(
            @PathVariable Long id,
            Model model) {

        Kamar kamar = kamarService.getKamarById(id);

        model.addAttribute("kamar", kamar);

        return "Edit";
    }

    @PostMapping("/kamar/edit/{id}")
    public String updateKamar(
            @PathVariable Long id,
            @RequestParam("type") String type,
            @RequestParam("harga") float harga,
            @RequestParam(value = "gambarKamar", required = false) MultipartFile gambarFile,
            RedirectAttributes redirectAttributes) {

        try {

            Kamar existing = kamarService.getKamarById(id);

            existing.setType(type);
            existing.setHarga(harga);

            kamarService.saveKamar(existing);

            if (gambarFile != null && !gambarFile.isEmpty()) {
                gambarService.saveGambarWithKamar(
                        existing,
                        gambarFile);
            }

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Kamar berhasil diperbarui");

        } catch (Exception e) {

            e.printStackTrace();

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    e.getMessage());
        }

        return "redirect:/showKamar";
    }

    @GetMapping("/delete-penghuni/{id}")
    public String deletePenghuni(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            // Panggil service untuk menghapus CalonPenyewa
            calonPenyewaService.deleteCalonPenyewaById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Penghuni berhasil dihapus!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Gagal menghapus penghuni: " + e.getMessage());
        }
        return "redirect:/penghuni"; // Redirect ke halaman daftar penghuni
    }

    @GetMapping("/kamar/list")
    public String listKamar(Model model) {
        List<Kamar> kamarList = kamarService.getAllKamar();
        model.addAttribute("listKamar", kamarList);

        // Ambil gambar kamar yang terhubung
        Map<Long, String> gambarMap = gambarService.getGambarMapForKamar(kamarList);
        model.addAttribute("gambarMap", gambarMap);

        return "showKamar"; // Nama file HTML yang sesuai
    }

    public Map<Long, String> getGambarMapForKamar(List<Kamar> kamarList) {
        Map<Long, String> gambarMap = new HashMap<>();
        for (Kamar kamar : kamarList) {
            // Ambil gambar yang sesuai dan encode dalam base64
            String base64Image = gambarService.getGambarBase64(kamar.getId());

            gambarMap.put(kamar.getId(), base64Image);
        }
        return gambarMap;
    }

    @GetMapping("/delete/{id}")
    public String deleteKamar(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        try {

            Kamar kamar = kamarService.getKamarById(id);

            if (kamar == null) {
                throw new RuntimeException(
                        "Kamar tidak ditemukan");
            }

            kamarService.deleteKamar(id);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Kamar berhasil dihapus");

        } catch (Exception e) {

            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    e.getMessage());
        }

        return "redirect:/showKamar";
    }

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        // Retrieve the user object from the session
        CalonPenyewa calonPenyewa = (CalonPenyewa) session.getAttribute("user");

        if (calonPenyewa == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", calonPenyewa);
        return "profile";
    }

    @PostMapping("/update-profile")
    public String updateProfile(@ModelAttribute CalonPenyewa calonPenyewa, HttpSession session) {
        // Ambil entitas yang ada dari database
        CalonPenyewa existingCalonPenyewa = calonPenyewaService.getCalonPenyewaById(calonPenyewa.getIdCalonPenyewa());

        // Update properti yang diperlukan
        existingCalonPenyewa.setUsername(calonPenyewa.getUsername());
        existingCalonPenyewa.setPassword(calonPenyewa.getPassword());
        existingCalonPenyewa.setEmail(calonPenyewa.getEmail());
        existingCalonPenyewa.setJenisKelamin(calonPenyewa.getJenisKelamin());
        existingCalonPenyewa.setAlamat(calonPenyewa.getAlamat());
        existingCalonPenyewa.setPhoneNumber(calonPenyewa.getPhoneNumber());

        // Simpan entitas yang telah diperbarui
        calonPenyewaService.updateCalonPenyewa(existingCalonPenyewa);

        // Perbarui sesi
        session.setAttribute("user", existingCalonPenyewa);

        return "redirect:/index"; // Redirect ke halaman utama
    }

}
