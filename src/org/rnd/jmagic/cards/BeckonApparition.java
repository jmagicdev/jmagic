package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Beckon Apparition")
@Types({Type.INSTANT})
@ManaCost("(W/B)")
@Printings({@Printings.Printed(ex = Expansion.GATECRASH, r = Rarity.COMMON), @Printings.Printed(ex = Expansion.EVENTIDE, r = Rarity.COMMON)})
@ColorIdentity({Color.WHITE, Color.BLACK})
public final class BeckonApparition extends Card
{
	public BeckonApparition(GameState state)
	{
		super(state);

		// Exile target card from a graveyard. Put a 1/1 white and black Spirit
		// creature token with flying onto the battlefield.
		SetGenerator target = targetedBy(this.addTarget(InZone.instance(GraveyardOf.instance(Players.instance())), "target card in a graveyard"));
		this.addEffect(exile(target, "Exile target card from a graveyard."));

		CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "Put a 1/1 white and black Spirit creature token with flying onto the battlefield.");
		token.setColors(Color.WHITE, Color.BLACK);
		token.setSubTypes(SubType.SPIRIT);
		token.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
		this.addEffect(token.getEventFactory());
	}
}
