package cr.ac.una.datos.model;

import java.io.*;

public class Player {
    private String name;
    private int levels;

    private static Player instance;

    private Player(String name, int levels) {
        this.name = name;
        this.levels = levels;
    }

    public static Player getInstance() {
        if (instance == null) {
            instance = new Player("DefaultPlayer", 0);
            instance.loadData();
        }
        return instance;
    }

    public String getName() {
        return name;
    }

    public int getLevels() {
        return levels;
    }

    public void setName(String name) {
        this.name = name;
        saveData();
    }

    public void setLevels(int levels) {
        this.levels = levels;
        saveData();
    }

    // Cargar datos desde el archivo
    public void loadData() {
        String saveDirectoryPath = "src/main/resources/cr/ac/una/datos/resources/Levels/player.txt";
        File playerInfo = new File(saveDirectoryPath);

        try {
            if (!playerInfo.exists()) {
                playerInfo.getParentFile().mkdirs();
                playerInfo.createNewFile();
                writeData(saveDirectoryPath);
                System.out.println("Archivo creado en: " + playerInfo.getAbsolutePath());
            } else {
                System.out.println("El archivo ya existe en: " + playerInfo.getAbsolutePath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        loadUnlockedLevels(saveDirectoryPath);
    }

    // Guardar datos en el archivo
    public void saveData() {
        String saveDirectoryPath = "src/main/resources/cr/ac/una/datos/resources/Levels/player.txt";
        writeData(saveDirectoryPath);
    }

    // Escribir datos en el archivo
    private void writeData(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Name: " + this.name);
            writer.newLine();
            writer.write("Levels: " + this.levels);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Cargar niveles desbloqueados desde el archivo
    private void loadUnlockedLevels(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Name: ")) {
                    this.name = line.substring(6);
                } else if (line.startsWith("Levels: ")) {
                    this.levels = Integer.parseInt(line.substring(8));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
