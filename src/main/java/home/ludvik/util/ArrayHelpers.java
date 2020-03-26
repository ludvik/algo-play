package home.ludvik.util;

public class ArrayHelpers {

    public static void printArray(String cap, int[] array, int from, int to){
        System.out.print(cap + " [" + from + ", " + to + "]: ");
        for(int i = from; i <= to; i++){
            System.out.print(array[i]);
            if(i != to){
                System.out.print(", ");
            }
        }
        System.out.println();
    }

    public static void printArray(int[] a, int from, int to){
        printArray("", a, from ,to);
    }

    public static void printArray(int[] array){
        printArray("", array, 0, array.length - 1);
    }

    public static void printArray(String cap, int[] array){
        printArray(cap, array, 0, array.length - 1);
    }
}
