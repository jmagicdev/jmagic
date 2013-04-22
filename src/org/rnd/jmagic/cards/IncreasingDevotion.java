package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Increasing Devotion")
@Types({Type.SORCERY})
@ManaCost("3WW")
@Printings({@Printings.Printed(ex = Expansion.DARK_ASCENSION, r = Rarity.RARE)})
@ColorIdentity({Color.WHITE})
public final class IncreasingDevotion extends Card
{
	public IncreasingDevotion(GameState state)
	{
		super(state);

		// Put five 1/1 white Human creature tokens onto the battlefield. If
		// Increasing Devotion was cast from a graveyard, put ten of those
		// tokens onto the battlefield instead.
		SetGenerator number = IfThenElse.instance(Intersect.instance(ZoneCastFrom.instance(This.instance()), GraveyardOf.instance(Players.instance())), numberGenerator(10), numberGenerator(5));

		CreateTokensFactory factory = new CreateTokensFactory(number, "Put five 1/1 white Human creature tokens onto the battlefield. If Increasing Devotion was cast from a graveyard, put ten of those tokens onto the battlefield instead.");
		factory.addCreature(1, 1);
		factory.setColors(Color.WHITE);
		factory.setSubTypes(SubType.HUMAN);
		this.addEffect(factory.getEventFactory());

		// Flashback (7)(W)(W) (You may cast this card from your graveyard for
		// its flashback cost. Then exile it.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flashback(state, "(7)(W)(W)"));
	}
}
