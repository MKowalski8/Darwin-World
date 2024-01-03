package worldElements;

public class FastEvolvingAnimal extends AbstractAnimal{

    public FastEvolvingAnimal(int energyOfAnimal, int energyRequiredForReproduction, int energyUsedByReproduction, int maxMutations, int minMutations, int genomeLength) {
        super(energyOfAnimal, energyRequiredForReproduction, energyUsedByReproduction, maxMutations, minMutations, genomeLength);
    }

    @Override
    public void genomeCreation(int genomeLength) {

    }
}
