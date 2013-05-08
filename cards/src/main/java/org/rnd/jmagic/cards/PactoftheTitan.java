package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Pact of the Titan")
@ManaCost("0")
@Types({Type.INSTANT})
@Printings({@Printings.Printed(ex = Expansion.FUTURE_SIGHT, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class PactoftheTitan extends org.rnd.jmagic.cardTemplates.Pact
{
	public PactoftheTitan(GameState state)
	{
		super(state);
	}

	@Override
	public void addEffects()
	{
		CreateTokensFactory token = new CreateTokensFactory(1, 4, 4, "Put a 4/4 red Giant creature token onto the battlefield.");
		token.setColors(Color.RED);
		token.setSubTypes(SubType.GIANT);
		this.addEffect(token.getEventFactory());
	}

	@Override
	public Color getColor()
	{
		return Color.RED;
	}

	@Override
	public String getUpkeepCost()
	{
		return "(4)(R)";
	}
}
