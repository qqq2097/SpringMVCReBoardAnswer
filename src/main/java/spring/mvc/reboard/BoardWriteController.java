package spring.mvc.reboard;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import board.data.BoardDaoInter;
import board.data.BoardDto;

@Controller
public class BoardWriteController {

	@Autowired
	BoardDaoInter dao;
	
	//����, ��� ��� �ش�
	@GetMapping("/board/writeform")
	public ModelAndView form(
			@RequestParam Map<String, String> map
			) {
		
		ModelAndView model = new ModelAndView();
		//�Ʒ� 5���� ���� ����� ��쿡�� �Ѿ�´�(������ �ȳѾ��)
		String currentPage = map.get("currentPage");
		String num = map.get("num");
		String regroup = map.get("regroup");
		String relevel = map.get("relevel");
		String restep = map.get("restep");
		
		System.out.println(currentPage+","+num); //������ ��� null �� ����
		//����̸� ���� ����
		
		//�Է����� hidden���� �־���� ��... ����� �� ���
		model.addObject("currentPage", currentPage==null?"1":currentPage);
		model.addObject("num", num==null?"0":num); //0�̾�� dao���� �� �۷� �ν�
		model.addObject("relevel", relevel==null?"0":relevel);
		model.addObject("regroup", regroup==null?"0":regroup);
		model.addObject("restep", restep==null?"0":restep);
		
		//0�̶�� �־�� ���۷� �ν��ϹǷ�
		//���� ����, ��� ����
		model.setViewName("board/writeform");
		
		return model;
	}
	
	@PostMapping("/board/insert")
	public String insert(@ModelAttribute BoardDto dto,
			@RequestParam String currentPage,
			@RequestParam ArrayList<MultipartFile> upload, 
			HttpSession session) 
	{
		//upload�� ���� ���
		String path = session.getServletContext().getRealPath("/WEB-INF/photo");
		System.out.println(path);
		
		String photo="";
		
		//���� ���� �� ���� ��� no, ���� ��� ,�� ����
		if(upload.get(0).getOriginalFilename().equals(""))
			photo="no";
		else {
			for(MultipartFile f:upload)
			{
				String fName=f.getOriginalFilename();
				photo+=fName+",";
				
				//���ε�
				try {
					f.transferTo(new File(path+"\\"+fName));
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			//photo���� ������ �ĸ� ����
			photo = photo.substring(0, photo.length()-1);
		}
		
		//dto�� photo�� �־��ֱ�
		dto.setPhoto(photo);
		
		//dao���� insert ȣ��
		dao.insertReboard(dto);
		
		//�ƽ� num
		int num = dao.getMaxNum();
		
		return "redirect:content?num="+num+"&currentPage="+currentPage; //content ���� �Ŀ� content�� �̵��ϰ� ���� ����
	}
}
