package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mold Shambler")
@Types({Type.CREATURE})
@SubTypes({SubType.FUNGUS, SubType.BEAST})
@ManaCost("3G")
@ColorIdentity({Color.GREEN})
public final class MoldShambler extends Card
{
	public static final class Moldy extends EventTriggeredAbility
	{
		private final CostCollection kickerCost;

		public Moldy(GameState state, CostCollection kickerCost)
		{
			super(state, "When Mold Shambler enters the battlefield, if it was kicked, destroy target noncreature permanent.");

			this.kickerCost = kickerCost;

			this.addPattern(whenThisEntersTheBattlefield());

			this.interveningIf = ThisPermanentWasKicked.instance(kickerCost);

			Target target = this.addTarget(RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.CREATURE)), "target noncreature permanent");

			this.addEffect(destroy(targetedBy(target), "Destroy target noncreature permanent"));
		}

		@Override
		public Moldy create(Game game)
		{
			return new Moldy(game.physicalState, this.kickerCost);
		}
	}

	public MoldShambler(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);
		org.rnd.jmagic.abilities.keywords.Kicker ability = new org.rnd.jmagic.abilities.keywords.Kicker(state, "1G");
		this.addAbility(ability);

		CostCollection kickerCost = ability.costCollections[0];

		this.addAbility(new Moldy(state, kickerCost));
	}
}
