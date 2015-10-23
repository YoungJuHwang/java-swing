package lotto;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

/**
 * @file_name : Lotto.java
 * @author    : pheonix0717@naver.com
 * @date      : 2015. 10. 22.
 * @story     : 로또 알고리즘.
 */
public class LottoProgram { //static : 실행하자마자 시작되는 메소드.
	/*public static void main(String[] args) { 
		//메인 메소드는 LP클래스에 속한게 아니라
		//위치만 여기 있는거임.
		LottoProgram lotto = new LottoProgram();
		for (String string : args) {
			System.out.println(lotto.setLotto());
		}
	}*/
	int[] lotto = new int[6];
	
	public LottoProgram() {
		this.setLotto();
	}
	public int[] getLotto() {
		return lotto;
	}
	public int[] setLotto() {
		
		for (int i = 0; i < lotto.length; i++) {
			int temp = (int) (Math.random()*45+1);
			boolean bool = false;
			for (int j = 0; j < lotto.length; j++) {
				if (temp == lotto[j]) {
					bool = true;
					break;
				}
			}
			if (bool) {
				i--;
			}
			else {
				lotto[i] = temp;
			}
		}
		Arrays.sort(lotto);
		
		return lotto;
	}

}	
