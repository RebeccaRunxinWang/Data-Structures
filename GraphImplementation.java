import java.util.*;

/**
 *  An implementation of the Graph interface built on an adjacency matrix.
 *  
 *  @author Rebecca Wang
 *  @version CSC 212, April 19, 2018
 *
 *  @param <N>  the type of the data to be associated with a node
 *  @param <E>  the type of the data to be associated with an edge
 */
public class GraphImplementation<N,E> implements Graph<N,E> {
  /** A set of nodes. */
  private Set<Node<N,E>> nodes = new HashSet<Graph.Node<N,E>>();

  /** A set of nodes. */
  private Set<Edge<N,E>> edges = new HashSet<Graph.Edge<N,E>>();

  /**
   *  Get a new set of all of the nodes in the graph.  Changes made to the
   *  returned set will not be reflected in the graph, though changes made to
   *  particular nodes in the set may be.
   *
   *  @return a new set of the nodes in the graph
   */
   @Override
  public Set<Node<N,E>> getNodes() {
  	Set<Node<N,E>> newnodes = nodes;
    return newnodes;
  }

  /**
   *  Get a new set of all of the edges in the graph.  Changes made to the
   *  returned set will not be reflected in the graph, though changes made to
   *  particular edges in the set may be.
   *
   *  @return a new set of the edges in the graph
   */
  public Set<Edge<N,E>> getEdges() {
  	Set<Edge<N,E>> newedges = edges;
    return newedges;
  }


  /**
   *  Find a particular edge given its tail and head.
   *
   *  @param tail  the tail ("from" node) of the edge to be found
   *  @param head  the head ("to" node) of the edge to be found
   *  @return      the edge, or null if there is no such edge
   */
  public Edge<N,E> findEdge(Node<N,E> tail, Node<N,E> head){
    Iterator<Edge<N,E>> iterator = edges.iterator(); 
    while (iterator.hasNext()) {
   	Edge<N,E> edge = iterator.next();
   	if (edge.getTail().equals(tail) && edge.getHead().equals(head)){
   	    return edge;
   	}
    }
   return null;
  }

  /**
   *  Short-cut to get the number of nodes in the graph.  This is
   *  equivalent to, though not necessarily implemented as,
   *  getNodes().size().
   *
   *  @return the number of nodes in the graph
   */
  public int numNodes(){
  	return nodes.size();
  }

   /**
   *  Short-cut to get the number of edges in the graph.  This is
   *  equivalent to, though not necessarily implemented as,
   *  getEdges().size().
   *
   *  @return the number of edges in the graph
   */
  public int numEdges(){
  	return edges.size();
  }

  /**
   *  Adds a node to the graph.  The new node will have degree 0.
   *
   *  @param data  the data to be associated with the node
   *  @return      the new node
   */
  public Node<N,E> addNode(N data){
  	Set<Edge<N,E>> outgoingedges = new HashSet<Graph.Edge<N,E>>();
  	Set<Edge<N,E>> incomingedges = new HashSet<Graph.Edge<N,E>>(); 
  	Node<N,E> node = new AMNode(data, outgoingedges, incomingedges);
  	nodes.add(node);
  	return node;
  }

  /**
   *  Adds an edge to the graph, given the tail and head nodes.
   *  @param data  the data of the edge to be added
   *  @param tail  the tail ("from" node) of the edge to be added
   *  @param head  the head ("to" node) of the edge to be added
   *  @return the new edge
   *  
   */
  @SuppressWarnings("unchecked")
  public Edge<N,E> addEdge(E data, Node<N,E> tail, Node<N,E> head){
  	 Edge<N,E> edge = new AMEdge(data, tail, head);
  	 ((AMNode)tail).addOutgoingEdge(edge);
  	 ((AMNode)head).addIncomingEdge(edge);
  	 edges.add(edge);
  	 return edge;
  }

 /**
   *  Removes a node and all its incident edges from the graph.
   *
   *  @param node  the node to be removed
   *  @throws Error  if the node does not belong to this graph
   *  
   */
  @SuppressWarnings("unchecked")
  public void removeNode(Node<N,E> node){
  	Set<Edge<N,E>> out = node.getOutgoingEdges();
    Iterator<Edge<N,E>> outiterator = out.iterator(); 
    while (outiterator.hasNext()) {
   	    Edge<N,E> edge = outiterator.next();
       	((AMNode)node).deleteOutgoingEdge(edge);
    }
    Set<Edge<N,E>> in = node.getIncomingEdges();
    Iterator<Edge<N,E>> initerator = in.iterator(); 
    while (initerator.hasNext()) {
   	    Edge<N,E> edge = initerator.next();
       	((AMNode)node).deleteIncomingEdge(edge);
    }
    if (nodes.contains(node)) {
  	nodes.remove(node);
    } else {
      throw new java.lang.Error("No such node.");
    }
  }

