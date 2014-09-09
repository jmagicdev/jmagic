package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("The Hive")
@Types({Type.ARTIFACT})
@ManaCost("5")
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
