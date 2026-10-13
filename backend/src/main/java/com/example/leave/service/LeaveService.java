package com.example.leave.service;

import com.example.leave.model.LeaveRequest;
import com.example.leave.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * LeaveService contains the business logic for leave management.
 *
 * Only change from MySQL version:
 *   - All method parameters using "Long id" are now "String id"
 *   - This is because MongoDB uses String ObjectId instead of numeric Long
 *
 * Everything else (logic, structure) stays exactly the same.
 */
@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    /**
     * Save a new leave request to MongoDB.
     */
    public void saveLeaveRequest(LeaveRequest leaveRequest) {
        leaveRequest.setStatus("PENDING");
        leaveRepository.save(leaveRequest);
    }

    /**
     * Retrieve all leave requests from MongoDB.
     */
    public List<LeaveRequest> getAllLeaveRequests() {
        return leaveRepository.findAll();
    }

    /**
     * Find a specific leave request by its MongoDB ID (String).
     */
    public Optional<LeaveRequest> getLeaveRequestById(String id) {
        return leaveRepository.findById(id);
    }

    /**
     * Approve a pending leave request.
     * Only changes status if current status is PENDING.
     */
    public void approveLeaveRequest(String id) {
        Optional<LeaveRequest> optionalRequest = leaveRepository.findById(id);

        if (optionalRequest.isPresent()) {
            LeaveRequest request = optionalRequest.get();
            if ("PENDING".equals(request.getStatus())) {
                request.setStatus("APPROVED");
                leaveRepository.save(request);
            }
        }
    }

    /**
     * Reject a pending leave request.
     * Only changes status if current status is PENDING.
     */
    public void rejectLeaveRequest(String id) {
        Optional<LeaveRequest> optionalRequest = leaveRepository.findById(id);

        if (optionalRequest.isPresent()) {
            LeaveRequest request = optionalRequest.get();
            if ("PENDING".equals(request.getStatus())) {
                request.setStatus("REJECTED");
                leaveRepository.save(request);
            }
        }
    }

    /**
     * Delete a leave request by MongoDB ID.
     */
    public void deleteLeaveRequest(String id) {
        leaveRepository.deleteById(id);
    }

    /**
     * Count total number of leave requests.
     */
    public long getTotalCount() {
        return leaveRepository.count();
    }

    /**
     * Count leave requests with PENDING status.
     */
    public long getPendingCount() {
        return leaveRepository.countByStatus("PENDING");
    }

    /**
     * Count leave requests with APPROVED status.
     */
    public long getApprovedCount() {
        return leaveRepository.countByStatus("APPROVED");
    }

    /**
     * Count leave requests with REJECTED status.
     */
    public long getRejectedCount() {
        return leaveRepository.countByStatus("REJECTED");
    }
}
