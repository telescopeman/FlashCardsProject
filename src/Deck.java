import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Deck - This class deals with a collection of QuizCards
 * @author Calculus5000, Caleb Copeland
 * @since August 21 2021
 * @version August 22 2021
 * */
public class Deck {
    private File file;
    private final List<QuizCard> quizCardList = new ArrayList<>();
    private String fileName = "Untitled";
    private boolean isModified;
    private boolean isTestRunning;
    private int numCorrect,numWrong;


    /** addQuizCard - creates and adds a QuizCard to quizCardList */
    public void addQuizCard(String q, String a){
        // Prevents any parsing exceptions occurring when opening a file
        if(q.length() == 0){
            q = " ";
        }
        if(a.length() == 0){
            a = " ";
        }
        quizCardList.add(new QuizCard(q, a));
    }





    /** shuffle - Shuffles the deck in place. If saved, the quiz cards will be saved in the new shuffled order. */
    public void shuffle(){
        Collections.shuffle(quizCardList);
    }

    public String toString()
    {
        try {
            return FileManager.fileToString(file);
        } catch (IOException e) {
            return "ERROR";
        }
    }

    // GETTERS
    public String getFileLocation(){
        return file.getAbsolutePath();
    }

    public String getFileName(){
        return fileName;
    }

    public boolean getIsModified(){
        return isModified;
    }

    public boolean getIsTestRunning(){
        return isTestRunning;
    }

    public int getNumCorrect(){
        return numCorrect;
    }

    public int getNumWrong(){
        return numWrong;
    }

    public List<QuizCard> getQuizCardList(){
        return quizCardList;
    }


    // SETTERS
    public void setFileName(String fileName) {
        if(fileName.contains(".")){
            fileName = fileName.split("\\.")[0];
        }
        this.fileName = fileName;
    }

    public void setFile(File file)
    {
        this.file = file;
    }

    public File getFile()
    {
        return file;
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
