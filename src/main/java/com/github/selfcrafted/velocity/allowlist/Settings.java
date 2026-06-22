package com.github.selfcrafted.velocity.allowlist;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
        private final URI source;
        private final int reloadInterval;
        private final Component deniedReason;

        public State() {
            try {
                source = new URI("file://allowlist.txt");
            } catch (URISyntaxException e) {
                throw new IllegalStateException("Could not parse default value of 'source'", e);
            }
            reloadInterval = 10;
            deniedReason = Component.text("You're not invited to the party...", NamedTextColor.RED);
        }

        public URI getSource() {
            return source;
        }

        public int getReloadInterval() {
            return reloadInterval;
        }

        public Component getDeniedReason() {
            return deniedReason;
        }
    }
}
