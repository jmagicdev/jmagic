package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Dread Statuary")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Worldwake.class, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class DreadStatuary extends Card
{
	public static final class Animate extends ActivatedAbility
	{
		public Animate(GameState state)
		{
			super(state, "(4): Dread Statuary becomes a 4/2 Golem artifact creature until end of turn. It's still a land.");

			this.setManaCost(new ManaPool("4"));

			Animator animator = new Animator(ABILITY_SOURCE_OF_THIS, 4, 2);
			animator.addType(Type.ARTIFACT);
			animator.addSubType(SubType.GOLEM);
			this.addEffect(createFloatingEffect("Dread Statuary becomes a 4/2 Golem artifact creature until end of turn. It's still a land.", animator.getParts()));
		}
	}

	public DreadStatuary(GameState state)
	{
		super(state);

		// (T): Add (1) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		// (4): Dread Statuary becomes a 4/2 Golem artifact creature until end
		// of turn. It's still a land.
		this.addAbility(new Animate(state));
	}
}
