package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Earthquake")
@Types({Type.SORCERY})
@ManaCost("XR")
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.RARE), @Printings.Printed(ex = Magic2010.class, r = Rarity.RARE), @Printings.Printed(ex = SeventhEdition.class, r = Rarity.RARE), @Printings.Printed(ex = ClassicSixthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = PortalSecondAge.class, r = Rarity.RARE), @Printings.Printed(ex = Portal.class, r = Rarity.RARE), @Printings.Printed(ex = FifthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = FourthEdition.class, r = Rarity.RARE), @Printings.Printed(ex = RevisedEdition.class, r = Rarity.RARE), @Printings.Printed(ex = UnlimitedEdition.class, r = Rarity.RARE), @Printings.Printed(ex = LimitedEditionBeta.class, r = Rarity.RARE), @Printings.Printed(ex = LimitedEditionAlpha.class, r = Rarity.RARE)})
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