  /**
   *  Removes an edge from the graph.
   *
   *  @param edge  the edge to be removed
   *  @throws Error  if the edge does not belong to this graph
   *  
   */
  @SuppressWarnings("unchecked")
  public void removeEdge(Edge<N,E> edge){
  	if (edges.contains(edge)){
      edges.remove(edge);
      ((AMNode)edge.getHead()).deleteIncomingEdge(edge);
      ((AMNode)edge.getTail()).deleteOutgoingEdge(edge);
  	} else {
      throw new java.lang.Error("No such edge.");
  	}
  }

  /** 
   *  Removes an edge from the graph given its tail and head nodes.
   *
   *  @param tail  the tail ("from" node) of the edge to be removed
   *  @param head  the head ("to" node) of the edge to be removed
   *  @throws Error  if either the tail or head nodes do not belong to this grap
   *  
   */
  @SuppressWarnings("unchecked")
  public void removeEdge(Node<N,E> tail, Node<N,E> head){
  	Set<Edge<N,E>> out = tail.getOutgoingEdges();
  	Iterator<Edge<N,E>> iterator = out.iterator(); 
  	boolean removed = false;
    while (iterator.hasNext()) {
      Edge<N,E> edge = iterator.next();
      if (edge.getHead().equals(head)){
   	    ((AMNode)tail).deleteOutgoingEdge(edge);
        ((AMNode)head).deleteIncomingEdge(edge);
        removed = true;
   	  }
    }
   if (!removed) {
   	 throw new java.lang.Error("Error when removing.");
   }
  }

   /**
   *  Verifies the internal consistency of the graph structure.
   *
   *  @return  true if consistent, false otherwise
   */
  public boolean validateGraph(){
    Iterator<Edge<N,E>> iterator = edges.iterator(); 
    while (iterator.hasNext()) {
   	Edge<N,E> edge = iterator.next();
   	if(!edge.getHead().getIncomingEdges().contains(edge)) {
          		return false;
   }
    if(!edge.getTail().getOutgoingEdges().contains(edge)) {
    	    	return false;
    }
}
   return true;
}
 
   /**
   *  Returns the set of nodes in the graph that are not in group.  This is
   *  equivalent to getNodeSet().removeAll(group), but may be implemented
   *  differently.
   *
   *  @param group  a set of nodes
   *  @return       all of the nodes of the graph not present in group
   */
  public Set<Node<N,E>> otherNodes(Set<Node<N,E>> group){
  	Set<Node<N,E>> all = nodes;
  	all.removeAll(group);
  	return all;
  }

  /**
   *  Returns the set of nodes including all and only those nodes that
   *  are one end or the other of the given set of edges.
   *
   *  @param edges  a set of edges
   *  @return       a set of nodes that those edges are incident to
   * 
   */
  @SuppressWarnings("unchecked")
  public Set<Node<N,E>> endpoints(Set<Edge<N,E>> edges){
    Set<Node<N,E>> end = new HashSet<Graph.Node<N,E>>();
    Iterator iterator = edges.iterator(); 
    while (iterator.hasNext()) {
    Edge<N,E> edge = (Edge<N,E>)iterator.next();
    if (edge.getHead()!= null)  {
     end.add(edge.getHead());
    } 
    if (edge.getTail()!= null) {
    end.add(edge.getTail());
    }
    }
  	return end;
  }

  /**
   *  Performs a breadth-first traversal of a graph starting from the given
   *  node.  As each node or edge is processed the appropriate method in the
   *  processor is invoked.  The traversal will continue until all of the nodes
   *  have been traversed or the processor returns true, whichever happens
   *  first.  The idea is that if, for instance, we are doing a search we can
   *  stop as soon as we find the desired node by returning true from the
   *  processor, at which point the traversal will also return true.
   *
   *  @param start      the starting node for the traversal
   *  @param processor  the processing object to be applied to each node/edge
   *  @return           true if the processor ever returns true, false otherwise
   *  @throws Error if the starting node is not a node of this graph
   */
  public boolean breadthFirstTraversal(Node<N,E> start, Processor<N,E> processor){
  	Queue<Graph.Node<N,E>> queue = new LinkedList<Graph.Node<N,E>>();
    Set<Graph.Node<N,E>> visited = new HashSet<Graph.Node<N,E>>();
    if (!nodes.contains(start)) {
       throw new java.lang.Error("Starting Node is not in the graph.");
    } else {
    queue.add(start);
    while (! queue.isEmpty()) {
      Graph.Node<N,E> node = queue.remove();
      if (visited.contains(node)) continue;
      visited.add(node);
      if (processor.preProcessNode(node)) {
        return true;
      }
      for (Graph.Edge<N,E> edge : node.getOutgoingEdges()) {
        if (processor.processEdge(edge)) {
          return true;
        }
        queue.add(edge.getHead());
      }
      if (processor.postProcessNode(node)) {
        return true;
      }
    }
    return false;
    }
  }



