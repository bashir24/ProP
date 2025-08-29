package ga.framework.operators;

import ga.framework.model.Solution;

import java.util.*;
import java.util.stream.Collectors;

// ================1.2 Überlebensoperator [2 Punkte]==================
public class TopKSurvival implements SurvivalOperator {
    private int k;
    private Random rand = new Random();

    public TopKSurvival(int k) {
        this.k = k;
    }

    @Override
    public List<Solution> selectPopulation(List<Solution> candidates, int populationSize)
            throws SurvivalException {
        if (k > populationSize) {
            throw new SurvivalException("k darf nicht größer als Population sein!");
        }

        List<Solution> sorted = candidates.stream()
                .sorted(Comparator.comparingDouble(Solution::getFitness).reversed())
                .collect(Collectors.toList());

        List<Solution> survivors = new ArrayList<>(sorted.subList(0, k));

        while (survivors.size() < populationSize) {
            survivors.add(sorted.get(rand.nextInt(sorted.size())));
        }

        return survivors;
    }
}
