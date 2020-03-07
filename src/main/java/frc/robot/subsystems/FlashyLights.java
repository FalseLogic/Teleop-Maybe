package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class FlashyLights extends SubsystemBase {

	private final AddressableLED strip;
	private final AddressableLEDBuffer buffer;

	public FlashyLights() {
		strip = new AddressableLED(Constants.LED_STRIP_ADDRESS);
		buffer = new AddressableLEDBuffer(Constants.LED_STRIP_LENGTH);
		strip.setLength(Constants.LED_STRIP_LENGTH);
		strip.setData(buffer);
		strip.start();
	}

	public void setAllColor(int r, int g, int b) {
		for(int i = 0; i < buffer.getLength(); i++) {
			buffer.setRGB(i, r, g, b);
		}
	}

	private int rainbowFirstPixelHue = 0;
	public void rainbow() {
		for(int i = 0;  i < buffer.getLength(); i++) {
			final int hue = (rainbowFirstPixelHue + (i * 180 / buffer.getLength())) % 180;
			buffer.setHSV(i, hue, 255, 128);
		}
		rainbowFirstPixelHue = (rainbowFirstPixelHue + 3) % 180;
	}

	private double flashTimer = 0;
	public void flash(int r, int g, int b, int duration) {
		flashTimer++;
		if(flashTimer / duration > .5) {
			r = 0;
			g = 0;
			b = 0;
		}

		for(int i = 0; i < buffer.getLength(); i++) {
			buffer.setRGB(i, r, g, b);
		}

		if(flashTimer % duration == 0) {
			flashTimer = 0;
		}

	}

	public void setData() {
		strip.setData(buffer);
	}

	@Override
	public void periodic() {
	}
}
