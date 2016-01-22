class Test {
  public double test() {
    double d1 = 6.0, d2 = 11.0
    int i = 7, j
    return (d1 - d2 + i) / 2
  }
}

{ double k = (new Test()).test()
  System.out.println(k) }