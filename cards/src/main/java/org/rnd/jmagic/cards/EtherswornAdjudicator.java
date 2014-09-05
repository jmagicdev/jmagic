package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Ethersworn Adjudicator")
@Types({Type.CREATURE, Type.ARTIFACT})
@SubTypes({SubType.KNIGHT, SubType.VEDALKEN})
@ManaCost("4U")
@Printings({@Printings.Printed(ex = Conflux.class, r = Rarity.MYTHIC)})
@ColorIdentity({Color.WHITE, Color.BLUE, Color.BLACK})
public final class EtherswornAdjudicator extends Card
{
	public static final class Adjudicate extends ActivatedAbility
	{
		public Adjudicate(GameState state)
		{
			super(state, "(1)(W)(B), (T): Destroy target creature or enchantment.");

			this.setManaCost(new ManaPool("1WB"));
			this.costsTap = true;

			Target target = this.addTarget(Intersect.instance(HasType.instance(Type.CREATURE, Type.ENCHANTMENT), Permanents.instance()), "target creature or enchantment");

			this.addEffect(destroy(targetedBy(target), "Destroy target creature or enchantment."));
		}
	}

	public static final class AdjudicateMore extends ActivatedAbility
	{
		public AdjudicateMore(GameState state)
		{
			super(state, "(2)(U): Untap Ethersworn Adjudicator.");
			this.setManaCost(new ManaPool("2U"));

			this.addEffect(untap(ABILITY_SOURCE_OF_THIS, "Untap Ethersworn Adjudicator."));
		}
	}

	public EtherswornAdjudicator(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		this.addAbility(new Adjudicate(state));

		this.addAbility(new AdjudicateMore(state));
		// (1)(W)(B), (T): Destroy target creature or enchantment.

		// (2)(U): Untap Ethersworn Adjudicator.
	}
}
