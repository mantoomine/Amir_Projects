package BlackJack.view;

import java.io.IOException;

public class SwedishView implements IView {
    public void DisplayWelcomeMessage() {

        for (int i = 0; i < 50; i++) {
            System.out.print("\n");
        }
        this.PrintLine("Hej Black Jack Världen");
        System.out.println("Skriv 'p' för att Spela, 'h' för nytt kort, 's' för att stanna 'q' för att avsluta\n");
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
                System.err.println("Fel inmatning försök igen!\n");
                return gameOptions.ERROR;
            }
        } catch (IOException e) {
            System.out.println("IO-fel inträffade" + e);
            return gameOptions.ERROR;
        } catch (Exception e) {
            System.out.println("IO-fel inträffade" + e);
            return gameOptions.ERROR;
        }
    }


    public void DisplayCard(BlackJack.model.Card a_card) {
        if (a_card.GetColor() == BlackJack.model.Card.Color.Hidden) {
            System.out.println("Dolt Kort");
        } else {
            String colors[] =
                    {"Hjärter", "Spader", "Ruter", "Klöver"};
            String values[] =
                    {"två", "tre", "fyra", "fem", "sex", "sju", "åtta", "nio", "tio", "knekt", "dam", "kung", "ess"};
            System.out.println("" + colors[a_card.GetColor().ordinal()] + " " + values[a_card.GetValue().ordinal()]);
        }
    }

    public void DisplayPlayerHand(Iterable<BlackJack.model.Card> a_hand, int a_score) {
        DisplayHand("Spelare", a_hand, a_score);
    }

    private void DisplayHand(String a_name, Iterable<BlackJack.model.Card> a_hand, int a_score) {
        System.out.println(a_name + " Har: " + a_score);
        for (BlackJack.model.Card c : a_hand) {
            DisplayCard(c);
        }
        this.PrintLine("Poäng: " + a_score);
        this.PrintLine("");
    }

    public void DisplayDealerHand(Iterable<BlackJack.model.Card> a_hand, int a_score) {
        DisplayHand("Croupier", a_hand, a_score);
    }

    public void DisplayGameOver(boolean a_dealerIsWinner) {
        this.PrintLine("Slut: ");
        if (a_dealerIsWinner) {
            this.PrintLine("Croupiern Vann!");
        } else {
            this.PrintLine("Du vann!");
        }
    }

    @Override
    public void PrintLine(String print) {
        System.out.println(print);
    }

}
