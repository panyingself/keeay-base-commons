

package com.keeay.anepoch.base.commons.base.query;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ying.pan
 * @date 2020/3/29 8:33 PM.
 */
public class BasicQuery extends HashMap<String, Object> {
    private static final String KEY_OFFSET = "_offset";
    private static final String KEY_LIMIT = "_limit";
    private static final String ORDER_BY = "_orderby";
    private static final String ORDER_SEQ = "_seq";
    private static final String DESC = "desc";

    public BasicQuery() {
    }

    public Map<String, Object> getParamMap() {
        return Collections.unmodifiableMap(this);
    }

    public boolean hasParam(String name) {
        return this.containsKey(name);
    }

    public Object getParam(String name) {
        return this.get(name);
    }

    public BasicQuery setPaging(int pageNum, int pageSize) {
        if (pageNum < 1) {
            throw new IllegalArgumentException("page num should >= 1");
        } else if (pageSize < 1) {
            throw new IllegalArgumentException("page size should >= 1 ");
        } else {
            long offset = (long) ((pageNum - 1) * pageSize);
            super.put(KEY_OFFSET, offset);
            super.put(KEY_LIMIT, pageSize);
            return this.returnThis();
        }
    }

    public BasicQuery setOrderBy(String... conditions) {
        if (ArrayUtils.isNotEmpty(conditions)) {
            super.put(ORDER_BY, Lists.newArrayList(conditions));
        }

        return this.returnThis();
    }

    public BasicQuery setOrderDesc(boolean orderByDesc) {
        if (orderByDesc) {
            super.put(ORDER_SEQ, DESC);
        }

        return this.returnThis();
    }

    private BasicQuery returnThis() {
        return this;
    }
}
