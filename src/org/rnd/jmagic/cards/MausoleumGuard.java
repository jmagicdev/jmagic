package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Mausoleum Guard")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.SCOUT})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = Expansion.INNISTRAD, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class MausoleumGuard extends Card
{
	public static final class MausoleumGuardAbility0 extends EventTriggeredAbility
	{
		public MausoleumGuardAbility0(GameState state)
		{
			super(state, "When Mausoleum Guard dies, put two 1/1 white Spirit creature tokens with flying onto the battlefield.");
			this.addPattern(whenThisDies());

			CreateTokensFactory factory = new CreateTokensFactory(2, 1, 1, "Put two 1/1 white Spirit creature tokens with flying onto the battlefield.");
			factory.setColors(Color.WHITE);
			factory.setSubTypes(SubType.SPIRIT);
			factory.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(factory.getEventFactory());
		}
	}

	public MausoleumGuard(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// When Mausoleum Guard dies, put two 1/1 white Spirit creature tokens
		// with flying onto the battlefield.
		this.addAbility(new MausoleumGuardAbility0(state));
	}
}
