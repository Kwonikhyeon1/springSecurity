package com.office.test.board;


import com.office.test.board.page.PageDefine;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequestMapping("/board")
public class BoardController {

    final private BoardService boardService;

    public BoardController(final BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping({"", "/"})
    public String home() {
        log.info("home");

        return "redirect:/board/list";
    }

//    list
    @GetMapping("/list")
    public String list (Model model,
                        @RequestParam(value = "pageNum", required = false, defaultValue = PageDefine.DEFAULT_PAGE_NUMBER) int pageNum,
                        @RequestParam(value = "amount", required = false, defaultValue = PageDefine.DEFAULT_AMOUNT) int amount) {
        log.info("list");

        String nextPage = "board/list";

        Map<String, Object> resultMap = boardService.list(pageNum, amount);
        model.addAttribute("resultMap", resultMap);

        return nextPage;
    }

    //인증 없이 접근시
    @GetMapping("authentication_entry_point")
    public String authenticationEntryPoint() {
        log.info("authenticationEntryPoint()");

        String nextPage = "board/authentication_entry_point";

        return nextPage;
    }

    //게시물 작성
    @GetMapping("/write_form")
    public String write() {
        log.info("write");

        String nextPage = "board/board_write";

        return nextPage;
    }

    @PostMapping("/write_confirm")
    public String writeConfirm(Principal principal, BoardDto boardDto) {
        log.info("writeConfirm");

        String nextPage = "redirect:/board/list";

        String loginedSeassionID = principal.getName();
        boardDto.setB_owner_id(loginedSeassionID);

        int result =  boardService.writeConfirm(boardDto);
        if (result <= 0) {

            nextPage = "board/board_write_ng";

            return nextPage;
        }
        return nextPage;
    }
}
