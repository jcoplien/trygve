class AClass {
	public void classf1() { }
	public void classf2() { }
}

class BClass extends AClass {
	private void classf2() { }
}

context Context {
	role Role1 {
		public void role1f1() { }
		public void role1f2() { classf2() }
	} requires {
		void classf2()
	}
	public Context() {
		Role1 = new BClass()
	}
}

new Context()
