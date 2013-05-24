package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Mist Raven")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD})
@ManaCost("2UU")
@Printings({@Printings.Printed(ex = Expansion.AVACYN_RESTORED, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class MistRaven extends Card
{
	public static final class MistRavenAbility1 extends EventTriggeredAbility
	{
		public MistRavenAbility1(GameState state)
		{
			super(state, "When Mist Raven enters the battlefield, return target creature to its owner's hand.");
			this.addPattern(whenThisEntersTheBattlefield());

			SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
			this.addEffect(bounce(target, "Return target creature to its owner's hand."));
		}
	}

	public MistRaven(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Mist Raven enters the battlefield, return target creature to its
		// owner's hand.
		this.addAbility(new MistRavenAbility1(state));
	}
}
