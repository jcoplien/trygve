/*
Based on the following use case:

1. User requests spell checking of the text buffer
1.1 Alternative flow: User requests spell checking of highlighted / selected text
1.2 Alternative flow: User requests spell checking of a file
2. Spell checking advances to next word
3. The dictionary checks the spelling of the word
4. The dictionary generates a possible correct spelling of the word
4.1 Alternative flow: Spelling is correct: go back to step 2
5. The user is asked whether the suggested spelling is correct
6. The user acknowledges the new spelling
6.1 Alternative flow: The user decides to keep the existing spelling: go back to step 2
7. The new spelling replaces the old one
*/

///////////////////////////////////////

class TextBuffer {
    public TextBuffer() {
        buffer_ = "";
    }

    public void add(String text) {
        buffer_ = buffer_ + text;
    }

    // TODO: insert(String text, int position)

    public void clear() {
        buffer_ = "";
    }

    public String getText() const { return buffer_; }

    private String buffer_;
}

///////////////////////////////////////

context TextSelection {
    stageprop Text {
        public String selection() const {
           return this.getText()
        }
    } requires {
        String getText() const;
    }

    public TextSelection(Text text, int start, int end) {
        assert(start >= 0, "Invalid start select position: " + start.toString());
        assert(start <= end, "End selection position less than start position.");

        Text = text.clone;
        start_ = start.clone;
        end_ = end.clone;
    }

    // public String contents() const { 
    //     return Text.selection().substring(start_, end_); 
    // }

    public int start() const { return start_; }
    public int end() const { return end_; }

    private int start_;
    private int end_;
}

///////////////////////////////////////

context TextFile {
    role Buffer {
        public void put(String text) {
            add(text);
        }
        public String contents() {
           return getText()
        }
        public void reset() {
           clear()
        }
    } requires {
        String getText() const;
        void add(String text);
        void clear();
    }

    public TextFile() {
        Buffer = new TextBuffer();
    }

    public void load(String filename) {
        if(filename == "twelve_days_of_christmas.txt") {            
            Buffer.reset();
            Buffer.put("On the twelveth day of Christmas my true love gave to me ");
            Buffer.put("twelve drummers drumming, eleven pipers piping, ten lords a-leaping, ");
            Buffer.put("nine ladies dancing, eight maids a-milking, seven swans a-swimming, ");
            Buffer.put("six geese a-laying, five gold rings; ");
            Buffer.put("four calling birds, three french hens, two turtle doves ");
            Buffer.put("and a partridge in a pear tree.");
        } else {
            assert(false, 
                "File '" + filename + "' not found (try 'twelve_days_of_christmas.txt')");
        }
//        return this;
    }

    // public String getText() { return Buffer.contents(); }
}

///////////////////////////////////////

context SpellCheck {
    role SpellChecker {
       public int position() const {
          return currentPos()
       }
    } requires {
       int currentPos() const;
       void setPosition(int pos);
    }
    role Text {
        public boolean isFinished() const {
            return SpellChecker.position() >= getText().length();
        }

        // Sorry, English only...
        private boolean isAlphabetic(String character) const {
            assert(character.length() == 1);
            return character == "-" || 
                   (character >= "a" && character <= "z") ||
                   (character >= "A" && character <= "Z");
        }

        private String nextCharacter() {
            // NOTE: Will this postfix "trick" work? Untested...
            String retval = getText().substring(SpellChecker.position(), SpellChecker.position());
            SpellChecker.setPosition(SpellChecker.position() + 1);
            return retval;
        }

        public String nextWord() {
            // NOTE: Cannot call isFinished, runtime error
            assert(!isFinished(), "Already parsed text");

            String output = "";
            // NOTE: Cannot call nextCharacter, runtime error
            String next = nextCharacter();

            while(!isAlphabetic(next)) {
                if(isFinished()) return output;
                next = nextCharacter();
            }

            while(isAlphabetic(next)) {                              
                output = output + next;
                if(isFinished()) return output;
                next = nextCharacter();
            }

            return output;
        }
    } requires {
        String getText() const;
    }

    public SpellCheck(Text text) {
        Text = text.clone;
        SpellChecker = this;
        currentPos_ = 0;
    }

    public int currentPos() const {
       return currentPos_.clone
    }

    public void setPosition(int pos) {
       currentPos_ = pos.clone
    }

    public void test() {
        System.out.println(Text.nextWord());
    }

    int currentPos_;
}

///// Entrypoint //////////////////////////////////////////////////

{
    TextFile file = new TextFile();
    file.load("twelve_days_of_christmas.txt");
    new SpellCheck(file).test();
}

/* GOLD:
line 115: WARNING: Script declaration for `setPosition' has the same name as the Context script declared at line 178
line 115: WARNING: Script declaration for `currentPos' has the same name as the Context script declared at line 174
line 139: NONCOMPLIANT: Trying to enact object script `setPosition(int)' without using the interface of the Role it is playing: `SpellChecker'.
line 194: No matching constructor on class `SpellCheck' for `new' invocation
___________________________________________________________
*/
