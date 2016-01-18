interface Buffer {
    public void add(String text);
    public void clear();
    public void insert(String text, int position);
    public void splice(int startIndex, int endIndex, String text);

    public int length() const;
    public String getText() const;
}

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

    private int start_;
    private int end_;
    private Buffer buffer_;
}

{
    Buffer b = new TextBuffer("12345");
    TextSelection s = new TextSelection(b, 0, 2);

    System.out.println(b.getText());
    System.out.println(s.getText());
}

/* GOLD:
___________________________________________________________
12345
12
*/