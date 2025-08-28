package knapsack;

import ga.framework.model.Problem;
import ga.framework.model.Solution;

import java.util.ArrayList;
import java.util.List;

//==========2.1 Problem und Lösung ============

public class KnapsackSolution extends Solution {
    private List<Item> chosenItems = new ArrayList<>();
    private int weight = 0;
    private int value = 0;



    public int getWeight() { return weight; }
    public int getValue() { return value; }
    public List<Item> getItems() { return chosenItems; }


    public KnapsackSolution(KnapsackProblem problem) {
        super(problem);
    }

    // Copy-Konstruktor
    public KnapsackSolution(KnapsackSolution other) {
        super(other);
        this.chosenItems = new ArrayList<>(other.chosenItems);
        this.weight = other.weight;
        this.value = other.value;
    }

    public void addItem(Item item) {
        chosenItems.add(item);
        weight += item.getWeight();
        value += item.getValue();
    }

    public void removeItem(Item item) {
        chosenItems.remove(item);
        weight -= item.getWeight();
        value -= item.getValue();
    }


}
