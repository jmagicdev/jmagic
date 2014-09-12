package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Return to the Ranks")
@Types({Type.SORCERY})
@ManaCost("XWW")
@ColorIdentity({Color.WHITE})
public final class ReturntotheRanks extends Card
{
	public ReturntotheRanks(GameState state)
	{
		super(state);

		// Convoke (Your creatures can help cast this spell. Each creature you
		// tap while casting this spell pays for (1) or one mana of that
		// creature's color.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Convoke(state));

		// Return X target creature cards with converted mana cost 2 or less
		// from your graveyard to the battlefield.

		SetGenerator small = HasConvertedManaCost.instance(Between.instance(0, 2));
		SetGenerator dead = InZone.instance(GraveyardOf.instance(You.instance()));
		SetGenerator things = HasType.instance(Type.CREATURE);
		SetGenerator smallDeadThings = Intersect.instance(small, dead, things);
		Target targetThings = this.addTarget(smallDeadThings, "X target creature cards with converted mana cost 2 or less from your graveyard");
		targetThings.setSingleNumber(ValueOfX.instance(This.instance()));
		SetGenerator target = targetedBy(targetThings);
		this.addEffect(putOntoBattlefield(target, "Return X target creature cards with converted mana cost 2 or less from your graveyard to the battlefield."));
	}
}
