package sample;

import java.util.InputMismatchException;
import java.util.zip.DataFormatException;

public class Test {
    public static void main(String[] args) {
        int data = 100;
        int id = 65;
        try{
            testData(data, id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    public static void testData(int data, int id) throws DataFormatException {
        if(data%id != 0){
            if(data%id < 5){
                throw new InputMismatchException("Not within range");
            }else{
                throw new ArithmeticException("Divisible by your id");
            }
        }else if(data%2 != 0){
            throw new DataFormatException("Should be even number");
        }
        System.out.println(data + "is a valid number");
    }
}
