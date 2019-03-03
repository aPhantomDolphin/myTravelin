package io.realm;


public interface com_example_travelin_PostRealmProxyInterface {
    public String realmGet$type();
    public void realmSet$type(String value);
    public com.example.travelin.User realmGet$author();
    public void realmSet$author(com.example.travelin.User value);
    public java.util.Date realmGet$datePosted();
    public void realmSet$datePosted(java.util.Date value);
    public String realmGet$body();
    public void realmSet$body(String value);
    public int realmGet$rateUp();
    public void realmSet$rateUp(int value);
    public int realmGet$rateDown();
    public void realmSet$rateDown(int value);
    public RealmList<com.example.travelin.Post> realmGet$replies();
    public void realmSet$replies(RealmList<com.example.travelin.Post> value);
    public RealmList<String> realmGet$imageURLs();
    public void realmSet$imageURLs(RealmList<String> value);
}
