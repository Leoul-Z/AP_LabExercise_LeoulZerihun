import java.util.ArrayList;

public class standardDeck {
        private ArrayList<StandardCard> deck = new ArrayList<StandardCard>();
     

    public standardDeck(){
            reset();
    }

    public void reset(){
        this.deck.clear();

        for(int i=2; i<15; i++){
            deck.add(new StandardCard( i, "Hearts"));
        }
        for(int j=2; j<15; j++){
            deck.add(new StandardCard( j, "Diamonds"));
        }
        for(int k=2; k<15; k++){
            deck.add(new StandardCard( k, "Spades"));
        }
        for(int l=2; l<15; l++){
            deck.add(new StandardCard( l, "Clubs"));
        }
        
    }
}
