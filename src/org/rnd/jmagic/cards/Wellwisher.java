package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Wellwisher")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF})
@ManaCost("1G")
@Printings({@Printings.Printed(ex = Expansion.ONSLAUGHT, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class Wellwisher extends Card
{
	public static final class GainLife extends ActivatedAbility
	{
		public GainLife(GameState state)
		{
			super(state, "(T): You gain 1 life for each Elf on the battlefield.");
			this.costsTap = true;

			SetGenerator elves = Intersect.instance(HasSubType.instance(SubType.ELF), Permanents.instance());
			this.addEffect(gainLife(You.instance(), Count.instance(elves), "You gain 1 life for each Elf on the battlefield."));
		}
	}

	public Wellwisher(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// (T): You gain 1 life for each Elf on the battlefield.
		this.addAbility(new GainLife(state));
	}
}
