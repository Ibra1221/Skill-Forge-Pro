/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Services;

import Backend.Database.CourseDatabase;

/**
 *
 * @author pola-nasser13
 */
public class InstructorService {
    
    CourseDatabase courses;
    
    InstructorService(){
                courses = new CourseDatabase("courses.json");
                courses.readFromFile();
    }
    
    void createCourse(){
        
    }
    
}
