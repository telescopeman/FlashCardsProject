import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class MainFrame extends JFrame{
    private DisplayUI contentPane;
    protected Deck deck;
    protected boolean isTestRunning = false;

    private boolean getIsTestRunning()
    {
        return isTestRunning;
    }

    private void setIsTestRunning(boolean i)
    {
        isTestRunning = i;
    }


    public MainFrame() {
        this.deck = new Deck();
        FileManager.initialize();
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
        refresh();
    }

    public void close() {
        contentPane.close();
    }


    enum MODE{
        FLASHCARDS("Flashcards"),
        WRITE("Write")
        ;

        final String name;
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
                try {
                    openFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Prevents window from popping up if there's no QuizCards to use
            if(deck.getQuizCardList().size() > 0) {
                if (getIsTestRunning()) {
                    Toolkit.getDefaultToolkit().beep();
                    //quizCardPlayer.toFront();
                } else {
                    setIsTestRunning(true);
                    //setTextAreaEditability(false);
                    //createQuizCardPlayer(myMode);
                    //quizCardPlayer.build();
                }
            }
        }
    }

    private void openFile() throws IOException {
        deck = FileManager.openFile(deck,this);
        refresh();
        System.out.println(deck);
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



    private void refresh()
    {
        contentPane.load();
        refreshTitle();
        repaint();
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
            try {
                openFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public final Action Save = new AbstractAction("Save"){
        @Override
        public void actionPerformed(ActionEvent ev){
            FileManager.save(deck, SwingUtilities.getWindowAncestor(getJMenuBar()));
        }
    };



    public void refreshTitle(){
        SwingUtilities.invokeLater(() -> setTitle(contentPane.getWindowTitle() + " - " + deck.getFileName()));
    }

    private final Action SaveAs = new AbstractAction("Save as...") {
        @Override
        public void actionPerformed(ActionEvent e) {
            FileManager.saveAs(deck,SwingUtilities.getWindowAncestor(getJMenuBar()));
            refreshTitle();
        }
    };

    private final Action ShuffleDeck = new AbstractAction("Shuffle deck"){
        @Override
        public void actionPerformed(ActionEvent ev){
            deck.shuffle();
        }
    };

}
