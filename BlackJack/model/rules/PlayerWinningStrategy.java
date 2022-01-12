package BlackJack.model.rules;

import BlackJack.model.Dealer;
import BlackJack.model.Player;

public class PlayerWinningStrategy implements IWinStrategy {


    @Override
    public boolean checkIfTheDealerWins(Dealer a_dealer, Player a_player, int g_maxScore) {

        g_maxScore = 21;
        if (a_player.CalcScore() > g_maxScore) {
            return true;
        } else if (a_dealer.CalcScore() > g_maxScore) {
            return false;
        } else if (a_dealer.CalcScore() == a_player.CalcScore()) {
            return false;
        }
        return a_dealer.CalcScore() > a_player.CalcScore();
    }
}
