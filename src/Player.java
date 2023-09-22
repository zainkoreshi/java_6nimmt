import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Player {
    /**
     * The cards held on a player hand
     */
    private Card[] hand;
    /**
     * The number of card held by the player. This variable should be maintained
     * to match array hand.
     */
    private int handCount;
    /**
     * A dynamic array that holds the score pile.
     */
    private Card[] pile;
    /**
     * The name of the player
     */
    private String name;
    /**
     * A static variable that tells how many player has been initialized
     */
    private static int count = 1;

    /**
     * Other constructor that specify the name of the player.
     * 
     * You need to initialize your data member properly.
     */
    public Player(String name) {

        this.name = name;
        hand = new Card[10];
        pile = new Card[0];
        handCount = 0;

    }

    /**
     * Default constructor that set the name of the player as "Computer #1",
     * "Computer #2", "Computer #3"...
     * The number grows when there are more computer players being created.
     * 
     * You need to initialize your data member properly.
     */
    public Player() {

        this("Computer #" + count);

        count++;

    }

    /**
     * Getter of name
     * 
     * @return - the name of the player
     */
    public String getName() {

        return name;

    }

    /**
     * This method is called when a player is required to take the card from a stack
     * to his score pile. The variable pile should be treated as a dynamic array so
     * that the array will auto-resize to hold enough number of cards. The length of
     * pile should properly record the total number of cards taken by a player.
     * 
     * Important: at the end of this method, you should also help removing all cards
     * from the parameter array "cards".
     * 
     * 
     * 
     * @param cards - an array of cards taken from a stack
     * @param count - number of cards taken from the stack
     */
    public void moveToPile(Card[] cards, int count) {


            // new array with array length of the pile and the stack
            Card[] newArray = new Card[pile.length + count];

            // first transfer all existing cards to the new pile
            for (int i = 0; i < pile.length; i++) {
                newArray[i] = pile[i];
            }

            // then transfer all the new cards picked up
            for (int i = 0, j = pile.length; j < pile.length + count; i++, j++) {
                newArray[j] = cards[i];
                cards[i].print();
                cards[i] = null;
            }

            // the pile now equals to the updated one
            pile = newArray;

    }

    /**
     * This method prompts a human player to play a card. It first print
     * all cards on his hand. Then the player will need to select a card
     * to play by entering the INDEX of the card.
     * 
     * @return - the card to play
     */
    public Card playCard() {

        while (true) {

            // print all cards in the players hand
            for (int i = 0; i < handCount; i++) {
                System.out.println(i + ": " + hand[i]);
            }

            // create a new scanner which asks the player to select a card
            Scanner selector = new Scanner(System.in);
            System.out.println("Please select a card to play: ");

            // here we run the helper method and return the card to be played
            Card card = playCard(selector.nextInt());
            if (card != null) {
                return card;
            }

        }

    }

    /**
     * This method let a computer player to play a card randomly. The computer
     * player will pick any available card to play in a random fashion.
     * 
     * @return - card to play
     */
    public Card playCardRandomly() {

        // play a random card by using a random number
        return  playCard(ThreadLocalRandom.current().nextInt(0, handCount));

//        // location of card
//        int location = 0;
//
//        // pick that card from the hand
//        for (int i = 1; i < handCount; i++) {
//            if (cardNumberToPlay == i) {
//                // for next time, make the hand count one less
//                handCount--;
//                // return the card needed to be played
//                location = i;
//            }
//        }
//
//        // return card to play
//        Card cardToReturn = hand[location];
//
//        Card[] newHand = new Card[hand.length - 1];
//
//        for (int i = 0; i < location; i++) {
//            newHand[i] = hand[i];
//        }
//
//        for (int i = location; i < newHand.length; i++) {
//            newHand[i] = hand[i + 1];
//        }
//
//        // transfer the cards
//        hand = newHand;
//
//        // return the index of the card inputted by the player
//        return cardToReturn;

    }

    // this method has been created for play card and play card randomly
    private Card playCard(int index) {

        // in case the index is out of bounds
        if (index < 0 || index >= handCount) {
            return null;
        }

        // create object for the card
        Card card = hand[index];

        // shift indexes
        for (int i = index; i < handCount - 1; i++) {
            hand[i] = hand[i+1];
        }

        // remove the card
        hand[--handCount] = null;

        // return the card
        return card;

    }

    /**
     * Deal a card to a player. This should add a card to the variable hand and
     * update the variable handCount. During this method, you do not need to resize
     * the array. You can assume that a player will be dealt with at most 10 cards.
     * That is, the method will only be called 10 times on a player.
     * 
     * After each call of this method, the hand should be sorted properly according
     * to the number of the card.
     * 
     * @param card - a card to be dealt
     */
    public void dealCard(Card card) {

        // if there are too many cards
        if (handCount == 10) {
            return;
        }

        // new variable for scanning through cards
        int i;

        // introduce a new temporary card
        for (i = 0; i < handCount; i++) {

            // insert it when the card is less
            if(card.getNumber() < hand[i].getNumber()) {

                // increase the index of the following cards
                for (int j = handCount - 1; j >= i; j--) {
                    hand[j+1] = hand[j];
                }
                break;

            }

        }

        // insert the new card and increase the handcount
        hand[i] = card;
        handCount++;

    }

    /**
     * Get the score of the player by counting the total number of Bull Head in the
     * score pile.
     * 
     * @return - score, 0 or a positive integer
     */
    public int getScore() {

        // new variable called score
        int score = 0;

        // add total number of bullheads in the score pile
        for (Card card : pile) {
            score += card.getBullHead();
        }

        // return value
        return score;

    }

    /**
     * To print the score pile. This method has completed for you.
     * 
     * You don't need to modify it and you should not modify it.
     */
    public void printPile() {
        for (Card c : pile) {
            c.print();
        }
        System.out.println();
    }

    /**
     * This is a getter of hand's card. This method has been completed for you
     *
     * You don't need to modify it and you should not modify it.
     * 
     * @param index - the index of card to take
     * @return - the card from the hand or null
     */
    public Card getHandCard(int index) {
        if (index < 0 || index >= handCount)
            return null;
        return hand[index];
    }
}
