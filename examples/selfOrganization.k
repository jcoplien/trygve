// In "The Self-Organization Game" we ask people to stand in a
// circle and to silently choose two other players in their
// mind. Then we ask them to self-organize so that each player
// is the same distance from the two players that he or she
// picked. This program simulates that game.

class DPoint {
	public DPoint(double x, double y) { x_ = x.clone; y_ = y.clone }
	public double x() { return x_.clone() }
	public double y() { return y_.clone() }
	public double distanceTo(DPoint p) {
		double xdist = p.x() - x_
		double ydist = p.y() - y_
		return Math.sqrt(xdist*xdist + ydist*ydist)
	}
	public double x_, y_
}

class Player {
	public Player(String name, double x, double y) { centre_ = new DPoint(x, y); name_ = name.clone }
	public DPoint center() { return centre_ }
	public double x() { return centre_.x() }
	public double y() { return centre_.y() }
	public void moveTo(DPoint c) { centre_ = c }
	public Color color() { return theColor_ }
	public void setColor(Color c) { theColor_ = new Color(c.getRed(), c.getBlue(), c.getGreen()) }
	public String name() { return name_ }

	DPoint centre_
	String name_
	Color theColor_
}

class DisplayClass {
	public DisplayClass(DPoint size) {
		size_ = size;
		offset_ = new DPoint(0.0, 0.0);
		panel_ = new Panel();
		frame_ = new Frame("");
		frame_.add("Center", panel_);
		frame_.resize(floor(size_.x) + 20, floor(size_.y) + 40);
		frame_.setVisible(true);
	}
	public void drawLine(int x1, int y1, int x2, int y2) { panel_.drawLine(x1, y1, x2, y2) }
	public void clear()   { panel_.clear(); }
	public void repaint() { panel_.repaint(); }
	public int floor(double v) { return v.toInteger(); }
	public void oval(DPoint center, double halfsize) {
		panel_.fillOval(
			floor(center.x - halfsize + offset_.x),
			floor(center.y - halfsize + offset_.y),
			floor(2.0*halfsize),
			floor(2.0*halfsize));
	}
	public void setColor(Color c) { panel_.setColor(c) }

	private Panel panel_;
	private Frame frame_;
	private DPoint size_;

	private DPoint offset_;
}

