context Base {
    int c;
    role Parent {
        public void Blah(){
            System.out.println("Blah " + DoIt().toString());
        }
    }  requires {
        public int DoIt();
    }

    public int DoIt(){
        return c;
    }

    public Base(int ch, Base p){
        c = ch.clone;
        Parent = p;
        Parent.Blah();
    }

    public void Act(){ Parent.Blah(); }
}

{ 
    Base b = new Base(1,null);
    Base child = new Base(2,b);
    child.Act();
}