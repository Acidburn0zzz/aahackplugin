package org.kos.aahackplugin;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.*;
import org.jdom.Element;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sun.awt.SunToolkit;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Method;

@SuppressWarnings({"CatchGenericClass"})
public class AAHackPluginComponent implements ApplicationComponent, Configurable, JDOMExternalizable {
	@Nullable
	private AAHackConfigurationPanel configurationPanel;
	@NotNull
	private AAHackConfiguration configuration = new AAHackConfiguration();

	@SuppressWarnings({"RedundantNoArgConstructor"})
	public AAHackPluginComponent() {
	}

	public void initComponent() {
		applySettings();
	}

	private void applySettings() {
		final Toolkit tk = Toolkit.getDefaultToolkit();
		if (!(tk instanceof SunToolkit)) {
			System.err.println("Not a Sun Toolkit...");
			return;
		}
		try {
			RenderingHints dfh = (RenderingHints) tk.getDesktopProperty(SunToolkit.DESKTOPFONTHINTS);
			final RenderingHints aa = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, configuration.getAAType().getRenderingHint());
			aa.add(new RenderingHints(RenderingHints.KEY_TEXT_LCD_CONTRAST, configuration.getContrast()));
			if (dfh != null)
				dfh.add(aa);
			else
				dfh = aa;
			final Method setDesktopProperty = Toolkit.class.getDeclaredMethod("setDesktopProperty", String.class, Object.class);
			setDesktopProperty.setAccessible(true);
			setDesktopProperty.invoke(tk, SunToolkit.DESKTOPFONTHINTS, dfh);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void disposeComponent() {
	}

	@NotNull
	public String getComponentName() {
		return "AAHackPluginComponent";
	}

	//----

	@Nls
	public String getDisplayName() {
		return "AA Hack";
	}

	@Nullable
	public Icon getIcon() {
		return IconLoader.getIcon("/org/kos/aahackplugin/icons/aahack.png");
	}

	@Nullable
	@NonNls
	public String getHelpTopic() {
		return null;
	}

	public JComponent createComponent() {
		if (configurationPanel == null)
			configurationPanel = new AAHackConfigurationPanel();
		return configurationPanel.getRootPanel();
	}

	public boolean isModified() {
		return configurationPanel != null && configurationPanel.isChanged(configuration);
	}

	public void apply() throws ConfigurationException {
		if (configurationPanel != null)
			configuration = configurationPanel.getConfiguration();
	}

	public void reset() {
		if (configurationPanel != null)
			configurationPanel.setConfiguration(configuration);
	}

	public void disposeUIResources() {
		configurationPanel = null;
	}


	public void readExternal(Element element) throws InvalidDataException {
		DefaultJDOMExternalizer.readExternal(configuration, element);
	}

	public void writeExternal(Element element) throws WriteExternalException {
		DefaultJDOMExternalizer.writeExternal(configuration, element);
	}
}
