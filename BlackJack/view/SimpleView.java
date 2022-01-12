package BlackJack.view;

import java.io.IOException;

public class SimpleView implements IView {


    public void DisplayWelcomeMessage() {
        for (int i = 0; i < 50; i++) {
            this.PrintLine("\n");
        }
        this.PrintLine("Hello Black Jack World");
        System.out.println("Type 'p' to Play, 'h' to Hit, 's' to Stand or 'q' to Quit\n");
    }

    @Override
    public gameOptions getInput() {
        try {
            int input = System.in.read();
            while (input == '\r' || input == '\n') {
                input = System.in.read();
            }
            if (input == 'p' || input == 'P') {
                return gameOptions.PLAY;
            } else if (input == 'q' || input == 'Q') {
                return gameOptions.QUIT;
            } else if (input == 'h' || input == 'H') {
                return gameOptions.HIT;
            } else if (input == 's' || input == 'S') {
                return gameOptions.STAND;
            } else {
                System.err.println("Wrong input try again!\n");
                return gameOptions.ERROR;
            }
        } catch (IOException e) {
            System.out.println("IO Error occurred" + e);
            return gameOptions.ERROR;
        } catch (Exception e) {
            System.out.println("Exception occurred" + e);
            return gameOptions.ERROR;
        }
    }

    public void DisplayCard(BlackJack.model.Card a_card) {

        this.PrintLine("" + a_card.GetValue() + " of " + a_card.GetColor());
    }

    public void DisplayPlayerHand(Iterable<BlackJack.model.Card> a_hand, int a_score) {
        DisplayHand("Player", a_hand, a_score);
    }

    public void DisplayDealerHand(Iterable<BlackJack.model.Card> a_hand, int a_score) {
        DisplayHand("Dealer", a_hand, a_score);
    }

    private void DisplayHand(String a_name, Iterable<BlackJack.model.Card> a_hand, int a_score) {
        this.PrintLine(a_name + " Has: ");
        for (BlackJack.model.Card c : a_hand) {
            DisplayCard(c);
        }
        this.PrintLine("Score: " + a_score);
        this.PrintLine("");
    }

    public void DisplayGameOver(boolean a_dealerIsWinner) {
        this.PrintLine("GameOver: ");
        if (a_dealerIsWinner) {
            this.PrintLine("Dealer Won!");
        } else {
            this.PrintLine("You Won!");
        }
    }

    @Override
    public void PrintLine(String print) {
        System.out.println(print);
    }

}
