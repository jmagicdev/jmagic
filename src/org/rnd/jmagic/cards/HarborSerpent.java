package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Harbor Serpent")
@Types({Type.CREATURE})
@SubTypes({SubType.SERPENT})
@ManaCost("4UU")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2012, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.MAGIC_2011, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class HarborSerpent extends Card
{
	public static final class HarborSerpentAbility1 extends StaticAbility
	{
		public HarborSerpentAbility1(GameState state)
		{
			super(state, "Harbor Serpent can't attack unless there are five or more Islands on the battlefield.");

			SetGenerator thisIsAttacking = Intersect.instance(Attacking.instance(), This.instance());

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.ATTACKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(thisIsAttacking));
			this.addEffectPart(part);

			SetGenerator islands = Intersect.instance(HasSubType.instance(SubType.ISLAND), Permanents.instance());
			SetGenerator fewerThanFiveIslands = Intersect.instance(Between.instance(null, 4), Count.instance(islands));
			this.canApply = Both.instance(this.canApply, fewerThanFiveIslands);
		}
	}

	public HarborSerpent(GameState state)
	{
		super(state);

		this.setPower(5);
		this.setToughness(5);

		// Islandwalk (This creature is unblockable as long as defending player
		// controls an Island.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Landwalk.Islandwalk(state));

		// Harbor Serpent can't attack unless there are five or more Islands on
		// the battlefield.
		this.addAbility(new HarborSerpentAbility1(state));
	}
}
