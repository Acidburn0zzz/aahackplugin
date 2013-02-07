package org.kos.aahackplugin;

import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.colors.EditorFontType;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class AAHackConfigurationPanel {
	private JLabel testLabel1;
	private JComboBox aaTypeCombo;
	private JSlider contrastSlider;
	private JPanel rootPanel;
	private JLabel warningLabel;
	private JLabel testLabel2;

	public AAHackConfigurationPanel() {
		contrastSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				updateTestLabel();
			}
		});
		aaTypeCombo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				updateTestLabel();
			}
		});

		final AAType[] types = AAType.values();
		for (AAType type : types) {
			aaTypeCombo.addItem(type);
		}
	}

	private AAType getSelectedAAType() {
		return (AAType) aaTypeCombo.getSelectedItem();
	}

	public int getContrast() {
		return contrastSlider.getValue();
	}

	private void updateTestLabel() {
		testLabel1.repaint();
	}

	public JPanel getRootPanel() {
		return rootPanel;
	}
	private void createUIComponents() {
		testLabel1 = new AAJLabel();

		final EditorColorsScheme colorsScheme = EditorColorsManager.getInstance().getGlobalScheme();
		testLabel1.setBackground(colorsScheme.getDefaultBackground());
		testLabel1.setForeground(colorsScheme.getDefaultForeground());
		testLabel1.setFont(colorsScheme.getFont(EditorFontType.PLAIN));

		testLabel2 = new AAJLabel();
		testLabel2.setBackground(colorsScheme.getDefaultBackground());
		testLabel2.setForeground(colorsScheme.getDefaultForeground());
		testLabel2.setFont(colorsScheme.getFont(EditorFontType.ITALIC));
	}

	public void setConfiguration(final AAHackConfiguration conf) {
		aaTypeCombo.setSelectedItem(conf.getAAType());
		contrastSlider.setValue(conf.getContrast());
	}

	public AAHackConfiguration getConfiguration() {
		return new AAHackConfiguration(getSelectedAAType(), contrastSlider.getValue());
	}

	public boolean isChanged(AAHackConfiguration conf) {
		boolean res = conf.getAAType() != getSelectedAAType() ||
				conf.getContrast() != getContrast();
		warningLabel.setEnabled(res);
		return res;
	}

	private class AAJLabel extends JLabel {
		public AAJLabel() {
			super("The quick brown fox jumps over the lazy dog");
			setOpaque(true);
		}

		protected void paintComponent(Graphics g) {
			final Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, getSelectedAAType().getRenderingHint());
			g2.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, getContrast());
			super.paintComponent(g);
		}
	}
}
