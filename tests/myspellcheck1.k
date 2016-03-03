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
   public List<String> wordsFor(String document) {
      String [] results = document.split("[ ,;.]");
      List<String> retval = new List<String>();
      for (String word : results) retval.add(word);
      return retval
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
         CopyEditor.dispatchWord();
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
      public void dispatchWord() {
         String word = CurrentWord.text();
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
      MyScanner parser = new MyScanner();
      Words = parser.wordsFor(buffer);
      CurrentWord = this;
      Dictionary = dictionary;
      CopyEditor = null
   }

   public void spellCheck() {
      Words[0].check()
   }

   public int currentIndex() const { return currentIndex_ }

   public void setBufferIndex(int i) { currentIndex_ = i.clone }

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
