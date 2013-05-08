package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Two-Headed Dragon")
@Types({Type.CREATURE})
@SubTypes({SubType.DRAGON})
@ManaCost("4RR")
@Printings({@Printings.Printed(ex = Expansion.DRAGONS, r = Rarity.RARE), @Printings.Printed(ex = Expansion.EIGHTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.MERCADIAN_MASQUES, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class TwoHeadedDragon extends Card
{
	public static final class TwoHeadedDragonAbility1 extends ActivatedAbility
	{
		public TwoHeadedDragonAbility1(GameState state)
		{
			super(state, "(1)(R): Two-Headed Dragon gets +2/+0 until end of turn.");
			this.setManaCost(new ManaPool("(1)(R)"));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +2, +0, "Two-Headed Dragon gets +2/+0 until end of turn."));
		}
	}

	public static final class TwoHeadedDragonAbility2 extends StaticAbility
	{
		public TwoHeadedDragonAbility2(GameState state)
		{
			super(state, "Two-Headed Dragon can't be blocked except by two or more creatures.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(Intersect.instance(Count.instance(Blocking.instance(This.instance())), numberGenerator(1))));
			this.addEffectPart(part);
		}
	}

	public static final class TwoHeadedDragonAbility3 extends StaticAbility
	{
		public TwoHeadedDragonAbility3(GameState state)
		{
			super(state, "Two-Headed Dragon can block an additional creature.");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CAN_BLOCK_AN_ADDITIONAL_CREATURE);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, This.instance());
			this.addEffectPart(part);
		}
	}

	public TwoHeadedDragon(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (1)(R): Two-Headed Dragon gets +2/+0 until end of turn.
		this.addAbility(new TwoHeadedDragonAbility1(state));

		// Two-Headed Dragon can't be blocked except by two or more creatures.
		this.addAbility(new TwoHeadedDragonAbility2(state));

		// Two-Headed Dragon can block an additional creature.
		this.addAbility(new TwoHeadedDragonAbility3(state));
	}
}
