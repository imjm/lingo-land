package io.openvidu.server;

import org.springframework.boot.info.BuildProperties;

import java.util.Properties;

public class BuildPropertiesEx extends BuildProperties {
    private static BuildProperties instance;

    private BuildPropertiesEx(Properties entries) {
        super(processEntries(entries));
    }

    public static synchronized BuildProperties getInstance(Properties entries) {
        if (instance == null) {
            instance = new BuildProperties(entries);
        }
        return instance;
    }

    public String getGroup() {
        return this.get("group");
    }

    public String getArtifact() {
        return this.get("artifact");
    }

    private static Properties processEntries(Properties entries) {
        // process entries if needed
        return entries;
    }
}
