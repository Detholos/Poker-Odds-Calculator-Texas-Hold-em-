public class Card {
  
  private int suit, kind;
  
  private char[] suits = {'H', 'D', 'S', 'C'};
  private int[] suitsNumerical = {0, 1, 2, 3};
  private String[] kinds = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"}; 
  private int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
  
  public Card(int suitSet, int kindSet) {
    suit = suitSet;
    kind = kindSet;
  }
  public int getS() {
    return suit;
  }
  public int getK() {
    return kind;
  }

  public String kind() {
    return kinds[kind];
  }

  public int suitNum() {
    return suitsNumerical[suit];
  }

  public char suit() {
    return suits[suit];
  }

  public boolean ace() {
    if(kinds[kind] == "A")
      return true;
    
    return false;
  }

  public int value() {
    return values[kind - 2];
  }

  public String toString() {
    return kinds[kind - 2] + suits[suit];
  }



}