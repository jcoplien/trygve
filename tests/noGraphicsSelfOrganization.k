// In "The Self-Organization Game" we ask people to stand in a
// circle and to silently choose two other players in their
// mind. Then we ask them to self-organize so that each player
// is the same distance from the two players that he or she
// picked. This program simulates that game.

class DPoint {
	public DPoint(double x, double y) { x_ = x.clone; y_ = y.clone }
	public double x() { return x_.clone() }
	public double y() { return y_.clone() }
	public double distanceTo(DPoint p) { return Math.sqrt(((p.x() - x_) ** 2) + ((p.y() - y_) ** 2)) }
	public double x_, y_
}

class Player {
	public Player(String name, double x, double y) { centre_ = new DPoint(x, y); name_ = name.clone }
	public DPoint center() { return centre_ }
	public double x() { return centre_.x }
	public double y() { return centre_.y }
	public void moveTo(DPoint c) { centre_ = c }
	public Color color() { return theColor_ }
	public void setColor(Color c) { theColor_ = new Color(c.getRed(), c.getBlue(), c.getGreen()) }
	public String name() { return name_ }

	DPoint centre_
	String name_
	Color theColor_
}

class DisplayClass {
	public DisplayClass(DPoint size) { 	}
	public void drawLine(int x1, int y1, int x2, int y2) { }
	public void clear()   { }
	public int floor(double v) { return v.toInteger(); }
	public void repaint() { }
	public void oval(DPoint center, double halfsize) { }
	public void setColor(Color c) { }
}

