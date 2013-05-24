package org.rnd.jmagic.interfaceAdapters;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.sanitized.*;

/**
 * This class intercepts choices for activating a single mana ability and
 * replaces it by allowing the player to activate multiple mana abilities, and
 * returning them to the engine one at a time.
 */
public class ManaAbilitiesAdapter extends SimplePlayerInterface
{
	private java.util.Queue<SanitizedPlayerAction> choices;
	private boolean done;

	private static java.util.Set<PlayerInterface.ChoiceType> ignores;
	static
	{
		ignores = java.util.EnumSet.of( //
		PlayerInterface.ChoiceType.COLOR, // e.g. black lotus
		PlayerInterface.ChoiceType.COSTS, // mana ability with multiple costs
		PlayerInterface.ChoiceType.MANA_EXPLOSION, // e.g. filterlands
		PlayerInterface.ChoiceType.MANA_PAYMENT, // e.g. filterlands
		PlayerInterface.ChoiceType.MOVEMENT_GRAVEYARD // e.g. black lotus
		);
	}

	public ManaAbilitiesAdapter(PlayerInterface adapt)
	{
		super(adapt);
		this.choices = new java.util.LinkedList<SanitizedPlayerAction>();
		this.done = false;
	}

	@Override
	public <T extends java.io.Serializable> java.util.List<Integer> choose(ChooseParameters<T> parameterObject)
	{
		if(parameterObject.type == PlayerInterface.ChoiceType.ACTIVATE_MANA_ABILITIES)
		{
			if(this.done || parameterObject.choices.isEmpty())
				return java.util.Collections.emptyList();

			int index = -1;
			while(index == -1)
			{
				if(this.choices.isEmpty())
				{
					ChooseParameters<T> newParameters = new ChooseParameters<T>(parameterObject);
					newParameters.number = new Set(new org.rnd.util.NumberRange(0, null));

					java.util.List<Integer> chosen = super.choose(newParameters);
					if(chosen.isEmpty())
						return chosen;
					for(Integer i: chosen)
						this.choices.add((SanitizedPlayerAction)newParameters.choices.get(i));
				}

				SanitizedPlayerAction choice = this.choices.remove();
				if(this.choices.isEmpty())
					this.done = true;

				// If the choice can't be found the loop will repeat.
				index = parameterObject.choices.indexOf(choice);
			}

			return java.util.Collections.singletonList(index);
		}

		if(!ignores.contains(parameterObject.type))
			this.done = false;
		return super.choose(parameterObject);
	}
}
