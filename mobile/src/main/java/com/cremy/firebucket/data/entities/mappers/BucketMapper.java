package com.cremy.firebucket.data.entities.mappers;

import com.cremy.firebucket.data.entities.BucketEntity;
import com.cremy.firebucket.data.entities.TaskEntity;
import com.cremy.firebucket.domain.models.BucketModel;
import com.cremy.firebucket.domain.models.TaskModel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by remychantenay on 23/02/2017.
 */
public class BucketMapper {

    public static BucketModel transform(BucketEntity bucketEntity) {
        BucketModel bucketModel = null;
        if (bucketEntity != null) {
            bucketModel = new BucketModel();
            if (bucketEntity.getTasks() != null) {
                bucketModel.setTasks(transform(bucketEntity.getTasks()));
            }
        }

        return bucketModel;
    }

    private static HashMap<String, TaskModel> transform(HashMap<String, TaskEntity> tasks) {
        HashMap<String, TaskModel> result = new HashMap<>();
        Iterator it = tasks.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, TaskEntity> pair = (Map.Entry)it.next();
            result.put(pair.getKey(), TaskMapper.transform(pair.getValue()));
        }

        return result;
    }
}
