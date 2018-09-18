package com.forum.dao;

import com.forum.domain.Topic;
import org.springframework.stereotype.Repository;

/**
 * Created by lizhiping03 on 2018/9/18.
 */

@Repository
public class TopicDao extends BaseDao<Topic> {

    public static final String GET_PAGED_BOPICS = "from Topic where boardId = ? order by lastPost desc";

    public Page getPagedTopics(int boardId, int pageNo, int pageSize) {
        return pagedQuery(GET_PAGED_BOPICS, pageNo, pageSize, boardId);
    }
}
