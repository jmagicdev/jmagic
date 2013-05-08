package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pentavus")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.CONSTRUCT})
@ManaCost("7")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({})
public final class Pentavus extends Card
{
	public static final class PentavusAbility1 extends ActivatedAbility
	{
		public PentavusAbility1(GameState state)
		{
			super(state, "(1), Remove a +1/+1 counter from Pentavus: Put a 1/1 colorless Pentavite artifact creature token with flying onto the battlefield.");
			this.setManaCost(new ManaPool("(1)"));
			this.addCost(removeCountersFromThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Pentavus"));

			CreateTokensFactory f = new CreateTokensFactory(1, 1, 1, "Put a 1/1 colorless Pentavite artifact creature token with flying onto the battlefield.");
			f.setSubTypes(SubType.PENTAVITE);
			f.setArtifact();
			f.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(f.getEventFactory());
		}
	}

	public static final class PentavusAbility2 extends ActivatedAbility
	{
		public PentavusAbility2(GameState state)
		{
			super(state, "(1), Sacrifice a Pentavite: Put a +1/+1 counter on Pentavus.");
			this.setManaCost(new ManaPool("(1)"));
			this.addCost(sacrifice(You.instance(), 1, HasSubType.instance(SubType.PENTAVITE), "Sacrifice a Pentavite"));
			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Pentavus"));
		}
	}

	public Pentavus(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(0);

		// Pentavus enters the battlefield with five +1/+1 counters on it.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, this.getName(), 5, Counter.CounterType.PLUS_ONE_PLUS_ONE));

		// (1), Remove a +1/+1 counter from Pentavus: Put a 1/1 colorless
		// Pentavite artifact creature token with flying onto the battlefield.
		this.addAbility(new PentavusAbility1(state));

		// (1), Sacrifice a Pentavite: Put a +1/+1 counter on Pentavus.
		this.addAbility(new PentavusAbility2(state));
	}
}
