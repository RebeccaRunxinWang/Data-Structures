import java.awt.*;
import java.awt.event.*;
import javax.swing.*;        
import java.io.*;

/**
 *  Class that runs a maze display/solution GUI
 *
 *  @author Rebecca Wang
 *  @version CSC 212, 13 April 2018
 */
public class GraphGUI {
  /** Holds the graph GUI component */
  private GraphComponent graphComponent; 

  /** Holds the graph to solve */
  private Dijkstra<PlacedData<Integer>, Integer> dijkstra;

  /** Holds the graph to solve */
  private GraphImplementation<PlacedData<Integer>,Integer> graph;

  /** The window */
  private JFrame frame;

  /** The node last selected by the user. */
  private Graph.Node<PlacedData<Integer>, Integer> chosenNode;

  /**
   *  Constructor that builds a completely empty graph.
   */
  @SuppressWarnings("unchecked")
  public GraphGUI() {
    this.graph = new GraphImplementation<PlacedData<Integer>, Integer>();
    initializeGraph();
    this.graphComponent = new GraphComponent(graph, chosenNode);
    dijkstra = new Dijkstra(graph);
  }

  /**
   *  Create and show the GUI.
   */
  public void createAndShowGUI() {
    // Create and set up the window.
    frame = new JFrame("Graph Application");
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
    pane.add(this.graphComponent);
    MyMouseListener ml = new MyMouseListener();
    this.graphComponent.addMouseListener(ml);
    this.graphComponent.addMouseMotionListener(ml);
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout());
    JButton resetButton = new JButton("Reset");
    resetButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
             graphComponent.showdistance(false);
             graphComponent.repaint();
        }
      });
    panel.add(resetButton);
    JButton dftButton = new JButton("Shortest distance");
    dftButton.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
         dijkstra.execute(chosenNode);
         dijkstra.printDistance();
         graphComponent.showdistance(true);
         graphComponent.repaint();
        }
      });
    panel.add(dftButton);
    pane.add(panel, BorderLayout.SOUTH);
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
   *  arguments the application will read the graph from the standard
   *  input; with one argument (a file name) it will read the graph
   *  from the named file.
   *
   *  @param args  the command-line arguments
   *  @throws IOException to throw exception
   */
  public static void main(String[] args) throws IOException {
    GraphGUI graphSolver;
    graphSolver = new GraphGUI();
    graphSolver.execute();
  }

    /**
   *  A mouse listener to handle click and drag actions on nodes.
   */
  private class MyMouseListener extends MouseAdapter {
    /** How far off the center of the node was the click? */
    private int deltaX;
    private int deltaY;

    public void mouseDragged(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
      System.out.println("MousePressed");
      for (Graph.Node<PlacedData<Integer>, Integer> node : graph.getNodes()) {
        double nodeX = node.getData().getX();
        double nodeY = node.getData().getY();
        double mouseX = e.getX();
        double mouseY = e.getY();
        if (Math.sqrt((nodeX-mouseX)*(nodeX-mouseX)
                      +(nodeY-mouseY)*(nodeY-mouseY))
            <= graphComponent.NODE_RADIUS) {
          chosenNode = node;
         graphComponent.setNode(chosenNode);
        graphComponent.repaint();
        }
      }
    }
  }

 /**
   *  Initialize the graph.
   */
  private void initializeGraph() {
    Graph.Node<PlacedData<Integer>, Integer> node1 = graph.addNode(new PlacedData<Integer>(1, 50, 50));
    Graph.Node<PlacedData<Integer>, Integer> node2 = graph.addNode(new PlacedData<Integer>(2, 150, 50));
    Graph.Node<PlacedData<Integer>, Integer> node3 = graph.addNode(new PlacedData<Integer>(3, 150, 150));
    Graph.Node<PlacedData<Integer>, Integer> node4 = graph.addNode(new PlacedData<Integer>(4, 80, 150));
    Graph.Node<PlacedData<Integer>, Integer> node5 = graph.addNode(new PlacedData<Integer>(5, 250, 20));
    Graph.Node<PlacedData<Integer>, Integer> node6 = graph.addNode(new PlacedData<Integer>(6, 250, 100));
    Graph.Node<PlacedData<Integer>, Integer> node7 = graph.addNode(new PlacedData<Integer>(7, 250, 180));
    Graph.Node<PlacedData<Integer>, Integer> node8 = graph.addNode(new PlacedData<Integer>(8, 150, 220));
    Graph.Edge<PlacedData<Integer>, Integer> edge1 = graph.addEdge(10, node1, node2);
    Graph.Edge<PlacedData<Integer>, Integer> edge2 = graph.addEdge(2, node1, node3);
    Graph.Edge<PlacedData<Integer>, Integer> edge3 = graph.addEdge(5, node2, node4);
    Graph.Edge<PlacedData<Integer>, Integer> edge4 = graph.addEdge(4, node2, node5);
    Graph.Edge<PlacedData<Integer>, Integer> edge5 = graph.addEdge(9, node3, node6);
    Graph.Edge<PlacedData<Integer>, Integer> edge6 = graph.addEdge(1, node3, node7);
    Graph.Edge<PlacedData<Integer>, Integer> edge7 = graph.addEdge(3, node3, node8);
    Graph.Edge<PlacedData<Integer>, Integer> edge8 = graph.addEdge(6, node4, node8);
    Graph.Edge<PlacedData<Integer>, Integer> edge9 = graph.addEdge(7, node8, node7);
    Graph.Edge<PlacedData<Integer>, Integer> edge10 = graph.addEdge(3, node6, node7);
    Graph.Edge<PlacedData<Integer>, Integer> edge11 = graph.addEdge(12, node6, node5);
    Graph.Edge<PlacedData<Integer>, Integer> edge12 = graph.addEdge(8, node6, node2);
    Graph.Edge<PlacedData<Integer>, Integer> edge13 = graph.addEdge(2, node2, node3);
    chosenNode = node1;
  }

}
