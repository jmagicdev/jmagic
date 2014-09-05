package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Forbidding Watchtower")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = UrzasLegacy.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class ForbiddingWatchtower extends Card
{
	public static final class AnimateWatchtower extends ActivatedAbility
	{
		public AnimateWatchtower(GameState state)
		{
			super(state, "(1)(W): Forbidding Watchtower becomes a 1/5 white Soldier creature until end of turn. It's still a land.");

			this.setManaCost(new ManaPool("1W"));

			Animator animator = new Animator(ABILITY_SOURCE_OF_THIS, 1, 5);
			animator.addColor(Color.WHITE);
			animator.addSubType(SubType.SOLDIER);
			this.addEffect(createFloatingEffect("Forbidding Watchtower becomes a 1/5 white Soldier creature until end of turn. It's still a land.", animator.getParts()));
		}
	}

	public ForbiddingWatchtower(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));
		this.addAbility(new org.rnd.jmagic.abilities.TapForW(state));
		this.addAbility(new AnimateWatchtower(state));
	}
}
