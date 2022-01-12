package BlackJack.model.rules;

import BlackJack.model.Dealer;
import BlackJack.model.Player;

public interface IWinStrategy {

    boolean checkIfTheDealerWins(Dealer a_dealer, Player a_player, int maxScore);
}

