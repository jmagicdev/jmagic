package org.rnd.jmagic.gui.dialogs;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.gameTypes.*;

public class GameTypeDialog extends javax.swing.JDialog
{
	private static class BeanConfiguration<T>
	{
		public final Class<? extends T> beanClass;
		public final String name;
		public final java.util.Map<String, PropertyConfiguration> properties;

		/**
		 * Create a new configuration for a Java bean with an optional
		 * {@link Description} annotation.
		 * 
		 * @param beanClass The bean class extending T
		 */
		public BeanConfiguration(Class<? extends T> beanClass)
		{
			this.beanClass = beanClass;
			this.name = beanClass.getAnnotation(Name.class).value();
			this.properties = new java.util.HashMap<String, PropertyConfiguration>();

			try
			{
				// Don't check for properties provided by Object
				for(final java.beans.PropertyDescriptor propertyDescriptor: java.beans.Introspector.getBeanInfo(this.beanClass, Object.class).getPropertyDescriptors())
				{
					// Don't bother with properties that can't be changed
					java.lang.reflect.Method writeMethod = propertyDescriptor.getWriteMethod();
					if(null == writeMethod)
						continue;

					String propertyName = propertyDescriptor.getName();
					PropertyConfiguration propertyConfiguration = PropertyConfiguration.create(propertyDescriptor);
					this.properties.put(propertyName, propertyConfiguration);
				}
			}
			catch(java.beans.IntrospectionException e)
			{
				LOG.log(java.util.logging.Level.SEVERE, "Error introspecting bean " + this.beanClass, e);
			}
		}

		public void clear()
		{
			for(PropertyConfiguration component: this.properties.values())
				component.clear();
		}

		public T getValue()
		{
			// Declare this up here so exceptions know which property was being
			// worked on
			String propertyName = null;

			try
			{
				T ret = this.beanClass.newInstance();

				// Don't check for properties provided by Object
				for(java.beans.PropertyDescriptor propertyDescriptor: java.beans.Introspector.getBeanInfo(this.beanClass, Object.class).getPropertyDescriptors())
				{
					// Don't bother with properties that can't be changed
					java.lang.reflect.Method writeMethod = propertyDescriptor.getWriteMethod();
					if(null == writeMethod)
						continue;

					propertyName = propertyDescriptor.getName();
					if(!this.properties.containsKey(propertyName))
					{
						LOG.severe("Bean configuration for " + this.beanClass + " does not have configuration for property " + propertyName);
						return null;
					}

					Object value = this.properties.get(propertyName).getValue();
					if(null == value)
						return null;
					writeMethod.invoke(ret, value);
				}

				return ret;
			}
			catch(IllegalAccessException e)
			{
				LOG.log(java.util.logging.Level.SEVERE, "Bean " + this.beanClass + " default constructor is not public", e);
			}
			catch(InstantiationException e)
			{
				LOG.log(java.util.logging.Level.SEVERE, "Bean " + this.beanClass + " isn't constructable", e);
			}
			catch(java.beans.IntrospectionException e)
			{
				LOG.log(java.util.logging.Level.SEVERE, "Error introspecting bea  " + this.beanClass, e);
			}
			catch(java.lang.reflect.InvocationTargetException e)
			{
				LOG.log(java.util.logging.Level.SEVERE, "Bean " + this.beanClass + " property " + propertyName + " write method threw exception", e.getCause());
			}

			return null;
		}

		/**
		 * Set the properties of this bean configuration to the values in the
		 * given bean instance.
		 */
		public void setValue(T beanValue)
		{
			// Declare this up here so exceptions know which property was being
			// worked on
			String propertyName = null;

			try
			{
				// Don't check for properties provided by Object
				for(java.beans.PropertyDescriptor propertyDescriptor: java.beans.Introspector.getBeanInfo(this.beanClass, Object.class).getPropertyDescriptors())
				{
					if(null == propertyDescriptor.getWriteMethod())
						continue;

					propertyName = propertyDescriptor.getName();
					if(!this.properties.containsKey(propertyName))
					{
						LOG.severe("Bean configuration for " + this.beanClass + " does not have configuration for property " + propertyName);
						continue;
					}

					java.lang.reflect.Method readMethod = propertyDescriptor.getReadMethod();
					if(null != readMethod)
						this.properties.get(propertyName).setValue(readMethod.invoke(beanValue));
				}
			}
			catch(java.beans.IntrospectionException ex)
			{
				LOG.log(java.util.logging.Level.SEVERE, "Error introspecting bean " + this.beanClass, ex);
			}
			catch(java.lang.reflect.InvocationTargetException ex)
			{
				LOG.log(java.util.logging.Level.SEVERE, "Bean " + this.beanClass + " property " + propertyName + " read method threw exception", ex.getCause());
			}
			catch(IllegalAccessException ex)
			{
				LOG.log(java.util.logging.Level.SEVERE, "Bean " + this.beanClass + " property " + propertyName + " read method is not public", ex);
			}
		}

