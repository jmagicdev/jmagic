package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Sleeper Agent")
@Types({Type.CREATURE})
@SubTypes({SubType.MINION})
@ManaCost("B")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.URZAS_SAGA, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK})
public final class SleeperAgent extends Card
{
	public static final class Infiltrate extends EventTriggeredAbility
	{
		public Infiltrate(GameState state)
		{
			super(state, "When Sleeper Agent enters the battlefield, target opponent gains control of it.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator controller = You.instance();

			Target target = this.addTarget(OpponentsOf.instance(controller), "target opponent");

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.CHANGE_CONTROL);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			part.parameters.put(ContinuousEffectType.Parameter.PLAYER, targetedBy(target));

			this.addEffect(createFloatingEffect(Empty.instance(), "Target opponent gains control of it", part));
		}
	}

	public static final class Betray extends EventTriggeredAbility
	{
		public Betray(GameState state)
		{
			super(state, "At the beginning of your upkeep, Sleeper Agent deals 2 damage to you.");

			this.addPattern(atTheBeginningOfYourUpkeep());
			this.addEffect(permanentDealDamage(2, You.instance(), "Sleeper Agent deals 2 damage to you."));
		}
	}

	public SleeperAgent(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new Infiltrate(state));
		this.addAbility(new Betray(state));
	}
}
