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
        //loadData();
        return name;
    }

    public int getLevels() {
       // loadData();
        return levels;
    }

    public void setName(String name) {
        this.name = name;
        saveData();
    }

    public void setLevels(int levels) {
        System.out.println("Niveles anteriores: " + this.levels);
        this.levels = levels;
        System.out.println("Nuevos niveles: " + this.levels);
        saveData();
    }

    public void incrementarNivel() {
        System.out.println("Niveles anteriores: " + this.levels);
        this.levels++;  // Incrementar el nivel en 1
        System.out.println("Nuevos niveles: " + this.levels);
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
        System.out.println("Guardando datos en el archivo...");
        System.out.println("Name: " + this.name);
        System.out.println("Levels: " + this.levels);

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
