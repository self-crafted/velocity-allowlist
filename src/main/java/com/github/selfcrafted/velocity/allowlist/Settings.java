package com.github.selfcrafted.velocity.allowlist;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

public class Settings {
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    private final Logger logger;
    private final File configFile;
    private State state;

    public Settings(Logger logger, File configFile) {
        this.logger = logger;
        this.configFile = configFile;
    }

    public void read() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(configFile));
            state = gson.fromJson(reader, State.class);
        } catch (FileNotFoundException e) {
            state = new State();
            try {
                write();
            } catch (IOException ex) {
                logger.error("Could not create settings file.", ex);
            }
        }
    }

    public void write() throws IOException {
        String json = gson.toJson(state);
        Writer writer = new FileWriter(configFile);
        writer.write(json);
        writer.close();
    }

    public State getState() {
        return state;
    }

    public static class State {
        URI source;
        int reloadInterval;

        public State() {
            try {
                source = new URI("file://allowlist.txt");
            } catch (URISyntaxException ignored) { }
            reloadInterval = 10;
        }

        public URI getSource() {
            return source;
        }

        public int getReloadInterval() {
            return reloadInterval;
        }
    }
}
