//********************************************************************
//
//  Developer:     Marshal Pfluger
//
//  Project #:     Nine
//
//  File Name:     Project9.java
//
//  Course:        COSC 4301 Modern Programming
//
//  Due Date:      04/21/2023
//
//  Instructor:    Prof. Fred Kumi 
//
//  Java Version:  17.0.4.1
//
//  Description:   Contains the main method for the program and runProg method.  
//
//********************************************************************

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.text.SimpleDateFormat;
import java.util.Date;



public class Project9 {
	private int currentDifficulty;
	private int levelCorrectLimit = 5;
	List<LevelInfo> levelInfo;
	private String timeStamp;
	
	
	public Project9() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		timeStamp = dateFormat.format(new Date());
		currentDifficulty = 0;
		listMaker();
	}
	
	 public static void main(String[] args) {
		 // Create object of project9 to call non static run program method. 
		 Project9 obj = new Project9();
		 obj.developerInfo();
		 obj.runProgram();
		 
	    }
	 
	 //***************************************************************
	 //
	 //  Method:       listMaker
	 // 
	 //  Description:  initializes the list of levelInfo objects
	 //
	 //  Parameters:   N/A
	 //
	 //  Returns:      N/A
	 //
	 //**************************************************************
	 public void listMaker() {
		 // Create new list of levelInfo objects
		 levelInfo = new ArrayList<LevelInfo>();
		    // Loop through and add new LevelInfo objects
			for(int i = 0; i <= 2; i++) {
				levelInfo.add(new LevelInfo());
			}
	 }
	 
	 //***************************************************************
	 //
	 //  Method:       runProgram
	 // 
	 //  Description:  Drives the whole program
	 //
	 //  Parameters:   N/A
	 //
	 //  Returns:      N/A
	 //
	 //**************************************************************
	 public void runProgram() {
		 // Indicate start of session on output log with time stamp
		 writeFile("***************START OF SESSION***************:" + timeStamp);
		 printOutput("Welcome to the Math Quiz!\n");
		 // Declare holder variable for user choice
		 String choice = "";
		 // While student doesn't want to exit loop through program
		 while (!choice.equals("e")) {
			 // If they are in basic level and get 5 correct, allow them the chance to go to intermediate level
			 if (currentDifficulty == 0 && levelInfo.get(currentDifficulty).getCorrect() >= levelCorrectLimit) {
				 // Prompt user of options sent to print method
				 printOutput("You have answered " + levelCorrectLimit +  " questions correctly at the Basic level. Would you like to try a more difficult level? (Y/N)(type (E) to exit)\n");
				 // Get user choice
				 choice = userChoiceString(true);
				 //Write decision to output file
				 writeFile(choice);
				 // If user selects intermediate level
				 if (choice.equals("y")) {
					 // Reset the levelCorrectLimit back to 5 for next level
					 levelCorrectLimit = 5;
					 // Change difficulty to intermediate level
					 currentDifficulty = 1;
					 // inform user of level change through print method
					 printOutput("You have selected the Intermediate level.\n");
					 // If user wants to keep using basic level 
					 } else if(choice.equals("n")) {
						 // Increase amount of times user can answer questions in this level
						 levelCorrectLimit++;
						 }
				 } else if (currentDifficulty == 1 && levelInfo.get(currentDifficulty).getCorrect() >= levelCorrectLimit) {
					 // Prompt user of options sent to print method
					 printOutput("You have answered " + levelCorrectLimit +  " questions correctly at the Intermediate level. Would you like to try a more difficult level? (Y/N)(type (E) to exit)\n");
					 // Get user choice
					 choice = userChoiceString(true);
					 //Write decision to output file
					 writeFile(choice);
					 // If user wants to go to advanced level
					 if (choice.equals("y")) {
						 // Reset the levelCorrectLimit back to 5 for next level
						 levelCorrectLimit = 5;
						 // Set difficulty to advanced level
						 currentDifficulty = 2;
						 // inform user of level change through print method
						 printOutput("You have selected the Advanced level.\n");
						 // If user wants to keep using intermediate level 
						 } else if(choice.equals("n")) {
							 // Increase amount of times user can answer questions in this level
							 levelCorrectLimit++;
							 }
					 } else if (currentDifficulty == 2 && levelInfo.get(currentDifficulty).getCorrect() >= levelCorrectLimit) {
						 // Prompt user of options sent to print method
						 printOutput("You have answered " + levelCorrectLimit +  " questions correctly at the Advanced level. Would you like to exit? (N)(type (E) to exit)\n");
						 // Get user choice
						 choice = userChoiceString(true);
						 //Write decision to output file
						 writeFile(choice);
						 // if user wants to keep using advanced level
						 if (choice.equals("n")) {
							 // Increase amount of times user can answer questions in this level
							 levelCorrectLimit++;
							 }
						 }
			 // if user answers E for exit do not generate question or try to check a question.
			 if(!choice.equals("e")) {
				 String question = generateQuestion();
				 questionCheckRunner(question);
				 }
			 }
		 calculateGrades();
		 // Indicate end of session on output log with time stamp
		 writeFile("***************END OF SESSION***************:" + timeStamp);
		 }
	 
	 //***************************************************************
	 //
	 //  Method:       questionCheckRunner
	 // 
	 //  Description:  Receives string question and runs necessary methods to check answer
	 //
	 //  Parameters:   String question
	 //
	 //  Returns:      N/A
	 //
	 //**************************************************************
	 public void questionCheckRunner(String question) {
		 // Declare status variables for state holding
		 boolean correctOrWrong = false;
		 // This variable keeps track of current wrong answer 
         boolean currentWrong = false;
         // loop through same question if student answers wrong
         while(!correctOrWrong) {
        	 // Send question display to print method
        	 printOutput("Question " + (totalNumQuestions()) + " (" + getDifficultyString() + "): " + question + " = ");
        	 // Parse the students numeric answer
        	 int answer = Integer.parseInt(userChoiceString(false));
        	 // Send the students answer to the output file
        	 writeFile(Integer.toString(answer));
        	 // Send the students answer and the string expression to answer checker
        	 correctOrWrong = checkAnswer(question, answer);
        	 // If student gets answer correct enter this block
        	 if (correctOrWrong) {
        		 levelInfo.get(currentDifficulty).incrementNumCorrect();
        		 // Reset current wrong when they successfully get it correct
        		 currentWrong = false;
        		 // send correct response to print method
        		 printOutput(getRandomResponse(true));
        		 printOutput("---------------------\n");
        		 } else {
        			 // Send incorrect response to print method
        			 printOutput(getRandomResponse(false));
        			 printOutput("---------------------\n");
        			 // set current wrong to still incorrect. 
        			 currentWrong = true;
        			 }
        	 }
         }
	 
	 //***************************************************************
	 //
	 //  Method:       calculateGrades
	 // 
	 //  Description:  Sends formatted strings to the printOutput method
	 //
	 //  Parameters:   N/A
	 //
	 //  Returns:      N/A
	 //
	 //**************************************************************
	 public void calculateGrades() {
		 // Declare variables to hold grade information
		 double basicGrade = 0;
		 double intermediateGrade = 0;
		 double advancedGrade = 0;
		 
		 printOutput("\n---------------------SESSION GRADES---------------------\n");
		 // If no questions were asked at beginner phase set grade to 0
		 if (levelInfo.get(0).getNumAsked() == 0) {
			 basicGrade = 0;
		 }else {
     		 // Calculate grade and set equal to holder variable
			 basicGrade = (double)levelInfo.get(0).getCorrect() / levelInfo.get(0).getNumAsked() * 100;
			 // Send formatted output to print method
			 printOutput(String.format("Basic level: %.2f%% correct\n", basicGrade));
		 }
		 // If no questions were asked at intermediate phase send correct output to print method
		 if(levelInfo.get(1).getNumAsked() == 0) {
			 printOutput("No questions were completed from the intermediate level\n");
		 }else {
			 // Calculate grade and set equal to holder variable
			 intermediateGrade = (double)levelInfo.get(1).getCorrect() / levelInfo.get(1).getNumAsked() * 100;
			 printOutput(String.format("Intermediate level: %.2f%% correct\n", intermediateGrade));
		 }
		// If no questions were asked at Advanced phase send correct output to print method
		 if(levelInfo.get(2).getNumAsked() == 0) {
			 printOutput("No questions were completed from the advanced level\n");
		 }else {
			 // Calculate grade and set equal to holder variable
			 advancedGrade = (double)levelInfo.get(2).getCorrect() / levelInfo.get(2).getNumAsked() * 100;
			 printOutput(String.format("Advanced level: %.2f%% correct\n", advancedGrade));
		 }
		 // If grade for basic level was bellow 80% send correct output to print method
		 if (basicGrade < 80)
			 printOutput("Please ask your teacher for extra help.\n");
	 }
	 
	 //***************************************************************
	 //
	 //  Method:       printOutput
	 // 
	 //  Description:  Prints the output from the user selected operation 
	 //
	 //  Parameters:   N/A
	 //
	 //  Returns:      N/A
	 //
	 //**************************************************************
	 public void printOutput(String outputString) {
		 // Write to output file
		 writeFile(outputString);
	 	// print nice looking output for user
	 	System.out.print(outputString);
	 	
	 }// End printOutput method
	 
	 
	 //***************************************************************
	 //
	 //  Method:       WriteFile
	 // 
	 //  Description:  Writes all output to the output file 
	 //
	 //  Parameters:   N/A
	 //
	 //  Returns:      N/A
	 //
	 //**************************************************************
	 public void writeFile(String outputString) {
		 // prevent program from running file creation every time it outputs information
		 if(totalNumQuestions() == 0) {
			 // Handle exceptions
			 try {
				 // create file object
				 File myObj = new File("Project9-Output.txt");
			     myObj.createNewFile();
			     } catch (IOException e) {
			    	 System.out.println("An error occurred.");
			    	 e.printStackTrace();
			    	 }
			 }
		 // Handle exceptions 
		 try {
			 // Open file with buffered writer 
			 BufferedWriter out = new BufferedWriter(new FileWriter("Project9-Output.txt", true));
			 // Writing to file
			 out.write(outputString + "\n");
			 // Close the file
			 out.close();
			 } catch (IOException e) {
				 // Inform user of error
				 System.out.println("File exception occurred" + e);
				 }
		 }
	 
	//***************************************************************
	//
	//  Method:       totalQuestions
	// 
	//  Description:  Adds all the total number of questions from each level. 
	//
	//  Parameters:   N/A
	//
	//  Returns:      int num of questions 
	//
	//************************************************************** 
	public int totalNumQuestions() {
		 return (levelInfo.get(0).getNumAsked() + levelInfo.get(1).getNumAsked() + levelInfo.get(2).getNumAsked());
	 }

	//***************************************************************
	//
	//  Method:       getDifficultyString
	// 
	//  Description:  returns the current difficult level.
	//
	//  Parameters:   N/A
	//
	//  Returns:      String expression
	//
	//************************************************************** 
	 public String getDifficultyString() {
		 String difficultyString;
		 // If Basic
		 if (currentDifficulty == 0) {
			 difficultyString = "Basic";
		// If Intermediate
		} else if (currentDifficulty == 1) {
			difficultyString = "Intermediate";
		// If Advanced
		} else {
			difficultyString = "Advanced";
			 }
		 return difficultyString;
		 }
	    
	//***************************************************************
	//
	//  Method:       generateQuestion
	// 
	//  Description:  Generates a string question based on the current difficulty
	//
	//  Parameters:   N/A
	//
	//  Returns:      String expression
	//
	//************************************************************** 
	public String generateQuestion() {
		levelInfo.get(currentDifficulty).incrementQuestionsAsked();
		SecureRandom rand = new SecureRandom();
		// Initialize variable
		String expression = "";
		// Add two to the current difficulty to get number of operands
		int numOperands = currentDifficulty + 2;
		// Add one to the current difficulty to get number of operators
		int numOperators = currentDifficulty + 1;
		// Create static array of operators
		char[] operators = {'*', '%', '+', '-'};
		// Initialize static arrays to hold the random operand(s)/operator(s)
		int[] operands = new int[numOperands];
		char[] operatorList = new char[numOperators];
		// Generate random operands
		for (int i = 0; i < numOperands; i++) {
			operands[i] = rand.nextInt(9) + 1;
			}
		// Generate random operators
		for (int i = 0; i < numOperators; i++) {
			operatorList[i] = operators[rand.nextInt(4)];
			}
		// Construct expression string
		for (int i = 0; i < numOperands - 1; i++) {
			expression += " " + operands[i] + " " + operatorList[i];
			}
		expression += " " + operands[numOperands - 1];
		return expression;
		}

	//***************************************************************
	//
	//  Method:       checkAnswer
	// 
	//  Description:  calls Evaluate expression to calculate correct answer
	//                and compares to student answer.
	//
	//  Parameters:   String question, int studentAnswer
	//
	//  Returns:      boolean rightOrWrong
	//
	//************************************************************** 
    public boolean checkAnswer(String question, int studentAnswer) {
    	boolean rightOrWrong;
    	// Get correct answer
        int correctAnswer = evaluateExpression(question);
        // Compare correct answer and student answer
        if (studentAnswer == correctAnswer) {
        	rightOrWrong = true;
        } else {
        	rightOrWrong = false;  
        }
        return rightOrWrong;
    }

    //***************************************************************
    //
    //  Method:       evaluateExpression
    // 
    //  Description:  Takes the string expression as a parameter and evaluates 
    //                the answer to the expression. uses helper functions hasPrecedence 
    //                and executeOperation
    //
    //  Parameters:   String expression
    //
    //  Returns:      int solution
    //
    //************************************************************** 
    public int evaluateExpression(String expression) {
    	// declare char array to hold pieces of the expression
    	char[] pieces = expression.toCharArray();
        // Create a stack to hold the operands
        Stack<Integer> operands = new Stack<Integer>();
        // Create a stack to hold the operators 
        Stack<Character> operators = new Stack<Character>();
        for (int i = 0; i < pieces.length; i++) {
            // If the current piece is a space, skip it. 
            if (pieces[i] == ' ')
            	continue;
            // If current piece is a number add it to operands stack 
            if (pieces[i] >= '0' && pieces[i] <= '9') {
            	// Push to operands stack
            	operands.push(Integer.parseInt(Character.toString(pieces[i])));
             // If current piece is an operator add it to operator stack
            }else if (pieces[i] == '+' || pieces[i] == '-' || pieces[i] == '*' || pieces[i] == '%') {
            	    // While the list of operators is not empty, check which operator has higher precedence
                    while (!operators.empty() && hasPrecedence(pieces[i], operators.peek())) {
                    	// execute the operation that has precedence and remove operator and operands from stack
                    	operands.push(executeOperation(operators.pop(), operands.pop(), operands.pop()));
                    }
                    // if current has higher precedence add to stack
                    operators.push(pieces[i]);
                }
            }
            // continue to process for more operators without precedence. 
            while (!operators.empty()) {
            	// execute the operation and remove operator and operands from stack
            	operands.push(executeOperation(operators.pop(), operands.pop(),operands.pop()));
            }
            // The top of the stack will now hold the answer, return to call location
            return operands.pop();
        }
     
    //***************************************************************
    //
    //  Method:       hasPrecedence
    // 
    //  Description:  Returns a boolean value depending on if operator1 has
    //                higher precedence than operator2
    //
    //  Parameters:   char operator1, char operator2
    //
    //  Returns:      boolean precedence
    //
    //**************************************************************  
    public boolean hasPrecedence(char operator1, char operator2){
    	boolean precedence;
    	// If operator1 has higher precedence than operator2 set false otherwise true
    	if ((operator1 == '*' || operator1 == '%') && (operator2 == '+' || operator2 == '-')) {
    		precedence = false;
    		} else {
    		precedence =  true;
    		}
    	return precedence;
    	}
     
    //***************************************************************
    //
    //  Method:       executeOperation
    // 
    //  Description:  Gets the solution to the operation of the expression
    //                that is passed in pieces. 
    //
    //  Parameters:   char operator, int operand2, int operand1
    //
    //  Returns:      int solution
    //
    //**************************************************************  
    public int executeOperation(char operator, int operand2, int operand1) {
    	int solution = 0; 
    	// Switch statement to execute correct operation
    	switch (operator) {
    	case '+':
    		solution = operand1 + operand2;
    		break;
        case '-':
        	solution = operand1 - operand2;
        	break;
        case '*':
        	solution = operand1 * operand2;
        	break;
        case '%':
        	// Handle division by zero exception
        	try {
        		solution = operand1 % operand2;	
        	} catch(ArithmeticException e) {
        		printOutput("Cannot divide by zero");
        		solution = 0;
        	}
        	break;
        	}
    	return solution;
    	}
        
    //***************************************************************
    //
    //  Method:       getRandomResponse
    // 
    //  Description:  returns a random response from the list of random responses
    //                depending on correct or incorrect answer
    //
    //  Parameters:   boolean correct
    //
    //  Returns:      String response
    //
    //**************************************************************   
    public String getRandomResponse(boolean correct) {
    	// Create new secure random instance
    	SecureRandom rand2 = new SecureRandom();
    	// Create arrays with responses
        String[] correctResponses = {"Excellent!", "Very good!", "Nice work!", "Way to go!", "Keep up the good work!"};
        String[] incorrectResponses = {"That is incorrect!", "No. Please try again!", "Wrong, Try once more!", "No. Don’t give up!", "Incorrect. Keep trying!"};
        // Declare variable to hold response
        String response = "";
        // If user enters correct response return correct response
        if (correct) {
            response = (correctResponses[rand2.nextInt(correctResponses.length)] + "\n");
        //if user enters incorrect answer 
        } else {
            response = incorrectResponses[rand2.nextInt(incorrectResponses.length)] + "\n";
        }
        return response;
    }
    
    //***************************************************************
    //
    //  Method:       userChoiceString
    // 
    //  Description:  takes an string from user. 
    //
    //  Parameters:   boolean questionType
    //
    //  Returns:      String userChoiceString
    //
    //**************************************************************
    public String userChoiceString(boolean questionType) {
    	boolean validInput = false; 
    	String userChoiceString = "";
    	// Use Scanner to receive user input
    	Scanner userInput = new Scanner(System.in);
    	while(!validInput) {
    		// Get user input 
        	userChoiceString = userInput.nextLine().trim().toLowerCase();
        	// Send user input to input validation 
        	validInput = inputValidation(userChoiceString, questionType);
    	}
    	// Close scanner when program exits. 
    	if (userChoiceString == "e") {
    		userInput.close();
    	}
    	return userChoiceString;
    }// End userChoice
	
    //***************************************************************
    //
    //  Method:       inputValidation
    // 
    //  Description:  validates the input of the program depending on what is being asked.  
    //
    //  Parameters:   String userChoiceString, boolean questionType
    //
    //  Returns:      boolean validity
    //
    //**************************************************************
    public boolean inputValidation(String userChoiceString, boolean questionType) {
    	// declare variable to hold bool validity
    	boolean validInvalid = false;
    	// There are two different types of questions to run validity for, split here
    	if(questionType) {
    		// If is a menu choice
    		if(userChoiceString.equals("y") || userChoiceString.equals("n") || userChoiceString.equals("e")) {
    			validInvalid = true;
    		}
        // If it is a student answer do not allow non-numeric answers. 
    	}else {
    		validInvalid = true;
    		// If input is non numeric this will handle exception and indicate invalid input. 
    		try {
    			Integer.parseInt(userChoiceString);
    		}catch (NumberFormatException e) {
    			validInvalid = false;
    		}
    	}
    	// If the input is invalid, inform user. 
    	if(!validInvalid)
    		printOutput("Input Error, please try again.");
    	return validInvalid;
    }
    
    //***************************************************************
    //
    //  Method:       developerInfo (Non Static)
    // 
    //  Description:  The developer information method of the program
    //
    //  Parameters:   None
    //
    //  Returns:      N/A 
    //
    //**************************************************************
        public void developerInfo(){
        	System.out.println("Name:    Marshal Pfluger");
    	    System.out.println("Course:  COSC 4301 Modern Programming");
    	    System.out.println("Project: Nine\n\n");
    	    } // End of the developerInfo method
        }