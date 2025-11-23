/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Services;

import Backend.Models.Course;
import Backend.Models.Lesson;
import Backend.Models.Student;
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
    ArrayList<Student> students = getEnrolledStudents();
    for(int i = 0; i < students.size(); i++){
        HashMap<String, Double> studentProgress = new HashMap<String, Double>();
        Student s = students.get(i);
        String name = s.getUsername();
        double progress = calculateCourseProgress(s);
        studentProgress.put(name, progress);
        totalProgress.add(studentProgress);
    }
    return totalProgress;
    }
    private double calculateCourseProgress(Student s){
        ArrayList<Integer> completedLessons = s.getCompletedLessonsByCourseId(course.getCourseId());
        ArrayList<Lesson> totalLessons = course.getLessons();
        double progress = ((double)completedLessons.size()/totalLessons.size())*100;
        return progress;
    }
    
    public double getAllLessonsAverage(){
        double sum = 0;
        ArrayList<Student> enrolledStudents = getEnrolledStudents();
        ArrayList<Lesson> totalLessons = course.getLessons();
        for(int i = 0; i < totalLessons.size(); i++){
            sum += getLessonAverage(totalLessons.get(i).getLessonId());
        }
        double average = sum / totalLessons.size();        
        return average;
    }
    
    public double getLessonAverage(int lessonId){
        double sum = 0.0;
        ArrayList<Student> enrolledStudents = getEnrolledStudents();
        for(int i = 0; i < enrolledStudents.size(); i++){
            if(enrolledStudents.get(i).getCompletedLessonsByCourseId(course.getCourseId()).contains(lessonId)){
                sum += enrolledStudents.get(i).getQuizScore(course.getCourseId(), lessonId);
            }
            else {
                sum += 0;
            }
        }
        double average = sum / enrolledStudents.size();        
        return average;
    }
    
    private ArrayList<Student> getEnrolledStudents(){
        ArrayList<Integer> studentsIds = course.getStudentIds();
        int numberOfEnrolledStudents = studentsIds.size();
        ArrayList<Student> students = new ArrayList<Student>();
        CourseService courseService = new CourseService(course);
        for(int i = 0; i < numberOfEnrolledStudents; i++){
        students.add(courseService.getStudentById(studentsIds.get(i)));
        }
        return students;
    }
    
    public double getLessonCompletionRate(int lessonId){
    ArrayList<Student> enrolledStudents = getEnrolledStudents();
    int completersCount = 0;
        for(int i = 0; i < enrolledStudents.size(); i++){
            if(enrolledStudents.get(i).getCompletedLessonsByCourseId(course.getCourseId()).contains(lessonId)){
                completersCount++;
            }
    }
        double percentageCompletion = ((double)completersCount/(enrolledStudents.size()))*100;
        return percentageCompletion;
    }
    
    public ArrayList<HashMap<String, Double>> getStudentQuizzesAverage(){
        ArrayList<Student> enrolledStudents = getEnrolledStudents();
        ArrayList<HashMap<String, Double>> studentsAverages = new ArrayList<HashMap<String, Double>>();
                
        for(int i = 0; i < enrolledStudents.size(); i++){
            double average = getStudentLessonsAverage(enrolledStudents.get(i)); 
            HashMap<String, Double> studentAverage = new HashMap<String, Double>();
            studentAverage.put(enrolledStudents.get(i).getUsername(), average);
            studentsAverages.add(studentAverage);
        }
        return studentsAverages;
    }
    
    
    private double getStudentLessonsAverage(Student s){
        double sum = 0.0;
            ArrayList<Integer> completedLessonsIndices = s.getCompletedLessonsByCourseId(course.getCourseId());
            for(int i = 0; i < completedLessonsIndices.size(); i++){
                sum += s.getQuizScore(course.getCourseId(), completedLessonsIndices.get(i));
            }
            double average = sum / completedLessonsIndices.size();
            return average;
        }
}

