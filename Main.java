import java.util.ArrayList;
import java.util.Scanner;
public class Main {

  public static Deck deck = new Deck(); 
  
  public static void main(String[] args) {
     Scanner Console = new Scanner(System.in);
    
    System.out.println("Welcome to the Poker Odds Calculator. Expect a tolerance of +- .5% for the wins and losses as rules may differ for how kickers are handled among texas hold'em poker. \n To Enter a Card, Enter the number (2-10) or if face/ace, (Q, J, K, or A), followed by the suit (H, D, S, or C).\n");

    ArrayList<Card> hand1 = new ArrayList<Card>();
    ArrayList<Card> hand2 = new ArrayList<Card>(); //inits
    
    
    System.out.println("Hand 1, Card 1: \n");
    hand1.add(deck.takeAt(determinePosition(Console.nextLine())));
    
    System.out.println("Hand 1, Card 2: \n");
    hand1.add(deck.takeAt(determinePosition(Console.nextLine()))); //turns string into int essentially
    
    System.out.println("Hand 2, Card 1: \n");
    hand2.add(deck.takeAt(determinePosition(Console.nextLine())));
    
    System.out.println("Hand 2, Card 2: \n");
    hand2.add(deck.takeAt(determinePosition(Console.nextLine())));
    
    
    
    
    System.out.println(hand1);
    System.out.println(hand2);
    
    ProbCalc.calc(hand1, hand2); //main function for calculations
    
    double[] savior = ProbCalc.returnPercentages();
    
    System.out.println("Win for hand 1: " + Math.round(savior[0] * 100) / 100.0 + "%");
    System.out.println("Win for Hand 2: "+  Math.round(savior[1] * 100) / 100.0 + "%"); //rounding stuff
    System.out.println("Tie for hands: " + Math.round(savior[2] * 100) / 100.0 + "%");
    
    Console.close();
  }
  
  public static int determinePosition(String str) {
    for(int i = 0; i < deck.size(); i++) {
      if(str.equals(deck.get(i).toString())) {
       return i;
      }
    }
    return 9999; //for error, dont be a meanie and type in a goofy thing
  }
}