package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Glen Elendra Archmage")
@Types({Type.CREATURE})
@SubTypes({SubType.WIZARD, SubType.FAERIE})
@ManaCost("3U")
@Printings({@Printings.Printed(ex = Expansion.EVENTIDE, r = Rarity.RARE)})
@ColorIdentity({Color.BLUE})
public final class GlenElendraArchmage extends Card
{
	public static final class GlenElendraArchmageAbility1 extends ActivatedAbility
	{
		public GlenElendraArchmageAbility1(GameState state)
		{
			super(state, "(U), Sacrifice Glen Elendra Archmage: Counter target noncreature spell.");
			this.setManaCost(new ManaPool("(U)"));
			this.addCost(sacrificeThis("Glen Elendra Archmage"));

			Target t = this.addTarget(RelativeComplement.instance(Spells.instance(), HasType.instance(Type.CREATURE)), "target noncreature spell");
			this.addEffect(counter(targetedBy(t), "Counter target noncreature spell."));
		}
	}

	public GlenElendraArchmage(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (U), Sacrifice Glen Elendra Archmage: Counter target noncreature
		// spell.
		this.addAbility(new GlenElendraArchmageAbility1(state));

		// Persist
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Persist(state));
	}
}
