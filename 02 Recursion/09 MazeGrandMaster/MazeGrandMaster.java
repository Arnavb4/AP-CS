import java.util.*;
import java.io.*;
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
            if(maze[r][c] == START)      //identify start
            {
               startRow = r;
               startCol = c;
            }
         }
      }
   } 
	
  /** 
	 * Write this one-arg constructor.
    * Use a try-catch block.
	 * Use next(), not nextLine() 
    * Search the maze to find the location of 'S' 
	 */
   public Maze(String filename)    
   {
      Scanner infile = null;
      try
      {
         infile = new Scanner(new File(filename));
      }
      catch (Exception e)
      {
         System.out.println("File not found");
         
      }
      int r=infile.nextInt();
      int c=infile.nextInt();
      char[][] arr=new char[r][c];
      
      for(int i=0; i<r; i++){
         String line=infile.next();
         for(int j=0; j<c; j++){
            arr[i][j]=line.charAt(j);
            if(arr[i][j]==START){
               startRow=i;
               startCol=j;
            }
         }
      }
      maze=arr;
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
            int shortestPath = findShortestLengthPath(startRow, startCol);
            if( shortestPath < 999 )
               System.out.println("Shortest path is " + shortestPath);
            else
               System.out.println("No path exists."); 
            display();
            break;
            
         case 2:
            String strShortestPath = findShortestPath(startRow, startCol);
            if( strShortestPath.length()!=0 )
            {
               System.out.println("Shortest length path is: " + getPathLength(strShortestPath));
               System.out.println("Shortest path is: " + strShortestPath);
               markPath(strShortestPath);
               display();  //display solved maze
            }
            else
               System.out.println("No path exists."); 
            break;
         default:
            System.out.println("File not found");   
      }
   }
   
   
 /*  MazeGrandMaster 1   
     recur until you find E, then return the shortest path
     returns 999 if it fails
     precondition: Start can't match with Exit
 */ 
   public int findShortestLengthPath(int r, int c)
   {
      if(r<0||c<0 || r>= maze.length || c >= maze[0].length)
         return 999;
      if(maze[r][c]==EXIT)
         return 1;
      if(maze[r][c]==DOT || maze[r][c] == START)
      {
         maze[r][c]=TEMP;
         int num=findShortestLengthPath(r-1, c);
         int num2=findShortestLengthPath(r+1, c);
         int num3=findShortestLengthPath(r, c-1);
         int num4=findShortestLengthPath(r, c+1);
         int smallest = Math.min(num, Math.min(num2, Math.min(num3, num4)));
         if (smallest >= 999)
            return 999;
         maze[r][c]=DOT;
         if (r == startRow && c == startCol)
            maze[r][c] = START;
         return 1 + smallest;
          
      }
      return 999;
   }  
   
/*  MazeGrandMaster 2   
    recur until you find E, then build the path with (r,c) locations
    and the number of steps, e.g. ((5,0),10),((5,1),9),((6,1),8),((6,2),7),
    ((6,3),6),((6,4),5),((6,5),4),((6,6),3),((5,6),2),((4,6),1),((4,7),0)

    as you build, choose the shortest path at each step
    returns empty String if there is no path
    precondition: Start can't match with Exit
 */
   public String findShortestPath(int r, int c)
   {
      if(r<0||c<0)
         return "";
      if(r==maze.length || c==maze[0].length)
         return "";
      if(maze[r][c]==EXIT)
         return "(("+r+","+c+"),0)";
      if(maze[r][c]==DOT||maze[r][c]==START){
         if(maze[r][c]!=START)
            maze[r][c]=TEMP;
         String num=findShortestPath(r-1, c);
         String num2=findShortestPath(r+1, c);
         String num3=findShortestPath(r, c-1);
         String num4=findShortestPath(r, c+1);
         if((num2.length()<num.length()&&num2.length()!=0) || num.length()==0)
            num=num2;
         if((num3.length()<num.length()&&num3.length()!=0) || num.length()==0)
            num=num3;   
         if((num4.length()<num.length()&&num4.length()!=0) || num.length()==0)
            num=num4;
         if(maze[r][c]!=START)
            maze[r][c]=DOT;
         if(num.equals(""))
            return "";
         int place=num.indexOf("),")+2;
         int ret=Integer.parseInt(num.substring(place, num.indexOf(")", place)));
         String add="(("+r+","+c+"),"+(ret+1)+"),";
         return add+num;
          
      }
      return "";
   }	

	/** MazeGrandMaster 2 
       returns the length, i.e., third number when the format of the strPath is 
            "((3,4),10),((3,5),9),..."
       returns 999 if the string is empty
       precondition: strPath is either empty or follows the format above
    */
   public int getPathLength(String strPath)
   {
      strPath= strPath.substring(strPath.indexOf(")")+2);
      return Integer.parseInt(strPath.substring(0, strPath.indexOf(")")));
   } 

  /** MazeGrandMaster 2 
      recursive method that takes a String created by findShortestPath(r, c)     
      in the form of ((5,0),10),((5,1),9),((6,1),8),((6,2),7),
              ((6,3),6),((6,4),5),((6,5),4),((6,6),3),((5,6),2),((4,6),1),
              ((4,7),0) and marks the actual path in the maze 
      precondition: the String is either an empty String or one that    
                    has the format shown above
                    the (r,c) must be correct for this method to work 
   */
   public void markPath(String strPath)
   {
      if(strPath.equals(""))
         return;
      int com=strPath.indexOf(",");
      int row=Integer.parseInt(strPath.substring(2, com));
      int col=Integer.parseInt(strPath.substring(com+1, strPath.indexOf(")")));
      if(maze[row][col]==EXIT)
         return;
      if(maze[row][col]!=START)
         maze[row][col]=PATH;
      markPath(strPath.substring(strPath.indexOf(",((")+1)); 
      
      /*  enter your code  below  */   
   }
}
   /*************************************************************
 ----jGRASP exec: java MazeGrandMaster_teacher
 Enter the maze's filename (no .txt): maze1
 WWWWWWWW
 W....W.W
 WW.W...W
 W....W.W
 W.W.WW.E
 S.W.WW.W
 W......W
 WWWWWWWW
 
 Options: 
 1: Length of the shortest path
 	If no path exists, say so.
 2: Length of the shortest path
 	List the shortest path
 	Display the shortest path
 	If no path exists, say so.
 Please make a selection: 1
 Shortest path is 10
 WWWWWWWW
 W....W.W
 WW.W...W
 W....W.W
 W.W.WW.E
 S.W.WW.W
 W......W
 WWWWWWWW
 
 
  ----jGRASP: operation complete.

 ******************************************************************/
 /**************************************************************
      ----jGRASP exec: java MazeGrandMaster_teacher
 Enter the maze's filename (no .txt): maze1
 WWWWWWWW
 W....W.W
 WW.W...W
 W....W.W
 W.W.WW.E
 S.W.WW.W
 W......W
 WWWWWWWW
 
 Options: 
 1: Length of the shortest path
 	If no path exists, say so.
 2: Length of the shortest path
 	List the shortest path
 	Display the shortest path
 	If no path exists, say so.
 Please make a selection: 2
 Shortest length path is: 10
 Shortest path is: ((5,0),10),((5,1),9),((6,1),8),((6,2),7),((6,3),6),((6,4),5),((6,5),4),((6,6),3),((5,6),2),((4,6),1),((4,7),0)
 WWWWWWWW
 W....W.W
 WW.W...W
 W....W.W
 W.W.WW*E
 S*W.WW*W
 W******W
 WWWWWWWW
 
 
  ----jGRASP: operation complete.
  
  ******************************************/