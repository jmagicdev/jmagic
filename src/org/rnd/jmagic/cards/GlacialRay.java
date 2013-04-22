package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Glacial Ray")
@Types({Type.INSTANT})
@SubTypes({SubType.ARCANE})
@ManaCost("1R")
@Printings({@Printings.Printed(ex = Expansion.CHAMPIONS_OF_KAMIGAWA, r = Rarity.COMMON)})
@ColorIdentity({Color.RED})
public final class GlacialRay extends Card
{
	public GlacialRay(GameState state)
	{
		super(state);
		Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
		this.addEffect(spellDealDamage(2, targetedBy(target), "Glacial Ray deals 2 damage to target creature or player."));
		this.addAbility(org.rnd.jmagic.abilities.keywords.Splice.ontoArcane(state, "(1)(R)"));
	}
}
