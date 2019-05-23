package Entity;

import java.util.ArrayList;

public class WordEntry {

    private String word;
    private int importance;

    public ArrayList<String> nouns = new ArrayList<String>();
    public ArrayList<String> verbs = new ArrayList<String>();
    public ArrayList<String> adjectives = new ArrayList<String>();
    public ArrayList<String> adverbs = new ArrayList<String>();
    public ArrayList<String> pronouns = new ArrayList<String>();
    public ArrayList<String> numerals = new ArrayList<String>();
    public ArrayList<String> articles = new ArrayList<String>();
    public ArrayList<String> prepositions = new ArrayList<String>();
    public ArrayList<String> conjunctions = new ArrayList<String>();
    public ArrayList<String> interjections = new ArrayList<String>();

    public ArrayList<String>[] pos;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public void packEntry() {
        pos = new ArrayList[]{nouns, verbs, adjectives, adverbs, pronouns, numerals, articles, prepositions, conjunctions, interjections};
    }
}
