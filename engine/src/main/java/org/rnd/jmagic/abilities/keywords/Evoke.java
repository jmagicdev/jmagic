package org.rnd.jmagic.abilities.keywords;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class Evoke extends Keyword
{
	public static final String EVOKE_COST = "Evoke cost";

	protected final String evokeCostString;

	public Evoke(GameState state, String manaCost)
	{
		super(state, "Evoke " + manaCost);
		this.evokeCostString = manaCost;
	}

	@Override
	public Evoke create(Game game)
	{
		return new Evoke(game.physicalState, this.evokeCostString);
	}

	@Override
	protected java.util.List<StaticAbility> createStaticAbilities()
	{
		return java.util.Collections.<StaticAbility>singletonList(new EvokeCastAbility(this.state, this));
	}

	public static final class EvokeCastAbility extends StaticAbility
	{
		private Evoke parent;

		public EvokeCastAbility(GameState state, Evoke parent)
		{
			super(state, "You may cast this spell for its evoke cost.");
			this.parent = parent;

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ALTERNATE_COST);
			part.parameters.put(ContinuousEffectType.Parameter.COST, Identity.instance(new CostCollection(EVOKE_COST, parent.evokeCostString)));
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, You.instance());
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());

			this.addEffectPart(part);

			this.canApply = NonEmpty.instance();
		}

		@Override
		public EvokeCastAbility create(Game game)
		{
			return new EvokeCastAbility(game.physicalState, this.parent);
		}
	}

	@Override
	protected java.util.List<NonStaticAbility> createNonStaticAbilities()
	{
		return java.util.Collections.<NonStaticAbility>singletonList(new EvokeSacrificeAbility(this.state));
	}

	public static final class EvokeSacrificeAbility extends EventTriggeredAbility
	{
		public EvokeSacrificeAbility(GameState state)
		{
			super(state, "If you cast this creature for its evoke cost, it's sacrificed when it enters the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			this.interveningIf = WasEvoked.instance(ABILITY_SOURCE_OF_THIS);

			this.addEffect(sacrificeThis("this creature"));
		}
	}
}
