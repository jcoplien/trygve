context AContext {
   void trigger(int i) {
      int j = 0;
      j = i++;
   }
}

{ AContext aContext = new AContext()  aContext.trigger(1) }
