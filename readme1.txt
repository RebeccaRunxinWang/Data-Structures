I used linkedlist in most places, and add some setter methods to Node class. I think each of the methods has the required performance characteristics: 

Graph methods:
getNodes(): O(1)
getEdges(): O(1)
findEdge(tail, head): iterating over edges, so O(e)
numNodes(): O(1)		
numEdges(): O(1)	
addNode(data): O(1)	
addEdge(data, tail, head): O(1)	
removeNode(node): iterating over edges, and iterator.remove() is O(1), so O(e)	
removeEdge(edge): O(1)		
removeEdge(tail, head):iterating over edges, things in if statemnt is O(1), so O(e)	
otherNodes(group): removeAll() in Hashset is O(n), so O(n)	
endpoints(edges): iterating over edges, O(e)		
breadthFirstTraversal(): while loop has O(n) and for loop has O(e), so O(n+e) (Assuming that the processor operations are O(1))
depthFirstTraversal(): while loop has O(n) and for loop has O(e), so O(n+e) (Assuming that the processor operations are O(1))

Node methods:
getData()： O(1)	
getOutgoingEdges(): O(1)
getIncomingEdges(): O(1)	
equals(): O(1)		
hashCode(): O(1)	

Edge methods:
getData(): O(1)		
getTail(): O(1)		
getHead(): O(1)	
equals(): O(1)		
hashCode(): O(1)	

