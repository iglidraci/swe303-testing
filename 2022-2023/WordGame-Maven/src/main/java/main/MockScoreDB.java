package main;

public interface MockScoreDB {
	boolean writeScoreDB(String word, double score);
	int readScoreDB(String word);
}
