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

            System.out.println("Moving to next word. Current position: " + currentPosition().toString());

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
        // NOTE: This method causes errors.
        public void checkSpelling(String word) {
            assert(word != null, "word was null.");

            List<String> suggestions = suggestSpelling(word);
            if(suggestions.size() == 0) {
                System.out.println(word + " spelled OK");
                return;
            }
if(suggestions.size() == 0) assert(false, "theoretically unreachable")
            int pos = SpellChecker.currentPosition();
            int oldWordPos = pos - word.length() - 1;
            int newPos = oldWordPos + suggestions.get(0).length() + 1;

            Text.splice(oldWordPos, oldWordPos + word.length(), "[" + suggestions.get(0) + "]");
            SpellChecker.setPosition(newPos);
        }

        // NOTE: Rename this method to "checkSpelling" and the above one to something else
        // to see the problem going away by inverting the logic.
        public void checkSpelling_working(String word) {
            assert(word != null, "word was null.");

            List<String> suggestions = suggestSpelling(word);
            if(suggestions.size() > 0) {
                int pos = SpellChecker.currentPosition();
                int oldWordPos = pos - word.length() - 1;
                int newPos = oldWordPos + suggestions.get(0).length() + 1;

                Text.splice(oldWordPos, oldWordPos + word.length(), "[" + suggestions.get(0) + "]");
                SpellChecker.setPosition(newPos);
            } else {
                System.out.println(word + " spelled OK");
            }
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

    // If private, they are inaccessible to role SpellChecker
    public int currentPosition() const { return currentPos_; }
    public void setPosition(int pos) { currentPos_ = pos.clone; }

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
line 208: NONCOMPLIANT: Trying to enact object script `currentPosition()' without using the interface of the Role it is playing: `SpellChecker'.
line 212: NONCOMPLIANT: Trying to enact object script `currentPosition()' without using the interface of the Role it is playing: `SpellChecker'.
line 231: NONCOMPLIANT: Trying to enact object script `currentPosition()' without using the interface of the Role it is playing: `SpellChecker'.
line 235: NONCOMPLIANT: Trying to enact object script `splice(int, int, String)' without using the interface of the Role it is playing: `Text'.
line 236: NONCOMPLIANT: Trying to enact object script `setPosition(int)' without using the interface of the Role it is playing: `SpellChecker'.
line 246: NONCOMPLIANT: Trying to enact object script `currentPosition()' without using the interface of the Role it is playing: `SpellChecker'.
line 250: NONCOMPLIANT: Trying to enact object script `splice(int, int, String)' without using the interface of the Role it is playing: `Text'.
line 251: NONCOMPLIANT: Trying to enact object script `setPosition(int)' without using the interface of the Role it is playing: `SpellChecker'.
0 warnings, 0 errors.
___________________________________________________________
On the twelveth day of Christmas my true love gave to me
twelve drummers drumming, eleven pipers piping, ten lords a-leaping,
nine ladies dancing, eight maids a-milking, seven swans a-swimming,
six geese a-laying, five gold rings;
four calling birds, three french hens, two turtle doves
and a partridge in a pear tree.

Moving to next word. Current position: 0
ten spelled OK
Moving to next word. Current position: 4
lords spelled OK
Moving to next word. Current position: 10
Moving to next word. Current position: 18
nine spelled OK
Moving to next word. Current position: 26
ladies spelled OK
Moving to next word. Current position: 33
dancing spelled OK
Moving to next word. Current position: 41
eight spelled OK
Moving to next word. Current position: 48
maids spelled OK
Moving to next word. Current position: 54
Moving to next word. Current position: 62
seven spelled OK
Moving to next word. Current position: 71
swans spelled OK
On the twelveth day of Christmas my true love gave to me
twelve drummers drumming, eleven pipers piping, ten lords [leaping],
nine ladies dancing, eight maids [milking], seven swans a-swimming,
six geese a-laying, five gold rings;
four calling birds, three french hens, two turtle doves
and a partridge in a pear tree.

Moving to next word. Current position: 0
On spelled OK
Moving to next word. Current position: 3
the spelled OK
Moving to next word. Current position: 7
twelveth spelled OK
Moving to next word. Current position: 16
day spelled OK
Moving to next word. Current position: 20
of spelled OK
Moving to next word. Current position: 23
Christmas spelled OK
Moving to next word. Current position: 33
my spelled OK
Moving to next word. Current position: 36
true spelled OK
Moving to next word. Current position: 41
love spelled OK
Moving to next word. Current position: 46
gave spelled OK
Moving to next word. Current position: 51
to spelled OK
Moving to next word. Current position: 54
me spelled OK
Moving to next word. Current position: 57
twelve spelled OK
Moving to next word. Current position: 64
drummers spelled OK
Moving to next word. Current position: 73
drumming spelled OK
Moving to next word. Current position: 82
eleven spelled OK
Moving to next word. Current position: 90
pipers spelled OK
Moving to next word. Current position: 97
piping spelled OK
Moving to next word. Current position: 104
ten spelled OK
Moving to next word. Current position: 109
lords spelled OK
Moving to next word. Current position: 115
leaping spelled OK
Moving to next word. Current position: 124
nine spelled OK
Moving to next word. Current position: 131
ladies spelled OK
Moving to next word. Current position: 138
dancing spelled OK
Moving to next word. Current position: 146
eight spelled OK
Moving to next word. Current position: 153
maids spelled OK
Moving to next word. Current position: 159
milking spelled OK
Moving to next word. Current position: 168
seven spelled OK
Moving to next word. Current position: 176
swans spelled OK
Moving to next word. Current position: 182
Moving to next word. Current position: 191
six spelled OK
Moving to next word. Current position: 198
geese spelled OK
Moving to next word. Current position: 204
Moving to next word. Current position: 211
five spelled OK
Moving to next word. Current position: 219
gold spelled OK
Moving to next word. Current position: 224
rings spelled OK
Moving to next word. Current position: 230
four spelled OK
Moving to next word. Current position: 236
calling spelled OK
Moving to next word. Current position: 244
birds spelled OK
Moving to next word. Current position: 250
three spelled OK
Moving to next word. Current position: 257
french spelled OK
Moving to next word. Current position: 264
hens spelled OK
Moving to next word. Current position: 269
two spelled OK
Moving to next word. Current position: 274
turtle spelled OK
Moving to next word. Current position: 281
doves spelled OK
Moving to next word. Current position: 287
and spelled OK
Moving to next word. Current position: 291
a spelled OK
Moving to next word. Current position: 293
partridge spelled OK
Moving to next word. Current position: 303
in spelled OK
Moving to next word. Current position: 306
a spelled OK
Moving to next word. Current position: 308
pear spelled OK
Moving to next word. Current position: 313
tree spelled OK
Moving to next word. Current position: 318
On the twelveth day of Christmas my true love gave to me
twelve drummers drumming, eleven pipers piping, ten lords [leaping],
nine ladies dancing, eight maids [milking], seven swans [swimming],
six geese [laying], five gold rings;
four calling birds, three french hens, two turtle doves
and a partridge in a pear tree.

*/
