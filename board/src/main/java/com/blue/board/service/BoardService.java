package com.blue.board.service;

import java.util.List;
import com.blue.board.domain.BoardVO;

public interface BoardService {
    public List<BoardVO> getList();
    public void register(BoardVO board);
    public BoardVO get(Long bno);
    public void modify(BoardVO board);
    public void remove(Long bno);
}