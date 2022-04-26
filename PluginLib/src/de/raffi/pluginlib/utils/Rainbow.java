package de.raffi.pluginlib.utils;

import org.bukkit.Color;
/**
 * 
 * @since 1.1-b5
 *
 */
public class Rainbow {

	private int r = 255;
	private int g = 0;
	private int b = 0;

	private void nextRGB() {
		if (r == 255 && g < 255 && b == 0) {
			g++;
		}
		if (g == 255 && r > 0 && b == 0) {
			r--;
		}
		if (g == 255 && b < 255 && r == 0) {
			b++;
		}
		if (b == 255 && g > 0 && r == 0) {
			g--;
		}
		if (b == 255 && r < 255 && g == 0) {
			r++;
		}
		if (r == 255 && b > 0 && g == 0) {
			b--;
		}
	}

	public Color next() {
		nextRGB();
		return Color.fromRGB(r, g, b);
	}
}
