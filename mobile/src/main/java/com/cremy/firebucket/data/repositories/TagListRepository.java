package com.cremy.firebucket.data.repositories;

import com.cremy.firebucket.data.entities.TagListEntity;
import com.cremy.firebucket.data.entities.mappers.TagListMapper;
import com.cremy.firebucket.data.repositories.datasource.TagListDataSourceRemote;
import com.cremy.firebucket.domain.models.TagListModel;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.functions.Function;


/**
 * The repository is in charge of getting the data from the DataSource,
 * It will also map data from entity to model
 * Created by remychantenay on 23/02/2017.
 */
@Singleton
public class TagListRepository {

    private final TagListDataSourceRemote tagListDataSourceRemote;

    @Inject
    public TagListRepository(TagListDataSourceRemote tagListDataSourceRemote) {
        this.tagListDataSourceRemote = tagListDataSourceRemote;
    }

    public Observable<TagListModel> getTagList() {

        return tagListDataSourceRemote.getTagList().map(new Function<TagListEntity, TagListModel>() {
            @Override
            public TagListModel apply(TagListEntity tagListEntity) throws Exception {
                return TagListMapper.transform(tagListEntity);
            }
        });
    }
}
