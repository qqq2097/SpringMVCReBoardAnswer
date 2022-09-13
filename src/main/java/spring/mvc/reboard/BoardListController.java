package spring.mvc.reboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import answer.data.AnswerDaoInter;
import board.data.BoardDaoInter;
import board.data.BoardDto;

@Controller
public class BoardListController {

	@Autowired
	BoardDaoInter dao;
	
	@Autowired
	AnswerDaoInter adao;
	
	@GetMapping("/")
	public String start() {
		return "redirect:board/list";
	}
	
	
	@GetMapping("/board/list")
	public ModelAndView list(@RequestParam(value="currentPage", defaultValue = "1") int currentPage) 
	{
		ModelAndView model = new ModelAndView();
		int totalCount = dao.getTotalCount();
		
		//����¡ó���� �ʿ��� ����
		int totalPage; //�� ��������
		int startPage; //������ ����������
		int endPage; //������ ��������
		int start; //���������� ���۹�ȣ
		int perPage=5; //���������� ������ �� ����
		int perBlock=5; //�Ѻ��� �������� ������ ����

		

		//�������� �������ϱ�
		totalPage=totalCount/perPage+(totalCount%perPage==0?0:1);

		//������ ����������
		//��:������������ 3�ΰ�� startpage=1,endpage= 5
		//������������ 6�ΰ�� startpage=6,endpage= 10
		startPage=(currentPage-1)/perBlock*perBlock+1;
		endPage=startPage+perBlock-1;

		//���� ���������� 8 -2��°��: 6-10 ..�̷����� endpage�� 8�� �����Ǿ����
		if(endPage>totalPage)
		   endPage=totalPage;

		//������������ �ҷ��� ���۹�ȣ
		start=(currentPage-1)*perPage;

		//������������ �ʿ��� �Խñ� ��������
		//autowired ������ ������ dao
		List<BoardDto> list=dao.getList(start, perPage);
		
		//list(���)��  �� �ۿ� ���� ��� ���� �߰�
		for(BoardDto d:list) {
			d.setAcount(adao.getAnswerList(d.getNum()).size());
		}
		

		//�� �۾տ� ���� ���۹�ȣ ���ϱ�
		//�ѱ��� 20����? 1������ 20 2������ 15���� ����ؼ� 1�� ����
		int no=totalCount-(currentPage-1)*perPage;

		
		//��¿� �ʿ��� �������� request�� ����
		model.addObject("list", list); //��� ���� ���� �� ����
		model.addObject("startPage", startPage);
		model.addObject("endPage", endPage);
		model.addObject("totalPage", totalPage);
		model.addObject("no", no);
		model.addObject("currentPage", currentPage);
		model.addObject("totalCount", totalCount);
		
		
		model.addObject("totalCount", totalCount);
		model.setViewName("board/boardlist");
		
		return model;
	}
}
