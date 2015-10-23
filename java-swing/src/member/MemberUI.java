package member;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MemberUI extends JFrame{
	private Vector data;
	private Vector title;
	JTable table;
	JButton btnAdd, btnSearch, btnUpdate, btnDel, btnClear;
	JTextField txtName, txtAddr, txtUserid;
	JLabel lblName, lblAddr, lblUserid;
	DefaultTableModel model; //찾아보기.
	
	public MemberUI() {
		//super("회원관리");  -1
		init();
		pack(); //합침
		setVisible(true);
	}
	
	
	public void init(){
		this.setTitle("회원관리");  //-1과 같은 역할을 함.
		data = new Vector<>(); // 테이블에 표시될 데이터 벡터(임시저장)
		title = new Vector<>();
		title.add("아이디");
		title.add("이름");
		title.add("주소");
		Vector result = null; //MemberDAO가 selectAll() 한 결과를 받는 컬렉션
		//model.setDataVector(result, title);
		table = new JTable(model); // 모델을 통해 테이블 생성.
		JScrollPane scroll = new JScrollPane(table); // 테이블 옆 스크롤
		
		//이벤트를 단 하나의 컴포넌트 객체에 할당하는 방법.
		table.addMouseListener(new MouseAdapter() { 
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = table.getSelectedRow();
				Vector in = (Vector) data.get(index); // member의 rs같은 역할?
				
				txtUserid.setText((String)in.get(0));
				txtName.setText((String)in.get(1));
				txtAddr.setText((String)in.get(2));
				// 사용자ID는 변경할 수 없다는 옵션
				txtUserid.setEditable(false);
				txtName.setEditable(false);
			}
		});
		//화면에 표시될 패널 생성
		JPanel panel = new JPanel();
		
		//값을 입력받거나 표시할 텍스트 필드
		txtName = new JTextField(8); //8글자 허용.
		txtUserid = new JTextField(10);
		txtAddr = new JTextField(30);
		
		//레이블 생성
		lblUserid = new JLabel("ID");
		lblName = new JLabel("이름");
		lblAddr = new JLabel("주소");
		
		//버튼 생성
		btnAdd = new JButton("추가");
		btnSearch = new JButton("검색");
		btnUpdate = new JButton("수정");
		btnDel = new JButton("삭제");
		btnClear = new JButton("초기화");
		
		// 추가버튼 클릭
		btnAdd.addActionListener(new ActionListener() {
			
			@Override//추가버튼 클릭하면 일어나는 이벤트.
			public void actionPerformed(ActionEvent e) {
				//위에 입력받은txtUserid를 userid로 가져옴. 
				//String userid = scan.next();와 같은 역할
				String userid = txtUserid.getText(); 
				String name = txtName.getText();
				String addr = txtAddr.getText();
				//service.insert(userid, name, addr);
				
				//Vector result = service.selectAll();
				model.setDataVector(result, title);
				
			}
		});
		
		// 검색버튼 클릭
		btnSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		// 업뎃버튼 클릭
		btnUpdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		// 삭제버튼 클릭
		btnDel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		// 초기화버튼 클릭
		btnClear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		//버튼이 있고 그 밑에 입력란이 있는 모양임.
		panel.add(lblUserid); //버튼
		panel.add(txtUserid); //입력란 
		panel.add(lblName);
		panel.add(txtName); 
		panel.add(lblAddr);
		panel.add(txtAddr); 
		
		panel.add(btnAdd); 
		panel.add(btnSearch); 
		panel.add(btnUpdate); 
		panel.add(btnDel); 
		panel.add(btnClear); 
		//패널들을 합치는(팩킹) 역할
		Container container = this.getContentPane(); 
		container.add(new JLabel("테이블 데모 프로그램", JLabel.CENTER),"North");
		container.add(scroll,BorderLayout.CENTER);
		container.add(panel,BorderLayout.SOUTH);
		
		//이쪽은 이해하면 안드로이드에 도움줌★★
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e){
				setVisible(false); // 윈도우 창을 보이지 않게 하기 -> 닫기
				System.exit(0); // 스윙 문법으로 종료처리. (return 의미)
		
			}
		});
		
		
		
	}
	
	
	
	
	
}
