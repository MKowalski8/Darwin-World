package components;

public record SimulationInformation(int maxMutation,
                                    int minMutation,
                                    boolean slowEvolvingFlag,
                                    boolean portalWorldFlag,
                                    int genomeLength,
                                    int energyRequiredForReproduction,
                                    int energyUsedByReproduction,
                                    int startingEnergy) {
}
