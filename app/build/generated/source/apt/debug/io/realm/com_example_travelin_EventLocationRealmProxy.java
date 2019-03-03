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
public class com_example_travelin_EventLocationRealmProxy extends com.example.travelin.EventLocation
    implements RealmObjectProxy, com_example_travelin_EventLocationRealmProxyInterface {

    static final class EventLocationColumnInfo extends ColumnInfo {
        long maxColumnIndexValue;
        long tagsIndex;
        long locNameIndex;

        EventLocationColumnInfo(OsSchemaInfo schemaInfo) {
            super(2);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("EventLocation");
            this.tagsIndex = addColumnDetails("tags", "tags", objectSchemaInfo);
            this.locNameIndex = addColumnDetails("locName", "locName", objectSchemaInfo);
            this.maxColumnIndexValue = objectSchemaInfo.getMaxColumnIndex();
        }

        EventLocationColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new EventLocationColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final EventLocationColumnInfo src = (EventLocationColumnInfo) rawSrc;
            final EventLocationColumnInfo dst = (EventLocationColumnInfo) rawDst;
            dst.tagsIndex = src.tagsIndex;
            dst.locNameIndex = src.locNameIndex;
            dst.maxColumnIndexValue = src.maxColumnIndexValue;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private EventLocationColumnInfo columnInfo;
    private ProxyState<com.example.travelin.EventLocation> proxyState;
    private RealmList<com.example.travelin.Tag> tagsRealmList;

    com_example_travelin_EventLocationRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (EventLocationColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.example.travelin.EventLocation>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
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
    @SuppressWarnings("cast")
    public String realmGet$locName() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.locNameIndex);
    }

    @Override
    public void realmSet$locName(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.locNameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.locNameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.locNameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.locNameIndex, value);
    }

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("EventLocation", 2, 0);
        builder.addPersistedLinkProperty("tags", RealmFieldType.LIST, "Tag");
        builder.addPersistedProperty("locName", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static EventLocationColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new EventLocationColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "EventLocation";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "EventLocation";
    }

    @SuppressWarnings("cast")
    public static com.example.travelin.EventLocation createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(1);
        if (json.has("tags")) {
            excludeFields.add("tags");
        }
        com.example.travelin.EventLocation obj = realm.createObjectInternal(com.example.travelin.EventLocation.class, true, excludeFields);

        final com_example_travelin_EventLocationRealmProxyInterface objProxy = (com_example_travelin_EventLocationRealmProxyInterface) obj;
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
        if (json.has("locName")) {
            if (json.isNull("locName")) {
                objProxy.realmSet$locName(null);
            } else {
                objProxy.realmSet$locName((String) json.getString("locName"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.example.travelin.EventLocation createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        final com.example.travelin.EventLocation obj = new com.example.travelin.EventLocation();
        final com_example_travelin_EventLocationRealmProxyInterface objProxy = (com_example_travelin_EventLocationRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
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
            } else if (name.equals("locName")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$locName((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$locName(null);
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return realm.copyToRealm(obj);
    }

    private static com_example_travelin_EventLocationRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating uexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.example.travelin.EventLocation.class), false, Collections.<String>emptyList());
        io.realm.com_example_travelin_EventLocationRealmProxy obj = new io.realm.com_example_travelin_EventLocationRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.example.travelin.EventLocation copyOrUpdate(Realm realm, EventLocationColumnInfo columnInfo, com.example.travelin.EventLocation object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.example.travelin.EventLocation) cachedRealmObject;
        }

        return copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.example.travelin.EventLocation copy(Realm realm, EventLocationColumnInfo columnInfo, com.example.travelin.EventLocation newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.example.travelin.EventLocation) cachedRealmObject;
        }

        com_example_travelin_EventLocationRealmProxyInterface realmObjectSource = (com_example_travelin_EventLocationRealmProxyInterface) newObject;

        Table table = realm.getTable(com.example.travelin.EventLocation.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, columnInfo.maxColumnIndexValue, flags);

        // Add all non-"object reference" fields
        builder.addString(columnInfo.locNameIndex, realmObjectSource.realmGet$locName());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_example_travelin_EventLocationRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        // Finally add all fields that reference other Realm Objects, either directly or through a list
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

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.example.travelin.EventLocation object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.example.travelin.EventLocation.class);
        long tableNativePtr = table.getNativePtr();
        EventLocationColumnInfo columnInfo = (EventLocationColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.EventLocation.class);
        long rowIndex = OsObject.createRow(table);
        cache.put(object, rowIndex);

        RealmList<com.example.travelin.Tag> tagsList = ((com_example_travelin_EventLocationRealmProxyInterface) object).realmGet$tags();
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
        String realmGet$locName = ((com_example_travelin_EventLocationRealmProxyInterface) object).realmGet$locName();
        if (realmGet$locName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.locNameIndex, rowIndex, realmGet$locName, false);
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.travelin.EventLocation.class);
        long tableNativePtr = table.getNativePtr();
        EventLocationColumnInfo columnInfo = (EventLocationColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.EventLocation.class);
        com.example.travelin.EventLocation object = null;
        while (objects.hasNext()) {
            object = (com.example.travelin.EventLocation) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = OsObject.createRow(table);
            cache.put(object, rowIndex);

            RealmList<com.example.travelin.Tag> tagsList = ((com_example_travelin_EventLocationRealmProxyInterface) object).realmGet$tags();
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
            String realmGet$locName = ((com_example_travelin_EventLocationRealmProxyInterface) object).realmGet$locName();
            if (realmGet$locName != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.locNameIndex, rowIndex, realmGet$locName, false);
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.example.travelin.EventLocation object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.example.travelin.EventLocation.class);
        long tableNativePtr = table.getNativePtr();
        EventLocationColumnInfo columnInfo = (EventLocationColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.EventLocation.class);
        long rowIndex = OsObject.createRow(table);
        cache.put(object, rowIndex);

        OsList tagsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.tagsIndex);
        RealmList<com.example.travelin.Tag> tagsList = ((com_example_travelin_EventLocationRealmProxyInterface) object).realmGet$tags();
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

        String realmGet$locName = ((com_example_travelin_EventLocationRealmProxyInterface) object).realmGet$locName();
        if (realmGet$locName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.locNameIndex, rowIndex, realmGet$locName, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.locNameIndex, rowIndex, false);
        }
        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.travelin.EventLocation.class);
        long tableNativePtr = table.getNativePtr();
        EventLocationColumnInfo columnInfo = (EventLocationColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.EventLocation.class);
        com.example.travelin.EventLocation object = null;
        while (objects.hasNext()) {
            object = (com.example.travelin.EventLocation) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = OsObject.createRow(table);
            cache.put(object, rowIndex);

            OsList tagsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.tagsIndex);
            RealmList<com.example.travelin.Tag> tagsList = ((com_example_travelin_EventLocationRealmProxyInterface) object).realmGet$tags();
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

            String realmGet$locName = ((com_example_travelin_EventLocationRealmProxyInterface) object).realmGet$locName();
            if (realmGet$locName != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.locNameIndex, rowIndex, realmGet$locName, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.locNameIndex, rowIndex, false);
            }
        }
    }

    public static com.example.travelin.EventLocation createDetachedCopy(com.example.travelin.EventLocation realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.example.travelin.EventLocation unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.example.travelin.EventLocation();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.example.travelin.EventLocation) cachedObject.object;
            }
            unmanagedObject = (com.example.travelin.EventLocation) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_example_travelin_EventLocationRealmProxyInterface unmanagedCopy = (com_example_travelin_EventLocationRealmProxyInterface) unmanagedObject;
        com_example_travelin_EventLocationRealmProxyInterface realmSource = (com_example_travelin_EventLocationRealmProxyInterface) realmObject;

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
        unmanagedCopy.realmSet$locName(realmSource.realmGet$locName());

        return unmanagedObject;
    }

    @Override
    @SuppressWarnings("ArrayToString")
    public String toString() {
        if (!RealmObject.isValid(this)) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("EventLocation = proxy[");
        stringBuilder.append("{tags:");
        stringBuilder.append("RealmList<Tag>[").append(realmGet$tags().size()).append("]");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{locName:");
        stringBuilder.append(realmGet$locName() != null ? realmGet$locName() : "null");
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
        com_example_travelin_EventLocationRealmProxy aEventLocation = (com_example_travelin_EventLocationRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aEventLocation.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aEventLocation.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aEventLocation.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
