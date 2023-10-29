//name:Arnav Bandam    date: 8/31/22

import java.text.DecimalFormat;
public class SmartCard 
{
   public final static DecimalFormat df = new DecimalFormat("$0.00");
   public final static double MIN_FARE = 0.5;
   /* enter the private fields */
   private double balance;
   private Station station;
   private boolean boarded;
   
   /* the one-arg constructor  */
   public SmartCard(double balance)
   {
      this.balance = balance;
      station = null;
      boarded = false;
   }
   /* write the instance methods  */
   public double getBalance()
   {
      return balance;
   }
   public String getFormattedBalance()
   {
      return df.format(balance);
   }
   public boolean getIsBoarded()
   {
      return boarded;
   }   
   public Station getBoardedAt()
   {
      if (boarded == true)
         return station;
      else
         return null;
   }
   
   public void board(Station s)
   {
      if(boarded == true)
      {
         System.out.println("Error: already boarded?!");
         return;
      }
      if(balance<MIN_FARE)
      {
         System.out.println("Insufficient funds to board. Please add more money.");
         return;
      }
      boarded = true;
      station = s;
   }
   public double cost(Station s)
   {
      int travelled = 0;
      if (station.getZone() - s.getZone() < 0)
         travelled = s.getZone() - station.getZone();
      else
         travelled = station.getZone() - s.getZone();
      return MIN_FARE + 0.75*(travelled);
   }
   public void exit(Station s)
   {
      if (boarded == false)
      {
         System.out.println("Error: Did not board?!");
         return;
      }
      boarded = false;
      balance -= cost(s);
      if (balance<MIN_FARE)
      {
         boarded = true;
         System.out.println("Insufficient funds to exit. Please add more money.");
         balance += cost(s);
         return;
      }
      System.out.print("From " + station.getName() + " to " + s.getName() + " costs " + df.format(cost(s)) + ".");
      System.out.println(" SmartCard has " + df.format(balance));
   }
   public void addMoney(double d)
   {
      balance += d;
      System.out.println(df.format(d) + " added. Your new balance is " + df.format(balance));
   } 
}
   
// ***********  start a new class.  The new class does NOT have public or private.  ***/
class Station
{
   private String name;
   private int zone;
   
   public Station()
   {
      name = "Station";
      zone = 1;
   }
   public Station(String name, int zone)
   {
      this.name = name;
      this.zone = zone;
   }
   
   public String getName()
   {
      return name;
   }
   public int getZone()
   {
      return zone;
   }
}

