import java.awt.FlowLayout;
import javax.swing.JFrame;
import uk.ac.leedsbeckett.oop.LBUGraphics;

// Main class for the Turtle Graphics program
public class MainClass extends LBUGraphics {

    private TurtleGraphics turtle; // Turtle drawing panel

    public MainClass() {
        turtle = new TurtleGraphics();

        // Set up main window
        JFrame MainFrame = new JFrame();
        MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainFrame.setLayout(new FlowLayout());
        MainFrame.add(turtle);
        MainFrame.pack();
        MainFrame.setVisible(true);

        //turtle.about();        // Show startup message
        turtle.consoleInput(); // Enable command input
    }

    // Handle user commands entered via GUI
    @Override
    public void processCommand(String command) {
        turtle.processCommand(command);
    }

    public static void main(String[] args) {
        new MainClass(); // Start the program
    }
}