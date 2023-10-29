// Name: J6-05-22   
// Date: 9/9/22
import java.util.*;
import java.io.*;
public class PigLatin
{
   public static void main(String[] args) 
   {
      //part_1_using_pig();
      part_2_using_piglatenizeFile();
      
      /*  extension only    
       String pigLatin = pig("What!?");
       System.out.print(pigLatin + "\t\t" + pigReverse(pigLatin));   //Yahwta!?
       pigLatin = pig("{(Hello!)}!?");
       System.out.print("\n" + pigLatin + "\t\t" + pigReverse(pigLatin)); //{(Yaholle!)}
       pigLatin = pig("\"McDonald???\"");
       System.out.println("\n" + pigLatin + "  " + pigReverse(pigLatin));//"YaDcmdlano???"
      */
   }

   public static void part_1_using_pig()
   {
      Scanner sc = new Scanner(System.in); //input from the keyboard
      while(true)
      {
         System.out.print("\nWhat word? ");
         String s = sc.next();     //reads up to white space
         if(s.equals("-1"))
         {
            System.out.println("Goodbye!"); 
            System.exit(0);
         }
         String p = pig(s);
         System.out.println( p );
      }		
   }

   public static final String punct = ",./;:'\"?<>[]{}|`~!@#$%^&*()";
   public static final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
   public static final String vowels = "AEIOUaeiou";
   public static String pig(String s)
   {
      if(s.length() == 0)
         return "";
   
      //remove and store the beginning punctuation
      String beginPunct = "";
      int beginPunctIndex = 0;
      while(!letters.contains(s.substring(beginPunctIndex, beginPunctIndex + 1)))
      {
         beginPunctIndex+=1;
      }
      beginPunct = s.substring(0,beginPunctIndex);
      s=s.substring(beginPunctIndex);
          
      //remove and store the ending punctuation 
      String endPunct = "";
      boolean flag1 = true;
      for(int i = s.length(); i > 0; i--)
      {
         if(punct.contains(s.substring(i - 1, i)) && flag1)
         {
            endPunct = s.substring(i - 1, i) + endPunct;
            s = s.substring(0, i - 1); 
         }
         else
            flag1 = false;
      }  
         
      //START HERE with the basic case:
      //     find the index of the first vowel
      //     y is a vowel if it is not the first letter
      //     qu
      String original = s;
      String modified = s;
      int vowelIndex = 0;
      boolean flag2 = true;
      for(int i = 0; i<s.length(); i++)
      {
         if(i == 0)
         {
            if(vowels.contains(s.substring(0, 1)) && flag2)
            {
               modified += "way";
               flag2 = false;
            }
               
         }
         else if((!vowels.contains(s.substring(0, 1))) && (s.substring(1).equals("y") || s.substring(1).equals("Y")))
         {
            s = s.substring(1) + s.substring(0, 1) + "way";
         }   
         else if(((vowels.contains(s.substring(i, i+1)) && (!s.substring(i - 1, i + 1).equals("qu")&& !s.substring(i - 1, i + 1).equals("Qu") &&!s.substring(i - 1, i + 1).equals("qU") &&!s.substring(i - 1, i + 1).equals("QU")))||(s.substring(i, i + 1).equals("y"))) && flag2)
         {
            modified = s.substring(i) + s.substring(0, i) + "ay";
            vowelIndex = i;
            flag2 = false;
         }
            
      }
      
      //if no vowel has been found
      boolean vowel = false;
      for (int i = 0; i < s.length(); i++)
      {
         if (vowels.contains(s.substring(i, i+1)))
            vowel = true;
      }
      if(vowel == false)
         return "**** NO VOWEL ****";
      
      //is the first letter capitalized?
      char capitalCheck = original.charAt(0);
      if(Character.isUpperCase(capitalCheck) == true)
      {
         int firstChar = original.length() - vowelIndex;
         String oldFirst = modified.substring(firstChar, firstChar + 1);
         oldFirst = oldFirst.toLowerCase();
         modified = modified.substring(0,1).toUpperCase() + modified.substring(1, firstChar) + oldFirst + modified.substring(firstChar + 1);
      }
      //return the piglatinized word
      return beginPunct + modified + endPunct;
   }

   public static void part_2_using_piglatenizeFile() 
   {
      Scanner sc = new Scanner(System.in);
      System.out.print("input filename including .txt: ");
      String fileNameIn = sc.next();
      System.out.print("output filename including .txt: ");
      String fileNameOut = sc.next();
      piglatenizeFile( fileNameIn, fileNameOut );
      System.out.println("Piglatin done!");
   }

/****************************** 
*  piglatinizes each word in each line of the input file
*    precondition:  both fileNames include .txt
*    postcondition:  output a piglatinized .txt file 
******************************/
   public static void piglatenizeFile(String fileNameIn, String fileNameOut) 
   {
      Scanner infile = null;
      try
      {
         infile = new Scanner(new File(fileNameIn));  
      }
      catch(IOException e)
      {
         System.out.println("oops");
         System.exit(0);   
      }
   
      PrintWriter outfile = null;
      try
      {
         outfile = new PrintWriter(new FileWriter(fileNameOut));
      }
      catch(IOException e)
      {
         System.out.println("File not created");
         System.exit(0);
      }
   	//process each word in each line
      while(infile.hasNextLine())
      {
         String currentLine = infile.nextLine();
         String[] words = currentLine.split(" ");
         String line = "";
         for(int i = 0; i < words.length; i++)
         {
            line += (pig(words[i]) + " ");
         }
         outfile.println(line);
      }
      
      
      infile.close();
      outfile.close();
      
   }
   
   /** EXTENSION: Output each PigLatin word in reverse, preserving before-and-after 
       punctuation.  
   */
   public static String pigReverse(String s)
   {
      if(s.length() == 0)
         return "";
         
      String beginPunct = "";
      int beginPunctIndex = 0;
      while(!letters.contains(s.substring(beginPunctIndex, beginPunctIndex + 1)))
      {
         beginPunctIndex+=1;
      }
      beginPunct = s.substring(0,beginPunctIndex);
      s=s.substring(beginPunctIndex);
          
      //remove and store the ending punctuation 
      String endPunct = "";
      boolean flag1 = true;
      for(int i = s.length(); i > 0; i--)
      {
         if(punct.contains(s.substring(i - 1, i)) && flag1)
         {
            endPunct = s.substring(i - 1, i) + endPunct;
            s = s.substring(0, i - 1); 
         }
         else
            flag1 = false;
      }
      boolean upperCase = false;
      if (Character.isUpperCase(s.charAt(0)))
      {
         upperCase = true;
      }
      String reversed = "";
      for(int i = 0; i < s.length(); i++)
      {
         reversed = s.substring(i, i + 1) + reversed;
      }
      if(upperCase)
         return beginPunct + reversed.substring(0, 1).toUpperCase() + reversed.substring(1, s.length() - 1) + reversed.substring(s.length()-1, s.length()).toLowerCase() + endPunct;
      else
         return beginPunct + reversed + endPunct;     
   }
}
