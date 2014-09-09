package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Shambling Shell")
@Types({Type.CREATURE})
@SubTypes({SubType.PLANT, SubType.ZOMBIE})
@ManaCost("1BG")
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class ShamblingShell extends Card
{
	public static final class ShamblingShellAbility0 extends ActivatedAbility
	{
		public ShamblingShellAbility0(GameState state)
		{
			super(state, "Sacrifice Shambling Shell: Put a +1/+1 counter on target creature.");
			this.addCost(sacrificeThis("Shambling Shell"));
			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(putCounters(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, target, "Put a +1/+1 counter on target creature."));
		}
	}

	public ShamblingShell(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(1);

		// Sacrifice Shambling Shell: Put a +1/+1 counter on target creature.
		this.addAbility(new ShamblingShellAbility0(state));

		// Dredge 3 (If you would draw a card, instead you may put exactly three
		// cards from the top of your library into your graveyard. If you do,
		// return this card from your graveyard to your hand. Otherwise, draw a
		// card.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Dredge(state, 3));
	}
}
