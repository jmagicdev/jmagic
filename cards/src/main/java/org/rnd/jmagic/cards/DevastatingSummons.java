package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Devastating Summons")
@Types({Type.SORCERY})
@ManaCost("R")
@Printings({@Printings.Printed(ex = RiseOfTheEldrazi.class, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class DevastatingSummons extends Card
{
	public DevastatingSummons(GameState state)
	{
		super(state);
		SetGenerator x = ValueOfX.instance(This.instance());

		// As an additional cost to cast Devastating Summons, sacrifice X lands.
		EventFactory sacrificeLands = new EventFactory(EventType.SACRIFICE_CHOICE, "sacrifice X lands");
		sacrificeLands.parameters.put(EventType.Parameter.CAUSE, This.instance());
		sacrificeLands.parameters.put(EventType.Parameter.NUMBER, x);
		sacrificeLands.parameters.put(EventType.Parameter.CHOICE, HasType.instance(Type.LAND));
		sacrificeLands.parameters.put(EventType.Parameter.PLAYER, You.instance());
		sacrificeLands.usesX();
		this.addCost(sacrificeLands);

		// Put two X/X red Elemental creature tokens onto the battlefield.
		CreateTokensFactory tokens = new CreateTokensFactory(numberGenerator(2), x, x, "Put two X/X red Elemental creature tokens onto the battlefield.");
		tokens.setColors(Color.RED);
		tokens.setSubTypes(SubType.ELEMENTAL);
		this.addEffect(tokens.getEventFactory());
	}
}
