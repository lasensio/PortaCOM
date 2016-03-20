package br.com.asensio.con;

import java.util.Enumeration;
import gnu.io.*;

public class ComTest {
	
	static Enumeration portList;
    static CommPortIdentifier serialId;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		portList = CommPortIdentifier.getPortIdentifiers();
        

        while(portList.hasMoreElements()) {
              serialId = (CommPortIdentifier) portList.nextElement();
              System.out.println(serialId.getName());
        }

	}

}
