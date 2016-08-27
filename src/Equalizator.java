import java.util.Random;

import static java.util.Arrays.sort;

/**
 * Created by Komdosh on 17.08.2016.
 *
 */
public class Equalizator {
    private int average;
    private int currentIndex;
    private int[] arrayOfNumbers;
    //private int[] arrayOfNumbers = {800,800,800,350,450};
    private int[][] result;
    private int[][] second;
    private int countOfBoxes;

    public Equalizator(int[] arrayOfNumbers, int countOfBoxes) {
        this.arrayOfNumbers = arrayOfNumbers;
        this.countOfBoxes = countOfBoxes;
        this.currentIndex = 0;
        this.average = getSum(arrayOfNumbers)/countOfBoxes;
        this.result = new int[countOfBoxes][arrayOfNumbers.length];
        this.second = new int[countOfBoxes][arrayOfNumbers.length];
    }

    public Equalizator() {
        Random random = new Random();
        int countOfElements = Math.abs(random.nextInt()%100)+12;
        arrayOfNumbers = new int[countOfElements];
        for (int i = 0; i < countOfElements; i++) {
            arrayOfNumbers[i]= Math.abs(random.nextInt()%100)+10;
        }
        countOfBoxes = Math.abs(random.nextInt()%8)+2;
        this.currentIndex = 0;
        this.average = getSum(arrayOfNumbers)/countOfBoxes;
        this.result = new int[countOfBoxes][arrayOfNumbers.length];
        this.second = new int[countOfBoxes][arrayOfNumbers.length];
    }

    public Equalizator(int countOfBoxes) {
        Random random = new Random();
        int countOfElements = Math.abs(random.nextInt()%50)+10;
        arrayOfNumbers = new int[countOfElements];
        for (int i = 0; i < countOfElements; i++) {
            arrayOfNumbers[i]= Math.abs(random.nextInt()%100)+10;
        }
        this.countOfBoxes = countOfBoxes;
        this.currentIndex = 0;
        this.average = getSum(arrayOfNumbers)/countOfBoxes;
        this.result = new int[countOfBoxes][arrayOfNumbers.length];
        this.second = new int[countOfBoxes][arrayOfNumbers.length];
    }

    public void reverse(int[] array) {
        if (array == null) {
            return;
        }
        int temp;
        for(int i = 0, j = array.length-1; j > array.length/2-1; ++i, --j){
            temp = array[j];
            array[j] = array[i];
            array[i] = temp;
        }
    }

    public void start(){
        sort(arrayOfNumbers);
        reverse(arrayOfNumbers);
        //printArrayInfo();
        lightDivideAlgorithm();
        if(getMaxDeviation(second)-getMinDeviation(second)!=0){
            init();
            if(arrayOfNumbers.length > countOfBoxes){
                int oldIndex = currentIndex - 1;
                while(currentIndex < arrayOfNumbers.length && oldIndex!=currentIndex){
                    oldIndex=currentIndex;
                    backward(getMaxIndex(result));
                    forward(getMaxIndex(result));
                }
                if(currentIndex < arrayOfNumbers.length)
                    align(result, currentIndex);
            }
            if(Math.abs(getMaxDeviation(result)-getMinDeviation(result))<Math.abs(getMaxDeviation(second)-getMinDeviation(second))){
                printTwoDim(result);
            }
            else{
                printTwoDim(second);
            }
        }
        else{
            printTwoDim(second);
        }
    }

    private void lightDivideAlgorithm(){
        int index = 0;
        for (int i = 0; i < countOfBoxes; i++) {
            for (int j = 0; index < arrayOfNumbers.length && (getSum(second[i])+arrayOfNumbers[index]) <= average; j++) {
                second[i][j] = arrayOfNumbers[index];
                ++index;
            }
        }
        align(second, index);
    }

    private void printArrayInfo(){
        System.out.print("Elements: ");
        for (int oneOfNumber : arrayOfNumbers)
            System.out.print(oneOfNumber+" ");
        System.out.println("\nCount of boxes: "+countOfBoxes);
        System.out.println("Count of elements: "+count(arrayOfNumbers));
        System.out.println("Sum of elements: "+getSum(arrayOfNumbers));
        System.out.println("Average of array for boxes: "+average);
    }

    private void printTwoDim(int[][] a){
        for (int r[] : a){
            for (int one : r)
                if(one!=0)
                    System.out.print(one+" ");
            System.out.print("sum is "+getSum(r));
            System.out.println();
        }
    }

    private int getMaxDeviation(int[][] array){
        int max = getSum(array[0]);
        for(int[] oneArray : array)
            if(max < getSum(oneArray))
                max = getSum(oneArray);
        return max;
    }

    private int getMinDeviation(int[][] array){
        int min = getSum(array[0]);
        for(int[] oneArray : array)
            if(min > getSum(oneArray))
                min = getSum(oneArray);
        return min;
    }

    private void align(int[][] array, int currentIndex){
        int index;
        while(currentIndex < arrayOfNumbers.length){
            index = getMinIndex(array);
            array[index][count(array[index])] = arrayOfNumbers[currentIndex];
            ++currentIndex;
        }
    }

    private int getSum(int[] a){
        int sum = 0;
        for (int one : a)
            sum+=one;
        return sum;
    }

    private void init(){
        for (int i = 0; i < countOfBoxes && currentIndex < arrayOfNumbers.length; i++) {
            result[i][0]=arrayOfNumbers[currentIndex];
            ++currentIndex;
        }
    }
    private void forward(int index){
        for (int i = 0; i < countOfBoxes; ++i) {
            if(i!=index && currentIndex < arrayOfNumbers.length && (getSum(result[i])+arrayOfNumbers[currentIndex]) <= average) {
                result[i][count(result[i])] = arrayOfNumbers[currentIndex];
                ++currentIndex;
            }
        }
    }
    private void backward(int index){
        for (int i = countOfBoxes-1; i > 0; --i) {
            if(i!=index && currentIndex < arrayOfNumbers.length && (getSum(result[i])+arrayOfNumbers[currentIndex]) <= average) {
                result[i][count(result[i])] = arrayOfNumbers[currentIndex];
                ++currentIndex;
            }
        }
    }

    private int getMaxIndex(int[][] array){
        int max=0;
        int index=0;
        for (int i = 0; i < countOfBoxes; i++) {
            if(getSum(array[i])>max){
                max=getSum(array[i]);
                index=i;
            }
        }
        return index;
    }

    private int getMinIndex(int[][] array){
        int index=0;
        int min=getSum(result[0]);
        for (int i = 1; i < countOfBoxes; i++) {
            if(getSum(array[i])<min){
                min=getSum(array[i]);
                index=i;
            }
        }
        return index;
    }

    private int count(int a[]){
        int s = 0;
        for (int one:a)
            if(one!=0)
                ++s;
        return s;
    }

    private int countAllNumber(int a[][]){
        int s = 0;
        for (int r[]:a)
            s+=count(r);
        return s;
    }
}
