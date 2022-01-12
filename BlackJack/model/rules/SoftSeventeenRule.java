package BlackJack.model.rules;

import BlackJack.model.Card;
import BlackJack.model.Player;

public class SoftSeventeenRule implements IHitStrategy {
    private final int g_hitLimit = 17;
    private int NumberOfAce = 0;
    private int handFace = 0;

    /**
     * http://www.readybetgo.com/blackjack/strategy/soft-17-rule-2496.html#:~:text=Any%20blackjack%20hand%20that%20contains,than%20their%20corresponding%20hard%20hands.
     */

    @Override
    public boolean DoHit(Player a_dealer) {
        Iterable<Card> dealerHand = a_dealer.GetHand();

        for (Card card : dealerHand) {
            if (card.GetValue() == Card.Value.Ace) {
                NumberOfAce++;
            } else if (card.GetValue() == Card.Value.Ten ||
                    card.GetValue() == Card.Value.Knight ||
                    card.GetValue() == Card.Value.Queen ||
                    card.GetValue() == Card.Value.King) {
                handFace++;
            }
        }

        if (a_dealer.CalcScore() == g_hitLimit && NumberOfAce > 0 && handFace == 0) {
            return true;
        } else {
            return a_dealer.CalcScore() < g_hitLimit;
        }
    }
}