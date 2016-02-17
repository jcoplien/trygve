class OnlineDictionary {
   public boolean isSpelledOK(String word) const {
      boolean retval =
         switch (word) {
         case "the":
         case "quick":
         case "brown":
         case "fox":
         case "jumped":
         case "over":
         case "lazy":
         case "dog": true; break
         default: false
         }
      return retval
   }
   public String correctionFor(String word) {
      String retval =
         switch (word) {
         case "teh": "the" break
         case "lasy": "lazy" break
         default: word
         }
      return retval
   }
}

context MyScanner {
   public MyScanner(String doc) {
      document = doc;
   }
   private boolean isDelim(String c) const {
      boolean isAlpha = (c >= "a" && c <= "z") ||
                        (c >= "A" && c <= "Z");
      return (!isAlpha)
   }
   private String wordStartingAt(int start) {
      String retval = "";
      String c = document.charAt(start);
      if (isDelim(c)) {
         retval = c.clone;
      } else {
         for (int i = start.clone; i < document.len(); i++) {
             c = document.charAt(i);
             if (isDelim(c)) break;
             retval = retval + c;
         }
      }
      System.err.print("retval=").println(retval);
      return retval;
   }
   public List<String> words() {
      List<String> retval = new List<String>();
      int i = 0;
      do {
         String nextWord = wordStartingAt(i);
         retval.add(nextWord);
         i = i + nextWord.length()
      } while (i < document.len());
     
      return retval
   }
   stageprop document {
      public int len() { return length() }
      public String charAt(int i) {
         String retval = substring(i, i+1);
         return retval
      }
   } requires {
      int length() const;
      String substring(int start, int end) const;
   }
}

context SpellCheck {
   role CurrentWord {
      public String text() {
         return Words[currentIndex()].text()
      }
      public void setIndex(int i) { setBufferIndex(i) }
      public void replaceWith(String correction) {
         Words[currentIndex()].replaceWith(correction)
      }
   } requires {
      int currentIndex() const;
      void setBufferIndex(int i)
   }
   role [] Words {
      public void check() {
         CopyEditor.reviewWord();
         if (index < lastIndex) {
            CurrentWord.setIndex(index + 1)
            Words[index+1].check()
         }
      }
      public void replaceWith(String correction) {
         replaceFirst(text(), correction)
      }

      public String text() { return toString() }
   } requires {
      String toString() const;
      String replaceFirst(String regex, String replacement)
   }
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
   role CopyEditor {
      private boolean isYes(String answer) {
          return (answer == "y" || answer == "Y" ||
                 answer == "Yes" || answer == "yes" ||
                 answer == "")
      }
      public void reviewWord() {
         String word = CurrentWord.text();
         if (isDelim(word)) return;
         boolean isSpelledCorrectly =
                              Dictionary.isSpelledCorrectly(word);
         if (isSpelledCorrectly == false) {
            String suggestedSpelling =
                        Dictionary.suggestedSpellingOf(word)
            System.out.print("Replace `").print(word)
                       .print("' with `").print(suggestedSpelling)
                       .print("'? ");
            Scanner scanner = new Scanner(System.in);
            String theAnswer = scanner.nextLine();
            if (isYes(theAnswer)) {
               CurrentWord.replaceWith(suggestedSpelling)
            } else {
               suggestedSpelling = theAnswer.clone;
               System.out.print("Replace `").print(word)
                         .print("' with `").print(theAnswer)
                         .print("'? ");
               theAnswer = scanner.nextLine();
               if (isYes(theAnswer)) {
                  CurrentWord.replaceWith(suggestedSpelling)
               }
            }
         }
      }
   }

   public SpellCheck(String buffer, OnlineDictionary dictionary) {
      MyScanner parser = new MyScanner(buffer);
      Words = parser.words();
      CurrentWord = this;
      Dictionary = dictionary;
      CopyEditor = null
   }

   public void spellCheck() {
      Words[0].check()
   }

   private int currentIndex() const { return currentIndex_ }

   private void setBufferIndex(int i) { currentIndex_ = i.clone }

   private int currentIndex_ = 0
}

{
   OnlineDictionary dict = new OnlineDictionary();
   SpellCheck spellChecker = new SpellCheck(
      "teh quick brown fox jumped over the lasy dog",
      dict
   )
   spellChecker.spellCheck()
}

/* GOLD:
line 3: Roles in `CollectionRole' cannot be played by objects of type `AClass':
line 3: 	`size' needed by Role `CollectionRole' does not appear in interface of `AClass'.
___________________________________________________________
*/
