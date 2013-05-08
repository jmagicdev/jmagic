package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Flusterstorm")
@Types({Type.INSTANT})
@ManaCost("U")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class Flusterstorm extends Card
{
	public Flusterstorm(GameState state)
	{
		super(state);

		// Counter target instant or sorcery spell unless its controller pays
		// (1).
		Target target = this.addTarget(Intersect.instance(Spells.instance(), HasType.instance(Type.INSTANT, Type.SORCERY)), "target instant or sorcery spell");
		this.addEffect(counterTargetUnlessControllerPays("(1)", target));

		// Storm (When you cast this spell, copy it for each spell cast before
		// it this turn. You may choose new targets for the copies.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Storm(state));
	}
}
