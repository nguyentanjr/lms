public class Week4 {
    public static int max2Int(int a, int b) {
        // Tim gia tri lon nhat cua hai so nguyen
        return Math.max(a, b);
    }

    public static int minArray(int[] array) {
        // Tim gia tri nho nhat cua 1 mang so nguyen
        int Min = Integer.MAX_VALUE;
        for (int i = 0; i < array.length; i++) {
            if (array[i] < Min) {
                Min = array[i];
            }
        }
        return Min;
    }

    public static String calculateBMI(double weight, double height) {
        // Tinh BMI
        double bmi = Math.round(weight / (height * height) * 10.0) / 10.0;
        if(bmi < 18.5) {
            return "Thiếu cân";
        }
        if(bmi <= 22.9) {
            return "Bình thường";
        }
        if(bmi <= 24.9) {
            return "Thừa cân";
        }
        return "Béo phì";
    }
}
 