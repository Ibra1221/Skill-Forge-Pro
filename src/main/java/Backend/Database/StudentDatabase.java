/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Database;

import Backend.Models.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.JSONArray;
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
        String r = j.optString("role", "");
        if (!"student".equalsIgnoreCase(r)) return null;
        return new Student(j);
    }

    private JSONArray readAllUsersArray() {
        try {
            File file = new File(filename);
            if (!file.exists()) return new JSONArray();
            StringBuilder content = new StringBuilder();
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                content.append(sc.nextLine());
            }
            sc.close();
            if (content.length() == 0) return new JSONArray();
            return new JSONArray(content.toString());
        } catch (Exception e) {
            return new JSONArray();
        }
    }

    private boolean writeAllUsersArray(JSONArray arr) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            pw.write(arr.toString(4));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void updateUserInFile(int userId, JSONObject updated) {
        JSONArray arr = readAllUsersArray();
        boolean changed = false;
        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            if (obj.optInt("userId", -1) == userId) {
                arr.put(i, updated);
                changed = true;
                break;
            }
        }
        if (changed) writeAllUsersArray(arr);
    }

    @Override
    public boolean insertRecord(JSONObject j) {
        try {
            int userId = j.getInt("userId");
            String email = j.getString("email");
            JSONArray arr = readAllUsersArray();
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                if (obj.optInt("userId", -1) == userId) return false;
                if (obj.optString("email", "").equalsIgnoreCase(email)) return false;
            }
            JSONArray newArr = arr;
            newArr.put(j);
            boolean ok = writeAllUsersArray(newArr);
            if (!ok) return false;
            Student newStudent = createRecordFrom(j);
            if (newStudent != null) {
                records.add(newStudent);
            }
            return true;
        } catch (Exception e) {
            System.out.println("Failed to insert student: " + e.getMessage());
            return false;
        }
    }

    public Student getStudentById(int studentId) {
        readFromFile();
        for (int i = 0; i < records.size(); i++) {
            Student s = records.get(i);
            if (s.getUserId() == studentId) {
                if ("student".equalsIgnoreCase(s.getRole())) {
                    return s;
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    public Student getStudentByEmail(String email) {
        readFromFile();
        for (int i = 0; i < records.size(); i++) {
            Student s = records.get(i);
            if (s.getEmail().equalsIgnoreCase(email)) {
                if ("student".equalsIgnoreCase(s.getRole())) {
                    return s;
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    public boolean contains(int studentId) {
        return getStudentById(studentId) != null;
    }

    public boolean enrollCourse(int studentId, int courseId, CourseDatabase courseDB) {
        readFromFile();
        Student s = getStudentById(studentId);
        if (s == null) {
            return false;
        }
        Course c = courseDB.getCourseById(courseId);
        if (c == null) {
            return false;
        }
        boolean ok1 = s.enrollCourseById(courseId);
        boolean ok2 = c.enrollStudentById(studentId);
        if (ok1 && ok2) {
            JSONObject updated = s.toJSON();
            updateUserInFile(studentId, updated);
        }
        return ok1 && ok2;
    }

    public boolean dropCourse(int studentId, int courseId, CourseDatabase courseDB) {
        readFromFile();
        Student s = getStudentById(studentId);
        if (s == null) {
            return false;
        }
        Course c = courseDB.getCourseById(courseId);
        if (c == null) {
            return false;
        }
        boolean ok1 = s.dropCourseById(courseId);
        boolean ok2 = c.removeStudentById(studentId);
        if (ok1 && ok2) {
            JSONObject updated = s.toJSON();
            updateUserInFile(studentId, updated);
        }
        return ok1 && ok2;
    }

    public boolean markLessonCompleted(int studentId, int courseId, int lessonId, CourseDatabase courseDB) {
        readFromFile();
        Student s = getStudentById(studentId);
        if (s == null) {
            return false;
        }
        Course c = courseDB.getCourseById(courseId);
        if (c == null) {
            return false;
        }
        boolean lessonExists = false;
        ArrayList<Lesson> lessons = c.getLessons();
        for (int i = 0; i < lessons.size(); i++) {
            if (lessons.get(i).getLessonId() == lessonId) {
                lessonExists = true;
                break;
            }
        }
        if (!lessonExists) {
            return false;
        }
        boolean ok = s.markLessonCompletedById(courseId, lessonId);
        if (ok) {
            JSONObject updated = s.toJSON();
            updateUserInFile(studentId, updated);
        }
        return ok;
    }
}