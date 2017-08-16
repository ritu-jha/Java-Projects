
package game;
public class Game
{
        public static void main(String[] args)
        {
        
            Player player1 = new Player("Yo Mamma");
            Player player2 = new Player("Gandalf");
        
            int[] p1Roll = player1.getRoll();
            int[] p2Roll = player2.getRoll();
        
            for(int i = 0; i < p1Roll.length; i++)
            {
                System.out.println(p1Roll[i] + " vs " + p2Roll[i]);
                if(p2Roll[i] >= p1Roll[i])
                {
                    System.out.println(player1.getName() + " loses an Army");
                    player1.loseArmy();
                }
                else
                {
                    System.out.println(player2.getName() + " loses and army");
                    player2.loseArmy();
                }
            }
            System.out.println(player1.getArmies() + " vs " + player2.getArmies());
        }
}
class Player
{
        
     Dice dice;
    private String name;
    private int wins, losses;
    private int numberOfArmies;
    private int[] playerRoll;
    
    public Player(String n){
        
        this.name = n;
        this.wins = 0;
        this.losses = 0;
        this.numberOfArmies = 100;
        this.dice = new Dice(numberOfArmies);
        this.playerRoll = dice.getRoll();
        
    }
    
    public String getName(){
        return name;
    }
    
    public int getArmies(){
        return numberOfArmies;
    }
    
    public void loseArmy(){
        
        this.numberOfArmies--;
        
    }
    
    public void addWin(){
        this.wins++;
    }
    
    public void addLoss(){
        this.losses++;
    }
    
    public int[] getRoll(){
        return playerRoll;
    }
    
}
class Dice
{
        
    private int[] numbers;
    private int numberOfDice;
    
    public Dice(int numberOfArmies){
        
        if(numberOfArmies < 2){
            this.numbers = new int[1];
        }else if(numberOfArmies < 3){
            this.numbers = new int[2];
        }else{
            this.numbers = new int[3];
        }
        
        getNumbers();
        
    }
    
    private void getNumbers(){
        
        for(int i = 0; i < numbers.length; i++){
            numbers[i] = (int)(Math.random() * 6) + 1;
        }
        
    }
    
    public int[] getRoll(){
        
        int[] roll = new int[numbers.length];
        int rPlace = 0;
        //Arrays.sort(numbers);
        
        for(int i = numbers.length - 1; i >= 0; i--){
            roll[rPlace] = numbers[i];
            rPlace++;
        }
        
        return roll;
    }
}
    