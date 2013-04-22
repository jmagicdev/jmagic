package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Firewing Phoenix")
@Types({Type.CREATURE})
@SubTypes({SubType.PHOENIX})
@ManaCost("3R")
@Printings({@Printings.Printed(ex = Expansion.MAGIC_2013, r = Rarity.RARE)})
@ColorIdentity({Color.RED})
public final class FirewingPhoenix extends Card
{
	public static final class FirewingPhoenixAbility1 extends ActivatedAbility
	{
		public FirewingPhoenixAbility1(GameState state)
		{
			super(state, "(1)(R)(R)(R): Return Firewing Phoenix from your graveyard to your hand.");
			this.setManaCost(new ManaPool("(1)(R)(R)(R)"));

			SetGenerator thisCard = ABILITY_SOURCE_OF_THIS;

			EventType.ParameterMap parameters = new EventType.ParameterMap();
			parameters.put(EventType.Parameter.CAUSE, This.instance());
			parameters.put(EventType.Parameter.TO, HandOf.instance(You.instance()));
			parameters.put(EventType.Parameter.OBJECT, thisCard);
			this.addEffect(new EventFactory(EventType.MOVE_OBJECTS, parameters, "Return Firewing Phoenix from your graveyard to your hand."));

			this.activateOnlyFromGraveyard();
		}
	}

	public FirewingPhoenix(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (1)(R)(R)(R): Return Firewing Phoenix from your graveyard to your
		// hand.
		this.addAbility(new FirewingPhoenixAbility1(state));
	}
}
