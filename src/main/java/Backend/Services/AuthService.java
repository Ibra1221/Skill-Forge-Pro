/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Services;
import Backend.Database.InstructorDatabase;
import Backend.Database.StudentDatabase;
import Backend.Models.Instructor;
import Backend.Models.Student;
import Backend.Models.User;
import org.json.JSONObject;
/**
 *
 * @author pola-nasser13
 */
public class AuthService {
    private StudentDatabase students;
    private InstructorDatabase instructors;
    public AuthService(){
        students = new StudentDatabase("users.json");
        instructors = new InstructorDatabase("users.json");
        students.readFromFile();
        instructors.readFromFile();
    }
    
public Student loginStudent(String email, String enteredPassword) {
    Student student = students.getStudentByEmail(email);

    if (student == null) {
        System.out.println("No Student found");
        return null;
    }

    // check correct role
    if (!"student".equalsIgnoreCase(student.getRole())) {
        System.out.println("User is not a student");
        return null;
    }

    if (!student.verifyPassword(enteredPassword)) {
        System.out.println("Wrong Password!");
        return null;
    }

    return student;
}

public Instructor loginInstructor(String email, String enteredPassword) {
    Instructor instructor = instructors.getInstructorByEmail(email);

    if (instructor == null) {
        System.out.println("No Instructor found");
        return null;
    }

    // check correct role
    if (!"instructor".equalsIgnoreCase(instructor.getRole())) {
        System.out.println("User is not an instructor");
        return null;
    }

    if (!instructor.verifyPassword(enteredPassword)) {
        System.out.println("Wrong Password!");
        return null;
    }

    return instructor;
}

    
   public boolean signup(int id, String role, String username,String email, String password){
         boolean insertStatus = false;
        if(role.equals("Student")){
            Student s;
             s = new Student(id,  username,email,  password);
             JSONObject JSONUser = s.toJSON();
             insertStatus = students.insertRecord(JSONUser);
            
        }
        else if(role.equals("Instructor")){
            Instructor ins;
            ins = new Instructor(id, username,email, password);
            JSONObject JSONUser = ins.toJSON();
            insertStatus = instructors.insertRecord(JSONUser);
        }
        return insertStatus;
    }
}
