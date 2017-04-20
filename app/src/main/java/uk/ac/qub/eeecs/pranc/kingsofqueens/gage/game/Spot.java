package uk.ac.qub.eeecs.pranc.kingsofqueens.gage.game;

        import android.graphics.Bitmap;
        import android.graphics.Rect;

        import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.Abilities.OwnerEffectedAbility;
        import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.graphics.IGraphics2D;
        import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.engine.io.AssetStore;
        import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.genAlgorithm;
        import uk.ac.qub.eeecs.pranc.kingsofqueens.gage.scaleScreenReso;

/**
 * Created by Paddy_Lenovo on 11/04/2017.
 */

public class Spot {


    private Boolean cardPlaced;
    private Card spotCard;
    private Rect spotRect;

    public Spot(){
        cardPlaced = false;
    }

    public void setSpotCard(Card spotCard) {
        cardPlaced = true;
        this.spotCard = spotCard;
    }

    public Card getSpotCard(){return spotCard;}

    public void draw(int top, int bot, int right , int left, genAlgorithm.field side, IGraphics2D iGraphics2D, AssetStore
            aStore, scaleScreenReso scalar){
        spotRect = scalar.scalarect(right, top, left, bot);

        if(cardPlaced) {
            spotCard.isPicked = false;
            spotCard.drawCard(spotRect, iGraphics2D);

        }
        else{
            Bitmap bit = aStore.getBitmap("Spot");
            iGraphics2D.drawBitmap(bit,null,spotRect,null);
        }


    }
    public Boolean getCardPlaced() {
        return cardPlaced;
    }

    public void dealDamageToCurrentCard(int totalAttack){
        if(spotCard.dealDamageToCard(totalAttack) == false) {
            spotCard = null;
            cardPlaced=false;
        }

    }
    public int getCardAttack(){
        return spotCard.getATK();
    }
    public Rect getSpotRect() {
        return spotRect;
    }

    public void cardEvolving(){
        spotCard.evolve();
    }
    public int getEvolvingCost(){
        return spotCard.getEvCost();
    }

    public void useCardAbility(Player pPlayer, Player pPlayerAi) {
        if(spotCard.ability.getHasAbility()){
            genAlgorithm.useCardAbility(spotCard,pPlayer,pPlayerAi);
        }
    }
}
