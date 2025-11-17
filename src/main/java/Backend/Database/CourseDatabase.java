/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Database;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import Backend.Models.*;

/**
 *
 * @author pola-nasser13
 */

public class CourseDatabase extends Database<Course> {

    public CourseDatabase(String filename) {
        super(filename);
    }

    @Override
    public Course createRecordFrom(JSONObject j) {
        return new Course(j);
    }

    @Override
    public boolean insertRecord(JSONObject j) {
        try {
            int courseId = j.getInt("courseId");
            JSONArray arr = readAllUsersArray();

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                if (obj.optInt("courseId", -1) == courseId) return false;
            }

            if (!j.has("students")) j.put("students", new JSONArray());
            if (!j.has("lessons")) j.put("lessons", new JSONArray());

            arr.put(j);
            boolean ok = writeAllUsersArray(arr);
            if (!ok) return false;

            Course c = createRecordFrom(j);
            if (c != null) records.add(c);

            return true;

        } catch (Exception e) {
            System.out.println("Failed to insert course: " + e.getMessage());
            return false;
        }
    }

    public Course getCourseById(int courseId) {
        readFromFile();
        for (int i = 0; i < records.size(); i++) {
            Course c = records.get(i);
            if (c.getCourseId() == courseId) return c;
        }
        return null;
    }

    public boolean contains(int courseId) {
        return getCourseById(courseId) != null;
    }

    public boolean deleteCourse(int courseId) {
        JSONArray arr = readAllUsersArray();
        boolean removed = false;
        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);
            if (obj.optInt("courseId", -1) == courseId) {
                arr.remove(i);
                removed = true;
                break;
            }
        }
        if (removed) {
            boolean ok = writeAllUsersArray(arr);
            readFromFile();
            return ok;
        }
        return false;
    }

    public ArrayList<Course> getAllCourses() {
        readFromFile();
        return records;
    }
}

