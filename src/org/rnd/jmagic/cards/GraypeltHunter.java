package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Graypelt Hunter")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.HUMAN, SubType.ALLY})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class GraypeltHunter extends Card
{
	public GraypeltHunter(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Trample
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Trample(state));

		// Whenever Graypelt Hunter or another Ally enters the battlefield under
		// your control, you may put a +1/+1 counter on Graypelt Hunter.
		this.addAbility(new org.rnd.jmagic.abilities.ZendikarAllyCounter(state, this.getName()));
	}
}
