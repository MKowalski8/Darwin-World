package presenter;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import presenter.smallerElements.GenomeDrawing;
import worldElements.Animal;

import java.util.Arrays;
import java.util.Optional;

public class AnimalStatisticsPresenter {
    public Label isFollowedAnimal;
    public ScrollPane followedGenome;
    public Label followedEnergy;
    public Label followedPlants;
    public Label followedChildren;
    public Label followedDescendants;
    public Label isFollowedAlive;
    public Label followedDays;


    public void updateFollowedAnimalStats(Optional<Animal> followedAnimal, int currentDay) {
        if (followedAnimal.isPresent()) {
            Animal animal = followedAnimal.get();
            if (!animal.isDead()) {
                String genome = Arrays.toString(animal.getGenome().getGenes());
                followedGenome.setContent(GenomeDrawing.drawGenome(genome, animal.getGeneIterator()));

                followedEnergy.setText(getFormat(animal.getEnergy()));
                followedPlants.setText(getFormat(animal.getNumberOfPlants()));
                followedChildren.setText(getFormat(animal.getNumberOfChildren()));
                followedDescendants.setText(getFormat(animal.getNumberOfUniqueDescendants()));

                setFollowedDays(animal);
            } else {
                setObituary(currentDay);
            }

        }
    }

    private void setObituary(int currentDay) {
        if (!isFollowedAlive.getText().equals("Zwierze zmarlo: ")) {
            isFollowedAnimal.setText("SLEDZONY ZWIERZAK NIE ZYJE");
            isFollowedAlive.setText("Zwierze zmarlo: ");
            followedDays.setText(String.format("%d dnia", currentDay));
        }
    }

    private void setFollowedDays(Animal animal) {
        isFollowedAnimal.setText(String.format("SLEDZISZ ZWIERZAKA %s", animal.getId().toString()));
        isFollowedAlive.setText("Zwierze zyje: ");
        followedDays.setText(String.format("%d dni", animal.getLifeTime()));
    }

    private static String getFormat(int statistic) {
        return String.format("%d", statistic);
    }

}
