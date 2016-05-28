class Chainable {
    public Chainable() {}

    public Chainable chain() {
        System.out.println("chain");
        return this;
    }
}

context Chainer {
    role Chain {
        public Chain chainMe() {
            return chain();
        }
    } requires {
        Chain chain();
    }

    public Chainer() {
        Chain = new Chainable();
    }

    public Chainer chainIt() {
        Chain.chainMe().chainMe();
        return this;
    }
}

(new Chainer()).chainIt().chainIt()

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
chain
chain
chain
chain
*/
