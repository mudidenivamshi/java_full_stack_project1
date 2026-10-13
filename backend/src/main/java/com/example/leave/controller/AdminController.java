package com.example.leave.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * AdminController handles admin login and logout.
 *
 * We use a simple HttpSession to track whether the admin is logged in.
 * No Spring Security is used — this is kept simple for a college project.
 *
 * Admin credentials are hardcoded below.
 * In a real project, credentials would be stored securely in a database.
 */
@Controller
public class AdminController {

    // ---- Hardcoded Admin Credentials ----
    // Change these to whatever you like.
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    // =========================================================
    // SHOW ADMIN LOGIN PAGE
    // =========================================================

    /**
     * Show the admin login form.
     * GET /admin/login
     */
    @GetMapping("/admin/login")
    public String showLoginPage(HttpSession session) {
        // If already logged in, go straight to leave list
        if (Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {
            return "redirect:/leave-list";
        }
        return "admin-login"; // renders admin-login.html
    }

    // =========================================================
    // HANDLE LOGIN FORM SUBMISSION
    // =========================================================

    /**
     * Process admin login.
     * POST /admin/login
     *
     * If username and password match, set "isAdmin" = true in the session.
     * Otherwise, show an error on the login page.
     */
    @PostMapping("/admin/login")
    public String processLogin(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            // ---- Login Successful ----
            // Store admin flag in session
            session.setAttribute("isAdmin", true);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Welcome, Admin! You can now approve or reject leave requests.");
            return "redirect:/leave-list";
        } else {
            // ---- Login Failed ----
            model.addAttribute("errorMessage", "Invalid username or password.");
            return "admin-login"; // show login page again with error
        }
    }

    // =========================================================
    // HANDLE LOGOUT
    // =========================================================

    /**
     * Log out the admin by invalidating the session.
     * POST /admin/logout
     */
    @PostMapping("/admin/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate(); // clears all session data
        redirectAttributes.addFlashAttribute("successMessage", "You have been logged out.");
        return "redirect:/leave-list";
    }
}
