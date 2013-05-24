package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Firespout")
@Types({Type.SORCERY})
@ManaCost("2(R/G)")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.SHADOWMOOR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN, Color.RED})
public final class Firespout extends Card
{
	public Firespout(GameState state)
	{
		super(state);

		// Firespout deals 3 damage to each creature without flying if (R) was
		// spent to cast Firespout and 3 damage to each creature with flying if
		// (G) was spent to cast it.
		SetGenerator colorsSpent = ColorsOf.instance(ManaSpentOn.instance(This.instance()));

		SetGenerator redSpent = Intersect.instance(colorsSpent, Identity.instance(Color.RED));
		SetGenerator nonflyers = RelativeComplement.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class));
		SetGenerator nonflyingTakers = IfThenElse.instance(redSpent, nonflyers, Empty.instance());

		SetGenerator greenSpent = Intersect.instance(colorsSpent, Identity.instance(Color.GREEN));
		SetGenerator flyers = Intersect.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class));
		SetGenerator flyingTakers = IfThenElse.instance(greenSpent, flyers, Empty.instance());

		this.addEffect(spellDealDamage(3, Union.instance(nonflyingTakers, flyingTakers), "Firespout deals 3 damage to each creature without flying if (R) was spent to cast Firespout and 3 damage to each creature with flying if (G) was spent to cast it."));
	}
}
