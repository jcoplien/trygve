class Note {
    public Note(String name) {  name_ = name.clone }
    public String name() const { return name_ }
    public int position() const { return 0 }
    private String name_;
}
context Interval {
    stageprop LowNote {} requires { int position() const }
    stageprop HighNote {} requires { int position() const }
    public Interval(LowNote lowNote, HighNote highNote) {
        LowNote = lowNote;
        HighNote = highNote;
    }
    public int semitones() { return 13 }
}

context Chord {
    stageprop Root {} requires {
        int position() const;
        String name() const;
    }

    role Notes {
        public boolean hasInterval(double semitones) {
            for (int i = 0; i < this.size(); i++) {
                System.out.print("i=").println(i);
                // NOTE: Cannot return, the loop keeps going
                if (true) return true;
            }
            return false;
        } 
    } requires {
        Note get(int pos);
        int size();
    }
    public Chord(List<Note> notes) {
        Root = notes.get(0);
        Notes = notes;
    }
    public String name() {
        System.out.println(Notes.hasInterval(1.5));
        return "Chord::name";
    }
}

// ----- Start -------------------------------

{
    System.out.println(new Interval(new Note("F"), new Note("Bb")).semitones());

    List<Note> notes = new List<Note>();
    notes.add(new Note("A"));
    notes.add(new Note("A"));
    Chord chord = new Chord(notes);
    System.out.println(chord.name());
}

/* GOLD:
line 18: WARNING: Script declaration for `name' has the same name as the Context script declared at line 40
___________________________________________________________
13
i=0
true
Chord::name
*/
