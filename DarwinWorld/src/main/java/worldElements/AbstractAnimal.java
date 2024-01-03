package worldElements;

import components.Vector2d;

public abstract class AbstractAnimal implements Animal{
    //    Wiele z tych atrybutów pewnie stąd wyleci gdzieś indziej,
    //    albo okaze sie, ze sa praktycznie bezuczytne
    private final int energyOfAnimal;

    private final int energyRequiredForReproduction;
    private final int energyUsedByReproduction;

//    Z tymi atrybutami nie wiem jeszcze do czego beda potrzebne
    private final int maxMutations;

    private final int minMutations;

    protected int[] genome;
//    Nie wiem czy tych spraw nie trzeba bedzie wrzyucic gdzies indziej
//    private int[] genom;

    public AbstractAnimal(int energyOfAnimal, int energyRequiredForReproduction, int energyUsedByReproduction, int maxMutations, int minMutations, int genomeLength) {
        this.energyOfAnimal = energyOfAnimal;
        this.energyRequiredForReproduction = energyRequiredForReproduction;
        this.energyUsedByReproduction = energyUsedByReproduction;
        this.maxMutations = maxMutations;
        this.minMutations = minMutations;
        genomeCreation(genomeLength);
    }



    @Override
    public Vector2d getPosition() {
        return null;
    }


}
