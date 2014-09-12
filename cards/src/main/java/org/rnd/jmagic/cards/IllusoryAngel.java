package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.SimpleEventPattern;
import org.rnd.jmagic.engine.trackers.*;

@Name("Illusory Angel")
@Types({Type.CREATURE})
@SubTypes({SubType.ILLUSION, SubType.ANGEL})
@ManaCost("2U")
@ColorIdentity({Color.BLUE})
public final class IllusoryAngel extends Card
{
	public static final class HaventCastASpell extends SetGenerator
	{
		private static SetGenerator _instance = null;

		private HaventCastASpell()
		{
			// singleton
		}

		public static SetGenerator instance()
		{
			if(_instance == null)
				_instance = new HaventCastASpell();
			return _instance;
		}

		public Set evaluate(GameState state, Identified thisObject)
		{
			Player you = You.instance().evaluate(state, thisObject).getOne(Player.class);
			if(state.getTracker(CastTracker.class).getValue(state).containsKey(you.ID))
				return Empty.set;
			return NonEmpty.set;
		}
	}

	public static final class IllusoryAngelAbility1 extends StaticAbility
	{
		public IllusoryAngelAbility1(GameState state)
		{
			super(state, "Cast Illusory Angel only if you've cast another spell this turn.");
			state.ensureTracker(new CastTracker());
			this.canApply = HaventCastASpell.instance();

			SimpleEventPattern castSpell = new SimpleEventPattern(EventType.CAST_SPELL_OR_ACTIVATE_ABILITY);
			castSpell.put(EventType.Parameter.OBJECT, This.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.PROHIBIT);
			part.parameters.put(ContinuousEffectType.Parameter.PROHIBITION, Identity.instance(castSpell));
			this.addEffectPart(part);
		}
	}

	public IllusoryAngel(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Cast Illusory Angel only if you've cast another spell this turn.
		this.addAbility(new IllusoryAngelAbility1(state));
	}
}
