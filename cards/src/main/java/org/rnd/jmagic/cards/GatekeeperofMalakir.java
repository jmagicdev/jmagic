package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Gatekeeper of Malakir")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.VAMPIRE})
@ManaCost("BB")
@ColorIdentity({Color.BLACK})
public final class GatekeeperofMalakir extends Card
{
	public static final class VampireEdict extends EventTriggeredAbility
	{
		private final CostCollection kickerCost;

		public VampireEdict(GameState state, CostCollection kickerCost)
		{
			super(state, "When Gatekeeper of Malakir enters the battlefield, if it was kicked, target player sacrifices a creature.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.kickerCost = kickerCost;

			this.interveningIf = ThisPermanentWasKicked.instance(kickerCost);

			Target target = this.addTarget(Players.instance(), "target player");

			this.addEffect(sacrifice(targetedBy(target), 1, CreaturePermanents.instance(), "Target player sacrifices a creature."));
		}

		@Override
		public VampireEdict create(Game game)
		{
			return new VampireEdict(game.physicalState, this.kickerCost);
		}
	}

	public GatekeeperofMalakir(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);
		org.rnd.jmagic.abilities.keywords.Kicker ability = new org.rnd.jmagic.abilities.keywords.Kicker(state, "(B)");
		this.addAbility(ability);

		CostCollection kickerCost = ability.costCollections[0];

		this.addAbility(new VampireEdict(state, kickerCost));
	}
}
