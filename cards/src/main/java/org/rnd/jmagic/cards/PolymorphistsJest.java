package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Polymorphist's Jest")
@Types({Type.INSTANT})
@ManaCost("1UU")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2015, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class PolymorphistsJest extends Card
{
	public PolymorphistsJest(GameState state)
	{
		super(state);


		// Until end of turn, each creature target player controls loses all abilities and becomes a blue Frog with base power and toughness 1/1.
		SetGenerator target = targetedBy(this.addTarget(Players.instance(), "target player"));
		SetGenerator creatures = Intersect.instance(HasType.instance(Type.CREATURE), ControlledBy.instance(target));

		Animator frog = new Animator(creatures, 1, 1);
		frog.addColor(Color.BLUE);
		frog.addSubType(SubType.FROG);
		frog.removeOldTypes();
		frog.losesAllAbilities();
		this.addEffect(createFloatingEffect("Until end of turn, each creature target player controls loses all abilities and becomes a blue Frog with base power and toughness 1/1.", frog.getParts()));
	}
}
