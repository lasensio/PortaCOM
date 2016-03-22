package br.com.asensio.con;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.SerialPort;

public class ControlPort {
	
	private OutputStream serialOut;
	private InputStream serialIn;
	private int taxa;
	private String portCOM;
	
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
	

}
