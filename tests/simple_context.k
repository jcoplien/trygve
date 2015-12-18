context Context {
   role R1 {
      void r1Method() { r2Method() }
      void r2Method() { }
   }
}

(new Context())
