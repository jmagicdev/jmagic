package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Pearl Lake Ancient")
@Types({Type.CREATURE})
@SubTypes({SubType.LEVIATHAN})
@ManaCost("5UU")
@ColorIdentity({Color.BLUE})
public final class PearlLakeAncient extends Card
{
	public static final class PearlLakeAncientAbility3 extends ActivatedAbility
	{
		public PearlLakeAncientAbility3(GameState state)
		{
			super(state, "Return three lands you control to their owner's hand: Return Pearl Lake Ancient to its owner's hand.");

			SetGenerator yourLands = Intersect.instance(ControlledBy.instance(You.instance()), HasType.instance(Type.LAND));
			this.addCost(bounceChoice(You.instance(), 3, yourLands, "Return three lands you control to their owner's hand"));

			this.addEffect(bounce(ABILITY_SOURCE_OF_THIS, "Return Pearl Lake Ancient to its owner's hand."));
		}
	}

	public PearlLakeAncient(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(7);

		// Flash
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flash(state));

		// Pearl Lake Ancient can't be countered.
		this.addAbility(new org.rnd.jmagic.abilities.CantBeCountered(state, this.getName()));

		// Prowess (Whenever you cast a noncreature spell, this creature gets
		// +1/+1 until end of turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Prowess(state));

		// Return three lands you control to their owner's hand: Return Pearl
		// Lake Ancient to its owner's hand.
		this.addAbility(new PearlLakeAncientAbility3(state));
	}
}
