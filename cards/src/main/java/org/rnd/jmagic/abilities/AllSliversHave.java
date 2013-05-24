package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class AllSliversHave extends StaticAbility
{
	public Class<?> ability;

	public AllSliversHave(GameState state, Class<?> ability, String text)
	{
		super(state, text);

		this.ability = ability;

		SetGenerator slivers = HasSubType.instance(SubType.SLIVER);
		SetGenerator sliverPermanents = Intersect.instance(Permanents.instance(), slivers);

		this.addEffectPart(addAbilityToObject(sliverPermanents, ability));
	}

	@Override
	public AllSliversHave create(Game game)
	{
		return new AllSliversHave(game.physicalState, this.ability, this.getName());
	}
}
