package io.realm;


public interface com_example_travelin_EventRealmProxyInterface {
    public com.example.travelin.EventLocation realmGet$location();
    public void realmSet$location(com.example.travelin.EventLocation value);
    public java.util.Date realmGet$date();
    public void realmSet$date(java.util.Date value);
    public String realmGet$name();
    public void realmSet$name(String value);
    public RealmList<com.example.travelin.Tag> realmGet$tags();
    public void realmSet$tags(RealmList<com.example.travelin.Tag> value);
    public RealmList<com.example.travelin.User> realmGet$rsvp();
    public void realmSet$rsvp(RealmList<com.example.travelin.User> value);
}
