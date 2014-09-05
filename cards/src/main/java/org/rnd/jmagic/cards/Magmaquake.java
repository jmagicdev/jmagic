package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Magmaquake")
@Types({Type.INSTANT})
@ManaCost("XRR")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class Magmaquake extends Card
{
	public Magmaquake(GameState state)
	{
		super(state);

		// Magmaquake deals X damage to each creature without flying and each
		// planeswalker.
		this.addEffect(spellDealDamage(ValueOfX.instance(This.instance()), Union.instance(RelativeComplement.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class)), Intersect.instance(Permanents.instance(), HasType.instance(Type.PLANESWALKER))), "Magmaquake deals X damage to each creature without flying and each planeswalker."));
	}
}
