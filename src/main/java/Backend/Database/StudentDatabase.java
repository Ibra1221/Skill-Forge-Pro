/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Database;
import Backend.Models.*;
import org.json.JSONObject;

/**
 *
 * @author pola-nasser13
 */
public class StudentDatabase extends Database<Student> {

    public StudentDatabase(String filename) {
        super(filename);
    }

    @Override
    public Student createRecordFrom(JSONObject j) {
        return new Student(j);
    }

    @Override
    public boolean insertRecord(JSONObject j) {
        try {
            int userId = j.getInt("userId");
            String email = j.getString("email");

            for (int i = 0; i < records.size(); i++) {
                Student s = records.get(i);

                if (s.getUserId() == userId ||
                    s.getEmail().equalsIgnoreCase(email)) {
                    return false;
                }
            }

            Student newStudent = createRecordFrom(j);
            records.add(newStudent);
            saveToFile();
            return true;

        } catch (Exception e) {
            System.out.println("Failed to insert student: " + e.getMessage());
            return false;
        }
    }

    public void deleteStudent(int studentId) {
        boolean deleted = false;

        for (int i = 0; i < records.size(); i++) {
            Student s = records.get(i);
            if (s.getId() == studentId) {
                records.remove(i);
                deleted = true;
                i--;
            }
        }

        if (deleted) {
            System.out.println("Student deleted: " + studentId);
            saveToFile();
        } else {
            System.out.println("No student found with ID: " + studentId);
        }
    }
        public Student getStudentById(int studentId) {
        for (int i = 0; i < records.size(); i++) {
            Student s = records.get(i);
            if (s.getId() == studentId) {
                return s;
            }
        }
        return null;
    }

    public Student getStudentByEmail(String email) {
        for (int i = 0; i < records.size(); i++) {
            Student s = records.get(i);
            if (s.getEmail().equalsIgnoreCase(email)) {
                return s;
            }
        }
        return null;
    }
        public boolean contains(int studentId) {
        return getStudentById(studentId) != null;
    }
}


