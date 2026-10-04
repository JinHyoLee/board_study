package com.blue.board.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.blue.board.domain.BoardVO;
import com.blue.board.mapper.BoardMapper;

@Service // 이 어노테이션을 붙여야 스프링이 서비스로 인식합니다.
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardMapper mapper; // 아까 만든 Mapper를 주입받습니다.

    @Override
    public List<BoardVO> getList() {
        return mapper.getList(); // 마이바티스를 통해 DB에서 리스트를 가져옵니다.
    }
    
    @Override
    public void register(BoardVO board) {
        System.out.println("====== 서비스 계층에서 글 등록 수행 ======");
        mapper.insert(board);
    }
    
    @Override
    public BoardVO get(Long bno) {
    	System.out.println("====== 서비스 계층에서 조회수 증가 및 상세보기 수행 ======");
        mapper.updateViewCnt(bno); // 1. 조회수 올리고
    	return mapper.read(bno); // 2. 글 세부 내용 DB에서 읽어서 리턴
    }

    @Override
    public void modify(BoardVO board) {
    	System.out.println("====== 서비스 계층에서 글 수정 수행 ======");
		mapper.update(board);
    }
       
    @Override
    public void remove(Long bno) {
        System.out.println("====== 서비스 계층에서 글 삭제 수행 ======");
        mapper.delete(bno);
    }
}
