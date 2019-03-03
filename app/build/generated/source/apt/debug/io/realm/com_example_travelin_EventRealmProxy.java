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
public class com_example_travelin_EventRealmProxy extends com.example.travelin.Event
    implements RealmObjectProxy, com_example_travelin_EventRealmProxyInterface {

    static final class EventColumnInfo extends ColumnInfo {
        long maxColumnIndexValue;
        long locationIndex;
        long dateIndex;
        long nameIndex;
        long tagsIndex;
        long rsvpIndex;

        EventColumnInfo(OsSchemaInfo schemaInfo) {
            super(5);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("Event");
            this.locationIndex = addColumnDetails("location", "location", objectSchemaInfo);
            this.dateIndex = addColumnDetails("date", "date", objectSchemaInfo);
            this.nameIndex = addColumnDetails("name", "name", objectSchemaInfo);
            this.tagsIndex = addColumnDetails("tags", "tags", objectSchemaInfo);
            this.rsvpIndex = addColumnDetails("rsvp", "rsvp", objectSchemaInfo);
            this.maxColumnIndexValue = objectSchemaInfo.getMaxColumnIndex();
        }

        EventColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new EventColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final EventColumnInfo src = (EventColumnInfo) rawSrc;
            final EventColumnInfo dst = (EventColumnInfo) rawDst;
            dst.locationIndex = src.locationIndex;
            dst.dateIndex = src.dateIndex;
            dst.nameIndex = src.nameIndex;
            dst.tagsIndex = src.tagsIndex;
            dst.rsvpIndex = src.rsvpIndex;
            dst.maxColumnIndexValue = src.maxColumnIndexValue;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private EventColumnInfo columnInfo;
    private ProxyState<com.example.travelin.Event> proxyState;
    private RealmList<com.example.travelin.Tag> tagsRealmList;
    private RealmList<com.example.travelin.User> rsvpRealmList;

    com_example_travelin_EventRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (EventColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.example.travelin.Event>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    public com.example.travelin.EventLocation realmGet$location() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNullLink(columnInfo.locationIndex)) {
            return null;
        }
        return proxyState.getRealm$realm().get(com.example.travelin.EventLocation.class, proxyState.getRow$realm().getLink(columnInfo.locationIndex), false, Collections.<String>emptyList());
    }

    @Override
    public void realmSet$location(com.example.travelin.EventLocation value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("location")) {
                return;
            }
            if (value != null && !RealmObject.isManaged(value)) {
                value = ((Realm) proxyState.getRealm$realm()).copyToRealm(value);
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                // Table#nullifyLink() does not support default value. Just using Row.
                row.nullifyLink(columnInfo.locationIndex);
                return;
            }
            proxyState.checkValidObject(value);
            row.getTable().setLink(columnInfo.locationIndex, row.getIndex(), ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex(), true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().nullifyLink(columnInfo.locationIndex);
            return;
        }
        proxyState.checkValidObject(value);
        proxyState.getRow$realm().setLink(columnInfo.locationIndex, ((RealmObjectProxy) value).realmGet$proxyState().getRow$realm().getIndex());
    }

    @Override
    @SuppressWarnings("cast")
    public Date realmGet$date() {
        proxyState.getRealm$realm().checkIfValid();
        if (proxyState.getRow$realm().isNull(columnInfo.dateIndex)) {
            return null;
        }
        return (java.util.Date) proxyState.getRow$realm().getDate(columnInfo.dateIndex);
    }

    @Override
    public void realmSet$date(Date value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.dateIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setDate(columnInfo.dateIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.dateIndex);
            return;
        }
        proxyState.getRow$realm().setDate(columnInfo.dateIndex, value);
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
    public RealmList<com.example.travelin.Tag> realmGet$tags() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (tagsRealmList != null) {
            return tagsRealmList;
        } else {
            OsList osList = proxyState.getRow$realm().getModelList(columnInfo.tagsIndex);
            tagsRealmList = new RealmList<com.example.travelin.Tag>(com.example.travelin.Tag.class, osList, proxyState.getRealm$realm());
            return tagsRealmList;
        }
    }

    @Override
    public void realmSet$tags(RealmList<com.example.travelin.Tag> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("tags")) {
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
        OsList osList = proxyState.getRow$realm().getModelList(columnInfo.tagsIndex);
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
    public RealmList<com.example.travelin.User> realmGet$rsvp() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (rsvpRealmList != null) {
            return rsvpRealmList;
        } else {
            OsList osList = proxyState.getRow$realm().getModelList(columnInfo.rsvpIndex);
            rsvpRealmList = new RealmList<com.example.travelin.User>(com.example.travelin.User.class, osList, proxyState.getRealm$realm());
            return rsvpRealmList;
        }
    }

    @Override
    public void realmSet$rsvp(RealmList<com.example.travelin.User> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("rsvp")) {
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
        OsList osList = proxyState.getRow$realm().getModelList(columnInfo.rsvpIndex);
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

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("Event", 5, 0);
        builder.addPersistedLinkProperty("location", RealmFieldType.OBJECT, "EventLocation");
        builder.addPersistedProperty("date", RealmFieldType.DATE, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedProperty("name", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedLinkProperty("tags", RealmFieldType.LIST, "Tag");
        builder.addPersistedLinkProperty("rsvp", RealmFieldType.LIST, "User");
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static EventColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new EventColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "Event";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "Event";
    }

    @SuppressWarnings("cast")
    public static com.example.travelin.Event createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(3);
        if (json.has("location")) {
            excludeFields.add("location");
        }
        if (json.has("tags")) {
            excludeFields.add("tags");
        }
        if (json.has("rsvp")) {
            excludeFields.add("rsvp");
        }
        com.example.travelin.Event obj = realm.createObjectInternal(com.example.travelin.Event.class, true, excludeFields);

        final com_example_travelin_EventRealmProxyInterface objProxy = (com_example_travelin_EventRealmProxyInterface) obj;
        if (json.has("location")) {
            if (json.isNull("location")) {
                objProxy.realmSet$location(null);
            } else {
                com.example.travelin.EventLocation locationObj = com_example_travelin_EventLocationRealmProxy.createOrUpdateUsingJsonObject(realm, json.getJSONObject("location"), update);
                objProxy.realmSet$location(locationObj);
            }
        }
        if (json.has("date")) {
            if (json.isNull("date")) {
                objProxy.realmSet$date(null);
            } else {
                Object timestamp = json.get("date");
                if (timestamp instanceof String) {
                    objProxy.realmSet$date(JsonUtils.stringToDate((String) timestamp));
                } else {
                    objProxy.realmSet$date(new Date(json.getLong("date")));
                }
            }
        }
        if (json.has("name")) {
            if (json.isNull("name")) {
                objProxy.realmSet$name(null);
            } else {
                objProxy.realmSet$name((String) json.getString("name"));
            }
        }
        if (json.has("tags")) {
            if (json.isNull("tags")) {
                objProxy.realmSet$tags(null);
            } else {
                objProxy.realmGet$tags().clear();
                JSONArray array = json.getJSONArray("tags");
                for (int i = 0; i < array.length(); i++) {
                    com.example.travelin.Tag item = com_example_travelin_TagRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    objProxy.realmGet$tags().add(item);
                }
            }
        }
        if (json.has("rsvp")) {
            if (json.isNull("rsvp")) {
                objProxy.realmSet$rsvp(null);
            } else {
                objProxy.realmGet$rsvp().clear();
                JSONArray array = json.getJSONArray("rsvp");
                for (int i = 0; i < array.length(); i++) {
                    com.example.travelin.User item = com_example_travelin_UserRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    objProxy.realmGet$rsvp().add(item);
                }
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.example.travelin.Event createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        final com.example.travelin.Event obj = new com.example.travelin.Event();
        final com_example_travelin_EventRealmProxyInterface objProxy = (com_example_travelin_EventRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("location")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$location(null);
                } else {
                    com.example.travelin.EventLocation locationObj = com_example_travelin_EventLocationRealmProxy.createUsingJsonStream(realm, reader);
                    objProxy.realmSet$location(locationObj);
                }
            } else if (name.equals("date")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$date(null);
                } else if (reader.peek() == JsonToken.NUMBER) {
                    long timestamp = reader.nextLong();
                    if (timestamp > -1) {
                        objProxy.realmSet$date(new Date(timestamp));
                    }
                } else {
                    objProxy.realmSet$date(JsonUtils.stringToDate(reader.nextString()));
                }
            } else if (name.equals("name")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$name((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$name(null);
                }
            } else if (name.equals("tags")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$tags(null);
                } else {
                    objProxy.realmSet$tags(new RealmList<com.example.travelin.Tag>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.example.travelin.Tag item = com_example_travelin_TagRealmProxy.createUsingJsonStream(realm, reader);
                        objProxy.realmGet$tags().add(item);
                    }
                    reader.endArray();
                }
            } else if (name.equals("rsvp")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$rsvp(null);
                } else {
                    objProxy.realmSet$rsvp(new RealmList<com.example.travelin.User>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.example.travelin.User item = com_example_travelin_UserRealmProxy.createUsingJsonStream(realm, reader);
                        objProxy.realmGet$rsvp().add(item);
                    }
                    reader.endArray();
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return realm.copyToRealm(obj);
    }

    private static com_example_travelin_EventRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating uexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.example.travelin.Event.class), false, Collections.<String>emptyList());
        io.realm.com_example_travelin_EventRealmProxy obj = new io.realm.com_example_travelin_EventRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.example.travelin.Event copyOrUpdate(Realm realm, EventColumnInfo columnInfo, com.example.travelin.Event object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.example.travelin.Event) cachedRealmObject;
        }

        return copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.example.travelin.Event copy(Realm realm, EventColumnInfo columnInfo, com.example.travelin.Event newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.example.travelin.Event) cachedRealmObject;
        }

        com_example_travelin_EventRealmProxyInterface realmObjectSource = (com_example_travelin_EventRealmProxyInterface) newObject;

        Table table = realm.getTable(com.example.travelin.Event.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, columnInfo.maxColumnIndexValue, flags);

        // Add all non-"object reference" fields
        builder.addDate(columnInfo.dateIndex, realmObjectSource.realmGet$date());
        builder.addString(columnInfo.nameIndex, realmObjectSource.realmGet$name());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_example_travelin_EventRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        // Finally add all fields that reference other Realm Objects, either directly or through a list
        com.example.travelin.EventLocation locationObj = realmObjectSource.realmGet$location();
        if (locationObj == null) {
            realmObjectCopy.realmSet$location(null);
        } else {
            com.example.travelin.EventLocation cachelocation = (com.example.travelin.EventLocation) cache.get(locationObj);
            if (cachelocation != null) {
                realmObjectCopy.realmSet$location(cachelocation);
            } else {
                realmObjectCopy.realmSet$location(com_example_travelin_EventLocationRealmProxy.copyOrUpdate(realm, (com_example_travelin_EventLocationRealmProxy.EventLocationColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.EventLocation.class), locationObj, update, cache, flags));
            }
        }

        RealmList<com.example.travelin.Tag> tagsList = realmObjectSource.realmGet$tags();
        if (tagsList != null) {
            RealmList<com.example.travelin.Tag> tagsRealmList = realmObjectCopy.realmGet$tags();
            tagsRealmList.clear();
            for (int i = 0; i < tagsList.size(); i++) {
                com.example.travelin.Tag tagsItem = tagsList.get(i);
                com.example.travelin.Tag cachetags = (com.example.travelin.Tag) cache.get(tagsItem);
                if (cachetags != null) {
                    tagsRealmList.add(cachetags);
                } else {
                    tagsRealmList.add(com_example_travelin_TagRealmProxy.copyOrUpdate(realm, (com_example_travelin_TagRealmProxy.TagColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.Tag.class), tagsItem, update, cache, flags));
                }
            }
        }

        RealmList<com.example.travelin.User> rsvpList = realmObjectSource.realmGet$rsvp();
        if (rsvpList != null) {
            RealmList<com.example.travelin.User> rsvpRealmList = realmObjectCopy.realmGet$rsvp();
            rsvpRealmList.clear();
            for (int i = 0; i < rsvpList.size(); i++) {
                com.example.travelin.User rsvpItem = rsvpList.get(i);
                com.example.travelin.User cachersvp = (com.example.travelin.User) cache.get(rsvpItem);
                if (cachersvp != null) {
                    rsvpRealmList.add(cachersvp);
                } else {
                    rsvpRealmList.add(com_example_travelin_UserRealmProxy.copyOrUpdate(realm, (com_example_travelin_UserRealmProxy.UserColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.User.class), rsvpItem, update, cache, flags));
                }
            }
        }

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.example.travelin.Event object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.example.travelin.Event.class);
        long tableNativePtr = table.getNativePtr();
        EventColumnInfo columnInfo = (EventColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.Event.class);
        long rowIndex = OsObject.createRow(table);
        cache.put(object, rowIndex);

        com.example.travelin.EventLocation locationObj = ((com_example_travelin_EventRealmProxyInterface) object).realmGet$location();
        if (locationObj != null) {
            Long cachelocation = cache.get(locationObj);
            if (cachelocation == null) {
                cachelocation = com_example_travelin_EventLocationRealmProxy.insert(realm, locationObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.locationIndex, rowIndex, cachelocation, false);
        }
        java.util.Date realmGet$date = ((com_example_travelin_EventRealmProxyInterface) object).realmGet$date();
        if (realmGet$date != null) {
            Table.nativeSetTimestamp(tableNativePtr, columnInfo.dateIndex, rowIndex, realmGet$date.getTime(), false);
        }
        String realmGet$name = ((com_example_travelin_EventRealmProxyInterface) object).realmGet$name();
        if (realmGet$name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
        }

        RealmList<com.example.travelin.Tag> tagsList = ((com_example_travelin_EventRealmProxyInterface) object).realmGet$tags();
        if (tagsList != null) {
            OsList tagsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.tagsIndex);
            for (com.example.travelin.Tag tagsItem : tagsList) {
                Long cacheItemIndextags = cache.get(tagsItem);
                if (cacheItemIndextags == null) {
                    cacheItemIndextags = com_example_travelin_TagRealmProxy.insert(realm, tagsItem, cache);
                }
                tagsOsList.addRow(cacheItemIndextags);
            }
        }

        RealmList<com.example.travelin.User> rsvpList = ((com_example_travelin_EventRealmProxyInterface) object).realmGet$rsvp();
        if (rsvpList != null) {
            OsList rsvpOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.rsvpIndex);
            for (com.example.travelin.User rsvpItem : rsvpList) {
                Long cacheItemIndexrsvp = cache.get(rsvpItem);
                if (cacheItemIndexrsvp == null) {
                    cacheItemIndexrsvp = com_example_travelin_UserRealmProxy.insert(realm, rsvpItem, cache);
                }
                rsvpOsList.addRow(cacheItemIndexrsvp);
            }
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.travelin.Event.class);
        long tableNativePtr = table.getNativePtr();
        EventColumnInfo columnInfo = (EventColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.Event.class);
        com.example.travelin.Event object = null;
        while (objects.hasNext()) {
            object = (com.example.travelin.Event) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = OsObject.createRow(table);
            cache.put(object, rowIndex);

            com.example.travelin.EventLocation locationObj = ((com_example_travelin_EventRealmProxyInterface) object).realmGet$location();
            if (locationObj != null) {
                Long cachelocation = cache.get(locationObj);
                if (cachelocation == null) {
                    cachelocation = com_example_travelin_EventLocationRealmProxy.insert(realm, locationObj, cache);
                }
                table.setLink(columnInfo.locationIndex, rowIndex, cachelocation, false);
            }
            java.util.Date realmGet$date = ((com_example_travelin_EventRealmProxyInterface) object).realmGet$date();
            if (realmGet$date != null) {
                Table.nativeSetTimestamp(tableNativePtr, columnInfo.dateIndex, rowIndex, realmGet$date.getTime(), false);
            }
            String realmGet$name = ((com_example_travelin_EventRealmProxyInterface) object).realmGet$name();
            if (realmGet$name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
            }

            RealmList<com.example.travelin.Tag> tagsList = ((com_example_travelin_EventRealmProxyInterface) object).realmGet$tags();
            if (tagsList != null) {
                OsList tagsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.tagsIndex);
                for (com.example.travelin.Tag tagsItem : tagsList) {
                    Long cacheItemIndextags = cache.get(tagsItem);
                    if (cacheItemIndextags == null) {
                        cacheItemIndextags = com_example_travelin_TagRealmProxy.insert(realm, tagsItem, cache);
                    }
                    tagsOsList.addRow(cacheItemIndextags);
                }
            }

            RealmList<com.example.travelin.User> rsvpList = ((com_example_travelin_EventRealmProxyInterface) object).realmGet$rsvp();
            if (rsvpList != null) {
                OsList rsvpOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.rsvpIndex);
                for (com.example.travelin.User rsvpItem : rsvpList) {
                    Long cacheItemIndexrsvp = cache.get(rsvpItem);
                    if (cacheItemIndexrsvp == null) {
                        cacheItemIndexrsvp = com_example_travelin_UserRealmProxy.insert(realm, rsvpItem, cache);
                    }
                    rsvpOsList.addRow(cacheItemIndexrsvp);
                }
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.example.travelin.Event object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.example.travelin.Event.class);
        long tableNativePtr = table.getNativePtr();
        EventColumnInfo columnInfo = (EventColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.Event.class);
        long rowIndex = OsObject.createRow(table);
        cache.put(object, rowIndex);

        com.example.travelin.EventLocation locationObj = ((com_example_travelin_EventRealmProxyInterface) object).realmGet$location();
        if (locationObj != null) {
            Long cachelocation = cache.get(locationObj);
            if (cachelocation == null) {
                cachelocation = com_example_travelin_EventLocationRealmProxy.insertOrUpdate(realm, locationObj, cache);
            }
            Table.nativeSetLink(tableNativePtr, columnInfo.locationIndex, rowIndex, cachelocation, false);
        } else {
            Table.nativeNullifyLink(tableNativePtr, columnInfo.locationIndex, rowIndex);
        }
        java.util.Date realmGet$date = ((com_example_travelin_EventRealmProxyInterface) object).realmGet$date();
        if (realmGet$date != null) {
            Table.nativeSetTimestamp(tableNativePtr, columnInfo.dateIndex, rowIndex, realmGet$date.getTime(), false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.dateIndex, rowIndex, false);
        }
        String realmGet$name = ((com_example_travelin_EventRealmProxyInterface) object).realmGet$name();
        if (realmGet$name != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.nameIndex, rowIndex, false);
        }

        OsList tagsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.tagsIndex);
        RealmList<com.example.travelin.Tag> tagsList = ((com_example_travelin_EventRealmProxyInterface) object).realmGet$tags();
        if (tagsList != null && tagsList.size() == tagsOsList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = tagsList.size();
            for (int i = 0; i < objects; i++) {
                com.example.travelin.Tag tagsItem = tagsList.get(i);
                Long cacheItemIndextags = cache.get(tagsItem);
                if (cacheItemIndextags == null) {
                    cacheItemIndextags = com_example_travelin_TagRealmProxy.insertOrUpdate(realm, tagsItem, cache);
                }
                tagsOsList.setRow(i, cacheItemIndextags);
            }
        } else {
            tagsOsList.removeAll();
            if (tagsList != null) {
                for (com.example.travelin.Tag tagsItem : tagsList) {
                    Long cacheItemIndextags = cache.get(tagsItem);
                    if (cacheItemIndextags == null) {
                        cacheItemIndextags = com_example_travelin_TagRealmProxy.insertOrUpdate(realm, tagsItem, cache);
                    }
                    tagsOsList.addRow(cacheItemIndextags);
                }
            }
        }


        OsList rsvpOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.rsvpIndex);
        RealmList<com.example.travelin.User> rsvpList = ((com_example_travelin_EventRealmProxyInterface) object).realmGet$rsvp();
        if (rsvpList != null && rsvpList.size() == rsvpOsList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = rsvpList.size();
            for (int i = 0; i < objects; i++) {
                com.example.travelin.User rsvpItem = rsvpList.get(i);
                Long cacheItemIndexrsvp = cache.get(rsvpItem);
                if (cacheItemIndexrsvp == null) {
                    cacheItemIndexrsvp = com_example_travelin_UserRealmProxy.insertOrUpdate(realm, rsvpItem, cache);
                }
                rsvpOsList.setRow(i, cacheItemIndexrsvp);
            }
        } else {
            rsvpOsList.removeAll();
            if (rsvpList != null) {
                for (com.example.travelin.User rsvpItem : rsvpList) {
                    Long cacheItemIndexrsvp = cache.get(rsvpItem);
                    if (cacheItemIndexrsvp == null) {
                        cacheItemIndexrsvp = com_example_travelin_UserRealmProxy.insertOrUpdate(realm, rsvpItem, cache);
                    }
                    rsvpOsList.addRow(cacheItemIndexrsvp);
                }
            }
        }

        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.travelin.Event.class);
        long tableNativePtr = table.getNativePtr();
        EventColumnInfo columnInfo = (EventColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.Event.class);
        com.example.travelin.Event object = null;
        while (objects.hasNext()) {
            object = (com.example.travelin.Event) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = OsObject.createRow(table);
            cache.put(object, rowIndex);

            com.example.travelin.EventLocation locationObj = ((com_example_travelin_EventRealmProxyInterface) object).realmGet$location();
            if (locationObj != null) {
                Long cachelocation = cache.get(locationObj);
                if (cachelocation == null) {
                    cachelocation = com_example_travelin_EventLocationRealmProxy.insertOrUpdate(realm, locationObj, cache);
                }
                Table.nativeSetLink(tableNativePtr, columnInfo.locationIndex, rowIndex, cachelocation, false);
            } else {
                Table.nativeNullifyLink(tableNativePtr, columnInfo.locationIndex, rowIndex);
            }
            java.util.Date realmGet$date = ((com_example_travelin_EventRealmProxyInterface) object).realmGet$date();
            if (realmGet$date != null) {
                Table.nativeSetTimestamp(tableNativePtr, columnInfo.dateIndex, rowIndex, realmGet$date.getTime(), false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.dateIndex, rowIndex, false);
            }
            String realmGet$name = ((com_example_travelin_EventRealmProxyInterface) object).realmGet$name();
            if (realmGet$name != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.nameIndex, rowIndex, realmGet$name, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.nameIndex, rowIndex, false);
            }

            OsList tagsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.tagsIndex);
            RealmList<com.example.travelin.Tag> tagsList = ((com_example_travelin_EventRealmProxyInterface) object).realmGet$tags();
            if (tagsList != null && tagsList.size() == tagsOsList.size()) {
                // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
                int objectCount = tagsList.size();
                for (int i = 0; i < objectCount; i++) {
                    com.example.travelin.Tag tagsItem = tagsList.get(i);
                    Long cacheItemIndextags = cache.get(tagsItem);
                    if (cacheItemIndextags == null) {
                        cacheItemIndextags = com_example_travelin_TagRealmProxy.insertOrUpdate(realm, tagsItem, cache);
                    }
                    tagsOsList.setRow(i, cacheItemIndextags);
                }
            } else {
                tagsOsList.removeAll();
                if (tagsList != null) {
                    for (com.example.travelin.Tag tagsItem : tagsList) {
                        Long cacheItemIndextags = cache.get(tagsItem);
                        if (cacheItemIndextags == null) {
                            cacheItemIndextags = com_example_travelin_TagRealmProxy.insertOrUpdate(realm, tagsItem, cache);
                        }
                        tagsOsList.addRow(cacheItemIndextags);
                    }
                }
            }


            OsList rsvpOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.rsvpIndex);
            RealmList<com.example.travelin.User> rsvpList = ((com_example_travelin_EventRealmProxyInterface) object).realmGet$rsvp();
            if (rsvpList != null && rsvpList.size() == rsvpOsList.size()) {
                // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
                int objectCount = rsvpList.size();
                for (int i = 0; i < objectCount; i++) {
                    com.example.travelin.User rsvpItem = rsvpList.get(i);
                    Long cacheItemIndexrsvp = cache.get(rsvpItem);
                    if (cacheItemIndexrsvp == null) {
                        cacheItemIndexrsvp = com_example_travelin_UserRealmProxy.insertOrUpdate(realm, rsvpItem, cache);
                    }
                    rsvpOsList.setRow(i, cacheItemIndexrsvp);
                }
            } else {
                rsvpOsList.removeAll();
                if (rsvpList != null) {
                    for (com.example.travelin.User rsvpItem : rsvpList) {
                        Long cacheItemIndexrsvp = cache.get(rsvpItem);
                        if (cacheItemIndexrsvp == null) {
                            cacheItemIndexrsvp = com_example_travelin_UserRealmProxy.insertOrUpdate(realm, rsvpItem, cache);
                        }
                        rsvpOsList.addRow(cacheItemIndexrsvp);
                    }
                }
            }

        }
    }

    public static com.example.travelin.Event createDetachedCopy(com.example.travelin.Event realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.example.travelin.Event unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.example.travelin.Event();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.example.travelin.Event) cachedObject.object;
            }
            unmanagedObject = (com.example.travelin.Event) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_example_travelin_EventRealmProxyInterface unmanagedCopy = (com_example_travelin_EventRealmProxyInterface) unmanagedObject;
        com_example_travelin_EventRealmProxyInterface realmSource = (com_example_travelin_EventRealmProxyInterface) realmObject;

        // Deep copy of location
        unmanagedCopy.realmSet$location(com_example_travelin_EventLocationRealmProxy.createDetachedCopy(realmSource.realmGet$location(), currentDepth + 1, maxDepth, cache));
        unmanagedCopy.realmSet$date(realmSource.realmGet$date());
        unmanagedCopy.realmSet$name(realmSource.realmGet$name());

        // Deep copy of tags
        if (currentDepth == maxDepth) {
            unmanagedCopy.realmSet$tags(null);
        } else {
            RealmList<com.example.travelin.Tag> managedtagsList = realmSource.realmGet$tags();
            RealmList<com.example.travelin.Tag> unmanagedtagsList = new RealmList<com.example.travelin.Tag>();
            unmanagedCopy.realmSet$tags(unmanagedtagsList);
            int nextDepth = currentDepth + 1;
            int size = managedtagsList.size();
            for (int i = 0; i < size; i++) {
                com.example.travelin.Tag item = com_example_travelin_TagRealmProxy.createDetachedCopy(managedtagsList.get(i), nextDepth, maxDepth, cache);
                unmanagedtagsList.add(item);
            }
        }

        // Deep copy of rsvp
        if (currentDepth == maxDepth) {
            unmanagedCopy.realmSet$rsvp(null);
        } else {
            RealmList<com.example.travelin.User> managedrsvpList = realmSource.realmGet$rsvp();
            RealmList<com.example.travelin.User> unmanagedrsvpList = new RealmList<com.example.travelin.User>();
            unmanagedCopy.realmSet$rsvp(unmanagedrsvpList);
            int nextDepth = currentDepth + 1;
            int size = managedrsvpList.size();
            for (int i = 0; i < size; i++) {
                com.example.travelin.User item = com_example_travelin_UserRealmProxy.createDetachedCopy(managedrsvpList.get(i), nextDepth, maxDepth, cache);
                unmanagedrsvpList.add(item);
            }
        }

        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("Event = proxy[");
        stringBuilder.append("{location:");
        stringBuilder.append(realmGet$location() != null ? "EventLocation" : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{date:");
        stringBuilder.append(realmGet$date() != null ? realmGet$date() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{name:");
        stringBuilder.append(realmGet$name() != null ? realmGet$name() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{tags:");
        stringBuilder.append("RealmList<Tag>[").append(realmGet$tags().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{rsvp:");
        stringBuilder.append("RealmList<User>[").append(realmGet$rsvp().size()).append("]");
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
        com_example_travelin_EventRealmProxy aEvent = (com_example_travelin_EventRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aEvent.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aEvent.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aEvent.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
