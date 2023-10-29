// Name:J6-05-22    Date: 10/7/22

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class NQueens extends JPanel
{
   // Instance Variables: Encapsulated data for EACH NQueens problem
   private JButton[][] board;
   private int N;
   JSlider speedSlider;
   private int timerDelay;

   public NQueens(int n)
   {
      N = n;
      this.setLayout(new BorderLayout());
   
      JPanel north = new JPanel();
      north.setLayout(new FlowLayout());
      add(north, BorderLayout.NORTH);
      JLabel label = new JLabel( N + "Queens solution");
      north.add(label);
      
      JPanel center = new JPanel();
      center.setLayout(new GridLayout(N,N));
      add(center, BorderLayout.CENTER);
      board = new JButton[N][N];
      for(int r = 0; r < N; r++)
         for(int c = 0; c < N; c++)
         {
            
            board[r][c] = new JButton();
            board[r][c].setBackground(Color.blue);
            board[r][c].setOpaque(true);
            board[r][c].setBorderPainted(false);
            center.add(board[r][c]);
         }
   
      speedSlider = new JSlider();   
      speedSlider.setInverted(true);   
      add(speedSlider, BorderLayout.SOUTH);   
   }

   /** Returns the number of queens to be placed on the board. **/
   public int numQueens()
   {
      return N;   
   }

    /** Solves (or attempts to solve) the N Queens Problem. **/
   public boolean solve()
   {
      return isPlaced(0, 0);
   }

 /**
  * Iteratively try to place the queen in each column.
  * Recursively try the next row.
  **/       
   private boolean isPlaced(int row, int col)
   {
      if(row == N)            //matrix is filled
         return true;
      /*  enter code here  */
      
      for(int colCounter = 0; colCounter < N; colCounter++)
         if(locationIsOK(row, colCounter))
         {
            addQueen(row, colCounter);
            if(isPlaced(row + 1, 0))
               return true;
            removeQueen(row, colCounter);
         }
      return false;
   } 
   
  /** Verify that another queen can't attack this location.
    * You only need to check the locations above this row.
    * Iteration is fine here.
    **/
   private boolean locationIsOK(int r, int c)
   {
      /*  enter code here  */
      for(int i = 0; i < r; i++)
      {
         if(board[i][c].getBackground().equals(Color.RED))
            return false;
      }
      for(int i = 0; i < c; i++)
      {
         if(board[r][i].getBackground().equals(Color.RED))
            return false;
      }
      int diagCounter = 0;
      while(r - diagCounter >= 0 && c - diagCounter >= 0)
      {
         if(board[r - diagCounter][c - diagCounter].getBackground() == Color.RED)
            return false;
         diagCounter ++;
      }
      diagCounter = 0;
      while(r - diagCounter >= 0 && c + diagCounter < N)
      {
         if(board[r - diagCounter][c + diagCounter].getBackground() == Color.RED)
            return false;
         diagCounter ++;
      }
      /*
      for(int i = r; i >= 0; i--)
      {
         for(int j = 0; j < c; j++)
         {
            if(board[i][j].getBackground().equals(Color.RED))
               return false;
         }
      }
      for(int i = r; i >= 0; i--)
      {
         for(int j = c; j >= 0; j--)
         {
            if(board[i][j].getBackground().equals(Color.RED))
               return false;
         }
      }
      */
      /*
      int sum = r + c;
      int diff = r - c;
      boolean diagonal = false;
      boolean queenPlaced = false;
      for(int i = 0; i < N; i++)
         for(int j = 0; j < N; j++)
         {
            if((diff == (i - j))||(sum == (i + j)) || diff == (j - i))
               diagonal = true;
            if(board[i][j].getBackground().equals(Color.RED))
               queenPlaced = true;
            if(queenPlaced && diagonal)
               return false;
         }
      */
      return true;
   }
    /** Adds a queen to the specified location.
       **/
   private void addQueen(int r, int c)
   {
      board[r][c].setBackground(Color.RED);
      pause();
   }

    /** Removes a queen from the specified location.
     **/
   private void removeQueen(int r, int c)
   {
      board[r][c].setBackground(Color.BLUE);
      pause();
   }
   private void pause()
   {
      int timerDelay = speedSlider.getValue(); 
      for(int i=1; i<=timerDelay*1E7; i++)  {}
   }
}