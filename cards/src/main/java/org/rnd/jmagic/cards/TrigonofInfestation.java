package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Trigon of Infestation")
@Types({Type.ARTIFACT})
@ManaCost("4")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class TrigonofInfestation extends Card
{
	public static final class TrigonofInfestationAbility1 extends ActivatedAbility
	{
		public TrigonofInfestationAbility1(GameState state)
		{
			super(state, "(G)(G), (T): Put a charge counter on Trigon of Infestation.");
			this.setManaCost(new ManaPool("(G)(G)"));
			this.costsTap = true;
			this.addEffect(putCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Put a charge counter on Trigon of Infestation"));
		}
	}

	public static final class TrigonofInfestationAbility2 extends ActivatedAbility
	{
		public TrigonofInfestationAbility2(GameState state)
		{
			super(state, "(2), (T), Remove a charge counter from Trigon of Infestation: Put a 1/1 green Insect creature token with infect onto the battlefield.");
			this.setManaCost(new ManaPool("(2)"));
			this.costsTap = true;
			this.addCost(removeCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Remove a charge counter from Trigon of Infestation"));

			CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "Put a 1/1 green Insect creature token with infect onto the battlefield.");
			token.setColors(Color.GREEN);
			token.setSubTypes(SubType.INSECT);
			token.addAbility(org.rnd.jmagic.abilities.keywords.Infect.class);
			this.addEffect(token.getEventFactory());
		}
	}

	public TrigonofInfestation(GameState state)
	{
		super(state);

		// Trigon of Infestation enters the battlefield with three charge
		// counters on it.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, "Trigon of Infestation", 3, Counter.CounterType.CHARGE));

		// (G)(G), (T): Put a charge counter on Trigon of Infestation.
		this.addAbility(new TrigonofInfestationAbility1(state));

		// (2), (T), Remove a charge counter from Trigon of Infestation: Put a
		// 1/1 green Insect creature token with infect onto the battlefield.
		this.addAbility(new TrigonofInfestationAbility2(state));
	}
}
