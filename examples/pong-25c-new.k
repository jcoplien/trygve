// D for DATA
class PongStage extends Panel {
// Panel has an extent on the screen is the only library class that knows how to draw on it. 
// The name Stage is chosen to honor the DCI theatre metaphor. An alternative was Arena.
	int XSIZE = 1000;
	int YSIZE = 600;
	private Frame frame_;
	public PongStage() {
		Panel();
		frame_	= new Frame("Pong");
		frame_.add("Center", this);
		frame_.setSize(xsize(), ysize());
		frame_.show();
	}
	public int xsize() const { return XSIZE }
	public int ysize() const { return YSIZE }
	public int stageLeft() const { return 0 }
	public int stageTop() const { return 0 }
	public int stageRight() const { return XSIZE }
	public int stageBottom() const { return YSIZE }	
	public Frame frame() { return frame_ }}
class PongBall extends Point {
// The ball is visible on the screen while it moves and bounces.
// Its reference Point is the upper/left corner of the circumscribed rectangle
// as is usual in Java shapes.
// It knows its diameter and velocity, but displaying it is part of the system behavior and
// is done in a DCI context.
	PongVelocity velocity_;
	int diameter_;
	public PongBall(int x, int y) {
		Point(x, y);
		velocity_ = new PongVelocity(7, 7);
		diameter_=20;
	}
	public int diameter() const { return diameter_}
	public int ballLeft() const { return x }
	public int ballTop() const { return y }
	public int ballRight() const { return x + diameter_ }
	public int ballBottom() const { return y + diameter_ }
	public PongVelocity velocity() const { return velocity_ }
	public void setVelocity(PongVelocity velocity) { velocity_=velocity }
}
class PongVelocity extends Point {
// This service class knows what it means to bounce the ball off a vertical or horizontal surface. 
	public PongVelocity(int x, int y) {
		Point(x, y);
	}
	public void bounceHorizontal() {setXY(-x, y)  }
	public void bounceVertical() {setXY(x,- y) }
}
// C for CONTEXT
context Pong {  
	public Pong() {
		PongStage panel = new PongStage();
		CANVAS = panel;
		STAGE = panel;
// MAESTRO is void by default and need not have a rolePlayer since  this role is pure behavior
		BALL = new PongBall(50, 50);
		PADDLE = new Point(450, 560); 
	} 
	public void start() {
		MAESTRO.run()
	}
// I for	INTERACTON
role MAESTRO {
	public void run() {
		do {
			STAGE.clear(); 
			PADDLE.step();
			BALL.step();
			Thread.sleep(20)
		} while (true)	
	}
}
role CANVAS {
	public void setColor(Color col); 
	public void fillOval(int x, int y, int h, int w);
	public void drawOval(int x, int y, int h, int w);
	public void drawRect(int x, int y, int h, int w);
	public void fillRect(int x, int y, int h, int w);
	public void repaint();
} requires {
	void setColor(Color col); 
	void fillOval(int x, int y, int h, int w);
	void drawOval(int x, int y, int h, int w);
	void drawRect(int x, int y, int h, int w);
	void fillRect(int x, int y, int h, int w);
	void repaint();
}
role STAGE {
			public int maxX() const { return xsize() }
			public int maxY() const { return ysize() }
//		public int extendedBottom {return stageBottom() + xsize( ) }
		public int extendedBottom {return stageBottom() + 200} //was 800
		public void drawPaddle() {
			setColor(Color.black);
			drawRect (PADDLE.getX()-1, PADDLE.getY()-1, PADDLE.width()+1, PADDLE.thickness()+1);
			setColor(Color.red);
			fillRect (PADDLE.getX(), PADDLE.getY(), PADDLE.width(), PADDLE.thickness());
			repaint();
		}
		public void bounceOffStageWall() {
			if ((BALL.velocity().getX > 0) && (BALL.ballRight() >= stageRight())) 
					BALL.velocity().bounceHorizontal; // bounce off right wall
			if ((BALL.velocity().getX < 0) && (BALL.ballLeft() <= stageLeft()))
					BALL.velocity().bounceHorizontal;// bounce off left wall
			if ((BALL.velocity().getY <0) && (BALL.ballTop() <= stageTop()))
					BALL.velocity().bounceVertical(); // bounce off ceiling
		}
		public int stageLeft();
		public int stageTop();
		public int stageRight();
		public int stageBottom();
		public void clear();
	} requires {
		void fillOval(int x, int y, int h, int w);
		void drawOval(int x, int y, int h, int w);
		void drawRect(int x, int y, int h, int w);
		void fillRect(int x, int y, int h, int w);
		int xsize() const;
		int ysize() const; 
		int stageLeft();
		int stageTop();
		int stageRight();
		int stageBottom();
		void clear();
		void repaint();
		void setColor(Color color); 
	}
	role BALL {	
		public void step() { 
			STAGE. bounceOffStageWall();
			PADDLE.bounceOffPaddle();
			setXY(getX() + velocity().getX, getY() + velocity().getY); 
			draw();
//			CANVAS.drawCircle(getX(), getY(), diameter());
//			STAGE.draw();
		} 
		public void draw() {
			CANVAS.setColor(Color.blue); 
			CANVAS.fillOval (getX, getY, diameter, diameter ); 
//			CANVAS.fillOval (getX(), getY(), diameter(), diameter() );
			CANVAS.repaint();

		}
		public int getX();
		public int getY();
		public int diameter();
		public velocity();
		public int ballLeft();
		public int ballTop();
		public int ballRight();
		public int ballBottom();

	} requires {
		int getX() const;
		int getY() const; 
		int ballLeft();
		int ballTop();
		int ballRight();
		int ballBottom();
		PongVelocity velocity();
		void setXY(int x, int y);
		int diameter();
	}
role PADDLE {
			public int thickness() const { return 10 }
			public int width() const { return 100 }
			public void draw() { 
			CANVAS.setColor(Color.black);
			CANVAS.drawRect (getX()+1, getY()+1, width()+1, thickness()+1);
			CANVAS.setColor(Color.red);
			CANVAS.fillRect (getX()+2, getY()+2, width(), thickness());
			CANVAS.repaint();
		}
		public void step()  { 
			int mouseX;
			PointerInfo info; 
			mouseX = MouseInfo.getPointerInfo().getLocation().getX ;
			if (mouseX < (width()/2) ) mouseX = width()/2; 
			if ( mouseX > (STAGE.stageRight() - (width()/2)) )  mouseX = STAGE.stageRight()-(width()/2); 
			setXY( mouseX - (width()/2), getY );
			draw()
		}
		public int paddleTop() {return getY - thickness()}
			public void moveTo(Point p) { setXY(p.x, p.y) }
			public boolean containsX(int x){
			return (x > getX) && (x < (getX + width()))
		}
			public int vertical()  { return getY - thickness() } 
		public void bounceOffPaddle() {  
			if (
				(BALL.velocity().getY >0) 
				&& (containsX(BALL.getX()) )
				&& ( 
						(BALL.ballBottom() >= paddleTop() ) 
						&& ((BALL.ballTop() <= STAGE.stageBottom())
								|| (BALL.ballTop() >= (STAGE.extendedBottom() )))
							)
					)
							BALL.velocity().bounceVertical;
		} 
 		public int getX();
		public int getY();
	} requires {
		void setXY(int x, int y);
//		int stageRight();
		int getX();
		int getY()
	} 
}


new Pong().start()



