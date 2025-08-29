package ga.framework.model;

import ga.framework.operators.*;

import java.util.*;

public class GeneticAlgorithm {



    private Problem problem;
    private int populationSize;
    private List<EvolutionaryOperator> operators = new ArrayList<>();
    private FitnessEvaluator evaluator;
    private SurvivalOperator survival;
    private SelectionOperator selection;
    private int maxIterations;

    private GeneticAlgorithm() {}

    // Einstiegspunkt Fluent API
    //Jede Methode der Fluent-API gibt ein Objekt einer inneren Klasse zurück, nicht direkt das Ergebnis der Optimierung.
    public static Solver solve(Problem problem) {
        GeneticAlgorithm ga = new GeneticAlgorithm();
        ga.problem = problem;
        return ga.new Solver();
    }

    // ----- Fluent API Builder -----
    public class Solver {
        public PopulationSetter withPopulationOfSize(int size) {
            populationSize = size;
            return new PopulationSetter();
        }
    }

    public class PopulationSetter {
        public PopulationSetter evolvingSolutionsWith(EvolutionaryOperator op) {
            operators.add(op);
            return this;
        }
        public EvaluatorSetter evaluatingSolutionsBy(FitnessEvaluator eval) {
            evaluator = eval;
            return new EvaluatorSetter();
        }
    }

    public class EvaluatorSetter {
        public SelectionSetter performingSelectionWith(SelectionOperator sel) {
            selection = sel;
            return new SelectionSetter();
        }
    }

    public class SelectionSetter {
        public SelectionSetter survivingWith(SurvivalOperator surv) {
            survival = surv;
            return this;
        }

        public StopSetter stoppingAtEvolution(int maxIter) {
            maxIterations = maxIter;
            return new StopSetter();
        }
    }


    public class StopSetter {
        public List<Solution> runOptimization() throws Exception {
            return GeneticAlgorithm.this.runOptimization();
        }
    }

    //===================1.1 Grundimplementierung==============================


    // ----- Kernalgorithmus -----
    private List<Solution> runOptimization()  {

            if (problem == null) {
                throw new IllegalStateException("Problem fehlt!");
            }
            if (evaluator == null) {
                throw new IllegalStateException("FitnessEvaluator fehlt!");
            }
            if (selection == null) {
                throw new IllegalStateException("SelectionOperator fehlt!");
            }
            if (survival == null) {
                throw new IllegalStateException("SurvivalOperator fehlt!");
            }


                Random rand = new Random();
        try {
        // Startpopulation
        List<Solution> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            population.add(problem.createNewSolution());
        }
        evaluator.evaluate(population);

        // Iterationen
        for (int iter = 0; iter < maxIterations; iter++) {
            List<Solution> offspring = new ArrayList<>();


            //===============1.3 Selektionsoperator hier wird integriert =======
            // Nachkommen erzeugen
            for (int i = 0; i < populationSize; i++) {
                Solution parent = selection.selectParent(population);
                EvolutionaryOperator op = operators.get(rand.nextInt(operators.size()));
                Solution child = op.evolve(parent);
                offspring.add(child);
            }

            // Fitness berechnen
            evaluator.evaluate(offspring);

            // Kandidaten = Eltern + Kinder
            List<Solution> candidates = new ArrayList<>(population);
            candidates.addAll(offspring);

            // Neue Population wählen
            population = survival.selectPopulation(candidates, populationSize);
        }

        return population;
    }
    catch (Exception e) {
        System.err.println("Optimierung abgebrochen: " + e.getMessage());
        return Collections.emptyList(); // oder null
    }
    }


    //===================1.1 Grundimplementierung==============================




    /* Startpopulation erzeugen

1-Fitness berechnen

2-Iteration starten:

3-Eltern auswählen

4-Kinder erzeugen

5-Fitness der Kinder berechnen

6-Kandidatenliste = Eltern + Kinder

7-Neue Population wählen (TopK)

8-Wiederholen, bis maxIterations erreicht

9-Beste Population zurückgeben */
}
