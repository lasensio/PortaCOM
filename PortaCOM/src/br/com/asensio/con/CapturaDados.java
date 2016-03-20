package br.com.asensio.con;

public class CapturaDados {

	public static void main(String[] args) {
		
		SerialInterface Com = new SerialInterface("/dev/cu.usbserial-AH03AYMB", 9600);

		
		Com.read(new SerialReadAction() {
			
			String s = "";
			
			@Override
			public void read(byte b) {
				s += (char)b;
				//System.out.println(s);
				if (b == '\n'){
					System.out.print(s);
					s = "";
				}
			}
		});

	}

}
