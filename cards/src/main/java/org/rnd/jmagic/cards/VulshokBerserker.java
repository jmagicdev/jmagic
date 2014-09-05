package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Vulshok Berserker")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.BERSERKER})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Magic2011.class, r = Rarity.COMMON), @Printings.Printed(ex = Mirrodin.class, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class VulshokBerserker extends Card
{
	public VulshokBerserker(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Haste (This creature can attack and (T) as soon as it comes under
		// your control.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));
	}
}
