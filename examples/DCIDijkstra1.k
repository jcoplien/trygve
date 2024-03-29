class DPoint {
	public DPoint(double x, double y) {
		x_ = x.clone ; y_ = y.clone
	}
	public double x() { return x_ }
	public double y() { return y_ }
	
	public double distanceTo(DPoint p) {
		double xdist = p.x() - x_
		double ydist = p.y() - y_
		return Math.sqrt(
			xdist*xdist + ydist*ydist
		)
	}

	public double x_, y_
}

class Vector {
	public double x, y;

	public Vector(double vx, double vy){ setxy(vx, vy); }
	public void set(Vector v){ setxy(v.x, v.y); }
	public void setxy(double vx, double vy){ x = vx.clone; y = vy.clone; }

	public Vector add(Vector b) { return new Vector(x + b.x, y + b.y); }
	public Vector sub(Vector b) { return new Vector(x - b.x, y - b.y); }
	public double length(){ return Math.sqrt(x*x + y*y); }
	public double length2(){ return x*x + y*y; }
}

class Player {
	public Player(String name, double x, double y) {
		center_ = new DPoint(x, y)
		name_ = name.clone
	}

	public DPoint center() { return center_ }

	public double x() { return center_.x() }
	public double y() { return center_.y() }

	public void moveTo(DPoint c) { center_ = c.clone }
	public Color color() { assert (!(theColor_ is null)); return theColor_ }
	public void setColor(Color c) { theColor_ = new Color(c.getRed(), c.getBlue(), c.getGreen()) }

	public String name() { return name_ }

	DPoint center_
	String name_
	Color theColor_
}

class DisplayClass {
	private Panel panel_;
	private Frame frame_;
	private Vector size_;

	private Vector offset_;

	public DisplayClass(){
		size_ = new Vector(640.0, 480.0);
		offset_ = new Vector(0.0, 0.0);
		panel_ = new Panel();

		frame_ = new Frame("");
		frame_.add("Center", panel_);
		frame_.resize(floor(size_.x) + 20, floor(size_.y) + 40);

		frame_.setVisible(true);
	}

	public Vector size() { return size_; }
	public Vector offset() { return offset_; }

	public void clear()   { panel_.clear(); }
	public void repaint() { panel_.repaint(); }

	private int floor(double v) { return v.toInteger(); }
	public void oval(DPoint center, double halfsize) {
		panel_.fillOval(
			floor(center.x - halfsize + offset_.x),
			floor(center.y - halfsize + offset_.y),
			floor(2.0*halfsize),
			floor(2.0*halfsize));
	}

	private void drawConnections(List<Player> players, Map<Player, List<Player>> neighbors) {
		for (Player p : players) {
			List<Player> neighborList = neighbors.at(p)
			if (!(neighborList is null)) {
				panel_.setColor(p.color())
				Player neighbor1 = neighborList.at(0)
				Player neighbor2 = neighborList.at(1)
				panel_.drawLine(floor(p.x()*400), floor(p.y()*400),
					floor(neighbor1.x()*400), floor(neighbor1.y()*400))
				panel_.drawLine(floor(p.x()*400), floor(p.y()*400),
					floor(neighbor2.x()*400), floor(neighbor2.y()*400))
			} else {
				System.out.print("neighbor list is null for ").println(p.name())
			}
		}
	}

	public void draw(List<Player> players, Map<Player, List<Player> > neighbors) {
		for (Player p : players) {
			double x = p.x() * 400
			double y = p.y() * 400
			DPoint c = new DPoint(x, y)
			if (!(p.color() is null)) panel_.setColor(p.color())
			oval(c, PlayerSize)
		}
		drawConnections(players, neighbors)
	}

	public void setColor(Color c) { panel_.setColor(c) }
	private double PlayerSize = 10.0
}

