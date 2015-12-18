class Note {
    public Note(String name) {
        name_ = name;
        assert(position() > 0.0, "Invalid note: " + name);
    }

    public String name() const {
        return name_;
    }

    // NOTE: I would have liked position to be an int,
    // but had problems with math in Interval::semitones.
    public double position() const {
        double pos = 0.0;
        if(name_ == "C")  pos = 1.0;
        if(name_ == "C#") pos = 2.0;
        if(name_ == "Db") pos = 2.0;
        if(name_ == "D")  pos = 3.0;
        if(name_ == "D#") pos = 4.0;
        if(name_ == "Eb") pos = 4.0;
        if(name_ == "E")  pos = 5.0;
        if(name_ == "F")  pos = 6.0;
        if(name_ == "F#") pos = 7.0;
        if(name_ == "Gb") pos = 7.0;
        if(name_ == "G")  pos = 8.0;
        if(name_ == "G#") pos = 9.0;
        if(name_ == "Ab") pos = 9.0;
        // NOTE: Compiler warning here
        // WARNING: Substituting double object for `10' in assignment to `pos'.
        if(name_ == "A")  pos = 10.0;
        if(name_ == "A#") pos = 11.0;
        if(name_ == "Bb") pos = 11.0;
        if(name_ == "B")  pos = 12.0;
        return pos;
    }

    private String name_;
}

context Interval {
    stageprop LowNote {} requires {
        double position() const;
    }

    stageprop HighNote {} requires {
        double position() const;
    }

    public Interval(Note lowNote, Note highNote) {
        LowNote = lowNote;
        HighNote = highNote;
    }

    public double semitones() {
        // NOTE: Parse error when calling methods in the if statement
        //double octaveOffset = if(LowNote.position() > HighNote.position()) 12.0 else 0.0;

        // Temp vars working
        double lowPos = LowNote.position();
        double highPos = HighNote.position();
        double octaveOffset = if(lowPos > highPos) 12.0 else 0.0;

        return (highPos - lowPos + octaveOffset) / 2;
    }
}

// -------------------------------------------------

// The first note in the list will be the root.
context Chord {
    stageprop Root {} requires {
        double position() const;
    }

    role Notes {
        public String quality() {
            return "";
        }

        public boolean hasInterval(double semitones) {
            for(int i = 1; i < this.size(); i++) {
                // NOTE: Need temp var
                Note note = this.get(i);
                System.out.println(note.name());
                return true;
            }
            return false;
        } 
    } requires {
        Note get(int pos);
        int size();
    }

    public Chord(List<Note> notes) {
        System.out.println("Chord::notes");
        System.out.println(notes.size());
        assert(notes.size() > 1, "Chord must have more than one note.");
//        Note root = notes.get(0);
//        System.out.println(root.name());
//        Root = root;
//        Notes = notes;
    }

    public String name() {
//        Notes.hasInterval(1.0);
        return "Chord::name";
    }
}

// ----- Start -------------------------------

{
    List<Note> notes = new List<Note>();
    notes.add(new Note("C"));
    notes.add(new Note("Eb"));
    notes.add(new Note("G"));

    System.out.println(notes.get(0).name());
    Chord chord = new Chord(notes);
//    System.out.println(chord.name());
}


// -----------------------------------------

/*
{
    Interval I = new Interval(A,C);
    System.out.print(A.name() + " to " + C.name() + ": ");
    System.out.print(I.semitones());
    System.out.println(" semitones.");

NOTE: What's happening here?
Object of type Note playing too many roles, including LowNote
	In Context Interval: HighNote.
	In Context Interval: LowNote.
Object of type Note playing too many roles, including HighNote
	In Context Interval: LowNote.
	In Context Interval: HighNote.
    Interval I2 = new Interval(C,A);
    System.out.print(C.name() + " to " + A.name() + ": ");
    System.out.print(I2.semitones());
    System.out.println(" semitones.");
}
*/

/* GOLD:
___________________________________________________________
C
Chord::notes
3
*/
