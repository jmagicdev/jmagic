package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Voice of the Provinces")
@Types({Type.CREATURE})
@SubTypes({SubType.ANGEL})
@ManaCost("4WW")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE})
public final class VoiceoftheProvinces extends Card
{
	public static final class VoiceoftheProvincesAbility1 extends EventTriggeredAbility
	{
		public VoiceoftheProvincesAbility1(GameState state)
		{
			super(state, "When Voice of the Provinces enters the battlefield, put a 1/1 white Human creature token onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			CreateTokensFactory f = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white Human creature token onto the battlefield.");
			f.setColors(Color.WHITE);
			f.setSubTypes(SubType.HUMAN);
			this.addEffect(f.getEventFactory());
		}
	}

	public VoiceoftheProvinces(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// When Voice of the Provinces enters the battlefield, put a 1/1 white
		// Human creature token onto the battlefield.
		this.addAbility(new VoiceoftheProvincesAbility1(state));
	}
}
