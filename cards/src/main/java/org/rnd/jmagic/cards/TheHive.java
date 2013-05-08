package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("The Hive")
@Types({Type.ARTIFACT})
@ManaCost("5")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.SIXTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.FIFTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.FOURTH_EDITION, r = Rarity.RARE), @Printings.Printed(ex = Expansion.REVISED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.UNLIMITED, r = Rarity.RARE), @Printings.Printed(ex = Expansion.BETA, r = Rarity.RARE), @Printings.Printed(ex = Expansion.ALPHA, r = Rarity.RARE)})
@ColorIdentity({})
public final class TheHive extends Card
{
	public static final class Shake extends ActivatedAbility
	{
		public Shake(GameState state)
		{
			super(state, "(5), (T): Put a 1/1 colorless Insect artifact creature token with flying named Wasp onto the battlefield.");

			this.setManaCost(new ManaPool("5"));

			this.costsTap = true;

			CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "Put a 1/1 colorless Insect artifact creature token with flying named Wasp onto the battlefield.");
			token.setSubTypes(SubType.INSECT);
			token.setArtifact();
			token.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			token.setName("Wasp");
			this.addEffect(token.getEventFactory());
		}
	}

	public TheHive(GameState state)
	{
		super(state);

		this.addAbility(new Shake(state));
	}
}
