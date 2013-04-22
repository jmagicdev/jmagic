package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Chemister's Trick")
@Types({Type.INSTANT})
@ManaCost("UR")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE, Color.RED})
public final class ChemistersTrick extends Card
{
	public ChemistersTrick(GameState state)
	{
		super(state);

		// Target creature you don't control gets -2/-0 until end of turn and
		// attacks this turn if able.
		SetGenerator target = targetedBy(this.addTarget(RelativeComplement.instance(CreaturePermanents.instance(), ControlledBy.instance(You.instance())), "target creature you don't control"));

		ContinuousEffect.Part attack = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_REQUIREMENT);
		attack.parameters.put(ContinuousEffectType.Parameter.ATTACKING, target);

		this.addEffect(createFloatingEffect("Target creature you don't control gets -2/-0 until end of turn and attacks this turn if able.", modifyPowerAndToughness(target, -2, -0), attack));

		// Overload (3)(U)(R) (You may cast this spell for its overload cost. If
		// you do, change its text by replacing all instances of "target" with
		// "each.")
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Overload(state, "(3)(U)(R)"));
	}
}
