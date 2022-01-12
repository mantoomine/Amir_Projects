package BlackJack.controller;

import BlackJack.model.Game;
import BlackJack.model.IObserver;
import BlackJack.view.IView;
import BlackJack.view.IView.gameOptions;

public class PlayGame implements IObserver {
    private final Game m_game;
    private final IView m_view;


    public PlayGame(Game a_game, IView a_view) {
        m_game = a_game;
        m_view = a_view;
        a_game.register(this);
    }

    public boolean Play(){
        m_view.DisplayWelcomeMessage();
        m_view.DisplayDealerHand(m_game.GetDealerHand(), m_game.GetDealerScore());
        m_view.DisplayPlayerHand(m_game.GetPlayerHand(), m_game.GetPlayerScore());
        if (m_game.IsGameOver()) {
            m_view.DisplayGameOver(m_game.IsDealerWinner());
        }
        gameOptions gameOptions = m_view.getInput();
        if (gameOptions == gameOptions.QUIT) {
            return false;
        } else if (gameOptions == gameOptions.PLAY) {
            m_game.NewGame();
        } else if (gameOptions == gameOptions.HIT) {
            m_game.Hit();
        } else if (gameOptions == gameOptions.STAND) {
            m_game.Stand();
        }
        return true;
    }

    @Override
    public void update() {
        try {
            m_view.DisplayDealerHand(m_game.GetDealerHand(), m_game.GetDealerScore());
            m_view.DisplayPlayerHand(m_game.GetPlayerHand(), m_game.GetPlayerScore());
            Thread.sleep(2500L);
        } catch (Exception e) {
            m_view.PrintLine(e.getMessage());
        }
    }
}