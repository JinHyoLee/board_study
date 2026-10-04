package com.blue.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.blue.board.domain.BoardVO;
import com.blue.board.service.BoardService;

@Controller
@RequestMapping("/board/*") // 주소창에 http://localhost:8089/board/로 시작하는 요청을 이 컨트롤러가 받습니다.
public class BoardController {

    @Autowired
    private BoardService service; // 서비스 부품 주입

    // 0. 게시글 목록 보기 요청 처리
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public void list(Model model) {
        System.out.println("====== 게시글 목록 화면 요청 수행 ======");
        
        // DB에서 가져온 리스트를 'list'라는 이름으로 화면(JSP)에 던져줍니다.
        model.addAttribute("list", service.getList());
    }
    
    // 1. 글쓰기 화면 띄우기 (GET)
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public void registerGET() {
        System.out.println("====== 글쓰기 등록 화면 요청 (GET) ======");
    }

    // 2. 글쓰기 실제 처리 (POST)
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    
    public String registerPOST(BoardVO board) {
        System.out.println("====== 글쓰기 실제 데이터 처리 (POST) ======");
        
        service.register(board); // DB에 저장
        
        // 글 저장이 끝났으면 다시 목록 화면으로 강제 이동(Redirect) 시킵니다.
        return "redirect:/board/list";
    }
    
    // 3. 게시글 상세보기 및 수정 페이지 띄우기 (두 주소 모두 같은 데이터를 사용하므로 묶어서 처리합니다)
    @RequestMapping(value = {"/get", "/modify"}, method = RequestMethod.GET)
    public void get(@RequestParam("bno") Long bno, Model model) {
        System.out.println("====== 글 상세보기 또는 수정 화면 요청 (GET) ======");
        // 주소창에서 넘어온 bno 값으로 DB를 조회해 'board'라는 이름으로 던집니다.
        model.addAttribute("board", service.get(bno));
    }

    // 4. 게시글 실제 수정 처리 (POST)
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String modifyPOST(BoardVO board) {
        System.out.println("====== 글 실제 수정 처리 (POST) ======");
        
        service.modify(board); // DB 수정
        
        // 수정한 뒤에는 다시 목록 화면으로 튕겨 보냅니다.
        return "redirect:/board/list";
    }
    
    // 5. 게시글 실제 삭제 처리 (POST)
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(@RequestParam("bno") Long bno) {
        System.out.println("====== 글 실제 삭제 처리 (POST) ======");
        
        service.remove(bno); // DB에서 삭제
        
        return "redirect:/board/list"; // 다시 목록으로
    }
}