package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Deceiver Exarch")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = NewPhyrexia.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE})
public final class DeceiverExarch extends Card
{
	public static final class DeceiverExarchAbility1 extends EventTriggeredAbility
	{
		public DeceiverExarchAbility1(GameState state)
		{
			super(state, "When Deceiver Exarch enters the battlefield, choose one \u2014\n\u2022 Untap target permanent you control.\n\u2022 Tap target permanent an opponent controls.");
			this.addPattern(whenThisEntersTheBattlefield());

			// Untap target permanent you control
			{
				SetGenerator target = targetedBy(this.addTarget(1, Intersect.instance(Permanents.instance(), ControlledBy.instance(You.instance())), "target permanent you control"));
				this.addEffect(1, untap(target, "Untap target permanent you control."));
			}

			// tap target permanent an opponent controls.
			{
				SetGenerator target = targetedBy(this.addTarget(2, Intersect.instance(Permanents.instance(), ControlledBy.instance(OpponentsOf.instance(You.instance()))), "target permanent an opponent controls"));
				this.addEffect(2, tap(target, "Tap target permanent an opponent controls."));
			}
		}
	}

	public DeceiverExarch(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(4);

		// Flash (You may cast this spell any time you could cast an instant.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// When Deceiver Exarch enters the battlefield, choose one \u2014 Untap
		// target permanent you control; or tap target permanent an opponent
		// controls.
		this.addAbility(new DeceiverExarchAbility1(state));
	}
}
