import java.util.*;
public class TestDijkstra {
  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
    GraphImplementation<String, Integer> g = new GraphImplementation<String, Integer>();
   // NodeProcessor<String, Integer> processor = new NodeProcessor<String, Integer>();
    Graph.Node<String, Integer> node0 = g.addNode("A");
    Graph.Node<String, Integer> node1 = g.addNode("B");
    Graph.Node<String, Integer> node2 = g.addNode("C");
    Graph.Node<String, Integer> node3 = g.addNode("D");
    Graph.Node<String, Integer> node4 = g.addNode("E");
    Graph.Node<String, Integer> node5 = g.addNode("F");
    Graph.Node<String, Integer> node6 = g.addNode("G");
    Graph.Edge<String, Integer> edge1 = g.addEdge(4, node0, node1);
    Graph.Edge<String, Integer> edge2 = g.addEdge(5, node0, node2);
    Graph.Edge<String, Integer> edge3 = g.addEdge(2, node0, node3);
    Graph.Edge<String, Integer> edge4 = g.addEdge(8, node2, node4);
    Graph.Edge<String, Integer> edge5 = g.addEdge(10, node3, node5);
    Graph.Edge<String, Integer> edge6 = g.addEdge(3, node4, node6);
    Graph.Edge<String, Integer> edge7 = g.addEdge(6, node1, node4);
    Graph.Edge<String, Integer> edge8 = g.addEdge(1, node4, node5);
    Graph.Edge<String, Integer> edge9 = g.addEdge(3, node5, node6);
      System.out.println(g.validateGraph());
      // System.out.println(g);

    Dijkstra<String, Integer> d = new Dijkstra(g);
    d.execute(node0);
     System.out.println("Distance: ");
    d.printDistance();
    System.out.print("Path from A to F: ");
    d.printPath(node5);
    System.out.println("All Paths:");
    d.printAllPath();
  }

  private class NodeProcessor<N,E> implements Graph.Processor<N, E> {
    public boolean processEdge(Graph.Edge<N,E> edge) {
       System.out.println("processEdge:"+edge.getData());
    return false;
    }

    public boolean preProcessNode(Graph.Node<N,E> node) {
      System.out.println("preProcessNode:"+node.getData());
      return false;
    }
    public boolean postProcessNode(Graph.Node<N,E> node) {
     System.out.println("postProcessNode:"+node.getData());
     return false;
    }
  }
}