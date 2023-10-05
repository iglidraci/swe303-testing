package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Hangman {

	public static final int MAX_TRIALS = 10;
	private int remainingTrials;
	private int score;
	private Set<String> usedWordsSet = new HashSet<>();
	private List<String> wordsList = new ArrayList<>();
	private MockScoreDB mockScoreDB;
	public Hangman() {
		
	}
	public Hangman(MockScoreDB mockScoreDB) {
		this.mockScoreDB = mockScoreDB;
	}


	public int countAlphabet(String word, char alphabet) {
		int result = 0;
		
		for (char c : word.toCharArray()) {
			if (c == alphabet) result++;
		}
		return result;
	}


	public String fetchWord(int requestedLength) {
		this.remainingTrials = MAX_TRIALS;
		for (String result : wordsList) {
			if (result.length() != requestedLength) continue;
			else if (usedWordsSet.add(result)) return result;
		}
		return null;
		
	}
	
	public void loadWords() {
		System.out.println("Loading the words ...");
		String word = null;
		try (BufferedReader br = new BufferedReader(new FileReader("WordSource.txt"))) {
			while ((word = br.readLine()) != null) {
				wordsList.add(word);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	public String fetchClue(String word) {
		return "-".repeat(word.length());
	}


	public String fetchClue(String word, String clue, char guess) {
		this.remainingTrials--;
		if (guess >= 'A' && guess <= 'Z') guess += 32;
		if (guess < 'a' || guess > 'z') throw new IllegalArgumentException("letters only");
		StringBuilder newClue = new StringBuilder();
		for(int i=0; i < word.length(); i++) {
			if (guess == word.charAt(i) && guess != clue.charAt(i)) {
				newClue.append(guess);
				score += (double)MAX_TRIALS / word.length();
			} else {
				newClue.append(clue.charAt(i));
			}
		}
		return newClue.toString();
	}


	public int getRemainingTrials() {
		return remainingTrials;
	}


	public int getScore() {
		return this.score;
	}


	public void setScore(int score) {
		this.score = score;
		
	}
	public boolean saveWordScore(String word, int score) {
		return this.mockScoreDB.writeScoreDB(word, score);
	}
	public int readWordScore(String word) {
		return this.mockScoreDB.readScoreDB(word);
	}
	



}
