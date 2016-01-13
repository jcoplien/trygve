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
        start_ = start.clone;
        end_ = end.clone;
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
            Buffer.add("On the twelveth day of Christmas my true love gave to me\n");
            Buffer.add("twelve drummers drumming, eleven pipers piping, ten lords a-leaping,\n");
            Buffer.add("nine ladies dancing, eight maids a-milking, seven swans a-swimming,\n");
            Buffer.add("six geese a-laying, five gold rings;\n");
            Buffer.add("four calling birds, three french hens, two turtle doves\n");
            Buffer.add("and a partridge in a pear tree.\n");
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
    role SpellChecker {
        public String nextWord() {
            if(Text.isFinished()) return "";

            String output = "";
            String next = Text.currentCharacter();
            advancePosition();

            while(!isAlphabetic(next)) {
                if(Text.isFinished()) return output;
                next = Text.currentCharacter();
                advancePosition();
            }

            while(isAlphabetic(next)) {                              
                output = output + next;
                if(Text.isFinished()) return output;
                next = Text.currentCharacter();
                advancePosition();
            }

            return output;
        }

        // Sorry, English only...
        private boolean isAlphabetic(String character) const {
            assert(character.length() == 1, "character string length not equal to one.");
            return character == "-" || 
                   (character >= "a" && character <= "z") ||
                   (character >= "A" && character <= "Z");
        }
    } requires {
        int currentPosition() const;
        void advancePosition();
    }

    role Text {
        public String currentCharacter() {
            int pos = SpellChecker.currentPosition();
            return getText().substring(pos, pos+1);
        }
        public boolean isFinished() const {
            return SpellChecker.currentPosition() >= getText().length();
        }
    } requires {
        String getText() const;
    }

    public SpellCheck(Text text) {
        currentPos_ = 0;
        Text = text;
        SpellChecker = this;
    }

    public void test() {
        String word = SpellChecker.nextWord();
        while(word.length() > 0) {
            System.out.println(word);
            word = SpellChecker.nextWord();
        }
    }

    private int currentPosition() const { return currentPos_; }
    private void advancePosition() { ++currentPos_; }

    private int currentPos_;
}

///// Entrypoint //////////////////////////////////////////////////

{
    TextFile file = new TextFile();
    file.load("twelve_days_of_christmas.txt");
    new SpellCheck(file).test();
}

/* GOLD:
___________________________________________________________
On
*/
