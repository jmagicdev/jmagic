package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Safehold Elite")
@Types({Type.CREATURE})
@SubTypes({SubType.ELF, SubType.SCOUT})
@ManaCost("1(WG)")
@Printings({@Printings.Printed(ex = Shadowmoor.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class SafeholdElite extends Card
{
	public SafeholdElite(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Persist(state));
	}
}
