package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Mutavault")
@Types({Type.LAND})
@Printings({@Printings.Printed(ex = Expansion.MORNINGTIDE, r = Rarity.RARE)})
@ColorIdentity({})
public final class Mutavault extends Card
{
	public static final class AnimateMutavault extends ActivatedAbility
	{

		public AnimateMutavault(GameState state)
		{
			super(state, "(1): Mutavault becomes a 2/2 creature with all creature types until end of turn. It's still a land.");

			this.setManaCost(new ManaPool("1"));

			Animator animator = new Animator(ABILITY_SOURCE_OF_THIS, 2, 2);
			animator.addSubTypes(SubType.getAllCreatureTypes());
			this.addEffect(createFloatingEffect("Mutavault becomes a 2/2 creature with all creature types until end of turn. It's still a land.", animator.getParts()));
		}
	}

	public Mutavault(GameState state)
	{
		super(state);

		this.addAbility(new org.rnd.jmagic.abilities.TapFor1(state));

		this.addAbility(new AnimateMutavault(state));
	}

}
