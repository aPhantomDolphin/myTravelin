package com.example.travelin;

public class Destinations {

    private String cityName;
    private String tags;
    private String info;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTags() {
        return tags;
    }

    public void addTags(String tag) {
        try{
            if(this.tags.equals("") || this.tags==null){
                this.tags="@"+tag;
            }
            else{
                this.tags += "@"+tag;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
