package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Goblin Legionnaire")
@Types({Type.CREATURE})
@SubTypes({SubType.SOLDIER, SubType.GOBLIN})
@ManaCost("RW")
@ColorIdentity({Color.WHITE, Color.RED})
public final class GoblinLegionnaire extends Card
{
	public static final class GoblinLegionnaireAbility0 extends ActivatedAbility
	{
		public GoblinLegionnaireAbility0(GameState state)
		{
			super(state, "(R), Sacrifice Goblin Legionnaire: Goblin Legionnaire deals 2 damage to target creature or player.");
			this.setManaCost(new ManaPool("(R)"));
			this.addCost(sacrificeThis("Goblin Legionnaire"));
			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");
			this.addEffect(permanentDealDamage(2, targetedBy(target), "Goblin Legionnaire deals 2 damage to target creature or player."));
		}
	}

	public static final class GoblinLegionnaireAbility1 extends ActivatedAbility
	{
		public GoblinLegionnaireAbility1(GameState state)
		{
			super(state, "(W), Sacrifice Goblin Legionnaire: Prevent the next 2 damage that would be dealt to target creature or player this turn.");
			this.setManaCost(new ManaPool("(W)"));
			this.addCost(sacrificeThis("Goblin Legionnaire"));

			Target target = this.addTarget(CREATURES_AND_PLAYERS, "target creature or player");

			EventFactory effect = new EventFactory(EventType.CREATE_FLOATING_CONTINUOUS_EFFECT, "Prevent the next 2 damage that would be dealt to target creature or player this turn.");
			effect.parameters.put(EventType.Parameter.CAUSE, This.instance());
			effect.parameters.put(EventType.Parameter.PREVENT, Identity.instance(2, targetedBy(target)));
			this.addEffect(effect);
		}
	}

	public GoblinLegionnaire(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// (R), Sacrifice Goblin Legionnaire: Goblin Legionnaire deals 2 damage
		// to target creature or player.
		this.addAbility(new GoblinLegionnaireAbility0(state));

		// (W), Sacrifice Goblin Legionnaire: Prevent the next 2 damage that
		// would be dealt to target creature or player this turn.
		this.addAbility(new GoblinLegionnaireAbility1(state));
	}
}