  /**
   *  Performs a depth-first traversal of a graph starting from the given node.
   *  As each node or edge is processed the appropriate method in the processor
   *  is invoked.  The traversal will continue until all of the nodes have been
   *  traversed or the processor returns true, whichever happens first.  The
   *  idea is that if, for instance, we are doing a search we can stop as soon
   *  as we find the desired node by returning true from the processor, at which
   *  point the traversal will also return true.
   *
   *  @param start      the starting node for the traversal
   *  @param processor  the processing class to be applied to each node/edge
   *  @return           true if the processor ever returns true, false otherwise
   *  @throws Error if the starting node is not a node of this graph
   */
  public boolean depthFirstTraversal(Node<N,E> start, Processor<N,E> processor){
  	 LinkedList<Graph.Node<N,E>> stack = new LinkedList<Graph.Node<N,E>>();
  	  if (!nodes.contains(start)) {
       throw new java.lang.Error("Starting Node is not in the graph.");
   } else {
    stack.push(start);
    Set<Graph.Node<N,E>> visited = new HashSet<Graph.Node<N,E>>();
    while (! stack.isEmpty()) {
      Graph.Node<N,E> node = stack.pop();
      if (visited.contains(node)) continue;
      visited.add(node);
      if (processor.preProcessNode(node)) {
        return true;
      }
      for (Graph.Edge<N,E> edge : node.getOutgoingEdges()) {
        if (processor.processEdge(edge)) {
          return true;
        }
        stack.push(edge.getHead());
      }
      if (processor.postProcessNode(node)) {
        return true;
      }
    }
    return false;
  }
}

  /**
   *  Returns a string representation of the graph.
   *
   *  @return  a string representation of the graph
   *  
   */
  @SuppressWarnings("unchecked")
  public String toString(){
  	StringBuilder builder = new StringBuilder();
  	builder.append("Graph");
  	Iterator nodeiterator = nodes.iterator(); 
    while (nodeiterator.hasNext()) {
         Node<N,E> node = (Node<N,E>)nodeiterator.next();
         builder.append("\n  Node: ");
         builder.append(node.getData().toString());
         Set<Edge<N,E>> edges = node.getOutgoingEdges();
         Iterator edgeiterator = edges.iterator(); 
         while (edgeiterator.hasNext()) {
         	Edge<N,E> edge = (Edge<N,E>)edgeiterator.next();
         	builder.append("\n    To: ").append(edge.getHead().getData()).append(", ").append(edge.getData());
         }
    }
    return builder.toString();
  }

 /**
   *  Represents a node in a graph.
   *
   *  @param <N>  the type of the data to be associated with a node
   *  @param <E>  the type of the data to be associated with an edge
   */
  private class AMNode implements Graph.Node<N,E> {
    /** The data associated with this node. */
    private N data;
    /** An indexed list of nodes. */
    private Set<Edge<N,E>> outgoingedges = new HashSet<Graph.Edge<N,E>>();
    /** An indexed list of nodes. */
    private Set<Edge<N,E>> incomingedges = new HashSet<Graph.Edge<N,E>>();


    /**
     *  Create a node with the given data, and save its index.
     *
     *  @param data  the data associated with this node
     *  @param index the index of this node in the nodes list
     */
    public AMNode(N data, Set<Edge<N,E>> outgoingedges, Set<Edge<N,E>> incomingedges) {
      this.data = data;
      this.outgoingedges = outgoingedges;
      this.incomingedges = incomingedges;
    }
    
    /**
     *  Return the data associated with this node.
     *
     *  @return the data associated with this node
     */
    public N getData() {
      return data;
    }

    /**
     *  Return a new set of edges leaving this node, i.e., the set of all edges
     *  whose tail is this node.  Changes to the returned set will not be
     *  reflected in the graph, but changes to individual edges in the set may
     *  be.
     *
     *  @return the set of edges leaving this node
     */
    public Set<Edge<N,E>> getOutgoingEdges(){
    	Set<Edge<N,E>> newoutgoingedges = outgoingedges;
    	return newoutgoingedges;
    }

