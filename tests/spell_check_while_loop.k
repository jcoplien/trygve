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

    public TextBuffer(String text) {
        assert(text != null, "text was null");
        buffer_ = text.clone;
    }

    public void add(String text) {
        assert(text != null, "text was null");
        buffer_ = buffer_ + text;
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

        buffer_ = buffer_.substring(0, startIndex) + 
                  text + 
                  buffer_.substring(endIndex, buffer_.length());
    }

    public void clear() {
        buffer_ = "";
    }

    public String getText() const { return buffer_; }
    public int length() const { return buffer_.length(); }

    private String buffer_;
}

///////////////////////////////////////
context TextSelection {
    private TextBuffer Buffer;

    public TextSelection(TextBuffer buffer, int start, int end) {
        assert(start >= 0, "Invalid start select position: " + start.toString());
        assert(start <= end, "End selection position less than start position.");

        Buffer = buffer;
        start_ = start.clone;
        end_ = end.clone;
    }

    public String getText() const { 
        return Buffer.getText().substring(start_, end_); 
    }

    public void splice(int startIndex, int endIndex, String text) {
        assert(startIndex >= 0, "startIndex was less than 0");
        assert(endIndex >= startIndex, "endIndex was less than startIndex");
        assert(text != null, "text was null");

        Buffer.splice(start_ + startIndex, start_ + endIndex, text);
    }

    public int start() const { return start_; }
    public int end() const { return end_; }

    private int start_;
    private int end_;
}

///////////////////////////////////////
class TextFile {
    private TextBuffer Buffer;

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
    }

    public TextBuffer getBuffer() const { return Buffer; }
    public String getText() const { return Buffer.getText(); }

    public void splice(int startIndex, int endIndex, String text) { 
        assert(startIndex >= 0, "startIndex was less than 0");
        assert(endIndex >= startIndex, "endIndex was less than startIndex");
        assert(text != null, "text was null");

        Buffer.splice(startIndex, endIndex, text); 
    }
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

        private void advancePosition() { 
            setPosition(currentPosition() + 1);
        }

        // Sorry, English only...
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
        public String currentCharacter() {
            int pos = SpellChecker.currentPosition();
            return getText().substring(pos, pos+1);
        }
        public boolean isFinished() {
            return SpellChecker.currentPosition() >= getText().length();
        }
    } requires {
        String getText() const;
        void splice(int startIndex, int endIndex, String text);
    }

    /////////////////////////////////////////
    stageprop Dictionary {
        public void checkSpelling(String word) {
            assert(word != null, "word was null.");

            List<String> suggestions = suggestSpelling(word);
            if(suggestions.size() == 0) {
                return;
            }

            int pos = SpellChecker.currentPosition();
            int oldWordPos = pos - word.length() - 1;
            int newPos = oldWordPos + suggestions.get(0).length() + 1;

            Text.splice(oldWordPos, oldWordPos + word.length(), "[" + suggestions.get(0) + "]");
            SpellChecker.setPosition(newPos);
        }
    } requires {
        List<String> suggestSpelling(String word) const;
    }

    /////////////////////////////////////////

    public SpellCheck(Text text) {
        currentPos_ = 0;
        Text = text;
        SpellChecker = this;
        Dictionary = new SimpleDictionary();
    }

    public void spellCheck() {
        String word = SpellChecker.nextWord();
        int i = 0; // safety
        while(word.length() > 0 && i++ < 60) {
            Dictionary.checkSpelling(word);
            word = SpellChecker.nextWord();
        }
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
    new SpellCheck(selection).spellCheck();
    System.out.println(file.getText());

    // Spell check the whole file and print it
    new SpellCheck(file).spellCheck();
    System.out.println(file.getText());
}

/* GOLD:
line 163: WARNING: Script declaration for `setPosition' has the same name as the Context script declared at line 257
line 163: WARNING: Script declaration for `currentPosition' has the same name as the Context script declared at line 256
line 206: NONCOMPLIANT: Trying to enact object script `currentPosition()' without using the interface of the Role it is playing: `SpellChecker'.
line 210: NONCOMPLIANT: Trying to enact object script `currentPosition()' without using the interface of the Role it is playing: `SpellChecker'.
line 227: NONCOMPLIANT: Trying to enact object script `currentPosition()' without using the interface of the Role it is playing: `SpellChecker'.
line 231: NONCOMPLIANT: Trying to enact object script `splice(int, int, String)' without using the interface of the Role it is playing: `Text'.
line 232: NONCOMPLIANT: Trying to enact object script `setPosition(int)' without using the interface of the Role it is playing: `SpellChecker'.
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
