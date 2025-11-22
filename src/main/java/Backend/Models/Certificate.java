/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Models;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Mohamed Walaa
 */
public class Certificate {
    private String certificateId;
    private int studentId;
    private int courseId;
    private String issueDate;

    public Certificate(String certificateId, int studentId, int courseId, String issueDate) {
        this.certificateId = certificateId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.issueDate = issueDate;
    }

    public Certificate(JSONObject j) {
        this.certificateId = j.getString("certificateId");
        this.studentId = j.getInt("studentId");
        this.courseId = j.getInt("courseId");
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

    @Override
    public JSONObject toJSON() {
        JSONObject j = new JSONObject();
        j.put("certificateId", certificateId);
        j.put("studentId", studentId);
        j.put("courseId", courseId);
        j.put("issueDate", issueDate);
        return j;
    }

}