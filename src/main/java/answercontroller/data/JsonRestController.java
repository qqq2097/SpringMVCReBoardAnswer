package answercontroller.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import board.data.BoardDaoInter;
import board.data.BoardDto;

@RestController // json����, �Ϲ� ��Ʈ�ѷ��� ����!
public class JsonRestController {

	@Autowired
	BoardDaoInter dao;

	// json �������� ������ @ResponseBody
	@GetMapping("/sist/list2")
	public List<BoardDto> list() {
		return dao.getList(0, 5);
	}

	@GetMapping("/sist/data2")
	public BoardDto readData(@RequestParam int num) {
		return dao.getData(num);
	}

}
