context Context {
	role auditor {
		public void audit() {
			list.atPut(2, "789")
		}
	}
	role list {
		public void add(String item) { atPut(incrementIndex(), item) }
		public String getAt(int theIndex) {
			String retval = at(translateIndex(theIndex));
			return at(translateIndex(theIndex))
		}
		private int translateIndex(int i) { return i + 1 }
		private int incrementIndex() {
			String theIndex = if (size() > 0) at(0) else null;
			int rawIndex = 1;
			if (theIndex is null) {
				atPut(0, "1")
			} else {
				rawIndex = theIndex.toInteger() + 1;
				atPut(0, rawIndex.toString())
			}
			return rawIndex
		}
		public int size()
	} requires {
		int size();
		String at(int theIndex);
		void atPut(int theIndex, String object)
	}
	public Context() { list = new String [16] }
	public void trigger() {
		list.add("123");
		list.add("456");
		auditor.audit();
		String j = list.getAt(1);
		System.out.format("list element is %s\n", j)
	}
}

new Context().trigger
