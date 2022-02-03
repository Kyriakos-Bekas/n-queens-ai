import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Initialising default values
        int N;
        int population = 1000;
        int maxSteps = 1000;

        Scanner input = new Scanner(System.in);
        System.out.println("Enter Number of Queens (or enter 0 for default input): ");
        N = input.nextInt();

        // If N == 0, run the program for 8 Queens (with default values)
        // else get parameters (population size, maximum steps) from user
        if(N != 0) {
            System.out.println("Enter Population Size: ");
            population = input.nextInt();
            System.out.println("Enter Maximum Steps: ");
            maxSteps = input.nextInt();
        } else {
            N = 8;
        }

        input.close();

        int minFitness = 0;

        // Calculate the fitness of the final state
        for (int i = 1; i < N; i++) {
            minFitness += N-i;
        }

        GeneticAlgorithm algorithm = new GeneticAlgorithm(N);
        //populationSize, mutationProbability, maximumSteps, minimumFitness
        long start = System.currentTimeMillis();
        Chromosome solution = algorithm.run(population, 0.08, maxSteps, minFitness);
        long end = System.currentTimeMillis();

		System.out.println("\n\n" + minFitness);
        solution.print();

        // Testing if the returned chromosome is a solution
        //
        // If the chromosome has fitness equal to the problem's minimum
        // fitness, then the algorithm found a solution
        if (solution.getFitness() == minFitness) {
            System.out.println("Solution found");
        } else {
            System.out.println("Solution not found");
        }

        // Total time of searching in seconds
        System.out.println("Search time: " + (double)(end - start) / 1000 + " sec.");
    }
}