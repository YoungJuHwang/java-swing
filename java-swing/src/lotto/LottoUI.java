package lotto;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class LottoUI extends JFrame implements ActionListener{
	//연관관계에 따라 화면만 구성함. VO랑 다른 개념.

	private static final long serialVersionUID = 1L;
	LottoProgram lotto;
	// 컴포넌트(스윙에서 화면 나타내는 클래스)는 연관관계로 부모-자식 관계를 맺음.
	
	JButton button, btnTest, btnExit;
	
	JPanel panelNorth, panelSouth;
	ImageIcon icon;
	List<JButton> buttons;
	
	public LottoUI() {
		init(); //생성자를 키우는 건 별로 안좋음.. 퍼포먼스 x
	}

	public void init() { // initialize의 약자로 초기화 메소드 이름으로 사용.
		// 부품 준비 단계 : 큰것 -> 작은것 순.
		this.setTitle("SBS로또추첨"); // JFrame을 상속받으므로 UI가 JFrame.
		lotto = new LottoProgram();
		buttons = new ArrayList<JButton>();
		panelNorth = new JPanel();
		panelSouth = new JPanel();
		button = new JButton("로또번호추첨");
		btnTest = new JButton("버튼 테스트");
		btnExit = new JButton("종료");
		
		// 조립 단계 : 작은것 -> 큰것
		button.addActionListener(this); // 이 클래스에서 구현한 관련 메소드 할당. 
		btnTest.addActionListener(this);//this는 밑의 action에 연결됨. 버튼 추가 할때 추가해야함.
		btnExit.addActionListener(this);
		// 자바책 641. addAcListener은 특정 이벤트가 와야 실행됨.(경계병)
		panelNorth.add(button); // 북족 패널에 버튼 장착
		panelNorth.add(btnTest);
		panelNorth.add(btnExit);
		this.add(panelNorth, BorderLayout.NORTH);
		this.add(panelSouth, BorderLayout.SOUTH);
		this.setBounds(300, 400, 1200, 300);
		// 300, 400은 좌표 x, y
		// 1200, 30안 픽셀값
		this.setVisible(true);
	} 
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "로또번호추첨":
			if (buttons.size() == 0) {
				JButton temp = null;
				for (int i = 0; i < 6; i++) {
					temp = new JButton(); // 번호가 붙지 않은 로또 객체.
					buttons.add(temp); // 로또볼6개를 리스트에 담음.
					panelNorth.add(temp); // 로또볼을 패널에 붙임.
				}
			}
			lotto.setLotto(); //로또 추첨 들어감.
			int[] temp = lotto.getLotto();
			for (int i = 0; i < temp.length; i++) {
				buttons.get(i).setIcon(new ImageIcon("src/image/"+ temp[i]+ ".gif"));
			}
			break;
		case "버튼 테스트":
			System.out.println("테스트 완료");
			break;
		case "종료":
			System.exit(0);
			break;
		default:
			break;
		}
		
	}
	
}
