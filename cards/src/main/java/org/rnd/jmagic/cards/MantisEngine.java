package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;

@Name("Mantis Engine")
@Types({Type.ARTIFACT, Type.CREATURE})
@SubTypes({SubType.INSECT})
@ManaCost("5")
@Printings({@Printings.Printed(ex = Expansion.TENTH_EDITION, r = Rarity.UNCOMMON), @Printings.Printed(ex = Expansion.URZAS_DESTINY, r = Rarity.UNCOMMON)})
@ColorIdentity({})
public final class MantisEngine extends Card
{
	public static final class LearnFlying extends ActivatedAbility
	{
		public LearnFlying(GameState state)
		{
			super(state, "(2): Mantis Engine gains flying until end of turn.");

			this.setManaCost(new ManaPool("2"));

			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.Flying.class, "Mantis Engine gains flying until end of turn"));
		}
	}

	public static final class LearnFirstStrike extends ActivatedAbility
	{
		public LearnFirstStrike(GameState state)
		{
			super(state, "(2): Mantis Engine gains first strike until end of turn.");

			this.setManaCost(new ManaPool("2"));

			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, org.rnd.jmagic.abilities.keywords.FirstStrike.class, "Mantis Engine gains first strike until end of turn"));
		}
	}

	public MantisEngine(GameState state)
	{
		super(state);

		this.setPower(3);
		this.setToughness(3);

		this.addAbility(new LearnFlying(state));
		this.addAbility(new LearnFirstStrike(state));
	}
}
