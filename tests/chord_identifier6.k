class Note {
    public Note(String name) {
        name_ = name.clone;
        assert(position() > 0.0, "Invalid note: " + name);
    }

    public String name() const {
        return name_;
    }

    public double position() const {
        double pos =
             switch (name_) {
                  case "C":  1.0; break;
                  case "C#":
                  case "Db": 2.0; break;
                  case "D":  3.0; break;
                  case "D#":
                  case "Eb": 4.0; break;
                  case "E":  5.0; break;
                  case "F":  6.0; break;
                  case "F#":
                  case "Gb": 7.0; break;
                  case "G":  8.0; break;
                  case "G#":
                  case "Ab": 9.0; break;
                  case "A": 10.0; break;
                  case "A#":
                  case "Bb": 11.0; break
                  case "B":  12.0; break;
                  default:    0.0
             }
        return pos;
    }

    private String name_;
}

// -------------------------------------------------

context Interval {
    stageprop LowNote {} requires {
        double position() const;
    }

    stageprop HighNote {} requires {
        double position() const;
    }

    public Interval(LowNote lowNote, HighNote highNote) {
        LowNote = lowNote;
        HighNote = highNote;
    }

    public double semitones() {
        double lowPos = LowNote.position();
        double highPos = HighNote.position();
        double octaveOffset = if(lowPos > highPos) 12.0 else 0.0;

        return highPos - lowPos + octaveOffset;
    }
}

// -------------------------------------------------

context Chord {
    stageprop Root {} requires {
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

            // NOTE: Expression cuts off sus2 and sus4 (see test output)
            return (if(isMinor()) "m" else "") + sus2 + sus4;
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
    public String name() {
        return Root.name() + Notes.quality() + Notes.dominant() + Notes.augmentations();
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

    System.out.println(chord.name());
    System.out.println(chord2.name());
    System.out.println(chord3.name());
    System.out.println(chord4.name());
    System.out.println(chord5.name());
    System.out.println(chord6.name());
}

/* GOLD:
line 67: WARNING: Script declaration for `name' may hide script declared at line 160
___________________________________________________________
Cm11
Dsus
Em7b5b9
Fmaj9
F#5
Gsus2
*/
