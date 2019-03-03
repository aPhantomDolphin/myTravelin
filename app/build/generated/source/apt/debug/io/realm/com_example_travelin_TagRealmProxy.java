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
public class com_example_travelin_TagRealmProxy extends com.example.travelin.Tag
    implements RealmObjectProxy, com_example_travelin_TagRealmProxyInterface {

    static final class TagColumnInfo extends ColumnInfo {
        long maxColumnIndexValue;
        long tagNameIndex;
        long relatedTagsIndex;

        TagColumnInfo(OsSchemaInfo schemaInfo) {
            super(2);
            OsObjectSchemaInfo objectSchemaInfo = schemaInfo.getObjectSchemaInfo("Tag");
            this.tagNameIndex = addColumnDetails("tagName", "tagName", objectSchemaInfo);
            this.relatedTagsIndex = addColumnDetails("relatedTags", "relatedTags", objectSchemaInfo);
            this.maxColumnIndexValue = objectSchemaInfo.getMaxColumnIndex();
        }

        TagColumnInfo(ColumnInfo src, boolean mutable) {
            super(src, mutable);
            copy(src, this);
        }

        @Override
        protected final ColumnInfo copy(boolean mutable) {
            return new TagColumnInfo(this, mutable);
        }

        @Override
        protected final void copy(ColumnInfo rawSrc, ColumnInfo rawDst) {
            final TagColumnInfo src = (TagColumnInfo) rawSrc;
            final TagColumnInfo dst = (TagColumnInfo) rawDst;
            dst.tagNameIndex = src.tagNameIndex;
            dst.relatedTagsIndex = src.relatedTagsIndex;
            dst.maxColumnIndexValue = src.maxColumnIndexValue;
        }
    }

    private static final OsObjectSchemaInfo expectedObjectSchemaInfo = createExpectedObjectSchemaInfo();

    private TagColumnInfo columnInfo;
    private ProxyState<com.example.travelin.Tag> proxyState;
    private RealmList<com.example.travelin.Tag> relatedTagsRealmList;

    com_example_travelin_TagRealmProxy() {
        proxyState.setConstructionFinished();
    }

    @Override
    public void realm$injectObjectContext() {
        if (this.proxyState != null) {
            return;
        }
        final BaseRealm.RealmObjectContext context = BaseRealm.objectContext.get();
        this.columnInfo = (TagColumnInfo) context.getColumnInfo();
        this.proxyState = new ProxyState<com.example.travelin.Tag>(this);
        proxyState.setRealm$realm(context.getRealm());
        proxyState.setRow$realm(context.getRow());
        proxyState.setAcceptDefaultValue$realm(context.getAcceptDefaultValue());
        proxyState.setExcludeFields$realm(context.getExcludeFields());
    }

    @Override
    @SuppressWarnings("cast")
    public String realmGet$tagName() {
        proxyState.getRealm$realm().checkIfValid();
        return (java.lang.String) proxyState.getRow$realm().getString(columnInfo.tagNameIndex);
    }

    @Override
    public void realmSet$tagName(String value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            final Row row = proxyState.getRow$realm();
            if (value == null) {
                row.getTable().setNull(columnInfo.tagNameIndex, row.getIndex(), true);
                return;
            }
            row.getTable().setString(columnInfo.tagNameIndex, row.getIndex(), value, true);
            return;
        }

        proxyState.getRealm$realm().checkIfValid();
        if (value == null) {
            proxyState.getRow$realm().setNull(columnInfo.tagNameIndex);
            return;
        }
        proxyState.getRow$realm().setString(columnInfo.tagNameIndex, value);
    }

    @Override
    public RealmList<com.example.travelin.Tag> realmGet$relatedTags() {
        proxyState.getRealm$realm().checkIfValid();
        // use the cached value if available
        if (relatedTagsRealmList != null) {
            return relatedTagsRealmList;
        } else {
            OsList osList = proxyState.getRow$realm().getModelList(columnInfo.relatedTagsIndex);
            relatedTagsRealmList = new RealmList<com.example.travelin.Tag>(com.example.travelin.Tag.class, osList, proxyState.getRealm$realm());
            return relatedTagsRealmList;
        }
    }

    @Override
    public void realmSet$relatedTags(RealmList<com.example.travelin.Tag> value) {
        if (proxyState.isUnderConstruction()) {
            if (!proxyState.getAcceptDefaultValue$realm()) {
                return;
            }
            if (proxyState.getExcludeFields$realm().contains("relatedTags")) {
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
        OsList osList = proxyState.getRow$realm().getModelList(columnInfo.relatedTagsIndex);
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

    private static OsObjectSchemaInfo createExpectedObjectSchemaInfo() {
        OsObjectSchemaInfo.Builder builder = new OsObjectSchemaInfo.Builder("Tag", 2, 0);
        builder.addPersistedProperty("tagName", RealmFieldType.STRING, !Property.PRIMARY_KEY, !Property.INDEXED, !Property.REQUIRED);
        builder.addPersistedLinkProperty("relatedTags", RealmFieldType.LIST, "Tag");
        return builder.build();
    }

    public static OsObjectSchemaInfo getExpectedObjectSchemaInfo() {
        return expectedObjectSchemaInfo;
    }

    public static TagColumnInfo createColumnInfo(OsSchemaInfo schemaInfo) {
        return new TagColumnInfo(schemaInfo);
    }

    public static String getSimpleClassName() {
        return "Tag";
    }

    public static final class ClassNameHelper {
        public static final String INTERNAL_CLASS_NAME = "Tag";
    }

    @SuppressWarnings("cast")
    public static com.example.travelin.Tag createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        final List<String> excludeFields = new ArrayList<String>(1);
        if (json.has("relatedTags")) {
            excludeFields.add("relatedTags");
        }
        com.example.travelin.Tag obj = realm.createObjectInternal(com.example.travelin.Tag.class, true, excludeFields);

        final com_example_travelin_TagRealmProxyInterface objProxy = (com_example_travelin_TagRealmProxyInterface) obj;
        if (json.has("tagName")) {
            if (json.isNull("tagName")) {
                objProxy.realmSet$tagName(null);
            } else {
                objProxy.realmSet$tagName((String) json.getString("tagName"));
            }
        }
        if (json.has("relatedTags")) {
            if (json.isNull("relatedTags")) {
                objProxy.realmSet$relatedTags(null);
            } else {
                objProxy.realmGet$relatedTags().clear();
                JSONArray array = json.getJSONArray("relatedTags");
                for (int i = 0; i < array.length(); i++) {
                    com.example.travelin.Tag item = com_example_travelin_TagRealmProxy.createOrUpdateUsingJsonObject(realm, array.getJSONObject(i), update);
                    objProxy.realmGet$relatedTags().add(item);
                }
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static com.example.travelin.Tag createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        final com.example.travelin.Tag obj = new com.example.travelin.Tag();
        final com_example_travelin_TagRealmProxyInterface objProxy = (com_example_travelin_TagRealmProxyInterface) obj;
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (false) {
            } else if (name.equals("tagName")) {
                if (reader.peek() != JsonToken.NULL) {
                    objProxy.realmSet$tagName((String) reader.nextString());
                } else {
                    reader.skipValue();
                    objProxy.realmSet$tagName(null);
                }
            } else if (name.equals("relatedTags")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    objProxy.realmSet$relatedTags(null);
                } else {
                    objProxy.realmSet$relatedTags(new RealmList<com.example.travelin.Tag>());
                    reader.beginArray();
                    while (reader.hasNext()) {
                        com.example.travelin.Tag item = com_example_travelin_TagRealmProxy.createUsingJsonStream(realm, reader);
                        objProxy.realmGet$relatedTags().add(item);
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

    private static com_example_travelin_TagRealmProxy newProxyInstance(BaseRealm realm, Row row) {
        // Ignore default values to avoid creating uexpected objects from RealmModel/RealmList fields
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        objectContext.set(realm, row, realm.getSchema().getColumnInfo(com.example.travelin.Tag.class), false, Collections.<String>emptyList());
        io.realm.com_example_travelin_TagRealmProxy obj = new io.realm.com_example_travelin_TagRealmProxy();
        objectContext.clear();
        return obj;
    }

    public static com.example.travelin.Tag copyOrUpdate(Realm realm, TagColumnInfo columnInfo, com.example.travelin.Tag object, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
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
            return (com.example.travelin.Tag) cachedRealmObject;
        }

        return copy(realm, columnInfo, object, update, cache, flags);
    }

    public static com.example.travelin.Tag copy(Realm realm, TagColumnInfo columnInfo, com.example.travelin.Tag newObject, boolean update, Map<RealmModel,RealmObjectProxy> cache, Set<ImportFlag> flags) {
        RealmObjectProxy cachedRealmObject = cache.get(newObject);
        if (cachedRealmObject != null) {
            return (com.example.travelin.Tag) cachedRealmObject;
        }

        com_example_travelin_TagRealmProxyInterface realmObjectSource = (com_example_travelin_TagRealmProxyInterface) newObject;

        Table table = realm.getTable(com.example.travelin.Tag.class);
        OsObjectBuilder builder = new OsObjectBuilder(table, columnInfo.maxColumnIndexValue, flags);

        // Add all non-"object reference" fields
        builder.addString(columnInfo.tagNameIndex, realmObjectSource.realmGet$tagName());

        // Create the underlying object and cache it before setting any object/objectlist references
        // This will allow us to break any circular dependencies by using the object cache.
        Row row = builder.createNewObject();
        io.realm.com_example_travelin_TagRealmProxy realmObjectCopy = newProxyInstance(realm, row);
        cache.put(newObject, realmObjectCopy);

        // Finally add all fields that reference other Realm Objects, either directly or through a list
        RealmList<com.example.travelin.Tag> relatedTagsList = realmObjectSource.realmGet$relatedTags();
        if (relatedTagsList != null) {
            RealmList<com.example.travelin.Tag> relatedTagsRealmList = realmObjectCopy.realmGet$relatedTags();
            relatedTagsRealmList.clear();
            for (int i = 0; i < relatedTagsList.size(); i++) {
                com.example.travelin.Tag relatedTagsItem = relatedTagsList.get(i);
                com.example.travelin.Tag cacherelatedTags = (com.example.travelin.Tag) cache.get(relatedTagsItem);
                if (cacherelatedTags != null) {
                    relatedTagsRealmList.add(cacherelatedTags);
                } else {
                    relatedTagsRealmList.add(com_example_travelin_TagRealmProxy.copyOrUpdate(realm, (com_example_travelin_TagRealmProxy.TagColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.Tag.class), relatedTagsItem, update, cache, flags));
                }
            }
        }

        return realmObjectCopy;
    }

    public static long insert(Realm realm, com.example.travelin.Tag object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.example.travelin.Tag.class);
        long tableNativePtr = table.getNativePtr();
        TagColumnInfo columnInfo = (TagColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.Tag.class);
        long rowIndex = OsObject.createRow(table);
        cache.put(object, rowIndex);
        String realmGet$tagName = ((com_example_travelin_TagRealmProxyInterface) object).realmGet$tagName();
        if (realmGet$tagName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.tagNameIndex, rowIndex, realmGet$tagName, false);
        }

        RealmList<com.example.travelin.Tag> relatedTagsList = ((com_example_travelin_TagRealmProxyInterface) object).realmGet$relatedTags();
        if (relatedTagsList != null) {
            OsList relatedTagsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.relatedTagsIndex);
            for (com.example.travelin.Tag relatedTagsItem : relatedTagsList) {
                Long cacheItemIndexrelatedTags = cache.get(relatedTagsItem);
                if (cacheItemIndexrelatedTags == null) {
                    cacheItemIndexrelatedTags = com_example_travelin_TagRealmProxy.insert(realm, relatedTagsItem, cache);
                }
                relatedTagsOsList.addRow(cacheItemIndexrelatedTags);
            }
        }
        return rowIndex;
    }

    public static void insert(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.travelin.Tag.class);
        long tableNativePtr = table.getNativePtr();
        TagColumnInfo columnInfo = (TagColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.Tag.class);
        com.example.travelin.Tag object = null;
        while (objects.hasNext()) {
            object = (com.example.travelin.Tag) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = OsObject.createRow(table);
            cache.put(object, rowIndex);
            String realmGet$tagName = ((com_example_travelin_TagRealmProxyInterface) object).realmGet$tagName();
            if (realmGet$tagName != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.tagNameIndex, rowIndex, realmGet$tagName, false);
            }

            RealmList<com.example.travelin.Tag> relatedTagsList = ((com_example_travelin_TagRealmProxyInterface) object).realmGet$relatedTags();
            if (relatedTagsList != null) {
                OsList relatedTagsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.relatedTagsIndex);
                for (com.example.travelin.Tag relatedTagsItem : relatedTagsList) {
                    Long cacheItemIndexrelatedTags = cache.get(relatedTagsItem);
                    if (cacheItemIndexrelatedTags == null) {
                        cacheItemIndexrelatedTags = com_example_travelin_TagRealmProxy.insert(realm, relatedTagsItem, cache);
                    }
                    relatedTagsOsList.addRow(cacheItemIndexrelatedTags);
                }
            }
        }
    }

    public static long insertOrUpdate(Realm realm, com.example.travelin.Tag object, Map<RealmModel,Long> cache) {
        if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
            return ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex();
        }
        Table table = realm.getTable(com.example.travelin.Tag.class);
        long tableNativePtr = table.getNativePtr();
        TagColumnInfo columnInfo = (TagColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.Tag.class);
        long rowIndex = OsObject.createRow(table);
        cache.put(object, rowIndex);
        String realmGet$tagName = ((com_example_travelin_TagRealmProxyInterface) object).realmGet$tagName();
        if (realmGet$tagName != null) {
            Table.nativeSetString(tableNativePtr, columnInfo.tagNameIndex, rowIndex, realmGet$tagName, false);
        } else {
            Table.nativeSetNull(tableNativePtr, columnInfo.tagNameIndex, rowIndex, false);
        }

        OsList relatedTagsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.relatedTagsIndex);
        RealmList<com.example.travelin.Tag> relatedTagsList = ((com_example_travelin_TagRealmProxyInterface) object).realmGet$relatedTags();
        if (relatedTagsList != null && relatedTagsList.size() == relatedTagsOsList.size()) {
            // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
            int objects = relatedTagsList.size();
            for (int i = 0; i < objects; i++) {
                com.example.travelin.Tag relatedTagsItem = relatedTagsList.get(i);
                Long cacheItemIndexrelatedTags = cache.get(relatedTagsItem);
                if (cacheItemIndexrelatedTags == null) {
                    cacheItemIndexrelatedTags = com_example_travelin_TagRealmProxy.insertOrUpdate(realm, relatedTagsItem, cache);
                }
                relatedTagsOsList.setRow(i, cacheItemIndexrelatedTags);
            }
        } else {
            relatedTagsOsList.removeAll();
            if (relatedTagsList != null) {
                for (com.example.travelin.Tag relatedTagsItem : relatedTagsList) {
                    Long cacheItemIndexrelatedTags = cache.get(relatedTagsItem);
                    if (cacheItemIndexrelatedTags == null) {
                        cacheItemIndexrelatedTags = com_example_travelin_TagRealmProxy.insertOrUpdate(realm, relatedTagsItem, cache);
                    }
                    relatedTagsOsList.addRow(cacheItemIndexrelatedTags);
                }
            }
        }

        return rowIndex;
    }

    public static void insertOrUpdate(Realm realm, Iterator<? extends RealmModel> objects, Map<RealmModel,Long> cache) {
        Table table = realm.getTable(com.example.travelin.Tag.class);
        long tableNativePtr = table.getNativePtr();
        TagColumnInfo columnInfo = (TagColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.Tag.class);
        com.example.travelin.Tag object = null;
        while (objects.hasNext()) {
            object = (com.example.travelin.Tag) objects.next();
            if (cache.containsKey(object)) {
                continue;
            }
            if (object instanceof RealmObjectProxy && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm() != null && ((RealmObjectProxy) object).realmGet$proxyState().getRealm$realm().getPath().equals(realm.getPath())) {
                cache.put(object, ((RealmObjectProxy) object).realmGet$proxyState().getRow$realm().getIndex());
                continue;
            }
            long rowIndex = OsObject.createRow(table);
            cache.put(object, rowIndex);
            String realmGet$tagName = ((com_example_travelin_TagRealmProxyInterface) object).realmGet$tagName();
            if (realmGet$tagName != null) {
                Table.nativeSetString(tableNativePtr, columnInfo.tagNameIndex, rowIndex, realmGet$tagName, false);
            } else {
                Table.nativeSetNull(tableNativePtr, columnInfo.tagNameIndex, rowIndex, false);
            }

            OsList relatedTagsOsList = new OsList(table.getUncheckedRow(rowIndex), columnInfo.relatedTagsIndex);
            RealmList<com.example.travelin.Tag> relatedTagsList = ((com_example_travelin_TagRealmProxyInterface) object).realmGet$relatedTags();
            if (relatedTagsList != null && relatedTagsList.size() == relatedTagsOsList.size()) {
                // For lists of equal lengths, we need to set each element directly as clearing the receiver list can be wrong if the input and target list are the same.
                int objectCount = relatedTagsList.size();
                for (int i = 0; i < objectCount; i++) {
                    com.example.travelin.Tag relatedTagsItem = relatedTagsList.get(i);
                    Long cacheItemIndexrelatedTags = cache.get(relatedTagsItem);
                    if (cacheItemIndexrelatedTags == null) {
                        cacheItemIndexrelatedTags = com_example_travelin_TagRealmProxy.insertOrUpdate(realm, relatedTagsItem, cache);
                    }
                    relatedTagsOsList.setRow(i, cacheItemIndexrelatedTags);
                }
            } else {
                relatedTagsOsList.removeAll();
                if (relatedTagsList != null) {
                    for (com.example.travelin.Tag relatedTagsItem : relatedTagsList) {
                        Long cacheItemIndexrelatedTags = cache.get(relatedTagsItem);
                        if (cacheItemIndexrelatedTags == null) {
                            cacheItemIndexrelatedTags = com_example_travelin_TagRealmProxy.insertOrUpdate(realm, relatedTagsItem, cache);
                        }
                        relatedTagsOsList.addRow(cacheItemIndexrelatedTags);
                    }
                }
            }

        }
    }

    public static com.example.travelin.Tag createDetachedCopy(com.example.travelin.Tag realmObject, int currentDepth, int maxDepth, Map<RealmModel, CacheData<RealmModel>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<RealmModel> cachedObject = cache.get(realmObject);
        com.example.travelin.Tag unmanagedObject;
        if (cachedObject == null) {
            unmanagedObject = new com.example.travelin.Tag();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmModel>(currentDepth, unmanagedObject));
        } else {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return (com.example.travelin.Tag) cachedObject.object;
            }
            unmanagedObject = (com.example.travelin.Tag) cachedObject.object;
            cachedObject.minDepth = currentDepth;
        }
        com_example_travelin_TagRealmProxyInterface unmanagedCopy = (com_example_travelin_TagRealmProxyInterface) unmanagedObject;
        com_example_travelin_TagRealmProxyInterface realmSource = (com_example_travelin_TagRealmProxyInterface) realmObject;
        unmanagedCopy.realmSet$tagName(realmSource.realmGet$tagName());

        // Deep copy of relatedTags
        if (currentDepth == maxDepth) {
            unmanagedCopy.realmSet$relatedTags(null);
        } else {
            RealmList<com.example.travelin.Tag> managedrelatedTagsList = realmSource.realmGet$relatedTags();
            RealmList<com.example.travelin.Tag> unmanagedrelatedTagsList = new RealmList<com.example.travelin.Tag>();
            unmanagedCopy.realmSet$relatedTags(unmanagedrelatedTagsList);
            int nextDepth = currentDepth + 1;
            int size = managedrelatedTagsList.size();
            for (int i = 0; i < size; i++) {
                com.example.travelin.Tag item = com_example_travelin_TagRealmProxy.createDetachedCopy(managedrelatedTagsList.get(i), nextDepth, maxDepth, cache);
                unmanagedrelatedTagsList.add(item);
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
        StringBuilder stringBuilder = new StringBuilder("Tag = proxy[");
        stringBuilder.append("{tagName:");
        stringBuilder.append(realmGet$tagName() != null ? realmGet$tagName() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{relatedTags:");
        stringBuilder.append("RealmList<Tag>[").append(realmGet$relatedTags().size()).append("]");
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
        com_example_travelin_TagRealmProxy aTag = (com_example_travelin_TagRealmProxy)o;

        String path = proxyState.getRealm$realm().getPath();
        String otherPath = aTag.proxyState.getRealm$realm().getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;

        String tableName = proxyState.getRow$realm().getTable().getName();
        String otherTableName = aTag.proxyState.getRow$realm().getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (proxyState.getRow$realm().getIndex() != aTag.proxyState.getRow$realm().getIndex()) return false;

        return true;
    }
}
