/*
Name: Mohammad Haroon
Date: 09/10/2022
Explanation: Jotto is a guessing game where the user attempts to guess a word that is randomly chosen from a file.
The program provides a hint by indicating how many characters in the word match the computer's chosen word.
To ensure a fair game, the program restricts the word to five characters in length and does not allow the user to guess words that are either shorter or longer.
In addition to the game itself, the program includes a menu with several options for the user to choose from.
These options might include settings for adjusting the difficulty level or selecting different categories of words to guess from.
Overall, Jotto provides an entertaining and challenging game for users who enjoy testing their vocabulary and guessing skills.





 */


import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;


class Jotto{

    private static final int WORD_SIZE = 5;
    private static String filename;
    private static final boolean DEBUG = true;
    private static ArrayList<String> playedWords;
    private static ArrayList<String> wordList;
    private static ArrayList<String> playerGuesses;
    private String currentWord;
    private int score;


    public Jotto(String filename) throws IOException{
        // initializing string with filename
        this.filename = filename;
        readWords();   // calling readWords method to read file
    }

    public int getLetterCount(String guessWord){
        int count = 0;  // set counter to zero which hold numbers of word s

        for(int i = 0 ; i < currentWord.length() ; i++){
            // checking if guess word from player and computer has any similar words
            if(currentWord.contains(String.valueOf(guessWord.charAt(i)))){
                count++; // counting similar words
            }
        }
        return count; // return the number of words
    }

    private int score(){
        return this.score;    // return the score
    }

    public void play(){
        Scanner scan = new Scanner(System.in);
        String word;
        System.out.println("Welcome to the game.");
        playerGuesses = new ArrayList<>(); // initializing the arraylist
        playedWords = new ArrayList<>();   // initializing the array list

        System.out.println("Current score: " + score);

        do{
            System.out.println("=-=-=-=-=-=-=-=-=-=-=");

            System.out.println("Choose one of the following:");
            System.out.println("1:     Start the game");
            System.out.println("2:     See the word list");
            System.out.println("3:     See the chosen words");
            System.out.println("4:     Show Player guesses");
            System.out.println("ZZ to exit");
            System.out.println("=-=-=-=-=-=-=-=-=-=-=");
            System.out.print("What is your choice: ");
            word = scan.nextLine();
            word = word.trim().toLowerCase();  // convert words to lower case and trim the spaces
            // checking user choice
            if(word.equals("1") || word.equals("one")){
                if(!pickWord()){ // if the pick up word return false
                    showPlayerGuesses(); // call showPlayerGuesses method show all the words
                }
                else{
                    guess(); // call guess method
                }
            }
            else if(word.equals("2") || word.equals("two") ){
                showWordList();  // if user choose 2 call showWordList method
            }
            else if(word.equals("3") || word.equals("three")){
                showPlayedWords();  // if user chosse 3 call showPlayedWords method
            }
            else if(word.equals("four") || word.equals("4")){
                showPlayerGuesses(); // if user choose 4 call showPlayerGuesses method
            }
            else if(word.equals("zz")){
                break;
            }
            else{   // when other then menu choice words print the below message
                System.out.println("I don't know ( "  + word + " ) is");
                System.out.println();
            }
            System.out.println("Press enter to continue");
            System.out.println();

        }while(!word.equals("zz")); // leave loop when equale zz


    }
    private boolean pickWord(){

        // read words from arraylist to string variable randomly
        currentWord = wordList.get(new Random().nextInt(wordList.size()));
        if (playedWords.contains(currentWord) && playedWords.size() == wordList.size()){ // when values match print the below message
            System.out.println("You've guessed them all!");
            return false;  // when value match return false
        }
        // when word doesnt match pickup new word or enter
        else if (playedWords.contains(currentWord) && playedWords.size() != wordList.size()){
            pickWord();  // calling pickWord method to enter new word
            playedWords.add(currentWord);
            if (DEBUG){ // return true print the currentword
                System.out.println(currentWord);
            }
            return true;
        }
        return true; // return true

    }

    private void showWordList(){

        // print the curent list of words
        System.out.println("Current word list: ");
        for (String word : wordList){ // using for each loop
            System.out.println(word);
        }

    }

    private void updateWordList(){


        System.out.println("Updating word list.");


        FileWriter writeToFile = null; // creating writing file
        try {
            // write to file path
            writeToFile = new FileWriter("/Users/zainabzoya/Documents/Java File/Jotto/src/oiled.txt");
            for(String str1: playerGuesses) {
                if(!wordList.containsAll(Collections.singleton(str1))) { // check duplicate value
                    wordList.add(str1); // add value to array;is after check
                }


            }
            for(String str2 : wordList){
                writeToFile.write(str2 + System.lineSeparator()); // adding value from arraylist to file
            }

            writeToFile.close(); // file reading close
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }




    }

