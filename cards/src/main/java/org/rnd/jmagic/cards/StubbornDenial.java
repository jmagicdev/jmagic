package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Stubborn Denial")
@Types({Type.INSTANT})
@ManaCost("U")
@ColorIdentity({Color.BLUE})
public final class StubbornDenial extends Card
{
	public StubbornDenial(GameState state)
	{
		super(state);

		// Counter target noncreature spell unless its controller pays (1).
		SetGenerator noncreatureSpells = RelativeComplement.instance(Spells.instance(), HasType.instance(Type.CREATURE));
		Target target = this.addTarget(noncreatureSpells, "target noncreature spell");
		EventFactory softCounter = counterTargetUnlessControllerPays("(1)", target);

		// Ferocious \u2014 If you control a creature with power 4 or greater,
		// counter that spell instead.
		EventFactory hardCounter = counter(targetedBy(target), "Counter target noncreature spell.");

		this.addEffect(ifThenElse(Ferocious.instance(), hardCounter, softCounter, "Counter target noncreature spell unless its controller pays (1).\n\nFerocious \u2014 If you control a creature with power 4 or greater, counter that spell instead."));
	}
}
