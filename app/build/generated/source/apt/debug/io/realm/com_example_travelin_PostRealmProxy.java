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
public class com_example_travelin_PostRealmProxy extends com.example.travelin.Post
    implements RealmObjectProxy, com_example_travelin_PostRealmProxyInterface {

    static final class PostColumnInfo extends ColumnInfo {
        long maxColumnIndexValue;
        long typeIndex;
        long authorIndex;
        long datePostedIndex;
        long bodyIndex;
        long rateUpIndex;
        long rateDownIndex;
        long repliesIndex;
        long imageURLsIndex;

        PostColumnInfo(OsSchemaInfo schemaInfo) {
            super(8);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("Post");
            this.typeIndex = addColumnDetails("type", "type", objectSchemaInfo);
            this.authorIndex = addColumnDetails("author", "author", objectSchemaInfo);
            this.datePostedIndex = addColumnDetails("datePosted", "datePosted", objectSchemaInfo);
            this.bodyIndex = addColumnDetails("body", "body", objectSchemaInfo);
            this.rateUpIndex = addColumnDetails("rateUp", "rateUp", objectSchemaInfo);
            this.rateDownIndex = addColumnDetails("rateDown", "rateDown", objectSchemaInfo);
            this.repliesIndex = addColumnDetails("replies", "replies", objectSchemaInfo);
            this.imageURLsIndex = addColumnDetails("imageURLs", "imageURLs", objectSchemaInfo);
            this.maxColumnIndexValue = objectSchemaInfo.getMaxColumnIndex();
        }

        PostColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new PostColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final PostColumnInfo src = (PostColumnInfo) rawSrc;
            final PostColumnInfo dst = (PostColumnInfo) rawDst;
            dst.typeIndex = src.typeIndex;
            dst.authorIndex = src.authorIndex;
            dst.datePostedIndex = src.datePostedIndex;
            dst.bodyIndex = src.bodyIndex;
            dst.rateUpIndex = src.rateUpIndex;
            dst.rateDownIndex = src.rateDownIndex;
            dst.repliesIndex = src.repliesIndex;
            dst.imageURLsIndex = src.imageURLsIndex;
            dst.maxColumnIndexValue = src.maxColumnIndexValue;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private PostColumnInfo columnInfo;
    private ProxyState<com.example.travelin.Post> proxyState;
    private RealmList<com.example.travelin.Post> repliesRealmList;
    private RealmList<String> imageURLsRealmList;

    com_example_travelin_PostRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (PostColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.example.travelin.Post>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$type() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.typeIndex);
    }

    @Override
    public void realmSet$type(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.typeIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.typeIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.typeIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.typeIndex, value);
    }

    @Override
    public com.example.travelin.User realmGet$author() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNullLink(columnInfo.authorIndex)) {
            return null;
        }
        return proxyState.getRealm$realm().get(com.example.travelin.User.class, proxyState.getRow$realm().getLink(columnInfo.authorIndex), false, Collections.<String>emptyList());
    }

    @Override
    public void realmSet$author(com.example.travelin.User value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("author")) {
                return;
            }
            if (value != null && !RealmObject.isManaged(value)) {
                value = ((Realm) proxyState.getRealm$realm()).copyToRealm(value);
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                // Table#nullifyLink() does not support default value. Just using Row.
                row.nullifyLink(columnInfo.authorIndex);
                return;
            }
            proxyState.checkValidObject(value);
            row.getTable().setLink(columnInfo.authorIndex, row.getIndex(), ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex(), true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().nullifyLink(columnInfo.authorIndex);
            return;
        }
        proxyState.checkValidObject(value);
        proxyState.getRow$realm().setLink(columnInfo.authorIndex, ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex());
    }

    @Override
    @SuppressWarnings("cast")
    public Date realmGet$datePosted() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.datePostedIndex)) {
            return null;
        }
        return (java.util.Date) proxyState.getRow$realm().getDate(columnInfo.datePostedIndex);
    }

    @Override
    public void realmSet$datePosted(Date value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.datePostedIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setDate(columnInfo.datePostedIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.datePostedIndex);
            return;
        }
        proxyState.getRow$realm().setDate(columnInfo.datePostedIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$body() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.bodyIndex);
    }

    @Override
    public void realmSet$body(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.bodyIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.bodyIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.bodyIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.bodyIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$rateUp() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.rateUpIndex);
    }

    @Override
    public void realmSet$rateUp(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.rateUpIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.rateUpIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public int realmGet$rateDown() {
        proxyState.getRealm$realm().checkIfValid();
        return (int) proxyState.getRow$realm().getLong(columnInfo.rateDownIndex);
    }

    @Override
    public void realmSet$rateDown(int value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            row.getTable().setLong(columnInfo.rateDownIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        proxyState.getRow$realm().setLong(columnInfo.rateDownIndex, value);
    }

    @Override
    public RealmList<com.example.travelin.Post> realmGet$replies() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (repliesRealmList != null) {
            return repliesRealmList;
        } else {
            OsList osList = proxyState.getRow$realm().getModelList(columnInfo.repliesIndex);
            repliesRealmList = new RealmList<com.example.travelin.Post>(com.example.travelin.Post.class, osList, proxyState.getRealm$realm());
            return repliesRealmList;
        }
    }

    @Override
    public void realmSet$replies(RealmList<com.example.travelin.Post> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("replies")) {
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
        OsList osList = proxyState.getRow$realm().getModelList(columnInfo.repliesIndex);
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
    public RealmList<String> realmGet$imageURLs() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (imageURLsRealmList != null) {
            return imageURLsRealmList;
        } else {
            OsList osList = proxyState.getRow$realm().getValueList(columnInfo.imageURLsIndex, RealmFieldType.STRING_LIST);
            imageURLsRealmList = new RealmList<java.lang.String>(java.lang.String.class, osList, proxyState.getRealm$realm());
            return imageURLsRealmList;
        }
    }

    @Override
    public void realmSet$imageURLs(RealmList<String> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("imageURLs")) {
                return;
            }
        }

        proxyState.getRealm$realm().checkIfValid();
        OsList osList = proxyState.getRow$realm().getValueList(columnInfo.imageURLsIndex, RealmFieldType.STRING_LIST);
        osList.removeAll();
        if (value == null) {
            return;
        }
        for (java.lang.String item : value) {
            if (item == null) {
                osList.addNull();
            } else {
                osList.addString(item);
            }
        }
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("Post", 8, 0);
        builder.addPersistedProperty("type", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedLinkProperty("author", RealmFieldType.OBJECT, "User");
        builder.addPersistedProperty("datePosted", RealmFieldType.DATE, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("body", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("rateUp", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedProperty("rateDown", RealmFieldType.INTEGER, !Property.PRIMARY_KEY, !Property.INDEXED, Property.REQUIRED);
        builder.addPersistedLinkProperty("replies", RealmFieldType.LIST, "Post");
        builder.addPersistedValueListProperty("imageURLs", RealmFieldType.STRING_LIST, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static PostColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new PostColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "Post";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "Post";
    }

    @SuppressWarnings("cast")
    public static com.example.travelin.Post createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(3);
        if (json.has("author")) {
            excludeFields.add("author");
        }
        if (json.has("replies")) {
            excludeFields.add("replies");
        }
        if (json.has("imageURLs")) {
            excludeFields.add("imageURLs");
        }
        com.example.travelin.Post obj = realm.createObjectInternal(com.example.travelin.Post.class, true, excludeFields);

        final com_example_travelin_PostRealmProxyInterface objProxy = (com_example_travelin_PostRealmProxyInterface) obj;
        if (json.has("type")) {
            if (json.isNull("type")) {
                objProxy.realmSet$type(null);
            } else {
                objProxy.realmSet$type((String) json.getString("type"));
            }
        }
        if (json.has("author")) {
            if (json.isNull("author")) {
                objProxy.realmSet$author(null);
            } else {
                com.example.travelin.User authorObj = com_example_travelin_UserRealmProxy.createOrUpdateUsingJsonObject(realm, json.getJSONObject("author"), update);
                objProxy.realmSet$author(authorObj);
            }
        }
        if (json.has("datePosted")) {
            if (json.isNull("datePosted")) {
                objProxy.realmSet$datePosted(null);
            } else {
                Object timestamp = json.get("datePosted");
                if (timestamp instanceof String) {
                    objProxy.realmSet$datePosted(JsonUtils.stringToDate((String) timestamp));
                } else {
                    objProxy.realmSet$datePosted(new Date(json.getLong("datePosted")));
                }
            }
        }
        if (json.has("body")) {
            if (json.isNull("body")) {
                objProxy.realmSet$body(null);
            } else {
                objProxy.realmSet$body((String) json.getString("body"));
            }
        }
        if (json.has("rateUp")) {
            if (json.isNull("rateUp")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'rateUp' to null.");
            } else {
                objProxy.realmSet$rateUp((int) json.getInt("rateUp"));
            }
        }
        if (json.has("rateDown")) {
            if (json.isNull("rateDown")) {
                throw new IllegalArgumentException("Trying to set non-nullable field 'rateDown' to null.");
            } else {
                objProxy.realmSet$rateDown((int) json.getInt("rateDown"));
            }
        }
        if (json.has("replies")) {
            if (json.isNull("replies")) {
                objProxy.realmSet$replies(null);
            } else {
                objProxy.realmGet$replies().clear();
                JSONArray array = json.getJSONArray("replies");
                for (int i = 0; i < array.length(); i++) {
                    com.example.travelin.Post item = com_example_travelin_PostRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    objProxy.realmGet$replies().add(item);
                }
            }
        }
        ProxyUtils.setRealmListWithJsonObject(objProxy.realmGet$imageURLs(), json, "imageURLs");
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.example.travelin.Post createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        final com.example.travelin.Post obj = new com.example.travelin.Post();
        final com_example_travelin_PostRealmProxyInterface objProxy = (com_example_travelin_PostRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("type")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$type((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$type(null);
                }
            } else if (name.equals("author")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$author(null);
                } else {
                    com.example.travelin.User authorObj = com_example_travelin_UserRealmProxy.createUsingJsonStream(realm, reader);
                    objProxy.realmSet$author(authorObj);
                }
            } else if (name.equals("datePosted")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$datePosted(null);
                } else if (reader.peek() == JsonToken.NUMBER) {
                    long timestamp = reader.nextLong();
                    if (timestamp > -1) {
                        objProxy.realmSet$datePosted(new Date(timestamp));
                    }
                } else {
                    objProxy.realmSet$datePosted(JsonUtils.stringToDate(reader.nextString()));
                }
            } else if (name.equals("body")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$body((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$body(null);
                }
            } else if (name.equals("rateUp")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$rateUp((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'rateUp' to null.");
                }
            } else if (name.equals("rateDown")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$rateDown((int) reader.nextInt());
                } else {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field 'rateDown' to null.");
                }
            } else if (name.equals("replies")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$replies(null);
                } else {
                    objProxy.realmSet$replies(new RealmList<com.example.travelin.Post>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.example.travelin.Post item = com_example_travelin_PostRealmProxy.createUsingJsonStream(realm, reader);
                        objProxy.realmGet$replies().add(item);
                    }
                    reader.endArray();
                }
            } else if (name.equals("imageURLs")) {
                objProxy.realmSet$imageURLs(ProxyUtils.createRealmListWithJsonStream(java.lang.String.class, reader));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return realm.copyToRealm(obj);
    }

    private static com_example_travelin_PostRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating uexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.example.travelin.Post.class), false, Collections.<String>emptyList());
        io.realm.com_example_travelin_PostRealmProxy obj = new io.realm.com_example_travelin_PostRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.example.travelin.Post copyOrUpdate(Realm realm, PostColumnInfo columnInfo, com.example.travelin.Post object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.example.travelin.Post) cachedRealmObject;
        }

        return copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.example.travelin.Post copy(Realm realm, PostColumnInfo columnInfo, com.example.travelin.Post newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.example.travelin.Post) cachedRealmObject;
        }

        com_example_travelin_PostRealmProxyInterface realmObjectSource = (com_example_travelin_PostRealmProxyInterface) newObject;

        Table table = realm.getTable(com.example.travelin.Post.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, columnInfo.maxColumnIndexValue, flags);

        // Add all non-"object reference" fields
        builder.addString(columnInfo.typeIndex, realmObjectSource.realmGet$type());
        builder.addDate(columnInfo.datePostedIndex, realmObjectSource.realmGet$datePosted());
        builder.addString(columnInfo.bodyIndex, realmObjectSource.realmGet$body());
        builder.addInteger(columnInfo.rateUpIndex, realmObjectSource.realmGet$rateUp());
        builder.addInteger(columnInfo.rateDownIndex, realmObjectSource.realmGet$rateDown());
        builder.addStringList(columnInfo.imageURLsIndex, realmObjectSource.realmGet$imageURLs());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_example_travelin_PostRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        // Finally add all fields that reference other Realm Objects, either directly or through a list
        com.example.travelin.User authorObj = realmObjectSource.realmGet$author();
        if (authorObj == null) {
            realmObjectCopy.realmSet$author(null);
        } else {
            com.example.travelin.User cacheauthor = (com.example.travelin.User) cache.get(authorObj);
            if (cacheauthor != null) {
                realmObjectCopy.realmSet$author(cacheauthor);
            } else {
                realmObjectCopy.realmSet$author(com_example_travelin_UserRealmProxy.copyOrUpdate(realm, (com_example_travelin_UserRealmProxy.UserColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.User.class), authorObj, update, cache, flags));
            }
        }

        RealmList<com.example.travelin.Post> repliesList = realmObjectSource.realmGet$replies();
        if (repliesList != null) {
            RealmList<com.example.travelin.Post> repliesRealmList = realmObjectCopy.realmGet$replies();
            repliesRealmList.clear();
            for (int i = 0; i < repliesList.size(); i++) {
                com.example.travelin.Post repliesItem = repliesList.get(i);
                com.example.travelin.Post cachereplies = (com.example.travelin.Post) cache.get(repliesItem);
                if (cachereplies != null) {
                    repliesRealmList.add(cachereplies);
                } else {
                    repliesRealmList.add(com_example_travelin_PostRealmProxy.copyOrUpdate(realm, (com_example_travelin_PostRealmProxy.PostColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.Post.class), repliesItem, update, cache, flags));
                }
            }
        }

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.example.travelin.Post object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.example.travelin.Post.class);
        long tableNativePtr = table.getNativePtr();
        PostColumnInfo columnInfo = (PostColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.Post.class);
        long rowIndex = OsObject.createRow(table);
        cache.put(object, rowIndex);
        String realmGet$type = ((com_example_travelin_PostRealmProxyInterface) object).realmGet$type();
        if (realmGet$type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.typeIndex, rowIndex, realmGet$type, false);
        }

        com.example.travelin.User authorObj = ((com_example_travelin_PostRealmProxyInterface) object).realmGet$author();
        if (authorObj != null) {
            Long cacheauthor = cache.get(authorObj);
            if (cacheauthor == null) {
                cacheauthor = com_example_travelin_UserRealmProxy.insert(realm, authorObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.authorIndex, rowIndex, cacheauthor, false);
        }
        java.util.Date realmGet$datePosted = ((com_example_travelin_PostRealmProxyInterface) object).realmGet$datePosted();
        if (realmGet$datePosted != null) {
            Table.nativeSetTimestamp(tableNativePtr, columnInfo.datePostedIndex, rowIndex, realmGet$datePosted.getTime(), false);
        }
        String realmGet$body = ((com_example_travelin_PostRealmProxyInterface) object).realmGet$body();
        if (realmGet$body != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.bodyIndex, rowIndex, realmGet$body, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.rateUpIndex, rowIndex, ((com_example_travelin_PostRealmProxyInterface) object).realmGet$rateUp(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.rateDownIndex, rowIndex, ((com_example_travelin_PostRealmProxyInterface) object).realmGet$rateDown(), false);

        RealmList<com.example.travelin.Post> repliesList = ((com_example_travelin_PostRealmProxyInterface) object).realmGet$replies();
        if (repliesList != null) {
            OsList repliesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.repliesIndex);
            for (com.example.travelin.Post repliesItem : repliesList) {
                Long cacheItemIndexreplies = cache.get(repliesItem);
                if (cacheItemIndexreplies == null) {
                    cacheItemIndexreplies = com_example_travelin_PostRealmProxy.insert(realm, repliesItem, cache);
                }
                repliesOsList.addRow(cacheItemIndexreplies);
            }
        }

        RealmList<java.lang.String> imageURLsList = ((com_example_travelin_PostRealmProxyInterface) object).realmGet$imageURLs();
        if (imageURLsList != null) {
            OsList imageURLsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.imageURLsIndex);
            for (java.lang.String imageURLsItem : imageURLsList) {
                if (imageURLsItem == null) {
                    imageURLsOsList.addNull();
                } else {
                    imageURLsOsList.addString(imageURLsItem);
                }
            }
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.travelin.Post.class);
        long tableNativePtr = table.getNativePtr();
        PostColumnInfo columnInfo = (PostColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.Post.class);
        com.example.travelin.Post object = null;
        while (objects.hasNext()) {
            object = (com.example.travelin.Post) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = OsObject.createRow(table);
            cache.put(object, rowIndex);
            String realmGet$type = ((com_example_travelin_PostRealmProxyInterface) object).realmGet$type();
            if (realmGet$type != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.typeIndex, rowIndex, realmGet$type, false);
            }

            com.example.travelin.User authorObj = ((com_example_travelin_PostRealmProxyInterface) object).realmGet$author();
            if (authorObj != null) {
                Long cacheauthor = cache.get(authorObj);
                if (cacheauthor == null) {
                    cacheauthor = com_example_travelin_UserRealmProxy.insert(realm, authorObj, cache);
                }
                table.setLink(columnInfo.authorIndex, rowIndex, cacheauthor, false);
            }
            java.util.Date realmGet$datePosted = ((com_example_travelin_PostRealmProxyInterface) object).realmGet$datePosted();
            if (realmGet$datePosted != null) {
                Table.nativeSetTimestamp(tableNativePtr, columnInfo.datePostedIndex, rowIndex, realmGet$datePosted.getTime(), false);
            }
            String realmGet$body = ((com_example_travelin_PostRealmProxyInterface) object).realmGet$body();
            if (realmGet$body != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.bodyIndex, rowIndex, realmGet$body, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.rateUpIndex, rowIndex, ((com_example_travelin_PostRealmProxyInterface) object).realmGet$rateUp(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.rateDownIndex, rowIndex, ((com_example_travelin_PostRealmProxyInterface) object).realmGet$rateDown(), false);

            RealmList<com.example.travelin.Post> repliesList = ((com_example_travelin_PostRealmProxyInterface) object).realmGet$replies();
            if (repliesList != null) {
                OsList repliesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.repliesIndex);
                for (com.example.travelin.Post repliesItem : repliesList) {
                    Long cacheItemIndexreplies = cache.get(repliesItem);
                    if (cacheItemIndexreplies == null) {
                        cacheItemIndexreplies = com_example_travelin_PostRealmProxy.insert(realm, repliesItem, cache);
                    }
                    repliesOsList.addRow(cacheItemIndexreplies);
                }
            }

            RealmList<java.lang.String> imageURLsList = ((com_example_travelin_PostRealmProxyInterface) object).realmGet$imageURLs();
            if (imageURLsList != null) {
                OsList imageURLsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.imageURLsIndex);
                for (java.lang.String imageURLsItem : imageURLsList) {
                    if (imageURLsItem == null) {
                        imageURLsOsList.addNull();
                    } else {
                        imageURLsOsList.addString(imageURLsItem);
                    }
                }
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.example.travelin.Post object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.example.travelin.Post.class);
        long tableNativePtr = table.getNativePtr();
        PostColumnInfo columnInfo = (PostColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.Post.class);
        long rowIndex = OsObject.createRow(table);
        cache.put(object, rowIndex);
        String realmGet$type = ((com_example_travelin_PostRealmProxyInterface) object).realmGet$type();
        if (realmGet$type != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.typeIndex, rowIndex, realmGet$type, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.typeIndex, rowIndex, false);
        }

        com.example.travelin.User authorObj = ((com_example_travelin_PostRealmProxyInterface) object).realmGet$author();
        if (authorObj != null) {
            Long cacheauthor = cache.get(authorObj);
            if (cacheauthor == null) {
                cacheauthor = com_example_travelin_UserRealmProxy.insertOrUpdate(realm, authorObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.authorIndex, rowIndex, cacheauthor, false);
        } else {
            Table.nativeNullifyLink(tableNativePtr, columnInfo.authorIndex, rowIndex);
        }
        java.util.Date realmGet$datePosted = ((com_example_travelin_PostRealmProxyInterface) object).realmGet$datePosted();
        if (realmGet$datePosted != null) {
            Table.nativeSetTimestamp(tableNativePtr, columnInfo.datePostedIndex, rowIndex, realmGet$datePosted.getTime(), false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.datePostedIndex, rowIndex, false);
        }
        String realmGet$body = ((com_example_travelin_PostRealmProxyInterface) object).realmGet$body();
        if (realmGet$body != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.bodyIndex, rowIndex, realmGet$body, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.bodyIndex, rowIndex, false);
        }
        Table.nativeSetLong(tableNativePtr, columnInfo.rateUpIndex, rowIndex, ((com_example_travelin_PostRealmProxyInterface) object).realmGet$rateUp(), false);
        Table.nativeSetLong(tableNativePtr, columnInfo.rateDownIndex, rowIndex, ((com_example_travelin_PostRealmProxyInterface) object).realmGet$rateDown(), false);

        OsList repliesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.repliesIndex);
        RealmList<com.example.travelin.Post> repliesList = ((com_example_travelin_PostRealmProxyInterface) object).realmGet$replies();
        if (repliesList != null && repliesList.size() == repliesOsList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = repliesList.size();
            for (int i = 0; i < objects; i++) {
                com.example.travelin.Post repliesItem = repliesList.get(i);
                Long cacheItemIndexreplies = cache.get(repliesItem);
                if (cacheItemIndexreplies == null) {
                    cacheItemIndexreplies = com_example_travelin_PostRealmProxy.insertOrUpdate(realm, repliesItem, cache);
                }
                repliesOsList.setRow(i, cacheItemIndexreplies);
            }
        } else {
            repliesOsList.removeAll();
            if (repliesList != null) {
                for (com.example.travelin.Post repliesItem : repliesList) {
                    Long cacheItemIndexreplies = cache.get(repliesItem);
                    if (cacheItemIndexreplies == null) {
                        cacheItemIndexreplies = com_example_travelin_PostRealmProxy.insertOrUpdate(realm, repliesItem, cache);
                    }
                    repliesOsList.addRow(cacheItemIndexreplies);
                }
            }
        }


        OsList imageURLsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.imageURLsIndex);
        imageURLsOsList.removeAll();
        RealmList<java.lang.String> imageURLsList = ((com_example_travelin_PostRealmProxyInterface) object).realmGet$imageURLs();
        if (imageURLsList != null) {
            for (java.lang.String imageURLsItem : imageURLsList) {
                if (imageURLsItem == null) {
                    imageURLsOsList.addNull();
                } else {
                    imageURLsOsList.addString(imageURLsItem);
                }
            }
        }

        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.travelin.Post.class);
        long tableNativePtr = table.getNativePtr();
        PostColumnInfo columnInfo = (PostColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.Post.class);
        com.example.travelin.Post object = null;
        while (objects.hasNext()) {
            object = (com.example.travelin.Post) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = OsObject.createRow(table);
            cache.put(object, rowIndex);
            String realmGet$type = ((com_example_travelin_PostRealmProxyInterface) object).realmGet$type();
            if (realmGet$type != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.typeIndex, rowIndex, realmGet$type, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.typeIndex, rowIndex, false);
            }

            com.example.travelin.User authorObj = ((com_example_travelin_PostRealmProxyInterface) object).realmGet$author();
            if (authorObj != null) {
                Long cacheauthor = cache.get(authorObj);
                if (cacheauthor == null) {
                    cacheauthor = com_example_travelin_UserRealmProxy.insertOrUpdate(realm, authorObj, cache);
                }
                Table.nativeSetLink(tableNativePtr, columnInfo.authorIndex, rowIndex, cacheauthor, false);
            } else {
                Table.nativeNullifyLink(tableNativePtr, columnInfo.authorIndex, rowIndex);
            }
            java.util.Date realmGet$datePosted = ((com_example_travelin_PostRealmProxyInterface) object).realmGet$datePosted();
            if (realmGet$datePosted != null) {
                Table.nativeSetTimestamp(tableNativePtr, columnInfo.datePostedIndex, rowIndex, realmGet$datePosted.getTime(), false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.datePostedIndex, rowIndex, false);
            }
            String realmGet$body = ((com_example_travelin_PostRealmProxyInterface) object).realmGet$body();
            if (realmGet$body != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.bodyIndex, rowIndex, realmGet$body, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.bodyIndex, rowIndex, false);
            }
            Table.nativeSetLong(tableNativePtr, columnInfo.rateUpIndex, rowIndex, ((com_example_travelin_PostRealmProxyInterface) object).realmGet$rateUp(), false);
            Table.nativeSetLong(tableNativePtr, columnInfo.rateDownIndex, rowIndex, ((com_example_travelin_PostRealmProxyInterface) object).realmGet$rateDown(), false);

            OsList repliesOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.repliesIndex);
            RealmList<com.example.travelin.Post> repliesList = ((com_example_travelin_PostRealmProxyInterface) object).realmGet$replies();
            if (repliesList != null && repliesList.size() == repliesOsList.size()) {
                // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
                int objectCount = repliesList.size();
                for (int i = 0; i < objectCount; i++) {
                    com.example.travelin.Post repliesItem = repliesList.get(i);
                    Long cacheItemIndexreplies = cache.get(repliesItem);
                    if (cacheItemIndexreplies == null) {
                        cacheItemIndexreplies = com_example_travelin_PostRealmProxy.insertOrUpdate(realm, repliesItem, cache);
                    }
                    repliesOsList.setRow(i, cacheItemIndexreplies);
                }
            } else {
                repliesOsList.removeAll();
                if (repliesList != null) {
                    for (com.example.travelin.Post repliesItem : repliesList) {
                        Long cacheItemIndexreplies = cache.get(repliesItem);
                        if (cacheItemIndexreplies == null) {
                            cacheItemIndexreplies = com_example_travelin_PostRealmProxy.insertOrUpdate(realm, repliesItem, cache);
                        }
                        repliesOsList.addRow(cacheItemIndexreplies);
                    }
                }
            }


            OsList imageURLsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.imageURLsIndex);
            imageURLsOsList.removeAll();
            RealmList<java.lang.String> imageURLsList = ((com_example_travelin_PostRealmProxyInterface) object).realmGet$imageURLs();
            if (imageURLsList != null) {
                for (java.lang.String imageURLsItem : imageURLsList) {
                    if (imageURLsItem == null) {
                        imageURLsOsList.addNull();
                    } else {
                        imageURLsOsList.addString(imageURLsItem);
                    }
                }
            }

        }
    }

    public static com.example.travelin.Post createDetachedCopy(com.example.travelin.Post realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.example.travelin.Post unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.example.travelin.Post();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.example.travelin.Post) cachedObject.object;
            }
            unmanagedObject = (com.example.travelin.Post) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_example_travelin_PostRealmProxyInterface unmanagedCopy = (com_example_travelin_PostRealmProxyInterface) unmanagedObject;
        com_example_travelin_PostRealmProxyInterface realmSource = (com_example_travelin_PostRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$type(realmSource.realmGet$type());

        // Deep copy of author
        unmanagedCopy.realmSet$author(com_example_travelin_UserRealmProxy.createDetachedCopy(realmSource.realmGet$author(), currentDepth + 1, maxDepth, cache));
        unmanagedCopy.realmSet$datePosted(realmSource.realmGet$datePosted());
        unmanagedCopy.realmSet$body(realmSource.realmGet$body());
        unmanagedCopy.realmSet$rateUp(realmSource.realmGet$rateUp());
        unmanagedCopy.realmSet$rateDown(realmSource.realmGet$rateDown());

        // Deep copy of replies
        if (currentDepth == maxDepth) {
            unmanagedCopy.realmSet$replies(null);
        } else {
            RealmList<com.example.travelin.Post> managedrepliesList = realmSource.realmGet$replies();
            RealmList<com.example.travelin.Post> unmanagedrepliesList = new RealmList<com.example.travelin.Post>();
            unmanagedCopy.realmSet$replies(unmanagedrepliesList);
            int nextDepth = currentDepth + 1;
            int size = managedrepliesList.size();
            for (int i = 0; i < size; i++) {
                com.example.travelin.Post item = com_example_travelin_PostRealmProxy.createDetachedCopy(managedrepliesList.get(i), nextDepth, maxDepth, cache);
                unmanagedrepliesList.add(item);
            }
        }

        unmanagedCopy.realmSet$imageURLs(new RealmList<java.lang.String>());
        unmanagedCopy.realmGet$imageURLs().addAll(realmSource.realmGet$imageURLs());

        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Post = proxy[");
        stringBuilder.append("{type:");
        stringBuilder.append(realmGet$type() != null ? realmGet$type() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{author:");
        stringBuilder.append(realmGet$author() != null ? "User" : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{datePosted:");
        stringBuilder.append(realmGet$datePosted() != null ? realmGet$datePosted() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{body:");
        stringBuilder.append(realmGet$body() != null ? realmGet$body() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{rateUp:");
        stringBuilder.append(realmGet$rateUp());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{rateDown:");
        stringBuilder.append(realmGet$rateDown());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{replies:");
        stringBuilder.append("RealmList<Post>[").append(realmGet$replies().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{imageURLs:");
        stringBuilder.append("RealmList<String>[").append(realmGet$imageURLs().size()).append("]");
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
        com_example_travelin_PostRealmProxy aPost = (com_example_travelin_PostRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aPost.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aPost.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aPost.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
