package br.com.asensio.con;




import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier;
import java.awt.event.ActionListener;
import gnu.io.NoSuchPortException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class ControlPort extends Thread implements SerialPortEventListener {
	
	private OutputStream serialOut;
	private InputStream serialIn;
	private int taxa;
	private String portCOM;
	private SerialReadAction serialReadAction = null;
	
	/**
	 * Contrutor da classe ControlPort
	 */
	public ControlPort(String portCOM, int taxa){
		this.portCOM = portCOM;
		this.taxa = taxa;
		this.initialize();
	}
	
	private void initialize(){
		try{
			
			CommPortIdentifier portId = null;
			
			try {
				
				portId = CommPortIdentifier.getPortIdentifier(this.portCOM);
				
			} catch (NoSuchPortException np) {
				System.out.println(np.getMessage());
			}
			
			//Open port
			SerialPort port = (SerialPort) portId.open("Serial comunication",this.taxa);
			serialOut = port.getOutputStream();
			serialIn = port.getInputStream();
			port.setSerialPortParams(this.taxa, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {
			serialOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendData(int opcao){
		try {
			serialOut.write(opcao);
		} catch (IOException e) {
			// TODO: handle exception
		}
	}
	
	public void getData(SerialReadAction listener){
		this.serialReadAction = listener;
		
	}

	@Override
	public void serialEvent(SerialPortEvent event) {
		switch(event.getEventType()) {
        case SerialPortEvent.BI:
        case SerialPortEvent.OE:
        case SerialPortEvent.FE:
        case SerialPortEvent.PE:
        case SerialPortEvent.CD:
        case SerialPortEvent.CTS:
        case SerialPortEvent.DSR:
        case SerialPortEvent.RI:
        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
            break;
        case SerialPortEvent.DATA_AVAILABLE:
            byte[] readBuffer = new byte[512];
            

            try {
            	int ETX = 03;
                int STX = 02;
                int CR = 13;
                int LF = 10;            	
            	try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int numBytes = 0;
                while (serialIn.available() > 0) {
                    numBytes = serialIn.read(readBuffer);
                    for (int i=0; i< numBytes; i+=1) {
                    	if (this.serialReadAction != null)
                    		this.serialReadAction.read(readBuffer[i]);
                    }
                }
            } catch (IOException e) {
            	e.printStackTrace();
            }
            break;
        }
	}
	

}
