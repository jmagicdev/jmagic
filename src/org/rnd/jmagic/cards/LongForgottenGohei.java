package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Long-Forgotten Gohei")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.CHAMPIONS_OF_KAMIGAWA, r = Rarity.RARE)})
@ColorIdentity({})
public final class LongForgottenGohei extends Card
{
	public LongForgottenGohei(GameState state)
	{
		super(state);

		// Arcane spells you cast cost (1) less to cast.
		this.addAbility(new org.rnd.jmagic.abilities.CostsYouLessToCast(state, HasSubType.instance(SubType.ARCANE), "(1)", "Arcane spells you cast cost (1) less to cast."));

		// Spirit creatures you control get +1/+1.
		SetGenerator yourSpirits = Intersect.instance(HasSubType.instance(SubType.SPIRIT), CREATURES_YOU_CONTROL);
		this.addAbility(new org.rnd.jmagic.abilities.StaticPTChange(state, yourSpirits, "Spirit creatures you control", +1, +1, true));
	}
}
