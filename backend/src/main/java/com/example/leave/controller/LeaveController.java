package com.example.leave.controller;

import com.example.leave.model.LeaveRequest;
import com.example.leave.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * LeaveController handles all HTTP requests and maps them to Thymeleaf pages.
 *
 * @Controller marks this class as a Spring MVC controller.
 *
 * Every method either:
 *   - Returns a String (template name) to render a Thymeleaf page
 *   - Returns "redirect:/..." to redirect the browser to another URL
 *
 * The Model object is used to pass data from Java to the Thymeleaf template.
 */
@Controller
public class LeaveController {

    // Spring injects the LeaveService bean automatically
    @Autowired
    private LeaveService leaveService;

    // =========================================================
    // 1. DASHBOARD - Home Page
    // =========================================================

    /**
     * Show the dashboard at the root URL "/" and also at "/index".
     *
     * Passes dashboard statistics (total, pending, approved, rejected counts)
     * to the Thymeleaf template.
     *
     * @param model - Spring's Model object to pass data to the template
     * @return "index" - renders templates/index.html
     */
    @GetMapping({"/", "/index"})
    public String showDashboard(Model model) {
        model.addAttribute("totalCount", leaveService.getTotalCount());
        model.addAttribute("pendingCount", leaveService.getPendingCount());
        model.addAttribute("approvedCount", leaveService.getApprovedCount());
        model.addAttribute("rejectedCount", leaveService.getRejectedCount());
        return "index"; // renders index.html
    }

    // =========================================================
    // 2. APPLY LEAVE - Show Form
    // =========================================================

    /**
     * Show the "Apply for Leave" form page.
     *
     * We add an empty LeaveRequest object to the model so Thymeleaf
     * can bind the form fields to it using th:object and th:field.
     *
     * @param model - Spring's Model object
     * @return "apply-leave" - renders templates/apply-leave.html
     */
    @GetMapping("/apply-leave")
    public String showApplyLeaveForm(Model model) {
        // Add an empty LeaveRequest for the form binding
        model.addAttribute("leaveRequest", new LeaveRequest());
        return "apply-leave"; // renders apply-leave.html
    }

    // =========================================================
    // 3. APPLY LEAVE - Handle Form Submission
    // =========================================================

    /**
     * Handle the POST request when the "Apply Leave" form is submitted.
     *
     * Steps:
     *   1. Validate that all required fields are filled.
     *   2. Validate that end date is not before start date.
     *   3. If validation fails, show the form again with error messages.
     *   4. If validation passes, save to database and redirect.
     *
     * @param leaveRequest        - Spring binds the form fields to this object
     * @param redirectAttributes  - Used to pass success/error messages after redirect
     * @param model               - Used to pass error messages back to the form page
     * @return redirect to leave-list on success, or return to form on error
     */
    @PostMapping("/apply-leave")
    public String submitLeaveRequest(
            @ModelAttribute LeaveRequest leaveRequest,
            RedirectAttributes redirectAttributes,
            Model model) {

        // ----- Validation -----
        String errorMessage = validateLeaveRequest(leaveRequest);

        if (errorMessage != null) {
            // Validation failed: send error message back to the form page
            model.addAttribute("errorMessage", errorMessage);
            model.addAttribute("leaveRequest", leaveRequest); // keep form data
            return "apply-leave"; // re-render the form with errors
        }

        // ----- Save to Database -----
        leaveService.saveLeaveRequest(leaveRequest);

        // ----- Redirect with Success Message -----
        // RedirectAttributes.addFlashAttribute stores data temporarily for the next page
        redirectAttributes.addFlashAttribute("successMessage",
                "Leave request submitted successfully!");

        return "redirect:/leave-list"; // redirect to the leave list page
    }

    // =========================================================
    // 4. VIEW LEAVE REQUESTS
    // =========================================================

    /**
     * Show all leave requests in a table.
     *
     * @param model - passes the list of leave requests to the template
     * @return "leave-list" - renders templates/leave-list.html
     */
    @GetMapping("/leave-list")
    public String showLeaveList(Model model) {
        List<LeaveRequest> leaveRequests = leaveService.getAllLeaveRequests();
        model.addAttribute("leaveRequests", leaveRequests);
        return "leave-list"; // renders leave-list.html
    }

    // =========================================================
    // 5. APPROVE LEAVE REQUEST
    // =========================================================

    /**
     * Approve a pending leave request.
     *
     * The form on leave-list.html POSTs to /approve/{id}.
     * After approving, redirect back to the leave list.
     *
     * @param id                  - the ID of the leave request to approve
     * @param redirectAttributes  - for passing flash messages
     * @return redirect to /leave-list
     */
    @PostMapping("/approve/{id}")
    public String approveLeaveRequest(@PathVariable String id,
                                      RedirectAttributes redirectAttributes) {
        leaveService.approveLeaveRequest(id);
        redirectAttributes.addFlashAttribute("successMessage",
                "Leave request approved successfully!");
        return "redirect:/leave-list";
    }

    // =========================================================
    // 6. REJECT LEAVE REQUEST
    // =========================================================

    /**
     * Reject a pending leave request.
     *
     * @param id                  - the ID of the leave request to reject
     * @param redirectAttributes  - for passing flash messages
     * @return redirect to /leave-list
     */
    @PostMapping("/reject/{id}")
    public String rejectLeaveRequest(@PathVariable String id,
                                     RedirectAttributes redirectAttributes) {
        leaveService.rejectLeaveRequest(id);
        redirectAttributes.addFlashAttribute("successMessage",
                "Leave request rejected.");
        return "redirect:/leave-list";
    }

    // =========================================================
    // 7. DELETE LEAVE REQUEST
    // =========================================================

    /**
     * Delete a leave request permanently.
     *
     * @param id                  - the ID of the leave request to delete
     * @param redirectAttributes  - for passing flash messages
     * @return redirect to /leave-list
     */
    @PostMapping("/delete/{id}")
    public String deleteLeaveRequest(@PathVariable String id,
                                     RedirectAttributes redirectAttributes) {
        leaveService.deleteLeaveRequest(id);
        redirectAttributes.addFlashAttribute("successMessage",
                "Leave request deleted successfully.");
        return "redirect:/leave-list";
    }

    // =========================================================
    // PRIVATE HELPER: Validate Leave Request
    // =========================================================

    /**
     * Validates all fields of the leave request.
     *
     * @param lr - the LeaveRequest object from the form
     * @return an error message String if invalid, or null if valid
     */
    private String validateLeaveRequest(LeaveRequest lr) {

        // Check: Employee Name must not be blank
        if (lr.getEmployeeName() == null || lr.getEmployeeName().trim().isEmpty()) {
            return "Employee Name is required.";
        }

        // Check: Employee ID must not be blank
        if (lr.getEmployeeId() == null || lr.getEmployeeId().trim().isEmpty()) {
            return "Employee ID is required.";
        }

        // Check: Leave Type must not be blank
        if (lr.getLeaveType() == null || lr.getLeaveType().trim().isEmpty()) {
            return "Leave Type is required.";
        }

        // Check: Start Date must be provided
        if (lr.getStartDate() == null) {
            return "Start Date is required.";
        }

        // Check: End Date must be provided
        if (lr.getEndDate() == null) {
            return "End Date is required.";
        }

        // Check: End Date must not be before Start Date
        if (lr.getEndDate().isBefore(lr.getStartDate())) {
            return "End Date cannot be before Start Date.";
        }

        // Check: Reason must not be blank
        if (lr.getReason() == null || lr.getReason().trim().isEmpty()) {
            return "Reason is required.";
        }

        // All validations passed
        return null;
    }
}
