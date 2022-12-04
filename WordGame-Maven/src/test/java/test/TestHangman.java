package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Hangman;
import main.MockScoreDB;

class TestHangman {
	
	static Random random;
	static Hangman hangman;
	static MockScoreDB mockScoreDB;
	int requestedLength;
	
	@BeforeAll
	public static void setupClass() {
		random = new Random();
		hangman = new Hangman();
		hangman.loadWords();
		mock(MockScoreDB.class);
		
	}
	@BeforeEach
	public void setupTest() {
		requestedLength = random.nextInt(6) + 5;
		hangman.setScore(0);
	}

	@Test
	void test_alphabetCountInAWord() {
		String word = "pizza";
		char alphabet = 'a';		
		int count = hangman.countAlphabet(word, alphabet);
		assertEquals(1, count);
	}

	
	@Test
	void test_lengthOfFetchedWordRandom() {
		String word = hangman.fetchWord(requestedLength);		
		assertTrue(requestedLength == word.length());
	}
	
	@Test
	void test_uniquenessOfFetchedWord() {
		Set<String> usedWordsSet = new HashSet<>();
		int round = 0;
		String word = null;
		while (round < 100) {
			requestedLength = random.nextInt(6) + 5;
			word = hangman.fetchWord(requestedLength);
			round++;
			assertTrue(usedWordsSet.add(word));
		}
	}
	@Test
	void test_fetchClueBeforeAnyGuess() {
		String clue = hangman.fetchClue("pizza");
		assertEquals("-----", clue);
	}
	@Test
	void test_fetchClueAfterCorrectGuess() {
		String clue = hangman.fetchClue("pizza");
		String newClue = hangman.fetchClue("pizza", clue, 'a');
		assertEquals("----a", newClue);
	}
	
	@Test
	void test_fetchClueAfterIncorrectGuess() {
		String clue = hangman.fetchClue("pizza");
		String newClue = hangman.fetchClue("pizza", clue, 'w');
		assertEquals("-----", newClue);
	}
	@Test
	void test_whenInvalidGuessThenFetchClueThrowsException() {
		assertThrows(IllegalArgumentException.class, () -> {
			hangman.fetchClue("pizza", "-----", '?');
		});
	}
	
	@Test
	void test_whenInvalidGuessThenFetchClueThrowsExceptionWithMessage() {
		Throwable e = assertThrows(IllegalArgumentException.class, () -> {
			hangman.fetchClue("pizza", "-----", '?');
		});
		assertEquals("letters only", e.getMessage());
	}
	
	@Test
	void test_remainingTrialsBeforeAnyGuess() {
		hangman.fetchWord(requestedLength);
		assertEquals(Hangman.MAX_TRIALS, hangman.getRemainingTrials());
	}
	
	@Test
	void test_remainingTrialsAfterOneGuess() {
		hangman.fetchWord(requestedLength);
		hangman.fetchClue("pizza", "-----", 'w');
		assertEquals(Hangman.MAX_TRIALS - 1, hangman.getRemainingTrials());
	}
	@Test
	void test_scoreBeforeAnyGuess() {
		hangman.fetchWord(requestedLength);
		assertEquals(0, hangman.getScore());
	}
	
	@Test
	void test_scoreAfterCorrectGuess() {
		String word = "pizza";
		String clue = "-----";
		char guess = 'a';
		hangman.fetchClue(word, clue, guess);
		assertEquals((double)Hangman.MAX_TRIALS / word.length(), hangman.getScore());
	}
	
	@Test
	void test_scoreAfterIncorrectGuess() {
		String word = "pizza";
		String clue = "-----";
		char guess = 'w';
		hangman.fetchClue(word, clue, guess);
		assertEquals(0, hangman.getScore());
	}
	@Test
	void test_saveScoreUsingMockDB() {
		Hangman hangman = new Hangman(mockScoreDB);
		when(mockScoreDB.writeScoreDB("apple", 10)).thenReturn(true);
		assertTrue(hangman.saveWordScore("apple", 10));
	}
	@Test
	void test_readScoreUsingMockDB() {
		Hangman hangman = new Hangman(mockScoreDB);
		when(mockScoreDB.readScoreDB("apple")).thenReturn(10);
		assertEquals(hangman.readWordScore("apple"), 10);
	}
}
