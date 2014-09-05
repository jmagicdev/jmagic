package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Aerie Mystics")
@Types({Type.CREATURE})
@SubTypes({SubType.BIRD, SubType.WIZARD})
@ManaCost("4W")
@Printings({@Printings.Printed(ex = Conflux.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.BLUE, Color.WHITE, Color.GREEN})
public final class AerieMystics extends Card
{
	public static final class Shroudiness extends ActivatedAbility
	{
		public Shroudiness(GameState state)
		{
			super(state, "(1)(G)(U): Creatures you control gain shroud until end of turn.");

			this.setManaCost(new ManaPool("1GU"));

			this.addEffect(addAbilityUntilEndOfTurn(Intersect.instance(HasType.instance(Type.CREATURE), ControlledBy.instance(You.instance())), org.rnd.jmagic.abilities.keywords.Shroud.class, "Creatures you control gain shroud until end of turn."));
		}
	}

	public AerieMystics(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new Shroudiness(state));
	}
}
