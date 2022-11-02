class Infinity { public int value() { return 999999 } }

class Nameable {
	public String name() { return null }
}

class Node extends Nameable {
	public Node(String name) {
		name_ = name.clone
	}
	public String name() { return name_ }

	private String name_
}


class NodeGraphInfo {
	public NodeGraphInfo() {
		pathsFrom_ = new Map<Nameable, Map<Nameable, int> >()
		nodes_ = new Set<Nameable>()
	}
	
	public void addNode(Nameable n) {
		pathsFrom_.atPut(n, new Map<Nameable, int>())
		nodes_.add(n)
	}
	public void addPathBetween(Nameable m, Nameable n) {
		Map<Nameable, int> distanceMap = pathsFrom_.at(m)
		distanceMap.atPut(n, new Infinity().value)
	}
	public int distanceBetween(Nameable m, Nameable n) {
		Map<Nameable, int> distanceMap = pathsFrom_.at(m)
		return distanceMap.at(n)
	}
	public Set<Nameable> pathsFrom(Nameable n) {
		Map<Nameable, int> distanceMap = pathsFrom_.at(n)
		return distanceMap.keys
	}
	public void setDistanceFor(Nameable from, Nameable to, int d) {
		Map<Nameable, int> distanceMap = pathsFrom_.at(from)
		distanceMap.atPut(to, d)
	}

	public void setStartAndEnd(Nameable start, Nameable endNode) {
		start_ = start; endNode_ = endNode
	}

	public Nameable startNode() { return start_ }
	public Nameable endNode() { return endNode_ }

	Set<Nameable> nodes() { return nodes_ }
	Map<Nameable, Map<Nameable, int> > pathsFrom_
	Set<Nameable> nodes_
	Nameable start_, endNode_
}

context Dijkstra {
	public List<Nameable> unvisitedNeighborsOf(Nameable node) {
		List<Nameable> allNeighbors = graph_.pathsFrom(node)
		List<Nameable> retval = new List<Nameable>()
		for (Nameable n : allNeighbors) {
			if (unvisiteds_.contains(n)) {
				retval.add(n)
			}
		}
		return retval
	}

	public Dijkstra(NodeGraphInfo graph) {
		graph_ = graph
		tentativeDistances_ = new Map<Nameable, int>()
		pathTo = new Map<Nameable, Nameable>()
	}

	private Nameable unvisitedNodeWithMinimumDistance() {
		int min = new Infinity().value
		Nameable retval = null
		for (Nameable n: unvisiteds_) {
			if (tentativeDistances_.at(n) < min) {
				min = tentativeDistances_.at(n)
				retval = n
			}
		}
		return retval
	}

	private void recur(Nameable current) {
		Set<Nameable> currentsUnvisitedNeighbors = unvisitedNeighborsOf(current)
		Nameable smallestDistanceNode = null
		int myTentativeDistance = tentativeDistances_.at(current)
		for (Nameable n : currentsUnvisitedNeighbors) {
			int distanceIncrement = graph_.distanceBetween(current, n)
			int itsDistance = tentativeDistances_.at(n)
			int net_distance = myTentativeDistance + distanceIncrement

			if (net_distance < itsDistance) {
				tentativeDistances_.atPut(n, net_distance)
				smallestDistanceNode = n.clone
				pathTo.atPut(n, current)
			}
		}

		unvisiteds_.remove(current)

		if (unvisiteds_.contains(graph_.endNode)) {
			recur(unvisitedNodeWithMinimumDistance())
		}
	}

	public void doit() {
		for (Nameable n : graph_.nodes) {
			tentativeDistances_.atPut(n, new Infinity().value)
		}
		tentativeDistances_.atPut(graph_.startNode, 0)
		pathTo.atPut(graph_.startNode, null)
		unvisiteds_ = graph_.nodes
		recur(graph_.startNode)
	}

	public Nameable pathTo(Nameable i) {
		return pathTo.at(i)
	}

	Map<Nameable, int> tentativeDistances_
	NodeGraphInfo graph_
	Set<Nameable> unvisiteds_
	Map<Nameable, Nameable> pathTo
}

{
	NodeGraphInfo graph = new NodeGraphInfo()

	/* Aliases to help set up the grid. Grid is of Manhattan form:
	 *
	 *		a - 2 - b - 3 -	c
	 *	  	|			|			|
	 *	   1			2			1
	 *	   |			|			|
	 *		d - 1 -	e - 1 -	f
	 *	   |						|
	 *		2						4
	 *    |						|
	 *		g - 1 -	h - 2 -	i
	 */

	Node a, b, c, d, e, f, g, h, i
	graph.addNode(a = new Node("a"))
	graph.addNode(b = new Node("b"))
	graph.addNode(c = new Node("c"))
	graph.addNode(d = new Node("d"))
	graph.addNode(e = new Node("e"))
	graph.addNode(f = new Node("f"))
	graph.addNode(g = new Node("g"))
	graph.addNode(h = new Node("h"))
	graph.addNode(i = new Node("i"))

	for (Nameable node1: graph.nodes) {
		for (Nameable node2: graph.nodes) {
			if (node1 is node2) {
				continue
			} else
				graph.setDistanceFor(node1, node2, new Infinity().value)
		}
	}

	graph.setDistanceFor(a, b, 2)
	graph.setDistanceFor(b, c, 3)
	graph.setDistanceFor(c, f, 1)
	graph.setDistanceFor(f, i, 4)
	graph.setDistanceFor(b, e, 2)
	graph.setDistanceFor(e, f, 1)
	graph.setDistanceFor(a, d, 1)
	graph.setDistanceFor(d, g, 2)
	graph.setDistanceFor(g, h, 1)
	graph.setDistanceFor(h, i, 2)
	graph.setDistanceFor(d, e, 1)

	graph.setStartAndEnd(a, i)

	Dijkstra dijkstra = new Dijkstra(graph)
	dijkstra.doit


   List<String> pathComponents = new List<String>()
	Nameable walker = graph.endNode
	do {
		pathComponents.add(walker.name)
		walker = dijkstra.pathTo(walker)
	} while (walker != null)

	for (int j = pathComponents.size - 1; j >= 0; --j) {
	   System.out.print(pathComponents.at(j)).print(" ")
	}

	System.out.println
}

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
a d g h i 
*/
