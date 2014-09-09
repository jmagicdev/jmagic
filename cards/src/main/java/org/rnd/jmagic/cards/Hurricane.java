package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hurricane")
@Types({Type.SORCERY})
@ManaCost("XG")
@ColorIdentity({Color.GREEN})
public final class Hurricane extends Card
{
	public Hurricane(GameState state)
	{
		super(state);

		SetGenerator takers = Union.instance(Intersect.instance(CreaturePermanents.instance(), HasKeywordAbility.instance(org.rnd.jmagic.abilities.keywords.Flying.class)), Players.instance());
		this.addEffect(spellDealDamage(ValueOfX.instance(This.instance()), takers, "Hurricane deals X damage to each creature with flying and each player."));
	}
}
