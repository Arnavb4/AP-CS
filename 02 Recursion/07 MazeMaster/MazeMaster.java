// Name: J6-05-22
// Date: 10/17/22

import java.util.*;
import java.io.*;

public class MazeMaster
{
   public static void main(String[] args)
   {
      Scanner sc = new Scanner(System.in);
      System.out.print("Enter the maze's filename (no .txt): ");
      Maze m = new Maze(sc.next()+".txt");   //append the .txt here
      // Maze m = new Maze();    //extension
      m.display();      
      System.out.println("Options: ");
      System.out.println("1: Mark all dots.");
      System.out.println("2: Mark all dots and display the number of recursive calls.");
      System.out.println("3: Mark only the correct path.");
      System.out.println("4: Mark only the correct path. If no path exists, say so.");
      System.out.println("5: Mark only the correct path and display the number of steps.\n\tIf no path exists, say so.");
      System.out.println("6: Mark only the correct path and list the steps.\n\tIf no path exists, say so.");
      System.out.print("Please make a selection: ");
      m.solve(sc.nextInt());
      m.display();      //display solved maze
   } 
}

class Maze
{
   //constants
   private final char WALL = 'W';
   private final char DOT = '.';
   private final char START = 'S';
   private final char EXIT = 'E';
   private final char TEMP = 'o';
   private final char PATH = '*';
   //instance fields
   private char[][] maze;
   private int startRow, startCol;
  
   //constructors
	
	/* 
	 * EXTENSION 
	 * This is a no-arg constructor that generates a random maze
    * Do not comment it out.  Do not delete it.
	 */
   public Maze()
   {
   
   }
	
	/** 
	 * Constructor.
    * Creates a "deep copy" of the array.
    * We use this in Codepost. 
	 */
   public Maze(char[][] m)  
   {
      maze = m;
      for(int r = 0; r < maze.length; r++)
      {
         for(int c = 0; c < maze[0].length; c++)
         { 
            if(maze[r][c] == START)    //location of start location
            {
               startRow = r;
               startCol = c;
            }
         }
      }
   } 
	
 	/* 
	 * Write this one-arg constructor.
    * the filename already has ".txt"
    * Use a try-catch block.
	 * Use next(), not nextLine() 
    * Search the maze and save the location of 'S' 
	 */
   public Maze(String filename)    
   {
      Scanner infile = null;
      try
      {
         infile = new Scanner(new File(filename));
      }
      catch(Exception e)
      {
         System.out.println("File not found.");
         System.exit(0);
      }
      int rowNum = Integer.parseInt(infile.next().trim());
      int colNum = Integer.parseInt(infile.next().trim());
      maze = new char[rowNum][colNum];
      for(int r = 0; r < maze.length; r++)
      {
         maze[r] = infile.next().trim().toCharArray();
         for(int c = 0; c < maze[0].length; c++)
         { 
            if(maze[r][c] == START)
            {
               startRow = r;
               startCol = c;
            }
         }
      }
      infile.close();
   }
   
   public char[][] getMaze()
   {
      return maze;
   }
   
   public void display()
   {
      if(maze==null) 
         return;
      for(int a = 0; a<maze.length; a++)
      {
         for(int b = 0; b<maze[0].length; b++)
         {
            System.out.print(maze[a][b]);
         }
         System.out.println();
      }
      System.out.println();
   }
   
   public void solve(int n)
   {
      switch(n)
      {
         case 1:
            markAll(startRow, startCol);
            break;
         case 2:
            int count = markAllAndCountRecursions(startRow, startCol);
            System.out.println("Number of recursions = " + count);
            break;
         case 3:
         case 4: 
            if( markTheCorrectPath(startRow, startCol) )
            {}
            else           //use mazeNoPath.txt 
               System.out.println("No path exists."); 
            break;
         case 5:
            if( markCorrectPathAndCountSteps(startRow, startCol, 0) )
            {}
            else           //use mazeNoPath.txt 
               System.out.println("No path exists."); 
            break;
         default:
            System.out.println("File not found");   
      }
   }
   
