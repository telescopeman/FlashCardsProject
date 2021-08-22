import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.SwingUtilities;
import javax.swing.BorderFactory;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.Action;
import javax.swing.AbstractAction;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JOptionPane;

import java.awt.Dimension;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/** QuizCardBuilder - This class allows the user to create, edit and save a Deck of QuizCards.
 * @author Calculus5000, Caleb Copeland
 * @since August 21 2021
 * */
public class QuizCardBuilder extends ElementUI {
    private JButton button;
    private final JFileChooser fileChooser = new JFileChooser();
    private final JTextArea answerText = new JTextArea(), questionText = new JTextArea();
    private QuizCardPlayer quizCardPlayer;



    public QuizCardBuilder(Deck deck) {
        super("Quiz Card Builder", new Dimension(400, 400));
        this.deck = deck;
        createQuizCardPlayer(MODE.FLASHCARDS);
    }

    public void build()
    {
        SwingUtilities.invokeLater(
                () -> {
                    buildFrame();
                    buildContentPane();
                    buildMenuBar();
                    contentPane.add(new EasyLabel("Question:"));
                    buildTextArea(questionText);
                    contentPane.add(new EasyLabel("Answer:"));
                    buildTextArea(answerText);
                    buildButtonPanel();
                    displayFrame();
                    questionText.requestFocusInWindow();
                }
        );
    }

    /** addCard - adds a QuizCard to the current Deck. */
    private void addCard(){
        deck.addQuizCard(getQuestionText().getText(), getAnswerText().getText());
        setQuestionText(null);
        setAnswerText(null);
    }

    private void buildButtonPanel() {
        button = new JButton("Add Flashcard");
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.addActionListener(ev -> addCard());
        contentPane.add(button);
    }

    private void buildContentPane() {
        contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 15, 0, 15));
        frame.setContentPane(contentPane);
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
        frame.setJMenuBar(jMenuBar);
    }

    private void buildTextArea(JTextArea jTextArea) {
        jTextArea.setWrapStyleWord(true);
        jTextArea.setLineWrap(true);
        jTextArea.setFont(FontConstants.textAreaFont);
        jTextArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                deck.setIsModified(true);
            }
        });
        JScrollPane jsp = new JScrollPane(jTextArea);
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jsp.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPane.add(jsp);
    }

    protected void close(){
        if (deck.getIsModified()) {
            // Automatically closes the program if there's nothing to be saved.
            if(deck.getQuizCardList().size() == 0 && getQuestionText().getText().length() == 0
                    && getAnswerText().getText().length() == 0) {
                System.exit(0);
            }else {
                int optionChosen = JOptionPane.showConfirmDialog(frame, "Do you want to save this deck?", "Save",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (optionChosen == JOptionPane.YES_OPTION) {
                    save();
                }
                if (optionChosen != JOptionPane.CANCEL_OPTION) {
                    System.exit(0);
                }
            }
        }else{
            // Automatically closes the program if there's no changes to be saved.
            System.exit(0);
        }
    }

    /** createQuizCardPlayer - safely creates an instance of QuizCardPlayer, whilst allowing QuizCardPlayer to
     * have a callback
     * @param myMode The type of studying.
     * */
    private void createQuizCardPlayer(MODE myMode){
        switch (myMode)
        {
            case FLASHCARDS: quizCardPlayer = new FlashcardsPlayer(deck, this); // registers the callback
            case WRITE: quizCardPlayer = new WritePlayer(deck, this); // registers the callback
        }
    }

    private void displayFrame() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /** openFile - opens a saved Deck */
    private void openFile(){
        int optionChosen = JOptionPane.YES_OPTION;
        if(deck.getIsModified()){
            optionChosen = JOptionPane.showConfirmDialog(frame, "Do you want to save this deck before " +
                    "opening another?", "Save", JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
            if(optionChosen == JOptionPane.YES_OPTION){
                save();
            }
        }

        if(optionChosen != JOptionPane.CANCEL_OPTION && fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION){
            deck = new Deck();
            deck.readFile(fileChooser.getSelectedFile().getAbsolutePath());
            setTitle(deck.getFileName());
            setQuestionText(null);
            setAnswerText(null);
        }
    }

    /** save - Saves the current Deck under the same name, if previously saved. If the Deck is new,
     * then saveAs is invoked */
    private void save(){
        if(deck.getFileName().equals("Untitled")){
            saveAs();
        }else{
            if(getQuestionText().getText().length() > 0){
                addCard();
            }
            deck.save(deck.getFileLocation());
            deck.setIsModified(false);
        }
    }

    /** saveAs - User gets to choose the filename that stores the current Deck */
    private void saveAs(){
        if(fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
            if(getQuestionText().getText().length() > 0){
                addCard();
            }
            deck.save(fileChooser.getSelectedFile().getAbsolutePath());
            deck.setFileName(fileChooser.getSelectedFile().getName());
            setTitle(deck.getFileName());
            deck.setIsModified(false);
        }
    }


    // GETTERS
    private JTextArea getAnswerText() {
        return answerText;
    }

    public JTextArea getQuestionText() {
        return questionText;
    }


    // SETTERS
    private void setAnswerText(String text) {
        SwingUtilities.invokeLater(() -> answerText.setText(text));
    }

    public void setTextAreaEditability(boolean isEditable){
        questionText.setEditable(isEditable);
        answerText.setEditable(isEditable);
        button.setEnabled(isEditable);
    }

    private void setTitle(String newTitle){
        SwingUtilities.invokeLater(() -> frame.setTitle("Quiz Card Builder - " + newTitle));
    }

    private void setQuestionText(String text) {
        SwingUtilities.invokeLater(() -> questionText.setText(text));
    }




    // ACTIONS
    private final Action Exit = new AbstractAction("Quit"){
        @Override
        public void actionPerformed(ActionEvent ev){
            close();
        }
    };

    private final Action Open = new AbstractAction("Open"){
        @Override
        public void actionPerformed(ActionEvent ev){
            openFile();
        }
    };


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
                    quizCardPlayer.toFront();
                } else {
                    deck.setIsTestRunning(true);
                    setTextAreaEditability(false);
                    createQuizCardPlayer(myMode);
                    quizCardPlayer.build();
                }
            }
        }
    }

    private final Action Save = new AbstractAction("Save"){
        @Override
        public void actionPerformed(ActionEvent ev){
            save();
        }
    };

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
