package components;

import maps.MapCell;

import java.util.*;

public class PlantGenerator {
    private final Boundary bounds;

    public PlantGenerator(Boundary bounds) {
        this.bounds = bounds;
    }

    public void generatePlants(List<Vector2d> actualPlants, int growingPlantsNumber) {
        int numberOfJunglePlants = (int) (growingPlantsNumber * 0.8);
        int numberOfPlantsOutsideJungle = growingPlantsNumber - numberOfJunglePlants;
        List<Vector2d> jungleCandidates = new ArrayList<>();
        List<Vector2d> candidatesOutside = new ArrayList<>();

        prepareCandidatesToGrow(actualPlants, jungleCandidates, candidatesOutside);

        //choosePlantsToGrow
        choosePlantsToGrow(actualPlants, numberOfJunglePlants, jungleCandidates, numberOfPlantsOutsideJungle, candidatesOutside);

    }

    private void choosePlantsToGrow(List<Vector2d> actualPlants, int numberOfJunglePlants, List<Vector2d> jungleCandidates, int numberOfPlantsOutsideJungle, List<Vector2d> candidatesOutside) {
        if (numberOfJunglePlants >= jungleCandidates.size()) {
            numberOfPlantsOutsideJungle += (numberOfJunglePlants - jungleCandidates.size());
            actualPlants.addAll(jungleCandidates);
        } else {
            choosePlantsToGrow(numberOfJunglePlants, jungleCandidates, actualPlants);
        }

        if (numberOfPlantsOutsideJungle >= candidatesOutside.size()) {
            actualPlants.addAll(candidatesOutside);
        } else {
            choosePlantsToGrow(numberOfPlantsOutsideJungle, candidatesOutside, actualPlants);
        }
    }

    private void prepareCandidatesToGrow(List<Vector2d> actualPlants, List<Vector2d> jungleCandidates, List<Vector2d> candidatesOutside) {
        for (int x = 0; x < bounds.getWidth(); x++) {
            for (int y = 0; y < bounds.getHeight(); y++) {

                Vector2d candidateToCandidateToGrow = new Vector2d(x, y);

                if (!actualPlants.contains(candidateToCandidateToGrow)) {
                    if (y >= bounds.getLowerJoungleBound() && y < bounds.getUpperJoungleBound()) {
                        jungleCandidates.add(candidateToCandidateToGrow);
                    } else {
                        candidatesOutside.add(candidateToCandidateToGrow);
                    }
                }
            }
        }
    }

    private void choosePlantsToGrow(int numberOfPlantsToGrow, List<Vector2d> candidates, List<Vector2d> actualPlants) {
        Collections.shuffle(candidates);
        for (int i = 0; i < numberOfPlantsToGrow; i++) {
            actualPlants.add(candidates.get(i));
        }
    }

}
