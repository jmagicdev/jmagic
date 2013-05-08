package org.rnd.jmagic.interfaceAdapters;

public class ConfigurableInterfaceDecorator extends SimplePlayerInterface implements ConfigurableInterface
{
	private final ConfigurableInterface adapt;

	public ConfigurableInterfaceDecorator(ConfigurableInterface adapt, org.rnd.jmagic.engine.PlayerInterface decorate)
	{
		super(decorate);
		this.adapt = adapt;
	}

	@Override
	public org.rnd.jmagic.gui.dialogs.ConfigurationFrame.OptionPanel getOptionPanel()
	{
		return null;
	}

	@Override
	public java.util.SortedSet<org.rnd.jmagic.gui.dialogs.ConfigurationFrame.OptionPanel> getOptions()
	{
		return this.adapt.getOptions();
	}

	@Override
	public void setProperties(java.util.Properties properties)
	{
		this.adapt.setProperties(properties);
	}
}
