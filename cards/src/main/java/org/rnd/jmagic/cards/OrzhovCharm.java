package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Orzhov Charm")
@Types({Type.INSTANT})
@ManaCost("WB")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class OrzhovCharm extends Card
{
	public OrzhovCharm(GameState state)
	{
		super(state);

		// Choose one \u2014 Return target creature you control and all Auras
		// you control attached to it to their owner's hand; or destroy target
		// creature and you lose life equal to its toughness; or return target
		// creature card with converted mana cost 1 or less from your graveyard
		// to the battlefield.
		SetGenerator target1 = targetedBy(this.addTarget(1, CREATURES_YOU_CONTROL, "target creature you control"));
		SetGenerator aurasYouControlAttached = Intersect.instance(HasSubType.instance(SubType.AURA), AttachedTo.instance(target1), ControlledBy.instance(You.instance()));
		SetGenerator toBounce = Union.instance(target1, aurasYouControlAttached);
		this.addEffect(1, bounce(toBounce, "Return target creature you control and all Auras you control attached to it to their owner's hand."));

		SetGenerator target2 = targetedBy(this.addTarget(2, CreaturePermanents.instance(), "target creature"));
		this.addEffect(2, destroy(target2, "Destroy target creature"));
		this.addEffect(2, loseLife(You.instance(), ToughnessOf.instance(target2), "and you lose life equal to its toughness."));

		SetGenerator convertedManaCostOneOrLess = HasConvertedManaCost.instance(Between.instance(null, 1));
		SetGenerator inYourYard = InZone.instance(GraveyardOf.instance(You.instance()));
		SetGenerator restriction = Intersect.instance(HasType.instance(Type.CREATURE), Cards.instance(), convertedManaCostOneOrLess, inYourYard);
		SetGenerator target3 = targetedBy(this.addTarget(3, restriction, "target creature card with converted mana cost 1 or less in your graveyard"));
		this.addEffect(3, putOntoBattlefield(target3, "Return target creature card with converted mana cost 1 or less from your graveyard to the battlefield."));
	}
}
