package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Lightkeeper of Emeria")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = Expansion.COMMANDER, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.WORLDWAKE, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class LightkeeperofEmeria extends Card
{
	public static final class LightkeeperofEmeriaAbility2 extends EventTriggeredAbility
	{
		private final CostCollection kickerCost;

		public LightkeeperofEmeriaAbility2(GameState state, CostCollection kickerCost)
		{
			super(state, "When Lightkeeper of Emeria enters the battlefield, you gain 2 life for each time it was kicked.");
			this.kickerCost = kickerCost;

			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator amount = Multiply.instance(numberGenerator(2), ThisPermanentWasKicked.instance(kickerCost));
			this.addEffect(gainLife(You.instance(), amount, "You gain 2 life for each time it was kicked."));
		}

		@Override
		public LightkeeperofEmeriaAbility2 create(Game game)
		{
			return new LightkeeperofEmeriaAbility2(game.physicalState, this.kickerCost);
		}
	}

	public LightkeeperofEmeria(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(4);

		// Multikicker (W) (You may pay an additional (W) any number of times as
		// you cast this spell.)
		org.rnd.jmagic.abilities.keywords.Kicker kicker = new org.rnd.jmagic.abilities.keywords.Kicker(state, true, "(W)");
		this.addAbility(kicker);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Lightkeeper of Emeria enters the battlefield, you gain 2 life
		// for each time it was kicked.
		this.addAbility(new LightkeeperofEmeriaAbility2(state, kicker.costCollections[0]));
	}
}
