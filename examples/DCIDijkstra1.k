class Infinity { public int value() { return 999999 } }

class Nameable {
	public String name() { return null }
}

class Thing extends Nameable {
	public Thing(String name) {
		name_ = name.clone
	}
	public String name() { return name_ }

	private String name_
}


class NodeGraphInfo {
	public NodeGraphInfo() {
		pathsFrom_ = new Map<Object, Map<Object, int> >()
		nodes_ = new Set<Nameable>()
	}
	public void addNode(Nameable n) {
		pathsFrom_.atPut(n, new Map<Object, int>())
		nodes_.add(n)
	}
	public void addPathBetween(Nameable m, Nameable n) {
		Map<Object, int> distanceMap = pathsFrom_.at(m)
		distanceMap.atPut(n, new Infinity().value)
	}
	public int distanceBetween(Object m, Object n) {
		Map<Object, int> distanceMap = pathsFrom_.at(m)
		return distanceMap.at(n)
	}
	public Set<Object> pathsFrom(Object n) {
		Map<Object, int> distanceMap = pathsFrom_.at(n)
		return distanceMap.keys
	}
	public void setDistanceFor(Nameable from, Nameable to, int d) {
		Map<Object, int> distanceMap = pathsFrom_.at(from)
		distanceMap.atPut(to, d)
	}
	public void setStartAndEnd(Nameable start, Nameable endNode) {
		start_ = start; endNode_ = endNode
	}

	public Nameable startNode() { return start_ }
	public Nameable endNode() { return endNode_ }
	public Set<Nameable> nodes() { return nodes_ }

	Map<Object, Map<Object, int> > pathsFrom_
	Set<Nameable> nodes_
	Nameable start_, endNode_
}

context Dijkstra {
	public Dijkstra(NodeGraphInfo graph) {
		TentativeDistances = new Map<Nameable, int>()
		Graph = graph
		EndNode = graph.endNode
		StartNode = graph.startNode
		pathTo_ = new Map<Nameable, Nameable>()
	}

	role Graph {
		public Set<Nameable> pathsFromNode(Node node) {
			return pathsFrom(node)
		}
		public int distanceBetween(Object n, Object n);
		Set<Object> nodes();
		Object endNode();
	} requires {
		Set<Nameable> pathsFrom(Object n);
		int distanceBetween(Object n, Object n);
		Set<Object> nodes();
		Object endNode();
	}

	role Node {
		public List<Nameable> unvisitedNeighbors() {
			List<Nameable> retval = new List<Nameable>()
			for (Nameable n : Graph.pathsFromNode(this))
				if (unvisiteds_.contains(n)) retval.add(n)
			return retval
		}
	}

	role Current {
		public List<Nameable> unvisitedNeighbors() {
			List<Nameable> retval = new List<Nameable>()
			for (Nameable n : Graph.pathsFromNode(this))
				if (unvisiteds_.contains(n)) retval.add(n)
			return retval
		}
	} requires {
		String name()
	}

	role EndNode {
		public List<Nameable> unvisitedNeighbors() {
			return new List<Nameable>()
		}
	}

	role StartNode {
	} requires {
		String name()
	}

	role TentativeDistances {
		public int at(Nameable n);
		void atPut(Nameable n, int d)
	} requires {
		int at(Nameable n);
		void atPut(Nameable n, int d)
	}

	role Neighbor {
		public int distanceTo(Node n) {
			int retval = Graph.distanceBetween(n, this)
			return retval
		}
		public int tentativeDistance() {
			return TentativeDistances.at(this)
		}
	} requires {
		String name()
	}

	Nameable unvisitedNodeWithMinimumDistance() {
		int min = new Infinity().value
		Nameable retval = null
		for (Nameable n: unvisiteds_) {
			if (TentativeDistances.at(n) < min) {
				min = TentativeDistances.at(n)
				retval = n
			}
		}
		return retval
	}

	void recur() {
		assert (Current != null)
		Set<Nameable> currentsUnvisitedNeighbors = Current.unvisitedNeighbors()
		assert (currentsUnvisitedNeighbors != null)
		int myTentativeDistance = TentativeDistances.at(Current)
		for (Neighbor: currentsUnvisitedNeighbors) {
			int distanceIncrement = Neighbor.distanceTo(Current)
			int itsDistance = Neighbor.tentativeDistance()
			int netDistance = myTentativeDistance + distanceIncrement

			if (netDistance < itsDistance) {
				TentativeDistances.atPut(Neighbor, netDistance)
				pathTo_.atPut(Neighbor, Current)
			}
		}

		unvisiteds_.remove(Current)

		if (unvisiteds_.contains(Graph.endNode)) {
			Current = unvisitedNodeWithMinimumDistance()
			recur()
		}
	}

	public void doit() {
		for (Nameable n : Graph.nodes)
			TentativeDistances.atPut(n, new Infinity().value)
			TentativeDistances.atPut(StartNode, 0)
		unvisiteds_ = Graph.nodes
		Current = StartNode
		recur()
	}

	public Nameable pathTo(Nameable i) {
		Nameable retval = pathTo_.at(i)
		return retval
	}

	Set<Object> unvisiteds_
	Map<Nameable, Nameable> pathTo_
}

{
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

	NodeGraphInfo graph = new NodeGraphInfo()
	Thing a, b, c, d, e, f, g, h, i
	graph.addNode(a = new Thing("a"))
	graph.addNode(b = new Thing("b"))
	graph.addNode(c = new Thing("c"))
	graph.addNode(d = new Thing("d"))
	graph.addNode(e = new Thing("e"))
	graph.addNode(f = new Thing("f"))
	graph.addNode(g = new Thing("g"))
	graph.addNode(h = new Thing("h"))
	graph.addNode(i = new Thing("i"))

	for (Nameable node1: graph.nodes)
		for (Nameable node2: graph.nodes)
			if (node1 is node2)
				continue
			else
				graph.setDistanceFor(node1, node2, new Infinity().value)


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

	// print
	List<String> pathComponents = new List<String>()
	for (Nameable walker = graph.endNode; !walker is graph.startNode; walker = dijkstra.pathTo(walker))
		pathComponents.add(walker.name)

	pathComponents.add(graph.startNode.name)

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