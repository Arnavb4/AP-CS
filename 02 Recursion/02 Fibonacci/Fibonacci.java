// Name: J6-05-22
// Date: 9/30/22
  
import java.util.*;
public class Fibonacci
{
   public static void main(String[] args)
   {
       long start, end, fib; //why long?
      int lastFibNumber = 43;
      int[] fibNumber = {1};
       System.out.println("\tFibonacci\tBy Iteration\tTime\tby Recursion\t Time");
       for(int n = fibNumber[0]; n <= lastFibNumber; n++)
       { 
           start = System.nanoTime();
           fib = fibIterate(n);
           end = System.nanoTime();
           System.out.print("\t\t\t" + n + "\t\t" + fib + "\t\t\t\t\t" + (end-start)/1000.);
           start = System.nanoTime();   	
           fib = fibRecur(n);      
           end = System.nanoTime();
           System.out.println("\t" + fib + "\t\t\t\t\t" + (end-start)/1000.);
       }
   }
   
   /**
    * Calculates the nth Fibonacci number by interation
    * @param n A variable of type int representing which Fibonacci number
    *          to retrieve
    * @returns A long data type representing the Fibonacci number
    */
   public static long fibIterate(int n)
   {
        if (n == 1 || n == 2)
            return 1;
        long [] arr = new long[n + 1];
        arr[0] = 1;
        arr[1] = 1;
        arr[2] = 1;
        for(int i = 3; i <=n; i++)
            arr[i] = arr[i - 2] + arr[i - 1];
        return arr[n];  
   }

   /**
    * Calculates the nth Fibonacci number by recursion
    * @param n A variable of type int representing which Fibonacci number
    *          to retrieve
    * @returns A long data type representing the Fibonacci number
    */
   public static long fibRecur(int n)
   {
        if (n == 1 || n == 2)
            return 1;
        return fibRecur(n - 1) + fibRecur(n - 2);
   }
}