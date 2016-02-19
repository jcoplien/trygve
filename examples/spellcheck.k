class OnlineDictionary {
   public boolean isSpelledOK(String word) const {
      boolean retval = false;
      for (String aWord : words_) {
         if (aWord == word) {
            retval = true;
            break
         }
      }
      return retval
   }
   public String correctionFor(String word) {
      double winningScore = 0.0;
      int winningIndex = -1;
      int dictSize = words_.size();
      int wordLength = word.length();
      for (int wordIndex = 0; wordIndex < dictSize; wordIndex++) {
         String dictWord = words_.get(wordIndex);
         int dictWordLength = dictWord.length();
         double score = 0.0;
         for (int column = 0; column < wordLength; column++) {
            String c = word.substring(column, column + 1);
            int highestColumnScore = 0;

            // See if c appears in the current dict word. Take a
            // single match (where the columns best align)
            for (int dictWordColumn = 0; dictWordColumn < dictWordLength; 
                                                               dictWordColumn++) {
               if (dictWord.substring(dictWordColumn, dictWordColumn+1) == c) {
                  int indexDifference = column - dictWordColumn;
                  indexDifference = if (indexDifference < 0) -indexDifference
                                    else indexDifference;

                  highestColumnScore =
                      if (highestColumnScore < (wordLength - indexDifference))
                           (wordLength - indexDifference)
                      else highestColumnScore
               }
            }
            score = score + highestColumnScore
         }

         if (score > winningScore) {
            winningScore = score.clone;
            winningIndex = wordIndex.clone
         }
      }
      assert (-1 != winningIndex);
      return words_.get(winningIndex)
   }
   
   public OnlineDictionary() {
      words_ = new List<String>();
      words_.add("the");
      words_.add("quick");
      words_.add("brown");
      words_.add("fox");
      words_.add("jumped");
      words_.add("over");
      words_.add("lazy");
      words_.add("dog");
   }
   List<String> words_;
}

// -------------------------------------------------------------------

class MutableString {
   public MutableString(String init) {
      string_ = init.clone
   }
   public int length() const { return string_.length() }
   public String substring(int start, int end) const {
      return string_.substring(start, end)
   }
   public String replaceFirstInSitu(String regex, String rep) {
      string_ = string_.replaceFirst(regex, rep);
      return string_
   }
   public String replaceAll(String replacement) {
      string_ = replacement.clone;
      return string_
   }
   private String string_
}

// -------------------------------------------------------------------

class Range {
   public Range(int a, int b) {
      start_ = a.clone;
      end_ = b.clone
   }
   public int start() { return start_ }
   public int end() { return end_ }

   private int start_, end_
}

// -------------------------------------------------------------------

context SpellCheck {
   role Utilities {
      public boolean isDelim(String c) const {
         boolean retval = switch (c) {
         case "ø": case "Ø": case "æ": case "Æ": case "å": case "Å": false; break
         default: (c < "a" || c > "z") && (c < "A" || c > "Z")
         }
         return retval
      }
   }

   // -------------------------------------------------------------------

   role [] Words {
      public void check() {
          Words[lastIndex-1].review()
      }

      public void replaceWith(String correction) {
         Document.replace(start(), end(), correction)
      }

      private String text() const {
         return Document.charsFrom(start(), end())
      }
      private boolean isYes(String answer) const {
          return (answer == "y" || answer == "Y" ||
                 answer == "Yes" || answer == "yes" ||
                 answer == "")
      }
      private boolean isNo(String answer) const {
          return (answer == "n" || answer == "N" ||
                 answer == "No" || answer == "no")
      }
      private void review() {
         reviewAWord();
         if (index != 0) Words[index-1].review()
      }
      private void reviewAWord() {
         Scanner scanner = new Scanner(System.in);
         String word = this.text();
         if (Utilities.isDelim(word)) return;
         boolean isSpelledCorrectly = Dictionary.isSpelledCorrectly(word);
         if (isSpelledCorrectly == false) {
            String suggestedSpelling =
                        Dictionary.suggestedSpellingOf(word)
            System.out.print("Replace `").print(word)
                       .print("' with `").print(suggestedSpelling)
                       .print("'? ");
            String theAnswer = scanner.nextLine();
            if (isYes(theAnswer)) {
               this.replaceWith(suggestedSpelling)
            } else if (isNo(theAnswer)) {
               ;
            } else {
               suggestedSpelling = theAnswer.clone;
               System.out.print("Replace `").print(word)
                         .print("' with `").print(theAnswer)
                         .print("'? ");
               theAnswer = scanner.nextLine();
               if (isYes(theAnswer)) {
                  this.replaceWith(suggestedSpelling)
               }
            }
         }
      }
   } requires {
      int start() const;
      int end() const;
   }

   // -------------------------------------------------------------------

   role Document {
      public int len() const { return length() }

      public String charsFrom(int start, int end) const {
         return substring(start, end)
      }

      public void replace(int start, int end, String correction) {
         String newDocument = substring(0, start);
         newDocument = newDocument + correction;
         newDocument = newDocument + substring(end, length());
         replaceAll(newDocument)
      }

      public String text() {
         return substring(0, length())
      }

      public List<Range> words() {
         List<Range> retval = new List<Range>();
         int i = 0;
         do {
            String nextWordText = Document.wordStartingAt(i);
            int len = nextWordText.length();
            Range newWord = new Range(i, i = i + len);
            retval.add(newWord);
         } while (i < Document.len());
         return retval
      }

      private String wordStartingAt(int start) {
         String retval = "";
         String c = charAt(start);
         if (Utilities.isDelim(c)) {
            retval = c.clone;
         } else {
            for (int i = start.clone; i < Document.len(); i++) {
               c = Document.charAt(i);
               if (Utilities.isDelim(c)) break;
               retval = retval + c;
            }
         }
         return retval;
      }

      private String charAt(int i) const {
         return substring(i, i+1)
      }
   } requires {
      int length() const;
      String substring(int start, int end) const;
      String replaceFirst(String regex, String rep);
      String replaceAll(String replacement)
   }

   // -------------------------------------------------------------------

   role Dictionary {
      public boolean isSpelledCorrectly(String word) {
         return isSpelledOK(word)
      }
      public String suggestedSpellingOf(String word) {
         return correctionFor(word)
      }
   } requires {
      boolean isSpelledOK(String word) const;
      String correctionFor(String word)
   }

   // -------------------------------------------------------------------

   public SpellCheck(String buffer, OnlineDictionary dictionary) {
      Utilities = this;
      MutableString mutableBuffer = new MutableString(buffer);
      Document = mutableBuffer;
      Words = Document.words();
      Dictionary = dictionary
   }

   public void spellCheck() {
      Words.check()
   }

   public String resultsText() {
     return Document.text()
   }
}

{
   OnlineDictionary dict = new OnlineDictionary();
   String text = "teh quick brown føx jumped over the lasy dog";
   System.out.println(text);
   SpellCheck spellChecker = new SpellCheck(text, dict);
   spellChecker.spellCheck();
   System.out.println(spellChecker.resultsText())
}

/* GOLD:

*/
