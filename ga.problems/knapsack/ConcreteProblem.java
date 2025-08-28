package knapsack;

import ga.framework.model.GeneticAlgorithm;
import ga.framework.model.Solution;
import ga.framework.operators.TopKSurvival;
import ga.framework.operators.TournamentSelection;

import java.util.List;

public class ConcreteProblem {
    public static void main(String[] args) throws Exception {
        List<Item> items = List.of(
                new Item("g1",5,10), new Item("g2",4,8), new Item("g3",4,6),
                new Item("g4",4,4), new Item("g5",3,7), new Item("g6",3,4),
                new Item("g7",2,6), new Item("g8",2,3), new Item("g9",1,3),
                new Item("g10",1,1)
        );

        KnapsackProblem problem = new KnapsackProblem(11, items);

        List<Solution> result = GeneticAlgorithm
                .solve(problem)
                .withPopulationOfSize(4) //4 Lösungen pro Generation.
                .evolvingSolutionsWith(new KnapsackMutation())
                .evaluatingSolutionsBy(new KnapsackFitnessEvaluator())
                .performingSelectionWith(new TournamentSelection())
                .survivingWith(new TopKSurvival(2))
                .stoppingAtEvolution(10)
                .runOptimization();


        // Ergebnisse ausgeben
        for (Solution s : result) {
            KnapsackSolution ks = (KnapsackSolution) s;
            System.out.println("Fitness: " + ks.getFitness() +
                    " | Items: " + ks.getItems().stream()
                    .map(Item::getName).toList());
        }
    }
}
