interface Buffer {
    public String getText() const;
}

class TextBuffer implements Buffer {
    public String getText() const {
        String foo = buffer_.getText()
        return foo // buffer_.getText().substring(0, 1);
    }

    private Buffer buffer_;
}

{
    Buffer b = new TextBuffer("12345");
}

/* GOLD:
___________________________________________________________
12345
12
*/