package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Urbis Protector")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.HUMAN})
@ManaCost("4WW")
@Printings({@Printings.Printed(ex = Gatecrash.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class UrbisProtector extends Card
{
	public static final class UrbisProtectorAbility0 extends EventTriggeredAbility
	{
		public UrbisProtectorAbility0(GameState state)
		{
			super(state, "When Urbis Protector enters the battlefield, put a 4/4 white Angel creature token with flying onto the battlefield.");
			this.addPattern(whenThisEntersTheBattlefield());

			CreateTokensFactory token = new CreateTokensFactory(1, 4, 4, "Put a 4/4 white Angel creature token with flying onto the battlefield.");
			token.setColors(Color.WHITE);
			token.setSubTypes(SubType.ANGEL);
			token.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(token.getEventFactory());
		}
	}

	public UrbisProtector(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// When Urbis Protector enters the battlefield, put a 4/4 white Angel
		// creature token with flying onto the battlefield.
		this.addAbility(new UrbisProtectorAbility0(state));
	}
}
