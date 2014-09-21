package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Bear's Companion")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.HUMAN})
@ManaCost("2GUR")
@ColorIdentity({Color.RED, Color.BLUE, Color.GREEN})
public final class BearsCompanion extends Card
{
	public static final class BearsCompanionAbility0 extends EventTriggeredAbility
	{
		public BearsCompanionAbility0(GameState state)
		{
			super(state, "When Bear's Companion enters the battlefield, put a 4/4 green Bear creature token onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			CreateTokensFactory bear = new CreateTokensFactory(1, 4, 4, "Put a 4/4 green Bear creature token onto the battlefield.");
			bear.setColors(Color.GREEN);
			bear.setSubTypes(SubType.BEAR);
			this.addEffect(bear.getEventFactory());
		}
	}

	public BearsCompanion(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Bear's Companion enters the battlefield, put a 4/4 green Bear
		// creature token onto the battlefield.
		this.addAbility(new BearsCompanionAbility0(state));
	}
}
