package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Battleflight Eagle")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD})
@ManaCost("4W")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class BattleflightEagle extends Card
{
	public static final class BattleflightEagleAbility1 extends EventTriggeredAbility
	{
		public BattleflightEagleAbility1(GameState state)
		{
			super(state, "When Battleflight Eagle enters the battlefield, target creature gets +2/+2 and gains flying until end of turn.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			ContinuousEffect.Part pt = modifyPowerAndToughness(target, +2, +2);
			ContinuousEffect.Part flying = addAbilityToObject(target, org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(createFloatingEffect("Target creature gets +2/+2 and gains flying until end of turn.", pt, flying));

		}
	}

	public BattleflightEagle(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Battleflight Eagle enters the battlefield, target creature gets
		// +2/+2 and gains flying until end of turn.
		this.addAbility(new BattleflightEagleAbility1(state));
	}
}
