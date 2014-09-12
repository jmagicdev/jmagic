package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Soul of Theros")
@Types({Type.CREATURE})
@SubTypes({SubType.AVATAR})
@ManaCost("4WW")
@ColorIdentity({Color.WHITE})
public final class SoulofTheros extends Card
{
	public static final class SoulofTherosAbility1 extends ActivatedAbility
	{
		public SoulofTherosAbility1(GameState state)
		{
			super(state, "(4)(W)(W): Creatures you control get +2/+2 and gain first strike and lifelink until end of turn.");
			this.setManaCost(new ManaPool("(4)(W)(W)"));

			this.addEffect(ptChangeAndAbilityUntilEndOfTurn(CREATURES_YOU_CONTROL, +2, +2,//
					"Creatures you control get +2/+2 and gain first strike and lifelink until end of turn.",//
					org.rnd.jmagic.abilities.keywords.FirstStrike.class,//
					org.rnd.jmagic.abilities.keywords.Lifelink.class));
		}
	}

	public static final class SoulofTherosAbility2 extends ActivatedAbility
	{
		public SoulofTherosAbility2(GameState state)
		{
			super(state, "(4)(W)(W), Exile Soul of Theros from your graveyard: Creatures you control get +2/+2 and gain first strike and lifelink until end of turn.");
			this.setManaCost(new ManaPool("(4)(W)(W)"));
			this.addCost(exile(ABILITY_SOURCE_OF_THIS, "Exile Soul of Theros from your graveyard"));
			this.activateOnlyFromGraveyard();

			this.addEffect(ptChangeAndAbilityUntilEndOfTurn(CREATURES_YOU_CONTROL, +2, +2,//
					"Creatures you control get +2/+2 and gain first strike and lifelink until end of turn.",//
					org.rnd.jmagic.abilities.keywords.FirstStrike.class,//
					org.rnd.jmagic.abilities.keywords.Lifelink.class));
		}
	}

	public SoulofTheros(GameState state)
	{
		super(state);

		this.setPower(6);
		this.setToughness(6);

		// Vigilance
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Vigilance(state));

		// (4)(W)(W): Creatures you control get +2/+2 and gain first strike and
		// lifelink until end of turn.
		this.addAbility(new SoulofTherosAbility1(state));

		// (4)(W)(W), Exile Soul of Theros from your graveyard: Creatures you
		// control get +2/+2 and gain first strike and lifelink until end of
		// turn.
		this.addAbility(new SoulofTherosAbility2(state));
	}
}
