//package game;

public class Refresh implements Runnable{
    
    private Main mainActivity;

    public Refresh(Main mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void run() {
        while(true) {
            try {
                Thread.sleep(30);
                mainActivity.repaint();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }

}