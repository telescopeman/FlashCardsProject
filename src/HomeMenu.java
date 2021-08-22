import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class HomeMenu extends JFrame{
    private final JFileChooser fileChooser = new JFileChooser();
    private DisplayUI contentPane;
    protected Deck deck;

    public HomeMenu() {
        this.deck = new Deck();
        loadDisplay(new StartMenu((this)));


        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        buildMenuBar();

        addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        close();
                    }
                }
        );
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        requestFocus();
    }

    public void loadDisplay(DisplayUI c)
    {
        contentPane = c;
        contentPane.load();
        setContentPane(contentPane);
        setMinimumSize(contentPane.getMinimumFrameSize());
        refreshTitle();
    }

    public void close() {
        contentPane.close();
        //invokeLater(contentPane.close());
    }


    /** saveAs - User gets to choose the filename that stores the current Deck */
    private void saveAs(){
        if(fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            deck.save(fileChooser.getSelectedFile().getAbsolutePath());
            deck.setFileName(fileChooser.getSelectedFile().getName());
            setTitle(deck.getFileName());
            deck.setIsModified(false);
        }
    }

    /** save - Saves the current Deck under the same name, if previously saved. If the Deck is new,
     * then saveAs is invoked */
    public void save(){
        if(deck.getFileName().equals("Untitled")){
            saveAs();
        }else{
            deck.save(deck.getFileLocation());
            deck.setIsModified(false);
        }
    }

    enum MODE{
        FLASHCARDS("Flashcards"),
        WRITE("Write")
        ;

        String name;
        MODE(String n) {
            name = n;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private class Play extends AbstractAction {
        private final MODE myMode;
        public Play(MODE mode) {
            super(mode.toString());
            myMode = mode;

        }
        @Override
        public void actionPerformed(ActionEvent ev){
            // Allows the user to open a file if no file is already open
            if(deck.getQuizCardList().size() == 0) {
                openFile();
            }

            // Prevents window from popping up if there's no QuizCards to use
            if(deck.getQuizCardList().size() > 0) {
                if (deck.getIsTestRunning()) {
                    Toolkit.getDefaultToolkit().beep();
                    //quizCardPlayer.toFront();
                } else {
                    deck.setIsTestRunning(true);
                    //setTextAreaEditability(false);
                    //createQuizCardPlayer(myMode);
                    //quizCardPlayer.build();
                }
            }
        }
    }

    private void buildMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        file.add(Open);
        file.add(Save);
        file.add(SaveAs);
        file.add(Exit);

        JMenu card = new JMenu("Study");
        //card.add(ShuffleDeck);
        card.add(new Play(MODE.FLASHCARDS));
        card.add(new Play(MODE.WRITE));
        //card.add(Flashcards);

        jMenuBar.add(file);
        jMenuBar.add(card);
        setJMenuBar(jMenuBar);
    }

    /** openFile - opens a saved Deck */
    private void openFile(){
        int optionChosen = JOptionPane.YES_OPTION;
        if(deck.getIsModified()){
            optionChosen = JOptionPane.showConfirmDialog(this, "Do you want to save this deck before " +
                    "opening another?", "Save", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
            if(optionChosen == JOptionPane.YES_OPTION){
                save();
            }
        }

        if(optionChosen != JOptionPane.CANCEL_OPTION && fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
            deck = new Deck();
            deck.readFile(fileChooser.getSelectedFile().getAbsolutePath());
            setTitle(deck.getFileName());
            //setQuestionText(null);
            //setAnswerText(null);
            return;
        }
    }

//    /** createQuizCardPlayer - safely creates an instance of QuizCardPlayer, whilst allowing QuizCardPlayer to
//     * have a callback
//     * @param myMode The type of studying.
//     * */
//    private QuizCardPlayer createQuizCardPlayer(MODE myMode){
//        switch (myMode)
//        {
//            case FLASHCARDS: return new FlashcardsPlayer(deck, this); // registers the callback
//            case WRITE: return new WritePlayer(deck, this); // registers the callback
//            default:
//                throw new IllegalArgumentException("bruh how");
//        }
//    }

    // ACTIONS
    public final Action Exit = new AbstractAction("Quit"){
        @Override
        public void actionPerformed(ActionEvent ev){
            close();
        }
    };

    public final Action Open = new AbstractAction("Open"){
        @Override
        public void actionPerformed(ActionEvent ev){
            openFile();
        }
    };

    public final Action Save = new AbstractAction("Save"){
        @Override
        public void actionPerformed(ActionEvent ev){
            save();
        }
    };



    public void refreshTitle(){
        SwingUtilities.invokeLater(() -> setTitle(contentPane.getWindowTitle() + " - " + deck.getFileName()));
    }

    private final Action SaveAs = new AbstractAction("Save as...") {
        @Override
        public void actionPerformed(ActionEvent e) {
            saveAs();
        }
    };

    private final Action ShuffleDeck = new AbstractAction("Shuffle deck"){
        @Override
        public void actionPerformed(ActionEvent ev){
            deck.shuffle();
        }
    };

}
