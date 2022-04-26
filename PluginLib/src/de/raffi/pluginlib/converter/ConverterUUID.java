package de.raffi.pluginlib.converter;

import java.util.UUID;

public class ConverterUUID extends Converter<UUID>{

	@Override
	public String stringify(UUID t) {
		return t.toString();
	}

	@Override
	public UUID create(String s) {
		return UUID.fromString(s);
	}

}
