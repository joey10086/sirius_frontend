package de.unijena.bioinf.sirius.gui.utils;

import eu.hansolo.rangeslider.RangeSlider;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Marcus Ludwig on 13.01.17.
 */
public class SliderWithTextField extends JPanel {

    final private JSlider slider;
    final boolean isRangeSlider;

    private final JTextField text1, text2;


    public SliderWithTextField(String name, int min, final int max, int value) {
        this(name, min, max, value, -1);
    }

    //// TODO: range upper value not working
    public SliderWithTextField(String name, int min, final int max, int currentMin, int currentMax) {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        final int length = (int)(Math.log10(max)+1);
        isRangeSlider = (currentMax>0);
        if (!isRangeSlider) currentMax = currentMin;

        this.add(new JLabel(name));

        if (!isRangeSlider){
            slider = new JSlider(min,max,currentMin);
            text1 = null;
        } else {
            slider = new RangeSlider(min, max);
            ((RangeSlider)slider).setLowerValue(currentMin);
            ((RangeSlider)slider).setUpperValue(currentMax);
            text1 = new JTextField(length+1);
            this.add(text1);

            if (currentMin==max){
                text1.setText("inf");
            } else {
                text1.setText(String.valueOf(currentMin));
            }

            text1.addKeyListener(new KeyAdapter(){
                @Override
                public void keyReleased(KeyEvent ke) {
                    String typed = text1.getText();
                    ((RangeSlider)slider).setLowerValue(0);
                    int value;
                    if (typed.equals("inf")){
                        value = max;
                    } else if(!typed.matches("\\d+") || typed.length() > length) {
                        return;
                    } else {
                        value = Integer.parseInt(typed);
                    }

                    ((RangeSlider)slider).setLowerValue(value);
                }
            });
        }

        slider.setMajorTickSpacing(5);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPreferredSize(new Dimension(slider.getPreferredSize().width/2, slider.getPreferredSize().height));

        text2 = new JTextField(length+1);

        if (currentMax==max){
            text2.setText("inf");
        } else {
            text2.setText(String.valueOf(currentMax));
        }


        this.add(slider);
        this.add(text2);

        slider.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                if (isRangeSlider){
                    int minValue = ((RangeSlider)slider).getLowerValue();
                    int maxValue = ((RangeSlider)slider).getUpperValue();
                    if (minValue==max){
                        text1.setText("inf");
                    } else {
                        text1.setText(String.valueOf(minValue));
                    }
                    if (maxValue==max){
                        text2.setText("inf");
                    } else {
                        text2.setText(String.valueOf(maxValue));
                    }
                } else {
                    if (slider.getValue()==max){
                        text2.setText("inf");
                    } else {
                        text2.setText(String.valueOf(slider.getValue()));
                    }
                }
            }
        });
        text2.addKeyListener(new KeyAdapter(){
            @Override
            public void keyReleased(KeyEvent ke) {
                String typed = text2.getText();
                if (isRangeSlider){
                    ((RangeSlider)slider).setUpperValue(0);
                } else {
                    slider.setValue(0);
                }

                int value;
                if (typed.equals("inf")){
                    value = max;
                } else if(!typed.matches("\\d+") || typed.length() > length) {
                    return;
                } else {
                    value = Integer.parseInt(typed);
                }

                if (isRangeSlider){
                    ((RangeSlider)slider).setUpperValue(value);
                } else {
                    slider.setValue(value);
                }
            }
        });
    }

    public int getMinValue(){
        if (isRangeSlider){
            return ((RangeSlider)slider).getLowerValue();
        } else {
            return slider.getValue();
        }
    }

    public int getMaxValue(){
        if (isRangeSlider){
            return ((RangeSlider)slider).getUpperValue();
        } else {
            return slider.getValue();
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        slider.setEnabled(enabled);
        if (text1!=null) text1.setEnabled(enabled);
        text2.setEnabled(enabled);
    }
}