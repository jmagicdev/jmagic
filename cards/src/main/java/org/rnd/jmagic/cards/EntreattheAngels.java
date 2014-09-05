package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Entreat the Angels")
@Types({Type.SORCERY})
@ManaCost("XXWWW")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE})
public final class EntreattheAngels extends Card
{
	public EntreattheAngels(GameState state)
	{
		super(state);

		// Put X 4/4 white Angel creature tokens with flying onto the
		// battlefield.
		CreateTokensFactory factory = new CreateTokensFactory(ValueOfX.instance(This.instance()), "Put X 4/4 white Angel creature tokens with flying onto the battlefield.");
		factory.addCreature(4, 4);
		factory.setColors(Color.WHITE);
		factory.setSubTypes(SubType.ANGEL);
		factory.addAbility(org.rnd.jmagic.abilities.keywords.Flying.class);
		this.addEffect(factory.getEventFactory());

		// Miracle (X)(W)(W) (You may cast this card for its miracle cost when
		// you draw it if it's the first card you drew this turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Miracle(state, "(X)(W)(W)"));
	}
}
