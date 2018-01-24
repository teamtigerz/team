package com.spring.client.board.controller;

import java.io.IOException;
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
import com.spring.common.page.Paging;

@Controller
@RequestMapping(value = "/board")
public class BoardController {
	Logger logger = Logger.getLogger(BoardController.class);

	@Autowired
	private BoardService boardService;

	/**************************************************************
	 * �۸�� �����ϱ�
	 **************************************************************/
	@RequestMapping(value = "/boardList", method = RequestMethod.GET)
	public String boardList(@ModelAttribute BoardVO bvo, Model model) {
		logger.info("boardList ȣ�� ����");
		
		Paging.setPage(bvo);
		
		int total = boardService.boardListCnt(bvo);
		
		logger.info("total = " + total);

		List<BoardVO> boardList = boardService.boardList(bvo);
		model.addAttribute("boardList", boardList);
		model.addAttribute("data", bvo);

		return "board/boardList";
	}

	/**************************************************************
	 * �۾��� �� ����ϱ�
	 **************************************************************/
	@RequestMapping(value = "/writeForm.do")
	public String writeForm() {
		logger.info("writeForm ȣ�� ����");
		return "board/writeForm";
	}

	/**************************************************************
	 * �۾��� �����ϱ�
	 **************************************************************/
	@RequestMapping(value = "/boardInsert.do", method = RequestMethod.POST)
	public String boardInsert(@ModelAttribute BoardVO bvo, Model model) {
		logger.info("boardInsert ȣ�� ����");
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

	/**************************************************************
	 * �� �󼼺��� ����
	 **************************************************************/
	@RequestMapping(value = "/boardDetail.do", method = RequestMethod.GET)
	public String boardDetail(@ModelAttribute BoardVO pvo, Model model) {
		logger.info("boardDetail ȣ�� ����");
		logger.info("b_num = " + pvo.getB_num());
		BoardVO detail = new BoardVO();
		detail = boardService.boardDetail(pvo);
		if (detail != null && (!detail.equals(""))) {
			detail.setB_content(detail.getB_content().toString().replaceAll("\n", "<br>"));
		}
		model.addAttribute("detail", detail);
		return "board/boardDetail";
	}

	/**************************************************************
	 * ��й�ȣ Ȯ��
	 * 
	 * @param b_num
	 * @param b_pwd
	 * @return int�� result�� 0 �Ǵ� 1�� ������ ���� �ְ�, String�� result ���� "���� or ����"�� ������ ����
	 *         �ִ�.(���� ���ڿ� ����) ���� : @ResponseBody�� ���޵� �並 ���ؼ� ����ϴ� ���� �ƴ϶� HTTP
	 *         Response Body�� ���� ����ϴ� ���. produces �Ӽ��� ������ �̵�� Ÿ�԰� ���õ� ������ �����ϴµ� �����
	 *         ���� ����Ʈ Ÿ���� ����.
	 **************************************************************/
	@ResponseBody
	@RequestMapping(value = "/pwdConfirm.do", method = RequestMethod.POST, produces = "text/plain; charset=UTF-8")
	public String pwdConfirm(@ModelAttribute BoardVO bvo) {
		logger.info("pwdConfirm ȣ�� ����");
		String value = "";
		// �Ʒ� �������� �Է� ������ ���� ���°� ����(1 or 0)
		int result = boardService.pwdConfirm(bvo);
		if (result == 1) {
			value = "����";
		} else {
			value = "����";
		}
		logger.info("result = " + result);
		return value + "";
	}

	/**************************************************************
	 * �ۼ��� �� ����ϱ�
	 * 
	 * @param :
	 *            b_num
	 * @return : BoardVO
	 **************************************************************/
	@RequestMapping(value = "/updateForm.do")
	public String updateForm(@ModelAttribute BoardVO bvo, Model model) {
		logger.info("updateForm ȣ�� ����");
		logger.info("b_num = " + bvo.getB_num());
		BoardVO updateData = new BoardVO();
		updateData = boardService.boardDetail(bvo);
		model.addAttribute("updateData", updateData);
		return "board/updateForm";
	}

	/**************************************************************
	 * �ۼ��� �����ϱ�
	 * 
	 * @param :
	 *            BoardVO
	 **************************************************************/
	@RequestMapping(value = "/boardUpdate.do", method = RequestMethod.POST)
	public String boardUpdate(@ModelAttribute BoardVO bvo) {
		logger.info("boardUpdate ȣ�� ����");
		int result = 0;
		String url = "";
		result = boardService.boardUpdate(bvo);
		if (result == 1) {
			// url="/board/boardList.do"; // ���� �� ������� �̵�
			// �Ʒ� url�� ���� �� �� �������� �̵�
			url = "/board/boardDetail.do?b_num=" + bvo.getB_num();
		} else {
			url = "/board/updateForm.do??b_num=" + bvo.getB_num();
		}
		return "redirect:" + url;
	}

	/**************************************************************
	 * �ۻ��� �����ϱ�
	 * 
	 * @throws IOException
	 **************************************************************/
	@RequestMapping(value = "/boardDelete.do")
	public String boardDelete(@ModelAttribute BoardVO bvo) {
		logger.info("boardDelete ȣ�� ����");
		// �Ʒ� �������� �Է� ������ ���� ���°� ����ϴ�.(1 or 0)
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
