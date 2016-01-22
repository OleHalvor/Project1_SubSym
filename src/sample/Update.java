package sample;

/**
 * Created by Ole on 21.01.2016.
 */
public class Update extends Thread {

    public void run(){
        System.out.println("MyThread running");
        move();
    }

    public void move(){
        for (int i=0; i<100; i++){
            Values.setX(Values.getX()+50);
            Values.setY(Values.getY()+50);
            System.out.println("moving");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
