package catdog.mine.datadict;

import java.util.Map;

/**
 * ������ ���۷����� �����ϴ� Ŭ����
 * @author ReB
 *
 */
public abstract class DataDictionary<T> {
	private Map<String, T> data;
	
	/**
	 * ������ �ε�
	 */
	public abstract void Load();
	public T getReference(String id) {
		return data.get(id);
	}
}
