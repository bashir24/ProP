package knapsack;

import ga.framework.model.NoSolutionException;
import ga.framework.model.Problem;
import ga.framework.model.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//==========2.1 Problem und Lösung ============
public class KnapsackProblem implements Problem {
    private int capacity;
    private List<Item> items;

    public KnapsackProblem(int capacity, List<Item> items) {
        this.capacity = capacity;
        this.items = items;
    }
    public int getCapacity() { return capacity; }
    public List<Item> getItems() { return items; }



//nur einmal zu Beginn aufgerufen, um die Startpopulation zu erstellen.

    @Override
    public Solution createNewSolution() throws NoSolutionException {
        KnapsackSolution sol = new KnapsackSolution(this);
        Random rand = new Random();

        List<Item> remaining = new ArrayList<>(items);
        while (!remaining.isEmpty()) {
            Item candidate = remaining.get(rand.nextInt(remaining.size()));
            if (sol.getWeight() + candidate.getWeight() <= capacity) {
                sol.addItem(candidate);
            }
            remaining.remove(candidate);
        }

        if (sol.getItems().isEmpty()) {
            throw new NoSolutionException("Kein Item passt in den Rucksack!");
        }

        return sol;
    }


}
