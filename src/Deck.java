import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Deck - This class deals with a collection of QuizCards
 * @author Calculus5000, Caleb Copeland
 * */
public class Deck {
    private File file;
    private final List<QuizCard> quizCardList = new ArrayList<>();
    private String fileName = "Untitled";
    private boolean isModified;
    private boolean isTestRunning;
    private int numCorrect;
    private int numWrong;

    private static final String QUIZ_CARD_TERMINATOR = "\n29rje2r9\n";
    private static final String QUIZ_CARD_SEPARATOR = "\te23bf0hj\t";

    /** addQuizCard - creates and adds a QuizCard to quizCardList */
    void addQuizCard(String q, String a){
        // Prevents any parsing exceptions occurring when opening a file
        if(q.length() == 0){
            q = " ";
        }
        if(a.length() == 0){
            a = " ";
        }
        quizCardList.add(new QuizCard(q, a));
    }

    /** parseData - parses the data from an input String using specified terminators and separators. */
    private void parseData(String unparsedData) {
        String[] stageOne = unparsedData.split(QUIZ_CARD_TERMINATOR);

        for (String stageTwo : stageOne) {
            String[] quizCardData = stageTwo.split(QUIZ_CARD_SEPARATOR);
            addQuizCard(quizCardData[0], quizCardData[1]);
        }
    }

    /** readFile - loads in the data from a saved deck into quizCardList */
    void readFile(String fileLocation){
        file = new File(fileLocation);
        setFileName(file.getName());
        assert file.canRead();
        try(BufferedReader input = new BufferedReader(new FileReader(file))){
            int letterNumber;
            StringBuilder dataToParse = new StringBuilder();
            while((letterNumber = input.read()) != -1){
                dataToParse.append((char) letterNumber);
            }
            parseData(dataToParse.toString());
        }catch(IOException ioEx){
            ioEx.printStackTrace();
        }
    }

    /** save - saves the Deck to specified file location */
    void save(String fileLocation){
        file = new File(fileLocation);
        assert file.canWrite();
        try (BufferedWriter output = new BufferedWriter(new FileWriter(file))) {
            for(QuizCard quizCard : quizCardList){
                output.write(quizCard.getQuestion() + QUIZ_CARD_SEPARATOR + quizCard.getAnswer() + QUIZ_CARD_TERMINATOR);
            }
        }catch(IOException ioEx){
            ioEx.printStackTrace();
        }
    }

    /** shuffle - Shuffles the deck in place. If saved, the quiz cards will be saved in the new shuffled order. */
    void shuffle(){
        Collections.shuffle(quizCardList);
    }


    // GETTERS
    String getFileLocation(){
        return file.getAbsolutePath();
    }

    String getFileName(){
        return fileName;
    }

    boolean getIsModified(){
        return isModified;
    }

    boolean getIsTestRunning(){
        return isTestRunning;
    }

    int getNumCorrect(){
        return numCorrect;
    }

    int getNumWrong(){
        return numWrong;
    }

    public List<QuizCard> getQuizCardList(){
        return quizCardList;
    }


    // SETTERS
    void setFileName(String fileName) {
        if(fileName.contains(".")){
            fileName = fileName.split("\\.")[0];
        }
        this.fileName = fileName;
    }

    public void setIsModified(boolean newValue){
        isModified = newValue;
    }

    public void setIsTestRunning(boolean newValue){
        isTestRunning = newValue;
    }

    public void incrementNumCorrect()
    {
        numCorrect += 1;
    }

    public void resetNumCorrect()
    {
        numCorrect = 0;
    }

    public void resetNumWrong()
    {
        numWrong = 0;
    }

    public void incrementNumWrong()
    {
        numWrong += 1;
    }

}
