import java.awt.Dimension;
import java.awt.Graphics;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 *  Class that use graph to represent maze
 *
 *  @author Rebecca Wang
 *  @version CSC 212, 3 May 2018
 */
public class MazeGraph extends JComponent {
  /** How many pixels on a side are the squares */
  private static final int SQUARE_SIZE = 15;
  /** The graph */
  private GraphImplementation<MazeData,String> maze;
  /** Start Node */
  private Graph.Node<MazeData,String> start= null;
  /** Finish Node */
  private Graph.Node<MazeData,String> finish = null;
  /** Map that stores node and previous node */
  private Map<Graph.Node<MazeData,String>, Graph.Node<MazeData,String>> shortpath = new HashMap<Graph.Node<MazeData,String>, Graph.Node<MazeData,String>>();
  /** 2d array of nodes */
  private Graph.Node<MazeData,String>[][] arr;
  /** Processor */
  private NodeProcessor processor = new NodeProcessor();
  /** Height of maze */
  private int height;
  /** Width of maze */
  private int width;
/** 
   *  Constructor to read a maze from a reader.
   *
   *  @param reader the reader which has the maze in it
   *  @throws IOException if there are I/O problems
   */
   @SuppressWarnings("unchecked")
   public MazeGraph(Reader reader) throws IOException {
    BufferedReader bufferedreader = new BufferedReader(reader);
    bufferedreader.mark(10000000);
    ArrayList<String> list = new ArrayList<String>();
    while (true) {
      String a = bufferedreader.readLine();
      if (a==null || a.equals('\n')) break;
      list.add(a);
    }
    height = list.size();
    width = list.get(0).length();
    maze = new GraphImplementation<MazeData,String>();
    arr = (GraphImplementation.Node<MazeData,String>[][])new Graph.Node[height][width];
    for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
          char x = list.get(i).charAt(j);
          if(x == '\n') break;
          Graph.Node<MazeData,String> node = null;
          if (x=='.') {
          	arr[i][j] = maze.addNode(new MazeData(MazeContents.OPEN, i, j));
          } else if (x=='S') {
            start = maze.addNode(new MazeData(MazeContents.START, i, j));
            arr[i][j] = start;
          } else if (x=='F') {
           finish = maze.addNode(new MazeData(MazeContents.FINISH, i, j));
           arr[i][j] = finish;
          }
        }
    } 
    for (int i = 0; i < height; i++) {
       for (int j = 0; j < width; j++) {
       if (arr[i][j]!= null) {
       	   	  StringBuilder sb2 = new StringBuilder();
              sb2.append(i);
              sb2.append(",");
              sb2.append(j);
              String str2 = sb2.toString();
   	    if ( j>=1 && arr[i][j-1]!= null) {
        maze.addEdge(str2, arr[i][j], arr[i][j-1]);
        }
        if ( j<width-1 && arr[i][j+1]!= null) {
           maze.addEdge(str2, arr[i][j], arr[i][j+1]);
        }
        if ( i>=1 && arr[i-1][j]!= null) {
          maze.addEdge(str2, arr[i][j],arr[i-1][j]);
        }
        if ( i<height-1 && arr[i+1][j]!= null) {
          maze.addEdge(str2, arr[i][j], arr[i+1][j]);
        }
         }
         }
       }
     }
   
  /**
   *  Constructor to read a maze from the standard input stream.
   *  @throws IOException if there are I/O problems
   */
  public MazeGraph() throws IOException {
    this(new InputStreamReader(System.in));
  }

  /**
   *  Reset the maze.  Revert all of the visited and dead-end squares
   *  to open.
   */
  public void reset() {
  	  	    for (int i = 0; i < height; i ++) {
      for (int j = 0; j < width; j++) {
      	if (arr[i][j]!= null){
      		if (arr[i][j].getData().getData() == MazeContents.VISITED || arr[i][j].getData().getData() == MazeContents.PATH){
             Graph.Node<MazeData,String> node = arr[i][j];
      		 node.getData().setData(MazeContents.OPEN);
        }  
          }
        }
    }
    repaint();
  }

  /**
   *  Solve the maze if possible.
   *
   *  @return true if successful
   */
  public boolean solve() {
     boolean solve = maze.breadthFirstTraversal(start, processor);
     if (solve) {
      List<Graph.Node<MazeData,String>> shortestPath = new ArrayList<>();
      Graph.Node<MazeData,String> node = finish;
       while(node != start) {
        shortestPath.add(node);
        node = shortpath.get(node);
    }
    Collections.reverse(shortestPath);
    for(int i=0;i<shortestPath.size();i++) {
        Graph.Node<MazeData,String> pathnode = shortestPath.get(i);
        if (!pathnode.equals(finish)){
        pathnode.getData().setData(MazeContents.PATH);
       }
    }
    repaint();
     }
     return solve;
  }
  
  

  /**
   *  Paint the maze when needed.
   * @param g graphics
   */
  public void paint(Graphics g) {
  	    for (int i = 0; i < height; i ++) {
      for (int j = 0; j < width; j++) {
      	if (arr[i][j]!=null){
            MazeContents current = arr[i][j].getData().getData();
             g.setColor(current.getColor());
        g.fillRect(SQUARE_SIZE*j, SQUARE_SIZE*i, SQUARE_SIZE, SQUARE_SIZE);
      	} else {
          MazeContents current = MazeContents.WALL;
                	g.setColor(current.getColor());
        g.fillRect(SQUARE_SIZE*j, SQUARE_SIZE*i, SQUARE_SIZE, SQUARE_SIZE);
      	}
        }
      }
    }
/**
 * Processor Class
 */
    private class NodeProcessor implements Graph.Processor<MazeData,String> {

      /**
       *  To process edge.
       *  @param edge the edge to be processed
       *  @return false 
       */
       public boolean processEdge(Graph.Edge<MazeData,String> edge) {
       System.out.println("processEdge:"+edge.getData());
       if (!shortpath.containsKey(edge.getHead())){
       	shortpath.put(edge.getHead(),edge.getTail());
       }
        return false;
    }
      /**
       *  To preprocess node.
       *  @param node the nodee to be preprocessed
       *  @return true if node is finish node 
       */
       public boolean preProcessNode(Graph.Node<MazeData,String> node) {
         System.out.println("preProcessNode:"+node.getData());
         if (node.equals(finish)) {
         	System.out.println("Success");
         	return true;    	
          } else {
           return false;
          }
       }

      /**
       *  To postprocess node.
       *  @param node the node to be processed
       *  @return false 
       */
       public boolean postProcessNode(Graph.Node<MazeData,String> node) {
          System.out.println("postProcessNode:"+node.getData()); 
          if (!node.equals(start)) {
           	node.getData().setData(MazeContents.VISITED);
          }
          return false;
       }
    }

}