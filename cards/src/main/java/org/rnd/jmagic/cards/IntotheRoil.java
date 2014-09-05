package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Into the Roil")
@Types({Type.INSTANT})
@ManaCost("1U")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class IntotheRoil extends Card
{
	public IntotheRoil(GameState state)
	{
		super(state);

		org.rnd.jmagic.abilities.keywords.Kicker ability = new org.rnd.jmagic.abilities.keywords.Kicker(state, "(1)(U)");
		this.addAbility(ability);

		CostCollection kicker = ability.costCollections[0];

		Target target = this.addTarget(RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.LAND)), "target nonland permanent");
		this.addEffect(bounce(targetedBy(target), "Return target nonland permanent to its owner's hand."));

		EventFactory factory = drawCards(You.instance(), 1, "Draw a card");

		EventFactory ifThen = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If Into the Roil was kicked, draw a card.");
		ifThen.parameters.put(EventType.Parameter.IF, ThisSpellWasKicked.instance(kicker));
		ifThen.parameters.put(EventType.Parameter.THEN, Identity.instance(factory));
		this.addEffect(ifThen);
	}
}
