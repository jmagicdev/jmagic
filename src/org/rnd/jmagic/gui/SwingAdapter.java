package org.rnd.jmagic.gui;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.interfaceAdapters.*;
import org.rnd.jmagic.sanitized.*;
import org.rnd.util.*;

public class SwingAdapter implements ConfigurableInterface
{
	private final java.util.SortedSet<org.rnd.jmagic.gui.dialogs.ConfigurationFrame.OptionPanel> options;

	private Play gui;
	private Deck deck;
	private String name;

	public SwingAdapter(Deck deck, String name)
	{
		this.options = new java.util.TreeSet<org.rnd.jmagic.gui.dialogs.ConfigurationFrame.OptionPanel>(new java.util.Comparator<org.rnd.jmagic.gui.dialogs.ConfigurationFrame.OptionPanel>()
		{
			@Override
			public int compare(org.rnd.jmagic.gui.dialogs.ConfigurationFrame.OptionPanel o1, org.rnd.jmagic.gui.dialogs.ConfigurationFrame.OptionPanel o2)
			{
				return o1.getName().compareTo(o2.getName());
			}
		});

		this.gui = new Play(this.getOptions());

		this.deck = deck;
		this.name = name;
	}

	@Override
	public void alertChoice(final int playerID, final ChooseParameters<?> choice)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				SwingAdapter.this.gui.alertChoice(playerID, choice);
			}
		});
	}

	@Override
	public void alertError(final ErrorParameters parameters)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				SwingAdapter.this.gui.alertError(parameters);
			}
		});
	}

	@Override
	public void alertEvent(final SanitizedEvent event)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				SwingAdapter.this.gui.alertEvent(event);
			}
		});
	}

	@Override
	public void alertState(final SanitizedGameState sanitizedGameState)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				SwingAdapter.this.gui.alertState(sanitizedGameState);
			}
		});
	}

	@Override
	public void alertStateReversion(final PlayerInterface.ReversionParameters parameters)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				SwingAdapter.this.gui.alertStateReversion(parameters);
			}
		});
	}

	@Override
	public void alertWaiting(final SanitizedPlayer who)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				SwingAdapter.this.gui.alertWaiting(who);
			}
		});
	}

	@Override
	public <T extends java.io.Serializable> java.util.List<Integer> choose(final ChooseParameters<T> parameterObject)
	{
		synchronized(this.gui)
		{
			this.gui.choiceReady = false;
			javax.swing.SwingUtilities.invokeLater(new Runnable()
			{
				@Override
				public void run()
				{
					synchronized(SwingAdapter.this.gui)
					{
						SwingAdapter.this.gui.choose(parameterObject);
					}
				}
			});
			try
			{
				while(!this.gui.choiceReady)
					this.gui.wait();
			}
			catch(InterruptedException e)
			{
				throw new Game.InterruptedGameException();
			}
			return this.gui.choose;
		}
	}

	@Override
	public int chooseNumber(final NumberRange range, final String description)
	{
		synchronized(this.gui)
		{
			this.gui.choiceReady = false;
			javax.swing.SwingUtilities.invokeLater(new Runnable()
			{
				@Override
				public void run()
				{
					synchronized(SwingAdapter.this.gui)
					{
						SwingAdapter.this.gui.chooseNumber(range, description);
					}
				}
			});
			try
			{
				while(!this.gui.choiceReady)
					this.gui.wait();
			}
			catch(InterruptedException e)
			{
				throw new Game.InterruptedGameException();
			}
			return this.gui.chooseNumber;
		}
	}

	@Override
	public void divide(final int quantity, final int minimum, final int whatFrom, final String beingDivided, final java.util.List<SanitizedTarget> targets)
	{
		synchronized(this.gui)
		{
			this.gui.choiceReady = false;
			javax.swing.SwingUtilities.invokeLater(new Runnable()
			{
				@Override
				public void run()
				{
					synchronized(SwingAdapter.this.gui)
					{
						SwingAdapter.this.gui.divide(quantity, minimum, whatFrom, beingDivided, targets);
					}
				}
			});
			try
			{
				while(!this.gui.choiceReady)
					this.gui.wait();
			}
			catch(InterruptedException e)
			{
				throw new Game.InterruptedGameException();
			}
		}
	}

	public org.rnd.jmagic.comms.ChatManager.Callback getChatCallback()
	{
		return this.gui.getChatCallback();
	}

	@Override
	public Deck getDeck()
	{
		return this.deck;
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public org.rnd.jmagic.gui.dialogs.ConfigurationFrame.OptionPanel getOptionPanel()
	{
		return null;
	}

	@Override
	public java.util.SortedSet<org.rnd.jmagic.gui.dialogs.ConfigurationFrame.OptionPanel> getOptions()
	{
		return this.options;
	}

	public void setMessagePoster(org.rnd.jmagic.comms.ChatManager.MessagePoster messagePoster)
	{
		this.gui.setMessagePoster(messagePoster);
	}

	@Override
	public void setPlayerID(final int playerID)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				SwingAdapter.this.gui.setPlayerID(playerID);
			}
		});
	}

	@Override
	public void setProperties(final java.util.Properties properties)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				SwingAdapter.this.gui.setProperties(properties);
			}
		});
	}
}
