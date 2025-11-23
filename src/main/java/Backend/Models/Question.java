/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Models;

import Backend.Database.Info;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Pc
 */
public class Question implements Info {
    private int questionId;
    private String text;
    private ArrayList<String> choices;
    private int correctChoice;
    
    public Question(int questionId, String text, ArrayList<String> choices, int correctChoice) {
        setQuestionId(questionId);
        setText(text);
        setChoices(choices);
        setCorrectChoice(correctChoice);
    }
    
    public Question(JSONObject json) {
        this.questionId = json.getInt("questionId");
        this.text = json.getString("text");
        this.correctChoice = json.getInt("correctChoice");
        this.choices = new ArrayList<>();
        JSONArray choicesArr = json.getJSONArray("choices");
        if (choicesArr != null) {
            for (int i = 0; i < choicesArr.length(); i++) {
                choices.add(choicesArr.getString(i));
            }
        }
    }
    
    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("questionId", questionId);
        obj.put("text", text);
        obj.put("correctChoice", correctChoice);
        JSONArray choicesArr = new JSONArray();
        for (int i = 0; i < choices.size(); i++) {
            choicesArr.put(choices.get(i));
        }
        obj.put("choices", choicesArr);
        return obj;
    }
    
    public int getQuestionId() {
        return questionId;
    }

    public String getText() {
        return text;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public int getCorrectChoice() {
        return correctChoice;
    }
    
    public void setQuestionId(int questionId) {
        if (questionId < 0) {
            throw new IllegalArgumentException("questionId must be greater than 0");
        }
        this.questionId = questionId;
    }
    
    public void setCorrectChoice(int correctChoice) {
        if (correctChoice < 0 || correctChoice >= choices.size()) {
            throw new IllegalArgumentException("correctChoice is out of range"); 
        }
        this.correctChoice = correctChoice;
    }
    
    public void setText(String text) {
        if (text == null || text.trim().isEmpty()) {    
            throw new IllegalArgumentException("Question text cannot be empty.");   
        }
        this.text = text;
    }
    
    public void setChoices(ArrayList<String> choices) {
        if (choices == null || choices.size() > 4 || choices.size() < 2) {
            throw new IllegalArgumentException("the choices must be between 2 and 4 choices");
        }
        for (int i = 0; i < choices.size(); i++) {
            if (choices.get(i) == null || choices.get(i).trim().isEmpty()) {
                throw new IllegalArgumentException("Choice " + (i + 1) + " cannot be empty");
            }
        }
        this.choices = choices;
    }
}