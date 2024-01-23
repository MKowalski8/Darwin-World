package components;

import MapStatisticsAndInformations.Boundary;

import java.util.*;

public class PlantGenerator {
    private final Boundary bounds;

    public PlantGenerator(Boundary bounds) {
        this.bounds = bounds;
    }

    public List<Vector2d> generatePlants(HashSet<Vector2d> actualPlants, int growingPlantsNumber) {
        int numberOfJunglePlants = (int) (growingPlantsNumber * 0.8);
        int numberOfPlantsOutsideJungle = growingPlantsNumber - numberOfJunglePlants;
        List<Vector2d> jungleCandidates = new ArrayList<>();
        List<Vector2d> candidatesOutside = new ArrayList<>();

        List<Vector2d> newPlants = new ArrayList<>();

        prepareCandidatesToGrow(actualPlants, jungleCandidates, candidatesOutside);
        //choosePlantsToGrow
        choosePlantsToGrow(newPlants, numberOfJunglePlants, jungleCandidates, numberOfPlantsOutsideJungle, candidatesOutside);

        return newPlants;
    }


    private void choosePlantsToGrow(List<Vector2d> newPlants, int numberOfJunglePlants,
                                    List<Vector2d> jungleCandidates, int numberOfPlantsOutsideJungle, List<Vector2d> candidatesOutside) {

        if (numberOfJunglePlants >= jungleCandidates.size()) {
            numberOfPlantsOutsideJungle += (numberOfJunglePlants - jungleCandidates.size());
            newPlants.addAll(jungleCandidates);
        } else {
            growPlants(numberOfJunglePlants, jungleCandidates, newPlants);
        }

        if (numberOfPlantsOutsideJungle >= candidatesOutside.size()) {
            newPlants.addAll(candidatesOutside);
        } else {
            growPlants(numberOfPlantsOutsideJungle, candidatesOutside, newPlants);
        }
    }

    private void prepareCandidatesToGrow(HashSet<Vector2d> actualPlants, List<Vector2d> jungleCandidates, List<Vector2d> candidatesOutside) {
        for (int x = 0; x < bounds.getWidth(); x++) {
            for (int y = 0; y < bounds.getHeight(); y++) {
                addCandidateToProperList(actualPlants, jungleCandidates, candidatesOutside, x, y);
            }
        }
    }

    private void addCandidateToProperList(HashSet<Vector2d> actualPlants, List<Vector2d> jungleCandidates, List<Vector2d> candidatesOutside, int x, int y) {
        Vector2d candidateToGrow = new Vector2d(x, y);

        if (!actualPlants.contains(candidateToGrow)) {
            if (y >= bounds.getLowerJungleBound() && y < bounds.getUpperJungleBound()) {
                jungleCandidates.add(candidateToGrow);
            } else {
                candidatesOutside.add(candidateToGrow);
            }
        }
    }

    private void growPlants(int numberOfPlantsToGrow, List<Vector2d> candidates, List<Vector2d> newPlants) {
        Collections.shuffle(candidates);
        for (int i = 0; i < numberOfPlantsToGrow; i++) {
            newPlants.add(candidates.get(i));
        }
    }
}
