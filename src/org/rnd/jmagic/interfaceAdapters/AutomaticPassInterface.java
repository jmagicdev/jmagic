package org.rnd.jmagic.interfaceAdapters;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.sanitized.*;

/**
 * An adapter interface to automatically pass during certain steps/phases/turns.
 * 
 * Defaults:
 * 
 * <pre>
 *           Your turn   Opponents' turns
 * Untap         Yes           Yes
 * Upkeep        Yes           Yes
 * Draw          Yes           Yes
 * Pre-C. Main    No           Yes
 * Beg. C.       Yes           Yes
 * Decl. A.       No            No
 * Decl. B.       No            No
 * C. Damage     Yes           Yes
 * End C.        Yes           Yes
 * Post-C. Main   No           Yes
 * End            No            No
 * Cleanup        No            No
 * </pre>
 */
public class AutomaticPassInterface extends SimpleConfigurableInterface
{
	private final static class APIOptionPanel extends org.rnd.jmagic.gui.dialogs.ConfigurationFrame.OptionPanel
	{
		private static final long serialVersionUID = 1L;

		private java.util.Map<Step.StepType, javax.swing.JCheckBox> playerOptions;
		private java.util.Map<Step.StepType, javax.swing.JCheckBox> opponentOptions;

		public APIOptionPanel()
		{
			super("Automatic Passes");

			this.setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.X_AXIS));

			this.playerOptions = new java.util.HashMap<Step.StepType, javax.swing.JCheckBox>();
			this.opponentOptions = new java.util.HashMap<Step.StepType, javax.swing.JCheckBox>();

			javax.swing.Box player = javax.swing.Box.createVerticalBox();
			player.setBorder(javax.swing.BorderFactory.createTitledBorder("Player"));

			javax.swing.Box opponent = javax.swing.Box.createVerticalBox();
			opponent.setBorder(javax.swing.BorderFactory.createTitledBorder("Opponent"));

			for(Step.StepType step: Step.StepType.values())
			{
				javax.swing.JCheckBox playerBox = new javax.swing.JCheckBox(step.toString());
				this.playerOptions.put(step, playerBox);
				player.add(playerBox);

				javax.swing.JCheckBox opponentBox = new javax.swing.JCheckBox(step.toString());
				this.opponentOptions.put(step, opponentBox);
				opponent.add(opponentBox);
			}

			javax.swing.Box hBox = javax.swing.Box.createHorizontalBox();
			hBox.add(player);
			hBox.add(opponent);

			javax.swing.Box vBox = javax.swing.Box.createVerticalBox();
			vBox.add(hBox);
			vBox.add(javax.swing.Box.createVerticalGlue());

