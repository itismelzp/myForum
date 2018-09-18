package com.forum.web;

import com.forum.dao.Page;
import com.forum.domain.Board;
import com.forum.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by lizhiping03 on 2018/9/17.
 */

@Controller
public class BoardManageController extends BaseController {

    @Autowired
    private ForumService forumService;

    @RequestMapping(value = "/board/listBoardTopics-{boardId}", method = RequestMethod.GET)
    public ModelAndView listBoardTopics(@PathVariable Integer boardId, @RequestParam(required = false) Integer pageNo) {

        ModelAndView mav = new ModelAndView();
        Board board = forumService.getBoardById(boardId);
        pageNo =  pageNo == null ? 1 : pageNo;
        Page pagedTopic = forumService.getPagedTopics(boardId, pageNo, 3);
        mav.addObject("board", board);
        mav.addObject("pagedTopic", pagedTopic);
        mav.setViewName("/listBoardTopics");
        return mav;
    }


}
