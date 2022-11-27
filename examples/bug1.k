class DPoint {
	public DPoint(double x, double y) {
		x_ = x.clone() ; y_ = y.clone()
	}
	public double x() { return x_ }
	public double y() { return y_ }
	
	public double x_, y_
}

class Player {
	
}


context Game {
	public boolean isMoving(Player p) {
		return isMoving_.containsKey(p)
	}
	public void setIsMoving(Player p, boolean tf) {
		isMoving_.atPut(p, tf)
	}

	role GameData {
		public boolean isMoving(Player p);
	} requires {
		boolean isMoving(Player p);
	}

	role CurrentPlayer {
		void startDebug() { }
		public boolean moved() {
			if (GameData.isMoving(this)) {
				return false
			} 
			return true
		}
	} 
	private Map<Player, boolean> isMoving_
}

{
	
}
