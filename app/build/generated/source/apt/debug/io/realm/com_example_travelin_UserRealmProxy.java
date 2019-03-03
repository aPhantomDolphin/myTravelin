package io.realm;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.JsonReader;
import android.util.JsonToken;
import io.realm.ImportFlag;
import io.realm.ProxyUtils;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsList;
import io.realm.internal.OsObject;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.Property;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.Table;
import io.realm.internal.android.JsonUtils;
import io.realm.internal.objectstore.OsObjectBuilder;
import io.realm.log.RealmLog;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("all")
public class com_example_travelin_UserRealmProxy extends com.example.travelin.User
    implements RealmObjectProxy, com_example_travelin_UserRealmProxyInterface {

    static final class UserColumnInfo extends ColumnInfo {
        long maxColumnIndexValue;
        long passwordIndex;
        long emailIndex;
        long ageIndex;
        long genderIndex;
        long myRatingsIndex;
        long reviewsIndex;
        long reportCountIndex;
        long bioIndex;
        long interestsIndex;
        long blockedIndex;
        long deletedIndex;
        long nameIndex;
        long messagesIndex;
        long usernameIndex;
        long profilePictureURLIndex;
        long previousTripsIndex;
        long favoritesIndex;
        long postsIndex;
        long avgRatingIndex;
        long imgIndex;
        long profileImagesIndex;

        UserColumnInfo(OsSchemaInfo schemaInfo) {
            super(21);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("User");
            this.passwordIndex = addColumnDetails("password", "password", objectSchemaInfo);
            this.emailIndex = addColumnDetails("email", "email", objectSchemaInfo);
            this.ageIndex = addColumnDetails("age", "age", objectSchemaInfo);
            this.genderIndex = addColumnDetails("gender", "gender", objectSchemaInfo);
            this.myRatingsIndex = addColumnDetails("myRatings", "myRatings", objectSchemaInfo);
            this.reviewsIndex = addColumnDetails("reviews", "reviews", objectSchemaInfo);
            this.reportCountIndex = addColumnDetails("reportCount", "reportCount", objectSchemaInfo);
            this.bioIndex = addColumnDetails("bio", "bio", objectSchemaInfo);
            this.interestsIndex = addColumnDetails("interests", "interests", objectSchemaInfo);
            this.blockedIndex = addColumnDetails("blocked", "blocked", objectSchemaInfo);
            this.deletedIndex = addColumnDetails("deleted", "deleted", objectSchemaInfo);
            this.nameIndex = addColumnDetails("name", "name", objectSchemaInfo);
            this.messagesIndex = addColumnDetails("messages", "messages", objectSchemaInfo);
            this.usernameIndex = addColumnDetails("username", "username", objectSchemaInfo);
            this.profilePictureURLIndex = addColumnDetails("profilePictureURL", "profilePictureURL", objectSchemaInfo);
            this.previousTripsIndex = addColumnDetails("previousTrips", "previousTrips", objectSchemaInfo);
            this.favoritesIndex = addColumnDetails("favorites", "favorites", objectSchemaInfo);
            this.postsIndex = addColumnDetails("posts", "posts", objectSchemaInfo);
            this.avgRatingIndex = addColumnDetails("avgRating", "avgRating", objectSchemaInfo);
            this.imgIndex = addColumnDetails("img", "img", objectSchemaInfo);
            this.profileImagesIndex = addColumnDetails("profileImages", "profileImages", objectSchemaInfo);
            this.maxColumnIndexValue = objectSchemaInfo.getMaxColumnIndex();
        }

        UserColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new UserColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final UserColumnInfo src = (UserColumnInfo) rawSrc;
            final UserColumnInfo dst = (UserColumnInfo) rawDst;
            dst.passwordIndex = src.passwordIndex;
            dst.emailIndex = src.emailIndex;
            dst.ageIndex = src.ageIndex;
            dst.genderIndex = src.genderIndex;
            dst.myRatingsIndex = src.myRatingsIndex;
            dst.reviewsIndex = src.reviewsIndex;
            dst.reportCountIndex = src.reportCountIndex;
            dst.bioIndex = src.bioIndex;
            dst.interestsIndex = src.interestsIndex;
            dst.blockedIndex = src.blockedIndex;
            dst.deletedIndex = src.deletedIndex;
            dst.nameIndex = src.nameIndex;
            dst.messagesIndex = src.messagesIndex;
            dst.usernameIndex = src.usernameIndex;
            dst.profilePictureURLIndex = src.profilePictureURLIndex;
            dst.previousTripsIndex = src.previousTripsIndex;
            dst.favoritesIndex = src.favoritesIndex;
            dst.postsIndex = src.postsIndex;
            dst.avgRatingIndex = src.avgRatingIndex;
            dst.imgIndex = src.imgIndex;
            dst.profileImagesIndex = src.profileImagesIndex;
            dst.maxColumnIndexValue = src.maxColumnIndexValue;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private UserColumnInfo columnInfo;
    private ProxyState<com.example.travelin.User> proxyState;
    private RealmList<com.example.travelin.MyRating> myRatingsRealmList;
    private RealmList<com.example.travelin.MyRating> reviewsRealmList;
    private RealmList<com.example.travelin.Tag> interestsRealmList;
    private RealmList<com.example.travelin.User> blockedRealmList;
    private RealmList<com.example.travelin.DirectMessage> messagesRealmList;
    private RealmList<com.example.travelin.EventLocation> previousTripsRealmList;
    private RealmList<com.example.travelin.User> favoritesRealmList;
    private RealmList<com.example.travelin.Post> postsRealmList;
    private RealmList<byte[]> profileImagesRealmList;

    com_example_travelin_UserRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (UserColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.example.travelin.User>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$password() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.passwordIndex);
    }

    @Override
    public void realmSet$password(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.passwordIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.passwordIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.passwordIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.passwordIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$email() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.emailIndex);
    }

    @Override
    public void realmSet$email(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.emailIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.emailIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.emailIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.emailIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$age() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.ageIndex);
    }

    @Override
    public void realmSet$age(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.ageIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.ageIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$gender() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.genderIndex);
    }

    @Override
    public void realmSet$gender(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.genderIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.genderIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.genderIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.genderIndex, value);
    }

    @Override
    public RealmList<com.example.travelin.MyRating> realmGet$myRatings() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (myRatingsRealmList != null) {
            return myRatingsRealmList;
        } else {
            OsList osList = proxyState.getRow$realm().getModelList(columnInfo.myRatingsIndex);
            myRatingsRealmList = new RealmList<com.example.travelin.MyRating>(com.example.travelin.MyRating.class, osList, proxyState.getRealm$realm());
            return myRatingsRealmList;
        }
    }

    @Override
    public void realmSet$myRatings(RealmList<com.example.travelin.MyRating> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("myRatings")) {
                return;
            }
            // if the list contains unmanaged RealmObjects, convert them to managed.
            if (value != null && !value.isManaged()) {
                final Realm realm = (Realm) proxyState.getRealm$realm();
                final RealmList<com.example.travelin.MyRating> original = value;
                value = new RealmList<com.example.travelin.MyRating>();
                for (com.example.travelin.MyRating item : original) {
                    if (item == null || RealmObject.isManaged(item)) {
                        value.add(item);
                    } else {
                        value.add(realm.copyToRealm(item));
                    }
                }
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        OsList osList = proxyState.getRow$realm().getModelList(columnInfo.myRatingsIndex);
        // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
        if (value != null && value.size() == osList.size()) {
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.example.travelin.MyRating linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.setRow(i, ((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        } else {
            osList.removeAll();
            if (value == null) {
                return;
            }
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.example.travelin.MyRating linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.addRow(((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        }
    }

    @Override
    public RealmList<com.example.travelin.MyRating> realmGet$reviews() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (reviewsRealmList != null) {
            return reviewsRealmList;
        } else {
            OsList osList = proxyState.getRow$realm().getModelList(columnInfo.reviewsIndex);
            reviewsRealmList = new RealmList<com.example.travelin.MyRating>(com.example.travelin.MyRating.class, osList, proxyState.getRealm$realm());
            return reviewsRealmList;
        }
    }

    @Override
    public void realmSet$reviews(RealmList<com.example.travelin.MyRating> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("reviews")) {
                return;
            }
            // if the list contains unmanaged RealmObjects, convert them to managed.
            if (value != null && !value.isManaged()) {
                final Realm realm = (Realm) proxyState.getRealm$realm();
                final RealmList<com.example.travelin.MyRating> original = value;
                value = new RealmList<com.example.travelin.MyRating>();
                for (com.example.travelin.MyRating item : original) {
                    if (item == null || RealmObject.isManaged(item)) {
                        value.add(item);
                    } else {
                        value.add(realm.copyToRealm(item));
                    }
                }
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        OsList osList = proxyState.getRow$realm().getModelList(columnInfo.reviewsIndex);
        // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
        if (value != null && value.size() == osList.size()) {
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.example.travelin.MyRating linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.setRow(i, ((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        } else {
            osList.removeAll();
            if (value == null) {
                return;
            }
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.example.travelin.MyRating linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.addRow(((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        }
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$reportCount() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.reportCountIndex);
    }

    @Override
    public void realmSet$reportCount(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.reportCountIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.reportCountIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$bio() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.bioIndex);
    }

    @Override
    public void realmSet$bio(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.bioIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.bioIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.bioIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.bioIndex, value);
    }

    @Override
    public RealmList<com.example.travelin.Tag> realmGet$interests() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (interestsRealmList != null) {
            return interestsRealmList;
        } else {
            OsList osList = proxyState.getRow$realm().getModelList(columnInfo.interestsIndex);
            interestsRealmList = new RealmList<com.example.travelin.Tag>(com.example.travelin.Tag.class, osList, proxyState.getRealm$realm());
            return interestsRealmList;
        }
    }

    @Override
    public void realmSet$interests(RealmList<com.example.travelin.Tag> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("interests")) {
                return;
            }
            // if the list contains unmanaged RealmObjects, convert them to managed.
            if (value != null && !value.isManaged()) {
                final Realm realm = (Realm) proxyState.getRealm$realm();
                final RealmList<com.example.travelin.Tag> original = value;
                value = new RealmList<com.example.travelin.Tag>();
                for (com.example.travelin.Tag item : original) {
                    if (item == null || RealmObject.isManaged(item)) {
                        value.add(item);
                    } else {
                        value.add(realm.copyToRealm(item));
                    }
                }
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        OsList osList = proxyState.getRow$realm().getModelList(columnInfo.interestsIndex);
        // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
        if (value != null && value.size() == osList.size()) {
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.example.travelin.Tag linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.setRow(i, ((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        } else {
            osList.removeAll();
            if (value == null) {
                return;
            }
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.example.travelin.Tag linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.addRow(((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        }
    }

    @Override
    public RealmList<com.example.travelin.User> realmGet$blocked() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (blockedRealmList != null) {
            return blockedRealmList;
        } else {
            OsList osList = proxyState.getRow$realm().getModelList(columnInfo.blockedIndex);
            blockedRealmList = new RealmList<com.example.travelin.User>(com.example.travelin.User.class, osList, proxyState.getRealm$realm());
            return blockedRealmList;
        }
    }

    @Override
    public void realmSet$blocked(RealmList<com.example.travelin.User> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("blocked")) {
                return;
            }
            // if the list contains unmanaged RealmObjects, convert them to managed.
            if (value != null && !value.isManaged()) {
                final Realm realm = (Realm) proxyState.getRealm$realm();
                final RealmList<com.example.travelin.User> original = value;
                value = new RealmList<com.example.travelin.User>();
                for (com.example.travelin.User item : original) {
                    if (item == null || RealmObject.isManaged(item)) {
                        value.add(item);
                    } else {
                        value.add(realm.copyToRealm(item));
                    }
                }
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        OsList osList = proxyState.getRow$realm().getModelList(columnInfo.blockedIndex);
        // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
        if (value != null && value.size() == osList.size()) {
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.example.travelin.User linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.setRow(i, ((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        } else {
            osList.removeAll();
            if (value == null) {
                return;
            }
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.example.travelin.User linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.addRow(((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        }
    }

    @Override
    @SuppressWarnings("cast")
    public boolean realmGet$deleted() {
        proxyState.getRealm$realm().checkIfValid();
        return (boolean) proxyState.getRow$realm().getBoolean(columnInfo.deletedIndex);
    }

    @Override
    public void realmSet$deleted(boolean value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setBoolean(columnInfo.deletedIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setBoolean(columnInfo.deletedIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$name() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.nameIndex);
    }

    @Override
    public void realmSet$name(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.nameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.nameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.nameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.nameIndex, value);
    }

    @Override
    public RealmList<com.example.travelin.DirectMessage> realmGet$messages() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (messagesRealmList != null) {
            return messagesRealmList;
        } else {
            OsList osList = proxyState.getRow$realm().getModelList(columnInfo.messagesIndex);
            messagesRealmList = new RealmList<com.example.travelin.DirectMessage>(com.example.travelin.DirectMessage.class, osList, proxyState.getRealm$realm());
            return messagesRealmList;
        }
    }

    @Override
    public void realmSet$messages(RealmList<com.example.travelin.DirectMessage> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("messages")) {
                return;
            }
            // if the list contains unmanaged RealmObjects, convert them to managed.
            if (value != null && !value.isManaged()) {
                final Realm realm = (Realm) proxyState.getRealm$realm();
                final RealmList<com.example.travelin.DirectMessage> original = value;
                value = new RealmList<com.example.travelin.DirectMessage>();
                for (com.example.travelin.DirectMessage item : original) {
                    if (item == null || RealmObject.isManaged(item)) {
                        value.add(item);
                    } else {
                        value.add(realm.copyToRealm(item));
                    }
                }
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        OsList osList = proxyState.getRow$realm().getModelList(columnInfo.messagesIndex);
        // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
        if (value != null && value.size() == osList.size()) {
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.example.travelin.DirectMessage linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.setRow(i, ((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        } else {
            osList.removeAll();
            if (value == null) {
                return;
            }
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.example.travelin.DirectMessage linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.addRow(((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        }
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$username() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.usernameIndex);
    }

    @Override
    public void realmSet$username(String value) {
        if (proxyState.isUnderConstruction()) {
            // default value of the primary key is always ignored.
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        throw new io.realm.exceptions.RealmException("Primary key field 'username' cannot be changed after object was created.");
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$profilePictureURL() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.profilePictureURLIndex);
    }

    @Override
    public void realmSet$profilePictureURL(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.profilePictureURLIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.profilePictureURLIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.profilePictureURLIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.profilePictureURLIndex, value);
    }

    @Override
    public RealmList<com.example.travelin.EventLocation> realmGet$previousTrips() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (previousTripsRealmList != null) {
            return previousTripsRealmList;
        } else {
            OsList osList = proxyState.getRow$realm().getModelList(columnInfo.previousTripsIndex);
            previousTripsRealmList = new RealmList<com.example.travelin.EventLocation>(com.example.travelin.EventLocation.class, osList, proxyState.getRealm$realm());
            return previousTripsRealmList;
        }
    }

    @Override
    public void realmSet$previousTrips(RealmList<com.example.travelin.EventLocation> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("previousTrips")) {
                return;
            }
            // if the list contains unmanaged RealmObjects, convert them to managed.
            if (value != null && !value.isManaged()) {
                final Realm realm = (Realm) proxyState.getRealm$realm();
                final RealmList<com.example.travelin.EventLocation> original = value;
                value = new RealmList<com.example.travelin.EventLocation>();
                for (com.example.travelin.EventLocation item : original) {
                    if (item == null || RealmObject.isManaged(item)) {
                        value.add(item);
                    } else {
                        value.add(realm.copyToRealm(item));
                    }
                }
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        OsList osList = proxyState.getRow$realm().getModelList(columnInfo.previousTripsIndex);
        // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
        if (value != null && value.size() == osList.size()) {
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.example.travelin.EventLocation linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.setRow(i, ((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        } else {
            osList.removeAll();
            if (value == null) {
                return;
            }
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.example.travelin.EventLocation linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.addRow(((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        }
    }

    @Override
    public RealmList<com.example.travelin.User> realmGet$favorites() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (favoritesRealmList != null) {
            return favoritesRealmList;
        } else {
            OsList osList = proxyState.getRow$realm().getModelList(columnInfo.favoritesIndex);
            favoritesRealmList = new RealmList<com.example.travelin.User>(com.example.travelin.User.class, osList, proxyState.getRealm$realm());
            return favoritesRealmList;
        }
    }

    @Override
    public void realmSet$favorites(RealmList<com.example.travelin.User> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("favorites")) {
                return;
            }
            // if the list contains unmanaged RealmObjects, convert them to managed.
            if (value != null && !value.isManaged()) {
                final Realm realm = (Realm) proxyState.getRealm$realm();
                final RealmList<com.example.travelin.User> original = value;
                value = new RealmList<com.example.travelin.User>();
                for (com.example.travelin.User item : original) {
                    if (item == null || RealmObject.isManaged(item)) {
                        value.add(item);
                    } else {
                        value.add(realm.copyToRealm(item));
                    }
                }
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        OsList osList = proxyState.getRow$realm().getModelList(columnInfo.favoritesIndex);
        // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
        if (value != null && value.size() == osList.size()) {
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.example.travelin.User linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.setRow(i, ((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        } else {
            osList.removeAll();
            if (value == null) {
                return;
            }
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.example.travelin.User linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.addRow(((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        }
    }

    @Override
    public RealmList<com.example.travelin.Post> realmGet$posts() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (postsRealmList != null) {
            return postsRealmList;
        } else {
            OsList osList = proxyState.getRow$realm().getModelList(columnInfo.postsIndex);
            postsRealmList = new RealmList<com.example.travelin.Post>(com.example.travelin.Post.class, osList, proxyState.getRealm$realm());
            return postsRealmList;
        }
    }

    @Override
    public void realmSet$posts(RealmList<com.example.travelin.Post> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("posts")) {
                return;
            }
            // if the list contains unmanaged RealmObjects, convert them to managed.
            if (value != null && !value.isManaged()) {
                final Realm realm = (Realm) proxyState.getRealm$realm();
                final RealmList<com.example.travelin.Post> original = value;
                value = new RealmList<com.example.travelin.Post>();
                for (com.example.travelin.Post item : original) {
                    if (item == null || RealmObject.isManaged(item)) {
                        value.add(item);
                    } else {
                        value.add(realm.copyToRealm(item));
                    }
                }
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        OsList osList = proxyState.getRow$realm().getModelList(columnInfo.postsIndex);
        // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
        if (value != null && value.size() == osList.size()) {
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.example.travelin.Post linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.setRow(i, ((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        } else {
            osList.removeAll();
            if (value == null) {
                return;
            }
            int objects = value.size();
            for (int i = 0; i < objects; i++) {
                com.example.travelin.Post linkedObject = value.get(i);
                proxyState.checkValidObject(linkedObject);
                osList.addRow(((RealmObjectProxy) linkedObject).realmGet$proxyState().getRow$realm().getIndex());
            }
        }
    }

    @Override
    @SuppressWarnings("cast")
    public double realmGet$avgRating() {
        proxyState.getRealm$realm().checkIfValid();
        return (double) proxyState.getRow$realm().getDouble(columnInfo.avgRatingIndex);
    }

    @Override
    public void realmSet$avgRating(double value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setDouble(columnInfo.avgRatingIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setDouble(columnInfo.avgRatingIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public byte[] realmGet$img() {
        proxyState.getRealm$realm().checkIfValid();
        return (byte[]) proxyState.getRow$realm().getBinaryByteArray(columnInfo.imgIndex);
    }

    @Override
    public void realmSet$img(byte[] value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.imgIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setBinaryByteArray(columnInfo.imgIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.imgIndex);
            return;
        }
        proxyState.getRow$realm().setBinaryByteArray(columnInfo.imgIndex, value);
    }

    @Override
    public RealmList<byte[]> realmGet$profileImages() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (profileImagesRealmList != null) {
            return profileImagesRealmList;
        } else {
            OsList osList = proxyState.getRow$realm().getValueList(columnInfo.profileImagesIndex, RealmFieldType.BINARY_LIST);
            profileImagesRealmList = new RealmList<byte[]>(byte[].class, osList, proxyState.getRealm$realm());
            return profileImagesRealmList;
        }
    }

    @Override
    public void realmSet$profileImages(RealmList<byte[]> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("profileImages")) {
                return;
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        OsList osList = proxyState.getRow$realm().getValueList(columnInfo.profileImagesIndex, RealmFieldType.BINARY_LIST);
        osList.removeAll();
        if (value == null) {
            return;
        }
        for (byte[] item : value) {
            if (item == null) {
                osList.addNull();
            } else {
                osList.addBinary(item);
            }
        }
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("User", 21, 0);
        builder.addPersistedProperty("password", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("email", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("age", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("gender", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedLinkProperty("myRatings", RealmFieldType.LIST, "MyRating");
        builder.addPersistedLinkProperty("reviews", RealmFieldType.LIST, "MyRating");
        builder.addPersistedProperty("reportCount", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("bio", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedLinkProperty("interests", RealmFieldType.LIST, "Tag");
        builder.addPersistedLinkProperty("blocked", RealmFieldType.LIST, "User");
        builder.addPersistedProperty("deleted", RealmFieldType.BOOLEAN, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedLinkProperty("messages", RealmFieldType.LIST, "DirectMessage");
        builder.addPersistedProperty("username", RealmFieldType.STRING, Property.PRIMARY_KEY, Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("profilePictureURL", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedLinkProperty("previousTrips", RealmFieldType.LIST, "EventLocation");
        builder.addPersistedLinkProperty("favorites", RealmFieldType.LIST, "User");
        builder.addPersistedLinkProperty("posts", RealmFieldType.LIST, "Post");
        builder.addPersistedProperty("avgRating", RealmFieldType.DOUBLE, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("img", RealmFieldType.BINARY, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedValueListProperty("profileImages", RealmFieldType.BINARY_LIST, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static UserColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new UserColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "User";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "User";
    }

    @SuppressWarnings("cast")
    public static com.example.travelin.User createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(9);
        com.example.travelin.User obj = null;
        if (update) {
            Table table = realm.getTable(com.example.travelin.User.class);
            UserColumnInfo columnInfo = (UserColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.User.class);
            long pkColumnIndex = columnInfo.usernameIndex;
            long rowIndex = Table.NO_MATCH;
            if (json.isNull("username")) {
                rowIndex = table.findFirstNull(pkColumnIndex);
            } else {
                rowIndex = table.findFirstString(pkColumnIndex, json.getString("username"));
            }
            if (rowIndex != Table.NO_MATCH) {
                final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), realm.getSchema().getColumnInfo(com.example.travelin.User.class), false, Collections.<String> emptyList());
                    obj = new io.realm.com_example_travelin_UserRealmProxy();
                } finally {
                    objectContext.clear();
                }
            }
        }
        if (obj == null) {
            if (json.has("myRatings")) {
                excludeFields.add("myRatings");
            }
            if (json.has("reviews")) {
                excludeFields.add("reviews");
            }
            if (json.has("interests")) {
                excludeFields.add("interests");
            }
            if (json.has("blocked")) {
                excludeFields.add("blocked");
            }
            if (json.has("messages")) {
                excludeFields.add("messages");
            }
            if (json.has("previousTrips")) {
                excludeFields.add("previousTrips");
            }
            if (json.has("favorites")) {
                excludeFields.add("favorites");
            }
            if (json.has("posts")) {
                excludeFields.add("posts");
            }
            if (json.has("profileImages")) {
                excludeFields.add("profileImages");
            }
            if (json.has("username")) {
                if (json.isNull("username")) {
                    obj = (io.realm.com_example_travelin_UserRealmProxy) realm.createObjectInternal(com.example.travelin.User.class, null, true, excludeFields);
                } else {
                    obj = (io.realm.com_example_travelin_UserRealmProxy) realm.createObjectInternal(com.example.travelin.User.class, json.getString("username"), true, excludeFields);
                }
            } else {
                throw new IllegalArgumentException("JSON object doesn't have the primary key field 'username'.");
            }
        }

        final com_example_travelin_UserRealmProxyInterface objProxy = (com_example_travelin_UserRealmProxyInterface) obj;
        if (json.has("password")) {
            if (json.isNull("password")) {
                objProxy.realmSet$password(null);
            } else {
                objProxy.realmSet$password((String) json.getString("password"));
            }
        }
        if (json.has("email")) {
            if (json.isNull("email")) {
                objProxy.realmSet$email(null);
            } else {
                objProxy.realmSet$email((String) json.getString("email"));
            }
        }
        if (json.has("age")) {
            if (json.isNull("age")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'age' to null.");
            } else {
                objProxy.realmSet$age((int) json.getInt("age"));
            }
        }
        if (json.has("gender")) {
            if (json.isNull("gender")) {
                objProxy.realmSet$gender(null);
            } else {
                objProxy.realmSet$gender((String) json.getString("gender"));
            }
        }
        if (json.has("myRatings")) {
            if (json.isNull("myRatings")) {
                objProxy.realmSet$myRatings(null);
            } else {
                objProxy.realmGet$myRatings().clear();
                JSONArray array = json.getJSONArray("myRatings");
                for (int i = 0; i < array.length(); i++) {
                    com.example.travelin.MyRating item = com_example_travelin_MyRatingRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    objProxy.realmGet$myRatings().add(item);
                }
            }
        }
        if (json.has("reviews")) {
            if (json.isNull("reviews")) {
                objProxy.realmSet$reviews(null);
            } else {
                objProxy.realmGet$reviews().clear();
                JSONArray array = json.getJSONArray("reviews");
                for (int i = 0; i < array.length(); i++) {
                    com.example.travelin.MyRating item = com_example_travelin_MyRatingRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    objProxy.realmGet$reviews().add(item);
                }
            }
        }
        if (json.has("reportCount")) {
            if (json.isNull("reportCount")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'reportCount' to null.");
            } else {
                objProxy.realmSet$reportCount((int) json.getInt("reportCount"));
            }
        }
        if (json.has("bio")) {
            if (json.isNull("bio")) {
                objProxy.realmSet$bio(null);
            } else {
                objProxy.realmSet$bio((String) json.getString("bio"));
            }
        }
        if (json.has("interests")) {
            if (json.isNull("interests")) {
                objProxy.realmSet$interests(null);
            } else {
                objProxy.realmGet$interests().clear();
                JSONArray array = json.getJSONArray("interests");
                for (int i = 0; i < array.length(); i++) {
                    com.example.travelin.Tag item = com_example_travelin_TagRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    objProxy.realmGet$interests().add(item);
                }
            }
        }
        if (json.has("blocked")) {
            if (json.isNull("blocked")) {
                objProxy.realmSet$blocked(null);
            } else {
                objProxy.realmGet$blocked().clear();
                JSONArray array = json.getJSONArray("blocked");
                for (int i = 0; i < array.length(); i++) {
                    com.example.travelin.User item = com_example_travelin_UserRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    objProxy.realmGet$blocked().add(item);
                }
            }
        }
        if (json.has("deleted")) {
            if (json.isNull("deleted")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'deleted' to null.");
            } else {
                objProxy.realmSet$deleted((boolean) json.getBoolean("deleted"));
            }
        }
        if (json.has("name")) {
            if (json.isNull("name")) {
                objProxy.realmSet$name(null);
            } else {
                objProxy.realmSet$name((String) json.getString("name"));
            }
        }
        if (json.has("messages")) {
            if (json.isNull("messages")) {
                objProxy.realmSet$messages(null);
            } else {
                objProxy.realmGet$messages().clear();
                JSONArray array = json.getJSONArray("messages");
                for (int i = 0; i < array.length(); i++) {
                    com.example.travelin.DirectMessage item = com_example_travelin_DirectMessageRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    objProxy.realmGet$messages().add(item);
                }
            }
        }
        if (json.has("profilePictureURL")) {
            if (json.isNull("profilePictureURL")) {
                objProxy.realmSet$profilePictureURL(null);
            } else {
                objProxy.realmSet$profilePictureURL((String) json.getString("profilePictureURL"));
            }
        }
        if (json.has("previousTrips")) {
            if (json.isNull("previousTrips")) {
                objProxy.realmSet$previousTrips(null);
            } else {
                objProxy.realmGet$previousTrips().clear();
                JSONArray array = json.getJSONArray("previousTrips");
                for (int i = 0; i < array.length(); i++) {
                    com.example.travelin.EventLocation item = com_example_travelin_EventLocationRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    objProxy.realmGet$previousTrips().add(item);
                }
            }
        }
        if (json.has("favorites")) {
            if (json.isNull("favorites")) {
                objProxy.realmSet$favorites(null);
            } else {
                objProxy.realmGet$favorites().clear();
                JSONArray array = json.getJSONArray("favorites");
                for (int i = 0; i < array.length(); i++) {
                    com.example.travelin.User item = com_example_travelin_UserRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    objProxy.realmGet$favorites().add(item);
                }
            }
        }
        if (json.has("posts")) {
            if (json.isNull("posts")) {
                objProxy.realmSet$posts(null);
            } else {
                objProxy.realmGet$posts().clear();
                JSONArray array = json.getJSONArray("posts");
                for (int i = 0; i < array.length(); i++) {
                    com.example.travelin.Post item = com_example_travelin_PostRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    objProxy.realmGet$posts().add(item);
                }
            }
        }
        if (json.has("avgRating")) {
            if (json.isNull("avgRating")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'avgRating' to null.");
            } else {
                objProxy.realmSet$avgRating((double) json.getDouble("avgRating"));
            }
        }
        if (json.has("img")) {
            if (json.isNull("img")) {
                objProxy.realmSet$img(null);
            } else {
                objProxy.realmSet$img(JsonUtils.stringToBytes(json.getString("img")));
            }
        }
        ProxyUtils.setRealmListWithJsonObject(objProxy.realmGet$profileImages(), json, "profileImages");
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.example.travelin.User createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        boolean jsonHasPrimaryKey = false;
        final com.example.travelin.User obj = new com.example.travelin.User();
        final com_example_travelin_UserRealmProxyInterface objProxy = (com_example_travelin_UserRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("password")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$password((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$password(null);
                }
            } else if (name.equals("email")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$email((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$email(null);
                }
            } else if (name.equals("age")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$age((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'age' to null.");
                }
            } else if (name.equals("gender")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$gender((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$gender(null);
                }
            } else if (name.equals("myRatings")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$myRatings(null);
                } else {
                    objProxy.realmSet$myRatings(new RealmList<com.example.travelin.MyRating>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.example.travelin.MyRating item = com_example_travelin_MyRatingRealmProxy.createUsingJsonStream(realm, reader);
                        objProxy.realmGet$myRatings().add(item);
                    }
                    reader.endArray();
                }
            } else if (name.equals("reviews")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$reviews(null);
                } else {
                    objProxy.realmSet$reviews(new RealmList<com.example.travelin.MyRating>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.example.travelin.MyRating item = com_example_travelin_MyRatingRealmProxy.createUsingJsonStream(realm, reader);
                        objProxy.realmGet$reviews().add(item);
                    }
                    reader.endArray();
                }
            } else if (name.equals("reportCount")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$reportCount((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'reportCount' to null.");
                }
            } else if (name.equals("bio")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$bio((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$bio(null);
                }
            } else if (name.equals("interests")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$interests(null);
                } else {
                    objProxy.realmSet$interests(new RealmList<com.example.travelin.Tag>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.example.travelin.Tag item = com_example_travelin_TagRealmProxy.createUsingJsonStream(realm, reader);
                        objProxy.realmGet$interests().add(item);
                    }
                    reader.endArray();
                }
            } else if (name.equals("blocked")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$blocked(null);
                } else {
                    objProxy.realmSet$blocked(new RealmList<com.example.travelin.User>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.example.travelin.User item = com_example_travelin_UserRealmProxy.createUsingJsonStream(realm, reader);
                        objProxy.realmGet$blocked().add(item);
                    }
                    reader.endArray();
                }
            } else if (name.equals("deleted")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$deleted((boolean) reader.nextBoolean());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'deleted' to null.");
                }
            } else if (name.equals("name")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$name((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$name(null);
                }
            } else if (name.equals("messages")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$messages(null);
                } else {
                    objProxy.realmSet$messages(new RealmList<com.example.travelin.DirectMessage>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.example.travelin.DirectMessage item = com_example_travelin_DirectMessageRealmProxy.createUsingJsonStream(realm, reader);
                        objProxy.realmGet$messages().add(item);
                    }
                    reader.endArray();
                }
            } else if (name.equals("username")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$username((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$username(null);
                }
                jsonHasPrimaryKey = true;
            } else if (name.equals("profilePictureURL")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$profilePictureURL((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$profilePictureURL(null);
                }
            } else if (name.equals("previousTrips")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$previousTrips(null);
                } else {
                    objProxy.realmSet$previousTrips(new RealmList<com.example.travelin.EventLocation>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.example.travelin.EventLocation item = com_example_travelin_EventLocationRealmProxy.createUsingJsonStream(realm, reader);
                        objProxy.realmGet$previousTrips().add(item);
                    }
                    reader.endArray();
                }
            } else if (name.equals("favorites")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$favorites(null);
                } else {
                    objProxy.realmSet$favorites(new RealmList<com.example.travelin.User>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.example.travelin.User item = com_example_travelin_UserRealmProxy.createUsingJsonStream(realm, reader);
                        objProxy.realmGet$favorites().add(item);
                    }
                    reader.endArray();
                }
            } else if (name.equals("posts")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$posts(null);
                } else {
                    objProxy.realmSet$posts(new RealmList<com.example.travelin.Post>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.example.travelin.Post item = com_example_travelin_PostRealmProxy.createUsingJsonStream(realm, reader);
                        objProxy.realmGet$posts().add(item);
                    }
                    reader.endArray();
                }
            } else if (name.equals("avgRating")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$avgRating((double) reader.nextDouble());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'avgRating' to null.");
                }
            } else if (name.equals("img")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$img(JsonUtils.stringToBytes(reader.nextString()));
                } else {
                    reader.skipValue();
                    objProxy.realmSet$img(null);
                }
            } else if (name.equals("profileImages")) {
                objProxy.realmSet$profileImages(ProxyUtils.createRealmListWithJsonStream(byte[].class, reader));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        if (!jsonHasPrimaryKey) {
            throw new IllegalArgumentException("JSON object doesn't have the primary key field 'username'.");
        }
        return realm.copyToRealm(obj);
    }

    private static com_example_travelin_UserRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating uexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.example.travelin.User.class), false, Collections.<String>emptyList());
        io.realm.com_example_travelin_UserRealmProxy obj = new io.realm.com_example_travelin_UserRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.example.travelin.User copyOrUpdate(Realm realm, UserColumnInfo columnInfo, com.example.travelin.User object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null) {
            final BaseRealm otherRealm = ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm();
            if (otherRealm.threadId != realm.threadId) {
                throw new IllegalArgumentException("Objects which belong to Realm instances in other threads cannot be copied into this Realm instance.");
            }
            if (otherRealm.getPath().equals(realm.getPath())) {
                return object;
            }
        }
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        RealmObjectProxy cachedRealmObject = cache.get(object);
        if (cachedRealmObject != null) {
            return (com.example.travelin.User) cachedRealmObject;
        }

        com.example.travelin.User realmObject = null;
        boolean canUpdate = update;
        if (canUpdate) {
            Table table = realm.getTable(com.example.travelin.User.class);
            long pkColumnIndex = columnInfo.usernameIndex;
            String value = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$username();
            long rowIndex = Table.NO_MATCH;
            if (value == null) {
                rowIndex = table.findFirstNull(pkColumnIndex);
            } else {
                rowIndex = table.findFirstString(pkColumnIndex, value);
            }
            if (rowIndex == Table.NO_MATCH) {
                canUpdate = false;
            } else {
                try {
                    objectContext.set(realm, table.getUncheckedRow(rowIndex), columnInfo, false, Collections.<String> emptyList());
                    realmObject = new io.realm.com_example_travelin_UserRealmProxy();
                    cache.put(object, (RealmObjectProxy) realmObject);
                } finally {
                    objectContext.clear();
                }
            }
        }

        return (canUpdate) ? update(realm, columnInfo, realmObject, object, cache, flags) : copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.example.travelin.User copy(Realm realm, UserColumnInfo columnInfo, com.example.travelin.User newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.example.travelin.User) cachedRealmObject;
        }

        com_example_travelin_UserRealmProxyInterface realmObjectSource = (com_example_travelin_UserRealmProxyInterface) newObject;

        Table table = realm.getTable(com.example.travelin.User.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, columnInfo.maxColumnIndexValue, flags);

        // Add all non-"object reference" fields
        builder.addString(columnInfo.passwordIndex, realmObjectSource.realmGet$password());
        builder.addString(columnInfo.emailIndex, realmObjectSource.realmGet$email());
        builder.addInteger(columnInfo.ageIndex, realmObjectSource.realmGet$age());
        builder.addString(columnInfo.genderIndex, realmObjectSource.realmGet$gender());
        builder.addInteger(columnInfo.reportCountIndex, realmObjectSource.realmGet$reportCount());
        builder.addString(columnInfo.bioIndex, realmObjectSource.realmGet$bio());
        builder.addBoolean(columnInfo.deletedIndex, realmObjectSource.realmGet$deleted());
        builder.addString(columnInfo.nameIndex, realmObjectSource.realmGet$name());
        builder.addString(columnInfo.usernameIndex, realmObjectSource.realmGet$username());
        builder.addString(columnInfo.profilePictureURLIndex, realmObjectSource.realmGet$profilePictureURL());
        builder.addDouble(columnInfo.avgRatingIndex, realmObjectSource.realmGet$avgRating());
        builder.addByteArray(columnInfo.imgIndex, realmObjectSource.realmGet$img());
        builder.addByteArrayList(columnInfo.profileImagesIndex, realmObjectSource.realmGet$profileImages());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_example_travelin_UserRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        // Finally add all fields that reference other Realm Objects, either directly or through a list
        RealmList<com.example.travelin.MyRating> myRatingsList = realmObjectSource.realmGet$myRatings();
        if (myRatingsList != null) {
            RealmList<com.example.travelin.MyRating> myRatingsRealmList = realmObjectCopy.realmGet$myRatings();
            myRatingsRealmList.clear();
            for (int i = 0; i < myRatingsList.size(); i++) {
                com.example.travelin.MyRating myRatingsItem = myRatingsList.get(i);
                com.example.travelin.MyRating cachemyRatings = (com.example.travelin.MyRating) cache.get(myRatingsItem);
                if (cachemyRatings != null) {
                    myRatingsRealmList.add(cachemyRatings);
                } else {
                    myRatingsRealmList.add(com_example_travelin_MyRatingRealmProxy.copyOrUpdate(realm, (com_example_travelin_MyRatingRealmProxy.MyRatingColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.MyRating.class), myRatingsItem, update, cache, flags));
                }
            }
        }

        RealmList<com.example.travelin.MyRating> reviewsList = realmObjectSource.realmGet$reviews();
        if (reviewsList != null) {
            RealmList<com.example.travelin.MyRating> reviewsRealmList = realmObjectCopy.realmGet$reviews();
            reviewsRealmList.clear();
            for (int i = 0; i < reviewsList.size(); i++) {
                com.example.travelin.MyRating reviewsItem = reviewsList.get(i);
                com.example.travelin.MyRating cachereviews = (com.example.travelin.MyRating) cache.get(reviewsItem);
                if (cachereviews != null) {
                    reviewsRealmList.add(cachereviews);
                } else {
                    reviewsRealmList.add(com_example_travelin_MyRatingRealmProxy.copyOrUpdate(realm, (com_example_travelin_MyRatingRealmProxy.MyRatingColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.MyRating.class), reviewsItem, update, cache, flags));
                }
            }
        }

        RealmList<com.example.travelin.Tag> interestsList = realmObjectSource.realmGet$interests();
        if (interestsList != null) {
            RealmList<com.example.travelin.Tag> interestsRealmList = realmObjectCopy.realmGet$interests();
            interestsRealmList.clear();
            for (int i = 0; i < interestsList.size(); i++) {
                com.example.travelin.Tag interestsItem = interestsList.get(i);
                com.example.travelin.Tag cacheinterests = (com.example.travelin.Tag) cache.get(interestsItem);
                if (cacheinterests != null) {
                    interestsRealmList.add(cacheinterests);
                } else {
                    interestsRealmList.add(com_example_travelin_TagRealmProxy.copyOrUpdate(realm, (com_example_travelin_TagRealmProxy.TagColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.Tag.class), interestsItem, update, cache, flags));
                }
            }
        }

        RealmList<com.example.travelin.User> blockedList = realmObjectSource.realmGet$blocked();
        if (blockedList != null) {
            RealmList<com.example.travelin.User> blockedRealmList = realmObjectCopy.realmGet$blocked();
            blockedRealmList.clear();
            for (int i = 0; i < blockedList.size(); i++) {
                com.example.travelin.User blockedItem = blockedList.get(i);
                com.example.travelin.User cacheblocked = (com.example.travelin.User) cache.get(blockedItem);
                if (cacheblocked != null) {
                    blockedRealmList.add(cacheblocked);
                } else {
                    blockedRealmList.add(com_example_travelin_UserRealmProxy.copyOrUpdate(realm, (com_example_travelin_UserRealmProxy.UserColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.User.class), blockedItem, update, cache, flags));
                }
            }
        }

        RealmList<com.example.travelin.DirectMessage> messagesList = realmObjectSource.realmGet$messages();
        if (messagesList != null) {
            RealmList<com.example.travelin.DirectMessage> messagesRealmList = realmObjectCopy.realmGet$messages();
            messagesRealmList.clear();
            for (int i = 0; i < messagesList.size(); i++) {
                com.example.travelin.DirectMessage messagesItem = messagesList.get(i);
                com.example.travelin.DirectMessage cachemessages = (com.example.travelin.DirectMessage) cache.get(messagesItem);
                if (cachemessages != null) {
                    messagesRealmList.add(cachemessages);
                } else {
                    messagesRealmList.add(com_example_travelin_DirectMessageRealmProxy.copyOrUpdate(realm, (com_example_travelin_DirectMessageRealmProxy.DirectMessageColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.DirectMessage.class), messagesItem, update, cache, flags));
                }
            }
        }

        RealmList<com.example.travelin.EventLocation> previousTripsList = realmObjectSource.realmGet$previousTrips();
        if (previousTripsList != null) {
            RealmList<com.example.travelin.EventLocation> previousTripsRealmList = realmObjectCopy.realmGet$previousTrips();
            previousTripsRealmList.clear();
            for (int i = 0; i < previousTripsList.size(); i++) {
                com.example.travelin.EventLocation previousTripsItem = previousTripsList.get(i);
                com.example.travelin.EventLocation cachepreviousTrips = (com.example.travelin.EventLocation) cache.get(previousTripsItem);
                if (cachepreviousTrips != null) {
                    previousTripsRealmList.add(cachepreviousTrips);
                } else {
                    previousTripsRealmList.add(com_example_travelin_EventLocationRealmProxy.copyOrUpdate(realm, (com_example_travelin_EventLocationRealmProxy.EventLocationColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.EventLocation.class), previousTripsItem, update, cache, flags));
                }
            }
        }

        RealmList<com.example.travelin.User> favoritesList = realmObjectSource.realmGet$favorites();
        if (favoritesList != null) {
            RealmList<com.example.travelin.User> favoritesRealmList = realmObjectCopy.realmGet$favorites();
            favoritesRealmList.clear();
            for (int i = 0; i < favoritesList.size(); i++) {
                com.example.travelin.User favoritesItem = favoritesList.get(i);
                com.example.travelin.User cachefavorites = (com.example.travelin.User) cache.get(favoritesItem);
                if (cachefavorites != null) {
                    favoritesRealmList.add(cachefavorites);
                } else {
                    favoritesRealmList.add(com_example_travelin_UserRealmProxy.copyOrUpdate(realm, (com_example_travelin_UserRealmProxy.UserColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.User.class), favoritesItem, update, cache, flags));
                }
            }
        }

        RealmList<com.example.travelin.Post> postsList = realmObjectSource.realmGet$posts();
        if (postsList != null) {
            RealmList<com.example.travelin.Post> postsRealmList = realmObjectCopy.realmGet$posts();
            postsRealmList.clear();
            for (int i = 0; i < postsList.size(); i++) {
                com.example.travelin.Post postsItem = postsList.get(i);
                com.example.travelin.Post cacheposts = (com.example.travelin.Post) cache.get(postsItem);
                if (cacheposts != null) {
                    postsRealmList.add(cacheposts);
                } else {
                    postsRealmList.add(com_example_travelin_PostRealmProxy.copyOrUpdate(realm, (com_example_travelin_PostRealmProxy.PostColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.Post.class), postsItem, update, cache, flags));
                }
            }
        }

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.example.travelin.User object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.example.travelin.User.class);
        long tableNativePtr = table.getNativePtr();
        UserColumnInfo columnInfo = (UserColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.User.class);
        long pkColumnIndex = columnInfo.usernameIndex;
        String primaryKeyValue = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$username();
        long rowIndex = Table.NO_MATCH;
        if (primaryKeyValue == null) {
            rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
        } else {
            rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, primaryKeyValue);
        } else {
            Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$password = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$password();
        if (realmGet$password != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.passwordIndex, rowIndex, realmGet$password, false);
        }
        String realmGet$email = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$email();
        if (realmGet$email != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.emailIndex, rowIndex, realmGet$email, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.ageIndex, rowIndex, ((com_example_travelin_UserRealmProxyInterface) object).realmGet$age(), false);
        String realmGet$gender = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$gender();
        if (realmGet$gender != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.genderIndex, rowIndex, realmGet$gender, false);
        }

        RealmList<com.example.travelin.MyRating> myRatingsList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$myRatings();
        if (myRatingsList != null) {
            OsList myRatingsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.myRatingsIndex);
            for (com.example.travelin.MyRating myRatingsItem : myRatingsList) {
                Long cacheItemIndexmyRatings = cache.get(myRatingsItem);
                if (cacheItemIndexmyRatings == null) {
                    cacheItemIndexmyRatings = com_example_travelin_MyRatingRealmProxy.insert(realm, myRatingsItem, cache);
                }
                myRatingsOsList.addRow(cacheItemIndexmyRatings);
            }
        }

        RealmList<com.example.travelin.MyRating> reviewsList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$reviews();
        if (reviewsList != null) {
            OsList reviewsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.reviewsIndex);
            for (com.example.travelin.MyRating reviewsItem : reviewsList) {
                Long cacheItemIndexreviews = cache.get(reviewsItem);
                if (cacheItemIndexreviews == null) {
                    cacheItemIndexreviews = com_example_travelin_MyRatingRealmProxy.insert(realm, reviewsItem, cache);
                }
                reviewsOsList.addRow(cacheItemIndexreviews);
            }
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.reportCountIndex, rowIndex, ((com_example_travelin_UserRealmProxyInterface) object).realmGet$reportCount(), false);
        String realmGet$bio = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$bio();
        if (realmGet$bio != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.bioIndex, rowIndex, realmGet$bio, false);
        }

        RealmList<com.example.travelin.Tag> interestsList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$interests();
        if (interestsList != null) {
            OsList interestsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.interestsIndex);
            for (com.example.travelin.Tag interestsItem : interestsList) {
                Long cacheItemIndexinterests = cache.get(interestsItem);
                if (cacheItemIndexinterests == null) {
                    cacheItemIndexinterests = com_example_travelin_TagRealmProxy.insert(realm, interestsItem, cache);
                }
                interestsOsList.addRow(cacheItemIndexinterests);
            }
        }

        RealmList<com.example.travelin.User> blockedList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$blocked();
        if (blockedList != null) {
            OsList blockedOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.blockedIndex);
            for (com.example.travelin.User blockedItem : blockedList) {
                Long cacheItemIndexblocked = cache.get(blockedItem);
                if (cacheItemIndexblocked == null) {
                    cacheItemIndexblocked = com_example_travelin_UserRealmProxy.insert(realm, blockedItem, cache);
                }
                blockedOsList.addRow(cacheItemIndexblocked);
            }
        }
        Table.nativeSetBoolean(tableNativePtr, columnInfo.deletedIndex, rowIndex, ((com_example_travelin_UserRealmProxyInterface) object).realmGet$deleted(), false);
        String realmGet$name = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$name();
        if (realmGet$name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
        }

        RealmList<com.example.travelin.DirectMessage> messagesList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$messages();
        if (messagesList != null) {
            OsList messagesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.messagesIndex);
            for (com.example.travelin.DirectMessage messagesItem : messagesList) {
                Long cacheItemIndexmessages = cache.get(messagesItem);
                if (cacheItemIndexmessages == null) {
                    cacheItemIndexmessages = com_example_travelin_DirectMessageRealmProxy.insert(realm, messagesItem, cache);
                }
                messagesOsList.addRow(cacheItemIndexmessages);
            }
        }
        String realmGet$profilePictureURL = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$profilePictureURL();
        if (realmGet$profilePictureURL != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.profilePictureURLIndex, rowIndex, realmGet$profilePictureURL, false);
        }

        RealmList<com.example.travelin.EventLocation> previousTripsList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$previousTrips();
        if (previousTripsList != null) {
            OsList previousTripsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.previousTripsIndex);
            for (com.example.travelin.EventLocation previousTripsItem : previousTripsList) {
                Long cacheItemIndexpreviousTrips = cache.get(previousTripsItem);
                if (cacheItemIndexpreviousTrips == null) {
                    cacheItemIndexpreviousTrips = com_example_travelin_EventLocationRealmProxy.insert(realm, previousTripsItem, cache);
                }
                previousTripsOsList.addRow(cacheItemIndexpreviousTrips);
            }
        }

        RealmList<com.example.travelin.User> favoritesList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$favorites();
        if (favoritesList != null) {
            OsList favoritesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.favoritesIndex);
            for (com.example.travelin.User favoritesItem : favoritesList) {
                Long cacheItemIndexfavorites = cache.get(favoritesItem);
                if (cacheItemIndexfavorites == null) {
                    cacheItemIndexfavorites = com_example_travelin_UserRealmProxy.insert(realm, favoritesItem, cache);
                }
                favoritesOsList.addRow(cacheItemIndexfavorites);
            }
        }

        RealmList<com.example.travelin.Post> postsList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$posts();
        if (postsList != null) {
            OsList postsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.postsIndex);
            for (com.example.travelin.Post postsItem : postsList) {
                Long cacheItemIndexposts = cache.get(postsItem);
                if (cacheItemIndexposts == null) {
                    cacheItemIndexposts = com_example_travelin_PostRealmProxy.insert(realm, postsItem, cache);
                }
                postsOsList.addRow(cacheItemIndexposts);
            }
        }
        Table.nativeSetDouble(tableNativePtr, columnInfo.avgRatingIndex, rowIndex, ((com_example_travelin_UserRealmProxyInterface) object).realmGet$avgRating(), false);
        byte[] realmGet$img = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$img();
        if (realmGet$img != null) {
            Table.nativeSetByteArray(tableNativePtr, columnInfo.imgIndex, rowIndex, realmGet$img, false);
        }

        RealmList<byte[]> profileImagesList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$profileImages();
        if (profileImagesList != null) {
            OsList profileImagesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.profileImagesIndex);
            for (byte[] profileImagesItem : profileImagesList) {
                if (profileImagesItem == null) {
                    profileImagesOsList.addNull();
                } else {
                    profileImagesOsList.addBinary(profileImagesItem);
                }
            }
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.travelin.User.class);
        long tableNativePtr = table.getNativePtr();
        UserColumnInfo columnInfo = (UserColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.User.class);
        long pkColumnIndex = columnInfo.usernameIndex;
        com.example.travelin.User object = null;
        while (objects.hasNext()) {
            object = (com.example.travelin.User) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            String primaryKeyValue = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$username();
            long rowIndex = Table.NO_MATCH;
            if (primaryKeyValue == null) {
                rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
            } else {
                rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, primaryKeyValue);
            } else {
                Table.throwDuplicatePrimaryKeyException(primaryKeyValue);
            }
            cache.put(object, rowIndex);
            String realmGet$password = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$password();
            if (realmGet$password != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.passwordIndex, rowIndex, realmGet$password, false);
            }
            String realmGet$email = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$email();
            if (realmGet$email != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.emailIndex, rowIndex, realmGet$email, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.ageIndex, rowIndex, ((com_example_travelin_UserRealmProxyInterface) object).realmGet$age(), false);
            String realmGet$gender = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$gender();
            if (realmGet$gender != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.genderIndex, rowIndex, realmGet$gender, false);
            }

            RealmList<com.example.travelin.MyRating> myRatingsList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$myRatings();
            if (myRatingsList != null) {
                OsList myRatingsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.myRatingsIndex);
                for (com.example.travelin.MyRating myRatingsItem : myRatingsList) {
                    Long cacheItemIndexmyRatings = cache.get(myRatingsItem);
                    if (cacheItemIndexmyRatings == null) {
                        cacheItemIndexmyRatings = com_example_travelin_MyRatingRealmProxy.insert(realm, myRatingsItem, cache);
                    }
                    myRatingsOsList.addRow(cacheItemIndexmyRatings);
                }
            }

            RealmList<com.example.travelin.MyRating> reviewsList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$reviews();
            if (reviewsList != null) {
                OsList reviewsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.reviewsIndex);
                for (com.example.travelin.MyRating reviewsItem : reviewsList) {
                    Long cacheItemIndexreviews = cache.get(reviewsItem);
                    if (cacheItemIndexreviews == null) {
                        cacheItemIndexreviews = com_example_travelin_MyRatingRealmProxy.insert(realm, reviewsItem, cache);
                    }
                    reviewsOsList.addRow(cacheItemIndexreviews);
                }
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.reportCountIndex, rowIndex, ((com_example_travelin_UserRealmProxyInterface) object).realmGet$reportCount(), false);
            String realmGet$bio = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$bio();
            if (realmGet$bio != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.bioIndex, rowIndex, realmGet$bio, false);
            }

            RealmList<com.example.travelin.Tag> interestsList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$interests();
            if (interestsList != null) {
                OsList interestsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.interestsIndex);
                for (com.example.travelin.Tag interestsItem : interestsList) {
                    Long cacheItemIndexinterests = cache.get(interestsItem);
                    if (cacheItemIndexinterests == null) {
                        cacheItemIndexinterests = com_example_travelin_TagRealmProxy.insert(realm, interestsItem, cache);
                    }
                    interestsOsList.addRow(cacheItemIndexinterests);
                }
            }

            RealmList<com.example.travelin.User> blockedList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$blocked();
            if (blockedList != null) {
                OsList blockedOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.blockedIndex);
                for (com.example.travelin.User blockedItem : blockedList) {
                    Long cacheItemIndexblocked = cache.get(blockedItem);
                    if (cacheItemIndexblocked == null) {
                        cacheItemIndexblocked = com_example_travelin_UserRealmProxy.insert(realm, blockedItem, cache);
                    }
                    blockedOsList.addRow(cacheItemIndexblocked);
                }
            }
            Table.nativeSetBoolean(tableNativePtr, columnInfo.deletedIndex, rowIndex, ((com_example_travelin_UserRealmProxyInterface) object).realmGet$deleted(), false);
            String realmGet$name = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$name();
            if (realmGet$name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
            }

            RealmList<com.example.travelin.DirectMessage> messagesList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$messages();
            if (messagesList != null) {
                OsList messagesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.messagesIndex);
                for (com.example.travelin.DirectMessage messagesItem : messagesList) {
                    Long cacheItemIndexmessages = cache.get(messagesItem);
                    if (cacheItemIndexmessages == null) {
                        cacheItemIndexmessages = com_example_travelin_DirectMessageRealmProxy.insert(realm, messagesItem, cache);
                    }
                    messagesOsList.addRow(cacheItemIndexmessages);
                }
            }
            String realmGet$profilePictureURL = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$profilePictureURL();
            if (realmGet$profilePictureURL != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.profilePictureURLIndex, rowIndex, realmGet$profilePictureURL, false);
            }

            RealmList<com.example.travelin.EventLocation> previousTripsList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$previousTrips();
            if (previousTripsList != null) {
                OsList previousTripsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.previousTripsIndex);
                for (com.example.travelin.EventLocation previousTripsItem : previousTripsList) {
                    Long cacheItemIndexpreviousTrips = cache.get(previousTripsItem);
                    if (cacheItemIndexpreviousTrips == null) {
                        cacheItemIndexpreviousTrips = com_example_travelin_EventLocationRealmProxy.insert(realm, previousTripsItem, cache);
                    }
                    previousTripsOsList.addRow(cacheItemIndexpreviousTrips);
                }
            }

            RealmList<com.example.travelin.User> favoritesList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$favorites();
            if (favoritesList != null) {
                OsList favoritesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.favoritesIndex);
                for (com.example.travelin.User favoritesItem : favoritesList) {
                    Long cacheItemIndexfavorites = cache.get(favoritesItem);
                    if (cacheItemIndexfavorites == null) {
                        cacheItemIndexfavorites = com_example_travelin_UserRealmProxy.insert(realm, favoritesItem, cache);
                    }
                    favoritesOsList.addRow(cacheItemIndexfavorites);
                }
            }

            RealmList<com.example.travelin.Post> postsList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$posts();
            if (postsList != null) {
                OsList postsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.postsIndex);
                for (com.example.travelin.Post postsItem : postsList) {
                    Long cacheItemIndexposts = cache.get(postsItem);
                    if (cacheItemIndexposts == null) {
                        cacheItemIndexposts = com_example_travelin_PostRealmProxy.insert(realm, postsItem, cache);
                    }
                    postsOsList.addRow(cacheItemIndexposts);
                }
            }
            Table.nativeSetDouble(tableNativePtr, columnInfo.avgRatingIndex, rowIndex, ((com_example_travelin_UserRealmProxyInterface) object).realmGet$avgRating(), false);
            byte[] realmGet$img = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$img();
            if (realmGet$img != null) {
                Table.nativeSetByteArray(tableNativePtr, columnInfo.imgIndex, rowIndex, realmGet$img, false);
            }

            RealmList<byte[]> profileImagesList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$profileImages();
            if (profileImagesList != null) {
                OsList profileImagesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.profileImagesIndex);
                for (byte[] profileImagesItem : profileImagesList) {
                    if (profileImagesItem == null) {
                        profileImagesOsList.addNull();
                    } else {
                        profileImagesOsList.addBinary(profileImagesItem);
                    }
                }
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.example.travelin.User object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.example.travelin.User.class);
        long tableNativePtr = table.getNativePtr();
        UserColumnInfo columnInfo = (UserColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.User.class);
        long pkColumnIndex = columnInfo.usernameIndex;
        String primaryKeyValue = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$username();
        long rowIndex = Table.NO_MATCH;
        if (primaryKeyValue == null) {
            rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
        } else {
            rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
        }
        if (rowIndex == Table.NO_MATCH) {
            rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, primaryKeyValue);
        }
        cache.put(object, rowIndex);
        String realmGet$password = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$password();
        if (realmGet$password != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.passwordIndex, rowIndex, realmGet$password, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.passwordIndex, rowIndex, false);
        }
        String realmGet$email = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$email();
        if (realmGet$email != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.emailIndex, rowIndex, realmGet$email, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.emailIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.ageIndex, rowIndex, ((com_example_travelin_UserRealmProxyInterface) object).realmGet$age(), false);
        String realmGet$gender = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$gender();
        if (realmGet$gender != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.genderIndex, rowIndex, realmGet$gender, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.genderIndex, rowIndex, false);
        }

        OsList myRatingsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.myRatingsIndex);
        RealmList<com.example.travelin.MyRating> myRatingsList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$myRatings();
        if (myRatingsList != null && myRatingsList.size() == myRatingsOsList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = myRatingsList.size();
            for (int i = 0; i < objects; i++) {
                com.example.travelin.MyRating myRatingsItem = myRatingsList.get(i);
                Long cacheItemIndexmyRatings = cache.get(myRatingsItem);
                if (cacheItemIndexmyRatings == null) {
                    cacheItemIndexmyRatings = com_example_travelin_MyRatingRealmProxy.insertOrUpdate(realm, myRatingsItem, cache);
                }
                myRatingsOsList.setRow(i, cacheItemIndexmyRatings);
            }
        } else {
            myRatingsOsList.removeAll();
            if (myRatingsList != null) {
                for (com.example.travelin.MyRating myRatingsItem : myRatingsList) {
                    Long cacheItemIndexmyRatings = cache.get(myRatingsItem);
                    if (cacheItemIndexmyRatings == null) {
                        cacheItemIndexmyRatings = com_example_travelin_MyRatingRealmProxy.insertOrUpdate(realm, myRatingsItem, cache);
                    }
                    myRatingsOsList.addRow(cacheItemIndexmyRatings);
                }
            }
        }


        OsList reviewsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.reviewsIndex);
        RealmList<com.example.travelin.MyRating> reviewsList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$reviews();
        if (reviewsList != null && reviewsList.size() == reviewsOsList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = reviewsList.size();
            for (int i = 0; i < objects; i++) {
                com.example.travelin.MyRating reviewsItem = reviewsList.get(i);
                Long cacheItemIndexreviews = cache.get(reviewsItem);
                if (cacheItemIndexreviews == null) {
                    cacheItemIndexreviews = com_example_travelin_MyRatingRealmProxy.insertOrUpdate(realm, reviewsItem, cache);
                }
                reviewsOsList.setRow(i, cacheItemIndexreviews);
            }
        } else {
            reviewsOsList.removeAll();
            if (reviewsList != null) {
                for (com.example.travelin.MyRating reviewsItem : reviewsList) {
                    Long cacheItemIndexreviews = cache.get(reviewsItem);
                    if (cacheItemIndexreviews == null) {
                        cacheItemIndexreviews = com_example_travelin_MyRatingRealmProxy.insertOrUpdate(realm, reviewsItem, cache);
                    }
                    reviewsOsList.addRow(cacheItemIndexreviews);
                }
            }
        }

        Table.nativeSetLong(tableNativePtr, columnInfo.reportCountIndex, rowIndex, ((com_example_travelin_UserRealmProxyInterface) object).realmGet$reportCount(), false);
        String realmGet$bio = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$bio();
        if (realmGet$bio != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.bioIndex, rowIndex, realmGet$bio, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.bioIndex, rowIndex, false);
        }

        OsList interestsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.interestsIndex);
        RealmList<com.example.travelin.Tag> interestsList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$interests();
        if (interestsList != null && interestsList.size() == interestsOsList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = interestsList.size();
            for (int i = 0; i < objects; i++) {
                com.example.travelin.Tag interestsItem = interestsList.get(i);
                Long cacheItemIndexinterests = cache.get(interestsItem);
                if (cacheItemIndexinterests == null) {
                    cacheItemIndexinterests = com_example_travelin_TagRealmProxy.insertOrUpdate(realm, interestsItem, cache);
                }
                interestsOsList.setRow(i, cacheItemIndexinterests);
            }
        } else {
            interestsOsList.removeAll();
            if (interestsList != null) {
                for (com.example.travelin.Tag interestsItem : interestsList) {
                    Long cacheItemIndexinterests = cache.get(interestsItem);
                    if (cacheItemIndexinterests == null) {
                        cacheItemIndexinterests = com_example_travelin_TagRealmProxy.insertOrUpdate(realm, interestsItem, cache);
                    }
                    interestsOsList.addRow(cacheItemIndexinterests);
                }
            }
        }


        OsList blockedOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.blockedIndex);
        RealmList<com.example.travelin.User> blockedList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$blocked();
        if (blockedList != null && blockedList.size() == blockedOsList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = blockedList.size();
            for (int i = 0; i < objects; i++) {
                com.example.travelin.User blockedItem = blockedList.get(i);
                Long cacheItemIndexblocked = cache.get(blockedItem);
                if (cacheItemIndexblocked == null) {
                    cacheItemIndexblocked = com_example_travelin_UserRealmProxy.insertOrUpdate(realm, blockedItem, cache);
                }
                blockedOsList.setRow(i, cacheItemIndexblocked);
            }
        } else {
            blockedOsList.removeAll();
            if (blockedList != null) {
                for (com.example.travelin.User blockedItem : blockedList) {
                    Long cacheItemIndexblocked = cache.get(blockedItem);
                    if (cacheItemIndexblocked == null) {
                        cacheItemIndexblocked = com_example_travelin_UserRealmProxy.insertOrUpdate(realm, blockedItem, cache);
                    }
                    blockedOsList.addRow(cacheItemIndexblocked);
                }
            }
        }

        Table.nativeSetBoolean(tableNativePtr, columnInfo.deletedIndex, rowIndex, ((com_example_travelin_UserRealmProxyInterface) object).realmGet$deleted(), false);
        String realmGet$name = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$name();
        if (realmGet$name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.nameIndex, rowIndex, false);
        }

        OsList messagesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.messagesIndex);
        RealmList<com.example.travelin.DirectMessage> messagesList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$messages();
        if (messagesList != null && messagesList.size() == messagesOsList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = messagesList.size();
            for (int i = 0; i < objects; i++) {
                com.example.travelin.DirectMessage messagesItem = messagesList.get(i);
                Long cacheItemIndexmessages = cache.get(messagesItem);
                if (cacheItemIndexmessages == null) {
                    cacheItemIndexmessages = com_example_travelin_DirectMessageRealmProxy.insertOrUpdate(realm, messagesItem, cache);
                }
                messagesOsList.setRow(i, cacheItemIndexmessages);
            }
        } else {
            messagesOsList.removeAll();
            if (messagesList != null) {
                for (com.example.travelin.DirectMessage messagesItem : messagesList) {
                    Long cacheItemIndexmessages = cache.get(messagesItem);
                    if (cacheItemIndexmessages == null) {
                        cacheItemIndexmessages = com_example_travelin_DirectMessageRealmProxy.insertOrUpdate(realm, messagesItem, cache);
                    }
                    messagesOsList.addRow(cacheItemIndexmessages);
                }
            }
        }

        String realmGet$profilePictureURL = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$profilePictureURL();
        if (realmGet$profilePictureURL != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.profilePictureURLIndex, rowIndex, realmGet$profilePictureURL, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.profilePictureURLIndex, rowIndex, false);
        }

        OsList previousTripsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.previousTripsIndex);
        RealmList<com.example.travelin.EventLocation> previousTripsList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$previousTrips();
        if (previousTripsList != null && previousTripsList.size() == previousTripsOsList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = previousTripsList.size();
            for (int i = 0; i < objects; i++) {
                com.example.travelin.EventLocation previousTripsItem = previousTripsList.get(i);
                Long cacheItemIndexpreviousTrips = cache.get(previousTripsItem);
                if (cacheItemIndexpreviousTrips == null) {
                    cacheItemIndexpreviousTrips = com_example_travelin_EventLocationRealmProxy.insertOrUpdate(realm, previousTripsItem, cache);
                }
                previousTripsOsList.setRow(i, cacheItemIndexpreviousTrips);
            }
        } else {
            previousTripsOsList.removeAll();
            if (previousTripsList != null) {
                for (com.example.travelin.EventLocation previousTripsItem : previousTripsList) {
                    Long cacheItemIndexpreviousTrips = cache.get(previousTripsItem);
                    if (cacheItemIndexpreviousTrips == null) {
                        cacheItemIndexpreviousTrips = com_example_travelin_EventLocationRealmProxy.insertOrUpdate(realm, previousTripsItem, cache);
                    }
                    previousTripsOsList.addRow(cacheItemIndexpreviousTrips);
                }
            }
        }


        OsList favoritesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.favoritesIndex);
        RealmList<com.example.travelin.User> favoritesList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$favorites();
        if (favoritesList != null && favoritesList.size() == favoritesOsList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = favoritesList.size();
            for (int i = 0; i < objects; i++) {
                com.example.travelin.User favoritesItem = favoritesList.get(i);
                Long cacheItemIndexfavorites = cache.get(favoritesItem);
                if (cacheItemIndexfavorites == null) {
                    cacheItemIndexfavorites = com_example_travelin_UserRealmProxy.insertOrUpdate(realm, favoritesItem, cache);
                }
                favoritesOsList.setRow(i, cacheItemIndexfavorites);
            }
        } else {
            favoritesOsList.removeAll();
            if (favoritesList != null) {
                for (com.example.travelin.User favoritesItem : favoritesList) {
                    Long cacheItemIndexfavorites = cache.get(favoritesItem);
                    if (cacheItemIndexfavorites == null) {
                        cacheItemIndexfavorites = com_example_travelin_UserRealmProxy.insertOrUpdate(realm, favoritesItem, cache);
                    }
                    favoritesOsList.addRow(cacheItemIndexfavorites);
                }
            }
        }


        OsList postsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.postsIndex);
        RealmList<com.example.travelin.Post> postsList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$posts();
        if (postsList != null && postsList.size() == postsOsList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = postsList.size();
            for (int i = 0; i < objects; i++) {
                com.example.travelin.Post postsItem = postsList.get(i);
                Long cacheItemIndexposts = cache.get(postsItem);
                if (cacheItemIndexposts == null) {
                    cacheItemIndexposts = com_example_travelin_PostRealmProxy.insertOrUpdate(realm, postsItem, cache);
                }
                postsOsList.setRow(i, cacheItemIndexposts);
            }
        } else {
            postsOsList.removeAll();
            if (postsList != null) {
                for (com.example.travelin.Post postsItem : postsList) {
                    Long cacheItemIndexposts = cache.get(postsItem);
                    if (cacheItemIndexposts == null) {
                        cacheItemIndexposts = com_example_travelin_PostRealmProxy.insertOrUpdate(realm, postsItem, cache);
                    }
                    postsOsList.addRow(cacheItemIndexposts);
                }
            }
        }

        Table.nativeSetDouble(tableNativePtr, columnInfo.avgRatingIndex, rowIndex, ((com_example_travelin_UserRealmProxyInterface) object).realmGet$avgRating(), false);
        byte[] realmGet$img = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$img();
        if (realmGet$img != null) {
            Table.nativeSetByteArray(tableNativePtr, columnInfo.imgIndex, rowIndex, realmGet$img, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.imgIndex, rowIndex, false);
        }

        OsList profileImagesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.profileImagesIndex);
        profileImagesOsList.removeAll();
        RealmList<byte[]> profileImagesList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$profileImages();
        if (profileImagesList != null) {
            for (byte[] profileImagesItem : profileImagesList) {
                if (profileImagesItem == null) {
                    profileImagesOsList.addNull();
                } else {
                    profileImagesOsList.addBinary(profileImagesItem);
                }
            }
        }

        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.travelin.User.class);
        long tableNativePtr = table.getNativePtr();
        UserColumnInfo columnInfo = (UserColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.User.class);
        long pkColumnIndex = columnInfo.usernameIndex;
        com.example.travelin.User object = null;
        while (objects.hasNext()) {
            object = (com.example.travelin.User) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            String primaryKeyValue = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$username();
            long rowIndex = Table.NO_MATCH;
            if (primaryKeyValue == null) {
                rowIndex = Table.nativeFindFirstNull(tableNativePtr, pkColumnIndex);
            } else {
                rowIndex = Table.nativeFindFirstString(tableNativePtr, pkColumnIndex, primaryKeyValue);
            }
            if (rowIndex == Table.NO_MATCH) {
                rowIndex = OsObject.createRowWithPrimaryKey(table, pkColumnIndex, primaryKeyValue);
            }
            cache.put(object, rowIndex);
            String realmGet$password = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$password();
            if (realmGet$password != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.passwordIndex, rowIndex, realmGet$password, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.passwordIndex, rowIndex, false);
            }
            String realmGet$email = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$email();
            if (realmGet$email != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.emailIndex, rowIndex, realmGet$email, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.emailIndex, rowIndex, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.ageIndex, rowIndex, ((com_example_travelin_UserRealmProxyInterface) object).realmGet$age(), false);
            String realmGet$gender = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$gender();
            if (realmGet$gender != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.genderIndex, rowIndex, realmGet$gender, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.genderIndex, rowIndex, false);
            }

            OsList myRatingsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.myRatingsIndex);
            RealmList<com.example.travelin.MyRating> myRatingsList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$myRatings();
            if (myRatingsList != null && myRatingsList.size() == myRatingsOsList.size()) {
                // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
                int objectCount = myRatingsList.size();
                for (int i = 0; i < objectCount; i++) {
                    com.example.travelin.MyRating myRatingsItem = myRatingsList.get(i);
                    Long cacheItemIndexmyRatings = cache.get(myRatingsItem);
                    if (cacheItemIndexmyRatings == null) {
                        cacheItemIndexmyRatings = com_example_travelin_MyRatingRealmProxy.insertOrUpdate(realm, myRatingsItem, cache);
                    }
                    myRatingsOsList.setRow(i, cacheItemIndexmyRatings);
                }
            } else {
                myRatingsOsList.removeAll();
                if (myRatingsList != null) {
                    for (com.example.travelin.MyRating myRatingsItem : myRatingsList) {
                        Long cacheItemIndexmyRatings = cache.get(myRatingsItem);
                        if (cacheItemIndexmyRatings == null) {
                            cacheItemIndexmyRatings = com_example_travelin_MyRatingRealmProxy.insertOrUpdate(realm, myRatingsItem, cache);
                        }
                        myRatingsOsList.addRow(cacheItemIndexmyRatings);
                    }
                }
            }


            OsList reviewsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.reviewsIndex);
            RealmList<com.example.travelin.MyRating> reviewsList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$reviews();
            if (reviewsList != null && reviewsList.size() == reviewsOsList.size()) {
                // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
                int objectCount = reviewsList.size();
                for (int i = 0; i < objectCount; i++) {
                    com.example.travelin.MyRating reviewsItem = reviewsList.get(i);
                    Long cacheItemIndexreviews = cache.get(reviewsItem);
                    if (cacheItemIndexreviews == null) {
                        cacheItemIndexreviews = com_example_travelin_MyRatingRealmProxy.insertOrUpdate(realm, reviewsItem, cache);
                    }
                    reviewsOsList.setRow(i, cacheItemIndexreviews);
                }
            } else {
                reviewsOsList.removeAll();
                if (reviewsList != null) {
                    for (com.example.travelin.MyRating reviewsItem : reviewsList) {
                        Long cacheItemIndexreviews = cache.get(reviewsItem);
                        if (cacheItemIndexreviews == null) {
                            cacheItemIndexreviews = com_example_travelin_MyRatingRealmProxy.insertOrUpdate(realm, reviewsItem, cache);
                        }
                        reviewsOsList.addRow(cacheItemIndexreviews);
                    }
                }
            }

            Table.nativeSetLong(tableNativePtr, columnInfo.reportCountIndex, rowIndex, ((com_example_travelin_UserRealmProxyInterface) object).realmGet$reportCount(), false);
            String realmGet$bio = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$bio();
            if (realmGet$bio != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.bioIndex, rowIndex, realmGet$bio, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.bioIndex, rowIndex, false);
            }

            OsList interestsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.interestsIndex);
            RealmList<com.example.travelin.Tag> interestsList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$interests();
            if (interestsList != null && interestsList.size() == interestsOsList.size()) {
                // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
                int objectCount = interestsList.size();
                for (int i = 0; i < objectCount; i++) {
                    com.example.travelin.Tag interestsItem = interestsList.get(i);
                    Long cacheItemIndexinterests = cache.get(interestsItem);
                    if (cacheItemIndexinterests == null) {
                        cacheItemIndexinterests = com_example_travelin_TagRealmProxy.insertOrUpdate(realm, interestsItem, cache);
                    }
                    interestsOsList.setRow(i, cacheItemIndexinterests);
                }
            } else {
                interestsOsList.removeAll();
                if (interestsList != null) {
                    for (com.example.travelin.Tag interestsItem : interestsList) {
                        Long cacheItemIndexinterests = cache.get(interestsItem);
                        if (cacheItemIndexinterests == null) {
                            cacheItemIndexinterests = com_example_travelin_TagRealmProxy.insertOrUpdate(realm, interestsItem, cache);
                        }
                        interestsOsList.addRow(cacheItemIndexinterests);
                    }
                }
            }


            OsList blockedOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.blockedIndex);
            RealmList<com.example.travelin.User> blockedList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$blocked();
            if (blockedList != null && blockedList.size() == blockedOsList.size()) {
                // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
                int objectCount = blockedList.size();
                for (int i = 0; i < objectCount; i++) {
                    com.example.travelin.User blockedItem = blockedList.get(i);
                    Long cacheItemIndexblocked = cache.get(blockedItem);
                    if (cacheItemIndexblocked == null) {
                        cacheItemIndexblocked = com_example_travelin_UserRealmProxy.insertOrUpdate(realm, blockedItem, cache);
                    }
                    blockedOsList.setRow(i, cacheItemIndexblocked);
                }
            } else {
                blockedOsList.removeAll();
                if (blockedList != null) {
                    for (com.example.travelin.User blockedItem : blockedList) {
                        Long cacheItemIndexblocked = cache.get(blockedItem);
                        if (cacheItemIndexblocked == null) {
                            cacheItemIndexblocked = com_example_travelin_UserRealmProxy.insertOrUpdate(realm, blockedItem, cache);
                        }
                        blockedOsList.addRow(cacheItemIndexblocked);
                    }
                }
            }

            Table.nativeSetBoolean(tableNativePtr, columnInfo.deletedIndex, rowIndex, ((com_example_travelin_UserRealmProxyInterface) object).realmGet$deleted(), false);
            String realmGet$name = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$name();
            if (realmGet$name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.nameIndex, rowIndex, false);
            }

            OsList messagesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.messagesIndex);
            RealmList<com.example.travelin.DirectMessage> messagesList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$messages();
            if (messagesList != null && messagesList.size() == messagesOsList.size()) {
                // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
                int objectCount = messagesList.size();
                for (int i = 0; i < objectCount; i++) {
                    com.example.travelin.DirectMessage messagesItem = messagesList.get(i);
                    Long cacheItemIndexmessages = cache.get(messagesItem);
                    if (cacheItemIndexmessages == null) {
                        cacheItemIndexmessages = com_example_travelin_DirectMessageRealmProxy.insertOrUpdate(realm, messagesItem, cache);
                    }
                    messagesOsList.setRow(i, cacheItemIndexmessages);
                }
            } else {
                messagesOsList.removeAll();
                if (messagesList != null) {
                    for (com.example.travelin.DirectMessage messagesItem : messagesList) {
                        Long cacheItemIndexmessages = cache.get(messagesItem);
                        if (cacheItemIndexmessages == null) {
                            cacheItemIndexmessages = com_example_travelin_DirectMessageRealmProxy.insertOrUpdate(realm, messagesItem, cache);
                        }
                        messagesOsList.addRow(cacheItemIndexmessages);
                    }
                }
            }

            String realmGet$profilePictureURL = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$profilePictureURL();
            if (realmGet$profilePictureURL != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.profilePictureURLIndex, rowIndex, realmGet$profilePictureURL, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.profilePictureURLIndex, rowIndex, false);
            }

            OsList previousTripsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.previousTripsIndex);
            RealmList<com.example.travelin.EventLocation> previousTripsList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$previousTrips();
            if (previousTripsList != null && previousTripsList.size() == previousTripsOsList.size()) {
                // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
                int objectCount = previousTripsList.size();
                for (int i = 0; i < objectCount; i++) {
                    com.example.travelin.EventLocation previousTripsItem = previousTripsList.get(i);
                    Long cacheItemIndexpreviousTrips = cache.get(previousTripsItem);
                    if (cacheItemIndexpreviousTrips == null) {
                        cacheItemIndexpreviousTrips = com_example_travelin_EventLocationRealmProxy.insertOrUpdate(realm, previousTripsItem, cache);
                    }
                    previousTripsOsList.setRow(i, cacheItemIndexpreviousTrips);
                }
            } else {
                previousTripsOsList.removeAll();
                if (previousTripsList != null) {
                    for (com.example.travelin.EventLocation previousTripsItem : previousTripsList) {
                        Long cacheItemIndexpreviousTrips = cache.get(previousTripsItem);
                        if (cacheItemIndexpreviousTrips == null) {
                            cacheItemIndexpreviousTrips = com_example_travelin_EventLocationRealmProxy.insertOrUpdate(realm, previousTripsItem, cache);
                        }
                        previousTripsOsList.addRow(cacheItemIndexpreviousTrips);
                    }
                }
            }


            OsList favoritesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.favoritesIndex);
            RealmList<com.example.travelin.User> favoritesList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$favorites();
            if (favoritesList != null && favoritesList.size() == favoritesOsList.size()) {
                // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
                int objectCount = favoritesList.size();
                for (int i = 0; i < objectCount; i++) {
                    com.example.travelin.User favoritesItem = favoritesList.get(i);
                    Long cacheItemIndexfavorites = cache.get(favoritesItem);
                    if (cacheItemIndexfavorites == null) {
                        cacheItemIndexfavorites = com_example_travelin_UserRealmProxy.insertOrUpdate(realm, favoritesItem, cache);
                    }
                    favoritesOsList.setRow(i, cacheItemIndexfavorites);
                }
            } else {
                favoritesOsList.removeAll();
                if (favoritesList != null) {
                    for (com.example.travelin.User favoritesItem : favoritesList) {
                        Long cacheItemIndexfavorites = cache.get(favoritesItem);
                        if (cacheItemIndexfavorites == null) {
                            cacheItemIndexfavorites = com_example_travelin_UserRealmProxy.insertOrUpdate(realm, favoritesItem, cache);
                        }
                        favoritesOsList.addRow(cacheItemIndexfavorites);
                    }
                }
            }


            OsList postsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.postsIndex);
            RealmList<com.example.travelin.Post> postsList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$posts();
            if (postsList != null && postsList.size() == postsOsList.size()) {
                // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
                int objectCount = postsList.size();
                for (int i = 0; i < objectCount; i++) {
                    com.example.travelin.Post postsItem = postsList.get(i);
                    Long cacheItemIndexposts = cache.get(postsItem);
                    if (cacheItemIndexposts == null) {
                        cacheItemIndexposts = com_example_travelin_PostRealmProxy.insertOrUpdate(realm, postsItem, cache);
                    }
                    postsOsList.setRow(i, cacheItemIndexposts);
                }
            } else {
                postsOsList.removeAll();
                if (postsList != null) {
                    for (com.example.travelin.Post postsItem : postsList) {
                        Long cacheItemIndexposts = cache.get(postsItem);
                        if (cacheItemIndexposts == null) {
                            cacheItemIndexposts = com_example_travelin_PostRealmProxy.insertOrUpdate(realm, postsItem, cache);
                        }
                        postsOsList.addRow(cacheItemIndexposts);
                    }
                }
            }

            Table.nativeSetDouble(tableNativePtr, columnInfo.avgRatingIndex, rowIndex, ((com_example_travelin_UserRealmProxyInterface) object).realmGet$avgRating(), false);
            byte[] realmGet$img = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$img();
            if (realmGet$img != null) {
                Table.nativeSetByteArray(tableNativePtr, columnInfo.imgIndex, rowIndex, realmGet$img, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.imgIndex, rowIndex, false);
            }

            OsList profileImagesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.profileImagesIndex);
            profileImagesOsList.removeAll();
            RealmList<byte[]> profileImagesList = ((com_example_travelin_UserRealmProxyInterface) object).realmGet$profileImages();
            if (profileImagesList != null) {
                for (byte[] profileImagesItem : profileImagesList) {
                    if (profileImagesItem == null) {
                        profileImagesOsList.addNull();
                    } else {
                        profileImagesOsList.addBinary(profileImagesItem);
                    }
                }
            }

        }
    }

    public static com.example.travelin.User createDetachedCopy(com.example.travelin.User realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.example.travelin.User unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.example.travelin.User();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.example.travelin.User) cachedObject.object;
            }
            unmanagedObject = (com.example.travelin.User) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_example_travelin_UserRealmProxyInterface unmanagedCopy = (com_example_travelin_UserRealmProxyInterface) unmanagedObject;
        com_example_travelin_UserRealmProxyInterface realmSource = (com_example_travelin_UserRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$password(realmSource.realmGet$password());
        unmanagedCopy.realmSet$email(realmSource.realmGet$email());
        unmanagedCopy.realmSet$age(realmSource.realmGet$age());
        unmanagedCopy.realmSet$gender(realmSource.realmGet$gender());

        // Deep copy of myRatings
        if (currentDepth == maxDepth) {
            unmanagedCopy.realmSet$myRatings(null);
        } else {
            RealmList<com.example.travelin.MyRating> managedmyRatingsList = realmSource.realmGet$myRatings();
            RealmList<com.example.travelin.MyRating> unmanagedmyRatingsList = new RealmList<com.example.travelin.MyRating>();
            unmanagedCopy.realmSet$myRatings(unmanagedmyRatingsList);
            int nextDepth = currentDepth + 1;
            int size = managedmyRatingsList.size();
            for (int i = 0; i < size; i++) {
                com.example.travelin.MyRating item = com_example_travelin_MyRatingRealmProxy.createDetachedCopy(managedmyRatingsList.get(i), nextDepth, maxDepth, cache);
                unmanagedmyRatingsList.add(item);
            }
        }

        // Deep copy of reviews
        if (currentDepth == maxDepth) {
            unmanagedCopy.realmSet$reviews(null);
        } else {
            RealmList<com.example.travelin.MyRating> managedreviewsList = realmSource.realmGet$reviews();
            RealmList<com.example.travelin.MyRating> unmanagedreviewsList = new RealmList<com.example.travelin.MyRating>();
            unmanagedCopy.realmSet$reviews(unmanagedreviewsList);
            int nextDepth = currentDepth + 1;
            int size = managedreviewsList.size();
            for (int i = 0; i < size; i++) {
                com.example.travelin.MyRating item = com_example_travelin_MyRatingRealmProxy.createDetachedCopy(managedreviewsList.get(i), nextDepth, maxDepth, cache);
                unmanagedreviewsList.add(item);
            }
        }
        unmanagedCopy.realmSet$reportCount(realmSource.realmGet$reportCount());
        unmanagedCopy.realmSet$bio(realmSource.realmGet$bio());

        // Deep copy of interests
        if (currentDepth == maxDepth) {
            unmanagedCopy.realmSet$interests(null);
        } else {
            RealmList<com.example.travelin.Tag> managedinterestsList = realmSource.realmGet$interests();
            RealmList<com.example.travelin.Tag> unmanagedinterestsList = new RealmList<com.example.travelin.Tag>();
            unmanagedCopy.realmSet$interests(unmanagedinterestsList);
            int nextDepth = currentDepth + 1;
            int size = managedinterestsList.size();
            for (int i = 0; i < size; i++) {
                com.example.travelin.Tag item = com_example_travelin_TagRealmProxy.createDetachedCopy(managedinterestsList.get(i), nextDepth, maxDepth, cache);
                unmanagedinterestsList.add(item);
            }
        }

        // Deep copy of blocked
        if (currentDepth == maxDepth) {
            unmanagedCopy.realmSet$blocked(null);
        } else {
            RealmList<com.example.travelin.User> managedblockedList = realmSource.realmGet$blocked();
            RealmList<com.example.travelin.User> unmanagedblockedList = new RealmList<com.example.travelin.User>();
            unmanagedCopy.realmSet$blocked(unmanagedblockedList);
            int nextDepth = currentDepth + 1;
            int size = managedblockedList.size();
            for (int i = 0; i < size; i++) {
                com.example.travelin.User item = com_example_travelin_UserRealmProxy.createDetachedCopy(managedblockedList.get(i), nextDepth, maxDepth, cache);
                unmanagedblockedList.add(item);
            }
        }
        unmanagedCopy.realmSet$deleted(realmSource.realmGet$deleted());
        unmanagedCopy.realmSet$name(realmSource.realmGet$name());

        // Deep copy of messages
        if (currentDepth == maxDepth) {
            unmanagedCopy.realmSet$messages(null);
        } else {
            RealmList<com.example.travelin.DirectMessage> managedmessagesList = realmSource.realmGet$messages();
            RealmList<com.example.travelin.DirectMessage> unmanagedmessagesList = new RealmList<com.example.travelin.DirectMessage>();
            unmanagedCopy.realmSet$messages(unmanagedmessagesList);
            int nextDepth = currentDepth + 1;
            int size = managedmessagesList.size();
            for (int i = 0; i < size; i++) {
                com.example.travelin.DirectMessage item = com_example_travelin_DirectMessageRealmProxy.createDetachedCopy(managedmessagesList.get(i), nextDepth, maxDepth, cache);
                unmanagedmessagesList.add(item);
            }
        }
        unmanagedCopy.realmSet$username(realmSource.realmGet$username());
        unmanagedCopy.realmSet$profilePictureURL(realmSource.realmGet$profilePictureURL());

        // Deep copy of previousTrips
        if (currentDepth == maxDepth) {
            unmanagedCopy.realmSet$previousTrips(null);
        } else {
            RealmList<com.example.travelin.EventLocation> managedpreviousTripsList = realmSource.realmGet$previousTrips();
            RealmList<com.example.travelin.EventLocation> unmanagedpreviousTripsList = new RealmList<com.example.travelin.EventLocation>();
            unmanagedCopy.realmSet$previousTrips(unmanagedpreviousTripsList);
            int nextDepth = currentDepth + 1;
            int size = managedpreviousTripsList.size();
            for (int i = 0; i < size; i++) {
                com.example.travelin.EventLocation item = com_example_travelin_EventLocationRealmProxy.createDetachedCopy(managedpreviousTripsList.get(i), nextDepth, maxDepth, cache);
                unmanagedpreviousTripsList.add(item);
            }
        }

        // Deep copy of favorites
        if (currentDepth == maxDepth) {
            unmanagedCopy.realmSet$favorites(null);
        } else {
            RealmList<com.example.travelin.User> managedfavoritesList = realmSource.realmGet$favorites();
            RealmList<com.example.travelin.User> unmanagedfavoritesList = new RealmList<com.example.travelin.User>();
            unmanagedCopy.realmSet$favorites(unmanagedfavoritesList);
            int nextDepth = currentDepth + 1;
            int size = managedfavoritesList.size();
            for (int i = 0; i < size; i++) {
                com.example.travelin.User item = com_example_travelin_UserRealmProxy.createDetachedCopy(managedfavoritesList.get(i), nextDepth, maxDepth, cache);
                unmanagedfavoritesList.add(item);
            }
        }

        // Deep copy of posts
        if (currentDepth == maxDepth) {
            unmanagedCopy.realmSet$posts(null);
        } else {
            RealmList<com.example.travelin.Post> managedpostsList = realmSource.realmGet$posts();
            RealmList<com.example.travelin.Post> unmanagedpostsList = new RealmList<com.example.travelin.Post>();
            unmanagedCopy.realmSet$posts(unmanagedpostsList);
            int nextDepth = currentDepth + 1;
            int size = managedpostsList.size();
            for (int i = 0; i < size; i++) {
                com.example.travelin.Post item = com_example_travelin_PostRealmProxy.createDetachedCopy(managedpostsList.get(i), nextDepth, maxDepth, cache);
                unmanagedpostsList.add(item);
            }
        }
        unmanagedCopy.realmSet$avgRating(realmSource.realmGet$avgRating());
        unmanagedCopy.realmSet$img(realmSource.realmGet$img());

        unmanagedCopy.realmSet$profileImages(new RealmList<byte[]>());
        unmanagedCopy.realmGet$profileImages().addAll(realmSource.realmGet$profileImages());

        return unmanagedObject;
    }

    static com.example.travelin.User update(Realm realm, UserColumnInfo columnInfo, com.example.travelin.User realmObject, com.example.travelin.User newObject, Map<RealmModel, RealmObjectProxy> cache, Set<ImportFlag> flags) {
        com_example_travelin_UserRealmProxyInterface realmObjectTarget = (com_example_travelin_UserRealmProxyInterface) realmObject;
        com_example_travelin_UserRealmProxyInterface realmObjectSource = (com_example_travelin_UserRealmProxyInterface) newObject;
        Table table = realm.getTable(com.example.travelin.User.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, columnInfo.maxColumnIndexValue, flags);
        builder.addString(columnInfo.passwordIndex, realmObjectSource.realmGet$password());
        builder.addString(columnInfo.emailIndex, realmObjectSource.realmGet$email());
        builder.addInteger(columnInfo.ageIndex, realmObjectSource.realmGet$age());
        builder.addString(columnInfo.genderIndex, realmObjectSource.realmGet$gender());

        RealmList<com.example.travelin.MyRating> myRatingsList = realmObjectSource.realmGet$myRatings();
        if (myRatingsList != null) {
            RealmList<com.example.travelin.MyRating> myRatingsManagedCopy = new RealmList<com.example.travelin.MyRating>();
            for (int i = 0; i < myRatingsList.size(); i++) {
                com.example.travelin.MyRating myRatingsItem = myRatingsList.get(i);
                com.example.travelin.MyRating cachemyRatings = (com.example.travelin.MyRating) cache.get(myRatingsItem);
                if (cachemyRatings != null) {
                    myRatingsManagedCopy.add(cachemyRatings);
                } else {
                    myRatingsManagedCopy.add(com_example_travelin_MyRatingRealmProxy.copyOrUpdate(realm, (com_example_travelin_MyRatingRealmProxy.MyRatingColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.MyRating.class), myRatingsItem, true, cache, flags));
                }
            }
            builder.addObjectList(columnInfo.myRatingsIndex, myRatingsManagedCopy);
        } else {
            builder.addObjectList(columnInfo.myRatingsIndex, new RealmList<com.example.travelin.MyRating>());
        }

        RealmList<com.example.travelin.MyRating> reviewsList = realmObjectSource.realmGet$reviews();
        if (reviewsList != null) {
            RealmList<com.example.travelin.MyRating> reviewsManagedCopy = new RealmList<com.example.travelin.MyRating>();
            for (int i = 0; i < reviewsList.size(); i++) {
                com.example.travelin.MyRating reviewsItem = reviewsList.get(i);
                com.example.travelin.MyRating cachereviews = (com.example.travelin.MyRating) cache.get(reviewsItem);
                if (cachereviews != null) {
                    reviewsManagedCopy.add(cachereviews);
                } else {
                    reviewsManagedCopy.add(com_example_travelin_MyRatingRealmProxy.copyOrUpdate(realm, (com_example_travelin_MyRatingRealmProxy.MyRatingColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.MyRating.class), reviewsItem, true, cache, flags));
                }
            }
            builder.addObjectList(columnInfo.reviewsIndex, reviewsManagedCopy);
        } else {
            builder.addObjectList(columnInfo.reviewsIndex, new RealmList<com.example.travelin.MyRating>());
        }
        builder.addInteger(columnInfo.reportCountIndex, realmObjectSource.realmGet$reportCount());
        builder.addString(columnInfo.bioIndex, realmObjectSource.realmGet$bio());

        RealmList<com.example.travelin.Tag> interestsList = realmObjectSource.realmGet$interests();
        if (interestsList != null) {
            RealmList<com.example.travelin.Tag> interestsManagedCopy = new RealmList<com.example.travelin.Tag>();
            for (int i = 0; i < interestsList.size(); i++) {
                com.example.travelin.Tag interestsItem = interestsList.get(i);
                com.example.travelin.Tag cacheinterests = (com.example.travelin.Tag) cache.get(interestsItem);
                if (cacheinterests != null) {
                    interestsManagedCopy.add(cacheinterests);
                } else {
                    interestsManagedCopy.add(com_example_travelin_TagRealmProxy.copyOrUpdate(realm, (com_example_travelin_TagRealmProxy.TagColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.Tag.class), interestsItem, true, cache, flags));
                }
            }
            builder.addObjectList(columnInfo.interestsIndex, interestsManagedCopy);
        } else {
            builder.addObjectList(columnInfo.interestsIndex, new RealmList<com.example.travelin.Tag>());
        }

        RealmList<com.example.travelin.User> blockedList = realmObjectSource.realmGet$blocked();
        if (blockedList != null) {
            RealmList<com.example.travelin.User> blockedManagedCopy = new RealmList<com.example.travelin.User>();
            for (int i = 0; i < blockedList.size(); i++) {
                com.example.travelin.User blockedItem = blockedList.get(i);
                com.example.travelin.User cacheblocked = (com.example.travelin.User) cache.get(blockedItem);
                if (cacheblocked != null) {
                    blockedManagedCopy.add(cacheblocked);
                } else {
                    blockedManagedCopy.add(com_example_travelin_UserRealmProxy.copyOrUpdate(realm, (com_example_travelin_UserRealmProxy.UserColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.User.class), blockedItem, true, cache, flags));
                }
            }
            builder.addObjectList(columnInfo.blockedIndex, blockedManagedCopy);
        } else {
            builder.addObjectList(columnInfo.blockedIndex, new RealmList<com.example.travelin.User>());
        }
        builder.addBoolean(columnInfo.deletedIndex, realmObjectSource.realmGet$deleted());
        builder.addString(columnInfo.nameIndex, realmObjectSource.realmGet$name());

        RealmList<com.example.travelin.DirectMessage> messagesList = realmObjectSource.realmGet$messages();
        if (messagesList != null) {
            RealmList<com.example.travelin.DirectMessage> messagesManagedCopy = new RealmList<com.example.travelin.DirectMessage>();
            for (int i = 0; i < messagesList.size(); i++) {
                com.example.travelin.DirectMessage messagesItem = messagesList.get(i);
                com.example.travelin.DirectMessage cachemessages = (com.example.travelin.DirectMessage) cache.get(messagesItem);
                if (cachemessages != null) {
                    messagesManagedCopy.add(cachemessages);
                } else {
                    messagesManagedCopy.add(com_example_travelin_DirectMessageRealmProxy.copyOrUpdate(realm, (com_example_travelin_DirectMessageRealmProxy.DirectMessageColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.DirectMessage.class), messagesItem, true, cache, flags));
                }
            }
            builder.addObjectList(columnInfo.messagesIndex, messagesManagedCopy);
        } else {
            builder.addObjectList(columnInfo.messagesIndex, new RealmList<com.example.travelin.DirectMessage>());
        }
        builder.addString(columnInfo.usernameIndex, realmObjectSource.realmGet$username());
        builder.addString(columnInfo.profilePictureURLIndex, realmObjectSource.realmGet$profilePictureURL());

        RealmList<com.example.travelin.EventLocation> previousTripsList = realmObjectSource.realmGet$previousTrips();
        if (previousTripsList != null) {
            RealmList<com.example.travelin.EventLocation> previousTripsManagedCopy = new RealmList<com.example.travelin.EventLocation>();
            for (int i = 0; i < previousTripsList.size(); i++) {
                com.example.travelin.EventLocation previousTripsItem = previousTripsList.get(i);
                com.example.travelin.EventLocation cachepreviousTrips = (com.example.travelin.EventLocation) cache.get(previousTripsItem);
                if (cachepreviousTrips != null) {
                    previousTripsManagedCopy.add(cachepreviousTrips);
                } else {
                    previousTripsManagedCopy.add(com_example_travelin_EventLocationRealmProxy.copyOrUpdate(realm, (com_example_travelin_EventLocationRealmProxy.EventLocationColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.EventLocation.class), previousTripsItem, true, cache, flags));
                }
            }
            builder.addObjectList(columnInfo.previousTripsIndex, previousTripsManagedCopy);
        } else {
            builder.addObjectList(columnInfo.previousTripsIndex, new RealmList<com.example.travelin.EventLocation>());
        }

        RealmList<com.example.travelin.User> favoritesList = realmObjectSource.realmGet$favorites();
        if (favoritesList != null) {
            RealmList<com.example.travelin.User> favoritesManagedCopy = new RealmList<com.example.travelin.User>();
            for (int i = 0; i < favoritesList.size(); i++) {
                com.example.travelin.User favoritesItem = favoritesList.get(i);
                com.example.travelin.User cachefavorites = (com.example.travelin.User) cache.get(favoritesItem);
                if (cachefavorites != null) {
                    favoritesManagedCopy.add(cachefavorites);
                } else {
                    favoritesManagedCopy.add(com_example_travelin_UserRealmProxy.copyOrUpdate(realm, (com_example_travelin_UserRealmProxy.UserColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.User.class), favoritesItem, true, cache, flags));
                }
            }
            builder.addObjectList(columnInfo.favoritesIndex, favoritesManagedCopy);
        } else {
            builder.addObjectList(columnInfo.favoritesIndex, new RealmList<com.example.travelin.User>());
        }

        RealmList<com.example.travelin.Post> postsList = realmObjectSource.realmGet$posts();
        if (postsList != null) {
            RealmList<com.example.travelin.Post> postsManagedCopy = new RealmList<com.example.travelin.Post>();
            for (int i = 0; i < postsList.size(); i++) {
                com.example.travelin.Post postsItem = postsList.get(i);
                com.example.travelin.Post cacheposts = (com.example.travelin.Post) cache.get(postsItem);
                if (cacheposts != null) {
                    postsManagedCopy.add(cacheposts);
                } else {
                    postsManagedCopy.add(com_example_travelin_PostRealmProxy.copyOrUpdate(realm, (com_example_travelin_PostRealmProxy.PostColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.Post.class), postsItem, true, cache, flags));
                }
            }
            builder.addObjectList(columnInfo.postsIndex, postsManagedCopy);
        } else {
            builder.addObjectList(columnInfo.postsIndex, new RealmList<com.example.travelin.Post>());
        }
        builder.addDouble(columnInfo.avgRatingIndex, realmObjectSource.realmGet$avgRating());
        builder.addByteArray(columnInfo.imgIndex, realmObjectSource.realmGet$img());
        builder.addByteArrayList(columnInfo.profileImagesIndex, realmObjectSource.realmGet$profileImages());

        builder.updateExistingObject();
        return realmObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("User = proxy[");
        stringBuilder.append("{password:");
        stringBuilder.append(realmGet$password() != null ? realmGet$password() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{email:");
        stringBuilder.append(realmGet$email() != null ? realmGet$email() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{age:");
        stringBuilder.append(realmGet$age());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{gender:");
        stringBuilder.append(realmGet$gender() != null ? realmGet$gender() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{myRatings:");
        stringBuilder.append("RealmList<MyRating>[").append(realmGet$myRatings().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{reviews:");
        stringBuilder.append("RealmList<MyRating>[").append(realmGet$reviews().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{reportCount:");
        stringBuilder.append(realmGet$reportCount());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{bio:");
        stringBuilder.append(realmGet$bio() != null ? realmGet$bio() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{interests:");
        stringBuilder.append("RealmList<Tag>[").append(realmGet$interests().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{blocked:");
        stringBuilder.append("RealmList<User>[").append(realmGet$blocked().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{deleted:");
        stringBuilder.append(realmGet$deleted());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{name:");
        stringBuilder.append(realmGet$name() != null ? realmGet$name() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{messages:");
        stringBuilder.append("RealmList<DirectMessage>[").append(realmGet$messages().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{username:");
        stringBuilder.append(realmGet$username() != null ? realmGet$username() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{profilePictureURL:");
        stringBuilder.append(realmGet$profilePictureURL() != null ? realmGet$profilePictureURL() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{previousTrips:");
        stringBuilder.append("RealmList<EventLocation>[").append(realmGet$previousTrips().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{favorites:");
        stringBuilder.append("RealmList<User>[").append(realmGet$favorites().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{posts:");
        stringBuilder.append("RealmList<Post>[").append(realmGet$posts().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{avgRating:");
        stringBuilder.append(realmGet$avgRating());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{img:");
        stringBuilder.append(realmGet$img() != null ? realmGet$img() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{profileImages:");
        stringBuilder.append("RealmList<byte[]>[").append(realmGet$profileImages().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public ProxyState<?> realmGet$proxyState() {
        return proxyState;
    }

    @Override
    public int hashCode() {
        String realmName = proxyState.getRealm$realm().getPath();
        String tableName = proxyState.getRow$realm().getTable().getName();
        long rowIndex = proxyState.getRow$realm().getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        com_example_travelin_UserRealmProxy aUser = (com_example_travelin_UserRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aUser.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aUser.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aUser.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
