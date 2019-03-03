package io.realm;


import android.util.JsonReader;
import io.realm.ImportFlag;
import io.realm.internal.ColumnInfo;
import io.realm.internal.OsObjectSchemaInfo;
import io.realm.internal.OsSchemaInfo;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.RealmProxyMediator;
import io.realm.internal.Row;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

@io.realm.annotations.RealmModule
class DefaultRealmModuleMediator extends RealmProxyMediator {

    private static final Set<Class<? extends RealmModel>> MODEL_CLASSES;
    static {
        Set<Class<? extends RealmModel>> modelClasses = new HashSet<Class<? extends RealmModel>>(7);
        modelClasses.add(com.example.travelin.DirectMessage.class);
        modelClasses.add(com.example.travelin.Event.class);
        modelClasses.add(com.example.travelin.EventLocation.class);
        modelClasses.add(com.example.travelin.MyRating.class);
        modelClasses.add(com.example.travelin.Post.class);
        modelClasses.add(com.example.travelin.Tag.class);
        modelClasses.add(com.example.travelin.User.class);
        MODEL_CLASSES = Collections.unmodifiableSet(modelClasses);
    }

    @Override
    public Map<Class<? extends RealmModel>, OsObjectSchemaInfo> getExpectedObjectSchemaInfoMap() {
        Map<Class<? extends RealmModel>, OsObjectSchemaInfo> infoMap = new HashMap<Class<? extends RealmModel>, OsObjectSchemaInfo>(7);
        infoMap.put(com.example.travelin.DirectMessage.class, io.realm.com_example_travelin_DirectMessageRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.example.travelin.Event.class, io.realm.com_example_travelin_EventRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.example.travelin.EventLocation.class, io.realm.com_example_travelin_EventLocationRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.example.travelin.MyRating.class, io.realm.com_example_travelin_MyRatingRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.example.travelin.Post.class, io.realm.com_example_travelin_PostRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.example.travelin.Tag.class, io.realm.com_example_travelin_TagRealmProxy.getExpectedObjectSchemaInfo());
        infoMap.put(com.example.travelin.User.class, io.realm.com_example_travelin_UserRealmProxy.getExpectedObjectSchemaInfo());
        return infoMap;
    }

