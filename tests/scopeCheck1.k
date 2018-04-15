class Demo {
    private int x_
    int getX() {
        return x_
    }
    int setX(int newX) {
        x_ = newX.clone
        Demo d = new Demo();
        d.x_
		  return x_
    }
}

new Demo().x_


/* GOLD:
line 9: Cannot access expression `d.x_' with `private' access qualifier across instances: violates encapsulation.
line 14: Cannot access expression `[new Demo].x_' with `private' access qualifier.
0 warnings, 2 errors.
___________________________________________________________
*/
