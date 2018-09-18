package com.forum.dao;

import com.forum.domain.Board;
import org.springframework.stereotype.Repository;

import java.util.Iterator;

/**
 * Created by lizhiping03 on 2018/9/17.
 */

@Repository
public class BoardDao extends BaseDao<Board> {

    private static final String GET_BOARD_NUM = "select count(f.boardId) from Board f";

    public long getBoardNum() {
        Iterator iter = hibernateTemplate.iterate(GET_BOARD_NUM);
        return (Long) iter.next();
    }
}
