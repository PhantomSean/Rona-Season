package GUI;

import javax.swing.*;
import java.awt.*;

public class InputPanel extends InfoPanel {

	private JSlider slider;

	public InputPanel(){
		super();

		slider = new JSlider(0,5,0);
		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);

		add(slider, BorderLayout.PAGE_END);


	}

	public int getSliderInput(){
		return slider.getValue();
	}


}
