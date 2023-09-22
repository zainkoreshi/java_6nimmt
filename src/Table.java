import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;

public class Table {

    /**
     * Total number of player. Use this variable whenever possible
     */
    private static final int NUM_OF_PLAYERS = 4;
    /**
     * Total number of cards used in this game. Use this variable whenever possible
     */
    private static final int TOTAL_NUMBER_OF_CARD = 104;
    /**
     * The four stacks of cards on the table.
     */
    private Card[][] stacks = new Card[4][6];
    /**
     * This number of cards of each stack on the table. For example, if the variable
     * stacks stores
     * -------------------------
     * | 0 | 10 13 14 -- -- -- |
     * | 1 | 12 45 -- -- -- -- |
     * | 2 | 51 55 67 77 88 90 |
     * | 3 | 42 -- -- -- -- -- |
     * -------------------------
     * 
     * stacksCount should be {3, 2, 6, 1}.
     * 
     * You are responsible to maintain the data consistency.
     */
    private int[] stacksCount = new int[4];
    /**
     * The array of players
     */
    private Player[] players = new Player[NUM_OF_PLAYERS];

    /**
     * Default constructor
     * 
     * In the constructor, you should perform the following tasks:
     * 
     * 1. Initialize cards for play. You should construct enough number of cards
     * to play. These cards should be unique (i.e., no two cards share the same
     * number). The value of card must be between 1 to 104. The number of bullHead
     * printed on each card can be referred to the rule.
     * 
     * 2. Initialize four player. The first player should be a human player, call
     * "Kevin". The other player should be a computer player. These computer player
     * should have the name "Computer #1", "Computer #2", "Computer #3".
     * 
     * 3. Deal randomly 10 cards to each player. A card can only be dealt to one
     * player. That is, no two players can have the same card.
     * 
     * 4. Deal a card on each stack. The card dealt on the stack should not be dealt
     * to any player. Card dealt on each stack should also be unique (no two stack
     * have the same card).
     * 
     */
    public Table() {

        // initialise cards for play

        // number of rounds variable
        int numberOfCardsDealt = 1;

        // initialising cards for play
        Card[] deck = new Card[TOTAL_NUMBER_OF_CARD];

        // giving values to all the cards within the deck
        for(int i = 0; i < deck.length; i++) {
            deck[i] = new Card(i+1);
        }


        // initialising the players

        // one human player
        players[0] = new Player("Kevin");

        // three computer players
        players[1] = new Player();
        players[2] = new Player();
        players[3] = new Player();


        // deal 10 cards randomly


        // for each player
        for (int l = 0; l < 4; l++) {

            // do for 10 turns
            for (int i = 0; i < 10; i++) {

                // generate a random number between 1 and 104
                int randomCardDrawn = ThreadLocalRandom.current().nextInt(0, deck.length);

                // assign the card to the player
                players[l].dealCard(deck[randomCardDrawn]);

                // new array with which will have one less space every turn
                Card[] updated = new Card[deck.length - 1];

                // transfer all the cards
                for (int j = 0; j < randomCardDrawn; j++) {
                        updated[j] = deck[j];
                }

                for (int j = (randomCardDrawn+1); j < updated.length; j++) {
                    updated[j] = deck[j + 1];
                }

                // now make deck equal to updated
                deck = updated;



            }

        }


        // deal card on each stack

        // for each stack
        for (int l = 0; l < 4; l++) {

            // generate a random number between 1 and 104
            int randomCardDrawn = ThreadLocalRandom.current().nextInt(0, deck.length);

            // assign the card to the stack
            stacks[l][0] = deck[randomCardDrawn];

            // new array with which will have one less space every turn
            Card[] updated = new Card[deck.length - 1];

            // transfer all the cards
            for (int j = 0; j < randomCardDrawn; j++) {
                updated[j] = deck[j];
            }

            for (int j = (randomCardDrawn+1); j < deck.length; j++) {
                updated[j] = deck[j + 1];
            }

            // now make deck equal to updated
            deck = updated;


        }

    }

