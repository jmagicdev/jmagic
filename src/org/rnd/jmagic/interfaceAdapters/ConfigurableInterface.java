package org.rnd.jmagic.interfaceAdapters;

import org.rnd.jmagic.engine.*;

public interface ConfigurableInterface extends PlayerInterface
{
	public org.rnd.jmagic.gui.dialogs.ConfigurationFrame.OptionPanel getOptionPanel();

	public java.util.SortedSet<org.rnd.jmagic.gui.dialogs.ConfigurationFrame.OptionPanel> getOptions();

	public void setProperties(java.util.Properties properties);
}