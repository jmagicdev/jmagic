package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Phyrexian Vatmother")
@Types({Type.CREATURE})
@SubTypes({SubType.HORROR})
@ManaCost("2BB")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class PhyrexianVatmother extends Card
{
	public static final class PhyrexianVatmotherAbility1 extends EventTriggeredAbility
	{
		public PhyrexianVatmotherAbility1(GameState state)
		{
			super(state, "At the beginning of your upkeep, you get a poison counter.");
			this.addPattern(atTheBeginningOfYourUpkeep());
			this.addEffect(putCounters(1, Counter.CounterType.POISON, You.instance(), "You get a poison counter."));
		}
	}

	public PhyrexianVatmother(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(5);

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));

		// At the beginning of your upkeep, you get a poison counter.
		this.addAbility(new PhyrexianVatmotherAbility1(state));
	}
}