    /**
     * This method is to find the correct stack that a card should be added to
     * according to the rule. It should return the stack among which top-card of
     * that stack is the largest of those smaller than the card to be placed. (If
     * the rule sounds complicate to you, please refer to the game video.)
     * 
     * In case the card to be place is smaller than the top cards of all stacks,
     * return -1.
     * 
     * @param card - the card to be placed
     * @return the index of stack (0,1,2,3) that the card should be place or -1 if
     *         the card is smaller than all top cards
     */
    public int findStackToAdd(Card card) {

        // create an array which stores all the numbers that are smaller than the card to be placed
        int size = 1;

        Card[] array = new Card[size];

        // now for every card that at the top of the stack which is smaller, increase the size of the
        // array and also add the card to that array
        for (int i = 0; i < 4; i++) {

            if (card.getNumber() > stacks[i][stacksCount[i]].getNumber()) {
                array[i] = stacks[i][stacksCount[i]];
                size++;
            }

        }

        // search for the maximum value within the array
        int maximum = 0;

        for (int j = 0; j < array.length; j++) {

            if (maximum < array[j].getNumber()) {

                maximum = array[j].getNumber();

            }

        }

        // index of the stack to place
        for (int k = 0; k < 4; k++) {

            if (stacks[k][stacksCount[k]].getNumber() == maximum) {

                return k;

            }

        }

        // if the card to be placed is smaller than the top cards of all stacks
        return -1;

    }

    /**
     * To print the stacks on the table. Please refer to the demo program for the
     * format. Within each stack, the card should be printed in ascending order,
     * left to right. However, there is no requirement on the order of stack to
     * print.
     */
    public void print() {

        for (int i = 0; i < stacks.length ; i++) {

            for (int j = 0; j < stacks[i].length; j++) {

                // if a card is present
                if (stacks[i][j] != null) {

                    System.out.print(stacks[i][j] + " ");

                    // update stacks count
                    stacksCount[i]++;

                }

                // if no card is present
                else { System.out.print("-- "); }

            }

            System.out.println();

        }

    }

