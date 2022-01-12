package BlackJack.model;

import BlackJack.model.rules.IHitStrategy;
import BlackJack.model.rules.INewGameStrategy;
import BlackJack.model.rules.IWinStrategy;
import BlackJack.model.rules.RulesFactory;

public class Dealer extends Player {

    private Deck m_deck;
    private INewGameStrategy m_newGameRule;
    private IHitStrategy m_hitRule;
    private IWinStrategy m_winningRule;

    public Dealer(RulesFactory a_rulesFactory) {

        m_newGameRule = a_rulesFactory.GetNewGameRule();
        m_hitRule = a_rulesFactory.GetHitRule();
        m_winningRule = a_rulesFactory.GetWinningRule();

    }

    public boolean NewGame(Player a_player) {
        if (m_deck == null || IsGameOver()) {
            m_deck = new Deck();
            ClearHand();
            a_player.ClearHand();
            return m_newGameRule.NewGame(this, a_player);
        }
        return false;
    }

    public boolean Hit(Player a_player) {
        if (m_deck != null && a_player.CalcScore() < g_maxScore && !IsGameOver()) {
            DealCard(a_player, true);
            return true;
        }
        return false;
    }

    public boolean Stand() {
        if (m_deck != null) {
            this.ShowHand();
            while (m_hitRule.DoHit(this)) {
                DealCard(m_deck.GetCard(), true);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 1. If the dealer points is more than or equal player's points. In this case the dealer is going to win.
     * <p>
     * 2. If the player's point is more than 21. Therefore, the dealer is going to win.
     * <p>
     * 3. If the dealer's point is equal to the player's point. Therefore, the player is going to win.
     */

    public boolean IsDealerWinner(Player a_player) {

        return m_winningRule.checkIfTheDealerWins(this, a_player, g_maxScore);
    }

    public boolean IsGameOver() {
        return m_deck != null && !m_hitRule.DoHit(this);
    }

    public void DealCard(Player a_player, boolean isVisible) {
        a_player.DealCard(m_deck.GetCard(), isVisible);
    }
}