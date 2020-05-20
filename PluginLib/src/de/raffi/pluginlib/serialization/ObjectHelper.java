package de.raffi.pluginlib.serialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;

public class ObjectHelper {
	
	private File file;
	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;

	public ObjectHelper(File file) {
		this.file = file;
	}
	public ObjectHelper writer(){
		try {
			objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
	public ObjectHelper reader() {
		try {			
			objectInputStream = new ObjectInputStream(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
		}
		return this;
	}
	public ObjectOutputStream getObjectOutputStream() {
		return objectOutputStream;
	}
	public ObjectInputStream getObjectInputStream() {
		return objectInputStream;
	}
	public File getFile() {
		return file;
	}
	public void writeObject(Object o) {
		try {
			objectOutputStream.writeObject(o);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Object readObject() throws ClassNotFoundException, IOException {
		return objectInputStream.readObject();
	}
	/**
	 * only for object list
	 * 
	 * @param s
	 * @deprecated
	 */
	public void saveList(List<Object> s) {
		writeObject(s.toArray(new Object[s.size()]));
	}
	/**
	 * only for object list
	 * 
	 * @param s
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @deprecated
	 */
	public void setupList(List<Object> s) throws ClassNotFoundException, IOException {
		s.clear();
		Object[] o = (Object[]) readObject();
		s.addAll(Arrays.asList(o));
	}
	public void closeWriter() {
		try {
			objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void closeReader() {
		try {
			objectInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void closeAll() {
		closeWriter();
		closeReader();
	}
}
