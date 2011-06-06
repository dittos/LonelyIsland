package catdog.mine.datadict;

import java.util.HashMap;
import java.util.Map;

/**
 * 데이터 레퍼런스를 소유하는 클래스
 * @author ReB
 *
 */
public abstract class DataDictionary<T> {
	private Map<String, T> data;
	
	public DataDictionary() {
		data = new HashMap<String, T>();
	}
	
	/**
	 * 데이터 로드
	 */
	public abstract void Load();
	
	/**
	 * 레퍼런스 구하기
	 * @param id : 데이터 id
	 * @return 데이터 레퍼런스
	 */
	public T getReference(String id) {
		return data.get(id);
	}
}
