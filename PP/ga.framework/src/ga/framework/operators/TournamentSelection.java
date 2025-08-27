package ga.framework.operators;

import ga.framework.model.Solution;
import java.util.List;
import java.util.Random;

//================1.3 Selektionsoperator [3 Punkte]======================
public class TournamentSelection implements SelectionOperator {
    private Random rand = new Random();

    @Override
    public Solution selectParent(List<Solution> candidates) {
        Solution s1 = candidates.get(rand.nextInt(candidates.size()));
        Solution s2 = candidates.get(rand.nextInt(candidates.size()));

        if (s1.getFitness() >= s2.getFitness()) {
            return s1;
        } else {
            return s2;
        }
    }
}
