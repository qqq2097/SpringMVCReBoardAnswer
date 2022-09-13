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
		
		
		//비밀번호가 일치하는지 체크
		int check = dao.getCheckPass(num, pass);
		//비밀번호가 일치하지 않은 경우
		if(check==0) {
			return "board/passfail";
		} else { //비번이 맞는 경우
			//포토 폴더 사진 지우기
			String photo = dao.getData(Integer.parseInt(num)).getPhoto();
			if(!photo.equals("no")) {
				// ,로 분리해서 여러 개의 사진 배열에 담기
				String [] fName= photo.split(","); 
				
				//실제 업로드 경로
				String path = session.getServletContext().getRealPath("/WEB-INF/photo");
				
				for(String f: fName) {
					File file = new File(path+"\\"+f);
					file.delete();
				}
			}
		}
		
		//db 삭제
		dao.deleteBoard(Integer.parseInt(num));
		
		return "redirect:list?currentPage="+currentPage;
	}
	
}
