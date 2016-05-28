class Note {
    public Note(String name) {
        name_ = name.clone();
        assert(position() > 0.0, "Invalid note: " + name);
    }

    public String name() const {
        return name_;
    }

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
        if(name_ == "A")  pos = 10.0;
        if(name_ == "A#") pos = 11.0;
        if(name_ == "Bb") pos = 11.0;
        if(name_ == "B")  pos = 12.0;
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

// The first note in the list will be the root.
context Chord {
    stageprop Root {
        public String chordName() const { return name() }
    } requires {
        double position() const;
        // NOTE: Compilation error with the "name" method included:
        String name() const;
    }

    role Notes {
        public String quality() {
            // NOTE: cannot use bool expression directly, must use if statement.
            boolean hasDominant = if (dominant().length() > 0) true else false;

            boolean minor = isMinor();
            boolean major = isMajor();

            // NOTE: Any call to isMinor() or isMajor() below
            // creates a runtime crash. Temp vars is the workaround.

            String sus2 = "";
            if(hasInterval(2.0)) {
                if(hasDominant == false) { 
                    sus2 = "sus2";
                    if(isMinor()) sus2 = "add9";
                    if(isMajor()) sus2 = "add9";
                }
            }

            String sus4 = "";
            if(hasInterval(5.0)) {
                if(hasDominant == false) { 
                    sus4 = "sus";
                    if(isMinor()) sus4 = "add11";
                    if(isMajor()) sus4 = "add11";
                }
            }

            // NOTE: Parenthesis around expression doesn't work, need temp var.
            String majorOrMinor = if(isMinor()) "m" else "";
            return majorOrMinor + sus2 + sus4;
        }

        private boolean isMajor() {
            return hasInterval(4.0)
        }

        private boolean isMinor() {
            return hasInterval(3.0)
        }

        public String dominant() {
            String highest = "";
            String maj7 = if(hasInterval(11.0)) "maj" else "";

            // NOTE: Crashes here for every hasInterval call
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
        
        public String augmentations() {
            List<String> augs = new List<String>();

            if (hasInterval(6.0)) augs.add("b5");
            if (hasInterval(8.0)) augs.add("#5");
            if (hasInterval(1.0)) augs.add("b9");
            if (hasInterval(3.0)) if(isMinor() == false) augs.add("#9");
            if (hasInterval(4.0)) if(isMajor() == false) augs.add("b11");
            if (hasInterval(6.0)) augs.add("#11");
            if(hasInterval(8.0)) augs.add("b13");

            String output = "";
            for(String aug : augs) output = output + aug;
            return output;
        }

        public boolean hasInterval(double semitones) {
            for (int i = 1; i < this.size(); i++) {
                // NOTE: Cannot access Root.position here, compilation error:
                // Exception in thread "AWT-EventQueue-0" java.lang.AssertionError
                double pos = Root.position();
                
                // NOTE: Cannot use Root directly for duck typing, need a temp var.
                Interval interval = new Interval(Root, this.get(i));
                
                // NOTE: Cannot return, the loop keeps going.
                // This is true in general, only one return statement seems 
                // allowed in methods.
                if(interval.semitones() == semitones) return true;
            }
            return false;
        } 
/*
        // Workaround for the broken method
        private boolean hasInterval(double semitones) {
            boolean has = false;
            Root root = Root;
            for(int i = 1; i < this.size(); i++) {
                Interval interval = new Interval(root, this.get(i));                
                if(interval.semitones() == semitones) has = true;
            }
            return has;
        } 
*/
    } requires {
        Note get(int pos);
        int size();
    }

    public Chord(List<Note> notes) {
        assert(notes.size() > 1, "Chord must have more than one note.");
        Root = notes.get(0);
        Notes = notes;
    }

    public String fullChordName() {
        // NOTE: Cannot use Root.chordName() directly, need temp var.
        return Root.chordName() + Notes.quality() + Notes.dominant() + Notes.augmentations();
    }
}

// ----- Start -------------------------------

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
    notes3.add(new Note("Eb"));
    Chord chord3 = new Chord(notes3);

    List<Note> notes4 = new List<Note>();
    notes4.add(new Note("F"));
    notes4.add(new Note("A"));
    notes4.add(new Note("C"));
    notes4.add(new Note("E"));
    notes4.add(new Note("G"));
    Chord chord4 = new Chord(notes4);

    System.out.println(chord.fullChordName());
    System.out.println(chord2.fullChordName());
    System.out.println(chord3.fullChordName());
    System.out.println(chord4.fullChordName());
}

/* GOLD:
line 160: NONCOMPLIANT: Trying to enact object script `position()' without using the interface of the Role it is playing: `Root'.
0 warnings, 0 errors.
___________________________________________________________
Cm11
Dsus
Emmaj7b5#11
Fmaj9
*/
