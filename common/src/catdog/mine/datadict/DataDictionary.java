package catdog.mine.datadict;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;

/**
 * 데이터 레퍼런스를 소유하는 클래스
 * @author ReB
 *
 */
public abstract class DataDictionary<T> {
	protected Map<String, T> data;
	
	public DataDictionary() {
		data = new HashMap<String, T>();
	}
	
	/**
	 * 필요한 파일 모두 읽어오기
	 */
	public abstract void Load();
	
	/**
	 * 리스트에 있는 파일 모두 불러오기
	 * @param listfile 리스트 파일 이름
	 */
	protected void Load(String listfile) {
		// 리스트 파일을 읽어서 라인 단위로 쪼개기
		String[] lines = Gdx.files.internal(listfile).readString()
						.split(System.getProperty("line.separator"));
		
		for(int i = 0; i < lines.length; i++) {
			// 한 라인에 데이터 : ID[탭문자]파일명
			String [] splitdata = lines[i].split("\t");
			this.loadFile(splitdata[0], splitdata[1]);
		}
	}
	
	/**
	 * 단일 파일 읽어오기
	 * @param id : 데이터 id
	 * @param filename : 파일 이름
	 */
	public abstract void loadFile(String id, String filename);
	
	/**
	 * 레퍼런스 구하기
	 * @param id : 데이터 id
	 * @return 데이터 레퍼런스
	 */
	public T getReference(String id) {
		return data.get(id);
	}
}
