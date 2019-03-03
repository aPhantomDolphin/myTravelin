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
public class com_example_travelin_DirectMessageRealmProxy extends com.example.travelin.DirectMessage
    implements RealmObjectProxy, com_example_travelin_DirectMessageRealmProxyInterface {

    static final class DirectMessageColumnInfo extends ColumnInfo {
        long maxColumnIndexValue;
        long recipientIndex;
        long bodyIndex;
        long authorIndex;
        long dateSentIndex;

        DirectMessageColumnInfo(OsSchemaInfo schemaInfo) {
            super(4);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("DirectMessage");
            this.recipientIndex = addColumnDetails("recipient", "recipient", objectSchemaInfo);
            this.bodyIndex = addColumnDetails("body", "body", objectSchemaInfo);
            this.authorIndex = addColumnDetails("author", "author", objectSchemaInfo);
            this.dateSentIndex = addColumnDetails("dateSent", "dateSent", objectSchemaInfo);
            this.maxColumnIndexValue = objectSchemaInfo.getMaxColumnIndex();
        }

        DirectMessageColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new DirectMessageColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final DirectMessageColumnInfo src = (DirectMessageColumnInfo) rawSrc;
            final DirectMessageColumnInfo dst = (DirectMessageColumnInfo) rawDst;
            dst.recipientIndex = src.recipientIndex;
            dst.bodyIndex = src.bodyIndex;
            dst.authorIndex = src.authorIndex;
            dst.dateSentIndex = src.dateSentIndex;
            dst.maxColumnIndexValue = src.maxColumnIndexValue;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private DirectMessageColumnInfo columnInfo;
    private ProxyState<com.example.travelin.DirectMessage> proxyState;

    com_example_travelin_DirectMessageRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (DirectMessageColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.example.travelin.DirectMessage>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    public com.example.travelin.User realmGet$recipient() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNullLink(columnInfo.recipientIndex)) {
            return null;
        }
        return proxyState.getRealm$realm().get(com.example.travelin.User.class, proxyState.getRow$realm().getLink(columnInfo.recipientIndex), false, Collections.<String>emptyList());
    }

    @Override
    public void realmSet$recipient(com.example.travelin.User value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("recipient")) {
                return;
            }
            if (value != null && !RealmObject.isManaged(value)) {
                value = ((Realm) proxyState.getRealm$realm()).copyToRealm(value);
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                // Table#nullifyLink() does not support default value. Just using Row.
                row.nullifyLink(columnInfo.recipientIndex);
                return;
            }
            proxyState.checkValidObject(value);
            row.getTable().setLink(columnInfo.recipientIndex, row.getIndex(), ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex(), true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().nullifyLink(columnInfo.recipientIndex);
            return;
        }
        proxyState.checkValidObject(value);
        proxyState.getRow$realm().setLink(columnInfo.recipientIndex, ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex());
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
    public Date realmGet$dateSent() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.dateSentIndex)) {
            return null;
        }
        return (java.util.Date) proxyState.getRow$realm().getDate(columnInfo.dateSentIndex);
    }

    @Override
    public void realmSet$dateSent(Date value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.dateSentIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setDate(columnInfo.dateSentIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.dateSentIndex);
            return;
        }
        proxyState.getRow$realm().setDate(columnInfo.dateSentIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("DirectMessage", 4, 0);
        builder.addPersistedLinkProperty("recipient", RealmFieldType.OBJECT, "User");
        builder.addPersistedProperty("body", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedLinkProperty("author", RealmFieldType.OBJECT, "User");
        builder.addPersistedProperty("dateSent", RealmFieldType.DATE, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static DirectMessageColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new DirectMessageColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "DirectMessage";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "DirectMessage";
    }

    @SuppressWarnings("cast")
    public static com.example.travelin.DirectMessage createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(2);
        if (json.has("recipient")) {
            excludeFields.add("recipient");
        }
        if (json.has("author")) {
            excludeFields.add("author");
        }
        com.example.travelin.DirectMessage obj = realm.createObjectInternal(com.example.travelin.DirectMessage.class, true, excludeFields);

        final com_example_travelin_DirectMessageRealmProxyInterface objProxy = (com_example_travelin_DirectMessageRealmProxyInterface) obj;
        if (json.has("recipient")) {
            if (json.isNull("recipient")) {
                objProxy.realmSet$recipient(null);
            } else {
                com.example.travelin.User recipientObj = com_example_travelin_UserRealmProxy.createOrUpdateUsingJsonObject(realm, json.getJSONObject("recipient"), update);
                objProxy.realmSet$recipient(recipientObj);
            }
        }
        if (json.has("body")) {
            if (json.isNull("body")) {
                objProxy.realmSet$body(null);
            } else {
                objProxy.realmSet$body((String) json.getString("body"));
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
        if (json.has("dateSent")) {
            if (json.isNull("dateSent")) {
                objProxy.realmSet$dateSent(null);
            } else {
                Object timestamp = json.get("dateSent");
                if (timestamp instanceof String) {
                    objProxy.realmSet$dateSent(JsonUtils.stringToDate((String) timestamp));
                } else {
                    objProxy.realmSet$dateSent(new Date(json.getLong("dateSent")));
                }
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.example.travelin.DirectMessage createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        final com.example.travelin.DirectMessage obj = new com.example.travelin.DirectMessage();
        final com_example_travelin_DirectMessageRealmProxyInterface objProxy = (com_example_travelin_DirectMessageRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("recipient")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$recipient(null);
                } else {
                    com.example.travelin.User recipientObj = com_example_travelin_UserRealmProxy.createUsingJsonStream(realm, reader);
                    objProxy.realmSet$recipient(recipientObj);
                }
            } else if (name.equals("body")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$body((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$body(null);
                }
            } else if (name.equals("author")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$author(null);
                } else {
                    com.example.travelin.User authorObj = com_example_travelin_UserRealmProxy.createUsingJsonStream(realm, reader);
                    objProxy.realmSet$author(authorObj);
                }
            } else if (name.equals("dateSent")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$dateSent(null);
                } else if (reader.peek() == JsonToken.NUMBER) {
                    long timestamp = reader.nextLong();
                    if (timestamp > -1) {
                        objProxy.realmSet$dateSent(new Date(timestamp));
                    }
                } else {
                    objProxy.realmSet$dateSent(JsonUtils.stringToDate(reader.nextString()));
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return realm.copyToRealm(obj);
    }

    private static com_example_travelin_DirectMessageRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating uexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.example.travelin.DirectMessage.class), false, Collections.<String>emptyList());
        io.realm.com_example_travelin_DirectMessageRealmProxy obj = new io.realm.com_example_travelin_DirectMessageRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.example.travelin.DirectMessage copyOrUpdate(Realm realm, DirectMessageColumnInfo columnInfo, com.example.travelin.DirectMessage object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.example.travelin.DirectMessage) cachedRealmObject;
        }

        return copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.example.travelin.DirectMessage copy(Realm realm, DirectMessageColumnInfo columnInfo, com.example.travelin.DirectMessage newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.example.travelin.DirectMessage) cachedRealmObject;
        }

        com_example_travelin_DirectMessageRealmProxyInterface realmObjectSource = (com_example_travelin_DirectMessageRealmProxyInterface) newObject;

        Table table = realm.getTable(com.example.travelin.DirectMessage.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, columnInfo.maxColumnIndexValue, flags);

        // Add all non-"object reference" fields
        builder.addString(columnInfo.bodyIndex, realmObjectSource.realmGet$body());
        builder.addDate(columnInfo.dateSentIndex, realmObjectSource.realmGet$dateSent());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_example_travelin_DirectMessageRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        // Finally add all fields that reference other Realm Objects, either directly or through a list
        com.example.travelin.User recipientObj = realmObjectSource.realmGet$recipient();
        if (recipientObj == null) {
            realmObjectCopy.realmSet$recipient(null);
        } else {
            com.example.travelin.User cacherecipient = (com.example.travelin.User) cache.get(recipientObj);
            if (cacherecipient != null) {
                realmObjectCopy.realmSet$recipient(cacherecipient);
            } else {
                realmObjectCopy.realmSet$recipient(com_example_travelin_UserRealmProxy.copyOrUpdate(realm, (com_example_travelin_UserRealmProxy.UserColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.User.class), recipientObj, update, cache, flags));
            }
        }

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

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.example.travelin.DirectMessage object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.example.travelin.DirectMessage.class);
        long tableNativePtr = table.getNativePtr();
        DirectMessageColumnInfo columnInfo = (DirectMessageColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.DirectMessage.class);
        long rowIndex = OsObject.createRow(table);
        cache.put(object, rowIndex);

        com.example.travelin.User recipientObj = ((com_example_travelin_DirectMessageRealmProxyInterface) object).realmGet$recipient();
        if (recipientObj != null) {
            Long cacherecipient = cache.get(recipientObj);
            if (cacherecipient == null) {
                cacherecipient = com_example_travelin_UserRealmProxy.insert(realm, recipientObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.recipientIndex, rowIndex, cacherecipient, false);
        }
        String realmGet$body = ((com_example_travelin_DirectMessageRealmProxyInterface) object).realmGet$body();
        if (realmGet$body != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.bodyIndex, rowIndex, realmGet$body, false);
        }

        com.example.travelin.User authorObj = ((com_example_travelin_DirectMessageRealmProxyInterface) object).realmGet$author();
        if (authorObj != null) {
            Long cacheauthor = cache.get(authorObj);
            if (cacheauthor == null) {
                cacheauthor = com_example_travelin_UserRealmProxy.insert(realm, authorObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.authorIndex, rowIndex, cacheauthor, false);
        }
        java.util.Date realmGet$dateSent = ((com_example_travelin_DirectMessageRealmProxyInterface) object).realmGet$dateSent();
        if (realmGet$dateSent != null) {
            Table.nativeSetTimestamp(tableNativePtr, columnInfo.dateSentIndex, rowIndex, realmGet$dateSent.getTime(), false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.travelin.DirectMessage.class);
        long tableNativePtr = table.getNativePtr();
        DirectMessageColumnInfo columnInfo = (DirectMessageColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.DirectMessage.class);
        com.example.travelin.DirectMessage object = null;
        while (objects.hasNext()) {
            object = (com.example.travelin.DirectMessage) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = OsObject.createRow(table);
            cache.put(object, rowIndex);

            com.example.travelin.User recipientObj = ((com_example_travelin_DirectMessageRealmProxyInterface) object).realmGet$recipient();
            if (recipientObj != null) {
                Long cacherecipient = cache.get(recipientObj);
                if (cacherecipient == null) {
                    cacherecipient = com_example_travelin_UserRealmProxy.insert(realm, recipientObj, cache);
                }
                table.setLink(columnInfo.recipientIndex, rowIndex, cacherecipient, false);
            }
            String realmGet$body = ((com_example_travelin_DirectMessageRealmProxyInterface) object).realmGet$body();
            if (realmGet$body != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.bodyIndex, rowIndex, realmGet$body, false);
            }

            com.example.travelin.User authorObj = ((com_example_travelin_DirectMessageRealmProxyInterface) object).realmGet$author();
            if (authorObj != null) {
                Long cacheauthor = cache.get(authorObj);
                if (cacheauthor == null) {
                    cacheauthor = com_example_travelin_UserRealmProxy.insert(realm, authorObj, cache);
                }
                table.setLink(columnInfo.authorIndex, rowIndex, cacheauthor, false);
            }
            java.util.Date realmGet$dateSent = ((com_example_travelin_DirectMessageRealmProxyInterface) object).realmGet$dateSent();
            if (realmGet$dateSent != null) {
                Table.nativeSetTimestamp(tableNativePtr, columnInfo.dateSentIndex, rowIndex, realmGet$dateSent.getTime(), false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.example.travelin.DirectMessage object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.example.travelin.DirectMessage.class);
        long tableNativePtr = table.getNativePtr();
        DirectMessageColumnInfo columnInfo = (DirectMessageColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.DirectMessage.class);
        long rowIndex = OsObject.createRow(table);
        cache.put(object, rowIndex);

        com.example.travelin.User recipientObj = ((com_example_travelin_DirectMessageRealmProxyInterface) object).realmGet$recipient();
        if (recipientObj != null) {
            Long cacherecipient = cache.get(recipientObj);
            if (cacherecipient == null) {
                cacherecipient = com_example_travelin_UserRealmProxy.insertOrUpdate(realm, recipientObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.recipientIndex, rowIndex, cacherecipient, false);
        } else {
            Table.nativeNullifyLink(tableNativePtr, columnInfo.recipientIndex, rowIndex);
        }
        String realmGet$body = ((com_example_travelin_DirectMessageRealmProxyInterface) object).realmGet$body();
        if (realmGet$body != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.bodyIndex, rowIndex, realmGet$body, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.bodyIndex, rowIndex, false);
        }

        com.example.travelin.User authorObj = ((com_example_travelin_DirectMessageRealmProxyInterface) object).realmGet$author();
        if (authorObj != null) {
            Long cacheauthor = cache.get(authorObj);
            if (cacheauthor == null) {
                cacheauthor = com_example_travelin_UserRealmProxy.insertOrUpdate(realm, authorObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.authorIndex, rowIndex, cacheauthor, false);
        } else {
            Table.nativeNullifyLink(tableNativePtr, columnInfo.authorIndex, rowIndex);
        }
        java.util.Date realmGet$dateSent = ((com_example_travelin_DirectMessageRealmProxyInterface) object).realmGet$dateSent();
        if (realmGet$dateSent != null) {
            Table.nativeSetTimestamp(tableNativePtr, columnInfo.dateSentIndex, rowIndex, realmGet$dateSent.getTime(), false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.dateSentIndex, rowIndex, false);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.travelin.DirectMessage.class);
        long tableNativePtr = table.getNativePtr();
        DirectMessageColumnInfo columnInfo = (DirectMessageColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.DirectMessage.class);
        com.example.travelin.DirectMessage object = null;
        while (objects.hasNext()) {
            object = (com.example.travelin.DirectMessage) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = OsObject.createRow(table);
            cache.put(object, rowIndex);

            com.example.travelin.User recipientObj = ((com_example_travelin_DirectMessageRealmProxyInterface) object).realmGet$recipient();
            if (recipientObj != null) {
                Long cacherecipient = cache.get(recipientObj);
                if (cacherecipient == null) {
                    cacherecipient = com_example_travelin_UserRealmProxy.insertOrUpdate(realm, recipientObj, cache);
                }
                Table.nativeSetLink(tableNativePtr, columnInfo.recipientIndex, rowIndex, cacherecipient, false);
            } else {
                Table.nativeNullifyLink(tableNativePtr, columnInfo.recipientIndex, rowIndex);
            }
            String realmGet$body = ((com_example_travelin_DirectMessageRealmProxyInterface) object).realmGet$body();
            if (realmGet$body != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.bodyIndex, rowIndex, realmGet$body, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.bodyIndex, rowIndex, false);
            }

            com.example.travelin.User authorObj = ((com_example_travelin_DirectMessageRealmProxyInterface) object).realmGet$author();
            if (authorObj != null) {
                Long cacheauthor = cache.get(authorObj);
                if (cacheauthor == null) {
                    cacheauthor = com_example_travelin_UserRealmProxy.insertOrUpdate(realm, authorObj, cache);
                }
                Table.nativeSetLink(tableNativePtr, columnInfo.authorIndex, rowIndex, cacheauthor, false);
            } else {
                Table.nativeNullifyLink(tableNativePtr, columnInfo.authorIndex, rowIndex);
            }
            java.util.Date realmGet$dateSent = ((com_example_travelin_DirectMessageRealmProxyInterface) object).realmGet$dateSent();
            if (realmGet$dateSent != null) {
                Table.nativeSetTimestamp(tableNativePtr, columnInfo.dateSentIndex, rowIndex, realmGet$dateSent.getTime(), false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.dateSentIndex, rowIndex, false);
            }
        }
    }

    public static com.example.travelin.DirectMessage createDetachedCopy(com.example.travelin.DirectMessage realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.example.travelin.DirectMessage unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.example.travelin.DirectMessage();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.example.travelin.DirectMessage) cachedObject.object;
            }
            unmanagedObject = (com.example.travelin.DirectMessage) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_example_travelin_DirectMessageRealmProxyInterface unmanagedCopy = (com_example_travelin_DirectMessageRealmProxyInterface) unmanagedObject;
        com_example_travelin_DirectMessageRealmProxyInterface realmSource = (com_example_travelin_DirectMessageRealmProxyInterface) realmObject;

        // Deep copy of recipient
        unmanagedCopy.realmSet$recipient(com_example_travelin_UserRealmProxy.createDetachedCopy(realmSource.realmGet$recipient(), currentDepth + 1, maxDepth, cache));
        unmanagedCopy.realmSet$body(realmSource.realmGet$body());

        // Deep copy of author
        unmanagedCopy.realmSet$author(com_example_travelin_UserRealmProxy.createDetachedCopy(realmSource.realmGet$author(), currentDepth + 1, maxDepth, cache));
        unmanagedCopy.realmSet$dateSent(realmSource.realmGet$dateSent());

        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("DirectMessage = proxy[");
        stringBuilder.append("{recipient:");
        stringBuilder.append(realmGet$recipient() != null ? "User" : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{body:");
        stringBuilder.append(realmGet$body() != null ? realmGet$body() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{author:");
        stringBuilder.append(realmGet$author() != null ? "User" : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{dateSent:");
        stringBuilder.append(realmGet$dateSent() != null ? realmGet$dateSent() : "null");
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
        com_example_travelin_DirectMessageRealmProxy aDirectMessage = (com_example_travelin_DirectMessageRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aDirectMessage.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aDirectMessage.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aDirectMessage.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
