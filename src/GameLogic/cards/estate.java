package GameLogic.cards;

class estate {

    public int cost = 2;
    public int realValue = 1;
    public Enum CardType = cardType.VictoryCard;
    public Enum CardName = GameLogic.cards.CardName.estate;

    public estate(int cost, int realValue, Enum cardType, Enum cardName) {
        this.cost = cost;
        this.realValue = realValue;
        this.CardType = cardType;
        this.CardName = cardName;
    }


}