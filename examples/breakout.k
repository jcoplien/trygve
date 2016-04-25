// UI library
class Vector {
	public double x, y;

	public Vector(double vx, double vy){ setxy(vx, vy); }
	public void set(Vector v){ setxy(v.x, v.y); }
	public void setxy(double vx, double vy){ x = vx.clone; y = vy.clone; }

	public Vector add(Vector b) { return new Vector(x + b.x, y + b.y); }
	public Vector sub(Vector b) { return new Vector(x - b.x, y - b.y); }
	public double length(){ return Math.sqrt(x*x + y*y); }
	public double length2(){ return x*x + y*y; }

	public double dot(Vector b){ return x * b.x + y * b.y; }

	public void reflect(Vector normal){
		double d = dot(normal);
		x = x - 2.0 * d * normal.x;
		y = y - 2.0 * d * normal.y;
	}

	public Vector normal(){
		double len = length();
		return new Vector(x / len, y / len);
	}

	public Vector scale(double s) { return new Vector(s*x, s*y); }
	public Vector flip(){ return new Vector(-x, -y); }
}

class Mouse {
	public Vector position;
	public Mouse(){ position = new Vector(0.0, 0.0); }
}

class MousePanel extends Panel {
	public Mouse mouse;

	public MousePanel(){
		Panel();
		mouse = new Mouse();
	}

	public boolean handleEvent(Event event) {
		if (event.id == Event.MOUSE_MOVE) {
			if(event.x != null){
				mouse.position.setxy(event.x, event.y);
			}
		}
		return false;
	}
}

context Display {
	private MousePanel panel_;
	private Frame frame_;
	private Vector size_;

	public Display(){
		size_ = new Vector(640.0, 480.0);
		panel_ = new MousePanel();

		frame_ = new Frame("");
		frame_.add("Center", panel_);
		frame_.resize(floor(size_.x) + 20, floor(size_.y) + 20);

		frame_.setVisible(true);
	}

	public Vector size() { return size_; }
	public Mouse mouse() { return panel_.mouse; }

	public void clear()   { panel_.removeAll(); }
	public void repaint() { panel_.repaint(); }

	public void fg(Color color) { panel_.setForeground(color); }

	private int floor(double v){ return v.toInteger(); }

	public void rect(Vector center, Vector halfsize) {
		//BUG: should be fillRect, doesn't work as intended for some reason
		panel_.drawRect(
			floor(center.x - halfsize.x),
			floor(center.y - halfsize.y),
			floor(2.0*halfsize.x),
			floor(2.0*halfsize.y));
	}
	public void oval(Vector center, Vector halfsize) {
		//BUG: fillOval x0, y0 are wrong, currently need to offset them
		panel_.fillOval(
			floor(center.x - 2.0*halfsize.x),
			floor(center.y - 2.0*halfsize.y),
			floor(2.0*halfsize.x),
			floor(2.0*halfsize.y));
	}
	public void circle(Vector center, double radius) {
		//BUG: fillOval x0, y0 are wrong, currently need to offset them
		panel_.fillOval(
			floor(center.x-2.0*radius),
			floor(center.y-2.0*radius),
			floor(2.0*radius),
			floor(2.0*radius));
	}
}

// Game implementation

// State defintion
class Ball {
	public Vector position;
	public Vector velocity;
	public double radius;
	public double mass;
	public boolean dead;

	public Ball(){
		dead = false;

		position = new Vector(320.0, 320.0);
		velocity = new Vector(
			(Math.random()*2.0-1.0)*50.0,
			(Math.random()*2.0-1.0)*50.0
		);
		radius = 10.0;
		mass = 1.0;
	}

	public double x() const { return position.x; }
	public double y() const { return position.y; }
}

class Block {
	public Vector position;
	public Vector halfsize;
	public boolean dead;

	public Block(Vector center){
		dead = false;

		position = center;
		halfsize = new Vector(30.0, 10.0);
	}

	public double width()  const { return 2.0 * halfsize.x; }
	public double height() const { return 2.0 * halfsize.x; }

	public double left()   const { return position.x - halfsize.x; }
	public double right()  const { return position.x + halfsize.x; }
	public double top()    const { return position.y - halfsize.y; }
	public double bottom() const { return position.y + halfsize.y; }
}

class Wall {
	public Vector position;
	public Vector halfsize;
	public boolean deadly;

	public Wall(Vector center, Vector hsize){
		position = center;
		halfsize = hsize;
		deadly = false;
	}

	public double width()  const { return 2.0 * halfsize.x; }
	public double height() const { return 2.0 * halfsize.x; }

	public double left()   const { return position.x - halfsize.x; }
	public double right()  const { return position.x + halfsize.x; }
	public double top()    const { return position.y - halfsize.y; }
	public double bottom() const { return position.y + halfsize.y; }
}

class Paddle {
	public Vector position;
	public Vector halfsize;

