
{
	String [] players = new String[2]
	players[0] = "zero"
	players[1] = "one"
	Map<String,List<String>> neighbors = new Map<String,List<String>>()
	List<String> l1 = new List<String>()
	l1.add("first l1 item")
	l1.add("second l1 item")

	List<String> l2 = new List<String>()
	l2.add("first l2 item")
	l2.add("second l2 item")

	neighbors.atPut("zero", l1)
	neighbors.atPut("one", l2)

	for (String p: players) {
		List<String> neighborsi = neighbors[p]
		String q = neighborsi[0]
		System.out.println(q)
	}
}
