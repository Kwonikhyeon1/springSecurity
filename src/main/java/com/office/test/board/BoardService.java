package com.office.test.board;


import com.office.test.board.page.Criteria;
import com.office.test.board.page.PageMaker;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class BoardService {

    final private BoardMapper boardMapper;

    public BoardService(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    public Map<String, Object> list(int pageNum, int amount) {
        log.info("list()");

        Map<String, Object> resultMap = new HashMap<>();

        Criteria criteria = new Criteria(pageNum, amount);

        List<BoardDto> boardDtos = boardMapper.getAllPost(criteria);

        int totalCnt = boardMapper.getTotalCnt();
        PageMaker pageMaker = new PageMaker(criteria, totalCnt);
        log.info("pageMaker--->{}", pageMaker);


        resultMap.put("pageMaker", pageMaker);
        resultMap.put("boardDtos", boardDtos);

        return resultMap;
    }

    public int writeConfirm(BoardDto boardDto) {
        log.info("writeConfirm()");

        return boardMapper.insertNewBoard(boardDto);
    }
}
