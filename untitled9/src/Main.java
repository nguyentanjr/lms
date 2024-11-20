
import java.util.*;
public class Main {
    public static List<Double> runningMedian(List<Integer> a) {
        PriorityQueue<Integer> pri1 = new PriorityQueue<>(a.size()/2,Comparator.reverseOrder());
        PriorityQueue<Integer> pri2 = new PriorityQueue<>(a.size()/2);
        List<Double> list = new ArrayList<>();
        for(int i = 0 ; i < a.size(); i++) {
            if(i % 2 == 0)pri1.add(a.get(i));
            else pri2.add(a.get(i));
            if(i % 2 != 0) {
                list.add((double)(pri1.peek() + pri2.peek())/2);
            }
            else list.add((double)pri1.peek());
        }
        return list;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        List<Integer> arr = new ArrayList<Integer>();
        LinkedHashMap <Integer, Integer> map = new LinkedHashMap <>();
        for(int i = 1; i <= 100; i++)map.put(i,0);
        for (int i = 0; i < n; i++) {
            int event = scanner.nextInt();
            arr.add(event);
        }
        PriorityQueue<Integer> pri2 = new PriorityQueue<>(arr.size()/2,Comparator.reverseOrder());
        PriorityQueue<Integer> pri1 = new PriorityQueue<>(arr.size()/2);
        List<Double> list = new ArrayList<>();
        for(int i = 0 ; i < arr.size(); i++) {
            if(i % 2 == 0)pri2.add(arr.get(i));
            else pri1.add(arr.get(i));
            if(i % 2 != 0) {
                System.out.println(pri1.peek() + " " + pri2.peek());
                //list.add((double)(pri1.peek() + pri2.peek())/2);
            }
            else {
                System.out.println(pri2.peek());
            }//list.add((double)pri1.peek());
        }
        //for(double i : list) System.out.println(i);
    }
}