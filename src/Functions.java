
import java.util.Random;


final class Functions {
    static final Random rand = new Random();


    static int clamp(int value, int high) {
        return Math.min(high, Math.max(value, 0));
    }

}