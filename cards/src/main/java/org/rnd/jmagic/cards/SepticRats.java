package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Septic Rats")
@Types({Type.CREATURE})
@SubTypes({SubType.RAT})
@ManaCost("1BB")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class SepticRats extends Card
{
	public static final class SepticRatsAbility1 extends EventTriggeredAbility
	{
		public SepticRatsAbility1(GameState state)
		{
			super(state, "Whenever Septic Rats attacks, if defending player is poisoned, it gets +1/+1 until end of turn.");
			this.addPattern(whenThisAttacks());

			this.interveningIf = Intersect.instance(DefendingPlayer.instance(ABILITY_SOURCE_OF_THIS), Poisoned.instance());

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, +1, "It gets +1/+1 until end of turn."));
		}
	}

	public SepticRats(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));

		// Whenever Septic Rats attacks, if defending player is poisoned, it
		// gets +1/+1 until end of turn.
		this.addAbility(new SepticRatsAbility1(state));
	}
}
