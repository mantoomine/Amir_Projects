package BlackJack.model.rules;

public class RulesFactory {

    public IHitStrategy GetHitRule() {
        return new SoftSeventeenRule();
    }

    public INewGameStrategy GetNewGameRule() {
        return new AmericanNewGameStrategy();
    }

    public IWinStrategy GetWinningRule() {
        return new DealerWinningStrategy(); // Give the power to Dealer
        // return new PlayerWinningStrategy(); Give the power to player
    }
}