	public Paddle(){
		position = new Vector(0.0, 0.0);
		halfsize = new Vector(30.0, 10.0);
	}

	public double width()  const { return 2.0 * halfsize.x; }
	public double height() const { return 2.0 * halfsize.x; }

	public double left()   const { return position.x - halfsize.x; }
	public double right()  const { return position.x + halfsize.x; }
	public double top()    const { return position.y - halfsize.y; }
	public double bottom() const { return position.y + halfsize.y; }
}

context State {
	public Paddle paddle;

	public List<Ball>  balls;
	public List<Block> blocks;
	public List<Wall>  walls;

	public Paddle getPaddle() const { return paddle; }
	public List<Ball> getBalls() const { return balls; }
	public List<Wall> getWalls() const { return walls; }
	public List<Block> getBlocks() const { return blocks; }


	public State(){
		paddle = new Paddle();
		balls = new List<Ball>();
		blocks = new List<Block>();
		walls = new List<Wall>();
	}
}

// Physics system

context Physics {
	public Physics(State state, Display display){
		StateRole = state;
		Debug = display;
	}

	role StateRole {
		public void integrate(double dt) {
			List<Ball> balls = getBalls();
			for(Ball A: balls){
				Integrator.integrate(dt, A.position, A.velocity);
			}
		}
		public void collide(double dt) {
			List<Ball> balls = getBalls();
			List<Wall> walls = getWalls();
			List<Block> blocks = getBlocks();

			for(int i = 0; i < balls.size; i++){
				Ball A = balls.get(i);

				for(int k = 0; k < blocks.size; k++){
					Collider.ball_block(dt, A, blocks.get(k));
				}

				for(int k = i.clone + 1; k < balls.size; k++){
					Collider.ball_ball(dt, A, balls.get(k));
				}

				for(int k = 0; k < walls.size; k++){
					Collider.ball_wall(dt, A, walls.get(k));
				}

				Collider.ball_paddle(dt, A, getPaddle());
			}
		}

		public void collect(){
			List<Ball> balls = getBalls();
			int i = 0;
			while(i < balls.size){
				Ball ball = balls.get(i);
				if(ball.dead){
					balls.remove(ball);
				} else {
					i++;
				}
			}

			List<Block> blocks = getBlocks();
			while(i < blocks.size){
				Block block = blocks.get(i);
				if(block.dead){
					blocks.remove(block);
				} else {
					i++;
				}
			}
		}
	} requires {
		public Paddle getPaddle() const;
		public List<Ball> getBalls() const;
		public List<Wall> getWalls() const;
		public List<Block> getBlocks() const;
	}

	role Integrator {
		public void integrate(double dt, Vector position, Vector velocity){
			position.x = position.x + velocity.x * dt;
			position.y = position.y + velocity.y * dt;
		}
	}

	role Collider {
		private double clamp(double v, double min, double max){
			if(v < min){ return min; }
			if(v > max){ return max; }
			return v;
		}

		public void ball_ball(double dt, Ball a, Ball b) {
			Vector delta = a.position.sub(b.position);
			if(delta.length < a.radius + b.radius){
				Debug.point(a.position.add(b.position).scale(0.5));

				// separate based on mass
				Vector pen = delta.normal.scale(a.radius + b.radius - delta.length);

				double ra = a.mass / (a.mass + b.mass);
				double rb = b.mass / (a.mass + b.mass);
				a.position = a.position.add(pen.scale(rb));
				b.position = b.position.add(pen.scale(-ra));

				// momentum transfer
				Vector normal = delta.normal;
				double p = 2.0 * (a.velocity.dot(normal) - b.velocity.dot(normal)) / (a.mass + b.mass);

				Vector av = a.velocity.sub(normal.scale(p * a.mass));
				Vector bv = b.velocity.add(normal.scale(p * b.mass));

				a.velocity.set(av);
				b.velocity.set(bv);
			}
		}

		public void ball_block(double dt, Ball a, Block b) {
			double cx = clamp(a.x, b.left, b.right);
			double cy = clamp(a.y, b.top, b.bottom);
			Vector closest = new Vector(cx, cy);


			Vector delta = a.position.sub(closest);
			if(delta.length < a.radius){
				Debug.point(closest);
				Vector pen = delta.normal.scale(a.radius - delta.length);
				a.position = a.position.sub(pen);
				a.velocity.reflect(delta.normal);

				//TODO: trigger a collision handler
				b.dead = true;
			}
		}

		public void ball_wall(double dt, Ball a, Wall b) {
			double cx = clamp(a.x, b.left, b.right);
			double cy = clamp(a.y, b.top, b.bottom);
			Vector closest = new Vector(cx, cy);

			Vector delta = a.position.sub(closest);
			if(delta.length < a.radius){
				Debug.point(closest);
				Vector pen = delta.normal.scale(a.radius - delta.length);
				a.position = a.position.add(pen);
				a.velocity.reflect(delta.normal);

				//TODO: trigger a collision handler
				if(b.deadly){
					a.dead = true;
				}
			}
		}

		public void ball_paddle(double dt, Ball a, Paddle b) {
			double cx = clamp(a.x, b.left, b.right);
			double cy = clamp(a.y, b.top, b.bottom);
			Vector closest = new Vector(cx, cy);

			Vector delta = a.position.sub(closest);
			if(delta.length < a.radius){
				Debug.point(closest);
				Vector pen = delta.normal.scale(a.radius - delta.length);
				a.position = a.position.add(pen);
				a.velocity.reflect(delta.normal);
			}
		}
	}

	//role CollisionHandler {} requires {
	//	void hit_block(Ball ball, Block block);
	//	void hit_wall(Ball ball, Wall wall);
	//}

	public void update(double dt){
		StateRole.integrate(dt*0.5);
		StateRole.collide(dt);
		StateRole.integrate(dt*0.5);
		StateRole.collect();
	}

	role Debug {
		public void point(Vector p){
			fg(new Color(255, 0, 0));
			circle(p, 3.0);
		}
	} requires {
		public void fg(Color color);
		public void circle(Vector center, double radius);
	}
}

