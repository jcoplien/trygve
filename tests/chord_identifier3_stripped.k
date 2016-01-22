class Note {
    public Note(String name) { name_ = name.clone; }
    public int position() const { return 10; }
    private String name_;
}
context Interval {
    stageprop LowNote {
       public double notePosition() const { return position() }
    } requires { int position() const; }
    stageprop HighNote {} requires { int position() const; }
    public Interval(LowNote lowNote) {
        System.out.print("Interval.Interval(")
                  .print(lowNote.notePosition())
                  .println(")");
        LowNote = lowNote;
        HighNote = lowNote
    }
    public int semitones() { return 0; }
}
context Chord {
    stageprop Root {} requires { int position() const; }
    role Notes {
        public void hasInterval(double semitones) {
            System.out.print("Chord.Root.hasInterval(")
                      .print(semitones)
                      .println(")");
            Interval interval = new Interval(Root);
        } 
    }
    public Chord(List<Note> notes) {
        Root = notes.get(0);
        Notes = notes;
    }
    public void f() {
        Notes.hasInterval(1.5)
    }
}

{
    System.out.println(new Interval(new Note("A")).semitones());

    List<Note> notes = new List<Note>();
    notes.add(new Note("A"));
    notes.add(new Note("A"));
    Chord chord = new Chord(notes);
    chord.f()
}

/* GOLD:
line 8: WARNING: substituting object of type `double' for `this.position()'.
___________________________________________________________
Interval.Interval(10.0)
0
Chord.Root.hasInterval(1.5)
Interval.Interval(10.0)
*/