		@Override
		public String toString()
		{
			return this.name;
		}
	}

	private static class BooleanPropertyConfiguration extends PropertyConfiguration
	{
		private javax.swing.JCheckBox checkBox;

		public BooleanPropertyConfiguration(String propertyName, Class<?> propertyType)
		{
			super(propertyName, propertyType);

			this.checkBox = new javax.swing.JCheckBox(this.propertyName + " (" + this.propertyType + ")");
			java.awt.GridBagConstraints textConstraints = new java.awt.GridBagConstraints();
			textConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
			textConstraints.gridx = 0;
			textConstraints.gridy = 0;
			textConstraints.weightx = 1;
			this.panel.add(this.checkBox, textConstraints);
		}

		@Override
		public void clear()
		{
			this.checkBox.setSelected(false);
		}

		@Override
		public Boolean getValue()
		{
			return this.checkBox.isSelected();
		}

		@Override
		public void setValue(Object value)
		{
			this.checkBox.setSelected((Boolean)value);
		}
	}

	private static class ComplexPropertyConfiguration extends PropertyConfiguration
	{
		private static String COMBO_BOX_NO_VALUE = "Choose an implementation";

		private javax.swing.JComboBox<Object> implementationCombo;
		private java.util.Map<Class<?>, BeanConfiguration<Object>> implementationConfigurations;

		public ComplexPropertyConfiguration(String propertyName, Class<?> propertyType)
		{
			super(propertyName, propertyType);
			this.implementationConfigurations = new java.util.HashMap<Class<?>, BeanConfiguration<Object>>();

			this.panel.setBorder(new javax.swing.border.TitledBorder(""));

			this.implementationCombo = new javax.swing.JComboBox<Object>();
			java.awt.GridBagConstraints comboConstraints = new java.awt.GridBagConstraints();
			comboConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
			comboConstraints.gridx = 0;
			comboConstraints.gridy = 0;
			comboConstraints.weightx = 1;
			this.panel.add(this.implementationCombo, comboConstraints);

			final java.awt.CardLayout implementationLayout = new java.awt.CardLayout();
			final javax.swing.JPanel implementationCards = new javax.swing.JPanel(implementationLayout);
			java.awt.GridBagConstraints cardsConstraints = new java.awt.GridBagConstraints();
			cardsConstraints.fill = java.awt.GridBagConstraints.BOTH;
			cardsConstraints.gridx = 0;
			cardsConstraints.gridy = 1;
			cardsConstraints.weightx = 1;
			cardsConstraints.weighty = 1;
			this.panel.add(implementationCards, cardsConstraints);

			this.implementationCombo.addItem(COMBO_BOX_NO_VALUE);
			implementationCards.add(new javax.swing.JPanel(), "");

			// TODO: This obviously won't work for anything that isn't in
			// this package, so make this more general
			for(Class<?> c: GameTypeDialog.findImplementations(this.propertyType, "org.rnd.jmagic.engine.gameTypes.packWars"))
			{
				BeanConfiguration<Object> implementationConfiguration = new BeanConfiguration<Object>(c);
				this.implementationConfigurations.put(c, implementationConfiguration);
				this.implementationCombo.addItem(implementationConfiguration);

				javax.swing.JPanel implementationPanel = new javax.swing.JPanel(new java.awt.GridBagLayout());
				int y = 0;

				for(PropertyConfiguration propertyConfiguration: implementationConfiguration.properties.values())
				{
					javax.swing.JPanel propertyPanel = propertyConfiguration.getPanel();
					java.awt.GridBagConstraints propertyConstraints = new java.awt.GridBagConstraints();
					propertyConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
					propertyConstraints.gridx = 0;
					propertyConstraints.gridy = y++;
					propertyConstraints.weightx = 1;
					implementationPanel.add(propertyPanel, propertyConstraints);
				}

				implementationCards.add(implementationPanel, implementationConfiguration.name);
			}

			this.implementationCombo.addActionListener(new java.awt.event.ActionListener()
			{
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					for(BeanConfiguration<Object> c: ComplexPropertyConfiguration.this.implementationConfigurations.values())
						c.clear();
					implementationLayout.show(implementationCards, ComplexPropertyConfiguration.this.implementationCombo.getSelectedItem().toString());
					implementationCards.revalidate();
				}
			});
		}

