import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

/**
 * @author Calculus5000, Caleb Copeland, baeldung
 */
public abstract class FileManager {
    private final static String EXTENSION = "cardset";
    private static MainFrame homeMenu;
    private final static JFileChooser fileChooser = new JFileChooser();

    private static final String QUIZ_CARD_TERMINATOR = "\n29rje2r9\n",
            QUIZ_CARD_SEPARATOR = "\te23bf0hj\t";

    public static void initialize(MainFrame menu)
    {
        fileChooser.setFileFilter(new FileNameExtensionFilter("Flashcards", EXTENSION));
        homeMenu = menu;
    }

    /** saveAs - User gets to choose the filename that stores the current Deck */
    public static void saveAs(Deck deck){
        if(fileChooser.showSaveDialog(homeMenu) == JFileChooser.APPROVE_OPTION) {
            saveHelper(deck, fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    /** save - Saves the current Deck under the same name, if previously saved. If the Deck is new,
     * then saveAs is invoked */
    public static void save(Deck deck){
        if(deck.getFileName().equals("Untitled")){
            saveAs(deck);
        }else{
            saveHelper(deck, deck.getFileLocation());
        }
    }

    /** readFile - loads in the data from a saved deck into quizCardList */
    private static Deck readFile(String fileLocation) throws IOException {
        File file = new File(fileLocation);
        Deck deck = new Deck();
        deck.setFileName(file.getName());
        deck.setFile(file);
        return parseData(deck,fileToString(file));
    }

    public static String fileToString(File file) throws IOException
    {
        assert file.canRead();
        try(BufferedReader input = new BufferedReader(new FileReader(file))){
            int letterNumber;
            StringBuilder dataToParse = new StringBuilder();
            while((letterNumber = input.read()) != -1){
                dataToParse.append((char) letterNumber);
            }
            return dataToParse.toString();
        }
    }

    private static String fixExtension(String fileLocation)
    {
        String newLocation;
        if (fileLocation.contains("."))
        {
            if (fileLocation.substring(fileLocation.lastIndexOf(".")+1).equals(EXTENSION))
            {
                newLocation = fileLocation;
            }
            else {
                newLocation = fileLocation.substring(0,fileLocation.lastIndexOf(".") + 1) + EXTENSION;
            }
        }
        else
        {
            newLocation = fileLocation + "." + EXTENSION;
        }
        return newLocation;
    }

    /** save - saves the Deck to specified file location */
    private static void saveHelper(Deck deck, String fileLocation){
        String newLocation = fixExtension(fileLocation);
        deck.setFile(new File(newLocation));
        deck.setFileName(fileChooser.getSelectedFile().getName());
        deck.setIsModified(false);
        assert deck.getFile().canWrite();
        try (BufferedWriter output = new BufferedWriter(new FileWriter(deck.getFile()))) {
            for(QuizCard quizCard : deck.getQuizCardList()){
                output.write(quizCard.getQuestion() + QUIZ_CARD_SEPARATOR + quizCard.getAnswer() + QUIZ_CARD_TERMINATOR);
            }
        }catch(IOException ioEx){
            ioEx.printStackTrace();
        }
    }


    /** parseData - parses the data from an input String using specified terminators and separators. */
    private static Deck parseData(Deck deck, String unparsedData) {
        String[] split_data = unparsedData.split(QUIZ_CARD_TERMINATOR);
        // We have now separated it out into each flashcard
        for (String flashcard_data : split_data) {
            String[] readable_flashcard_data = flashcard_data.split(QUIZ_CARD_SEPARATOR);
            deck.addQuizCard(readable_flashcard_data[0], readable_flashcard_data[1]);
        }
        return deck;
    }

    /** openFile - opens a saved Deck */
    public static Deck openFile(Deck current_deck) throws IOException {
        int optionChosen = JOptionPane.YES_OPTION;
        if(current_deck.getIsModified()){
            optionChosen = JOptionPane.showConfirmDialog(homeMenu, "Do you want to save this deck before " +
                    "opening another?", "Save", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
            if(optionChosen == JOptionPane.YES_OPTION){
                save(current_deck);
            }
        }

        if(optionChosen != JOptionPane.CANCEL_OPTION && fileChooser.showOpenDialog(homeMenu) == JFileChooser.APPROVE_OPTION){
            return readFile(fileChooser.getSelectedFile().getAbsolutePath());
        }
        else return current_deck;
    }

}
