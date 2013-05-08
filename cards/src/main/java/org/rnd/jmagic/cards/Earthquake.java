package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Earthquake")
@Types({Type.SORCERY})
@ManaCost("XR")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SEVENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.PORTAL_SECOND_AGE, r = Rarity.RARE), @Printings.Printed(ex = Expansion.PORTAL, r = Rarity.RARE), @Printings.Printed(ex = Expansion.FIFTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.FOURTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.REVISED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.BETA, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class Earthquake extends Card
{
	public Earthquake(GameState state)
	{
		super(state);

		SetGenerator hasFlying = HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class);
		SetGenerator takers = RelativeComplement.instance(CREATURES_AND_PLAYERS, hasFlying);
		this.addEffect(spellDealDamage(ValueOfX.instance(This.instance()), takers, "Earthquake deals X damage to each creature without flying and each player."));
	}
}
