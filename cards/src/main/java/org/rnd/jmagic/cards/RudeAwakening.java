package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Rude Awakening")
@Types({Type.SORCERY})
@ManaCost("4G")
@Printings({@Printings.Printed(ex = Expansion.FIFTH_DAWN, r = Rarity.RARE)})
@ColorIdentity({Color.GREEN})
public final class RudeAwakening extends Card
{
	public RudeAwakening(GameState state)
	{
		super(state);

		SetGenerator landsYouControl = Intersect.instance(LandPermanents.instance(), ControlledBy.instance(You.instance()));

		this.addEffect(1, untap(landsYouControl, "Untap all lands you control."));

		ContinuousEffect.Part typePart = new ContinuousEffect.Part(ContinuousEffectType.ADD_TYPES);
		typePart.parameters.put(ContinuousEffectType.Parameter.OBJECT, landsYouControl);
		typePart.parameters.put(ContinuousEffectType.Parameter.TYPE, Identity.instance(Type.CREATURE));

		this.addEffect(2, createFloatingEffect("Until end of turn, lands you control become 2/2 creatures that are still lands.", typePart, setPowerAndToughness(landsYouControl, 2, 2)));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Entwine(state, "(2)(G)"));
	}
}