context Game {
	public double DELTA() { return 0.02 }
	public int moveCount() { return moveCount_++ }

	int moveCount_ = 0

	public Game(List<Player> players, Map<Player, List<Player>> neighbors, DisplayClass display) {
		neighbors_ = neighbors
		players_ = players
		Display = display
		GameData = this
		Map<Player,int> aMap = new Map<Player,int>()
		for (Player p : players) {
			aMap.atPut(p, 0)
			int check = aMap.at(p)
			assert (check == 0)
		}
		PlayerMoveCount = aMap
	}

	role PlayerMoveCount {
		public int moveCountForPlayer() {
			int retval = this.at(CurrentPlayer)
			if(retval is null) {
				retval = 0
			}
			return retval
		}
		public void incrementCountForPlayer() {
			this.atPut(CurrentPlayer, this.at(CurrentPlayer) + 1)
		}
		public void resetMoveCountForPlayer() {
			this.atPut(CurrentPlayer, 0)
		}
		public int atPut(Player p, int i);
	} requires {
		int at(Player p);
		void atPut(Player p, int i);
	}

	role GameData {
		public double DELTA();
		public Map<Player, List<Player> > neighbors();
		public List<Player> players();
		public int moveCount();
	} requires {
		double DELTA();
		Map<Player, List<Player> > neighbors();
		List<Player> players();
		int moveCount();
	}
	public Map<Player, List<Player>> neighbors() { return neighbors_ }
	public List<Player> players() { return players_ }

	role Display {
		public void draw(List<Player> players, Map<Player, List<Player>> neighbors);
		public void clear();
		public void repaint();
		public void setColor(Color c);
	} requires {
		void draw(List<Player> players, Map<Player, List<Player>> neighbors);
		void clear();
		void repaint();
		void setColor(Color c);
	}

	role CurrentPlayer {
		public boolean moved() {

			if (PlayerMoveCount.moveCountForPlayer() > 10) {
				// move it to the center and get out
				PlayerMoveCount.resetMoveCountForPlayer()
				moveTo(new DPoint(0.7, 0.5))
				return true
			}

			if (GameData.moveCount() % 5 == 0) {
				Display.clear()
				Display.draw(GameData.players(), GameData.neighbors())
				Display.repaint()
				Thread.sleep(200)
			}
			boolean retval = false
			List<Player> neighborList = GameData.neighbors().at(this)
			if (!(neighborList is null)) {
				Player player1 = neighborList.at(0)
				Player player2 = neighborList.at(1)
				double distance1 = distanceTo(player1), distance2 = distanceTo(player2);
				if (Math.abs(distance1 - distance2) > 0.1) {
					PlayerMoveCount.incrementCountForPlayer();
					moveBetweenNeighbors(player1, player2)
					CurrentPlayer = player1
					CurrentPlayer.moved()
					CurrentPlayer = player2
					CurrentPlayer.moved()
					retval = true
				}
 			} else {
				System.out.print("neighbor list is null for ").println(name())
			}
			return retval
		}

		private void moveBetweenNeighbors(Player p1, Player p2) {
			int INERTIA  =  4
			double x = suitableNewXFixingYAt(y(), p1, p2)

			DPoint newXPoint = new DPoint(x, y())
			double y = suitableNewYFixingXAt(x(), p1, p2)

			DPoint newYPoint = new DPoint(x(), y)
			DPoint midpoint
			if (center().distanceTo(newXPoint) < center().distanceTo(newYPoint)) {
				midpoint = new DPoint((newXPoint.x() + ((INERTIA-1)*x())) / INERTIA,
												(newXPoint.y() + ((INERTIA-1)*y()))  / INERTIA)
				moveTo(midpoint)
			} else if (center().distanceTo(newYPoint) > 0) {
				midpoint = new DPoint((newYPoint.x() + ((INERTIA-1)*x())) / INERTIA,
												(newYPoint.y() + ((INERTIA-1)*y())) / INERTIA)
				moveTo(newYPoint)
			}
		}
		private double suitableNewXFixingYAt(double y, Player p1, Player p2) {
			double retval = ((-p1.x()*p1.x()) + (2*p1.y()*y) - (p1.y()*p1.y()) + (p2.x()*p2.x()) -
										(2*p2.y()*y) + (p2.y()*p2.y())) / (2 * (p2.x()-p1.x()))
			if (retval < 0.1) retval = 0.1 + Math.random() / 10.0
			return retval
		}
		private double suitableNewYFixingXAt(double x, Player p1, Player p2) {
			double retval = ((2*p1.x()*x) - (p1.x()*p1.x()) - (p1.y()*p1.y()) - (2*p2.x()*x) +
					(p2.x()*p2.x()) + (p2.y()*p2.y())) / (2 * (p2.y() - p1.y()))
			if (retval < 0.1) retval = 0.1 + Math.random() / 10.0
			return retval
		}
		private double distanceTo(Player p) {
			return center().distanceTo(p.center())
		}
		String name();
	} requires {
		double x();
		double y();
		DPoint center();
		void moveTo(DPoint c);
		String name()
	}

	public void play() {
		Display.setColor(Color.green)
		boolean done = false
		while (!done) {
			done = true
			for (Player p : players_) {
				CurrentPlayer = p
				if (CurrentPlayer.moved()) {
					done = false
				}
			}
		}
		dump()
	}

	public void dump() {
		for(Player p : GameData.players()) {
			Player n1 = GameData.neighbors().at(p).at(0)
			Player n2 = GameData.neighbors().at(p).at(1)
			System.out.print("\"").print(p.name()).print("\" chose \"").print(n1.name()).print("\" and \"").print(n2.name()).println("\" as neighbors.")
		}
	}

	private Map<Player, List<Player> > neighbors_
	private Display display_
	private List<Player> players_
}

{
	int NumberOfPlayers = 12
	String [] names = new String[NumberOfPlayers]
	for (int i = 0; i < NumberOfPlayers; i++) {
		names[i] = (i + 65).to1CharString()
	}
	
	List<Player> players = new List<Player>()
	for (int i = 0; i < NumberOfPlayers; i++) {
		double randX = Math.random() + 0.1, randY = Math.random() + 0.1
		Player p = new Player(names[i], randX, randY)
		Color newColor = new Color(Math.random(), Math.random(), Math.random())
		p.setColor(newColor)
		players.add(p)
	}

	// Connect each player randomly to two other distinct players
	Map<Player, List<Player>> neighbors = new Map<Player, List<Player>>()
	List<Player> nullList = null
	for (Player p : players) neighbors.atPut(p, nullList);
	
	for (int i = 0; i < NumberOfPlayers; i++) {
		Player player = players.at(i)
		int otherPlayer1Index = 0
		do {
			otherPlayer1Index = (Math.random() * NumberOfPlayers).toInteger();
		} while (otherPlayer1Index == i || otherPlayer1Index >= NumberOfPlayers)
		int otherPlayer2Index = 0;
		do {
			otherPlayer2Index = (Math.random() * NumberOfPlayers).toInteger();
		} while (otherPlayer1Index == otherPlayer2Index || otherPlayer2Index == i ||
								otherPlayer1Index >= NumberOfPlayers)
		List<Player> neighborList = new List<Player>()
		Player otherPlayer1 = players.at(otherPlayer1Index), otherPlayer2 = players.at(otherPlayer2Index)
		assert (!(otherPlayer1 is null))
		assert (!(otherPlayer2 is null))
		neighborList.add(otherPlayer1)
		neighborList.add(otherPlayer2)
		neighbors.put(player, neighborList)
	}

	DisplayClass display = new DisplayClass()
	Game game = new Game(players, neighbors, display)
	game.dump()
	game.play()
	System.out.println("exit")
}
