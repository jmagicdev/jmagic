package org.rnd.jmagic.abilities;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

import static org.rnd.jmagic.Convenience.*;

public abstract class PlayExtraLands extends StaticAbility
{
	private static class CanBeUsed extends SetGenerator
	{
		public static CanBeUsed instance(int extraLandsID)
		{
			return new CanBeUsed(extraLandsID);
		}

		private final int extraLandsID;

		private CanBeUsed(int extraLandsID)
		{
			this.extraLandsID = extraLandsID;
		}

		@Override
		public Set evaluate(GameState state, Identified thisObject)
		{
			if(state.currentTurn() == null)
				return NonEmpty.instance().evaluate(state, thisObject);

			PlayExtraLands ability = state.<PlayExtraLands>get(this.extraLandsID);
			if(ability.number != null && ability.flag.getValue(state) >= ability.number)
				return Empty.instance().evaluate(state, thisObject);
			return NonEmpty.instance().evaluate(state, thisObject);
		}
	}

	private GenericIntegerTracker flag;
	protected final Integer number;
	protected final SetGenerator who;
	protected final String abilityName;

	public PlayExtraLands(GameState state, Integer number, String abilityName)
	{
		this(state, number, You.instance(), abilityName);
	}

	public PlayExtraLands(GameState state, Integer number, SetGenerator who, String abilityName)
	{
		super(state, abilityName);
		this.flag = new GenericIntegerTracker();

		this.number = number;
		this.who = who;
		this.abilityName = abilityName;

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PLAY_ADDITIONAL_LANDS);
		part.parameters.put(ContinuousEffectType.Parameter.PLAYER, who);
		if(number == null)
			part.parameters.put(ContinuousEffectType.Parameter.NUMBER, Empty.instance());
		else
			part.parameters.put(ContinuousEffectType.Parameter.NUMBER, numberGenerator(number));
		this.addEffectPart(part);

		this.setCanApply(this.canApply);
	}

	@Override
	public PlayExtraLands clone(GameState state)
	{
		PlayExtraLands ret = (PlayExtraLands)super.clone(state);
		ret.flag = this.flag.clone();
		return ret;
	}

	protected void setCanApply(SetGenerator canApply)
	{
		this.canApply = Both.instance(canApply, CanBeUsed.instance(this.ID));
	}

	public static final class Final extends PlayExtraLands
	{
		public Final(GameState state, Integer number, String abilityName)
		{
			super(state, number, abilityName);
		}

		public Final(GameState state, Integer number, SetGenerator who, String abilityName)
		{
			super(state, number, who, abilityName);
		}

		@Override
		public Final create(Game game)
		{
			return new Final(game.physicalState, this.number, this.who, this.abilityName);
		}
	}
}
