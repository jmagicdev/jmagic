package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ghastly Demise")
@Types({Type.INSTANT})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.ODYSSEY, r = Rarity.COMMON)})
@ColorIdentity({Color.BLACK})
public final class GhastlyDemise extends Card
{
	public GhastlyDemise(GameState state)
	{
		super(state);

		// Destroy target nonblack creature if its toughness is less than or
		// equal to the number of cards in your graveyard.
		SetGenerator nonblackCreatures = RelativeComplement.instance(CreaturePermanents.instance(), HasColor.instance(Color.BLACK));
		SetGenerator target = targetedBy(this.addTarget(nonblackCreatures, "target nonblack creature"));

		EventFactory effect = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "Destroy target nonblack creature if its toughness is less than or equal to the number of cards in your graveyard.");
		effect.parameters.put(EventType.Parameter.IF, Intersect.instance(ToughnessOf.instance(target), Between.instance(Empty.instance(), Count.instance(InZone.instance(GraveyardOf.instance(You.instance()))))));
		effect.parameters.put(EventType.Parameter.THEN, Identity.instance(destroy(target, "Destroy target nonblack creature")));
		this.addEffect(effect);
	}
}
