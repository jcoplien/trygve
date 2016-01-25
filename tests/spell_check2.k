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
    public void append(String text);
    public void delete(int start, int end);
    public void insert(int offset, String text);
    public void replace(int start, int end, String text);

    public int length() const;
    public String toString() const;
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

    public void append(String text) {
        assert(text != null, "text was null");
        text_ = text_ + text;
    }

    public void delete(int start, int end) {
        assert(start >= 0, "start was less than 0");
        assert(end >= start, "end was less than start");
        replace(start, end, "");
    }

    public void insert(int position, String text) {
        assert(position >= 0, "position was less than 0");
        assert(text != null, "text was null");

        replace(position, position, text);
    }

    public void replace(int start, int end, String text) {
        assert(start >= 0, "start was less than 0");
        assert(end >= start, "end was less than start");
        assert(text != null, "text was null");

        text_ = text_.substring(0, start) + 
                text + 
                text_.substring(end, text_.length());
    }

    public int length() const { return text_.length(); }
    public String toString() const { return text_; }

    private String text_;
}

///////////////////////////////////////
class WordIterator {
    public WordIterator(Buffer buffer) {
        buffer_ = buffer;
        i_ = -1;
    }

    public void add(String word) { assert(false, "WordIterator::add not supported.") }
    public void remove() { assert(false, "WordIterator::remove not supported.") }

    public boolean hasNext() const {
        int j = i_.clone;
        while(++j < buffer_.length()) {
            String currentChar = buffer_.toString().substring(j, j+1);
            if(isAlphabetic(currentChar)) return true;
        }
        return false;
    }

    public String next() {
        String word = "";

        // Advance past non-alphabetic characters
        while(++i_ < buffer_.length()) {
            word = buffer_.toString().substring(i_, i_+1);
            if(isAlphabetic(word)) break;
        }

        // Create and advance past current word
        while(++i_ < buffer_.length()) {
            String currentChar = buffer_.toString().substring(i_, i_+1);
            if(!isAlphabetic(currentChar)) break;
            word = word + currentChar;
        }

        last_ = word.clone;
        return word;
    }

    public void set(String word) {
        assert(word != null, "word was null");

        i_ = i_ - last_.length();
        buffer_.replace(i_, i_ + last_.length(), word);
        i_ = i_ + word.length();
    }

    // TODO: Multi-language support?
    private boolean isAlphabetic(String character) {
        assert(character.length() == 1, "character string length not equal to one.");
        return character == "-" || 
               (character >= "a" && character <= "z") ||
               (character >= "A" && character <= "Z");
    }

    private Buffer buffer_;
    private int i_;
    private String last_;
}

///////////////////////////////////////
class TextSelection implements Buffer {
    public TextSelection(Buffer buffer, int start, int end) {
        assert(start >= 0, "Invalid start select position: " + start.toString());
        assert(start <= end, "End selection position less than start position.");

        buffer_ = buffer;
        start_ = start.clone;
        end_ = end.clone;
    }

    public void append(String text) {
        assert(text != null, "text was null");
        buffer_.append(text);
        end_ = end_ + text.length();
    }

    public void delete(int start, int end) {
        assert(start >= 0, "start was less than 0");
        assert(end >= start, "end was less than start");
        replace(start, end, "");
    }

    public void insert(int position, String text) {
        assert(position >= 0, "position was less than 0");
        assert(text != null, "text was null");

        buffer_.replace(start_ + position, start_ + position, text);
        end_ = end_ + text.length();
    }

    public void replace(int start, int end, String text) {
        assert(start >= 0, "start was less than 0");
        assert(end >= start, "end was less than start");
        assert(text != null, "text was null");

        buffer_.replace(start_ + start, start_ + end, text);
        end_ = end_ + text.length() + start - end;
    }

    public String toString() const { 
        return buffer_.toString().substring(start_, end_);
    }

    public int length() const {
        return end() - start();
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
            buffer_.delete(0, buffer_.length());
            buffer_.append("On the twelveth day of Christmas my true love gave to me\n");
            buffer_.append("twelve drummers drumming, eleven pipers piping, ten lords a-leaping,\n");
            buffer_.append("nine ladies dancing, eight maids a-milking, seven swans a-swimming,\n");
            buffer_.append("six geese a-laying, five gold rings;\n");
            buffer_.append("four calling birds, three french hens, two turtle doves\n");
            buffer_.append("and a partridge in a pear tree.\n");
        } else {
            assert(false, 
                "File '" + filename + "' not found (try 'twelve_days_of_christmas.txt')");
        }
    }

    public String toString() const { return buffer_.toString(); }
    public Buffer getBuffer() const { return buffer_; }

    public void replace(int start, int end, String text) { 
        assert(start >= 0, "start was less than 0");
        assert(end >= start, "end was less than start");
        assert(text != null, "text was null");

        buffer_.replace(start, end, text); 
    }

    private Buffer buffer_;
}

///////////////////////////////////////
// An inverted dictionary. Anything not in it is considered correctly spelled.
class InvertedDictionary {
    public InvertedDictionary() {}

    public List<String> suggestSpelling(String word) const {
        assert(word != null, "word was null.");
        assert(word.length() > 0, "Cannot use an empty String for spelling suggestions.");

        List<String> output = new List<String>();

        switch (word) {
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

            case "french":
                output.add("French");
                break
        }

        return output;
    }
}

///////////////////////////////////////
context SpellCheck {

    role SpellChecker {
        public void advanceToNextWord() {
            if(!hasNext()) return;
            Dictionary.checkSpelling(next());
        }
        public void replaceCurrentSpellingWith(String word) {
            set(word);
        }
    } requires {
        boolean hasNext() const;
        String next();
        void set(String word);
    }

    stageprop Dictionary {
        public void checkSpelling(String word) {
            List<String> suggestions = suggestSpelling(word);

            if(suggestions.size() > 0) {
                // TODO: User input and selection
                SpellChecker.replaceCurrentSpellingWith("[" + suggestions.get(0) + "]");
            }

            SpellChecker.advanceToNextWord();
        }
    } requires {
        List<String> suggestSpelling(String word) const;
    }

    /////////////////////////////////////////

    // NOTE: Exception in thread "AWT-EventQueue-0" java.lang.AssertionError
    // if uncommenting this constructor.
    public SpellCheck(SpellChecker spellChecker) {
        SpellChecker = spellChecker;
        Dictionary = new InvertedDictionary();
    }

    public SpellCheck(Buffer buffer) {
        SpellChecker = new WordIterator(buffer);
        Dictionary = new InvertedDictionary();
    }

    public void start() {
        SpellChecker.advanceToNextWord();
    }
}

///// Entrypoint //////////////////////////////////////////////////

{
    // Load and print the original file
    TextFile file = new TextFile();
    file.load("twelve_days_of_christmas.txt");
    System.out.println(file.toString());

    // Spell check a selection and print it
    TextSelection selection = new TextSelection(file.getBuffer(), 105, 181);
    new SpellCheck(selection).start();
    System.out.println(file.toString());

    // Spell check the whole file and print it
    new SpellCheck(file.getBuffer()).start();
    System.out.println(file.toString());
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
four calling birds, three [French] hens, two turtle doves
and a partridge in a pear tree.

*/
