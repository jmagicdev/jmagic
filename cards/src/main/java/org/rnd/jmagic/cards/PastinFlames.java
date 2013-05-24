package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Past in Flames")
@Types({Type.SORCERY})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.MYTHIC)})
@ColorIdentity({Color.RED})
public final class PastinFlames extends Card
{
	public PastinFlames(GameState state)
	{
		super(state);

		// Each instant and sorcery card in your graveyard gains flashback until
		// end of turn. The flashback cost is equal to its mana cost.
		ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.GRANT_COSTED_KEYWORD);
		part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(org.rnd.jmagic.abilities.keywords.Flashback.class));
		part.parameters.put(ContinuousEffectType.Parameter.OBJECT, Intersect.instance(HasType.instance(Type.INSTANT, Type.SORCERY), InZone.instance(GraveyardOf.instance(You.instance()))));
		this.addEffect(createFloatingEffect("Each instant or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost.", part));

		// Flashback (4)(R) (You may cast this card from your graveyard for its
		// flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(4)(R)"));
	}
}