// Render system
context Render {
	public Render(State state, Display display){
		StateRole = state;
		UI = display;
	}

	stageprop StateRole {
		public void render(){
			for(Ball ball : getBalls()){
				UI.ball(ball);
			}
			for(Wall wall : getWalls()){
				UI.wall(wall);
			}
			for(Block block : getBlocks()){
				UI.block(block);
			}

			UI.paddle(getPaddle());
		}
	} requires {
		public Paddle getPaddle() const;
		public List<Ball> getBalls() const;
		public List<Wall> getWalls() const;
		public List<Block> getBlocks() const;
	}

	role UI {
		public void ball(Ball ball){
			fg(new Color(144, 195, 212));
			circle(ball.position, ball.radius);
		}

		public void block(Block block){
			fg(new Color(212, 195, 144));
			rect(block.position, block.halfsize);
		}

		public void wall(Wall wall){
			if(wall.deadly) {
				fg(new Color(255, 0, 0));
			} else {
				fg(new Color(212, 144, 195));
			}
			rect(wall.position, wall.halfsize);
		}

		public void paddle(Paddle paddle){
			fg(new Color(144, 212, 195));
			rect(paddle.position, paddle.halfsize);
		}
	} requires {
		public Vector size();
		public void fg(Color color);
		public void rect(Vector center, Vector halfsize);
		public void circle(Vector center, double radius);
	}

	public void render(){
		StateRole.render();
	}
}

context Breakout {
	private State state_;
	private Physics physics_;

	private Display display_;
	private Render render_;

	public Breakout(){
		state_ = new State();

		display_ = new Display();

		physics_ = new Physics(state_, display_);
		render_ = new Render(state_, display_);

		setup_level1();
	}

	/*
	public void hit_block(Ball ball, Block block){
		//TODO: add counting
		state_.blocks.remove(block);
	}

	public void hit_wall(Ball ball, Wall wall){
		//TODO: restart when zero balls
		if(wall.deadly){
			state_.balls.remove(ball);
		}
	}
	*/

	public void setup_level1(){
		state_.balls.add(new Ball());
		state_.balls.add(new Ball());
		state_.balls.add(new Ball());

		double wallsize = 20.0 / 2.0;

		state_.walls.add(new Wall(
			new Vector(0.0, display_.size.y/2.0),
			new Vector(wallsize, display_.size.y/2.0)
		));

		state_.walls.add(new Wall(
			new Vector(display_.size.x, display_.size.y/2.0),
			new Vector(wallsize, display_.size.y/2.0)
		));

		state_.walls.add(new Wall(
			new Vector(display_.size.x/2.0, 0.0),
			new Vector(display_.size.x/2.0, wallsize)
		));

		Wall zone = new Wall(
			new Vector(display_.size.x/2.0, display_.size.y),
			new Vector(display_.size.x/2.0, wallsize)
		);
		zone.deadly = true;
		state_.walls.add(zone);

		Block block = new Block(new Vector(0.0, 0.0));

		for(double yi = 0.0; yi < 3.0; yi = yi + 1.0){
			double y = yi * (block.height + 10.0) + wallsize * 3.0;
			for(double x = wallsize * 2.0; x < display_.size.x - wallsize * 2.0; x = x + block.width + 10.0){
				state_.blocks.add(new Block(new Vector(x, y)));
			}
		}

		state_.paddle.position.setxy(
			display_.size.x/2.0,
			display_.size.y - wallsize - state_.paddle.halfsize.y);
	}

	public void run(){
		while(true){
			double dt = 0.033;

			display_.clear();

			state_.paddle.position.x = display_.mouse.position.x.clone;

			render_.render();
			physics_.update(dt);

			display_.repaint();

			Thread.sleep(13);
		}
	}
}

new Breakout().run()

