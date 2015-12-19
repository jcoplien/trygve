class Note {
    public Note(String name) {
        name_ = name;
        assert(position() > 0, "Invalid note: " + name);
    }

    public String name() const {
        return name_;
    }

    public int position() const {
        int pos = 0;
        if(name_ == "C")  pos = 1;
        if(name_ == "C#") pos = 2;
        if(name_ == "Db") pos = 2;
        if(name_ == "D")  pos = 3;
        if(name_ == "D#") pos = 4;
        if(name_ == "Eb") pos = 4;
        if(name_ == "E")  pos = 5;
        if(name_ == "F")  pos = 6;
        if(name_ == "F#") pos = 7;
        if(name_ == "Gb") pos = 7;
        if(name_ == "G")  pos = 8;
        if(name_ == "G#") pos = 9;
        if(name_ == "Ab") pos = 9;
        if(name_ == "A")  pos = 10;
        if(name_ == "A#") pos = 11;
        if(name_ == "Bb") pos = 11;
        if(name_ == "B")  pos = 12;
        return pos;
    }

    private String name_;
}

// -------------------------------------------------

context Interval {
    stageprop LowNote {} requires {
        int position() const;
    }

    stageprop HighNote {} requires {
        int position() const;
    }

    public Interval(LowNote lowNote, HighNote highNote) {
        LowNote = lowNote;
        HighNote = highNote;
    }

    public int semitones() {
        int lowPos = LowNote.position();
        int highPos = HighNote.position();
        int octaveOffset = if(lowPos > highPos) 12 else 0;

        return (highPos - lowPos + octaveOffset) / 2;
    }
}

// -------------------------------------------------

// The first note in the list will be the root.
context Chord {
    stageprop Root {} requires {
        int position() const;
        String name() const;
    }

    role Notes {
        public String quality() {
            return "Chord::Notes::quality";
        }

        public boolean hasInterval(double semitones) {
            for(int i = 1; i < this.size(); i++) {
                  int pos = Root.position();
                  System.out.println(pos);
                
                // NOTE: Cannot use Root for duck typing:
//                Interval i = new Interval(Root, this.get(i));
                
                // NOTE: Cannot return, the loop keeps going
//                if(i.semitones() == semitones) return true;
            }
            return false;
        } 
    } requires {
        Note get(int pos);
        int size();
    }

    public Chord(List<Note> notes) {
        assert(notes.size() > 1, "Chord must have more than one note.");
        System.out.println("made it through assert")
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
    notes.add(new Note("C"));
    notes.add(new Note("Eb"));
    notes.add(new Note("G"));

    Chord chord = new Chord(notes);
    System.out.println(chord.name());
}

/* GOLD:
line 65: WARNING: Method declaration for `name' may hide method declared at line 100
___________________________________________________________
2
made it through assert
1
1
false
Chord::name
*/
