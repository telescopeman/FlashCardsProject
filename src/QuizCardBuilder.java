import javax.swing.*;
import java.awt.*;

/** QuizCardBuilder - This class allows the user to create, edit and save a Deck of QuizCards.
 * @author Calculus5000, Caleb Copeland
 * @since August 21 2021
 * */
public class QuizCardBuilder extends DisplayUI {
    private JButton button;
    private final JTextArea answerText = new JTextArea(), questionText = new JTextArea();


    public QuizCardBuilder(HomeMenu menu) {
        super(menu,"Quiz Card Builder", new Dimension(400, 400));
        //createQuizCardPlayer(MODE.FLASHCARDS);
    }

    public QuizCardBuilder(DisplayUI ui)
    {
        this(ui.getMenu());
    }


    @Override
    public void close() {
        if (getDeck().getIsModified()) {
            // Automatically closes the program if there's nothing to be saved.
            if (getDeck().getQuizCardList().size() == 0 &&
                    getQuestionText().getText().length() == 0 &&
                    getAnswerText().getText().length() == 0) {
                System.exit(0);
                } else {
                    int optionChosen = JOptionPane.showConfirmDialog(this, "Do you want to save this deck?", "Save",
                            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (optionChosen == JOptionPane.YES_OPTION) {
                        getMenu().save();
                    }
                    if (optionChosen != JOptionPane.CANCEL_OPTION) {
                        System.exit(0);
                    }
                System.exit(0);
                }
            }
        else {
            // Automatically closes the program if there's no changes to be saved.
            System.exit(0);
        }
    }

    protected void buildContent()
    {
        SwingUtilities.invokeLater(
                () -> {
                    //buildMenuBar();
                    add(new EasyLabel("Question:"));
                    buildTextArea();
                    add(new EasyLabel("Answer:"));
                    buildTextArea();
                    buildButtonPanel();
                    //questionText.requestFocusInWindow();
               }
        );
    }

    protected void buildTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        String question;
        try {
            question = getMenu().deck.getQuizCardList().get(0).getQuestion();
        }
        catch (IndexOutOfBoundsException ex)
        {
            question = "";
        }
        textArea.setText(question);
        textArea.setFont(FontConstants.textAreaFont);
        JScrollPane jsp = new JScrollPane(textArea);
        jsp.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(BorderLayout.CENTER, jsp);
    }
//
    /** addCard - adds a QuizCard to the current Deck. */
    private void addCard(){
        getMenu().deck.addQuizCard(getQuestionText().getText(), getAnswerText().getText());
        setQuestionText(null);
        setAnswerText(null);
        }
//
    private void buildButtonPanel() {
        button = new JButton("Add Flashcard");
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.addActionListener(ev -> addCard());
        add(button);
    }
//
//    private void buildContentPane() {
//        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//        setBorder(BorderFactory.createEmptyBorder(5, 15, 0, 15));
//        //setContentPane(contentPane);
//    }
//
//
//
//
//
//    private void buildTextArea(JTextArea jTextArea) {
//        jTextArea.setWrapStyleWord(true);
//        jTextArea.setLineWrap(true);
//        jTextArea.setFont(FontConstants.textAreaFont);
//        jTextArea.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyTyped(KeyEvent e) {
//                deck.setIsModified(true);
//            }
//        });
//        JScrollPane jsp = new JScrollPane(jTextArea);
//        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//        jsp.setAlignmentX(Component.LEFT_ALIGNMENT);
//        contentPane.add(jsp);
//    }
//
//
//
//
//
//    private void displayFrame() {
//        frame.pack();
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
//    }
//
//
//
//
//    // GETTERS
    private JTextArea getAnswerText() {
        return answerText;
    }
//
    public JTextArea getQuestionText() {
        return questionText;
    }
//
//
//    // SETTERS
    private void setAnswerText(String text) {
        SwingUtilities.invokeLater(() -> answerText.setText(text));
    }
//
//    public void setTextAreaEditability(boolean isEditable){
//        questionText.setEditable(isEditable);
//        answerText.setEditable(isEditable);
//        button.setEnabled(isEditable);
//    }
//
//
    private void setQuestionText(String text)
        {
        SwingUtilities.invokeLater(() -> questionText.setText(text));
    }
//
//
//
//
//
//
//
//
//
//
//

}
