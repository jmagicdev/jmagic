package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Spectral Procession")
@Types({Type.SORCERY})
@ManaCost("(2/W)(2/W)(2/W)")
@Printings({@Printings.Printed(ex = Expansion.SHADOWMOOR, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class SpectralProcession extends Card
{
	public SpectralProcession(GameState state)
	{
		super(state);

		// Put three 1/1 white Spirit creature tokens with flying onto the
		// battlefield.
		CreateTokensFactory tokens = new CreateTokensFactory(3, 1, 1, "Put three 1/1 white Spirit creature tokens with flying onto the battlefield.");
		tokens.setColors(Color.WHITE);
		tokens.setSubTypes(SubType.SPIRIT);
		tokens.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
		this.addEffect(tokens.getEventFactory());
	}
}
