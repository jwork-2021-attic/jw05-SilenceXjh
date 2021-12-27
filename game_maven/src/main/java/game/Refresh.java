package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class Refresh implements Runnable {

    private Main mainActivity;

    public Refresh(Main mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(30);
                mainActivity.repaint();
                File file = new File("log.txt");
                Boolean b = file.exists();
                FileOutputStream fileOutputStream = new FileOutputStream("log.txt", true);
                ObjectOutputStream objectOutputStream;
                if(b) {
                    objectOutputStream = new MyObjectOutputStream(fileOutputStream);
                }
                else {
                    objectOutputStream = new ObjectOutputStream(fileOutputStream);
                }
                objectOutputStream.writeObject(mainActivity.getScreen());
                objectOutputStream.flush();
                objectOutputStream.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

}

class MyObjectOutputStream extends ObjectOutputStream {
    
    public MyObjectOutputStream() throws SecurityException, IOException {
        super();
    }
    public MyObjectOutputStream(OutputStream o) throws IOException {
        super(o);
    }

    public void writeStreamHeader() throws IOException {
        super.reset();
    }
}
