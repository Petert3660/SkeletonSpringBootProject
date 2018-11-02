package com.ptconsultancy.admin.restoperations;

import com.ptconsultancy.admin.adminsupport.Credentials;
import java.io.Serializable;

public class Service implements Serializable {

    private String name;
    private String absolutePath;
    private boolean running;
    private String url = "http://";
    private Credentials credentials;
    private String serviceStatus = "Not Running";

    public Service(String absolutePath, boolean running) {
        this.absolutePath = absolutePath;
        this.running = running;
        name = absolutePath.substring(absolutePath.lastIndexOf('\\') + 1);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
        if (running) {
            setServiceStatus("Running");
        } else {
            setServiceStatus("Not Running");
        }
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public void setUrl(String url) {
        this.url = this.url + url;
    }

    public String getUrl() {
        return url;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }
}
