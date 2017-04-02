package com.cremy.firebucket.data.entities.mappers;

import com.cremy.firebucket.data.entities.TagListEntity;
import com.cremy.firebucket.domain.models.TagListModel;

/**
 * Created by remychantenay on 23/02/2017.
 */
public class TagListMapper {

    public static TagListModel transform(TagListEntity tagListEntity) {
        TagListModel tagListModel = new TagListModel();
        if (tagListEntity != null) {
            tagListModel.setTags(tagListEntity.getTags());
        }

        return tagListModel;
    }
}
