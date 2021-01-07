package com.shotexplorer.newsfeed.Models;
//this source will have some of the values like id,description,name,category,language &country
public class Source {
    private String id,name,description,url,category,language,country;

    public Source(String id,String name,String description,String url,String category,String country) {
        this.id = id;
        this.name=name;
        this.description=description;
        this.url=url;
        this.category=category;
        this.country=country;
    }
    public Source(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
