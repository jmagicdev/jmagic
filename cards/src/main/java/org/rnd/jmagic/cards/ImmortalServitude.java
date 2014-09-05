package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Immortal Servitude")
@Types({Type.SORCERY})
@ManaCost("X(W/B)(W/B)(W/B)")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class ImmortalServitude extends Card
{
	public ImmortalServitude(GameState state)
	{
		super(state);

		// Return each creature card with converted mana cost X from your
		// graveyard to the battlefield.
		SetGenerator creatures = HasType.instance(Type.CREATURE);
		SetGenerator cmcX = HasConvertedManaCost.instance(ValueOfX.instance(This.instance()));
		SetGenerator inYourYard = InZone.instance(GraveyardOf.instance(You.instance()));
		SetGenerator toAffect = Intersect.instance(creatures, cmcX, inYourYard);
		this.addEffect(putOntoBattlefield(toAffect, "Return each creature card with converted mana cost X from your graveyard to the battlefield."));
	}
}
