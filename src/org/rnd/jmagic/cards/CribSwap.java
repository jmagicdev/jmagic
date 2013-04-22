package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Crib Swap")
@Types({Type.TRIBAL, Type.INSTANT})
@SubTypes({SubType.SHAPESHIFTER})
@ManaCost("2W")
@Printings({@Printings.Printed(ex = Expansion.LORWYN, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE})
public final class CribSwap extends Card
{
	public CribSwap(GameState state)
	{
		super(state);

		// Changeling (This card is every creature type at all times.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Changeling(state));

		// Exile target creature.
		SetGenerator target = targetedBy(this.addTarget(CreaturePermanents.instance(), "target creature"));
		this.addEffect(exile(target, "Exile target creature."));

		// Its controller puts a 1/1 colorless Shapeshifter creature token with
		// changeling onto the battlefield.
		CreateTokensFactory token = new CreateTokensFactory(1, 1, 1, "Its controller puts a 1/1 colorless Shapeshifter creature token with changeling onto the battlefield.");
		token.setController(ControllerOf.instance(target));
		token.setSubTypes(SubType.SHAPESHIFTER);
		token.addAbility(org.rnd.jmagic.abilities.keywords.Changeling.class);
		this.addEffect(token.getEventFactory());
	}
}
