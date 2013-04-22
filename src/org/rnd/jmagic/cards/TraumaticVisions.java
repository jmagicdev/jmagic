package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Traumatic Visions")
@Types({Type.INSTANT})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = Expansion.CONFLUX, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class TraumaticVisions extends Card
{
	public TraumaticVisions(GameState state)
	{
		super(state);

		Target target = this.addTarget(Spells.instance(), "target spell");

		this.addEffect(counter(targetedBy(target), "Counter target spell."));

		this.addAbility(new org.rnd.jmagic.abilities.keywords.TypeCycling.BasicLandCycling(state, "(1)(U)"));
	}
}
