package sandbox.rcc_2022;

import java.util.ArrayList;

/**
 * Created on 3/10/2022 with IntelliJ IDEA.
 *
 * @author Andrea Della Chiesa (a.dellachiesa@reply.it)
 * @version 1.0
 */
public class ArrayUtils {

    public ArrayList<ArrayList<Integer>> permute(int[] num) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        permute(num, 0, result);
        return result;
    }


    void permute(int[] num, int start, ArrayList<ArrayList<Integer>> result) {


        if (start >= num.length) {
            ArrayList<Integer> item = convertArrayToList(num);
            result.add(item);
        }


        for (int j = start; j <= num.length - 1; j++) {
            swap(num, start, j);
            permute(num, start + 1, result);
            swap(num, start, j);
        }
    }


    private ArrayList<Integer> convertArrayToList(int[] num) {
        ArrayList<Integer> item = new ArrayList<Integer>();
        for (int h = 0; h < num.length; h++) {
            item.add(num[h]);
        }
        return item;
    }


    private void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public int[] init(int n) {
        int[] vet = new int[n];
        for (int i = 0; i < n; i++) {
            vet[i] = i;
        }
        return vet;
    }
}
