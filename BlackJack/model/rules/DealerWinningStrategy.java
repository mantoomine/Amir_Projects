package BlackJack.model.rules;

import BlackJack.model.Dealer;
import BlackJack.model.Player;

public class DealerWinningStrategy implements IWinStrategy {
    @Override
    public boolean checkIfTheDealerWins(Dealer a_dealer, Player a_player, int maxScore) {
        maxScore = 21;

        if (a_player.CalcScore() > maxScore) {
            return true;
        } else if (a_dealer.CalcScore() > maxScore) {
            return false;
        } else if (a_dealer.CalcScore() == a_player.CalcScore()) {
            return true;
        }

        return a_dealer.CalcScore() >= a_player.CalcScore();
    }
}
