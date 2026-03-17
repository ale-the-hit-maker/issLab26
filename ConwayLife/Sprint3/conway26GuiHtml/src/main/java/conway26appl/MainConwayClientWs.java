package conway26appl;

import java.util.concurrent.CountDownLatch;

import conway26appl.caller.CallerServerWs;
import main.java.conway.MainConwayLifeJava;
import main.java.conway.domain.GameController;
import main.java.conway.domain.Life;
import main.java.conway.domain.LifeController;
import main.java.conway.domain.LifeInterface;

public class MainConwayClientWs {
	
	private CallerServerWs outputDevice;
	private GameController gctrl;
	
	public MainConwayClientWs() {
		
		// creiamo il modello del gioco
		LifeInterface gameModel = new Life(20, 20);
		// creiamo il proxy con cui il life controller comunica con il web server
		this.outputDevice = new CallerServerWs();
		// creiamo il controller della logica di business
		this.gctrl = new LifeController(gameModel, outputDevice);
		// setto il controller nel client websocket
		this.outputDevice.setController(this.gctrl);
		
	}
	
	
	public void startSystem() throws InterruptedException {
        System.out.println("MainConwayClientWs | Sistema pronto. In attesa del comando START dal Web...");
        
        // Il thread main si "addormenta" in modo pulito e senza consumare CPU 
        // finché il client ws non riceve dal serverIo un messaggio di EXIT --> keepAliveLatch.countDown().
        this.outputDevice.getLatch().await(); 
	}

	public static void main(String[] args) {
		
	    System.out.println("MainConway | STARTS " );  
	    try {
	        MainConwayClientWs app = new MainConwayClientWs();
	        
	        // attesa del main
	        app.startSystem();
            
	    } catch(Exception e) {
	        e.printStackTrace();
	    }

	    System.out.println("MainConway | ENDS " );  

	}

}
