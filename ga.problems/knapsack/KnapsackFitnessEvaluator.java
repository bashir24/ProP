package knapsack;

import ga.framework.model.Solution;
import ga.framework.operators.FitnessEvaluator;

import java.util.List;

public class KnapsackFitnessEvaluator implements FitnessEvaluator {
    @Override
    public void evaluate(List<Solution> solutions) {
        solutions.forEach(sol -> {
            KnapsackSolution ks = (KnapsackSolution) sol;
            double fitness = ks.getItems().stream()
                    .mapToInt(Item::getValue)
                    .sum();
            ks.setFitness(fitness);
        });
    }
}
