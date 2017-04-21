package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class MainGenerator {
    private static final String PROJECT_DIR = System.getProperty("user.dir");

    public static void main(String[] args) {
        Schema schema = new Schema(1, "com.tylerphelps.motormonitor");
        schema.enableKeepSectionsByDefault();

        addTables(schema);

        try {
            new DaoGenerator().generateAll(schema, PROJECT_DIR + "/app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTables(final Schema schema) {
        Entity sensorModule = addModule(schema);
        Entity sensorDataEntry = addDataEntry(schema);

        //Property sensorModuleId = sensorModule.addLongProperty("sensorModuleId").notNull().getProperty();
        //sensorModule.addToMany(sensorDataEntry, sensorModuleId, "userRepos");
    }

    private static Entity addModule(final Schema schema) {
        Entity module = schema.addEntity("SensorModule");
        module.addIdProperty().primaryKey().autoincrement();
        module.addStringProperty("access_name").notNull();
        module.addStringProperty("access_passcode").notNull();
        module.addStringProperty("viewable_name").notNull();
        module.addStringProperty("group");
        module.addStringProperty("details");

        return module;
    }

    private static Entity addDataEntry(final Schema schema) {
        Entity data = schema.addEntity("SensorDataEntry");
        data.addIdProperty().primaryKey().autoincrement();
        data.addStringProperty("module_access_name").notNull();
        data.addDateProperty("date");
        data.addDoubleProperty("time");
        data.addDoubleProperty("vibration");
        data.addDoubleProperty("temperature");
        data.addDoubleProperty("current");

        return data;
    }
}