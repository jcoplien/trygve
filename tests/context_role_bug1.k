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
    stageprop Text {} requires {
        String getText() const;
    }

    public TextSelection(Text text, int start, int end) {
        assert(start >= 0, "Invalid start select position: " + start.toString());
        assert(start <= end, "End selection position less than start position.");

        Text = text;
        start_ = start;
        end_ = end;
    }

    public String getText() const { 
        return Text.getText().substring(start_, end_); 
    }

    public int start() const { return start_; }
    public int end() const { return end_; }

    private int start_;
    private int end_;
}

///////////////////////////////////////

context TextFile {
    role Buffer {} requires {
        String getText() const;
        void add(String text);
        void clear();
    }

    public TextFile() {
        Buffer = new TextBuffer();
    }

    public void load(String filename) {
        if(filename == "twelve_days_of_christmas.txt") {            
            Buffer.clear();
            Buffer.add("On the twelveth day of Christmas my true love gave to me ");
            Buffer.add("twelve drummers drumming, eleven pipers piping, ten lords a-leaping, ");
            Buffer.add("nine ladies dancing, eight maids a-milking, seven swans a-swimming, ");
            Buffer.add("six geese a-laying, five gold rings; ");
            Buffer.add("four calling birds, three french hens, two turtle doves ");
            Buffer.add("and a partridge in a pear tree.");
        } else {
            assert(false, 
                "File '" + filename + "' not found (try 'twelve_days_of_christmas.txt')");
        }
//        return this;
    }

    public String getText() { return Buffer.getText(); }
}

///////////////////////////////////////

context SpellCheck {
    role Text {
        public boolean isFinished() const {
            return currentPos_ >= getText().length();
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
            return getText().substring(currentPos_, ++currentPos_);
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
        Text = text;
        currentPos_ = 0;
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
line 42: WARNING: Script declaration for `getText' may hide script declared at line 55
line 51: WARNING: Assignment / initialization does not create a new instance. Both `start_' and `start' will refer to the same object. Use `start.clone' to create a separate instance.
line 52: WARNING: Assignment / initialization does not create a new instance. Both `end_' and `end' will refer to the same object. Use `end.clone' to create a separate instance.
line 69: WARNING: Script declaration for `getText' may hide script declared at line 95
line 103: NONCOMPLIANT: Attempt to access Context member `currentPos_' from within scope of Role script `Text.isFinished'. Roles may not directly access Context data.  Consider binding the Context to one of its own Roles instead.
line 116: NONCOMPLIANT: Attempt to access Context member `currentPos_' from within scope of Role script `Text.nextCharacter'. Roles may not directly access Context data.  Consider binding the Context to one of its own Roles instead.
line 116: NONCOMPLIANT: Attempt to access Context member `currentPos_' from within scope of Role script `Text.nextCharacter'. Roles may not directly access Context data.  Consider binding the Context to one of its own Roles instead.
___________________________________________________________
Line 108: Assertion failed.
*/
