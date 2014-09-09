package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Stirring Wildwood")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class StirringWildwood extends Card
{
	public static final class Animate extends ActivatedAbility
	{
		public Animate(GameState state)
		{
			super(state, "(1)(G)(W): Until end of turn, Stirring Wildwood becomes a 3/4 green and white Elemental creature with reach. It's still a land.");

			this.setManaCost(new ManaPool("1GW"));

			Animator animator = new Animator(ABILITY_SOURCE_OF_THIS, 3, 4);
			animator.addColor(Color.GREEN);
			animator.addColor(Color.WHITE);
			animator.addSubType(SubType.ELEMENTAL);
			animator.addAbility(org.rnd.jmagic.abilities.keywords.Reach.class);
			this.addEffect(createFloatingEffect("Until end of turn, Stirring Wildwood becomes a 3/4 green and white Elemental creature with reach. It's still a land.", animator.getParts()));
		}
	}

	public StirringWildwood(GameState state)
	{
		super(state);

		// Stirring Wildwood enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// (T): Add (G) or (W) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(GW)"));

		// (1)(G)(W): Until end of turn, Stirring Wildwood becomes a 3/4 green
		// and white Elemental creature with reach. It's still a land.
		this.addAbility(new Animate(state));
	}
}
