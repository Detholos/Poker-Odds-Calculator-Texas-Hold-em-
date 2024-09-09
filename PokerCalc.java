import java.util.ArrayList;
import java.util.Collections;

public class PokerCalc {

    
    public static int handStrength(ArrayList<Card> hand) {
        // Check for Royal Flush
        if (isRoyalFlush(hand)) {
            return 1000000;
        }

        // Check for Straight Flush
        int straightFlushHigh = isStraightFlush(hand);
        if (straightFlushHigh > 0) {
            return 900000 + straightFlushHigh;
        }

        // Check for Four of a Kind
        int fourKind = ofAKind(hand, 4);
        if (fourKind > 0) {
            int kicker = getHighestKicker(hand, fourKind);
            return 800000 + fourKind * 100 + kicker;
        }

        // Check for Full House
        int fullHouse = isFullHouse(hand);
        if (fullHouse > 0) {
            return 700000 + fullHouse;
        }

        // Check for Flush
        int flushScore = isFlush(hand);
        if (flushScore > 0) {
            return flushScore;
        }

        // Check for Straight
        int straightHigh = isStraight(hand);
        if (straightHigh > 0) {
            return 500000 + straightHigh;
        }

        // Check for Three of a Kind
        int threeKind = ofAKind(hand, 3);
        if (threeKind > 0) {
            ArrayList<Integer> kickers = getKickers(hand, 3, threeKind);
            return 400000 + threeKind * 1000 + kickers.get(0) * 100 + kickers.get(1);
        }

        // Check for Two Pair
        int twoPairScore = isTwoPair(hand);
        if (twoPairScore > 0) {
            return 300000 + twoPairScore;
        }

        // Check for One Pair
        int onePair = ofAKind(hand, 2);
        if (onePair > 0) {
            ArrayList<Integer> kickers = getKickers(hand, 2, onePair);
            return 200000 + onePair * 1000 + kickers.get(0) * 100 + kickers.get(1) * 10 + kickers.get(2);
        }

        // High Card
        ArrayList<Integer> highCards = getHighCards(hand, 5);
        int highCardScore = 100000;
        for (int i = 0; i < highCards.size(); i++) {
            highCardScore += highCards.get(i) * Math.pow(10, 4 - i);
        }
        return highCardScore;
    }

   
    public static int ofAKind(ArrayList<Card> hand, int kindAmount) {
        int[] ranks = new int[15];
        for (Card card : hand) {
            ranks[card.value()]++;
        }

        for (int i = 14; i >= 2; i--) {
            if (ranks[i] == kindAmount) {
                return i;
            }
        }
        return 0;
    }

   
    public static int isTwoPair(ArrayList<Card> hand) {
        int[] ranks = new int[15];
        for (Card card : hand) {
            ranks[card.value()]++;
        }

        ArrayList<Integer> pairs = new ArrayList<>();
        for (int i = 14; i >= 2; i--) {
            if (ranks[i] >= 2) {
                pairs.add(i);
            }
        }

        if (pairs.size() >= 2) {
            int highPair = pairs.get(0);
            int lowPair = pairs.get(1);
            // Find the highest kicker not in the two pairs
            int kicker = 0;
            for (int i = 14; i >= 2; i--) {
                if (ranks[i] > 0 && i != highPair && i != lowPair) {
                    kicker = i;
                    break;
                }
            }
            return highPair * 100 + lowPair * 10 + kicker;
        }
        return 0;
    }

  
    public static int isFullHouse(ArrayList<Card> hand) {
        int trip = ofAKind(hand, 3);
        if (trip == 0) return 0;
        // Find the highest pair excluding the trip
        int[] ranks = new int[15];
        for (Card card : hand) {
            ranks[card.value()]++;
        }
        int pair = 0;
        for (int i = 14; i >= 2; i--) {
            if (ranks[i] >= 2 && i != trip) {
                pair = i;
                break;
            }
        }
        if (pair > 0) {
            return trip * 100 + pair;
        }
        return 0;
    }

    
    public static int isFlush(ArrayList<Card> hand) {
        int[] suits = new int[4];
        for (Card card : hand) {
            suits[card.suitNum()]++;
        }

        for (int i = 0; i < 4; i++) {
            if (suits[i] >= 5) {
                // Find top 5 flush cards
                ArrayList<Integer> flushCards = getTopFlushCards(hand, 5, i);
                // Encode flush score
                int score = 600000;
                for (int j = 0; j < flushCards.size(); j++) {
                    score += flushCards.get(j) * Math.pow(10, 4 - j);
                }
                return score;
            }
        }
        return 0;
    }

   
    public static ArrayList<Integer> getTopFlushCards(ArrayList<Card> hand, int count, int suit) {
        ArrayList<Card> flushCards = new ArrayList<>();
        for (Card card : hand) {
            if (card.suitNum() == suit) {
                flushCards.add(card);
            }
        }
        // Sort descending
        Collections.sort(flushCards, (c1, c2) -> c2.value() - c1.value());

        ArrayList<Integer> topCards = new ArrayList<>();
        for (int i = 0; i < count && i < flushCards.size(); i++) {
            topCards.add(flushCards.get(i).value());
        }
        return topCards;
    }

   
    public static int isStraightFlush(ArrayList<Card> hand) {
        for (int suit = 0; suit < 4; suit++) {
            boolean[] presence = new boolean[15];
            for (Card card : hand) {
                if (card.suitNum() == suit) {
                    presence[card.value()] = true;
                    if (card.value() == 14) presence[1] = true; // Ace as 1
                }
            }
            for (int i = 10; i >= 1; i--) {
                if (presence[i] && presence[i + 1] && presence[i + 2] && presence[i + 3] && presence[i + 4]) {
                    return i + 4;
                }
            }
        }
        return 0;
    }

   
    public static boolean isRoyalFlush(ArrayList<Card> hand) {
        for (int suit = 0; suit < 4; suit++) {
            boolean has10 = false, hasJ = false, hasQ = false, hasK = false, hasA = false;
            for (Card card : hand) {
                if (card.suitNum() == suit) {
                    if (card.value() == 10) has10 = true;
                    if (card.value() == 11) hasJ = true;
                    if (card.value() == 12) hasQ = true;
                    if (card.value() == 13) hasK = true;
                    if (card.value() == 14) hasA = true;
                }
            }
            if (has10 && hasJ && hasQ && hasK && hasA) {
                return true;
            }
        }
        return false;
    }

   
    public static int isStraight(ArrayList<Card> hand) {
        boolean[] presence = new boolean[15];
        for (Card card : hand) {
            presence[card.value()] = true;
            if (card.value() == 14) presence[1] = true; // Ace as 1
        }

        for (int i = 10; i >= 1; i--) {
            if (presence[i] && presence[i + 1] && presence[i + 2] && presence[i + 3] && presence[i + 4]) {
                return i + 4;
            }
        }
        return 0;
    }

   
    public static int getHighestKicker(ArrayList<Card> hand, int excludedValue) {
        int high = 0;
        for (Card card : hand) {
            if (card.value() != excludedValue && card.value() > high) {
                high = card.value();
            }
        }
        return high;
    }

  
    public static ArrayList<Integer> getKickers(ArrayList<Card> hand, int kindAmount, int excludedValue) {
        ArrayList<Integer> kickers = new ArrayList<>();
        int count = 0;
        for (Card card : hand) {
            if (card.value() != excludedValue) {
                kickers.add(card.value());
            } else {
                count++;
                if (count >= kindAmount) {
                    // Exclude only the necessary count of excludedValue
                    continue;
                }
            }
        }
        Collections.sort(kickers, Collections.reverseOrder());
        return kickers;
    }

    
    public static ArrayList<Integer> getHighCards(ArrayList<Card> hand, int count) {
        ArrayList<Integer> values = new ArrayList<>();
        for (Card card : hand) {
            values.add(card.value());
        }
        Collections.sort(values, Collections.reverseOrder());
        ArrayList<Integer> highCards = new ArrayList<>();
        for (int i = 0; i < count && i < values.size(); i++) {
            highCards.add(values.get(i));
        }
        return highCards;
    }
}
