package catdog.mine.datadict;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;

/**
 * ������ ���۷����� �����ϴ� Ŭ����
 * @author ReB
 *
 */
public abstract class DataDictionary<T> {
	protected Map<String, T> data;
	
	public DataDictionary() {
		data = new HashMap<String, T>();
	}
	
	/**
	 * �ʿ��� ���� ��� �о����
	 */
	public abstract void Load();
	
	/**
	 * ����Ʈ�� �ִ� ���� ��� �ҷ�����
	 * @param listfile ����Ʈ ���� �̸�
	 */
	protected void Load(String listfile) {
		// ����Ʈ ������ �о ���� ������ �ɰ���
		String[] lines = Gdx.files.internal(listfile).readString()
						.split(System.getProperty("line.separator"));
		
		for(int i = 0; i < lines.length; i++) {
			// �� ���ο� ������ : ID[�ǹ���]���ϸ�
			String [] splitdata = lines[i].split("\t");
			this.loadFile(splitdata[0], splitdata[1]);
		}
	}
	
	/**
	 * ���� ���� �о����
	 * @param id : ������ id
	 * @param filename : ���� �̸�
	 */
	public abstract void loadFile(String id, String filename);
	
	/**
	 * ���۷��� ���ϱ�
	 * @param id : ������ id
	 * @return ������ ���۷���
	 */
	public T getReference(String id) {
		return data.get(id);
	}
}
