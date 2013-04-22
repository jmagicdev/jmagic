package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Hada Freeblade")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.ALLY, SubType.HUMAN})
@ManaCost("W")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class HadaFreeblade extends Card
{
	public HadaFreeblade(GameState state)
	{
		super(state);

		this.setPower(0);
		this.setToughness(1);

		// Whenever Hada Freeblade or another Ally enters the battlefield under
		// your control, you may put a +1/+1 counter on Hada Freeblade.
		this.addAbility(new org.rnd.jmagic.abilities.ZendikarAllyCounter(state, this.getName()));
	}
}
