package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Flurry of Wings")
@Types({Type.INSTANT})
@ManaCost("GWU")
@Printings({@Printings.Printed(ex = Expansion.ALARA_REBORN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.GREEN})
public final class FlurryofWings extends Card
{
	public FlurryofWings(GameState state)
	{
		super(state);

		String effectName = "Put X 1/1 white Bird Soldier creature tokens with flying onto the battlefield, where X is the number of attacking creatures.";
		CreateTokensFactory token = new CreateTokensFactory(Count.instance(Attacking.instance()), numberGenerator(1), numberGenerator(1), effectName);
		token.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
		token.setColors(Color.WHITE);
		token.setSubTypes(SubType.BIRD, SubType.SOLDIER);
		this.addEffect(token.getEventFactory());
	}
}
