package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Din of the Fireherd")
@Types({Type.SORCERY})
@ManaCost("5(B/R)(B/R)(B/R)")
@Printings({@Printings.Printed(ex = Expansion.SHADOWMOOR, r = Rarity.RARE)})
@ColorIdentity({Color.BLACK, Color.RED})
public final class DinoftheFireherd extends Card
{
	public DinoftheFireherd(GameState state)
	{
		super(state);

		Target target = this.addTarget(OpponentsOf.instance(You.instance()), "target opponent");

		CreateTokensFactory token = new CreateTokensFactory(1, 5, 5, "Put a 5/5 black and red Elemental creature token onto the battlefield.");
		token.setColors(Color.BLACK, Color.RED);
		token.setSubTypes(SubType.ELEMENTAL);
		this.addEffect(token.getEventFactory());

		EventFactory sacrificeCritterFactory = new EventFactory(EventType.SACRIFICE_CHOICE, ("Target opponent sacrifices a creature for each black creature you control, "));
		sacrificeCritterFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		sacrificeCritterFactory.parameters.put(EventType.Parameter.NUMBER, Count.instance(Intersect.instance(Intersect.instance(HasColor.instance(Color.BLACK), HasType.instance(Type.CREATURE)), ControlledBy.instance(You.instance()))));
		sacrificeCritterFactory.parameters.put(EventType.Parameter.CHOICE, HasType.instance(Type.CREATURE));
		sacrificeCritterFactory.parameters.put(EventType.Parameter.PLAYER, targetedBy(target));
		this.addEffect(sacrificeCritterFactory);

		EventFactory sacrificeLandFactory = new EventFactory(EventType.SACRIFICE_CHOICE, ("then sacrifices a land for each red creature you control."));
		sacrificeLandFactory.parameters.put(EventType.Parameter.CAUSE, This.instance());
		sacrificeLandFactory.parameters.put(EventType.Parameter.NUMBER, Count.instance(Intersect.instance(Intersect.instance(HasColor.instance(Color.RED), HasType.instance(Type.CREATURE)), ControlledBy.instance(You.instance()))));
		sacrificeLandFactory.parameters.put(EventType.Parameter.CHOICE, HasType.instance(Type.LAND));
		sacrificeLandFactory.parameters.put(EventType.Parameter.PLAYER, targetedBy(target));
		this.addEffect(sacrificeLandFactory);
	}
}
