package spring.mvc.reboard;

import java.io.File;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import board.data.BoardDaoInter;

@Controller
public class BoardDeleteController {

	@Autowired
	BoardDaoInter dao;
	
	@GetMapping("/board/deletepassform")
	public ModelAndView deletepassForm(
			@RequestParam String num, @RequestParam String currentPage)
	{
		ModelAndView model = new ModelAndView();
		model.addObject("num", num);
		model.addObject("currentPage", currentPage);
		
		model.setViewName("board/deletepassform");
		
		
		return model;
	}
	
	@PostMapping("/board/delete")
	public String delete(
			@RequestParam String num,
			@RequestParam String currentPage,
			@RequestParam String pass,
			HttpSession session) {
		
		
		//��й�ȣ�� ��ġ�ϴ��� üũ
		int check = dao.getCheckPass(num, pass);
		//��й�ȣ�� ��ġ���� ���� ���
		if(check==0) {
			return "board/passfail";
		} else { //����� �´� ���
			//���� ���� ���� �����
			String photo = dao.getData(Integer.parseInt(num)).getPhoto();
			if(!photo.equals("no")) {
				// ,�� �и��ؼ� ���� ���� ���� �迭�� ���
				String [] fName= photo.split(","); 
				
				//���� ���ε� ���
				String path = session.getServletContext().getRealPath("/WEB-INF/photo");
				
				for(String f: fName) {
					File file = new File(path+"\\"+f);
					file.delete();
				}
			}
		}
		
		//db ����
		dao.deleteBoard(Integer.parseInt(num));
		
		return "redirect:list?currentPage="+currentPage;
	}
	
}
