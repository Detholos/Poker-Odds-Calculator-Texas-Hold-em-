import java.util.ArrayList;

public class Deck {

 public static ArrayList<Card> deck;

  public Deck() {
    
    deck = new ArrayList<Card>();
    
      deck = new ArrayList<Card>();
    for(int i = 0; i < 4; i++)
      for(int j = 2; j < 15; j++)
        deck.add(new Card(i, j));
    
  }

  public Card take() { //takes card from deck           
   return deck.remove(0);
  }

  public Card takeAt(int i) {
    return deck.remove(i);
  }
  
  public void shuffle() {
    
    int rand;
    Card randCard; //temps
    
    for(int i = 0; i < deck.size(); i++) { //does this process for every card 
      
      rand = (int) Math.round(Math.random() * (deck.size() - 1)); 
      randCard = deck.get(rand); //chooses random index on deck, then gets a random card
      
      deck.set(rand, deck.get(i)); 
      deck.set(i, randCard); //sets random card to for loop card, then swaps positions
    }
  }

  public String toString() {
    return deck.toString();
  }

  public int amountKind(int value) { // aces only accept 1 here
    int count = 0;

    for(int i = 0; i < deck.size(); i++)
      if(deck.get(i).value() == value)
        count++;
    
    return count;
  }

  public int amountSuit(char suit) {
    int count = 0;

    for(int i = 0; i < deck.size(); i++)
      if(deck.get(i).suit() == suit)
        count++;

    return count;
  }

  public boolean hasCard(Card card) {
    return deck.contains(card);
  }

  public int size() {
    return deck.size();
  }

  public Card get(int i) {
    return deck.get(i);
  }

}
