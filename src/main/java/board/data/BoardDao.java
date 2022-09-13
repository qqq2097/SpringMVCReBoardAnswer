package board.data;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

public class BoardDao extends SqlSessionDaoSupport implements BoardDaoInter {

	@Override
	public int getTotalCount() {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("TotalCountOfReboard");
	}

	@Override
	public int getMaxNum() {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("MaxNumOfReboard");
	}

	@Override
	public void updateRestep(int regroup, int restep) {
		// TODO Auto-generated method stub
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("regroup", regroup);
		map.put("restep", restep);
		getSqlSession().update("UpdateStepOfReboard", map);
	}

	@Override
	public void insertReboard(BoardDto dto) {
		// TODO Auto-generated method stub
		int num=dto.getNum(); //0: ����, 1���� ū ��: ���
		int regroup = dto.getRegroup();
		int restep = dto.getRestep();
		int relevel = dto.getRelevel();
		
		if(num==0) //����
		{
			regroup = getMaxNum()+1; //num�� �ִ밪����+1
			restep = 0;
			relevel=0;
		}else {
			//���� �׷� �߿��� ���޹��� restep���� ū �۵��� ��� +1
			this.updateRestep(regroup, restep);
			//���޹��� step�� level�� +1
			restep++;
			relevel++;
		}
		
		//�ٲ� ������ �ٽ� dto�� ��´�
		dto.setRegroup(regroup);
		dto.setRestep(restep);
		dto.setRelevel(relevel);
		
		//insert
		getSqlSession().insert("insertOfReboard",dto);
	}

	@Override
	public List<BoardDto> getList(int start, int perpage) {
		// TODO Auto-generated method stub
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("start", start);
		map.put("perpage", perpage);
		
		return getSqlSession().selectList("SelectPagingOfReboard", map);
	}

	@Override
	public void updateReadCount(int num) {
		// TODO Auto-generated method stub
		getSqlSession().update("UpdateReadCountOfReboard", num);
	}

	@Override
	public BoardDto getData(int num) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne("GetDataOfReboard", num);
	}

	@Override
	public int getCheckPass(String num, String pass) {
		// TODO Auto-generated method stub
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("num", num);
		map.put("pass", pass);
	
		return getSqlSession().selectOne("CheckPassEqualOfReboard",map);
	}

	@Override
	public void updateBoard(BoardDto dto) {
		getSqlSession().update("UpdateOfReboard", dto);
		
	}

	@Override
	public void deleteBoard(int num) {
		// TODO Auto-generated method stub
		getSqlSession().delete("DeleteOfBoard", num);
	}

	@Override
	public List<BoardDto> getAllDatas() {
		// TODO Auto-generated method stub
		return getSqlSession().selectList("SelectAllOfReboard");
	}

}