			this.add(vBox);
		}

		@Override
		public void loadSettings(java.util.Properties properties)
		{
			for(Step.StepType step: Step.StepType.values())
			{
				this.playerOptions.get(step).setSelected(AutomaticPassInterface.getAutomaticPassOnPlayerStep(properties, step));
				this.opponentOptions.get(step).setSelected(AutomaticPassInterface.getAutomaticPassOnOpponentStep(properties, step));
			}
		}

		@Override
		public void saveChanges(java.util.Properties properties)
		{
			for(Step.StepType step: Step.StepType.values())
			{
				AutomaticPassInterface.setAutomaticPassOnPlayerStep(properties, step, this.playerOptions.get(step).isSelected());
				AutomaticPassInterface.setAutomaticPassOnOpponentStep(properties, step, this.opponentOptions.get(step).isSelected());
			}
		}
	}

	private static final String getKey(Step.StepType type, boolean player)
	{
		return "AutomaticPassInterface.PassStep." + (player ? "Player" : "Opponent") + "." + type.name();
	}

	private static final java.util.Set<Step.StepType> playerDefault = java.util.EnumSet.of(Step.StepType.UNTAP, Step.StepType.UPKEEP, Step.StepType.DRAW, Step.StepType.BEGINNING_OF_COMBAT, Step.StepType.COMBAT_DAMAGE, Step.StepType.END_OF_COMBAT);
	private static final java.util.Set<Step.StepType> opponentDefault = java.util.EnumSet.of(Step.StepType.UNTAP, Step.StepType.UPKEEP, Step.StepType.DRAW, Step.StepType.PRECOMBAT_MAIN, Step.StepType.BEGINNING_OF_COMBAT, Step.StepType.COMBAT_DAMAGE, Step.StepType.END_OF_COMBAT, Step.StepType.POSTCOMBAT_MAIN);

	private SanitizedGameState state;
	private int lastPassedTurn;
	private Step.StepType lastPassedStep;
	private int playerID;
	private java.util.Properties properties;

	public AutomaticPassInterface(ConfigurableInterface adapt)
	{
		super(adapt);

		this.state = null;
		this.lastPassedTurn = -1;
		this.lastPassedStep = null;
		this.playerID = -1;
		this.properties = null;
	}

	@Override
	public void alertState(SanitizedGameState sanitizedGameState)
	{
		this.state = sanitizedGameState;
		super.alertState(sanitizedGameState);
	}

	@Override
	public <T extends java.io.Serializable> java.util.List<Integer> choose(ChooseParameters<T> parameterObject)
	{
		if(parameterObject.type == ChoiceType.NORMAL_ACTIONS)
		{
			SanitizedZone stack = (SanitizedZone)(this.state.get(this.state.stack));
			if(stack.objects.isEmpty())
			{
				boolean pass = false;

				if(this.lastPassedTurn != this.state.turn || this.lastPassedStep != this.state.step)
				{
					if(this.state.turn == this.playerID)
					{
						if(getAutomaticPassOnPlayerStep(this.properties, this.state.step))
							pass = true;
					}
					else
					{
						if(getAutomaticPassOnOpponentStep(this.properties, this.state.step))
							pass = true;
					}
				}

				this.lastPassedTurn = this.state.turn;
				this.lastPassedStep = this.state.step;

				if(pass)
					return java.util.Collections.emptyList();
			}
			else
			{
				// The player might want to respond after the spell resolves.
				this.lastPassedTurn = this.state.turn;
				this.lastPassedStep = this.state.step;
			}
		}
		return super.choose(parameterObject);
	}

	@Override
	public void setPlayerID(int playerID)
	{
		this.playerID = playerID;
		super.setPlayerID(playerID);
	}

	@Override
	public void setProperties(java.util.Properties properties)
	{
		this.properties = properties;

		for(Step.StepType step: Step.StepType.values())
		{
			String playerKey = getKey(step, true);
			if(!properties.containsKey(playerKey))
				properties.setProperty(playerKey, Boolean.toString(playerDefault.contains(step)));

			String oppKey = getKey(step, false);
			if(!properties.containsKey(oppKey))
				properties.setProperty(oppKey, Boolean.toString(opponentDefault.contains(step)));
		}

		super.setProperties(properties);
	}

	public static boolean getAutomaticPassOnPlayerStep(java.util.Properties properties, Step.StepType type)
	{
		return Boolean.parseBoolean(properties.getProperty(getKey(type, true)));
	}

	public static boolean getAutomaticPassOnOpponentStep(java.util.Properties properties, Step.StepType type)
	{
		return Boolean.parseBoolean(properties.getProperty(getKey(type, false)));
	}

	public static void setAutomaticPassOnPlayerStep(java.util.Properties properties, Step.StepType type, Boolean pass)
	{
		String key = getKey(type, true);
		if(pass == null)
			properties.setProperty(key, Boolean.toString(playerDefault.contains(type)));
		else
			properties.setProperty(key, Boolean.toString(pass));
	}

	public static void setAutomaticPassOnOpponentStep(java.util.Properties properties, Step.StepType type, Boolean pass)
	{
		String key = getKey(type, false);
		if(pass == null)
			properties.setProperty(key, Boolean.toString(opponentDefault.contains(type)));
		else
			properties.setProperty(key, Boolean.toString(pass));
	}

	@Override
	public APIOptionPanel getOptionPanel()
	{
		return new APIOptionPanel();
	}
}
