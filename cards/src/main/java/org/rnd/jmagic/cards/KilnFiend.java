package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Kiln Fiend")
@Types({Type.CREATURE})
@SubTypes({SubType.BEAST, SubType.ELEMENTAL})
@ManaCost("1R")
@ColorIdentity({Color.RED})
public final class KilnFiend extends Card
{
	public static final class SpellBoost extends EventTriggeredAbility
	{
		public SpellBoost(GameState state)
		{
			super(state, "Whenever you cast an instant or sorcery spell, Kiln Fiend gets +3/+0 until end of turn.");

			SimpleEventPattern youCastInstantOrSorcery = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			youCastInstantOrSorcery.put(EventType.Parameter.PLAYER, You.instance());
			youCastInstantOrSorcery.put(EventType.Parameter.OBJECT, HasType.instance(Type.INSTANT, Type.SORCERY));
			this.addPattern(youCastInstantOrSorcery);

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +3, +0, "Kiln Fiend gets +3/+0 until end of turn."));
		}
	}

	public KilnFiend(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Whenever you cast an instant or sorcery spell, Kiln Fiend gets +3/+0
		// until end of turn.
		this.addAbility(new SpellBoost(state));
	}
}
