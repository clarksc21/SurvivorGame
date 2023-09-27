import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeightedSpinner<E> {
    private double totalWeight;
    private List<E> items;
    private List<Double> weights;

    public WeightedSpinner() {
        totalWeight = 0;
        items = new ArrayList<>();
        weights = new ArrayList<>();
    }

    public void add(E item, double weight) {
        if (weight < 0) {
            throw new IllegalArgumentException();
        }
        items.add(item);
        weights.add(weight);
        totalWeight += weight;
    }

    public E spin() {
        if (items.size() == 0) {
            throw new NullPointerException();
        }
        Random random = new Random();
        double r = random.nextDouble() * totalWeight;
        for (int i = 0; i < items.size(); i++) {
            r -= weights.get(i);
            if (r < 0) {
                return items.get(i);
            }
        }
        return items.get(items.size()-1);
    }

    public double getTotalWeight() {
        return totalWeight;
    }
}
