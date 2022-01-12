package BlackJack;

import BlackJack.controller.PlayGame;
import BlackJack.model.Game;
import BlackJack.view.IView;
import BlackJack.view.SimpleView;


public class Program {

    public static void main(String[] a_args){
        Game game = new Game();
        IView view = new SimpleView(); /*new SwedishView();*/
        PlayGame ctrl = new PlayGame(game, view);
        while (ctrl.Play()) ;
    }
}