    @Override
    public ColumnInfo createColumnInfo(Class<? extends RealmModel> clazz, OsSchemaInfo schemaInfo) {
        checkClass(clazz);

        if (clazz.equals(com.example.travelin.DirectMessage.class)) {
            return io.realm.com_example_travelin_DirectMessageRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.example.travelin.Event.class)) {
            return io.realm.com_example_travelin_EventRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.example.travelin.EventLocation.class)) {
            return io.realm.com_example_travelin_EventLocationRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.example.travelin.MyRating.class)) {
            return io.realm.com_example_travelin_MyRatingRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.example.travelin.Post.class)) {
            return io.realm.com_example_travelin_PostRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.example.travelin.Tag.class)) {
            return io.realm.com_example_travelin_TagRealmProxy.createColumnInfo(schemaInfo);
        }
        if (clazz.equals(com.example.travelin.User.class)) {
            return io.realm.com_example_travelin_UserRealmProxy.createColumnInfo(schemaInfo);
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public String getSimpleClassNameImpl(Class<? extends RealmModel> clazz) {
        checkClass(clazz);

        if (clazz.equals(com.example.travelin.DirectMessage.class)) {
            return "DirectMessage";
        }
        if (clazz.equals(com.example.travelin.Event.class)) {
            return "Event";
        }
        if (clazz.equals(com.example.travelin.EventLocation.class)) {
            return "EventLocation";
        }
        if (clazz.equals(com.example.travelin.MyRating.class)) {
            return "MyRating";
        }
        if (clazz.equals(com.example.travelin.Post.class)) {
            return "Post";
        }
        if (clazz.equals(com.example.travelin.Tag.class)) {
            return "Tag";
        }
        if (clazz.equals(com.example.travelin.User.class)) {
            return "User";
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public <E extends RealmModel> E newInstance(Class<E> clazz, Object baseRealm, Row row, ColumnInfo columnInfo, boolean acceptDefaultValue, List<String> excludeFields) {
        final BaseRealm.RealmObjectContext objectContext = BaseRealm.objectContext.get();
        try {
            objectContext.set((BaseRealm) baseRealm, row, columnInfo, acceptDefaultValue, excludeFields);
            checkClass(clazz);

            if (clazz.equals(com.example.travelin.DirectMessage.class)) {
                return clazz.cast(new io.realm.com_example_travelin_DirectMessageRealmProxy());
            }
            if (clazz.equals(com.example.travelin.Event.class)) {
                return clazz.cast(new io.realm.com_example_travelin_EventRealmProxy());
            }
            if (clazz.equals(com.example.travelin.EventLocation.class)) {
                return clazz.cast(new io.realm.com_example_travelin_EventLocationRealmProxy());
            }
            if (clazz.equals(com.example.travelin.MyRating.class)) {
                return clazz.cast(new io.realm.com_example_travelin_MyRatingRealmProxy());
            }
            if (clazz.equals(com.example.travelin.Post.class)) {
                return clazz.cast(new io.realm.com_example_travelin_PostRealmProxy());
            }
            if (clazz.equals(com.example.travelin.Tag.class)) {
                return clazz.cast(new io.realm.com_example_travelin_TagRealmProxy());
            }
            if (clazz.equals(com.example.travelin.User.class)) {
                return clazz.cast(new io.realm.com_example_travelin_UserRealmProxy());
            }
            throw getMissingProxyClassException(clazz);
        } finally {
            objectContext.clear();
        }
    }

    @Override
    public Set<Class<? extends RealmModel>> getModelClasses() {
        return MODEL_CLASSES;
    }

    @Override
    public <E extends RealmModel> E copyOrUpdate(Realm realm, E obj, boolean update, Map<RealmModel, RealmObjectProxy> cache, Set<ImportFlag> flags) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<E> clazz = (Class<E>) ((obj instanceof RealmObjectProxy) ? obj.getClass().getSuperclass() : obj.getClass());

        if (clazz.equals(com.example.travelin.DirectMessage.class)) {
            com_example_travelin_DirectMessageRealmProxy.DirectMessageColumnInfo columnInfo = (com_example_travelin_DirectMessageRealmProxy.DirectMessageColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.DirectMessage.class);
            return clazz.cast(io.realm.com_example_travelin_DirectMessageRealmProxy.copyOrUpdate(realm, columnInfo, (com.example.travelin.DirectMessage) obj, update, cache, flags));
        }
        if (clazz.equals(com.example.travelin.Event.class)) {
            com_example_travelin_EventRealmProxy.EventColumnInfo columnInfo = (com_example_travelin_EventRealmProxy.EventColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.Event.class);
            return clazz.cast(io.realm.com_example_travelin_EventRealmProxy.copyOrUpdate(realm, columnInfo, (com.example.travelin.Event) obj, update, cache, flags));
        }
        if (clazz.equals(com.example.travelin.EventLocation.class)) {
            com_example_travelin_EventLocationRealmProxy.EventLocationColumnInfo columnInfo = (com_example_travelin_EventLocationRealmProxy.EventLocationColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.EventLocation.class);
            return clazz.cast(io.realm.com_example_travelin_EventLocationRealmProxy.copyOrUpdate(realm, columnInfo, (com.example.travelin.EventLocation) obj, update, cache, flags));
        }
        if (clazz.equals(com.example.travelin.MyRating.class)) {
            com_example_travelin_MyRatingRealmProxy.MyRatingColumnInfo columnInfo = (com_example_travelin_MyRatingRealmProxy.MyRatingColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.MyRating.class);
            return clazz.cast(io.realm.com_example_travelin_MyRatingRealmProxy.copyOrUpdate(realm, columnInfo, (com.example.travelin.MyRating) obj, update, cache, flags));
        }
        if (clazz.equals(com.example.travelin.Post.class)) {
            com_example_travelin_PostRealmProxy.PostColumnInfo columnInfo = (com_example_travelin_PostRealmProxy.PostColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.Post.class);
            return clazz.cast(io.realm.com_example_travelin_PostRealmProxy.copyOrUpdate(realm, columnInfo, (com.example.travelin.Post) obj, update, cache, flags));
        }
        if (clazz.equals(com.example.travelin.Tag.class)) {
            com_example_travelin_TagRealmProxy.TagColumnInfo columnInfo = (com_example_travelin_TagRealmProxy.TagColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.Tag.class);
            return clazz.cast(io.realm.com_example_travelin_TagRealmProxy.copyOrUpdate(realm, columnInfo, (com.example.travelin.Tag) obj, update, cache, flags));
        }
        if (clazz.equals(com.example.travelin.User.class)) {
            com_example_travelin_UserRealmProxy.UserColumnInfo columnInfo = (com_example_travelin_UserRealmProxy.UserColumnInfo) realm.getSchema().getColumnInfo(com.example.travelin.User.class);
            return clazz.cast(io.realm.com_example_travelin_UserRealmProxy.copyOrUpdate(realm, columnInfo, (com.example.travelin.User) obj, update, cache, flags));
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public void insert(Realm realm, RealmModel object, Map<RealmModel, Long> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

        if (clazz.equals(com.example.travelin.DirectMessage.class)) {
            io.realm.com_example_travelin_DirectMessageRealmProxy.insert(realm, (com.example.travelin.DirectMessage) object, cache);
        } else if (clazz.equals(com.example.travelin.Event.class)) {
            io.realm.com_example_travelin_EventRealmProxy.insert(realm, (com.example.travelin.Event) object, cache);
        } else if (clazz.equals(com.example.travelin.EventLocation.class)) {
            io.realm.com_example_travelin_EventLocationRealmProxy.insert(realm, (com.example.travelin.EventLocation) object, cache);
        } else if (clazz.equals(com.example.travelin.MyRating.class)) {
            io.realm.com_example_travelin_MyRatingRealmProxy.insert(realm, (com.example.travelin.MyRating) object, cache);
        } else if (clazz.equals(com.example.travelin.Post.class)) {
            io.realm.com_example_travelin_PostRealmProxy.insert(realm, (com.example.travelin.Post) object, cache);
        } else if (clazz.equals(com.example.travelin.Tag.class)) {
            io.realm.com_example_travelin_TagRealmProxy.insert(realm, (com.example.travelin.Tag) object, cache);
        } else if (clazz.equals(com.example.travelin.User.class)) {
            io.realm.com_example_travelin_UserRealmProxy.insert(realm, (com.example.travelin.User) object, cache);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public void insert(Realm realm, Collection<? extends RealmModel> objects) {
        Iterator<? extends RealmModel> iterator = objects.iterator();
        RealmModel object = null;
        Map<RealmModel, Long> cache = new HashMap<RealmModel, Long>(objects.size());
        if (iterator.hasNext()) {
            //  access the first element to figure out the clazz for the routing below
            object = iterator.next();
            // This cast is correct because obj is either
            // generated by RealmProxy or the original type extending directly from RealmObject
            @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

            if (clazz.equals(com.example.travelin.DirectMessage.class)) {
                io.realm.com_example_travelin_DirectMessageRealmProxy.insert(realm, (com.example.travelin.DirectMessage) object, cache);
            } else if (clazz.equals(com.example.travelin.Event.class)) {
                io.realm.com_example_travelin_EventRealmProxy.insert(realm, (com.example.travelin.Event) object, cache);
            } else if (clazz.equals(com.example.travelin.EventLocation.class)) {
                io.realm.com_example_travelin_EventLocationRealmProxy.insert(realm, (com.example.travelin.EventLocation) object, cache);
            } else if (clazz.equals(com.example.travelin.MyRating.class)) {
                io.realm.com_example_travelin_MyRatingRealmProxy.insert(realm, (com.example.travelin.MyRating) object, cache);
            } else if (clazz.equals(com.example.travelin.Post.class)) {
                io.realm.com_example_travelin_PostRealmProxy.insert(realm, (com.example.travelin.Post) object, cache);
            } else if (clazz.equals(com.example.travelin.Tag.class)) {
                io.realm.com_example_travelin_TagRealmProxy.insert(realm, (com.example.travelin.Tag) object, cache);
            } else if (clazz.equals(com.example.travelin.User.class)) {
                io.realm.com_example_travelin_UserRealmProxy.insert(realm, (com.example.travelin.User) object, cache);
            } else {
                throw getMissingProxyClassException(clazz);
            }
            if (iterator.hasNext()) {
                if (clazz.equals(com.example.travelin.DirectMessage.class)) {
                    io.realm.com_example_travelin_DirectMessageRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.example.travelin.Event.class)) {
                    io.realm.com_example_travelin_EventRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.example.travelin.EventLocation.class)) {
                    io.realm.com_example_travelin_EventLocationRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.example.travelin.MyRating.class)) {
                    io.realm.com_example_travelin_MyRatingRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.example.travelin.Post.class)) {
                    io.realm.com_example_travelin_PostRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.example.travelin.Tag.class)) {
                    io.realm.com_example_travelin_TagRealmProxy.insert(realm, iterator, cache);
                } else if (clazz.equals(com.example.travelin.User.class)) {
                    io.realm.com_example_travelin_UserRealmProxy.insert(realm, iterator, cache);
                } else {
                    throw getMissingProxyClassException(clazz);
                }
            }
        }
    }

    @Override
    public void insertOrUpdate(Realm realm, RealmModel obj, Map<RealmModel, Long> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((obj instanceof RealmObjectProxy) ? obj.getClass().getSuperclass() : obj.getClass());

        if (clazz.equals(com.example.travelin.DirectMessage.class)) {
            io.realm.com_example_travelin_DirectMessageRealmProxy.insertOrUpdate(realm, (com.example.travelin.DirectMessage) obj, cache);
        } else if (clazz.equals(com.example.travelin.Event.class)) {
            io.realm.com_example_travelin_EventRealmProxy.insertOrUpdate(realm, (com.example.travelin.Event) obj, cache);
        } else if (clazz.equals(com.example.travelin.EventLocation.class)) {
            io.realm.com_example_travelin_EventLocationRealmProxy.insertOrUpdate(realm, (com.example.travelin.EventLocation) obj, cache);
        } else if (clazz.equals(com.example.travelin.MyRating.class)) {
            io.realm.com_example_travelin_MyRatingRealmProxy.insertOrUpdate(realm, (com.example.travelin.MyRating) obj, cache);
        } else if (clazz.equals(com.example.travelin.Post.class)) {
            io.realm.com_example_travelin_PostRealmProxy.insertOrUpdate(realm, (com.example.travelin.Post) obj, cache);
        } else if (clazz.equals(com.example.travelin.Tag.class)) {
            io.realm.com_example_travelin_TagRealmProxy.insertOrUpdate(realm, (com.example.travelin.Tag) obj, cache);
        } else if (clazz.equals(com.example.travelin.User.class)) {
            io.realm.com_example_travelin_UserRealmProxy.insertOrUpdate(realm, (com.example.travelin.User) obj, cache);
        } else {
            throw getMissingProxyClassException(clazz);
        }
    }

    @Override
    public void insertOrUpdate(Realm realm, Collection<? extends RealmModel> objects) {
        Iterator<? extends RealmModel> iterator = objects.iterator();
        RealmModel object = null;
        Map<RealmModel, Long> cache = new HashMap<RealmModel, Long>(objects.size());
        if (iterator.hasNext()) {
            //  access the first element to figure out the clazz for the routing below
            object = iterator.next();
            // This cast is correct because obj is either
            // generated by RealmProxy or the original type extending directly from RealmObject
            @SuppressWarnings("unchecked") Class<RealmModel> clazz = (Class<RealmModel>) ((object instanceof RealmObjectProxy) ? object.getClass().getSuperclass() : object.getClass());

            if (clazz.equals(com.example.travelin.DirectMessage.class)) {
                io.realm.com_example_travelin_DirectMessageRealmProxy.insertOrUpdate(realm, (com.example.travelin.DirectMessage) object, cache);
            } else if (clazz.equals(com.example.travelin.Event.class)) {
                io.realm.com_example_travelin_EventRealmProxy.insertOrUpdate(realm, (com.example.travelin.Event) object, cache);
            } else if (clazz.equals(com.example.travelin.EventLocation.class)) {
                io.realm.com_example_travelin_EventLocationRealmProxy.insertOrUpdate(realm, (com.example.travelin.EventLocation) object, cache);
            } else if (clazz.equals(com.example.travelin.MyRating.class)) {
                io.realm.com_example_travelin_MyRatingRealmProxy.insertOrUpdate(realm, (com.example.travelin.MyRating) object, cache);
            } else if (clazz.equals(com.example.travelin.Post.class)) {
                io.realm.com_example_travelin_PostRealmProxy.insertOrUpdate(realm, (com.example.travelin.Post) object, cache);
            } else if (clazz.equals(com.example.travelin.Tag.class)) {
                io.realm.com_example_travelin_TagRealmProxy.insertOrUpdate(realm, (com.example.travelin.Tag) object, cache);
            } else if (clazz.equals(com.example.travelin.User.class)) {
                io.realm.com_example_travelin_UserRealmProxy.insertOrUpdate(realm, (com.example.travelin.User) object, cache);
            } else {
                throw getMissingProxyClassException(clazz);
            }
            if (iterator.hasNext()) {
                if (clazz.equals(com.example.travelin.DirectMessage.class)) {
                    io.realm.com_example_travelin_DirectMessageRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.example.travelin.Event.class)) {
                    io.realm.com_example_travelin_EventRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.example.travelin.EventLocation.class)) {
                    io.realm.com_example_travelin_EventLocationRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.example.travelin.MyRating.class)) {
                    io.realm.com_example_travelin_MyRatingRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.example.travelin.Post.class)) {
                    io.realm.com_example_travelin_PostRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.example.travelin.Tag.class)) {
                    io.realm.com_example_travelin_TagRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else if (clazz.equals(com.example.travelin.User.class)) {
                    io.realm.com_example_travelin_UserRealmProxy.insertOrUpdate(realm, iterator, cache);
                } else {
                    throw getMissingProxyClassException(clazz);
                }
            }
        }
    }

    @Override
    public <E extends RealmModel> E createOrUpdateUsingJsonObject(Class<E> clazz, Realm realm, JSONObject json, boolean update)
        throws JSONException {
        checkClass(clazz);

        if (clazz.equals(com.example.travelin.DirectMessage.class)) {
            return clazz.cast(io.realm.com_example_travelin_DirectMessageRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.example.travelin.Event.class)) {
            return clazz.cast(io.realm.com_example_travelin_EventRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.example.travelin.EventLocation.class)) {
            return clazz.cast(io.realm.com_example_travelin_EventLocationRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.example.travelin.MyRating.class)) {
            return clazz.cast(io.realm.com_example_travelin_MyRatingRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.example.travelin.Post.class)) {
            return clazz.cast(io.realm.com_example_travelin_PostRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.example.travelin.Tag.class)) {
            return clazz.cast(io.realm.com_example_travelin_TagRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        if (clazz.equals(com.example.travelin.User.class)) {
            return clazz.cast(io.realm.com_example_travelin_UserRealmProxy.createOrUpdateUsingJsonObject(realm, json, update));
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public <E extends RealmModel> E createUsingJsonStream(Class<E> clazz, Realm realm, JsonReader reader)
        throws IOException {
        checkClass(clazz);

        if (clazz.equals(com.example.travelin.DirectMessage.class)) {
            return clazz.cast(io.realm.com_example_travelin_DirectMessageRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.example.travelin.Event.class)) {
            return clazz.cast(io.realm.com_example_travelin_EventRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.example.travelin.EventLocation.class)) {
            return clazz.cast(io.realm.com_example_travelin_EventLocationRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.example.travelin.MyRating.class)) {
            return clazz.cast(io.realm.com_example_travelin_MyRatingRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.example.travelin.Post.class)) {
            return clazz.cast(io.realm.com_example_travelin_PostRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.example.travelin.Tag.class)) {
            return clazz.cast(io.realm.com_example_travelin_TagRealmProxy.createUsingJsonStream(realm, reader));
        }
        if (clazz.equals(com.example.travelin.User.class)) {
            return clazz.cast(io.realm.com_example_travelin_UserRealmProxy.createUsingJsonStream(realm, reader));
        }
        throw getMissingProxyClassException(clazz);
    }

    @Override
    public <E extends RealmModel> E createDetachedCopy(E realmObject, int maxDepth, Map<RealmModel, RealmObjectProxy.CacheData<RealmModel>> cache) {
        // This cast is correct because obj is either
        // generated by RealmProxy or the original type extending directly from RealmObject
        @SuppressWarnings("unchecked") Class<E> clazz = (Class<E>) realmObject.getClass().getSuperclass();

        if (clazz.equals(com.example.travelin.DirectMessage.class)) {
            return clazz.cast(io.realm.com_example_travelin_DirectMessageRealmProxy.createDetachedCopy((com.example.travelin.DirectMessage) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.example.travelin.Event.class)) {
            return clazz.cast(io.realm.com_example_travelin_EventRealmProxy.createDetachedCopy((com.example.travelin.Event) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.example.travelin.EventLocation.class)) {
            return clazz.cast(io.realm.com_example_travelin_EventLocationRealmProxy.createDetachedCopy((com.example.travelin.EventLocation) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.example.travelin.MyRating.class)) {
            return clazz.cast(io.realm.com_example_travelin_MyRatingRealmProxy.createDetachedCopy((com.example.travelin.MyRating) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.example.travelin.Post.class)) {
            return clazz.cast(io.realm.com_example_travelin_PostRealmProxy.createDetachedCopy((com.example.travelin.Post) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.example.travelin.Tag.class)) {
            return clazz.cast(io.realm.com_example_travelin_TagRealmProxy.createDetachedCopy((com.example.travelin.Tag) realmObject, 0, maxDepth, cache));
        }
        if (clazz.equals(com.example.travelin.User.class)) {
            return clazz.cast(io.realm.com_example_travelin_UserRealmProxy.createDetachedCopy((com.example.travelin.User) realmObject, 0, maxDepth, cache));
        }
        throw getMissingProxyClassException(clazz);
    }

}
