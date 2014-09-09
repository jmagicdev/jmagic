package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Core Prowler")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.HORROR})
@ManaCost("4")
@ColorIdentity({})
public final class CoreProwler extends Card
{
	public static final class CoreProwlerAbility1 extends EventTriggeredAbility
	{
		public CoreProwlerAbility1(GameState state)
		{
			super(state, "When Core Prowler dies, proliferate. ");
			this.addPattern(whenThisDies());
			this.addEffect(proliferate());
		}
	}

	public CoreProwler(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));

		// When Core Prowler is put into a graveyard from the battlefield,
		// proliferate. (You choose any number of permanents and/or players with
		// counters on them, then give each another counter of a kind already
		// there.)
		this.addAbility(new CoreProwlerAbility1(state));
	}
}
