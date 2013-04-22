package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Windstorm")
@Types({Type.INSTANT})
@ManaCost("XG")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2010, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class Windstorm extends Card
{
	public Windstorm(GameState state)
	{
		super(state);

		SetGenerator amount = Intersect.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class));
		this.addEffect(spellDealDamage(ValueOfX.instance(This.instance()), amount, "Windstorm deals X damage to each creature with flying."));
	}
}
