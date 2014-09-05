package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;
import org.rnd.jmagic.expansions.*;

@Name("Prized Elephant")
@Types({Type.CREATURE})
@SubTypes({SubType.ELEPHANT})
@ManaCost("3W")
@Printings({@Printings.Printed(ex = Magic2013.class, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.WHITE, Color.GREEN})
public final class PrizedElephant extends Card
{
	public static final class PrizedElephantAbility0 extends StaticAbility
	{
		public PrizedElephantAbility0(GameState state)
		{
			super(state, "Prized Elephant gets +1/+1 as long as you control a Forest.");
			this.addEffectPart(modifyPowerAndToughness(This.instance(), +1, +1));
			this.canApply = Both.instance(this.canApply, Intersect.instance(ControlledBy.instance(You.instance()), HasSubType.instance(SubType.FOREST)));
		}
	}

	public static final class PrizedElephantAbility1 extends ActivatedAbility
	{
		public PrizedElephantAbility1(GameState state)
		{
			super(state, "(G): Prized Elephant gains trample until end of turn.");
			this.setManaCost(new ManaPool("(G)"));
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Trample.class, "Prized Elephant gains trample until end of turn."));
		}
	}

	public PrizedElephant(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		// Prized Elephant gets +1/+1 as long as you control a Forest.
		this.addAbility(new PrizedElephantAbility0(state));

		// (G): Prized Elephant gains trample until end of turn. (If this
		// creature would assign enough damage to its blockers to destroy them,
		// you may have it assign the rest of its damage to defending player or
		// planeswalker.)
		this.addAbility(new PrizedElephantAbility1(state));
	}
}
