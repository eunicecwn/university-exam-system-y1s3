package ExtraFunction;

public class ClearScreen {
    public void clearConsole() { // No "static" keyword
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }
}
