package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Tuktuk the Explorer")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.GOBLIN})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.RISE_OF_THE_ELDRAZI, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class TuktuktheExplorer extends Card
{
	public static final class Return extends EventTriggeredAbility
	{
		public Return(GameState state)
		{
			super(state, "When Tuktuk the Explorer dies, put a legendary 5/5 colorless Goblin Golem artifact creature token named Tuktuk the Returned onto the battlefield.");
			this.addPattern(whenThisDies());

			CreateTokensFactory token = new CreateTokensFactory(1, 5, 5, "Put a legendary 5/5 colorless Goblin Golem artifact creature token named Tuktuk the Returned onto the battlefield.");
			token.setLegendary();
			token.setSubTypes(SubType.GOBLIN, SubType.GOLEM);
			token.setName("Tuktuk the Returned");
			token.setArtifact();
			this.addEffect(token.getEventFactory());
		}
	}

	public TuktuktheExplorer(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(1);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// When Tuktuk the Explorer is put into a graveyard from the
		// battlefield, put a legendary 5/5 colorless Goblin Golem artifact
		// creature token named Tuktuk the Returned onto the battlefield.
		this.addAbility(new Return(state));
	}
}
