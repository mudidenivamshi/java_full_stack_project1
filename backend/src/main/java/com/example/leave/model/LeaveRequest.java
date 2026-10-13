package com.example.leave.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;

/**
 * LeaveRequest is the MongoDB Document (collection mapping).
 *
 * @Document  tells Spring Data that this class maps to a MongoDB collection.
 *            collection = "leave_requests" is the MongoDB collection name.
 *
 * Unlike MySQL/JPA, MongoDB is schema-less — no need to create a table first.
 * Spring Data MongoDB creates the collection automatically.
 *
 * Key difference from MySQL:
 *   - MySQL  : uses Long id (numeric auto-increment)
 *   - MongoDB: uses String id (MongoDB ObjectId stored as a hex string)
 */
@Document(collection = "leave_requests")
public class LeaveRequest {

    // MongoDB generates this ID automatically as an ObjectId (e.g., "507f1f77bcf86cd799439011")
    @Id
    private String id;

    private String employeeName;
    private String employeeId;
    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;

    // Default status is PENDING when a new request is created
    private String status = "PENDING";

    // ---- Default constructor (required by Spring Data) ----
    public LeaveRequest() {
    }

    // ---- Getters and Setters ----

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
