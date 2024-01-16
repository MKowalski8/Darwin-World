package components;

public record AnimalInformation(
        int energyRequiredForReproduction,
        int energyUsedByReproduction,
        int startingEnergy,
        int energyUsedToSurviveNextDay,
        int energyProvidedByEating,
        GenomeInformation genomeInfo) {
}
