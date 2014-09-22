package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Celestial Colonnade")
@Types({Type.LAND})
@ColorIdentity({Color.WHITE, Color.BLUE})
public final class CelestialColonnade extends Card
{
	public static final class AnimateColonnade extends ActivatedAbility
	{
		public AnimateColonnade(GameState state)
		{
			super(state, "(3)(W)(U): Until end of turn, Celestial Colonnade becomes a 4/4 white and blue Elemental creature with flying and vigilance. It's still a land.");
			this.setManaCost(new ManaPool("3WU"));

			Animator animator = new Animator(ABILITY_SOURCE_OF_THIS, 4, 4);
			animator.addColor(Color.WHITE);
			animator.addColor(Color.BLUE);
			animator.addSubType(SubType.ELEMENTAL);
			animator.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			animator.addAbility(org.rnd.jmagic.abilities.keywords.Vigilance.class);
			this.addEffect(createFloatingEffect("Until end of turn, Celestial Colonnade becomes a 4/4 white and blue Elemental creature with flying and vigilance. It's still a land.", animator.getParts()));
		}
	}

	public CelestialColonnade(GameState state)
	{
		super(state);

		// Celestial Colonnade enters the battlefield tapped.
		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));

		// (T): Add (W) or (U) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(WU)"));

		// (3)(W)(U): Until end of turn, Celestial Colonnade becomes a 4/4 white
		// and blue Elemental creature with flying and vigilance. It's still a
		// land.
		this.addAbility(new AnimateColonnade(state));
	}
}
