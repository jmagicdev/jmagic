package org.rnd.jmagic.abilities;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class CantBlock extends StaticAbility
{
	public static final class Factory implements AbilityFactory
	{
		private String cardName;

		public Factory(String cardName)
		{
			this.cardName = cardName;
		}

		@Override
		public Identified create(GameState state, Identified thisObject)
		{
			return new CantBlock(state, this.cardName);
		}

		@Override
		public Class<?> clazz()
		{
			return CantBlock.class;
		}
	}

	private String cardName;

	public CantBlock(GameState state, String cardName)
	{
		super(state, cardName + " can't block.");
		this.cardName = cardName;

		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
		part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Blocking.instance(), This.instance())));
		this.addEffectPart(part);
	}

	@Override
	public CantBlock create(Game game)
	{
		return new CantBlock(game.physicalState, this.cardName);
	}
}
