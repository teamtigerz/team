package com.spring.client.board.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.client.board.dao.BoardDao;
import com.spring.client.board.vo.BoardVO;

@Service
@Transactional
public class BoardServiceImpl implements BoardService {
	Logger logger = Logger.getLogger(BoardServiceImpl.class);

	@Autowired
	private BoardDao boardDao;

	// �۸�� ����
	@Override
	public List<BoardVO> boardList() {
		List<BoardVO> myList = null;
		myList = boardDao.boardList();
		return myList;
	}

	// ���Է� ����
	@Override
	public int boardInsert(BoardVO bvo) {
		int result = 0;
		try {
			result = boardDao.boardInsert(bvo);
		} catch (Exception e) {
			e.printStackTrace();
			result = 0;
		}
		return result;
	}

	// �ۻ� ����
	@Override
	public BoardVO boardDetail(BoardVO bvo) {
		BoardVO detail = null;
		detail = boardDao.boardDetail(bvo);
		return detail;
	}

	// ��й�ȣ Ȯ�� ����
	@Override
	public int pwdConfirm(BoardVO bvo) {
		int result = 0;
		result = boardDao.pwdConfirm(bvo);
		return result;
	}

	// �ۼ��� ����
	@Override
	public int boardUpdate(BoardVO bvo) {
		int result = 0;
		try {
			result = boardDao.boardUpdate(bvo);
		} catch (Exception e) {
			e.printStackTrace();
			result = 0;
		}
		return result;
	}

	// �ۻ��� ����
	@Override
	public int boardDelete(int b_num) {
		int result = 0;
		try {
			result = boardDao.boardDelete(b_num);
		} catch (Exception e) {
			e.printStackTrace();
			result = 0;
		}
		return result;
	}

}