    private void readWords() throws IOException {
        // intilizating Bufferreader to null value
        BufferedReader buRead = null;
        // checking exception
        try{
            buRead = new BufferedReader(new FileReader(filename));  // reading file
        }catch(FileNotFoundException e){  // when file not found print message
            System.out.println("File not found");

        }
        // reading file to string variable
        filename = buRead.readLine();
        wordList =  new ArrayList<>();   // initialization  of
        // loop will run until reach null value in file
        while(filename != null){
            if(!wordList.containsAll(Collections.singleton(filename))) {  // cehk duplicate value before adding to arraylist
                wordList.add(filename); // add to arraylist all teh value
                filename = buRead.readLine(); // reading again
            }
            else{ // if dupkicate value found print the message
                System.out.println("duplicate value found in the file ");
                filename = buRead.readLine(); // continue read the file
            }
        }



        buRead.close(); // file reading close done

    }

    private void showPlayedWords(){


        // check if there is no word in list print the below message
        if(playedWords.isEmpty()){
            System.out.println("No words have been played");
        }
        else{ // if there is value call showWordList method to  print the value
            showWordList();
        }

    }

    private void playerGuessScores(ArrayList<String> arr){
        // show the guess word with number of similar char
        for (String word: arr){
            System.out.println(word + "     " + getLetterCount(word));
        }

    }

    private void addPlayerGuess(String str){
        // if the word of guess is not in array list add it
        if(!playerGuesses.contains(str)){
            playerGuesses.add(str);
        }
    }

    private int guess(){
        // intelligizing the arraylist
        ArrayList<String> currentGuesses = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        int letterCount = 0;
        int score = WORD_SIZE +1; // using for score
        String wordGuess; // string variable using for user input

        do{ // start of do while loop
            System.out.println("Current score: " + score);
            System.out.print("What is your guess(q to quit");
            wordGuess = scan.nextLine().trim().toLowerCase();
            // user enter q
            if (wordGuess.equals('q')){
                if (this.score < 0){ // if score less than zero print that score
                    score = this.score;
                } else {  // or print zero
                    score = 0;
                }
            }
            // check word enter by user the lenght is 5 char
            if(wordGuess.length() > WORD_SIZE || wordGuess.length() < WORD_SIZE){
                System.out.println("Word must be 5 characters (" + wordGuess + " is " + wordGuess.length() + " )");
            } else {  // when enter correct length word
                if (wordGuess.equals(currentWord)){  // when both word aee same print the below message
                    System.out.println("DINGDINGDING!!! the word was " + wordGuess);
                    playedWords.add(wordGuess); // calling method
                    addPlayerGuess(wordGuess);// calling method
                    currentGuesses.add(wordGuess);// calling method
                    playerGuessScores(currentGuesses);// calling method
                    System.out.println("Your score is " + score);
                    return score; // return the score
                } else { // if user enter repeat word
                    if (playerGuesses.contains(wordGuess)){  // check for same word entry
                        System.out.println("The word have already been guessed");
                    } else { // if the owrd is not in list from the user
                        addPlayerGuess(wordGuess); // calling method
                        currentGuesses.add(wordGuess);// calling method
                        letterCount = getLetterCount(wordGuess);// calling method
                        if (letterCount != WORD_SIZE){ // check word lenght and letter count size
                            System.out.println("Guess         Score");

                        } else {
                            System.out.println("The word you choose is anagram");
                        }
                        score--; // in worng answer decrement the score
                        playerGuessScores(currentGuesses);

                    }
                }


            }


        }while(!wordGuess.equals("q")); // end of while loop and leave the loop when equal q
        return score; // return the curent score
    }

    private void showPlayerGuesses(){
        if (playerGuesses.isEmpty()){ // no guess yet print the message
            System.out.println("No guesses yet");
        } else {
            System.out.println("Current player guesses: "); // if there is guess words print it
            for (String word: playerGuesses){ // for each loop
                System.out.println(word);
            }
            System.out.println("Would you like to add the words to the word list? (y/n)"); // asking use for new update of guess list
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine().trim().toLowerCase();
            if (input.equals("y")){ // when equale y update the list
                updateWordList(); // calling method
                showWordList();// calling method
            }
        }
    }



}

class Drive{
    public static void main(String[] args) throws IOException {
        String filepath = "/Users/zainabzoya/Documents/Java File/Jotto/src/oiled.txt";
//        String filepath = "debug.txt";
//        String filepath = "/Users/zainabzoya/Documents/Java File/Jotto/src/wordList.txt";
        Jotto game = new Jotto(filepath);
        game.play();
    }
}