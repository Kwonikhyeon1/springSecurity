package com.office.test.board;

import com.office.test.board.page.Criteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardDto> getAllPost(Criteria criteria);

    int getTotalCnt();

    int insertNewBoard(BoardDto boardDto);
}
