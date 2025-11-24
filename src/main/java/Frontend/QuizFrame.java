/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Frontend;

import Backend.Models.Course;
import Backend.Models.Lesson;
import Backend.Models.Question;
import Backend.Models.Quiz;
import Backend.Models.Student;
import Backend.Services.QuizService;
import Backend.Services.StudentService;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author pola-nasser13
 */
public class QuizFrame extends javax.swing.JPanel {
    private QuizService quizService;
    private StudentService studentService;
    private Student currentStudent;
    private Quiz currentQuiz;
    private ArrayList<Question> questions;
    private ArrayList<Integer> userAnswers;
    private int currentQuestionIndex;
    private int courseId;
    private int lessonId;
    
    /**
     * Creates new form QuizFrame
     */
    public QuizFrame(Student student, int courseId, int lessonId) {
        initComponents();
        this.currentStudent = student;
        this.courseId = courseId;
        this.lessonId = lessonId;
        this.studentService = new StudentService(student);
        
        initializeQuiz();
        
        if (questions != null && !questions.isEmpty()) {
            loadQuestion(currentQuestionIndex);
        }
    }

    private void initializeQuiz() {
        try {
            Course course = studentService.getCourse(courseId); 
            if (course == null) {
                JOptionPane.showMessageDialog(this, "Course not found!");
                return;
            }

            Lesson lesson = course.getLessonById(lessonId);
            if (lesson == null) {
                JOptionPane.showMessageDialog(this, "Lesson not found!");
                return;
            }

            if (!lesson.hasQuiz()) {
                JOptionPane.showMessageDialog(this, "This lesson doesn't have a quiz!");
                return;
            }

            currentQuiz = lesson.getQuiz();
            
            if (currentQuiz == null) {
                JOptionPane.showMessageDialog(this, "Quiz data is missing for this lesson!");
                return;
            }

            questions = currentQuiz.getQuestions();
            
            if (questions == null || questions.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No questions available for this quiz!");
                return;
            }

            quizService = new QuizService(currentQuiz, currentStudent, courseId, lessonId);
            userAnswers = new ArrayList<>();
            for (int i = 0; i < questions.size(); i++) {
                userAnswers.add(-1);
            }

            currentQuestionIndex = 0;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error initializing quiz: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadQuestion(int questionIndex) {
        if (questionIndex < 0 || questionIndex >= questions.size()) {
            return;
        }

        Question question = questions.get(questionIndex);
        
        tfquestionNum.setText(String.valueOf(questionIndex + 1));
        tfnumOfQuestions.setText(String.valueOf(questions.size()));
        tfcourseId.setText(String.valueOf(courseId));
        tfLessonId.setText(String.valueOf(lessonId));
        
        tfQuestion.setText(question.getText());
        
        ArrayList<String> choices = question.getChoices();
        tfChoiceA.setText(choices.size() > 0 ? choices.get(0) : "");
        tfChoiceB.setText(choices.size() > 1 ? choices.get(1) : "");
        tfChoiceC.setText(choices.size() > 2 ? choices.get(2) : "");
        tfChoiceD.setText(choices.size() > 3 ? choices.get(3) : "");

        clearCheckboxes();
        int previousAnswer = userAnswers.get(questionIndex);
        if (previousAnswer != -1) {
            setCheckbox(previousAnswer);
        }

        updateNavigationButtons();
        
        updateUI();
    }

    private void clearCheckboxes() {
        checkboxA.setState(false);
        checkboxB.setState(false);
        checkboxC.setState(false);
        checkboxD.setState(false);
    }

    private void setCheckbox(int choice) {
        switch (choice) {
            case 0: checkboxA.setState(true); break;
            case 1: checkboxB.setState(true); break;
            case 2: checkboxC.setState(true); break;
            case 3: checkboxD.setState(true); break;
        }
    }

    private void saveCurrentAnswer() {
        int selectedChoice = getSelectedChoice();
        userAnswers.set(currentQuestionIndex, selectedChoice);
    }

    private int getSelectedChoice() {
        if (checkboxA.getState()) return 0;
        if (checkboxB.getState()) return 1;
        if (checkboxC.getState()) return 2;
        if (checkboxD.getState()) return 3;
        return -1;
    }

    private void updateNavigationButtons() {
        btnPreviousQuestion.setEnabled(currentQuestionIndex > 0);
        btnNextQuestion1.setEnabled(currentQuestionIndex < questions.size() - 1);
    }

    private void showQuizResults(double score, boolean passed, int attempts) {
        SumbitQuiz resultsPanel = new SumbitQuiz(score, passed, attempts, questions, userAnswers);
        
        javax.swing.JFrame frame = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.getContentPane().removeAll();
            frame.getContentPane().add(resultsPanel);
            frame.revalidate();
            frame.repaint();
        }
    }

    private boolean areAllQuestionsAnswered() {
        for (int answer : userAnswers) {
            if (answer == -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel16 = new javax.swing.JLabel();
        tfnumOfQuestions = new javax.swing.JTextField();
        tfquestionNum = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        tfcourseId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tfLessonId = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnSubmit = new javax.swing.JButton();
        btnPreviousQuestion = new javax.swing.JButton();
        btnNextQuestion1 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        tfGotonumQuestion = new javax.swing.JTextField();
        btnGotoQuestionNum = new javax.swing.JButton();
        checkboxD = new java.awt.Checkbox();
        tfChoiceD = new javax.swing.JTextField();
        checkboxA = new java.awt.Checkbox();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        checkboxC = new java.awt.Checkbox();
        checkboxB = new java.awt.Checkbox();
        tfChoiceA = new javax.swing.JTextField();
        tfChoiceB = new javax.swing.JTextField();
        tfChoiceC = new javax.swing.JTextField();
        tfQuestion = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();

        jLabel16.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        jLabel16.setText("Number of Questions:");

        tfnumOfQuestions.addActionListener(this::tfnumOfQuestionsActionPerformed);

        tfquestionNum.addActionListener(this::tfquestionNumActionPerformed);

        jLabel1.setFont(new java.awt.Font("Liberation Sans", 0, 48)); // NOI18N
        jLabel1.setText("QUIZ");

        tfcourseId.addActionListener(this::tfcourseIdActionPerformed);

        jLabel3.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        jLabel3.setText("Course");

        tfLessonId.addActionListener(this::tfLessonIdActionPerformed);

        jLabel6.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        jLabel6.setText("Lesson");

        jLabel8.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        jLabel8.setText("Question num:");

        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(this::btnSubmitActionPerformed);

        btnPreviousQuestion.setText("Previous Question");
        btnPreviousQuestion.addActionListener(this::btnPreviousQuestionActionPerformed);

        btnNextQuestion1.setText("Next Question");
        btnNextQuestion1.addActionListener(this::btnNextQuestion1ActionPerformed);

        jLabel15.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        jLabel15.setText(" Go to Question num:");

        tfGotonumQuestion.addActionListener(this::tfGotonumQuestionActionPerformed);

        btnGotoQuestionNum.setText("Go");
        btnGotoQuestionNum.addActionListener(this::btnGotoQuestionNumActionPerformed);

        tfChoiceD.addActionListener(this::tfChoiceDActionPerformed);

        jLabel12.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        jLabel12.setText("B)");

        jLabel14.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        jLabel14.setText("C)");

        jLabel13.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        jLabel13.setText("D)");

        jLabel11.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        jLabel11.setText("A)");

        tfChoiceA.addActionListener(this::tfChoiceAActionPerformed);

        tfChoiceB.addActionListener(this::tfChoiceBActionPerformed);

        tfChoiceC.addActionListener(this::tfChoiceCActionPerformed);

        tfQuestion.addActionListener(this::tfQuestionActionPerformed);

        jLabel9.setFont(new java.awt.Font("Liberation Sans", 0, 18)); // NOI18N
        jLabel9.setText("Question:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(btnPreviousQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNextQuestion1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(362, 362, 362))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(tfChoiceB, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(checkboxC, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel14)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(tfChoiceC, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(94, 94, 94)
                                    .addComponent(checkboxD, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel13)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(tfChoiceD, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(133, 133, 133))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel15)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(tfGotonumQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnGotoQuestionNum)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 348, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(88, 88, 88)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(checkboxA, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 763, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tfChoiceA, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(98, 98, 98)
                                .addComponent(checkboxB, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(jLabel12)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(378, 378, 378)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfquestionNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfcourseId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(tfLessonId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(71, 71, 71)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tfnumOfQuestions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(156, 156, 156))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(tfcourseId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(tfLessonId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfnumOfQuestions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(tfquestionNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(tfChoiceA, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(15, 15, 15)
                                                .addComponent(checkboxB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel12)
                                                .addComponent(tfChoiceB, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(tfChoiceC, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel14)
                                                .addComponent(tfChoiceD, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel13)))
                                        .addGap(31, 31, 31))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel11)
                                            .addComponent(checkboxA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(42, 42, 42))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tfQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(66, 66, 66)
                                        .addComponent(checkboxD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(65, 65, 65)
                                        .addComponent(checkboxC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPreviousQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNextQuestion1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(tfGotonumQuestion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGotoQuestionNum))
                        .addGap(18, 18, 18)
                        .addComponent(btnSubmit, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tfnumOfQuestionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfnumOfQuestionsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfnumOfQuestionsActionPerformed

    private void tfquestionNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfquestionNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfquestionNumActionPerformed

    private void tfLessonIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfLessonIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfLessonIdActionPerformed

    private void tfcourseIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfcourseIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfcourseIdActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        saveCurrentAnswer();

        if (!areAllQuestionsAnswered()) {
            int result = JOptionPane.showConfirmDialog(this, 
                "Some questions are unanswered. Submit anyway?", 
                "Confirm Submission", 
                JOptionPane.YES_NO_OPTION);
            if (result != JOptionPane.YES_OPTION) {
                return;
            }
        }

        try {
            if (quizService == null) {
                JOptionPane.showMessageDialog(this, "Quiz service not initialized!");
                return;
            }
            
            double score = quizService.calculateScore(userAnswers);
            boolean passed = quizService.isPassed(score);
            int attempts = quizService.getQuizAttempts();
            showQuizResults(score, passed, attempts);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error submitting quiz: " + e.getMessage());              
        }
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void btnPreviousQuestionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousQuestionActionPerformed
        saveCurrentAnswer();
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            loadQuestion(currentQuestionIndex);
        }
    }//GEN-LAST:event_btnPreviousQuestionActionPerformed

    private void btnNextQuestion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextQuestion1ActionPerformed
        saveCurrentAnswer();
        if (currentQuestionIndex < questions.size() - 1) {
            currentQuestionIndex++;
            loadQuestion(currentQuestionIndex);}
    }//GEN-LAST:event_btnNextQuestion1ActionPerformed

    private void tfGotonumQuestionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfGotonumQuestionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfGotonumQuestionActionPerformed

    private void btnGotoQuestionNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGotoQuestionNumActionPerformed
        try {
            saveCurrentAnswer();
            int questionNum = Integer.parseInt(tfGotonumQuestion.getText()) - 1;
            if (questionNum >= 0 && questionNum < questions.size()) {
                currentQuestionIndex = questionNum;
                loadQuestion(currentQuestionIndex);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid question number! Must be between 1 and " + questions.size());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number!");
        }
    }//GEN-LAST:event_btnGotoQuestionNumActionPerformed

    private void tfChoiceDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfChoiceDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfChoiceDActionPerformed

    private void tfChoiceAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfChoiceAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfChoiceAActionPerformed

    private void tfChoiceBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfChoiceBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfChoiceBActionPerformed

    private void tfChoiceCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfChoiceCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfChoiceCActionPerformed

    private void tfQuestionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfQuestionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfQuestionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGotoQuestionNum;
    private javax.swing.JButton btnNextQuestion1;
    private javax.swing.JButton btnPreviousQuestion;
    private javax.swing.JButton btnSubmit;
    private java.awt.Checkbox checkboxA;
    private java.awt.Checkbox checkboxB;
    private java.awt.Checkbox checkboxC;
    private java.awt.Checkbox checkboxD;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField tfChoiceA;
    private javax.swing.JTextField tfChoiceB;
    private javax.swing.JTextField tfChoiceC;
    private javax.swing.JTextField tfChoiceD;
    private javax.swing.JTextField tfGotonumQuestion;
    private javax.swing.JTextField tfLessonId;
    private javax.swing.JTextField tfQuestion;
    private javax.swing.JTextField tfcourseId;
    private javax.swing.JTextField tfnumOfQuestions;
    private javax.swing.JTextField tfquestionNum;
    // End of variables declaration//GEN-END:variables
}
