package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Carnifex Demon")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("4BB")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class CarnifexDemon extends Card
{
	public static final class CarnifexDemonAbility2 extends ActivatedAbility
	{
		public CarnifexDemonAbility2(GameState state)
		{
			super(state, "(B), Remove a -1/-1 counter from Carnifex Demon: Put a -1/-1 counter on each other creature.");
			this.setManaCost(new ManaPool("(B)"));
			this.addCost(removeCounters(1, Counter.CounterType.MINUS_ONE_MINUS_ONE, ABILITY_SOURCE_OF_THIS, "Remove a -1/-1 counter from Carnifex Demon"));
			this.addEffect(putCounters(1, Counter.CounterType.MINUS_ONE_MINUS_ONE, RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS), "Put a -1/-1 counter on each other creature."));
		}
	}

	public CarnifexDemon(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// Carnifex Demon enters the battlefield with two -1/-1 counters on it.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldWithCounters(state, "Carnifex Demon", 2, Counter.CounterType.MINUS_ONE_MINUS_ONE));

		// (B), Remove a -1/-1 counter from Carnifex Demon: Put a -1/-1 counter
		// on each other creature.
		this.addAbility(new CarnifexDemonAbility2(state));
	}
}
