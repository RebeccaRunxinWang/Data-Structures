import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.*;
import javax.swing.JComponent;

/**
 *  A component that draws a picture of a graph.
 */
public class GraphComponent extends JComponent {
  /** The radius of the circle to draw for a node. */
  public static final int NODE_RADIUS = 16;

  /** The diameter of the circle to draw for a node. */
  public static final int NODE_DIAMETER = 2 * NODE_RADIUS;

  /** The length of a stroke of an arrow head. */
  public static final int ARROW_HEAD_LENGTH = 15;

  /** The angle of the stroke of an arrow head with respect to the line. */
  public static final double ARROW_ANGLE = 9.0*Math.PI/10.0;

  private boolean show = false;

  /** 
   *  The graph to draw.  Note that this graph is based on PlacedData,
   *  so the data associated with a node includes its position and
   *  color.
   */
  private GraphImplementation<PlacedData<Integer>, Integer> graphWithPlacement;

  private Dijkstra<PlacedData<Integer>, Integer> dijkstra;

  /** 
   *  Constructor.
   *
   *  @param graphWithPlacement - the graph to draw
   *  @param chosenNode the node to start the Dijkstra's algorithm from
   */
  @SuppressWarnings("unchecked")
  public GraphComponent(GraphImplementation<PlacedData<Integer>, Integer> graphWithPlacement, Graph.Node<PlacedData<Integer>, Integer> chosenNode ) {
    this.graphWithPlacement = graphWithPlacement;
    dijkstra =  new Dijkstra(graphWithPlacement);
    setMinimumSize(new Dimension(100, 100));
    setPreferredSize(new Dimension(400, 400));
    dijkstra.execute(chosenNode);
  }

  /**
   *  Paints nodes and edges.
   *
   *  @param g the graphics context in which to render
   */
  public void paint(Graphics g) {
    for (Graph.Edge<PlacedData<Integer>, Integer> edge : graphWithPlacement.getEdges()) {
      g.setColor(Color.black);
      int x1 = edge.getTail().getData().getX();
      int y1 = edge.getTail().getData().getY();
      int x2 = edge.getHead().getData().getX();
      int y2 = edge.getHead().getData().getY();
      double theta = Math.atan2(y2 - y1, x2 - x1);  // angle of edge's arrow
      
      // arrow head
      double edgeX1 = x1 + Math.cos(theta)*NODE_RADIUS;
      double edgeY1 = y1 + Math.sin(theta)*NODE_RADIUS;
      double edgeX2 = x2 - Math.cos(theta)*NODE_RADIUS;
      double edgeY2 = y2 - Math.sin(theta)*NODE_RADIUS;
      // arrow
      g.drawLine((int)Math.round(edgeX1), (int)Math.round(edgeY1),
                 (int)Math.round(edgeX2), (int)Math.round(edgeY2));

      g.drawString(edge.getData().toString(),
                   (x1+x2+10)/2, (y1+y2-10)/2);
      // arrow head
      double arrowX1 = edgeX2 + Math.cos(theta-ARROW_ANGLE)*ARROW_HEAD_LENGTH;
      double arrowY1 = edgeY2 + Math.sin(theta-ARROW_ANGLE)*ARROW_HEAD_LENGTH;
      double arrowX2 = edgeX2 + Math.cos(theta+ARROW_ANGLE) * ARROW_HEAD_LENGTH;
      double arrowY2 = edgeY2 + Math.sin(theta+ARROW_ANGLE) * ARROW_HEAD_LENGTH;
      Polygon arrowHead = new Polygon(new int[]{(int)Math.round(edgeX2),
                                                (int)Math.round(arrowX1),
                                                (int)Math.round(arrowX2)},
                                      new int[]{(int)Math.round(edgeY2),
                                                (int)Math.round(arrowY1),
                                                (int)Math.round(arrowY2)},
                                      3);
      g.fillPolygon(arrowHead);
    }
    for (Graph.Node<PlacedData<Integer>, Integer> node : graphWithPlacement.getNodes()) {
      g.setColor(node.getData().getColor());
      g.fillOval(node.getData().getX() - NODE_RADIUS,
                 node.getData().getY() - NODE_RADIUS,
                 NODE_DIAMETER, NODE_DIAMETER);
      g.setColor(Color.black);
      g.drawString(node.getData().getData().toString(),
                   node.getData().getX(), node.getData().getY());
      if(show) {
     g.drawString(Integer.toString(dijkstra.ShortDistance(node)), node.getData().getX()-10, node.getData().getY()+15);
      }
    }
  }

/**
 *  to set the showdistance to active or inactive. 
 * @param condition the showdistance is active(true) or inactive(false).
 */

  public void showdistance(boolean condition) {
       show = condition;
  }

/**
 *   to set the chosennode which is to start the Dijkstra's algorithm from
 * @param chosenNode to set the chosennode, which to start the Dijkstra's algorithm
 */
  @SuppressWarnings("unchecked")
  public void setNode(Graph.Node<PlacedData<Integer>, Integer> chosenNode) {
    dijkstra =  new Dijkstra(graphWithPlacement);
    dijkstra.execute(chosenNode);
  }
}
