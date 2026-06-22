package com.github.selfcrafted.velocity.allowlist;

import org.slf4j.Logger;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Allowlist {
    private final Logger logger;

    private final URI source;
    private Set<UUID> allowlist = Set.of();

    public Allowlist(URI allowlistSource, Logger logger) {
        this.source = allowlistSource;
        this.logger = logger;
    }

    void reload() {
        var scheme = source.getScheme();
        try {
            BufferedReader reader;
            if (scheme.equals("file")) {
                reader = new BufferedReader(new FileReader(source.getPath()));
            } else if (scheme.equals("http")) {
                reader = new BufferedReader(new InputStreamReader(source.toURL().openStream()));
            } else {
                throw new IllegalArgumentException("Unknown scheme: " + scheme);
            }
            Set<UUID> uuidSet = new HashSet<>();
            String uuidLine;
            while ((uuidLine = reader.readLine()) != null) {
                if (uuidLine.startsWith("#") || uuidLine.startsWith("//") || uuidLine.isBlank()) continue;
                try {
                    uuidSet.add(UUID.fromString(uuidLine.stripIndent()));
                } catch (IllegalArgumentException e) {
                    logger.warn("Line is not a valid UUID: \"{}\"\nComment lines start with a '#'.", uuidLine);
                }
            }
            allowlist = Set.copyOf(uuidSet);
        } catch (FileNotFoundException e) {
            try {
                Files.createFile(Path.of(source));
                logger.info("Created allowlist file");
            } catch (IOException e2) {
                logger.warn("Could not create allowlist file", e2);
            }
        } catch (IOException e) {
            logger.error("Could not read allowlist source", e);
        }
    }

    public Set<UUID> getAllowlist() {
        return allowlist;
    }
}
