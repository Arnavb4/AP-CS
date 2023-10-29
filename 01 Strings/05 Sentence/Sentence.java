// Name: J6-05-22  
// Date: 9/16/22
  
public class Sentence
{
   private String mySentence;
   private int myNumWords;
   
   //Precondition:  str is not empty.
   //               Words in str separated by exactly one blank.
   public Sentence( String str )
   { 
      mySentence = str;
      String[] count = str.split(" ");
      myNumWords = count.length;
   }
   
   public int getNumWords()
   {  
      return myNumWords;  
   }
   
   public String getSentence()
   {
      return mySentence; 
   }
   
   //Returns true if mySentence is a palindrome, false otherwise.
   //calls the 3-arg isPalindrome(String, int, int)
   public boolean isPalindrome()
   {
      String modified = lowerCase(mySentence);
      modified = removeBlanks(modified);
      modified = removePunctuation(modified);
      return (isPalindrome(modified, 0, modified.length() - 1));  
   }
   //Precondition: s has no blanks, no punctuation, and is in lower case.
   //Recursive method.
   //Returns true if s is a palindrome, false otherwise.
   public static boolean isPalindrome( String s, int start, int end )
   {
      if(s.length() == 1||s.length() == 0)
         return true;
      else if(start == end)
         return true;
      else if ((end - start == 1) && (s.charAt(start) == s.charAt(end)))
         return true;
      else
      {
         if(s.charAt(start) != s.charAt(end))
            return false;
         else
            return isPalindrome(s, start + 1, end - 1);
      }
   }
   //Returns copy of String s with all blanks removed.
   //Postcondition:  Returned string contains just one word.
   public static String removeBlanks( String s )
   {  
      String[] words = s.split(" ");
      s = "";
      for(int i = 0; i<words.length; i++)
      {
         s += words[i];
      }
      return s.trim();
   }
   
   //Returns copy of String s with all letters in lowercase.
   //Postcondition:  Number of words in returned string equals
   //						number of words in s.
   public static String lowerCase( String s )
   {  
      return s.toLowerCase();
   }
   
   //Returns copy of String s with all punctuation removed.
   //Postcondition:  Number of words in returned string equals
   //						number of words in s.
   public static String removePunctuation( String s )
   { 
      String punct = ".,'?!:;\"(){}[]<>"; 
      for(int i = 0; i < punct.length(); i++)
      {
         s = s.replace("" + punct.charAt(i), "");
      }
      return s;
   }
}

 /*****************************************
   
 PALINDROME TESTER
 "Hello there!" she said.
 4
 false
 
 A Santa lived as a devil at NASA.
 8
 true
 
 Flo, gin is a sin! I golf.
 7
 true
 
 Eva, can I stab bats in a cave?
 8
 true
 
 Madam, I'm Adam.
 3
 true

 **********************************************/

