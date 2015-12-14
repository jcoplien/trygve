class ReturnTester {
  public ReturnTester() { i_ = 3 }
  public int returnConstant() { return 1 }
  public int returnSum() { return 1 + 1 }
  public int returnRef() { return i_ }
  public int returnError() { return array_[i_++] }
  private int i_;
  private int [] array_
}

{
   ReturnTester rt = new ReturnTester()
   int i;
    i = rt.returnConstant();
    System.out.print("returnConstant: ").println(i)
    i = rt.returnSum();
    System.out.print("returnSum:      ").println(i)
    i = rt.returnRef();
    System.out.print("returnRef:      ").println(i)
    i = rt.returnError();
    System.out.print("returnError:    ").println(i)
}

/* GOLD:
___________________________________________________________
returnConstant: 1
returnSum:      2
returnRef:      3
line 6: Likely access of uninitialized array. Machine halted.
*/
