class Item (
   public void moveTo(int x, int y) {
   }
}

class RolePair {
   public RolePair(Item item1, Item item2, int strength) {
   }
}

context Graph {
    role [] Node {
      void adjustPosition() {
      }
    } requires {
       void moveTo(int x, int y)
    }
    role [] Connection {
      Connection myConnetionTo(Node n) {
         Connection retval;
         return retval;
      }
    }
    public Graph(List<RolePair> connections, List<Item> nodes) {
    }
}

{
   Graph g = new Graph(connections, nodes)

}
