package worldElements;

public class SlowEvolvingAnimal extends AbstractAnimal{


    public SlowEvolvingAnimal(int energyOfAnimal, int energyRequiredForReproduction, int energyUsedByReproduction, int maxMutations, int minMutations, int genomeLength) {
        super(energyOfAnimal, energyRequiredForReproduction, energyUsedByReproduction, maxMutations, minMutations, genomeLength);
    }

    @Override
    public void genomeCreation(int genomeLength) {

    }
}
