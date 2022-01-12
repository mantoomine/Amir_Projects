package BlackJack.view;

public interface IView {
    void DisplayWelcomeMessage();

    gameOptions getInput();

    void DisplayCard(BlackJack.model.Card a_card);

    void DisplayPlayerHand(Iterable<BlackJack.model.Card> a_hand, int a_score);

    void DisplayDealerHand(Iterable<BlackJack.model.Card> a_hand, int a_score);

    void DisplayGameOver(boolean a_dealerIsWinner);

    void PrintLine(String print);

    enum gameOptions {
        PLAY,
        HIT,
        STAND,
        QUIT,
        ERROR
    }
}