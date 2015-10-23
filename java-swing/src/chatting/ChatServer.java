package chatting;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class ChatServer extends JFrame implements ActionListener, Runnable {
	//채팅 프로그램은 무조건 서버가 하나 있어야 한다.
	public static void main(String[] args) {
		ChatServer cs = new ChatServer();
		new Thread(cs).start(); //데코 패턴.
		
	}

	private static final long serialVersionUID = 1L; //직렬화
	Vector<Service> vec;	 //요기에 new Vector();밖지 말기.
	JTextArea txt;
	JButton btn; 

	public ChatServer(){
		super("서버"); //프레임 이름 지정
		
		//부품 준비
		vec = new Vector<Service>(); //vec는 접속자를 담아두는 자료구조.
		txt = new JTextArea();
		btn = new JButton("서버종료"); // 서버종료는 내부적으로 this.name = name 처럼 명령어로 내장됨.
		btn.addActionListener(this); // 밑의 actionPerformed로 감.
		//조립 과정
		this.add(txt,"Center"); //BorderLayout.CENTER 텍스트는 중아엥 둠.
		this.add(btn, "South"); //BorderLayout.South 버튼은 아래로
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //프레임에 닫기 버튼
		this.setBounds(50, 100, 300, 600); //x좌표, y좌표, 가로크기, 세로크기
		this.pack(); // 컴포넌트끼리 팩킹함.
		this.setVisible(true);
	}
	
	// "서버종료"라는 버튼 클릭하면 이벤트 발생(리스너의 기능)★
	// ★ 이벤트리스너에 대한 패턴은 중요함. 
	// 2기종 언어끼리의 데이터 교환은 int, String타입이 공통됨.
	// 하지만 튜브를 타게 되면 int형도 String으로 바뀜.(직렬화)
	@Override
	public void actionPerformed(ActionEvent e) {
		/*if (e.getSource() == btn) { // 이벤트를 발생시킨 객체가 버튼?
			System.exit(0);
		} */// btn1, btn2....줘서 if else로 쭉 쓰는 방법은 예전방법.. 안드로이드에서 안씀.
		
		switch (e.getActionCommand()) { //Command에선 내부적으로 get/set. 
										//btn에 준 명령어를 
		case "서버종료":
			System.exit(0);
			break;

		default:
			break;
		}
		
	} 

	@Override
	public void run() {
		ServerSocket ss = null; //소캣 : 채팅서버 하나가 고장나면 다른 서버로 연결해주는 역할.
								//	필드에 때려버리면 안됨. 랜선을 인터넷포트에 납땜시켜논꼴.
								// 전체 관계는 의존관계, 상관관계 밖에 없다.
		try { //try/catch는 외부 시스템에(서)? 접속할 때 뜸.
			ss = new ServerSocket(4444);//5555는 서버 접속포트 번호(포트 : 103번지, 101동101호)
										//이미 여기까진 같은 게이트웨이로 들어왔음.
		} catch (IOException e) {
			e.printStackTrace();
		}//요거 빠져나오면 연결되었음.
		while (true) {
			txt.append("클라이언트 접속 대기중\n"); //append는 스트링버퍼에서 썻다..
			try {
				Socket s = ss.accept(); //소켓 접속시 사용되는 문법.(shallow copy)
				//클라이언트의 소켓은 서버소켓이 허용해야 생성된다.
				txt.append("클라이언트 접속 처리\n");
				Service cs = new Service(s); 
				cs.start(); //클라이언트의 스레드가 작동.
				cs.nickName = cs.in.readLine();
				cs.sendMessageAll("/c"+cs.nickName); //이미 접속되어 있는 다른 클라이언트에 나를 알림.
				vec.add(cs); //접속된 클라이언트를 벡터에 추가함.
				for (int i = 0; i < vec.size(); i++) {
					Service service = vec.elementAt(i);
					service.sendMessage("/c"+service.nickName);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		
	}
	//이너 클래스 자바책 288 
	class Service extends Thread{ // 접속자 객체를 생성하는 기능.
		String nickName = "guest"; //대화명
		// *Reader, * Writer 는 2바이트씩 처리하므로 문자에 최적화(빠르다)
		BufferedReader in; 
		// *Stream 은 1바이트씩 처리하므로 이미지, 음악, 파일에 최적화.
		OutputStream out;
		Socket s; // 클라언트쪽 단자.
		Calendar now = Calendar.getInstance(); //싱글톤 패턴.
		// 디폴트 생성자 안만들고 파라미터가 존재하는 생성자 만들면
		// 반드시 객체를 생성할 때 소켓을 연결해야 한다는 의미.
		public Service(Socket s) {
			this.s = s;
			//java p.729
			try {//BufferedReader는 원래 2바이트만 받아들이는데 브릿지 패턴 통해 InputStreamReader 역할까지 함.
				in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				out = s.getOutputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		@Override
		public void run() {
			while (true) {
				try {
					String msg = in.readLine(); //내부적으로 버퍼에 받고있다 전송 시 String으로 쏨.
					txt.append("전송메세지 : " + msg +"\n");
					if (msg == null) {
						return;  // 메시지가 더 이상 없으면 종료
					}
					if (msg.charAt(0) == '/') {
						char temp = msg.charAt(1);
						switch (temp) {
						case 'n': // n : 이름바꾸기
							if (msg.charAt(2) == ' ') {
								sendMessageAll("\n"+nickName+"-"+msg.substring(3).trim());
								// " 위의 문장중에서 - 는 닉네임과 이름을 분리하기 위해 임의로 설정한 기호
								this.nickName = msg.substring(3).trim();
							}
							break;
						case 'q': // 클라이언트가 퇴장
							for (int i = 0; i < vec.size(); i++) {
								Service svc = (Service) vec.get(i);
								if (nickName.equals(svc.nickName)) {
									vec.remove(i);
									break;
								}
							}
							sendMessage(" -> " +nickName +"님이 퇴장하셨습니다.");
							in.close(); // 인풋 네트워크 해제
							out.close();// 아웃풋 네트워크 해제
							s.close();  // 소켓 해제
							return;
						case 's':
							String name = msg.substring(2, msg.indexOf('-')).trim();
							for (int i = 0; i < vec.size(); i++) {
								Service cs3 = (Service) vec.elementAt(i);
								if (name.equals(cs3.nickName)) {
									cs3.sendMessage(nickName+" >>(귓속말)"
											+msg.substring(msg.indexOf('-')+1));
									break;
								}
							}
						default:
							sendMessageAll(nickName + " >> " + msg);
							break;
						}
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		// 벡터의 데이터를 꺼내어 클라이언트에게 보내기
		public void sendMessageAll(String msg){
			for (int i = 0; i < vec.size(); i++) {
				Service cs = (Service) vec.elementAt(i);
				cs.sendMessage(msg);
			}
		}
		
		private void sendMessage(String msg){
			try {
				out.write((msg+"\n").getBytes());
				txt.append("보냄 : " +msg + "\t");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	
	

}
