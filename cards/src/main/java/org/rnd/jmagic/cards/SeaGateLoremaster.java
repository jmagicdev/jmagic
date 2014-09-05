package org.rnd.jmagic.cards;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Sea Gate Loremaster")
@Types({Type.CREATURE})
@SubTypes({SubType.MERFOLK, SubType.ALLY, SubType.WIZARD})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Zendikar.class, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class SeaGateLoremaster extends Card
{
	public static final class ArcadiaConference extends ActivatedAbility
	{
		public ArcadiaConference(GameState state)
		{
			super(state, "(T): Draw a card for each Ally you control.");

			this.costsTap = true;

			EventFactory factory = new EventFactory(EventType.DRAW_CARDS, "Draw a card for each Ally you control");
			factory.parameters.put(EventType.Parameter.CAUSE, This.instance());
			factory.parameters.put(EventType.Parameter.PLAYER, You.instance());
			factory.parameters.put(EventType.Parameter.NUMBER, Count.instance(Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.ALLY))));
			this.addEffect(factory);
		}
	}

	public SeaGateLoremaster(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(3);

		this.addAbility(new ArcadiaConference(state));
	}
}
