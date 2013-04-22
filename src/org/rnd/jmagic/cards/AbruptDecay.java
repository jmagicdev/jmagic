package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Abrupt Decay")
@Types({Type.INSTANT})
@ManaCost("BG")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN, Color.BLACK})
public final class AbruptDecay extends Card
{
	public AbruptDecay(GameState state)
	{
		super(state);

		// Abrupt Decay can't be countered by spells or abilities.
		this.addAbility(new org.rnd.jmagic.abilities.CantBeCountered(state, "Abrupt Decay", true));

		// Destroy target nonland permanent with converted mana cost 3 or less.
		SetGenerator nonlandPermanent = RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.LAND));
		SetGenerator convManaCost = HasConvertedManaCost.instance(Between.instance(null, 3));
		SetGenerator restriction = Intersect.instance(nonlandPermanent, convManaCost);
		SetGenerator target = targetedBy(this.addTarget(restriction, "target nonland permanent with converted mana cost 3 or less"));
		this.addEffect(destroy(target, "Destroy target nonland permanent with converted mana cost 3 or less."));
	}
}
