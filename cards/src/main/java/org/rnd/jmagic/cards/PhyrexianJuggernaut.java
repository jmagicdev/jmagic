package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;

@Name("Phyrexian Juggernaut")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.JUGGERNAUT})
@ManaCost("6")
@Printings({@Printings.Printed(ex = Expansion.MIRRODIN_BESIEGED, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class PhyrexianJuggernaut extends Card
{
	public PhyrexianJuggernaut(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Infect (This creature deals damage to creatures in the form of -1/-1
		// counters and to players in the form of poison counters.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Infect(state));

		// Phyrexian Juggernaut attacks each turn if able.
		this.addAbility(new org.rnd.jmagic.abilities.AttacksEachTurnIfAble(state, this.getName()));
	}
}
