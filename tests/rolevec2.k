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
   List<AClass> list = new List<AClass>();
   list.add(new AClass());
   new C1(list).trigger();
}

/* GOLD:
line 3: Roles in `CollectionRole' cannot be played by objects of type `AClass':
line 3: 	`size()' needed by Role `CollectionRole' does not appear in interface of `AClass'.
0 warnings, 2 errors.
___________________________________________________________
*/
