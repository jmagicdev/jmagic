package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Countersquall")
@Types({Type.INSTANT})
@ManaCost("UB")
@Printings({@Printings.Printed(ex = Expansion.CONFLUX, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.BLACK})
public final class Countersquall extends Card
{
	public Countersquall(GameState state)
	{
		super(state);

		// Counter target noncreature spell. Its controller loses 2 life.
		SetGenerator noncreatureSpells = RelativeComplement.instance(Spells.instance(), HasType.instance(Type.CREATURE));
		Target target = this.addTarget(noncreatureSpells, "target noncreature spell");

		this.addEffect(counter(targetedBy(target), "Counter target noncreature spell"));
		this.addEffect(loseLife(ControllerOf.instance(targetedBy(target)), 2, "Its controller loses 2 life."));
	}
}
