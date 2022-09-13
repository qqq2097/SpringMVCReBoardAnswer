package answer.data;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

public class AnswerDao extends SqlSessionDaoSupport implements AnswerDaoInter {

	@Override
	public void insertAnswer(AnswerDto dto) {
		getSqlSession().insert("insertOfReAnswer", dto);
		
	}

	@Override
	public List<AnswerDto> getAnswerList(int num) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList("selectNumOfReAnswer", num); 
	}

	@Override
	public int getCheckPass(int idx, String pass) {
		// TODO Auto-generated method stub
		//파라미터가 하나는 int이고 하나는 pass일 때 object로
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("idx", idx);
		map.put("pass", pass);
		return getSqlSession().selectOne("PassCheckOfReAnswer", map);
	}

	@Override
	public void deleteAnswer(int idx) {
		getSqlSession().delete("DeleteOfReAnswer", idx);
		
	}

}
