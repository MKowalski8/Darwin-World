package presenter;

import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileConfiguration {

    StartPresenter startPresenter;

    public FileConfiguration(StartPresenter startPresenter) {
        this.startPresenter = startPresenter;
    }

    public void saveActualConfiguration() {
        File file = saveConfigurationFile();
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(startPresenter.getToSave() + "\n" +
                    startPresenter.getMapVariant()+ "\n" +
                    startPresenter.getEvolutionVariant() + "\n" +
                    startPresenter.getMapHeight() + "\n" +
                    startPresenter.getMapWidth() + "\n" +
                    startPresenter.getPlantNumber() + "\n" +
                    startPresenter.getPlantGrowingDaily() + "\n" +
                    startPresenter.getEnergyFromPlant() + "\n" +
                    startPresenter.getStartAnimalEnergy() + "\n" +
                    startPresenter.getStartAnimalNumber() + "\n" +
                    startPresenter.getEnergyForReproduction() + "\n" +
                    startPresenter.getEnergyUsedByReproduction() + "\n" +
                    startPresenter.getMaxMutationNumber() + "\n" +
                    startPresenter.getMinMutationNumber() + "\n" +
                    startPresenter.getGenomeLength() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSavedConfiguration() {
        try(Scanner scanner = new Scanner(loadConfigurationFile())) {
            startPresenter.setToSave(scanner.nextLine());
            startPresenter.setMapVariant(scanner.nextLine());
            startPresenter.setEvolutionVariant(scanner.nextLine());
            startPresenter.setMapHeight(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, scanner.nextInt()));
            startPresenter.setMapWidth(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, scanner.nextInt()));
            startPresenter.setPlantNumber(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, scanner.nextInt()));
            startPresenter.setPlantGrowingDaily(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, scanner.nextInt()));
            startPresenter.setEnergyFromPlant(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, scanner.nextInt()));
            startPresenter.setStartAnimalEnergy(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, scanner.nextInt()));
            startPresenter.setStartAnimalNumber(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 100, scanner.nextInt()));
            startPresenter.setEnergyForReproduction(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, scanner.nextInt()));
            startPresenter.setEnergyUsedByReproduction(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, scanner.nextInt()));
            startPresenter.setMaxMutationNumber(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, scanner.nextInt()));
            startPresenter.setMinMutationNumber(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, scanner.nextInt()));
            startPresenter.setGenomeLength(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, scanner.nextInt()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File loadConfigurationFile(){
        FileChooser fileChooser = configureFileChooser();
        return fileChooser.showOpenDialog(new Stage());
    }

    private static File saveConfigurationFile() {
        FileChooser fileChooser = configureFileChooser();
        return fileChooser.showSaveDialog(new Stage());
    }

    private static FileChooser configureFileChooser() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Conf file", "*.conf"));
        return fileChooser;
    }
}
