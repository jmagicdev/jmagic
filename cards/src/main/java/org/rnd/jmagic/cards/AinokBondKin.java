package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Ainok Bond-Kin")
@Types({Type.CREATURE})
@SubTypes({SubType.HOUND, SubType.SOLDIER})
@ManaCost("1W")
@ColorIdentity({Color.WHITE})
public final class AinokBondKin extends Card
{
	public static final class AinokBondKinAbility1 extends StaticAbility
	{
		public AinokBondKinAbility1(GameState state)
		{
			super(state, "Each creature you control with a +1/+1 counter on it has first strike.");
			this.addEffectPart(addAbilityToObject(CREATURES_YOU_CONTROL_WITH_PLUS_ONE_COUNTER, org.rnd.jmagic.abilities.keywords.FirstStrike.class));
		}
	}

	public AinokBondKin(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Outlast (1)(W) ((1)(W), (T): Put a +1/+1 counter on this creature.
		// Outlast only as a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Outlast(state, "(1)(W)"));

		// Each creature you control with a +1/+1 counter on it has first
		// strike.
		this.addAbility(new AinokBondKinAbility1(state));
	}
}
