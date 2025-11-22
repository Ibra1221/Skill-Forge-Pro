/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Services;

import Backend.Models.Course;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author HP_Laptop
 */
public class AnalyticsService {
    private Course course;
    
    public AnalyticsService(Course course){
        this.course = course;
    }
    
    public ArrayList<HashMap<String, Double>> getStudentsProgress(){
    ArrayList<HashMap<String, Double>> totalProgress = new ArrayList<HashMap<String,Double>>();
    int totalEnrolledStudents = course.getStudentIds().size();
    }
    
    
} 
    
