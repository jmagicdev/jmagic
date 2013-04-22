package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Heartless Summoning")
@Types({Type.ENCHANTMENT})
@ManaCost("1B")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class HeartlessSummoning extends Card
{
	public static final class HeartlessSummoningAbility1 extends StaticAbility
	{
		public HeartlessSummoningAbility1(GameState state)
		{
			super(state, "Creatures you control get -1/-1.");

			this.addEffectPart(modifyPowerAndToughness(CREATURES_YOU_CONTROL, -1, -1));
		}
	}

	public HeartlessSummoning(GameState state)
	{
		super(state);

		// Creature spells you cast cost (2) less to cast.
		// Treefolk spells and Shaman spells you cast cost (1) less to cast.
		this.addAbility(new org.rnd.jmagic.abilities.CostsYouLessToCast(state, HasType.instance(Type.CREATURE), "(2)", "Creature spells you cast cost (2) less to cast."));

		// Creatures you control get -1/-1.
		this.addAbility(new HeartlessSummoningAbility1(state));
	}
}
