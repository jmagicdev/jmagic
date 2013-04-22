package org.rnd.jmagic.abilities;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

public final class AllSliverCreaturesHave extends StaticAbility
{
	private Class<?> ability;

	public AllSliverCreaturesHave(GameState state, Class<?> ability, String text)
	{
		super(state, text);

		this.ability = ability;

		SetGenerator slivers = HasSubType.instance(SubType.SLIVER);
		SetGenerator sliverCreatures = Intersect.instance(CreaturePermanents.instance(), slivers);

		this.addEffectPart(addAbilityToObject(sliverCreatures, ability));
	}

	@Override
	public AllSliverCreaturesHave create(Game game)
	{
		return new AllSliverCreaturesHave(game.physicalState, this.ability, this.getName());
	}
}
