package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Gelectrode")
@Types({Type.CREATURE})
@SubTypes({SubType.WEIRD})
@ManaCost("1UR")
@ColorIdentity({Color.BLUE, Color.RED})
public final class Gelectrode extends Card
{
	public static final class SpellsUntap extends EventTriggeredAbility
	{
		public SpellsUntap(GameState state)
		{
			super(state, "Whenever you cast an instant or sorcery spell, you may untap Gelectrode.");

			SimpleEventPattern youCastInstantOrSorcery = new SimpleEventPattern(EventType.BECOMES_PLAYED);
			youCastInstantOrSorcery.put(EventType.Parameter.PLAYER, You.instance());
			youCastInstantOrSorcery.put(EventType.Parameter.OBJECT, HasType.instance(Type.INSTANT, Type.SORCERY));
			this.addPattern(youCastInstantOrSorcery);

			EventFactory untap = untap(ABILITY_SOURCE_OF_THIS, "Untap Gelectrode");
			this.addEffect(youMay(untap, "You may untap Gelectrode."));
		}
	}

	public Gelectrode(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		// (T): Gelectrode deals 1 damage to target creature or player.
		this.addAbility(new org.rnd.jmagic.abilities.Ping(state, "Gelectrode"));

		// Whenever you cast an instant or sorcery spell, you may untap
		// Gelectrode.
		this.addAbility(new SpellsUntap(state));
	}
}
