package io.realm;


public interface com_example_travelin_UserRealmProxyInterface {
    public String realmGet$password();
    public void realmSet$password(String value);
    public String realmGet$email();
    public void realmSet$email(String value);
    public int realmGet$age();
    public void realmSet$age(int value);
    public String realmGet$gender();
    public void realmSet$gender(String value);
    public RealmList<com.example.travelin.MyRating> realmGet$myRatings();
    public void realmSet$myRatings(RealmList<com.example.travelin.MyRating> value);
    public RealmList<com.example.travelin.MyRating> realmGet$reviews();
    public void realmSet$reviews(RealmList<com.example.travelin.MyRating> value);
    public int realmGet$reportCount();
    public void realmSet$reportCount(int value);
    public String realmGet$bio();
    public void realmSet$bio(String value);
    public RealmList<com.example.travelin.Tag> realmGet$interests();
    public void realmSet$interests(RealmList<com.example.travelin.Tag> value);
    public RealmList<com.example.travelin.User> realmGet$blocked();
    public void realmSet$blocked(RealmList<com.example.travelin.User> value);
    public boolean realmGet$deleted();
    public void realmSet$deleted(boolean value);
    public String realmGet$name();
    public void realmSet$name(String value);
    public RealmList<com.example.travelin.DirectMessage> realmGet$messages();
    public void realmSet$messages(RealmList<com.example.travelin.DirectMessage> value);
    public String realmGet$username();
    public void realmSet$username(String value);
    public String realmGet$profilePictureURL();
    public void realmSet$profilePictureURL(String value);
    public RealmList<com.example.travelin.EventLocation> realmGet$previousTrips();
    public void realmSet$previousTrips(RealmList<com.example.travelin.EventLocation> value);
    public RealmList<com.example.travelin.User> realmGet$favorites();
    public void realmSet$favorites(RealmList<com.example.travelin.User> value);
    public RealmList<com.example.travelin.Post> realmGet$posts();
    public void realmSet$posts(RealmList<com.example.travelin.Post> value);
    public double realmGet$avgRating();
    public void realmSet$avgRating(double value);
    public byte[] realmGet$img();
    public void realmSet$img(byte[] value);
    public RealmList<byte[]> realmGet$profileImages();
    public void realmSet$profileImages(RealmList<byte[]> value);
}
