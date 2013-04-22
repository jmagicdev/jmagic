package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Haze Frog")
@Types({Type.CREATURE})
@SubTypes({SubType.FROG})
@ManaCost("3GG")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.COMMON)})
@ColorIdentity({Color.GREEN})
public final class HazeFrog extends Card
{
	public static final class HazeFrogAbility1 extends EventTriggeredAbility
	{
		public HazeFrogAbility1(GameState state)
		{
			super(state, "When Haze Frog enters the battlefield, prevent all combat damage that other creatures would deal this turn.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator otherCreatures = RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS);
			DamageReplacementEffect prevent = new org.rnd.jmagic.abilities.PreventCombatDamage(state.game, otherCreatures, "Prevent all combat damage that other creatures would deal this turn");
			this.addEffect(createFloatingReplacement(prevent, "Prevent all combat damage that other creatures would deal this turn."));
		}
	}

	public HazeFrog(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Flash (You may cast this spell any time you could cast an instant.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// When Haze Frog enters the battlefield, prevent all combat damage that
		// other creatures would deal this turn.
		this.addAbility(new HazeFrogAbility1(state));
	}
}
