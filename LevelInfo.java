//********************************************************************
//
//  Developer:     Marshal Pfluger
//
//  Project #:     Nine
//
//  File Name:     LevelInfo.java
//
//  Course:        COSC 4301 Modern Programming
//
//  Due Date:      04/21/2023
//
//  Instructor:    Prof. Fred Kumi 
//
//  Java Version:  17.0.4.1
//
//  Description:   Contains Information about student progress  
//
//********************************************************************

public class LevelInfo {
	private int numCorrect;
	private int questionsAsked;
	
	public LevelInfo() {
		setNumCorrect(0);
		questionsAsked = 0;	
	}
	
	public int getCorrect() {
		return numCorrect;
	}
	
	public int getWrong() {
		return (questionsAsked - numCorrect);
	}
	
	public int getNumAsked() {
		return questionsAsked;
	}
	
	public void incrementNumCorrect() {
		numCorrect++;
	}
	
	public void incrementQuestionsAsked() {
		questionsAsked++;
	}
	
	public void setNumCorrect(int num) {
		numCorrect = num;
	}
	
	public void setNumAsked (int num) {
		questionsAsked = num;
	}
}
