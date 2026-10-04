package com.blue.board.mapper;

import java.util.List;
import com.blue.board.domain.BoardVO;

public interface BoardMapper {
    // xml에 적은 <select id="getList">와 메서드명이 일치해야 합니다.
    public List<BoardVO> getList();
    public void insert(BoardVO board);
    public BoardVO read(Long bno);
    public void update(BoardVO board);
    public void delete(Long bno);
    public void updateViewCnt(Long bno);
}