    /**
     * This method is the main logic of the game. You should create a loop for 10
     * times (running 10 rounds). In each round all players will need to play a
     * card. These cards will be placed to the stacks from small to large according
     * to the rule of the game.
     * 
     * In case a player plays a card smaller than all top cards, he will be
     * selecting one of the stack of cards and take them to his/her own score pile.
     * If the player is a human player, he will be promoted for selection. If the
     * player is a computer player, the computer player will select the "cheapest"
     * stack, i.e. the stack that has fewest bull heads. If there are more than
     * one stack having fewest bull heads, selecting any one of them.
     */
    public void runApp() {
        for (int turn = 0; turn < 10; turn++) {

            System.out.println("----------Table----------");

            // print Table
            print();

            System.out.println("-------------------------");


            // array to store the drawn cards
            Card[] cardsDrawn = new Card[4];

            // for each player
            for (int i = 0; i < players.length; i++) {

                // for the human player
                if (i == 0) {

                    // ask the player to pick a card and make it equal to the card
                    cardsDrawn[i] = players[i].playCard();

                }

                // for the computer players
                else {

                    // pick random cards for computers and transfer
                    cardsDrawn[i] = players[i].playCardRandomly();

                }

            }

            //place each of the cards in the stacks
            for (int l = 0; l < 4; l++) {

                // smallest Card
                Card smallestCard = cardsDrawn[0];

                // assign initial value to smallest card
                do {
                    for(int i = 1; i < cardsDrawn.length; i++) {
                        smallestCard = cardsDrawn[i];
                    }
                }
                while (smallestCard == null);

                // search the array for the smallest card
                for (int j = 1; j < cardsDrawn.length; j++) {

                    if (smallestCard.getNumber() > cardsDrawn[j].getNumber()) {

                        // reassign the value to the smallest card
                        smallestCard = cardsDrawn[j];

                    }

                }

                // update the stacks count array

                // if the card a player plays is not a card smaller than all top cards
                if (findStackToAdd(smallestCard) > 0) {

                    stacks[findStackToAdd(smallestCard)][stacksCount[findStackToAdd(smallestCard)]] = smallestCard;


                }

                // if the card a player plays a card smaller than all top cards
                else if (findStackToAdd(smallestCard) < 0) {

                    // if player is human
                    if (cardsDrawn[0] == smallestCard) {

                        // transfer all the cards to the players pile
                        Scanner in = new Scanner(System.in);
                        System.out.print("Pick a stack to collect the cards:");
                        int indexOfStackToCollect = in.nextInt();

                        for (int q = 0 ; q < stacksCount.length ; q++) {

                            for (int p = 0; p < stacksCount[indexOfStackToCollect]; p++) {

                                players[0].moveToPile(stacks[p], stacksCount[indexOfStackToCollect]);

                            }

                        }

                        // put the card played in its place
                        stacks[indexOfStackToCollect][0] = smallestCard;

                    }

                    // new array to store bullheads in each row
                    int[] numberOfBullHeads = new int[4];

                    // new variable to find row with minimum number of bullheads
                    int minimumNumberOfBullHeads = numberOfBullHeads[0];

                    // if player is computer
                    if (cardsDrawn[1] == smallestCard || cardsDrawn[2] == smallestCard || cardsDrawn[3] == smallestCard) {

                        // add the number of bullheads in each row
                        for (int m = 0 ; m < stacksCount.length ; m++) {

                            for (int n = 0 ; n < stacksCount[m] ; n++) {

                                numberOfBullHeads[m] += stacks[m][n].getBullHead();

                            }

                        }

                        // find the row with the minimum number of bull heads
                        for (int o = 1 ; o < numberOfBullHeads.length; o++) {

                            if (minimumNumberOfBullHeads > numberOfBullHeads[o]) {

                                minimumNumberOfBullHeads = numberOfBullHeads[o];

                            }

                        }

                        // new variables
                        int sizeOfCounterArray = 1;
                        int[] counter = new int[sizeOfCounterArray];


                        // find the number of rows with the minimum number of bull heads
                        for (int o = 0; o < numberOfBullHeads.length; o++) {

                            if (minimumNumberOfBullHeads == numberOfBullHeads[o]) {

                                counter[sizeOfCounterArray-1] = numberOfBullHeads[o];

                                sizeOfCounterArray++;

                            }

                        }


                        // if there are more than one minimum values, select a random one from any of them
                        if (sizeOfCounterArray > 2) {

                            minimumNumberOfBullHeads = counter[ThreadLocalRandom.current().nextInt(0, counter.length + 1)];

                        }

                        // locate the row with the minimum number of bull heads in the table
                        for (int m = 0 ; m < stacksCount.length ; m++) {

                            if (numberOfBullHeads[minimumNumberOfBullHeads] == stacksCount[m]) {

                                for (int p = 0; p < stacksCount[m]; p++) {

                                    // transfer the cards in this row to the players pile
                                    if (cardsDrawn[1] == smallestCard) {

                                        players[1].moveToPile(stacks[p], stacksCount[m]);

                                    } else if (cardsDrawn[2] == smallestCard) {

                                        players[2].moveToPile(stacks[p], stacksCount[m]);

                                    } else if (cardsDrawn[3] == smallestCard) {

                                        players[3].moveToPile(stacks[p], stacksCount[m]);

                                    }

                                }

                                // put the card played in its place
                                stacks[m][0] = smallestCard;

                            }

                        }

                    }


                }

                // remove the card which has already been placed from the array
                for (int k = 0; k < cardsDrawn.length; k++) {

                    if (smallestCard.getNumber() == cardsDrawn[k].getNumber()) {

                        cardsDrawn[k] = null;

                    }

                }

            }

        }

        for (Player p : players) {
            System.out.println(p.getName() + " has a score of " + p.getScore());
            p.printPile();
        }

    }

    /**
     * Programme main. You should not change this.
     * 
     * @param args - no use.
     */
    public static void main(String[] args) {
        new Table().runApp();
    }

}