		@Override
		public void clear()
		{
			this.implementationCombo.setSelectedItem("");
			for(BeanConfiguration<?> c: this.implementationConfigurations.values())
				c.clear();
		}

		@Override
		@SuppressWarnings("unchecked")
		public Object getValue()
		{
			Object selectedItem = this.implementationCombo.getSelectedItem();
			if(selectedItem.equals(COMBO_BOX_NO_VALUE))
			{
				LOG.warning(this.propertyName + " must have an implementation selected to get a value from");
				return null;
			}
			return ((BeanConfiguration<Object>)(this.implementationCombo.getSelectedItem())).getValue();
		}

		@Override
		public void setValue(Object value)
		{
			Class<?> valueClass = value.getClass();
			if(!this.implementationConfigurations.containsKey(valueClass))
			{
				LOG.severe("Complex property " + this.propertyName + " doesn't contain implementation configuration for value of type " + valueClass);
				return;
			}

			for(java.util.Map.Entry<Class<?>, BeanConfiguration<Object>> e: this.implementationConfigurations.entrySet())
			{
				if(e.getKey().equals(valueClass))
				{
					BeanConfiguration<Object> implementationConfiguration = e.getValue();
					this.implementationCombo.setSelectedItem(implementationConfiguration);
					implementationConfiguration.setValue(value);
				}
				else
					e.getValue().clear();
			}
		}
	}

	private static class EnumerationPropertyConfiguration extends PropertyConfiguration
	{
		private javax.swing.JComboBox<Object> comboBox;

		public EnumerationPropertyConfiguration(String propertyName, Class<?> propertyType)
		{
			super(propertyName, propertyType);
			@SuppressWarnings("unchecked") Class<Enum<?>> enumType = (Class<Enum<?>>)propertyType;

			try
			{
				this.comboBox = new javax.swing.JComboBox<Object>((Object[])(enumType.getMethod("values").invoke(null)));
				java.awt.GridBagConstraints textConstraints = new java.awt.GridBagConstraints();
				textConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
				textConstraints.gridx = 0;
				textConstraints.gridy = 0;
				this.panel.add(this.comboBox, textConstraints);

				javax.swing.JLabel label = new javax.swing.JLabel(this.propertyName + " (" + this.propertyType + ")");
				java.awt.GridBagConstraints labelConstraints = new java.awt.GridBagConstraints();
				labelConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
				labelConstraints.gridx = 1;
				labelConstraints.gridy = 0;
				labelConstraints.weightx = 1;
				this.panel.add(label, labelConstraints);
			}
			catch(NoSuchMethodException e)
			{
				LOG.log(java.util.logging.Level.SEVERE, enumType + " is supposed to be an enumeration type but doesn't have a values() method", e);
			}
			catch(IllegalArgumentException e)
			{
				LOG.log(java.util.logging.Level.SEVERE, " null is not a legal argument to " + enumType + " values() method", e);
			}
			catch(SecurityException e)
			{
				LOG.log(java.util.logging.Level.SEVERE, enumType + " values() method is not allowed to be called", e);
			}
			catch(IllegalAccessException e)
			{
				LOG.log(java.util.logging.Level.SEVERE, enumType + " values() method is not public", e);
			}
			catch(java.lang.reflect.InvocationTargetException e)
			{
				LOG.log(java.util.logging.Level.SEVERE, enumType + " values() threw an exception", e.getCause());
			}
		}

		@Override
		public void clear()
		{
			this.comboBox.setSelectedIndex(-1);
		}

		@Override
		public Object getValue()
		{
			return this.comboBox.getSelectedItem();
		}

		@Override
		public void setValue(Object value)
		{
			this.comboBox.setSelectedItem(value);
		}
	}

	private static class IndexedPropertyConfiguration extends PropertyConfiguration
	{
		private final java.util.LinkedHashMap<javax.swing.JPanel, PropertyConfiguration> individuals;

		public IndexedPropertyConfiguration(String propertyName, Class<?> indexedPropertyType)
		{
			super(propertyName, indexedPropertyType);
			this.individuals = new java.util.LinkedHashMap<javax.swing.JPanel, PropertyConfiguration>();

			rebuildPanel();
		}

		@Override
		public void clear()
		{
			this.individuals.clear();
			rebuildPanel();
		}

		private PropertyConfiguration createNewIndividualConfiguration()
		{
			PropertyConfiguration individual = PropertyConfiguration.create(this.propertyName + "[" + this.individuals.size() + "]", this.propertyType);
			this.individuals.put(individual.getPanel(), individual);
			return individual;
		}

		@Override
		public Object[] getValue()
		{
			Object[] ret = (Object[])java.lang.reflect.Array.newInstance(this.propertyType, this.individuals.size());
			int i = 0;
			for(PropertyConfiguration p: this.individuals.values())
			{
				Object value = p.getValue();
				if(null == value)
					return null;

				ret[i] = value;
				++i;
			}
			return ret;
		}

		private void rebuildPanel()
		{
			this.panel.removeAll();
			int y = 0;

			javax.swing.JButton addButton = new javax.swing.JButton("Add to " + this.propertyName);
			java.awt.GridBagConstraints addConstraints = new java.awt.GridBagConstraints();
			addConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
			addConstraints.gridx = 0;
			addConstraints.gridy = y++;
			addConstraints.weightx = 1;
			this.panel.add(addButton, addConstraints);

			addButton.addActionListener(new java.awt.event.ActionListener()
			{
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					IndexedPropertyConfiguration.this.createNewIndividualConfiguration();
					IndexedPropertyConfiguration.this.rebuildPanel();
				}
			});

			for(final javax.swing.JPanel panel: this.individuals.keySet())
			{
				java.awt.GridBagConstraints panelConstraints = new java.awt.GridBagConstraints();
				panelConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
				panelConstraints.gridx = 0;
				panelConstraints.gridy = y;
				panelConstraints.weightx = 1;
				this.panel.add(panel, panelConstraints);

				javax.swing.JButton removeButton = new javax.swing.JButton("Remove");
				java.awt.GridBagConstraints removeConstraints = new java.awt.GridBagConstraints();
				removeConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
				removeConstraints.gridx = 1;
				removeConstraints.gridy = y;
				this.panel.add(removeButton, removeConstraints);

				y++;

				removeButton.addActionListener(new java.awt.event.ActionListener()
				{
					@Override
					public void actionPerformed(java.awt.event.ActionEvent e)
					{
						IndexedPropertyConfiguration.this.individuals.remove(panel);
						IndexedPropertyConfiguration.this.rebuildPanel();
					}
				});
			}

			this.panel.revalidate();
		}

		@Override
		public void setValue(Object value)
		{
			if(!(value instanceof Object[]))
				throw new UnsupportedOperationException("Can't set an indexed property with a non-array");

			this.individuals.clear();

			Object[] array = (Object[])value;
			for(int i = 0; i < array.length; ++i)
				this.createNewIndividualConfiguration().setValue(array[i]);
			this.rebuildPanel();
		}
	}

	private static class IndexedStringPropertyConfiguration extends PropertyConfiguration
	{
		private final javax.swing.JTextArea textArea;

		public IndexedStringPropertyConfiguration(String propertyName)
		{
			super(propertyName, String.class);

			this.textArea = new javax.swing.JTextArea(5, 30);
			java.awt.GridBagConstraints textConstraints = new java.awt.GridBagConstraints();
			textConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
			textConstraints.gridx = 0;
			textConstraints.gridy = 0;
			this.panel.add(this.textArea, textConstraints);

			javax.swing.JLabel label = new javax.swing.JLabel(this.propertyName + " (" + this.propertyType + ") delimited by line-breaks");
			java.awt.GridBagConstraints labelConstraints = new java.awt.GridBagConstraints();
			labelConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
			labelConstraints.gridx = 1;
			labelConstraints.gridy = 0;
			labelConstraints.weightx = 1;
			this.panel.add(label, labelConstraints);
		}

		@Override
		public void clear()
		{
			this.textArea.setText("");
		}

		@Override
		public String[] getValue()
		{
			return this.textArea.getText().split("\n");
		}

		@Override
		public void setValue(Object value)
		{
			if(!(value instanceof String[]))
				throw new UnsupportedOperationException("Can't set an indexed property with a non-array");

			String[] array = (String[])value;
			StringBuilder text = new StringBuilder();
			for(int i = 0; i < array.length; ++i)
			{
				if(0 != text.length())
					text.append("\n");
				text.append(array[i]);
			}
			this.textArea.setText(text.toString());
		}
	}

	private static class IntegerPropertyConfiguration extends PropertyConfiguration
	{
		private javax.swing.JTextField textBox;

		public IntegerPropertyConfiguration(String propertyName, Class<?> propertyType)
		{
			super(propertyName, propertyType);

			this.textBox = new javax.swing.JTextField(8);
			java.awt.GridBagConstraints textConstraints = new java.awt.GridBagConstraints();
			textConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
			textConstraints.gridx = 0;
			textConstraints.gridy = 0;
			this.panel.add(this.textBox, textConstraints);

			javax.swing.JLabel label = new javax.swing.JLabel(this.propertyName + " (" + this.propertyType + ")");
			java.awt.GridBagConstraints labelConstraints = new java.awt.GridBagConstraints();
			labelConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
			labelConstraints.gridx = 1;
			labelConstraints.gridy = 0;
			labelConstraints.weightx = 1;
			this.panel.add(label, labelConstraints);
		}

		@Override
		public void clear()
		{
			this.textBox.setText("");
		}

		@Override
		public Object getValue()
		{
			try
			{
				return Integer.parseInt(this.textBox.getText());
			}
			catch(NumberFormatException e)
			{
				LOG.log(java.util.logging.Level.WARNING, this.propertyName + " must have a valid number value", e);
				return null;
			}
		}

		@Override
		public void setValue(Object value)
		{
			this.textBox.setText(value.toString());
		}
	}

	private abstract static class PropertyConfiguration
	{
		public static PropertyConfiguration create(java.beans.PropertyDescriptor descriptor)
		{
			if(descriptor instanceof java.beans.IndexedPropertyDescriptor)
			{
				Class<?> type = ((java.beans.IndexedPropertyDescriptor)descriptor).getIndexedPropertyType();
				if(String.class.equals(type))
					return new IndexedStringPropertyConfiguration(descriptor.getName());
				return new IndexedPropertyConfiguration(descriptor.getName(), type);
			}
			return create(descriptor.getName(), descriptor.getPropertyType());
		}

		public static PropertyConfiguration create(String propertyName, Class<?> propertyType)
		{
			if(Boolean.class.equals(propertyType) || boolean.class.equals(propertyType))
				return new BooleanPropertyConfiguration(propertyName, propertyType);
			if(Enum.class.isAssignableFrom(propertyType))
				return new EnumerationPropertyConfiguration(propertyName, propertyType);
			if(Integer.class.equals(propertyType) || int.class.equals(propertyType))
				return new IntegerPropertyConfiguration(propertyName, propertyType);
			if(String.class.equals(propertyType))
				return new StringPropertyConfiguration(propertyName, propertyType);
			return new ComplexPropertyConfiguration(propertyName, propertyType);
		}

		protected final javax.swing.JPanel panel;
		protected final String propertyName;
		protected final Class<?> propertyType;

		public PropertyConfiguration(String propertyName, Class<?> propertyType)
		{
			this.panel = new javax.swing.JPanel(new java.awt.GridBagLayout());
			this.propertyName = propertyName;
			this.propertyType = propertyType;
		}

		/**
		 * Clear any controls on the panel returned by {@link #getPanel()}.
		 */
		public abstract void clear();

		/**
		 * Get the panel with controls to change the state of this property.
		 */
		public final javax.swing.JPanel getPanel()
		{
			return this.panel;
		}

		/**
		 * Get the value of this property based on the state of the controls on
		 * the panel returned by {@link #getPanel()}.
		 * 
		 * @return Non-{@code null} for a valid value, {@code null} if an error
		 * occurred and a value can't be returned
		 */
		public abstract Object getValue();

		/**
		 * Set the state of controls on the panel returned by {@link #getPanel}
		 * to correspond to a value.
		 */
		public abstract void setValue(Object value);
	}

	private static class RuleConfiguration extends BeanConfiguration<GameType.GameTypeRule>
	{
		// TODO: Can't this be set by the platform look-and-feel?
		private static final int SCROLL_INCREMENT = 5;
		public final javax.swing.JToggleButton checkBox;
		public final javax.swing.tree.DefaultMutableTreeNode node;
		public final javax.swing.JScrollPane scrollPane;

		public RuleConfiguration(Class<? extends GameType.GameTypeRule> ruleClass)
		{
			super(ruleClass);
			this.node = new javax.swing.tree.DefaultMutableTreeNode(this.name);

			javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridBagLayout());
			int y = 0;

			this.checkBox = new javax.swing.JCheckBox("Enable");
			java.awt.GridBagConstraints enableConstraints = new java.awt.GridBagConstraints();
			enableConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
			enableConstraints.gridx = 0;
			enableConstraints.gridy = y++;
			enableConstraints.weightx = 1;
			panel.add(this.checkBox, enableConstraints);

			for(PropertyConfiguration propertyConfiguration: this.properties.values())
			{
				javax.swing.JPanel propertyPanel = propertyConfiguration.getPanel();
				java.awt.GridBagConstraints propertyConstraints = new java.awt.GridBagConstraints();
				propertyConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
				propertyConstraints.gridx = 0;
				propertyConstraints.gridy = y++;
				propertyConstraints.weightx = 1;

				panel.add(propertyPanel, propertyConstraints);
			}

			// Create a description panel
			String description = this.beanClass.getAnnotation(Description.class).value();
			javax.swing.JTextPane descriptionPanel = new javax.swing.JTextPane();
			descriptionPanel.setEditable(false);
			descriptionPanel.setText(description);
			java.awt.GridBagConstraints descriptionConstraints = new java.awt.GridBagConstraints();
			descriptionConstraints.fill = java.awt.GridBagConstraints.BOTH;
			descriptionConstraints.gridx = 0;
			descriptionConstraints.gridy = y++;
			descriptionConstraints.weightx = 1;
			descriptionConstraints.weighty = 1;
			panel.add(descriptionPanel, descriptionConstraints);

			this.scrollPane = new javax.swing.JScrollPane(panel, javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			this.scrollPane.getVerticalScrollBar().setUnitIncrement(SCROLL_INCREMENT);
		}

		@Override
		public void clear()
		{
			this.checkBox.setSelected(false);
			super.clear();
		}

		@Override
		public void setValue(GameType.GameTypeRule beanValue)
		{
			this.checkBox.setSelected(true);
			super.setValue(beanValue);
		}
	}

	private static class StringPropertyConfiguration extends PropertyConfiguration
	{
		private javax.swing.JTextField textBox;

		public StringPropertyConfiguration(String propertyName, Class<?> propertyType)
		{
			super(propertyName, propertyType);

			this.textBox = new javax.swing.JTextField(8);
			java.awt.GridBagConstraints textConstraints = new java.awt.GridBagConstraints();
			textConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
			textConstraints.gridx = 0;
			textConstraints.gridy = 0;
			this.panel.add(this.textBox, textConstraints);

			javax.swing.JLabel label = new javax.swing.JLabel(this.propertyName + " (" + this.propertyType + ")");
			java.awt.GridBagConstraints labelConstraints = new java.awt.GridBagConstraints();
			labelConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
			labelConstraints.gridx = 1;
			labelConstraints.gridy = 0;
			labelConstraints.weightx = 1;
			this.panel.add(label, labelConstraints);
		}

		@Override
		public void clear()
		{
			this.textBox.setText("");
		}

		@Override
		public Object getValue()
		{
			return this.textBox.getText();
		}

		@Override
		public void setValue(Object value)
		{
			this.textBox.setText(value.toString());
		}
	}

	private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger("org.rnd.jmagic.gui.dialogs.GameTypeDialog");

	private static final long serialVersionUID = 1L;

	private static <T> java.util.SortedSet<Class<? extends T>> findImplementations(Class<T> rootType, String pkg)
	{
		return findImplementations(rootType, pkg, null);
	}

	private static <T> java.util.SortedSet<Class<? extends T>> findImplementations(Class<T> rootType, String pkg, Class<? extends java.lang.annotation.Annotation> ignoreAnnotation)
	{
		java.util.SortedSet<Class<? extends T>> ret = new java.util.TreeSet<Class<? extends T>>(new java.util.Comparator<Class<? extends T>>()
		{
			@Override
			public int compare(Class<? extends T> o1, Class<? extends T> o2)
			{
				return o1.getAnnotation(Name.class).value().compareTo(o2.getAnnotation(Name.class).value());
			}
		});

		org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider scanner = new org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new org.springframework.core.type.filter.AssignableTypeFilter(rootType));
		if(ignoreAnnotation != null)
			scanner.addExcludeFilter(new org.springframework.core.type.filter.AnnotationTypeFilter(ignoreAnnotation));

		for(org.springframework.beans.factory.config.BeanDefinition bean: scanner.findCandidateComponents(pkg))
		{
			if((bean instanceof org.springframework.context.annotation.ScannedGenericBeanDefinition) //
					&& ((org.springframework.context.annotation.ScannedGenericBeanDefinition)bean).getMetadata().isConcrete())
			{
				try
				{
					@SuppressWarnings("unchecked") Class<? extends T> c = (Class<? extends T>)(Class.forName(bean.getBeanClassName()));
					ret.add(c);
				}
				catch(ClassNotFoundException e)
				{
					LOG.log(java.util.logging.Level.SEVERE, "Error loading class from scanned game-type rules", e);
				}
			}
		}

		return ret;
	}

	private GameType gameType;

	private String gameTypeName;

	private java.util.Map<Class<? extends GameType.GameTypeRule>, RuleConfiguration> ruleConfiguration;

	private javax.swing.JComboBox<Object> presetSelector;

	public GameTypeDialog(javax.swing.JFrame parent, java.util.Set<GameType> presets)
	{
		super(parent, "jMagic Game Type", true);
		this.gameType = null;
		this.gameTypeName = "Custom";
		this.ruleConfiguration = new java.util.HashMap<Class<? extends GameType.GameTypeRule>, RuleConfiguration>();
		this.setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new java.awt.event.WindowAdapter()
		{
			@Override
			public void windowClosing(java.awt.event.WindowEvent e)
			{
				GameTypeDialog.this.gameType = null;
				GameTypeDialog.this.setVisible(false);
			}
		});

		this.presetSelector = new javax.swing.JComboBox<Object>();
		this.presetSelector.addItem("");
		for(GameType preset: presets)
			this.presetSelector.addItem(preset);
		this.presetSelector.addItemListener(new java.awt.event.ItemListener()
		{
			@Override
			public void itemStateChanged(java.awt.event.ItemEvent e)
			{
				for(RuleConfiguration rule: GameTypeDialog.this.ruleConfiguration.values())
					rule.clear();

				if((java.awt.event.ItemEvent.SELECTED == e.getStateChange()) && !e.getItem().equals(""))
					GameTypeDialog.this.setGameType(e.getItem());
			}
		});

		javax.swing.JButton saveAsNewBaseButton = new javax.swing.JButton("Save as new base");
		saveAsNewBaseButton.addActionListener(new java.awt.event.ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
				String customName = javax.swing.JOptionPane.showInputDialog("What name do you want this game type to have (an empty name will cancel)?");
				if(customName == null || (0 == customName.length()))
					return;

				GameTypeDialog.this.gameTypeName = customName;
				GameType ret = GameTypeDialog.this.createGameType();
				GameTypeDialog.this.presetSelector.addItem(ret);
				GameTypeDialog.this.presetSelector.setSelectedItem(ret);
			}
		});

		javax.swing.JPanel topPanel = new javax.swing.JPanel();
		topPanel.add(new javax.swing.JLabel("Base on:"), java.awt.BorderLayout.LINE_START);
		topPanel.add(this.presetSelector);
		topPanel.add(saveAsNewBaseButton);
		this.add(topPanel, java.awt.BorderLayout.PAGE_START);

		final java.awt.CardLayout centerLayout = new java.awt.CardLayout();
		final javax.swing.JPanel centerPanel = new javax.swing.JPanel(centerLayout);

		String rootName = "Options";
		javax.swing.tree.DefaultMutableTreeNode root = new javax.swing.tree.DefaultMutableTreeNode(rootName);

		javax.swing.JTextPane rootDescriptionPanel = new javax.swing.JTextPane();
		rootDescriptionPanel.setEditable(false);
		rootDescriptionPanel.setText("These are the possible options to build a game-type for jMagic.");
		centerPanel.add(rootDescriptionPanel, rootName);

		for(Class<? extends GameType.GameTypeRule> rule: findImplementations(GameType.GameTypeRule.class, "org.rnd.jmagic.engine.gameTypes", GameType.Ignore.class))
			createRuleConfiguration(rule, root, centerPanel);

		this.add(centerPanel, java.awt.BorderLayout.CENTER);

		final javax.swing.JTree ruleTree = new javax.swing.JTree(root);
		ruleTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener()
		{
			@Override
			public void valueChanged(javax.swing.event.TreeSelectionEvent e)
			{
				centerLayout.show(centerPanel, ruleTree.getLastSelectedPathComponent().toString());
			}
		});
		ruleTree.getSelectionModel().setSelectionMode(javax.swing.tree.TreeSelectionModel.SINGLE_TREE_SELECTION);
		this.add(new javax.swing.JScrollPane(ruleTree), java.awt.BorderLayout.LINE_START);

		javax.swing.JButton closeButton = new javax.swing.JButton("OK");
		closeButton.addActionListener(new java.awt.event.ActionListener()
		{
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
				GameType ret = GameTypeDialog.this.createGameType();
				if(null != ret)
				{
					GameTypeDialog.this.gameType = ret;
					GameTypeDialog.this.setVisible(false);
				}
			}
		});

		javax.swing.AbstractAction cancelAction = new javax.swing.AbstractAction("Cancel")
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e)
			{
				GameTypeDialog.this.dispose();
			}
		};
		javax.swing.JButton cancelButton = new javax.swing.JButton(cancelAction);

		javax.swing.KeyStroke cancelKeyStroke = javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		cancelButton.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(cancelKeyStroke, "Cancel");
		cancelButton.getActionMap().put("Cancel", cancelAction);

		javax.swing.JPanel bottomPanel = new javax.swing.JPanel();
		bottomPanel.add(closeButton);
		bottomPanel.add(cancelButton);
		this.add(bottomPanel, java.awt.BorderLayout.PAGE_END);

		this.pack();
	}

	private void createRuleConfiguration(Class<? extends GameType.GameTypeRule> ruleClass, javax.swing.tree.DefaultMutableTreeNode root, javax.swing.JPanel panelContainer)
	{
		// Don't create configuration for this rule if we've already created it
		if(this.ruleConfiguration.containsKey(ruleClass))
			return;

		RuleConfiguration config = new RuleConfiguration(ruleClass);

		// Add the option to the tree of options
		DependsOn dependsOn = ruleClass.getAnnotation(DependsOn.class);
		if(null != dependsOn)
		{
			Class<? extends GameType.GameTypeRule> parentOption = dependsOn.value();
			if(!this.ruleConfiguration.containsKey(parentOption))
				createRuleConfiguration(parentOption, root, panelContainer);
			this.ruleConfiguration.get(parentOption).node.add(config.node);
		}
		else
			root.add(config.node);

		this.ruleConfiguration.put(ruleClass, config);

		panelContainer.add(config.scrollPane, config.name);
	}

	private GameType createGameType()
	{
		GameType ret = new GameType(GameTypeDialog.this.gameTypeName);
		java.util.Set<Class<?>> rulesAlreadyCreated = new java.util.HashSet<Class<?>>();
		for(Class<? extends GameType.GameTypeRule> ruleClass: GameTypeDialog.this.ruleConfiguration.keySet())
			if(!createRule(ret, ruleClass, rulesAlreadyCreated))
				return null;

		return ret;
	}

	private boolean createRule(GameType gameType, Class<? extends GameType.GameTypeRule> ruleClass, java.util.Set<Class<?>> rulesAlreadyCreated)
	{
		// Don't create rules that are already created
		if(rulesAlreadyCreated.contains(ruleClass))
			return true;

		RuleConfiguration config = this.ruleConfiguration.get(ruleClass);

		// Don't create any rule that isn't selected
		if(!config.checkBox.isSelected())
			return true;

		DependsOn dependsOn = ruleClass.getAnnotation(DependsOn.class);
		if(null != dependsOn)
		{
			Class<? extends GameType.GameTypeRule> parentOption = dependsOn.value();
			if(!rulesAlreadyCreated.contains(parentOption))
				createRule(gameType, parentOption, rulesAlreadyCreated);
		}
		rulesAlreadyCreated.add(ruleClass);

		GameType.GameTypeRule value = config.getValue();
		if(null == value)
			return false;

		gameType.addRule(value);
		return true;
	}

	public GameType getGameType()
	{
		return this.gameType;
	}

	private void setGameType(Object object)
	{
		GameType gameType = (GameType)object;
		for(GameType.GameTypeRule rule: gameType.getRules())
		{
			Class<? extends GameType.GameTypeRule> ruleClass = rule.getClass();
			if(!GameTypeDialog.this.ruleConfiguration.containsKey(ruleClass))
			{
				LOG.severe(ruleClass + " missing from possible rules to configure");
				continue;
			}

			this.ruleConfiguration.get(ruleClass).setValue(rule);
		}
	}
}
