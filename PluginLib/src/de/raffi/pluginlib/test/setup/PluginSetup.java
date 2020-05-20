package de.raffi.pluginlib.test.setup;

import java.io.File;
import java.lang.reflect.Field;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.raffi.pluginlib.configuration.ConfigurationPluginLib;
import de.raffi.pluginlib.test.InputHandler;
import de.raffi.pluginlib.test.MessageHandler;
import de.raffi.pluginlib.test.YesNoCallback;
import de.raffi.pluginlib.utils.Logger;

public class PluginSetup {
	
	
	private Player p;
	private Field[] fields;
	private int index;
	private Runnable finish;
	public PluginSetup(Player p,Class<?> setup,Runnable finish) {
		this.p = p;
		this.fields = setup.getDeclaredFields();
		this.index = 0;
		this.finish = finish;
	}
	public int getIndex() {
		return index;
	}
	public Field[] getFields() {
		return fields;
	}
	public void cancel() {
		finish.run();
	}
	public Player getPlayer() {
		return p;
	}
	public void start() {
		perform();
	}
	public void next() {
		index++;
		if (index < fields.length) {
			perform();
		} else
			cancel();		
	}
	public void perform() {
		Field f = fields[index];
		Setup desc = f.getAnnotation(Setup.class);
		if(desc!=null) {
			f.setAccessible(true);
			String d = "§a§l"+desc.description();
			
			Object fieldtype=null;
			try {
				fieldtype = f.get(null);
			} catch (Exception e1) {			
				Logger.debug(e1);
				Logger.debug("Fieldtype: " + fieldtype);
				Logger.debug("Fieldname: " + f.getName());
				e1.printStackTrace();
			} 
			String def=" §7§o[DEFAULT:"+desc.defaultValue()+"§7§o]";
			if(fieldtype instanceof String) {
				InputHandler.getInputFrom(p, d + def+" §8§o[String]", new MessageHandler() {
					
					@Override
					public void onMessageDenied(String message) {}
					
					@Override
					public boolean handleMessage(String message) {
						try {f.set(null, message);} catch (Exception e) {}
						return true;
					}

					@Override
					public void onHandlerRemoved() {
						accepted(f);
						
					}
				});
			} else if(fieldtype instanceof Integer) {
				InputHandler.getInputFrom(p, d+ def+" §8§o[Integer]", new MessageHandler() {
					
					@Override
					public void onMessageDenied(String message) {
						p.sendMessage("§c" + message + " is not a valid value.");	
					}
					
					@Override
					public boolean handleMessage(String message) {
						try {
							int i =Integer.parseInt(message);
							f.set(null, i);
							return true;
						} catch (Exception e) {
							return false;
						}
					
					}
					@Override
					public void onHandlerRemoved() {
						accepted(f);
					}
				});
			} else if(fieldtype instanceof Float) {
				InputHandler.getInputFrom(p, d+ def+" §8§o[Float]", new MessageHandler() {
					
					@Override
					public void onMessageDenied(String message) {
						p.sendMessage("§c" + message + " is not a valid value.");	
					}
					
					@Override
					public boolean handleMessage(String message) {
						try {
							f.set(null, Float.parseFloat(message));
							return true;
						} catch (Exception e) {
							return false;
						}
					}

					@Override
					public void onHandlerRemoved() {
						accepted(f);
					}
				});
			} else if(fieldtype instanceof Boolean) {
				InputHandler.getYesNoFeedback(p, d+def, ConfigurationPluginLib.SETUP_TXT_YES, " "+ConfigurationPluginLib.SETUP_TXT_NO, new YesNoCallback() {
					
					@Override
					public void decline() {
						try {
							f.set(null, false);
						} catch (Exception e) {}
					}
					
					@Override
					public void accept() {
						try {
							f.set(null, true);
						} catch (Exception e) {
							e.printStackTrace();
						}						
					}

					@Override
					public void onHandlerRemoved(boolean b) {
						accepted(f);
						
					}
				});
			} else if(fieldtype instanceof Double) {
				InputHandler.getInputFrom(p, d+ def+" §8§o[Double]", new MessageHandler() {
					
					@Override
					public void onMessageDenied(String message) {
						p.sendMessage("§c" + message + " is not a valid value.");	
					}
					
					@Override
					public boolean handleMessage(String message) {
						try {
							f.set(null, Double.parseDouble(message));
							return true;
						} catch (Exception e) {
							return false;
						}
					
					}
					@Override
					public void onHandlerRemoved() {
						accepted(f);
					}
				});
			} else {
				p.sendMessage("§cUnknown field type:" + fieldtype);
				next();
			}
		} else next();
		
	}
	private void accepted(Field f) {
		try {
			p.sendMessage("§eSet §f" + f.getName() + "§e to §f"+f.get(null).toString());
			next();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void loadValues(Class<?> cl, File config) {
		try {
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(config);
			for(Field f : cl.getDeclaredFields()) {
				if(!f.isAnnotationPresent(Setup.class) || !cfg.isSet(f.getName())) continue;
				f.setAccessible(true);
				Object o = f.get(null);
				if(o instanceof String) {
					f.set(null, cfg.getString(f.getName()).replace("&", "§"));
				} else f.set(null, cfg.get(f.getName()));
			}
			cfg.save(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static boolean isSaved(Class<?> cl, File config) {
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(config);
		for(Field f:cl.getDeclaredFields()) {
			if(!f.isAnnotationPresent(Setup.class)) continue;
			if(cfg.isSet(f.getName())) return true;
		}
		return false;
	}
	public static void saveValues(Class<?> cl, File config) {
		try {
			FileConfiguration cfg = YamlConfiguration.loadConfiguration(config);
			for(Field f : cl.getDeclaredFields()) {
				if(!f.isAnnotationPresent(Setup.class)) continue;
				f.setAccessible(true);
				Object o = f.get(null);
				if(o instanceof String) {
					cfg.set(f.getName(), o.toString().replace("§", "&"));
				} else cfg.set(f.getName(), o);
			}
			cfg.save(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
