package spring.mvc.reboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import answer.data.AnswerDaoInter;
import answer.data.AnswerDto;
import board.data.BoardDaoInter;
import board.data.BoardDto;

@Controller
public class BoardContentController {

	@Autowired //�ڵ�����
	BoardDaoInter dao;
	@Autowired
	AnswerDaoInter adao;
	
	@GetMapping("/board/content")
	public ModelAndView content(
			@RequestParam int num, 
			@RequestParam int currentPage) {
		ModelAndView model = new ModelAndView();
		
		//ī��Ʈ ����
		dao.updateReadCount(num);
		
		//�ϳ��� ������
		BoardDto dto = dao.getData(num);
		
		model.addObject("dto", dto);
		model.addObject("currentPage", currentPage);
		
		//num�� �ش��ϴ� ����� Answer�� db���� ������ ������
		List<AnswerDto> alist = adao.getAnswerList(num);
		
		//����� ���� �� ������ �ϹǷ�
		model.addObject("alist", alist); //��� ����
		model.addObject("acount", alist.size()); //��� ����
		
		
		
		model.setViewName("board/content");
		
		return model;
	}
	
}
