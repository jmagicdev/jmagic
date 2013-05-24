package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Flaring Flame-Kin")
@Types({Type.CREATURE})
@SubTypes({SubType.WARRIOR, SubType.ELEMENTAL})
@ManaCost("2R")
@Printings({@Printings.Printed(ex = Expansion.DISSENSION, r = Rarity.UNCOMMON)})
@ColorIdentity({Color.RED})
public final class FlaringFlameKin extends Card
{
	// required even though we have .abilities.Firebreathing;
	// ADD_ABILITY_TO_OBJECT requires a class with a (GameState) constructor and
	// .abilities.Firebreathing only has a (GameState, String) constructor.
	public static final class Firebreathing extends ActivatedAbility
	{
		public Firebreathing(GameState state)
		{
			super(state, "(R): Flaring Flame-Kin gets +1/+0 until end of turn.");
			this.setManaCost(new ManaPool("R"));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, +0, "Flaring Flame-Kin gets +1/+0 until end of turn."));
		}
	}

	public static final class AngryWhenEnchanted extends StaticAbility
	{
		public AngryWhenEnchanted(GameState state)
		{
			super(state, "As long as Flaring Flame-Kin is enchanted, it gets +2/+2, has trample, and has \"(R): Flaring Flame-Kin gets +1/+0 until end of turn.\"");

			SetGenerator thisIsEnchanted = Intersect.instance(HasSubType.instance(SubType.AURA), AttachedTo.instance(This.instance()));
			this.canApply = Both.instance(this.canApply, thisIsEnchanted);

			this.addEffectPart(modifyPowerAndToughness(This.instance(), +2, +2));
			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.Trample.class, Firebreathing.class));
		}
	}

	public FlaringFlameKin(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(2);

		// As long as Flaring Flame-Kin is enchanted, it gets +2/+2, has
		// trample, and has
		// "(R): Flaring Flame-Kin gets +1/+0 until end of turn."
		this.addAbility(new AngryWhenEnchanted(state));
	}
}
