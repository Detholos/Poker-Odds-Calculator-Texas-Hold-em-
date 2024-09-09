import java.util.ArrayList;

class ProbCalc {

    public static int Twins;
    public static int Tlosses;
    public static int Tties;

  
    public static void calc(ArrayList<Card> h1, ArrayList<Card> h2) {
        Twins = 0;
        Tlosses = 0;
        Tties = 0;

        // note, community piles in everything essentially
        ArrayList<Card> remainingDeck = new ArrayList<>(Deck.deck);
        remainingDeck.removeAll(h1);
        remainingDeck.removeAll(h2);

        int deckSize = remainingDeck.size();
        // Iterate through all possible 5-card community combinations (comp intensive, 5 sec usually)
        for (int i = 0; i < deckSize - 4; i++) {
            Card c1 = remainingDeck.get(i);
            for (int j = i + 1; j < deckSize - 3; j++) {
                Card c2 = remainingDeck.get(j);
                for (int k = j + 1; k < deckSize - 2; k++) {
                    Card c3 = remainingDeck.get(k);
                    for (int l = k + 1; l < deckSize - 1; l++) {
                        Card c4 = remainingDeck.get(l);
                        for (int m = l + 1; m < deckSize; m++) {
                            Card c5 = remainingDeck.get(m);

                            // community card pile in 
                            ArrayList<Card> community = new ArrayList<>();
                            community.add(c1);
                            community.add(c2);
                            community.add(c3);
                            community.add(c4);
                            community.add(c5);

                            // Combine player hands with community cards
                            ArrayList<Card> hand1Full = new ArrayList<>(h1);
                            ArrayList<Card> hand2Full = new ArrayList<>(h2);

                            hand1Full.addAll(community);
                            hand2Full.addAll(community);

                            // Calc hand strengths
                            int h1val = PokerCalc.handStrength(hand1Full);
                            int h2val = PokerCalc.handStrength(hand2Full);

                            // Comparison
                            if (h1val > h2val) {
                                Twins++;
                            } else if (h1val < h2val) {
                                Tlosses++;
                            } else {
                                Tties++;
                            }
                        }
                    }
                }
            }
        }
    }

    // the WLT code
    
    public static double[] returnPercentages() {
        double[] winLossTie = new double[3];
        double total = Twins + Tlosses + Tties;

        if (total > 0) {
            winLossTie[0] = (double) (Twins / total) * 100;
            winLossTie[1] = (double) (Tlosses / total) * 100;
            winLossTie[2] = (double) (Tties / total) * 100;
        } else {
            winLossTie[0] = 0.0;
            winLossTie[1] = 0.0;
            winLossTie[2] = 0.0; //for debug purposes
        }
        return winLossTie;
    }
}
