/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Models;

import Backend.Database.Info;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Mohamed Walaa
 */
public class Certificate implements Info {

    private String certificateId;
    private int studentId;
    private int courseId;
    private String issueDate;
    private String studentName;
    private String courseTitle;
    private String instructorName;

    public Certificate(String certificateId, int studentId, int courseId,
            String studentName, String courseTitle, String instructorName) {
        this.certificateId = certificateId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.studentName = studentName;
        this.courseTitle = courseTitle;
        this.instructorName = instructorName;
        this.issueDate = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public Certificate(JSONObject j) {
        this.certificateId = j.getString("certificateId");
        this.studentId = j.getInt("studentId");
        this.courseId = j.getInt("courseId");
        this.studentName = j.getString("studentName");
        this.courseTitle = j.getString("courseTitle");
        this.instructorName = j.getString("instructorName");
        this.issueDate = j.getString("issueDate");
    }

    public String getCertificateId() {
        return certificateId;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getInstructorName() {
        return instructorName;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject j = new JSONObject();
        j.put("certificateId", certificateId);
        j.put("studentId", studentId);
        j.put("courseId", courseId);
        j.put("studentName", studentName);
        j.put("courseTitle", courseTitle);
        j.put("instructorName", instructorName);
        j.put("issueDate", issueDate);
        return j;
    }
}