    /**
     *  Return a new set of edges entering this node, i.e., the set of all edges
     *  whose head is this node.  Changes to the returned set will not be
     *  reflected in the graph, but changes to individual edges in the set may
     *  be.
     *
     *  @return the set of edges entering this node
     */
    public Set<Edge<N,E>> getIncomingEdges(){
    	Set<Edge<N,E>> newincomingedges = incomingedges;
    	return newincomingedges;
    }

    /**
     *  Set the data associated with this node.
     *
     *  @param data the data associated with this node
     */
    public void setData(N data) {
    	this.data = data;
    }
     /**
     *  Add Incomingedge
     *
     *  @param edge the edge to add
     */
    public void addIncomingEdge(Edge<N,E> edge){
    	incomingedges.add(edge);
    }

     /**
     *  Add Outgoingedge
     *
     *  @param edge the edge to add
     */

    public void addOutgoingEdge(Edge<N,E> edge){
    	outgoingedges.add(edge);
    }
     /**
     *  Delete Incomingedge
     *
     *  @param edge the edge to delete
     */
    public void deleteIncomingEdge(Edge<N,E> edge){
    	incomingedges.remove(edge);
    }

    /**
     *  Delete Outgoingedge
     *
     *  @param edge the edge to delate
     */

    public void deleteOutgoingEdge(Edge<N,E> edge){
    	outgoingedges.remove(edge);
    }


    /**
     *  Is this node equal to that node, (i.e., are the contents equal
     *  to each other)?
     *
     *  @param that  the node to compare to this one
     *  @return true if the data associated with this is equal to the data
     *               associated with that
     */
    public boolean equals(Node<N,E> that) {
      return this.getData().equals(that.getData());
    }

    /**
     * Is this node equal to that object, (i.e., are the contents
     * equal to each other)?
     *
     *  @param that  the object t8o compare to this one
     *  @return true if that is a Node, and the data associated with
     *          this is equal to the data associated with that
     *  
     */
    @SuppressWarnings("unchecked")
    public boolean equals(Object that){
      return that instanceof Node && this.equals((Node<N,E>)that);
    }

    /**
     *  Returns a hash code for this node.  This must be defined such
     *  that if two nodes are equal (as determined by equals) then
     *  their hash codes are the same.
     *
     *  @return the hash code computed for this object
     */
    public int hashCode(){
    	return data.hashCode();
    }
  }

   /**
   *  Represents an edge in the graph.
   *
   *  @param <N>  the type of the data to be associated with a node
   *  @param <E>  the type of the data to be associated with an edge
   */
  private class AMEdge implements Graph.Edge<N,E> {
    /** The data associated with this edge. */
    private E data;

    /** The tail of this edge. */
    private Node<N,E> tail;

    /** The head of this edge. */
    private Node<N,E> head;

    /**
     *  Constructor.
     *
     *  @param data  the data associated with this edge
     *  @param tail  the tail node of this edge
     *  @param head  the head node of this edge
     */
    public AMEdge(E data, Node<N,E> tail, Node<N,E> head) {
      this.data = data;
      this.tail = tail;
      this.head = head;
    }
    
    /**
     *  Return the data associated with this edge.
     *
     *  @return the data associated with this edge
     */
    public E getData() {
      return data;
    }

    /**
     *  Return the tail ("from" node) of this edge.
     *
     *  @return the tail
     */
    public Node<N,E> getTail() {
      return tail;
    }

    /**
     *  Return the head ("to" node) of this edge.
     *
     *  @return the head
     */
    public Node<N,E> getHead() {
      return head;
    }

    /**
     *  Is this edge equal to that edge, (i.e., are the head, tail,
     *  and contents equal to each other)?
     *
     *  @param that  the edge to compare to this one
     *  @return true if the head, tail, and data associated with this
     *          are equal to the head, tail, and data associated with
     *          that
     */
    public boolean equals(Edge<N,E> that) {
      return this.data.equals(that.getData())
        && this.head.equals(that.getHead())
        && this.tail.equals(that.getTail());
    }

    /**
     * Is this edge equal to that object, (i.e., are the head, tail,
     * and contents equal to each other)?
     *
     *  @param that  the object to compare to this one
     *  @return true if that is an Edge, and the two edges are equal as determined by .equals(Edge<N,E> that)
     *  
     */
    @SuppressWarnings("unchecked")
    public boolean equals(Object that) {
      return that instanceof Edge && this.equals((Edge<N,E>)that);
    }

    /**
     *  Returns a hash code for this edge.  This must be defined such
     *  that if two edges are equal (as determined by equals) then
     *  their hash codes are the same.
     *
     *  @return the hash code computed for this object
     */
    public int hashCode() {
      return data.hashCode() * tail.hashCode() * head.hashCode();
    }

}


}



