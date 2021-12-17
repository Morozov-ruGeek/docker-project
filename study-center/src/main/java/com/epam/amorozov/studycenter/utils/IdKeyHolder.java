package com.epam.amorozov.studycenter.utils;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
public class IdKeyHolder implements KeyHolder {

    /**
     * Copied from GeneratedKeyHolder for educational purposes
     */

    private final List<Map<String, Object>> keyList;

    public IdKeyHolder() {
        this.keyList = new ArrayList<>(1);
    }

    @Override
    public Number getKey() throws InvalidDataAccessApiUsageException {
        return getKeyAs(Number.class);
    }

    @Override
    public <T> T getKeyAs(Class<T> keyType) throws InvalidDataAccessApiUsageException {
        if (this.keyList.isEmpty()) {
            return null;
        }
        if (this.keyList.size() > 1 || this.keyList.get(0).size() > 1) {
            throw new InvalidDataAccessApiUsageException(
                    "The getKey method should only be used when a single key is returned. " +
                            "The current key entry contains multiple keys: " + this.keyList);
        }
        Iterator<Object> keyIter = this.keyList.get(0).values().iterator();
        if (keyIter.hasNext()) {
            Object key = keyIter.next();
            if (key == null || !(keyType.isAssignableFrom(key.getClass()))) {
                throw new DataRetrievalFailureException(
                        "The generated key type is not supported. " +
                                "Unable to cast [" + (key != null ? key.getClass().getName() : null) +
                                "] to [" + keyType.getName() + "].");
            }
            return keyType.cast(key);
        }
        else {
            throw new DataRetrievalFailureException("Unable to retrieve the generated key. " +
                    "Check that the table has an identity column enabled.");
        }
    }

    @Override
    public Map<String, Object> getKeys() throws InvalidDataAccessApiUsageException {
        return null;
    }

    @Override
    public List<Map<String, Object>> getKeyList() {
        return null;
    }
}
