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

public class InstructorDatabase extends Database<Instructor> {

    public InstructorDatabase(String filename) {
        super(filename);
    }

    @Override
    public Instructor createRecordFrom(JSONObject j) {
        String r = j.optString("role", "");
        if (!"instructor".equalsIgnoreCase(r)) return null;
        return new Instructor(j);
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
            Instructor newIns = createRecordFrom(j);
            if (newIns != null) records.add(newIns);
            return true;
        } catch (Exception e) {
            System.out.println("Failed to insert instructor: " + e.getMessage());
            return false;
        }
    }

    public Instructor getInstructorById(int instructorId) {
        readFromFile();
        for (int i = 0; i < records.size(); i++) {
            Instructor ins = records.get(i);
            if (ins.getUserId() == instructorId && "instructor".equalsIgnoreCase(ins.getRole())) {
                return ins;
            }
        }
        return null;
    }

    public Instructor getInstructorByEmail(String email) {
        readFromFile();
        for (int i = 0; i < records.size(); i++) {
            Instructor ins = records.get(i);
            if (ins.getEmail().equalsIgnoreCase(email) && "instructor".equalsIgnoreCase(ins.getRole())) {
                return ins;
            }
        }
        return null;
    }

    public boolean contains(int instructorId) {
        return getInstructorById(instructorId) != null;
    }

    public boolean addCourseIdToInstructor(int instructorId, int courseId) {
        readFromFile();
        Instructor ins = getInstructorById(instructorId);
        if (ins == null) return false;
        boolean added = ins.addCourseId(courseId);
        if (added) {
            JSONObject updated = ins.toJSON();
            updateUserInFile(instructorId, updated);
        }
        return added;
    }

    public boolean removeCourseIdFromInstructor(int instructorId, int courseId) {
        readFromFile();
        Instructor ins = getInstructorById(instructorId);
        if (ins == null) return false;
        boolean removed = ins.removeCourseId(courseId);
        if (removed) {
            JSONObject updated = ins.toJSON();
            updateUserInFile(instructorId, updated);
        }
        return removed;
    }

    public ArrayList<Integer> getInstructorCourseIds(int instructorId) {
        Instructor ins = getInstructorById(instructorId);
        if (ins == null) return new ArrayList<>();
        return ins.getCreatedCourseIds();
    }
}
