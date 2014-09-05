package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Orim's Thunder")
@Types({Type.INSTANT})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = MagicTheGatheringCommander.class, r = Rarity.COMMON), @Printings.Printed(ex = Apocalypse.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.RED})
public final class OrimsThunder extends Card
{
	public OrimsThunder(GameState state)
	{
		super(state);

		// Kicker (R)
		org.rnd.jmagic.abilities.keywords.Kicker kicker = new org.rnd.jmagic.abilities.keywords.Kicker(state, "(R)");
		this.addAbility(kicker);

		// Destroy target artifact or enchantment.
		Target destroyTarget = this.addTarget(Union.instance(ArtifactPermanents.instance(), EnchantmentPermanents.instance()), "target artifact or enchantment");
		this.addEffect(destroy(targetedBy(destroyTarget), "Destroy target artifact or enchantment."));

		// If Orim's Thunder was kicked, it deals damage equal to that
		// permanent's converted mana cost to target creature.
		Target kickedTarget = this.addTarget(CreaturePermanents.instance(), "target creature");
		kickedTarget.condition = ThisSpellWasKicked.instance(kicker.costCollections[0]);

		SetGenerator targetCreature = targetedBy(kickedTarget);
		SetGenerator amount = ConvertedManaCostOf.instance(targetedBy(destroyTarget));
		this.addEffect(spellDealDamage(amount, targetCreature, "If Orim's Thunder was kicked, it deals damage equal to that permanent's converted mana cost to target creature."));
	}
}
