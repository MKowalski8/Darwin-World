package worldElements;

import components.Genome;
import components.SimulationInformation;
import components.Vector2d;

public class Animal implements WorldElement{
    private final int energyOfAnimal;
    private final int energyRequiredForReproduction;
    private final int energyUsedByReproduction;
    private final SimulationInformation info;
    private final Genome genome;

    public Animal(SimulationInformation info, Genome genome) {
        this.energyOfAnimal = info.startingEnergy();
        this.energyRequiredForReproduction = info.energyRequiredForReproduction();
        this.energyUsedByReproduction = info.energyUsedByReproduction();
        this.genome = genome;
        this.info=info;
    }

    public Animal reproduce(Animal anotherAnimal){
        return new Animal(info,genome);
    }


    @Override
    public Vector2d getPosition() {
        return null;
    }
}
