package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Abzan Battle Priest")
@Types({Type.CREATURE})
@SubTypes({SubType.CLERIC, SubType.HUMAN})
@ManaCost("3W")
@ColorIdentity({Color.WHITE})
public final class AbzanBattlePriest extends Card
{
	public static final class AbzanBattlePriestAbility1 extends StaticAbility
	{
		public AbzanBattlePriestAbility1(GameState state)
		{
			super(state, "Each creature you control with a +1/+1 counter on it has lifelink.");
			this.addEffectPart(addAbilityToObject(CREATURES_YOU_CONTROL_WITH_PLUS_ONE_COUNTER, org.rnd.jmagic.abilities.keywords.Lifelink.class));
		}
	}

	public AbzanBattlePriest(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(2);

		// Outlast (W) ((W), (T): Put a +1/+1 counter on this creature. Outlast
		// only as a sorcery.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Outlast(state, "(W)"));

		// Each creature you control with a +1/+1 counter on it has lifelink.
		this.addAbility(new AbzanBattlePriestAbility1(state));
	}
}
