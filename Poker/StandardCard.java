public class StandardCard {
    

   private String suit;
   private int value;
   private String color;
   

    

    public StandardCard(String suit, int value, String color){
        this.value=value;

        if (this.suit=="Spade" || this.suit=="Club"){
            this.color="Black";
        }
        if (this.suit=="Heart" || this.suit=="Diamond"){
            this.color="Red";
        }

    }

    public int getValue(){
        return this.value;
    }

    public String getSuit(){
        return this.suit;
    }

    public String getColor(){
        return this.color;
    }

    public String convertValueToName(){
        switch (this.value) {
            case 1: return "Ace";
            case 2: return "Two";
            case 3: return "Three";
            case 4: return "Four";
            case 5: return "FIve";
            case 6: return "Six";
            case 7: return "Seven";
            case 8: return "Eight";
            case 9: return "Nine";
            case 10: return "Ten";
            case 11: return "Jack";
            case 12: return "Queen";
            case 13: return "King";
            case 14: return "Ace";
            default:
                return "Value not Valid";
        }
    }

    public String toString(){
        return (convertValueToName() + "of "+ this.suit);
    }





}
