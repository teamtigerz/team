package com.spring.client.board.controller;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.spring.client.board.service.BoardService;
import com.spring.client.board.vo.BoardVO;

@Controller
@RequestMapping(value = "/board")
public class BoardController {
	Logger logger = Logger.getLogger(BoardController.class);
	@Autowired
	private BoardService boardService;

	@RequestMapping(value = "/boardList.do", method = RequestMethod.GET)
	public String boardList(@ModelAttribute BoardVO bvo, Model model) {
		logger.info("boardList 호출 성공");
		List<BoardVO> boardList = boardService.boardList();
		model.addAttribute("boardList", boardList);
		model.addAttribute("data", bvo);
		return "board/boardList";
	}

	@RequestMapping(value = "/writeForm.do")
	public String writeForm() {
		logger.info("writeForm 호출 성공");
		return "board/writeForm";
	}

	@RequestMapping(value = "/boardInsert.do", method = RequestMethod.POST)
	public String boardInsert(@ModelAttribute BoardVO bvo, Model model) {
		logger.info("boardInsert 호출 성공");
		int result = 0;
		String url = "";
		result = boardService.boardInsert(bvo);
		if (result == 1) {
			url = "/board/boardList.do";
		} else {
			model.addAttribute("code", 1);
			url = "/board/writeForm.do";
		}
		return "redirect:" + url;
	}

	@RequestMapping(value = "/boardDetail.do", method = RequestMethod.GET)
	public String boardDetail(@ModelAttribute BoardVO pvo, Model model) {
		logger.info("boardDetail 호출 성공");
		logger.info("b_num = " + pvo.getB_num());
		BoardVO detail = new BoardVO();
		detail = boardService.boardDetail(pvo);
		if (detail != null && (!detail.equals(""))) {
			detail.setB_content(detail.getB_content().toString().replaceAll("\n", "<br>"));
		}
		model.addAttribute("detail", detail);
		return "board/boardDetail";
	}

	@ResponseBody
	@RequestMapping(value = "/pwdConfirm.do", method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	public String pwdConfirm(@ModelAttribute BoardVO bvo) {
		logger.info("pwdConfirm 호출 성공");
		String value = "";
		// 아래 변수에는 입력 성공에 대한 상태값 저장(1 or 0)
		int result = boardService.pwdConfirm(bvo);
		if (result == 1) {
			value = "성공";
		} else {
			value = "실패";
		}
		logger.info("result = " + result);
		return value + "";
	}

	@RequestMapping(value = "/updateForm.do")
	public String updateForm(@ModelAttribute BoardVO bvo, Model model) {
		logger.info("updateForm 호출 성공");
		logger.info("b_num = " + bvo.getB_num());
		BoardVO updateData = new BoardVO();
		updateData = boardService.boardDetail(bvo);
		model.addAttribute("updateData", updateData);
		return "board/updateForm";
	}

	@RequestMapping(value = "/boardUpdate.do", method = RequestMethod.POST)
	public String boardUpdate(@ModelAttribute BoardVO bvo) {
		logger.info("boardUpdate 호출 성공");
		int result = 0;
		String url = "";
		result = boardService.boardUpdate(bvo);
		if (result == 1) {
			// url="/board/boardList.do"; // 수정 후 목록으로 이동
			// 아래 url은 수정 후 상세 페이지로 이동
			url = "/board/boardDetail.do?b_num=" + bvo.getB_num();
		} else {
			url = "/board/updateForm.do??b_num=" + bvo.getB_num();
		}
		return "redirect:" + url;
	}

	@RequestMapping(value = "/boardDelete.do")
	public String boardDelete(@ModelAttribute BoardVO bvo) {
		logger.info("boardDelete 호출 성공");
		// 아래 변수에는 입력 성공에 대한 상태값 담습니다.(1 or 0)
		int result = 0;
		String url = "";
		result = boardService.boardDelete(bvo.getB_num());
		if (result == 1) {
			url = "/board/boardList.do";
		} else {
			url = "/board/boardDetail.do?b_num=" + bvo.getB_num();
		}
		return "redirect:" + url;
	}
}
