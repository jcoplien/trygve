class SimpleDictionary {
    public SimpleDictionary() {}

    public List<String> suggestSpelling(String word) const {
        List<String> output = new List<String>();

        switch(word) {
            case "a-milking":
                output.add("milking");
                break;
        }

        return output;
    }
}

context SpellCheck {
    stageprop Dictionary {
        public void checkSpelling(String word) {
            List<String> suggestions = suggestSpelling(word);
            if(suggestions.size() == 0) return;
            // NOTE: Cannot call join, runtime error
            System.out.println(word + " -> [" + join(suggestions) + "]");
        }

        private String join(List<String> words) {
            String output = "";
            int end = words.size()-1;
            for(int i = 0; i <= end; i++) {
                output = output + words.get(i);
                if(i < end) output = output + ", ";
            }
            return output;
        }
    } requires {
        List<String> suggestSpelling(String word) const;
    }

    public SpellCheck() {
        Dictionary = new SimpleDictionary();
    }

    public void test() {
        Dictionary.checkSpelling("a-milking");
    }
}

new SpellCheck().test()

/* GOLD:
___________________________________________________________
a-milking -> [milking]
*/