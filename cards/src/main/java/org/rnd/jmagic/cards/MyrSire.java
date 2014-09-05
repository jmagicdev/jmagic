package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Myr Sire")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.MYR})
@ManaCost("2")
@Printings({@Printings.Printed(ex = MirrodinBesieged.class, r = Rarity.COMMON)})
@ColorIdentity({})
public final class MyrSire extends Card
{
	public static final class MyrSireAbility0 extends EventTriggeredAbility
	{
		public MyrSireAbility0(GameState state)
		{
			super(state, "When Myr Sire dies, put a 1/1 colorless Myr artifact creature token onto the battlefield.");
			this.addPattern(whenThisDies());

			CreateTokensFactory factory = new CreateTokensFactory(1, 1, 1, "Put a 1/1 colorless Myr artifact creature token onto the battlefield.");
			factory.setSubTypes(SubType.MYR);
			factory.setArtifact();
			this.addEffect(factory.getEventFactory());
		}
	}

	public MyrSire(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// When Myr Sire is put into a graveyard from the battlefield, put a 1/1
		// colorless Myr artifact creature token onto the battlefield.
		this.addAbility(new MyrSireAbility0(state));
	}
}
