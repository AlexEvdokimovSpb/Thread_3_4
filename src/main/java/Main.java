public class Main {

    private static Object mon = new Object();
    private static volatile int currentNum = 1;
    private static final int numPrint = 5;


    public static void main(String[] args) {


        new Thread(new Runnable() {
            public void run() {
                printChar('A', mon, numPrint, 1, 2);
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                printChar('B', mon, numPrint, 2, 3);
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                printChar('C', mon, numPrint, 3, 4);
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                printChar(' ', mon, numPrint, 4, 1);
            }
        }).start();


    }

    /**
     *
     * @param charToPrint Символ для печати
     * @param mon Объект для монитора
     * @param num Колличество повторов
     * @param waitNum Управляющий номер для комманды на печать
     * @param newNum Новый управляющий номер - кому будет передана печать
     */
    public static void printChar(char charToPrint, Object mon, int num, int waitNum, int newNum)  {
        try {
            for (int i = 0; i < num; i++) {
                synchronized (mon) {
                    while (currentNum != waitNum) {
                                mon.wait();
                    }
                    System.out.print(charToPrint);
                    currentNum = newNum;
                    mon.notifyAll();
                }
            }
        } catch (InterruptedException e) {
                    e.printStackTrace();
        }
    }

}
