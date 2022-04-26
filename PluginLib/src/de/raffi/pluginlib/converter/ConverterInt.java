package de.raffi.pluginlib.converter;

public class ConverterInt extends Converter<Integer>{

	@Override
	public String stringify(Integer t) {
		return String.valueOf(t);
	}

	@Override
	public Integer create(String s) {
		return Integer.valueOf(s);
	}

}
