package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Sage's Dousing")
@Types({Type.INSTANT, Type.TRIBAL})
@SubTypes({SubType.WIZARD})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = Morningtide.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class SagesDousing extends Card
{
	public SagesDousing(GameState state)
	{
		super(state);

		// Counter target spell unless its controller pays (3). If you control a
		// Wizard, draw a card.
		Target target = this.addTarget(Spells.instance(), "target spell");
		this.addEffect(counterTargetUnlessControllerPays("(3)", target));

		EventFactory draw = drawCards(You.instance(), 1, "Draw a card");

		EventFactory effect = new EventFactory(EventType.IF_CONDITION_THEN_ELSE, "If you control a Wizard, draw a card.");
		effect.parameters.put(EventType.Parameter.IF, Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.WIZARD)));
		effect.parameters.put(EventType.Parameter.THEN, Identity.instance(draw));
		this.addEffect(effect);
	}
}