	/* 
	 * From handout, #1.
	 * Fill the maze, mark every step.
	 * This is a lot like AreaFill.
	 */ 
   public void markAll(int r, int c)
   {
      if(r > maze.length || c > maze[0].length)
         return;
      if (maze[r][c] == DOT)maze[r][c] = PATH;
      if(r > 0 && maze[r - 1][c] == DOT)
         markAll(r-1, c);
      if(r < maze.length - 1 && maze[r + 1][c] == DOT)
         markAll(r+1, c);
      if(c < maze[0].length - 1 && maze[r][c + 1] == DOT)
         markAll(r, c + 1);
      if(c > 0 && maze[r][c - 1] == DOT)
         markAll(r, c - 1);
      return;
   }

	/* 
	 * From handout, #2.
	 * Fill the maze, mark and count every recursive call as you go.
	 * Like AreaFill's counting without a static variable.
	 */ 
   public int markAllAndCountRecursions(int r, int c)
   {
      if(r >= maze.length|| c >= maze[0].length|| c < 0 || r < 0)
         return 1;
      if(maze[r][c] == START)
      {
         return 1 + markAllAndCountRecursions(r + 1, c) + markAllAndCountRecursions(r - 1, c) + markAllAndCountRecursions(r, c + 1) + markAllAndCountRecursions(r, c - 1);
      }
      if(maze[r][c] == DOT)
      {
         maze[r][c] = PATH;
         return 1 + markAllAndCountRecursions(r + 1, c) + markAllAndCountRecursions(r - 1, c) + markAllAndCountRecursions(r, c + 1) + markAllAndCountRecursions(r, c - 1);
      }
      return 1;
   }

   /* 
	 * From handout, #3.
	 * Solve the maze, OR the booleans, and mark the path through it with an asterisk
	 * Recur until you find E, then mark the path.
	 */ 	
   public boolean markTheCorrectPath(int r, int c)
   {
      boolean a = false, b = false, d = false, e = false;
      if(r >= maze.length|| c >= maze[0].length|| c < 0 || r < 0 || maze[r][c] == TEMP || maze[r][c] == WALL)
         return false;
      else if(maze[r][c] == EXIT)
         return true;
      maze[r][c] = TEMP;
      a = markTheCorrectPath(r - 1, c);
      b = markTheCorrectPath(r + 1, c);
      d = markTheCorrectPath(r, c - 1);
      e = markTheCorrectPath(r, c + 1);
      if(a || b || d || e)
      {
         if(c == startCol && r == startRow)
            maze[r][c] = START;
         else
            maze[r][c] = PATH;
         return true;
      }
      maze[r][c] = DOT;
      return false;
   }
	
	
   /*  4   Mark only the correct path. If no path exists, say so.
           Hint:  the method above returns the boolean that you need. */
      

   /* 
	 * From handout, #5.
	 * Solve the maze, mark the path, count the steps. 	 
	 * Mark only the correct path and display the number of steps.
	 * If no path exists, say so.
	 */ 	
   public boolean markCorrectPathAndCountSteps(int r, int c, int count)
   {
      if(r >= maze.length|| c >= maze[0].length|| c < 0 || r < 0 || maze[r][c] == TEMP || maze[r][c] == WALL)
         return false;
      if(maze[r][c] == EXIT)
      {
         System.out.println("Number of Steps = " + count);
         return true;
      }
      maze[r][c] = TEMP;
      if(markCorrectPathAndCountSteps(r, c + 1, count + 1) || markCorrectPathAndCountSteps(r - 1, c, count + 1) || markCorrectPathAndCountSteps(r + 1, c, count + 1) || markCorrectPathAndCountSteps(r, c - 1, count + 1))
      {
         if(c == startCol && r == startRow)
            maze[r][c] = START;
         else
            maze[r][c] = PATH;
         return true;
      }
      maze[r][c] = DOT;
      return false;
   }
}

