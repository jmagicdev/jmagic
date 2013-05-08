package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Blightwidow")
@Types({Type.CREATURE})
@SubTypes({SubType.SPIDER})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class Blightwidow extends Card
{
	public Blightwidow(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// Reach (This creature can block creatures with flying.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Reach(state));

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));
	}
}
