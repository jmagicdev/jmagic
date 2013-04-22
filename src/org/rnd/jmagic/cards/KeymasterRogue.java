package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Keymaster Rogue")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.ROGUE})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class KeymasterRogue extends Card
{
	public static final class KeymasterRogueAbility1 extends EventTriggeredAbility
	{
		public KeymasterRogueAbility1(GameState state)
		{
			super(state, "When Keymaster Rogue enters the battlefield, return a creature you control to its owner's hand.");
			this.addPattern(whenThisEntersTheBattlefield());
			this.addEffect(bounceChoice(You.instance(), 1, CREATURES_YOU_CONTROL, "Return a creature you control to its owner's hand."));
		}
	}

	public KeymasterRogue(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Keymaster Rogue is unblockable.
		this.addAbility(new org.rnd.jmagic.abilities.Unblockable(state, this.getName()));

		// When Keymaster Rogue enters the battlefield, return a creature you
		// control to its owner's hand.
		this.addAbility(new KeymasterRogueAbility1(state));
	}
}
