import java.util.*;

/**
 *  An implementation of Dijkstra's algorithm. 
 *  
 *  @author Rebecca Wang
 *  @version CSC 212, April 19, 2018
 *
 *  @param <N>  the type of the data to be associated with a node
 *  @param <E>  the type of the data to be associated with an edge
 */

public class Dijkstra<N,E> {
    /** A set of nodes. */
    private Set<Graph.Node<N,E>> nodes = new HashSet<Graph.Node<N,E>>();
    /** A set of edges. */
    private Set<Graph.Edge<N,E>> edges = new HashSet<Graph.Edge<N,E>>();
    /** A set of visited nodes. */
    private Set<Graph.Node<N,E>> visited = new HashSet<Graph.Node<N,E>>();
    /** A queue. */
    private Queue<Graph.Node<N,E>> queue = new LinkedList<Graph.Node<N,E>>();
    /** A map of nodes. */
    private Map<Graph.Node<N,E>,Graph.Node<N,E>> map = new HashMap<Graph.Node<N,E>, Graph.Node<N,E>>();
    /** A map of distances. */
    private Map<Graph.Node<N,E>, Integer> distance = new HashMap<Graph.Node<N,E>, Integer>();
    /** A Graph. */
    private Graph<N,E> graph = new GraphImplementation<N,E>();
    /** A start node. */
    private Graph.Node<N,E> startnode; 
  
  /**
   *  Constructor.
   * @param g  the graph to use Dijkstra on
   */
    public Dijkstra(GraphImplementation<N,E> g) {
    	graph = g;
        nodes = g.getNodes();
        edges = g.getEdges();
    }

  /**
   *  Execute Dijkstra's algorithm 
   * @param start  the start node
   */
    public void execute(Graph.Node<N,E> start) {
        startnode = start;
        distance.put(start, 0);
        queue.add(start);
        while (!queue.isEmpty()) {
            Graph.Node<N,E> node = getMin(queue);
            visited.add(node);
            queue.remove(node);
            MinDistances(node);
        }
    }

  /**
   *  get minimum distance from a start node.
   * @param node  the start node
   */
    public void MinDistances(Graph.Node<N,E> node) {
        List<Graph.Node<N,E>> neighbors = getNeighbors(node);
        for (Graph.Node<N,E> target : neighbors) {
            if (ShortDistance(target) > ShortDistance(node)+ (int)graph.findEdge(node, target).getData()) {
                distance.put(target, ShortDistance(node)+ (int)graph.findEdge(node, target).getData());
                map.put(target, node);
                queue.add(target);
            }
        }
    }

  /**
   *  get neighbors of a node.
   * @param node  the start node
   * @return list of nodes
   */
    public List<Graph.Node<N,E>> getNeighbors(Graph.Node<N,E> node) {
        List<Graph.Node<N,E>> neighbors = new ArrayList<Graph.Node<N,E>>();
        for (Graph.Edge<N,E> edge : edges) {
            if (edge.getTail().equals(node) && !visited.contains(edge.getHead())) {
                neighbors.add(edge.getHead());
            }
        }
        return neighbors;
    }

  /**
   *  get node with min distance.
   * @param nodes  the queue of node
   * @return the node that has min distance
   */
    public Graph.Node<N,E> getMin(Queue<Graph.Node<N,E>> nodes) {
        Graph.Node<N,E> min = null;
        for (Graph.Node<N,E> node : nodes) {
            if (min == null) {
                min = node;
            } else {
                if (ShortDistance(node) < ShortDistance(min)) {
                    min= node;
                }
            }
        }
        return min;
    }

  /**
   *  get Shortest distance to a node.
   * @param destination  the node to get to 
   * @return int the distance
   */
    public int ShortDistance(Graph.Node<N,E> destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    /**
     *  Returns the path from the source to the selected node.
     * @param node the node to get to 
     * @return the list of nodes on the path
     */
    public LinkedList<Graph.Node<N,E>> getPath(Graph.Node<N,E> node) {
        LinkedList<Graph.Node<N,E>> path = new LinkedList<Graph.Node<N,E>>();
        Graph.Node<N,E> step = node;
        if (map.get(step) == null) {
            return null;
        }
        path.add(step);
        while (map.get(step) != null) {
            step = map.get(step);
            path.add(step);
        }
        // Reverse it to get the right order
        Collections.reverse(path);
        return path;
    }
   /**
     *   Print the distances
     */
    public void printDistance() {
        Iterator<Graph.Node<N,E>> iterator = nodes.iterator(); 
        while (iterator.hasNext()) {
   	    Graph.Node<N,E> node = iterator.next();
    	System.out.println(node.getData()+": "+distance.get(node));
       }
    }

  /**
   *  Print the path to a node.
   *  @param node  the node to get to
   */
    public void printPath(Graph.Node<N,E> node) {
    	LinkedList<Graph.Node<N,E>> path = getPath(node);
    	while (path.size()>1) {
    	  System.out.print(path.removeFirst().getData());
          System.out.print("-");
          }
          System.out.print(path.removeFirst().getData());
          System.out.print("\n");
    }

   /**
     *   Print all paths. 
     */
     public void printAllPath() {
         for (Graph.Node<N,E> node : nodes) {
        if (!node.equals(startnode)){
        LinkedList<Graph.Node<N,E>> path = getPath(node);
        while (path.size()>1) {
          System.out.print(path.removeFirst().getData());
          System.out.print("-");
          }
          System.out.print(path.removeFirst().getData());
          System.out.print("\n");
         }
        }
     }


}