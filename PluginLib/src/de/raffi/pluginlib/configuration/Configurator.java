package de.raffi.pluginlib.configuration;

import java.io.File;
import java.lang.reflect.Field;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Configurator {
	

	/**
	 * loads all Fields with the {@link Configurable} Annotatation
	 * @param cl The class the Fields should be loaded from
	 * @param folder the folder in which the config files are in
	 */
	public static void load(Class<?> cl,String folder) {
		for(Field f : cl.getDeclaredFields()) {
			if(f.isAnnotationPresent(Configurable.class)) {
				f.setAccessible(true);
				Configurable configurable = f.getAnnotation(Configurable.class);
				File save = new File(folder,configurable.value());
				FileConfiguration cfg=YamlConfiguration.loadConfiguration(save);

				if(cfg.isSet(f.getName())) {			 //check if the config already contains the field name	
					try {
						boolean b = f.get(null) instanceof String;
						if(b)
							f.set(null, ((String)cfg.get(f.getName())).replace("&", "§")); //replace & to §
						else f.set(null, cfg.get(f.getName()));
							
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else { //sets the field value to the config if the config does not contain the field name
					try {
						Object set = f.get(null);
						if(set instanceof String) {
							cfg.set(f.getName(), ((String)set).replace("§", "&")); //replace § to &
						} else
							cfg.set(f.getName(), f.get(null));
						cfg.save(save); //save the file
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