/*****************************************
 
 ----jGRASP exec: java MazeMaster_teacher
 Enter the maze's filename (no .txt): maze1
 WWWWWWWW
 W....W.W
 WW.WW..W
 W....W.W
 W.W.WW.E
 S.W.WW.W
 WW.....W
 WWWWWWWW
 
 Options: 
 1: Mark all dots.
 2: Mark all dots and display the number of recursive calls.
 3: Mark only the correct path.
 4: Mark only the correct path. If no path exists, say so.
 5: Mark only the correct path and display the number of steps.
 	If no path exists, say so.
 Please make a selection: 1
 WWWWWWWW
 W****W*W
 WW*WW**W
 W****W*W
 W*W*WW*E
 S*W*WW*W
 WW*****W
 WWWWWWWW
 
 
  ----jGRASP: operation complete.
 
  ----jGRASP exec: java MazeMaster_teacher
 Enter the maze's filename (no .txt): maze1
 WWWWWWWW
 W....W.W
 WW.WW..W
 W....W.W
 W.W.WW.E
 S.W.WW.W
 WW.....W
 WWWWWWWW
 
 Options: 
 1: Mark all dots.
 2: Mark all dots and display the number of recursive calls.
 3: Mark only the correct path.
 4: Mark only the correct path. If no path exists, say so.
 5: Mark only the correct path and display the number of steps.
 	If no path exists, say so.
 Please make a selection: 2
 Number of recursions = 105
 WWWWWWWW
 W****W*W
 WW*WW**W
 W****W*W
 W*W*WW*E
 S*W*WW*W
 WW*****W
 WWWWWWWW
 
 
  ----jGRASP: operation complete.
 
  ----jGRASP exec: java MazeMaster_teacher
 Enter the maze's filename (no .txt): maze1
 WWWWWWWW
 W....W.W
 WW.WW..W
 W....W.W
 W.W.WW.E
 S.W.WW.W
 WW.....W
 WWWWWWWW
 
 Options: 
 1: Mark all dots.
 2: Mark all dots and display the number of recursive calls.
 3: Mark only the correct path.
 4: Mark only the correct path. If no path exists, say so.
 5: Mark only the correct path and display the number of steps.
 	If no path exists, say so.
 Please make a selection: 3
 WWWWWWWW
 W....W.W
 WW.WW..W
 W***.W.W
 W*W*WW*E
 S*W*WW*W
 WW.****W
 WWWWWWWW
 
 
  ----jGRASP: operation complete.
 
     
  ----jGRASP exec: java MazeMaster_teacher
 Enter the maze's filename (no .txt): mazeNoPath
 WWWWWWWW
 W....W.W
 WW.WW..E
 W..WW.WW
 W.W.W..W
 S.W.WW.W
 WWW....W
 WWWWWWWW
 
 Options: 
 1: Mark all dots.
 2: Mark all dots and display the number of recursive calls.
 3: Mark only the correct path.
 4: Mark only the correct path. If no path exists, say so.
 5: Mark only the correct path and display the number of steps.
 	If no path exists, say so.
 Please make a selection: 4
 No path exists.
 WWWWWWWW
 W....W.W
 WW.WW..E
 W..WW.WW
 W.W.W..W
 S.W.WW.W
 WWW....W
 WWWWWWWW
 
 
  ----jGRASP: operation complete.
 
  ----jGRASP exec: java MazeMaster_teacher
 Enter the maze's filename (no .txt): maze1
 WWWWWWWW
 W....W.W
 WW.WW..W
 W....W.W
 W.W.WW.E
 S.W.WW.W
 WW.....W
 WWWWWWWW
 
 Options: 
 1: Mark all dots.
 2: Mark all dots and display the number of recursive calls.
 3: Mark only the correct path.
 4: Mark only the correct path. If no path exists, say so.
 5: Mark only the correct path and display the number of steps.
 	If no path exists, say so.
 Please make a selection: 5
 Number of steps = 14
 WWWWWWWW
 W....W.W
 WW.WW..W
 W***.W.W
 W*W*WW*E
 S*W*WW*W
 WW.****W
 WWWWWWWW
 
 
  ----jGRASP: operation complete.
  ********************************************/