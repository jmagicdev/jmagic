package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Carnivorous Moss-Beast")
@Types({Type.CREATURE})
@SubTypes({SubType.PLANT, SubType.ELEMENTAL, SubType.BEAST})
@ManaCost("4GG")
@ColorIdentity({Color.GREEN})
public final class CarnivorousMossBeast extends Card
{
	public static final class CarnivorousMossBeastAbility0 extends ActivatedAbility
	{
		public CarnivorousMossBeastAbility0(GameState state)
		{
			super(state, "(5)(G)(G): Put a +1/+1 counter on Carnivorous Moss-Beast.");
			this.setManaCost(new ManaPool("(5)(G)(G)"));
			this.addEffect(putCountersOnThis(1, Counter.CounterType.PLUS_ONE_PLUS_ONE, "Carnivorous Moss-Beast"));
		}
	}

	public CarnivorousMossBeast(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(5);

		// (5)(G)(G): Put a +1/+1 counter on Carnivorous Moss-Beast.
		this.addAbility(new CarnivorousMossBeastAbility0(state));
	}
}
