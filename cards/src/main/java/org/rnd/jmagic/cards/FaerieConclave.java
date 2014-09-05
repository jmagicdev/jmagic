package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Faerie Conclave")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = TenthEdition.class, r = Rarity.UNCOMMON), @Printings.Printed(ex = UrzasLegacy.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class FaerieConclave extends Card
{
	public static final class AnimateConclave extends ActivatedAbility
	{
		public AnimateConclave(GameState state)
		{
			super(state, "(1)(U): Faerie Conclave becomes a 2/1 blue Faerie creature with flying until end of turn. It's still a land.");

			this.setManaCost(new ManaPool("1U"));

			Animator animator = new Animator(ABILITY_SOURCE_OF_THIS, 2, 1);
			animator.addColor(Color.BLUE);
			animator.addSubType(SubType.FAERIE);
			animator.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(createFloatingEffect("Faerie Conclave becomes a 2/1 blue Faerie creature with flying until end of turn. It's still a land.", animator.getParts()));
		}
	}

	public FaerieConclave(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.EntersTheBattlefieldTapped(state, this.getName()));
		this.addAbility(new org.rnd.jmagic.abilities.TapForU(state));
		this.addAbility(new AnimateConclave(state));
	}
}
