import java.awt.*;
import java.awt.event.*;
import javax.swing.*;        
import java.io.*;

/**
 *  Class that runs a maze display/solution GUI
 *
 *  @author Nicholas R. Howe, John Ridgway
 *  @version CSC 212, 13 April 2081
 */
public class MazeSolver {
  /** Holds the maze to solve */
  private static MazeGraph maze;

  /** The window */
  private JFrame frame;

  /** Solve button */
  private JButton solveButton;

  /** Reset button */
  private JButton resetButton;
 
 /** TextField */
  private TextField textField;

  /**
   *  Constructor that builds a maze from a file.
   *
   *  @param fileName  the name of the file holding the maze
   *  @throws IOException if there are I/O problems
   */
  public MazeSolver(String fileName) throws IOException {
    maze = new MazeGraph(new FileReader(fileName));

  }
  
  /**
   *  Constructor that gets the maze from the standard input.
   *
   *  @throws IOException if there are I/O problems
   */
  public MazeSolver() throws IOException {
    maze = new MazeGraph();

  }

  /**
   *  Create and show the GUI.
   */
  public void createAndShowGUI() {
    // // Make sure we have nice window decorations.
    // JFrame.setDefaultLookAndFeelDecorated(true);

    // Create and set up the window.
    frame = new JFrame("Maze Application");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Add components
    createComponents(frame.getContentPane());

    // Display the window.
    frame.pack();
    frame.setVisible(true);
  }

  /**
   *  Create the components and add them to the frame.
   *
   *  @param pane the frame to which to add them
   */
  public void createComponents(Container pane) {
    pane.add(maze);
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout());
    solveButton = new JButton("Solve");
    solveButton.addActionListener(new SolveListener());
    panel.add(solveButton);
    resetButton = new JButton("Reset");
    resetButton.addActionListener(new ResetListener());
    panel.add(resetButton);
    pane.add(panel, BorderLayout.SOUTH);
    textField = new TextField(20);
    panel.add(textField);
    textField.addTextListener(new MazeTextListener("Enter Maze File"));
  }

  /**
   *  Execute the application.
   */
  public void execute() {
    // Schedule a job for the event-dispatching thread:
    // creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
        public void run() {
		    createAndShowGUI();
        }
      });
  }

  /**
   *  The obligatory main method for the application.  With no
   *  arguments the application will read the maze from the standard
   *  input; with one argument (a file name) it will read the maze
   *  from the named file.
   *
   *  @param args  the command-line arguments
   *  @throws IOException if there are I/O problems
   */
  public static void main(String[] args) throws IOException {
    MazeSolver mazeSolver;
    if (args.length == 0) {
      mazeSolver = new MazeSolver();
    } else {
      mazeSolver = new MazeSolver(args[0]);
    }
    mazeSolver.execute();
  }

  /** 
   *  Event handler for Solve button
   */
  private class SolveListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      maze.reset();
      // call to solve should be on a new thread
      solveButton.setEnabled(false);
      resetButton.setEnabled(false);
      (new SolverThread()).execute();
    }
  }

  /**
   *  Event handler for Reset button
   */
  private class ResetListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      maze.reset();
    }
  }


  /** 
   *  Event handler for Text Field
   */
  private class MazeTextListener implements TextListener{
  String preface;

  public MazeTextListener(String source) {
    preface = source ;
  }

  public void textValueChanged(TextEvent e) {
    TextComponent tc = (TextComponent) e.getSource();
    // System.out.println("Typed maze filename " + tc.getText());
    try {
    frame.getContentPane().remove(maze);
    maze = new MazeGraph(new FileReader(tc.getText()));
    maze.repaint();
    frame.getContentPane().add(maze);
    frame.pack();
   } catch ( Exception ex)  {
     // System.out.println("File Not Found.");
   }
   frame.setVisible(true);
  }
 }

  /**
   *  Worker class for solving the maze
   */
  private class SolverThread extends SwingWorker<Boolean, Object> {
    @Override
    public Boolean doInBackground() {
      return maze.solve();
    }

    @Override
    protected void done() {
      try {
        if (!get()) {  // test the result of doInBackground()
          System.out.println("Maze has no valid solution.");
        }
        solveButton.setEnabled(true);
        resetButton.setEnabled(true);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
