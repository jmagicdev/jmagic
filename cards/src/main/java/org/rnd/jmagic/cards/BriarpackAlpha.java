package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Briarpack Alpha")
@Types({Type.CREATURE})
@SubTypes({SubType.WOLF})
@ManaCost("3G")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.GREEN})
public final class BriarpackAlpha extends Card
{
	public static final class BriarpackAlphaAbility1 extends EventTriggeredAbility
	{
		public BriarpackAlphaAbility1(GameState state)
		{
			super(state, "When Briarpack Alpha enters the battlefield, target creature gets +2/+2 until end of turn.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(ptChangeUntilEndOfTurn(target, +2, +2, "Target creature gets +2/+2 until end of turn."));
		}
	}

	public BriarpackAlpha(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flash (You may cast this spell any time you could cast an instant.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// When Briarpack Alpha enters the battlefield, target creature gets
		// +2/+2 until end of turn.
		this.addAbility(new BriarpackAlphaAbility1(state));
	}
}
