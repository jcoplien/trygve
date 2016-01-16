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
interface Buffer {
    public void add(String text);
    public void clear();
    public void insert(String text, int position);
    public void splice(int startIndex, int endIndex, String text);

    public int length() const;
    public String getText() const;
}

///////////////////////////////////////
class TextBuffer implements Buffer {
    public TextBuffer() {
        text_ = "";
    }

    public TextBuffer(String text) {
        assert(text != null, "text was null");
        text_ = text.clone;
    }

    public void add(String text) {
        assert(text != null, "text was null");
        text_ = text_ + text;
    }

    public void insert(String text, int position) {
        assert(text != null, "text was null");
        assert(position >= 0, "position was less than 0");

        splice(position, position, text);
    }

    public void splice(int startIndex, int endIndex, String text) {
        assert(startIndex >= 0, "startIndex was less than 0");
        assert(endIndex >= startIndex, "endIndex was less than startIndex");
        assert(text != null, "text was null");

        text_ = text_.substring(0, startIndex) + 
                text + 
                text_.substring(endIndex, text_.length());
    }

    public void clear() {
        text_ = "";
    }

    public String getText() const { return text_; }
    public int length() const { return text_.length(); }

    private String text_;
}

///////////////////////////////////////
class TextSelection {
    public TextSelection(Buffer buffer, int start, int end) {
        assert(start >= 0, "Invalid start select position: " + start.toString());
        assert(start <= end, "End selection position less than start position.");

        buffer_ = buffer;
        start_ = start.clone;
        end_ = end.clone;
    }

    public String getText() const { 
        return buffer_.getText().substring(start_, end_);
    }

    public void splice(int startIndex, int endIndex, String text) {
        assert(startIndex >= 0, "startIndex was less than 0");
        assert(endIndex >= startIndex, "endIndex was less than startIndex");
        assert(text != null, "text was null");

        buffer_.splice(start_ + startIndex, start_ + endIndex, text);
    }

    public int start() const { return start_; }
    public int end() const { return end_; }

    private int start_;
    private int end_;
    private Buffer buffer_;
}

///////////////////////////////////////
class TextFile {
    public TextFile() {
        buffer_ = new TextBuffer();
    }

    public void load(String filename) {
        if(filename == "twelve_days_of_christmas.txt") {            
            buffer_.clear();
            buffer_.add("On the twelveth day of Christmas my true love gave to me\n");
            buffer_.add("twelve drummers drumming, eleven pipers piping, ten lords a-leaping,\n");
            buffer_.add("nine ladies dancing, eight maids a-milking, seven swans a-swimming,\n");
            buffer_.add("six geese a-laying, five gold rings;\n");
            buffer_.add("four calling birds, three french hens, two turtle doves\n");
            buffer_.add("and a partridge in a pear tree.\n");
        } else {
            assert(false, 
                "File '" + filename + "' not found (try 'twelve_days_of_christmas.txt')");
        }
    }

    public String getText() const { return buffer_.getText(); }
    public Buffer getBuffer() const { return buffer_; }

    public void splice(int startIndex, int endIndex, String text) { 
        assert(startIndex >= 0, "startIndex was less than 0");
        assert(endIndex >= startIndex, "endIndex was less than startIndex");
        assert(text != null, "text was null");

        buffer_.splice(startIndex, endIndex, text); 
    }

    private Buffer buffer_;
}

///////////////////////////////////////
// An inverted dictionary. Anything not in it is considered correctly spelled.
class SimpleDictionary {
    public SimpleDictionary() {}

    public List<String> suggestSpelling(String word) const {
        assert(word != null, "word was null.");
        assert(word.length() > 0, "Cannot use an empty String for spelling suggestions.");

        List<String> output = new List<String>();

        switch(word) {
            case "a-milking":
                output.add("milking");
                break;

            case "a-leaping":
                output.add("leaping");
                break;

            case "a-swimming":
                output.add("swimming");
                break;

            case "a-laying":
                output.add("laying");
                break;
        }

        return output;
    }
}

