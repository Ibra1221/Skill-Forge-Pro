package Backend.Services;

import Backend.Database.CourseDatabase;
import Backend.Models.Admin;
import Backend.Models.ApprovalStatus;
import Backend.Models.Course;
import java.time.Instant;

public class AdminService {



    public AdminServiceService() {

    }

    public void approveCourse(Course course) {
        course.setApprovalStatus(ApprovalStatus.APPROVED);
        course.addAudit("APPROVED",this.getUserId(),Instant.now().toString());
        CourseDatabase.save();
    }
    
    
    
    public void rejectCourse(Course course, String reason) {
        course.setApprovalStatus(ApprovalStatus.REJECTED);
        course.addAudit("REJECTED: " + reason,this.getUserId(),Instant.now().toString());
        CourseDatabase.save();
    }




    public List<Course> getPendingCourses() {
        List<Course> pending = new ArrayList<>();

        for (Course c : CourseDatabase.getAllCourses()) {
            if (c.getApprovalStatus() == ApprovalStatus.PENDING) {
                pending.add(c);
            }
        }

        return pending;
    }



    public List<Course> getApprovedCourses() {
        List<Course> approved = new ArrayList<>();

        for (Course c : CourseDatabase.getAllCourses()) {
            if (c.getApprovalStatus() == ApprovalStatus.APPROVED) {
                approved.add(c);
            }
        }

        return approved;
    }

    public List<Course> getRejectedCourses() {
        List<Course> rejected = new ArrayList<>();

        for (Course c : CourseDatabase.getAllCourses()) {
            if (c.getApprovalStatus() == ApprovalStatus.REJECTED) {
                rejected.add(c);
            }
        }

        return rejected;
    }

    
}