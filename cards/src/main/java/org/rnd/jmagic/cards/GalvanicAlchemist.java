package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.expansions.*;

@Name("Galvanic Alchemist")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.WIZARD})
@ManaCost("2U")
@Printings({@Printings.Printed(ex = AvacynRestored.class, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class GalvanicAlchemist extends Card
{
	public static final class GalvanicAlchemistAbility1 extends ActivatedAbility
	{
		public GalvanicAlchemistAbility1(GameState state)
		{
			super(state, "(2)(U): Untap this creature.");
			this.setManaCost(new ManaPool("(2)(U)"));
			this.addEffect(untap(ABILITY_SOURCE_OF_THIS, "Untap this creature."));
		}
	}

	public GalvanicAlchemist(GameState state)
	{
		super(state);

		this.setPower(1);
		this.setToughness(4);

		// Soulbond (You may pair this creature with another unpaired creature
		// when either enters the battlefield. They remain paired for as long as
		// you control both of them.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Soulbond(state));

		// As long as Galvanic Alchemist is paired with another creature, each
		// of those creatures has "(2)(U): Untap this creature."
		this.addAbility(new org.rnd.jmagic.abilities.AbilityIfPaired.Final(state, "As long as Galvanic Alchemist is paired with another creature, each of those creatures has \"(2)(U): Untap this creature.\"", GalvanicAlchemistAbility1.class));
	}
}
