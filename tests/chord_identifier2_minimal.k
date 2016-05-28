class Note {
    public Note(String name) {
        name_ = name.clone;
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
    stageprop LowNote {
        public double notePosition() const { return position() }
    } requires {
        double position() const;
    }

    stageprop HighNote {
        public double notePosition() const { return position() }
    } requires {
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
        double lowPos = LowNote.notePosition();
        double highPos = HighNote.notePosition();
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

    }

    public String name() {
        return "Chord::name";
    }
}

{
    List<Note> notes = new List<Note>();
    notes.add(new Note("C"));
    notes.add(new Note("Eb"));
    notes.add(new Note("G"));

    System.out.println(notes.get(0).name());
    Chord chord = new Chord(notes);
}

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
C
*/
