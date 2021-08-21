/** MainCode - creates an instance of a simple flashcard program
 * @author Calculus5000, Caleb Copeland
 * @since August 21 2021
 * */
public class MainCode {
    public static void main(String[] args){
        MainCode q = new MainCode();
        q.go();
    }
    private void go(){
        QuizCardBuilder qcb = new QuizCardBuilder(new Deck());
        qcb.build();
    }
}