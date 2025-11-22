/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Services;

import Backend.Database.CourseDatabase;
import Backend.Database.UserDatabase;
import Backend.Models.Course;
import Backend.Models.Lesson;
import Backend.Models.Question;
import Backend.Models.Quiz;
import Backend.Models.Student;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author HP_Laptop
 */
public class QuizService {
    private Quiz quiz;
    private Student student;
    private int courseId;
    private int lessonId;
    private CourseService courseService;
    private Course c;
    private Lesson l;
    private CourseDatabase courses;
    private UserDatabase users;
    
    public QuizService(Quiz quiz, Student student, int courseId, int lessonId){
        this.quiz = quiz;
        this.student = student;
        this.courseId = courseId;
        this.lessonId = lessonId;
        c = getCourseById(courseId);
        courseService = new CourseService(c);
        l = courseService.getLessonById(lessonId);
         this.courses = new CourseDatabase("courses.json");
         this.users = new UserDatabase("users.json");
    }
    

    public Course getCourseById(int id) {
        return courses.getCourseById(id);
    }

    private boolean markQuestion(int questionNumber, int choice){
        if(choice == quiz.getQuestions().get(questionNumber).getCorrectChoice()){
            return true;
        }
        else {
            return false;
        }
    }
    
    public double getQuizScore(String answers){
        student.incrementQuizAttempts(lessonId, courseId);
        String[] answersArray = answers.split(",");
        int correctAnswersCount = 0;
        for(int i=0; i < answersArray.length; i++){
            int choice = -1;
            try{
                choice = Integer.parseInt(answersArray[i]);
            }
            catch(NumberFormatException e){
                System.out.println("Error! Choice not a number!");
            }
            boolean answerCorrect = markQuestion(i,choice);
            if(answerCorrect){
                correctAnswersCount++;
            }
        }
        double score = (correctAnswersCount / answersArray.length) * 100;
        student.setQuizScore(courseId, lessonId, score);
        users.updateUser(student);
        return score;
    }
    
    public boolean isQuizPassed(double score){
        return quiz.isPassed(score);
    }
    
    public int getQuizAttempts(){
        HashMap<String, Integer> totalAttempts = student.getQuizAttempts();
        String key = courseId + "_" + lessonId;
        int attempts = totalAttempts.get(key);
        return attempts;
    }
    
    public ArrayList<Question> getQuestions(){
        return quiz.getQuestions();
    }
}
