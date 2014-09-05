package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Spawning Pool")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = UrzasLegacy.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLACK})
public final class SpawningPool extends Card
{
	public static final class AnimatePool extends ActivatedAbility
	{
		public static final class RegenerateB extends org.rnd.jmagic.abilities.Regenerate
		{
			public RegenerateB(GameState state)
			{
				super(state, "(B)", "this creature");
			}
		}

		public AnimatePool(GameState state)
		{
			super(state, "(1)(B): Spawning Pool becomes a 1/1 black Skeleton creature with \"(B): Regenerate this creature\" until end of turn. It's still a land.");

			this.setManaCost(new ManaPool("1B"));

			Animator animator = new Animator(ABILITY_SOURCE_OF_THIS, 1, 1);
			animator.addColor(Color.BLACK);
			animator.addSubType(SubType.SKELETON);
			animator.addAbility(RegenerateB.class);
			this.addEffect(createFloatingEffect("Spawning Pool becomes a 1/1 black Skeleton creature with \"(B): Regenerate this creature\" until end of turn. It's still a land.", animator.getParts()));
		}
	}

	public SpawningPool(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));
		this.addAbility(new org.rnd.jmagic.abilities.TapForB(state));
		this.addAbility(new AnimatePool(state));
	}
}
