package de.raffi.pluginlib.serialization;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.naming.OperationNotSupportedException;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class JSONHelper {
	
	private String file;
	private Gson gson;
	public JSONHelper(String file) {
		this.file = file;
		this.gson = new Gson();
	}
	public JSONHelper() {
		try {
			throw new OperationNotSupportedException();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getFile() {
		return file;
	}
	public Gson getGson() {
		return gson;
	}
	/**
	 * 
	 * @param data json data
	 * @throws IOException
	 */
	public void write(String data) throws IOException {
		File f = new File(file);
		if (!f.exists()) {
			File directory = new File(f.getParent());
			if (!directory.exists()) {
				directory.mkdirs();
			}
			f.createNewFile();
		}

		// Convenience class for writing character files
		FileWriter writer = new FileWriter(f.getAbsoluteFile(), true);

		// Writes text to a character-output stream
		BufferedWriter bufferWriter = new BufferedWriter(writer);
		bufferWriter.write(data);
		bufferWriter.close();
	}
	/**
	 * 
	 * @param o object to write
	 * @throws IOException
	 */
	public void write(Object o) throws IOException {
		write(gson.toJson(o));
	}
	
	public Object read(Class<?> clazz) throws IOException {
		File f = new File(file);
		if (!f.exists())
			f.createNewFile();

		InputStreamReader reader = new InputStreamReader(new FileInputStream(f), "UTF-8");
		JsonReader myReader = new JsonReader(reader);
		return gson.fromJson(myReader, clazz);
	}

}
