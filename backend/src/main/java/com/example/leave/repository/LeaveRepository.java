package com.example.leave.repository;

import com.example.leave.model.LeaveRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * LeaveRepository handles all database operations for LeaveRequest in MongoDB.
 *
 * Changed from JpaRepository (MySQL) to MongoRepository (MongoDB).
 *
 * MongoRepository<LeaveRequest, String>
 *   - LeaveRequest : the document class
 *   - String       : the type of the @Id field (MongoDB uses String ObjectId)
 *
 * Spring Data MongoDB automatically provides:
 *   save(entity)       - Insert or update a document
 *   findAll()          - Get all documents
 *   findById(id)       - Find a document by its _id
 *   deleteById(id)     - Delete a document by its _id
 *   count()            - Count total documents
 *
 * The custom method countByStatus works the same way — Spring Data
 * generates the MongoDB query automatically from the method name.
 */
@Repository
public interface LeaveRepository extends MongoRepository<LeaveRequest, String> {

    /**
     * Counts leave requests that have the given status.
     *
     * Spring Data MongoDB automatically generates the query:
     *   db.leave_requests.countDocuments({ status: "PENDING" })
     *
     * @param status - "PENDING", "APPROVED", or "REJECTED"
     * @return count of matching documents
     */
    long countByStatus(String status);
}
