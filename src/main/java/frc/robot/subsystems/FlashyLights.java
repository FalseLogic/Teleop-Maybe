package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FlashyLights extends SubsystemBase {

	private final AddressableLED strip;
	private final AddressableLEDBuffer ledBuffer;

	public FlashyLights() {
		strip = new AddressableLED(0);
		ledBuffer = new AddressableLEDBuffer(62);
		strip.start();
	}

	public void setAllColor(int h, int s, int v) {
		for(int i = 0; i < 62; i++) {
			ledBuffer.setHSV(i, h, s, v);
		}
	}

	private int rainbowFirstPixelHue = 0;
	public void rainbow() {
		for(int i = 0;  i < 62; i++) {
			final int hue = (rainbowFirstPixelHue + (i * 180 / 62)) % 180;
			ledBuffer.setHSV(i, hue, 255, 128);
		}
		rainbowFirstPixelHue = (rainbowFirstPixelHue + 3) % 180;
	}

	@Override
	public void periodic() {
		strip.setData(ledBuffer);
	}
}
