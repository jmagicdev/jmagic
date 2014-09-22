package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Thopter Foundry")
@Types({Type.ARTIFACT})
@ManaCost("(W/B)U")
@ColorIdentity({Color.WHITE, Color.BLUE, Color.BLACK})
public final class ThopterFoundry extends Card
{
	public static final class ThopterBabies extends ActivatedAbility
	{
		public ThopterBabies(GameState state)
		{
			super(state, "(1), Sacrifice a nontoken artifact: Put a 1/1 blue Thopter artifact creature token with flying onto the battlefield. You gain 1 life.");
			this.setManaCost(new ManaPool("1"));

			SetGenerator nontokenArtifacts = RelativeComplement.instance(ArtifactPermanents.instance(), Tokens.instance());
			this.addCost(sacrifice(You.instance(), 1, nontokenArtifacts, "Sacrifice a nontoken artifact"));

			CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "Put a 1/1 blue Thopter artifact creature token with flying onto the battlefield.");
			token.setColors(Color.BLUE);
			token.setSubTypes(SubType.THOPTER);
			token.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
			this.addEffect(token.getEventFactory());

			this.addEffect(gainLife(You.instance(), 1, "You gain 1 life."));
		}
	}

	public ThopterFoundry(GameState state)
	{
		super(state);

		// (1), Sacrifice a nontoken artifact: Put a 1/1 blue Thopter artifact
		// creature token with flying onto the battlefield. You gain 1 life.
		this.addAbility(new ThopterBabies(state));
	}
}
