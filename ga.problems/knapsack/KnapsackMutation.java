package knapsack;

import ga.framework.model.Solution;
import ga.framework.operators.EvolutionException;
import ga.framework.operators.EvolutionaryOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KnapsackMutation implements EvolutionaryOperator {
    private Random rand = new Random();

    @Override
    public Solution evolve(Solution parent) throws EvolutionException {
        KnapsackSolution copy = new KnapsackSolution((KnapsackSolution) parent);
        KnapsackProblem problem = (KnapsackProblem) copy.getProblem();

        boolean canRemove = !copy.getItems().isEmpty();
        boolean canAdd = problem.getItems().stream()
                .anyMatch(i -> !copy.getItems().contains(i) &&
                        copy.getWeight() + i.getWeight() <= problem.getCapacity());

        List<Runnable> mutations = new ArrayList<>();

        if (canRemove) {
            mutations.add(() -> {
                Item toRemove = copy.getItems()
                        .get(rand.nextInt(copy.getItems().size()));
                copy.removeItem(toRemove);
            });
        }

        if (canAdd) {
            mutations.add(() -> {
                List<Item> available = problem.getItems().stream()
                        .filter(i -> !copy.getItems().contains(i))
                        .filter(i -> copy.getWeight() + i.getWeight() <= problem.getCapacity())
                        .toList();
                Item toAdd = available.get(rand.nextInt(available.size()));
                copy.addItem(toAdd);
            });
        }

        if (mutations.isEmpty()) {
            throw new EvolutionException("Keine Mutation möglich!");
        }

        // Eine gültige Mutation zufällig ausführen
        mutations.get(rand.nextInt(mutations.size())).run();

        return copy;
    }
}
