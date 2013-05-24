package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Azorius Keyrune")
@Types({Type.ARTIFACT})
@ManaCost("3")
@Printings({@Printings.Printed(ex = Expansion.RETURN_TO_RAVNICA, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE})
public final class AzoriusKeyrune extends Card
{
	public static final class AzoriusKeyruneAbility1 extends ActivatedAbility
	{
		public AzoriusKeyruneAbility1(GameState state)
		{
			super(state, "(W)(U): Azorius Keyrune becomes a 2/2 white and blue Bird artifact creature with flying until end of turn.");
			this.setManaCost(new ManaPool("(W)(U)"));

			Animator animator = new Animator(ABILITY_SOURCE_OF_THIS, 2, 2);
			animator.addColor(Color.WHITE);
			animator.addColor(Color.BLUE);
			animator.addSubType(SubType.BIRD);
			animator.addType(Type.ARTIFACT);
			animator.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(createFloatingEffect("Azorius Keyrune becomes a 2/2 white and blue Bird artifact creature with flying until end of turn.", animator.getParts()));
		}
	}

	public AzoriusKeyrune(GameState state)
	{
		super(state);

		// (T): Add (W) or (U) to your mana pool.
		this.addAbility(new org.rnd.jmagic.abilities.TapForMana.Final(state, "(WU)"));

		// (W)(U): Azorius Keyrune becomes a 2/2 white and blue Bird artifact
		// creature with flying until end of turn.
		this.addAbility(new AzoriusKeyruneAbility1(state));
	}
}
