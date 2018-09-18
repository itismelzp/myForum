package com.forum.service;

import com.forum.dao.BoardDao;
import com.forum.dao.Page;
import com.forum.dao.TopicDao;
import com.forum.domain.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lizhiping03 on 2018/9/17.
 */

@Service
public class ForumService {

    @Autowired
    private BoardDao boardDao;

    @Autowired
    private TopicDao topicDao;

    public void addBoard(Board board) {
        boardDao.save(board);
    }

    public void removeBoard(int boardId) {
        Board board = boardDao.get(boardId);
        boardDao.remove(board);
    }

    public Board getBoardById(int baordId) {
        return boardDao.get(baordId);
    }

    public List<Board> getAllBoards() {
        return boardDao.loadAll();
    }

    public Page getPagedTopics(int boardId, int pageNo, int pageSize) {
        return topicDao.getPagedTopics(boardId, pageNo, pageSize);
    }
}
