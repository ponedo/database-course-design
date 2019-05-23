package Database;

import Entity.WordEntry;
import Entity.WordlistRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBHandler {

    private Connection connection = DBUtil.getConn();
    private PreparedStatement statement;
    private ResultSet resultSet;
    private String sql;
    private int study_operation;

    private static final int LEARN = 0;
    private static final int REVIEW = 1;
    private static final int ORDINARY_VIEW = 2;
    public static final int IMPORTANCE_INCREASE = 1;
    public static final int IMPORTANCE_DECREASE = -1;

    /**Next 2 methods are for MainFrame*/
    public int getToLearnNum() {
        sql = "select count(*) from words " +
                "where learned=0";
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int num = resultSet.getInt("count(*)");
                return num;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getToReviewNum() {
        sql = "select count(*) from toreviewlist";
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int num = resultSet.getInt("count(*)");
                return num;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**Next 1 method is for AddWordDialog*/
    public void addWord(String word, String pos, String meaning) {

        try {
            sql = "Insert into words (word) " +
                    "values " +
                    "(\""+ word +"\") " +
                    "ON DUPLICATE KEY UPDATE learned=DEFAULT, remembered=DEFAULT";
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();

            sql = "Insert into wordlist " +
                    "values" +
                    "(\"" + word + "\", \"" + pos + "\", \"" + meaning + "\")";
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**Next 7 methods are for Study/ReviewFrame
     * The most forward two prepare the result set for query of learning or reviewing
     * Following two get the word to display
     * The last three modify the database according to the user's operation*/
    public void prepareToLearnSet() {
        study_operation=LEARN;
        sql = "select word from words " +
                "where learned=0 " +
                "order by " +
                "appenddate asc";
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Next 1 method is also used by WordlistViewer
    public void prepareToReviewSet() {
        study_operation=REVIEW;
        sql = "select word, importance from toreviewlist " +
                "order by " +
                "lastreviewdate asc";
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public WordEntry getNextWordEntry() {

        ResultSet wordInfo;
        WordEntry entry;
        String word;
        int importance;
        String pos;
        try {

            entry= new WordEntry();

            if (resultSet.next()) {
                //get word
                word = resultSet.getString("word");
                entry.setWord(word);
                //if reviewing, get importance
                if (study_operation==REVIEW) {
                    importance = resultSet.getInt("importance");
                    entry.setImportance(importance);
                }
                //setup word entry
                sql = "select * from wordlist " +
                        "where word=\"" + word + "\"";
                statement = connection.prepareStatement(sql);
                wordInfo = statement.executeQuery();
                while (wordInfo.next()) {
                    pos = wordInfo.getString("PartofSpeech");
                    switch (pos) {
                        case "v.":
                            entry.verbs.add(wordInfo.getString("meaning"));
                            break;
                        case "adj.":
                            entry.adjectives.add(wordInfo.getString("meaning"));
                            break;
                        case "adv.":
                            entry.adverbs.add(wordInfo.getString("meaning"));
                            break;
                        case "pron.":
                            entry.pronouns.add(wordInfo.getString("meaning"));
                            break;
                        case "num.":
                            entry.numerals.add(wordInfo.getString("meaning"));
                            break;
                        case "art.":
                            entry.articles.add(wordInfo.getString("meaning"));
                            break;
                        case "prep.":
                            entry.prepositions.add(wordInfo.getString("meaning"));
                            break;
                        case "conj.":
                            entry.conjunctions.add(wordInfo.getString("meaning"));
                            break;
                        case "itrj.":
                            entry.interjections.add(wordInfo.getString("meaning"));
                            break;
                        default:
                        case "n.":
                            entry.nouns.add(wordInfo.getString("meaning"));
                            break;
                    }
                }
            } else {
                entry.setWord("No next word!");
                entry.setImportance(0);
            }
            entry.packEntry();
            return entry;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public WordEntry getPreviousWordEntry() {

        ResultSet wordInfo;
        WordEntry entry;
        String word;
        String pos;
        try {

            entry= new WordEntry();

            if (resultSet.previous()) {
                //get word
                word = resultSet.getString("word");
                entry.setWord(word);
                //this method do not support the review mode
                //setup word entry
                sql = "select * from wordlist " +
                        "where word=\"" + word + "\"";
                statement = connection.prepareStatement(sql);
                wordInfo = statement.executeQuery();
                while (wordInfo.next()) {
                    pos = wordInfo.getString("PartofSpeech");
                    switch (pos) {
                        case "v.":
                            entry.verbs.add(wordInfo.getString("meaning"));
                            break;
                        case "adj.":
                            entry.adjectives.add(wordInfo.getString("meaning"));
                            break;
                        case "adv.":
                            entry.adverbs.add(wordInfo.getString("meaning"));
                            break;
                        case "pron.":
                            entry.pronouns.add(wordInfo.getString("meaning"));
                            break;
                        case "num.":
                            entry.numerals.add(wordInfo.getString("meaning"));
                            break;
                        case "art.":
                            entry.articles.add(wordInfo.getString("meaning"));
                            break;
                        case "prep.":
                            entry.prepositions.add(wordInfo.getString("meaning"));
                            break;
                        case "conj.":
                            entry.conjunctions.add(wordInfo.getString("meaning"));
                            break;
                        case "itrj.":
                            entry.interjections.add(wordInfo.getString("meaning"));
                            break;
                        default:
                        case "n.":
                            entry.nouns.add(wordInfo.getString("meaning"));
                            break;
                    }
                }
            } else {
                entry.setWord("No previous word!");
                entry.setImportance(0);
            }
            entry.packEntry();
            return entry;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setLearned(String word) {
        try {
            sql = "update words set learned=1 " +
                    "where word=\"" + word + "\"";
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateToReviewInfo(String word, int importance, int opreation) {
        int new_importance = importance + opreation;
        try {
            sql = "update toReviewList " +
                    "set importance=" + new_importance + ", " +
                    "lastreviewdate=CURDATE() " +
                    "where word=\"" + word + "\"";
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteToReviewWords() {

        try {
            resultSet.close();
            sql = "call delete_dangling_words()";
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**Next 2 methods are for WordlistViewer*/
    public void prepareRememberedSet() {
        study_operation=ORDINARY_VIEW;
        sql = "select word from words " +
                "where remembered=1 ";
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void prepareWholeSet() {
        study_operation=ORDINARY_VIEW;
        sql = "select word from words ";
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**Next 2 methods are for AlterWordDialog*/
    public ArrayList<WordlistRecord> getRecords(String word) {
        ArrayList<WordlistRecord> records = new ArrayList();
        try {
            sql = "select * from wordlist " +
                "where word=\"" + word + "\"";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                WordlistRecord record = new WordlistRecord();
                record.setWord(resultSet.getString("word"));
                record.setPos(resultSet.getString("PartofSpeech"));
                record.setMeaning(resultSet.getString("meaning"));
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    public void deleteRecord(String word, String pos, String meaning) {
        try {
            sql = "delete from wordlist " +
                    "where word=\"" + word + "\" && PartofSpeech=\"" + pos + "\" && meaning=\"" + meaning + "\"";
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**General closing method*/
    public void closeAll() {
        DBUtil.myClose(connection, statement, resultSet);
    }
}
