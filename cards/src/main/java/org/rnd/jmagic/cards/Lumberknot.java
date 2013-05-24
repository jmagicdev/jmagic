package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.engine.patterns.*;

@Name("Lumberknot")
@Types({Type.CREATURE})
@SubTypes({SubType.TREEFOLK})
@ManaCost("2GG")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class Lumberknot extends Card
{
	public static final class LumberknotAbility1 extends EventTriggeredAbility
	{
		public LumberknotAbility1(GameState state)
		{
			super(state, "Whenever a creature dies, put a +1/+1 counter on Lumberknot.");
			this.addPattern(new SimpleZoneChangePattern(Battlefield.instance(), GraveyardOf.instance(Players.instance()), CreaturePermanents.instance(), true));
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, ABILITY_SOURCE_OF_THIS, "Put a +1/+1 counter on Lumberknot."));
		}
	}

	public Lumberknot(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Hexproof (This creature can't be the target of spells or abilities
		// your opponents control.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Hexproof(state));

		// Whenever a creature dies, put a +1/+1 counter on Lumberknot.
		this.addAbility(new LumberknotAbility1(state));
	}
}
