class Note {
    public Note(String name) {
        name_ = name.clone;
        assert(position() > 0.0, "Invalid note: " + name);
    }

    public String name() const {
        return name_;
    }

    public double position() const {
        double pos = 0.0;
        if(name_ == "C")  pos = 1.0;
        if(name_ == "B#") pos = 1.0;
        if(name_ == "C#") pos = 2.0;
        if(name_ == "Db") pos = 2.0;
        if(name_ == "D")  pos = 3.0;
        if(name_ == "D#") pos = 4.0;
        if(name_ == "Eb") pos = 4.0;
        if(name_ == "E")  pos = 5.0;
        if(name_ == "Fb") pos = 5.0;
        if(name_ == "E#") pos = 6.0;
        if(name_ == "F")  pos = 6.0;
        if(name_ == "F#") pos = 7.0;
        if(name_ == "Gb") pos = 7.0;
        if(name_ == "G")  pos = 8.0;
        if(name_ == "G#") pos = 9.0;
        if(name_ == "Ab") pos = 9.0;
        if(name_ == "A")  pos = 10.0;
        if(name_ == "A#") pos = 11.0;
        if(name_ == "Bb") pos = 11.0;
        if(name_ == "B")  pos = 12.0;
        if(name_ == "Cb") pos = 12.0;
        return pos;
    }

    private String name_;
}

// -------------------------------------------------

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

    public Interval(LowNote lowNote, HighNote highNote) {
        LowNote = lowNote;
        HighNote = highNote;
    }

    public double semitones() {
        double lowPos = LowNote.notePosition();
        double highPos = HighNote.notePosition();
        double octaveOffset = if(lowPos > highPos) 12.0 else 0.0;

        return highPos - lowPos + octaveOffset;
    }
}

// -------------------------------------------------

context Chord {
    stageprop Root {
        public String chordName() const { return name() }
    } requires {
        double position() const;
        String name() const;
    }

    role Notes {
        public String quality() {
            boolean hasDominant = dominant().length() > 0;

            String sus2 = "";
            if(hasInterval(2.0) && hasDominant == false) { 
                sus2 = "sus2";
                if(isMinor()) sus2 = "add9";
                if(isMajor()) sus2 = "add9";
            }

            String sus4 = "";
            if(hasInterval(5.0) && !hasDominant) {
                sus4 = "sus";
                if(isMinor()) sus4 = "add11";
                if(isMajor()) sus4 = "add11";
            }

            return (if(isMinor()) "m" else "") + sus2 + sus4;
        }

        private boolean isMajor() {
            return hasInterval(4.0)
        }

        private boolean isMinor() {
            // NOTE: This expression doesn't work
            return hasInterval(3.0);
        }

        public String dominant() {
            String highest = "";
            String maj7 = if(hasInterval(11.0)) "maj" else "";

            if(hasInterval(7.0) && size() == 2) highest = "5";
            if(hasInterval(9.0)) highest = "6";
            if(hasInterval(10.0)) highest = "7";
            if(hasInterval(11.0)) highest = "7";

            // When restarting the interval check,
            // test if we have already detected a previous one.
            // (otherwise 9 may be confused with add9 for example)
            if (highest.length() > 0) {
                if(hasInterval(2.0)) highest = "9";
                if(hasInterval(5.0)) highest = "11";
                if(hasInterval(9.0)) highest = "13";
            }

            return maj7 + highest;
        }
        
        // TODO: Calculate #11 (Lydian chord)
        public String augmentations() {
            // NOTE: In the future, List::remove could be useful in this method.
            List<String> augs = new List<String>();

            if (hasInterval(6.0)) augs.add("b5");
            if (hasInterval(8.0)) augs.add("#5");
            if (hasInterval(1.0)) augs.add("b9");
            if (hasInterval(3.0) && isMinor() == false) augs.add("#9");
            if (hasInterval(4.0) && !isMajor()) augs.add("b11");
            if (hasInterval(8.0)) augs.add("b13");

            // NOTE: List/Array::join would be nice here.
            String output = "";
            for(String aug : augs) output = output + aug;
            return output;
        }

        public boolean hasInterval(double semitones) {
            for (int i = 1; i < this.size(); i++) {
                Interval interval = new Interval(Root, this.get(i));
                if(interval.semitones() == semitones) return true;
            }
            return false;
        } 
    } requires {
        Note get(int pos);
        int size();
    }

    public Chord(List<Note> notes) {
        assert(notes.size() > 1, "Chord must have more than one note.");
        Root = notes.get(0);
        Notes = notes;
    }

    // NOTE: How to handle the "may hide method" warning?
    public String fullChordName() {
        return Root.chordName() + Notes.quality() + Notes.dominant() + Notes.augmentations();
    }
}

// ----- Entrypoint -------------------------------

{
    List<Note> notes = new List<Note>();
    notes.add(new Note("C"));
    notes.add(new Note("Eb"));
    notes.add(new Note("G"));
    notes.add(new Note("Bb"));
    notes.add(new Note("F"));
    Chord chord = new Chord(notes);

    List<Note> notes2 = new List<Note>();
    notes2.add(new Note("D"));
    notes2.add(new Note("G"));
    notes2.add(new Note("A"));
    Chord chord2 = new Chord(notes2);

    List<Note> notes3 = new List<Note>();
    notes3.add(new Note("E"));
    notes3.add(new Note("G"));
    notes3.add(new Note("Bb"));
    notes3.add(new Note("D"));
    notes3.add(new Note("F"));
    Chord chord3 = new Chord(notes3);

    List<Note> notes4 = new List<Note>();
    notes4.add(new Note("F"));
    notes4.add(new Note("A"));
    notes4.add(new Note("C"));
    notes4.add(new Note("E"));
    notes4.add(new Note("G"));
    Chord chord4 = new Chord(notes4);

    List<Note> notes5 = new List<Note>();
    notes5.add(new Note("F#"));
    notes5.add(new Note("C#"));
    Chord chord5 = new Chord(notes5);

    List<Note> notes6 = new List<Note>();
    notes6.add(new Note("G"));
    notes6.add(new Note("A"));
    notes6.add(new Note("D"));
    Chord chord6 = new Chord(notes6);

    List<Note> notes7 = new List<Note>();
    notes7.add(new Note("Cb"));
    notes7.add(new Note("D"));
    notes7.add(new Note("Eb"));
    notes7.add(new Note("A"));
    notes7.add(new Note("Ab"));
    Chord chord7 = new Chord(notes7);

    System.out.println(chord .fullChordName());
    System.out.println(chord2.fullChordName());
    System.out.println(chord3.fullChordName());
    System.out.println(chord4.fullChordName());
    System.out.println(chord5.fullChordName());
    System.out.println(chord6.fullChordName());
    System.out.println(chord7.fullChordName());
}

/* GOLD:
0 warnings, 0 errors.
___________________________________________________________
Cm11
Dsus
Em7b5b9
Fmaj9
F#5
Gsus2
Cbm13
*/
