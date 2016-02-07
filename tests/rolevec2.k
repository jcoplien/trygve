context C1 {
    public C1(List<AClass> myList) {
        CollectionRole = myList;
    }

    public void trigger() {
        CollectionRole[0].bar();
    }

    role [] CollectionRole {
        public void bar() {
            for (int i=0; i < size(); i++) {
                CollectionRole[i].doBar();
            }
        }

        private void doBar() {}
    }
    requires {
        void foo();
        int size() const;
    }
}

class AClass {
    public void foo() {}
}

{
    new C1(new List<AClass>()).trigger();
}