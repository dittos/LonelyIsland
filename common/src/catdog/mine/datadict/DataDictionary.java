package catdog.mine.datadict;

import java.util.HashMap;
import java.util.Map;

/**
 * ������ ���۷����� �����ϴ� Ŭ����
 * @author ReB
 *
 */
public abstract class DataDictionary<T> {
	private Map<String, T> data;
	
	public DataDictionary() {
		data = new HashMap<String, T>();
	}
	
	/**
	 * ������ �ε�
	 */
	public abstract void Load();
	
	/**
	 * ���۷��� ���ϱ�
	 * @param id : ������ id
	 * @return ������ ���۷���
	 */
	public T getReference(String id) {
		return data.get(id);
	}
}
