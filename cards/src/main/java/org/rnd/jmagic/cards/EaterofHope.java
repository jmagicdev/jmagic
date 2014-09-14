package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Eater of Hope")
@Types({Type.CREATURE})
@SubTypes({SubType.DEMON})
@ManaCost("5BB")
@ColorIdentity({Color.BLACK})
public final class EaterofHope extends Card
{
	public static final class EaterofHopeAbility1 extends ActivatedAbility
	{
		public EaterofHopeAbility1(GameState state)
		{
			super(state, "(B), Sacrifice another creature: Regenerate Eater of Hope.");
			this.setManaCost(new ManaPool("(B)"));

			SetGenerator anotherCreature = RelativeComplement.instance(CreaturePermanents.instance(), ABILITY_SOURCE_OF_THIS);
			this.addCost(sacrifice(You.instance(), 1, anotherCreature, "Sacrifice another creature"));

			this.addEffect(regenerate(ABILITY_SOURCE_OF_THIS, "Regenerate Eater of Hope."));
		}
	}

	public static final class EaterofHopeAbility2 extends ActivatedAbility
	{
		public EaterofHopeAbility2(GameState state)
		{
			super(state, "(2)(B), Sacrifice two other creatures: Destroy target creature.");
			this.setManaCost(new ManaPool("(2)(B)"));
			// Sacrifice two other creatures
		}
	}

	public EaterofHope(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(4);

		// Flying
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Flying(state));

		// (B), Sacrifice another creature: Regenerate Eater of Hope.
		this.addAbility(new EaterofHopeAbility1(state));

		// (2)(B), Sacrifice two other creatures: Destroy target creature.
		this.addAbility(new EaterofHopeAbility2(state));
	}
}