context Game {
	public double DELTA() { return 0.10 }
	public int moveCount() { return moveCount_++ }

	int moveCount_ = 0

	public boolean isMoving(Player p) { return isMoving_.at(p.name()) }
	public void setIsMoving(Player p, boolean tf) { isMoving_.atPut(p.name(), tf) }

	public Game(List<Player> players, Map<String, List<Player>> neighbors, DisplayClass display) {
		neighbors_ = neighbors
		Neighbors = neighbors
		players_ = players
		Display = display
		GameData = this

		isMoving_ = new Map<String, boolean>()
		PlayerMoveCount = new Map<String,int>()
		for (Player p : players) {
			PlayerMoveCount.atPut(p.name(), 0)
			GameData.setIsMoving(p, false)
		}

		setUpWatchers()
	}

	private void setUpWatchers() {
		watchers_ = new Map<String, List<Player>>()
		for (Player p : players_) {
			String playerName = p.name
			watchers_.atPut(playerName, new List<Player>())
			for (Player p2 : neighbors_.at(playerName)) {
				if (p2.name == p.name) {
					watchers_.at(playerName).add(p2)
				}
			}
		}
	}

	Map<String, List<Player>> watchers_

	public List<Player> watchersOf(Player p) {
		return watchers_.at(p.name)
	}

	role PlayerMoveCount {
		public int moveCountForPlayer() { return this.at(name()) }
		public void incrementCountForPlayer() { atPut(name(), this.at(name()) + 1) }
		public void resetMoveCountForPlayer() { atPut(name(), 0) }
		String name() { return CurrentPlayer.name }
		int atPut(String p, int i);
	} requires {
		int at(String p);
		void atPut(String p, int i);
	}

	role GameData {
		public double DELTA();
		public List<Player> players();
		public int moveCount();
		public void setIsMoving(Player p, boolean b);
		public boolean isMoving(Player p);
		public List<Player> watchersOf(Player p);
	} requires {
		double DELTA();
		List<Player> players();
		int moveCount();
		void setIsMoving(Player p, boolean b);
		public boolean isMoving(Player p);
		public List<Player> watchersOf(Player p)
	}

	public List<Player> players() { return players_ }

	role Display {
		public void clear();
		public void repaint();
		public void setColor(Color c);
		private void drawConnections() {
			for (Player p : GameData.players) {
				List<Player> neighborList = Neighbors.of(p)
				setColor(p.color())
				Player neighbor1 = neighborList[0]
				Player neighbor2 = neighborList[1]
				drawLine(floor(p.x()*400), floor(p.y()*400),
						floor(neighbor1.x()*400), floor(neighbor1.y()*400))
				drawLine(floor(p.x()*400), floor(p.y()*400),
						floor(neighbor2.x()*400), floor(neighbor2.y()*400))
			}
		}
		public void draw() {
			for (Player p : GameData.players) {
				double x = p.x() * 400, y = p.y() * 400
				DPoint c = new DPoint(x, y)
				setColor(p.color())
				oval(c, PlayerSize())
			}
			drawConnections()
		}
		public double PlayerSize { return 10.0 }
	} requires {
		int floor(double v);
		void clear();
		void repaint();
		void setColor(Color c);
		void oval(DPoint center, double halfsize);
		void drawLine(int x1, int y1, int x2, int y2)
	}

	role CurrentPlayer {
		public boolean moved() {
			boolean somethingMoved = false
			if (!amIMoving()) {
				setIAmMoving(true)
				if (moveToGetUnstuck()) {
					somethingMoved = true
				} else {
					paintDisplay()
					somethingMoved = letNeighborsMove()
				}
				setIAmMoving(false)
			}
			return somethingMoved
		}

		private boolean moveToGetUnstuck() {
			boolean iMoved
			if (PlayerMoveCount.moveCountForPlayer > 30) {
				// move it to the center and get out - get it unstuck
				PlayerMoveCount.resetMoveCountForPlayer
				DPoint newCenter = new DPoint(0.7 + (Math.random() - 0.5)/2, 0.5 + (Math.random() - 0.5)/2)
				moveTo(nonoverlapping(newCenter))
				iMoved = true
			} else {
				iMoved = false
			}
			return iMoved
		}

		private void paintDisplay() {
			if (GameData.moveCount() % 5 == 0) {
				Display.clear
				Display.draw
				Display.repaint
				Thread.sleep(50)
			}
		}

		private boolean letNeighborsMove() {
			boolean somethingMoved
			List<Player> neighborList = Neighbors.of(this)
			Player player1 = neighborList[0]
			Player player2 = neighborList[1]
			double distance1 = distanceTo(player1), distance2 = distanceTo(player2);
			if (Math.abs(distance1 - distance2) > GameData.DELTA) {
				PlayerMoveCount.incrementCountForPlayer
				moveBetweenNeighbors(player1, player2)
				somethingMoved = true
			} else {
				somethingMoved = false
			}
			
			if (somethingMoved) {
				// tail recursive
				for (Player p : watchers()) {
					CurrentPlayer = p
					CurrentPlayer.moved
				}
			}

			return somethingMoved
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
			} else if (center().distanceTo(newYPoint) > 0) {
				midpoint = new DPoint((newYPoint.x() + ((INERTIA-1)*x())) / INERTIA,
												(newYPoint.y() + ((INERTIA-1)*y())) / INERTIA)
			}
			moveTo(nonoverlapping(midpoint));
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
		private DPoint nonoverlapping(DPoint xy) {
			moveTo(xy)
			boolean moved = false
			do {
				moved = false
				for (Player p: GameData.players()) {
					if (!(p is this)) {
						double distanceToPlayer = distanceTo(p)
						if (distanceToPlayer < Display.PlayerSize()/400) {
							// Overlap
							double xdist = Math.max(x() - p.x(), Display.PlayerSize()/100),
										ydist = Math.max(y() - p.y(), Display.PlayerSize()/100)
							double newX = x() + xdist + Math.random()/400
							double newY = y() + ydist + Math.random()/400
							moveTo( new DPoint(newX, newY) )
							moved = true
						}
					}
				}
			} while (moved)
			return center()
		}
		private List<Player> watchers() {
			return GameData.watchersOf(this)
		}

		public double distanceTo(Player p) { return center().distanceTo(p.center()) }
		private void setIAmMoving(boolean tf) { GameData.setIsMoving(this, tf) }
		private boolean amIMoving() { return GameData.isMoving(this) }
		public String name();
	} requires {
		double x();
		double y();
		DPoint center();
		void moveTo(DPoint c);
		String name();
		void setColor(Color c);
		Color color()
	}

	stageprop Neighbors {
		public List<Player> of(Player p) { return at(p.name()) }
	} requires {
		List<Player> at(String name) const
	}

	public void play() {
		boolean done = false
		while (!done) {
			done = true
			for (CurrentPlayer : players_) {
				if (CurrentPlayer.moved()) {
					done = false
				}
			}
		}
		Display.draw
	}

	private Map<String, List<Player> > neighbors_
	private Display display_
	private List<Player> players_
	private Map<String, boolean> isMoving_
}

{
	int NumberOfPlayers = 20
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
	Map<String, List<Player>> neighbors = new Map<String, List<Player>>()
	for (int i = 0; i < NumberOfPlayers; i++) {
		Player player = players[i]
		int otherPlayer1Index = 0, otherPlayer2Index = 0;
		do {
			otherPlayer1Index = (Math.random() * NumberOfPlayers).toInteger();
		} while (otherPlayer1Index == i || otherPlayer1Index >= NumberOfPlayers)
		do {
			otherPlayer2Index = (Math.random() * NumberOfPlayers).toInteger();
		} while (otherPlayer1Index == otherPlayer2Index || otherPlayer2Index == i ||
								otherPlayer1Index >= NumberOfPlayers)
		List<Player> neighborList = new List<Player>()
		Player otherPlayer1 = players[otherPlayer1Index], otherPlayer2 = players[otherPlayer2Index]
		neighborList.add(otherPlayer1)
		neighborList.add(otherPlayer2)
		neighbors.atPut(player.name(), neighborList)
	}

	double xsize = if (NumberOfPlayers > 50) 1500.0 else 640.0
	double ysize = if (NumberOfPlayers > 50)  600.0 else 480.0
	DisplayClass display = new DisplayClass(new DPoint(xsize, ysize))
	Game game = new Game(players, neighbors, display);
	game.play()
	System.out.println("exit (please close the graphical display)")
}