///////////////////////////////////////
context SpellCheck {
    /////////////////////////////////////////
    role SpellChecker {
        public void advanceToNextWord() {
            if(isFinished()) return;

            // Advance past current word
            while(isAlphabetic(currentCharacter())) {
                setPosition(currentPosition() + 1);
                if(isFinished()) return;
            }

            // Advance past non-alphabetic characters
            while(!isAlphabetic(currentCharacter())) {
                setPosition(currentPosition() + 1);
                if(isFinished()) return;
            }

            Dictionary.checkCurrentWord();
        }

        public String currentWord() {
            if(isFinished()) return "";

            String output = "";
            int pos = currentPosition().clone;

            while(isAlphabetic(Text.characterAt(pos))) {
                output = output + Text.characterAt(pos++);
                // NOTE: Cannot call Text.getText().length() here.
                if(pos >= Text.length()) return output;
            }

            return output;
        }

        private String currentCharacter() {
            return Text.characterAt(currentPosition());
        }

        private boolean isFinished() {
            return currentPosition() >= Text.length();
        }

        private boolean isAlphabetic(String character) {
            assert(character.length() == 1, "character string length not equal to one.");
            return character == "-" || 
                   (character >= "a" && character <= "z") ||
                   (character >= "A" && character <= "Z");
        }
    } requires {
        int currentPosition() const;
        void setPosition(int pos);
    }

    /////////////////////////////////////////
    role Text {
        public String characterAt(int pos) {
            return getText().substring(pos, pos+1);
        }
        public int length() {
            return getText().length();
        }
    } requires {
        String getText() const;
        void splice(int startIndex, int endIndex, String text);
    }

    /////////////////////////////////////////
    stageprop Dictionary {
        public void checkCurrentWord() {
            String word = SpellChecker.currentWord();
            List<String> suggestions = suggestSpelling(word);

            if(suggestions.size() > 0) {
                int pos = SpellChecker.currentPosition();

                // TODO: User input and selection
                Text.splice(pos, pos + word.length(), "[" + suggestions.get(0) + "]");
            }

            SpellChecker.advanceToNextWord();
        }
    } requires {
        List<String> suggestSpelling(String word) const;
    }

    /////////////////////////////////////////

    public SpellCheck(Text text) {
        Text = text;
        SpellChecker = this;
        Dictionary = new SimpleDictionary();
    }

    public void start() {
        SpellChecker.setPosition(0);
        Dictionary.checkCurrentWord();
    }

    private int currentPosition() const { return currentPos_; }
    private void setPosition(int pos) { currentPos_ = pos.clone; }

    private int currentPos_;
}

///// Entrypoint //////////////////////////////////////////////////

{
    // Load and print the original file
    TextFile file = new TextFile();
    file.load("twelve_days_of_christmas.txt");
    System.out.println(file.getText());

    // Spell check a selection and print it
    TextSelection selection = new TextSelection(file.getBuffer(), 105, 181);
    new SpellCheck(selection).start();
    System.out.println(file.getText());

    // Spell check the whole file and print it
    new SpellCheck(file).start();
    System.out.println(file.getText());
}

/* GOLD:
___________________________________________________________
On the twelveth day of Christmas my true love gave to me
twelve drummers drumming, eleven pipers piping, ten lords a-leaping,
nine ladies dancing, eight maids a-milking, seven swans a-swimming,
six geese a-laying, five gold rings;
four calling birds, three french hens, two turtle doves
and a partridge in a pear tree.

On the twelveth day of Christmas my true love gave to me
twelve drummers drumming, eleven pipers piping, ten lords [leaping],
nine ladies dancing, eight maids [milking], seven swans a-swimming,
six geese a-laying, five gold rings;
four calling birds, three french hens, two turtle doves
and a partridge in a pear tree.

On the twelveth day of Christmas my true love gave to me
twelve drummers drumming, eleven pipers piping, ten lords [leaping],
nine ladies dancing, eight maids [milking], seven swans [swimming],
six geese [laying], five gold rings;
four calling birds, three french hens, two turtle doves
and a partridge in a pear tree.

*/
