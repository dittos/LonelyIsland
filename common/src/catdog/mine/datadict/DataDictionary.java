package catdog.mine.datadict;

import java.util.Map;

/**
 * 데이터 레퍼런스를 소유하는 클래스
 * @author ReB
 *
 */
public abstract class DataDictionary<T> {
	private Map<String, T> data;
	
	/**
	 * 데이터 로드
	 */
	public abstract void Load();
	public T getReference(String id) {
		return data.get(id);
	}
}
