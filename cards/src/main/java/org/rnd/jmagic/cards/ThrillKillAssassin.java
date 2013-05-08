package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Thrill-Kill Assassin")
@Types({Type.CREATURE})
@SubTypes({SubType.ASSASSIN, SubType.HUMAN})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class ThrillKillAssassin extends Card
{
	public ThrillKillAssassin(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(2);

		// Deathtouch
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Deathtouch(state));

		// Unleash (You may have this creature enter the battlefield with a
		// +1/+1 counter on it. It can't block as long as it has a +1/+1 counter
		// on it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Unleash(state));
	}
}
