package com.shotexplorer.newsfeed.Models;

import java.util.List;

//for every website we will have a status and arrayList of all the web sources.
public class WebSite {
    private String status;
    private List<Source> sources;

    public WebSite(String status,List<Source> sources) {
        this.status=status;
        this.sources = sources;
    }
    public WebSite(){}

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
