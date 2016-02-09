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
         String retval =
            switch (word) {
            case "teh": "the" break
            case "lasy": "lazy" break
            default: word
            }
         return retval
      }
   } requires {
      boolean isSpelledOK(String word) const
   }
   role CopyEditor {
      public void dispatchWord() {
         String word = CurrentWord.text();
         boolean isSpelledCorrectly =
                              Dictionary.isSpelledCorrectly(word);
         if (isSpelledCorrectly == false) {
             String suggestedSpelling =
                        Dictionary.suggestedSpellingOf(word)
             System.out.print("Replace `").print(word)
                       .print("' with `").print(suggestedSpelling)
                       .print("'?");
             Scanner scanner = new Scanner(System.in);
             String answer = scanner.nextLine();
             // String answer = "Y";
             if (answer == "y" || answer == "Y" ||
                 answer == "Yes" || answer == "yes") {
                 CurrentWord.replaceWith(suggestedSpelling)
             }
         }
      }
   }

   public SpellCheck(String buffer, OnlineDictionary dictionary) {
      MyScanner parser = new MyScanner();
      Words = parser.wordsFor(buffer);
      CurrentWord = this;
      Dictionary = dictionary
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