context Game {
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

	public void play() {
		boolean done = false
		while (!done) {
			done = true
			for (CurrentPlayer : players_) {
				if (CurrentPlayer.moved()) done = false
			}
		}
		Display.draw
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
					somethingMoved = checkAndAdjustPosition()
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
				//Thread.sleep(50)
			}
		}

		private boolean checkAndAdjustPosition() {
			boolean somethingMoved = false
			List<Player> neighborList = Neighbors.of(this)
			Player player1 = neighborList[0], player2 = neighborList[1]
			double distance1 = distanceTo(player1), distance2 = distanceTo(player2);
			if (Math.abs(distance1 - distance2) > GameData.DELTA) {
				PlayerMoveCount.incrementCountForPlayer
				moveBetweenNeighbors(player1, player2)
				somethingMoved = true

				// tail recursive
				for (Player p : myWatchers()) {
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
			boolean moved = false
			moveTo(xy)
			do {
				moved = false
				for (Player p: GameData.players()) {
					if (p is not this) {
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

		private List<Player> myWatchers() { return GameData.watchersOf(this) }
		public double distanceTo(Player p) { return center().distanceTo(p.center) }
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

	private void setUpWatchers() {
		watchers_ = new Map<String, List<Player>>()
		for (Player p : players_) {
			String playerName = p.name
			watchers_.atPut(playerName, new List<Player>())
			for (Player p2 : neighbors_.at(playerName)) {
				if (p2.name == p.name) watchers_[playerName].add(p2)
			}
		}
	}

	public double DELTA() { return 0.10 }
	public int moveCount() { return moveCount_++ }
	public boolean isMoving(Player p) { return isMoving_[p.name] }
	public void setIsMoving(Player p, boolean tf) { isMoving_.atPut(p.name(), tf) }
	public List<Player> players() { return players_ }
	public List<Player> watchersOf(Player p) { return watchers_[p.name] }

	Map<String, List<Player>> watchers_, neighbors_
	Map<String, boolean> isMoving_
	Display display_
	List<Player> players_
	int moveCount_ = 0
}

{
	int NumberOfPlayers = 50
	Math.setSeed(12345)
	
	List<Player> players = new List<Player>()
	for (int i = 0; i < NumberOfPlayers; i++) {
		double randX = Math.random() + 0.1, randY = Math.random() + 0.1
		Player p = new Player((i + 65).to1CharString(), randX, randY)
		Color newColor = new Color(Math.random(), Math.random(), Math.random())
		p.setColor(newColor)
		players.add(p)
	}

	// Connect each player randomly to two other distinct players
	Map<String, List<Player>> neighbors = new Map<String, List<Player>>()
List<Player> l = new List<Player>()
l.add(new Player("Joe DiMaggio", 100.0, 100.0))
neighbors["A"] = l
System.out.println(neighbors["A"][0].name())
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

	// Test the distances
	for (Player p: players) {
		List<Player> neighborsi = neighbors[p.name]
		Player q = neighborsi[0]
		double d0 = p.center().distanceTo(neighborsi[0].center())
		double d1 = p.center().distanceTo(neighborsi[1].center())
		System.out.print(d0).print(", ").print(d1).print(", ").println(Math.abs(d0 - d1))
		d0.assert(Math.abs(d0 - d1) < 0.1, "delta assertion")
	}

	System.out.println("exit")

	display.assert (false, "assertion certain to fail")
}

/* GOLD
0 warnings, 0 errors.
___________________________________________________________
Joe DiMaggio
0.35654840943026317, 0.26301284020404714, 0.09353556922621603
0.20185369085191227, 0.19966720622361364, 0.002186484628298624
0.5289699813220208, 0.4485973049743664, 0.08037267634765438
0.27322352300186975, 0.36843893286777313, 0.09521540986590338
0.35634767402975936, 0.4530084947259263, 0.09666082069616694
0.09480101210382838, 0.06262031439542612, 0.03218069770840226
0.13249573446310398, 0.16384741470081807, 0.03135168023771409
0.17010576563831772, 0.25531677441234535, 0.08521100877402762
0.3309380355051147, 0.3714801433381038, 0.040542107832989116
0.3830237104324711, 0.4743567019120335, 0.09133299147956242
0.3550307578312968, 0.357067712996398, 0.0020369551651012174
0.3834820130375948, 0.3340778445719936, 0.04940416846560125
0.20071423517231646, 0.29624014670247234, 0.09552591153015588
0.47575049304396333, 0.38305761443027814, 0.09269287861368519
0.5706111349614383, 0.5072539333250919, 0.0633572016363464
0.4117065755745246, 0.3164133507957469, 0.09529322477877772
0.4520534974956639, 0.43640611460293427, 0.015647382892729655
0.2460855498086048, 0.32197579384276864, 0.07589024403416383
0.2437758136575243, 0.336850758412189, 0.09307494475466468
0.27359668083483785, 0.1954454269523587, 0.07815125388247915
0.2328613431025575, 0.3207383939509007, 0.08787705084834321
0.3808106187099993, 0.29565616138314915, 0.08515445732685017
0.25132464093753953, 0.17345169447111256, 0.07787294646642698
0.5492161320539922, 0.45057349373077016, 0.09864263832322206
0.2125131296738815, 0.274763098368686, 0.06224996869480451
0.2420262926804538, 0.1618666563629638, 0.08015963631749001
0.2247606488508885, 0.1435112164461866, 0.0812494324047019
0.37845765458854613, 0.2906431076532063, 0.08781454693533985
0.2350292046303468, 0.32212302854668035, 0.08709382391633355
0.1618666563629638, 0.13279803699472162, 0.029068619368242177
0.3799245975796405, 0.3033450652191839, 0.07657953236045656
0.5126359178594241, 0.5100428399022584, 0.0025930779571656792
0.49085364707646134, 0.4114434694928522, 0.07941017758360913
0.6710719271622938, 0.7658046504912381, 0.09473272332894433
0.21220518892833448, 0.29328802883602423, 0.08108283990768975
0.09589895548422682, 0.18581427015969507, 0.08991531467546825
0.2684919529948288, 0.2956991431733187, 0.027207190178489882
0.6016565799311596, 0.5510140961399592, 0.0506424837912004
0.20534181715079958, 0.2526133710502458, 0.047271553899446206
0.27407507582484836, 0.34182060230653216, 0.0677455264816838
0.32882625067618615, 0.24303850653385026, 0.08578774414233589
0.3908922090872029, 0.2968942668285979, 0.09399794225860503
0.37429806242540326, 0.2930786341871493, 0.08121942823825395
0.46079773442924055, 0.5370513712152284, 0.07625363678598784
0.31272255463901727, 0.25665522589763984, 0.05606732874137743
0.26337277221594896, 0.3527939187293829, 0.08942114651343397
0.5168853295744166, 0.5700987589929972, 0.05321342941858065
0.16563611577253332, 0.24293844442390403, 0.07730232865137071
0.11028496951577275, 0.1970454938177117, 0.08676052430193894
0.32224948708503603, 0.403809425515636, 0.08155993843059994
exit

Line 347: Assertion failed: assertion certain to fail
	In script `Object.assert'
*/
