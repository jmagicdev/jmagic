package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Banishing Stroke")
@Types({Type.INSTANT})
@ManaCost("5W")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class BanishingStroke extends Card
{
	public BanishingStroke(GameState state)
	{
		super(state);

		// Put target artifact, creature, or enchantment on the bottom of its
		// owner's library.
		SetGenerator target = targetedBy(this.addTarget(Union.instance(ArtifactPermanents.instance(), CreaturePermanents.instance(), EnchantmentPermanents.instance()), "target artifact, creature, or enchantment"));

		this.addEffect(putOnBottomOfLibrary(target, "Put target artifact, creature, or enchantment on the bottom of its owner's library."));

		// Miracle (W) (You may cast this card for its miracle cost when you
		// draw it if it's the first card you drew this turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Miracle(state, "(W)"));
	}
}
