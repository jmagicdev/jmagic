package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;

@Name("Thornling")
@Types({Type.CREATURE})
@SubTypes({SubType.SHAPESHIFTER, SubType.ELEMENTAL})
@ManaCost("3GG")
@Printings({@Printings.Printed(ex = Expansion.CONFLUX, r = Rarity.MYTHIC)})
@ColorIdentity({Color.GREEN})
public final class Thornling extends Card
{
	public static final class GainAbility extends ActivatedAbility
	{
		private static String effectName(Class<? extends Identified> ability)
		{
			return "Thornling gains " + ability.getAnnotation(Name.class).value().toLowerCase() + " until end of turn.";
		}

		private final Class<? extends Identified> ability;

		public GainAbility(GameState state, Class<? extends Identified> ability)
		{
			super(state, "(G): " + effectName(ability));

			this.setManaCost(new ManaPool("G"));
			this.addEffect(addAbilityUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, ability, effectName(ability)));

			this.ability = ability;
		}

		@Override
		public GainAbility create(Game game)
		{
			return new GainAbility(game.physicalState, this.ability);
		}
	}

	public static final class BecomeIndestructible extends ActivatedAbility
	{
		public BecomeIndestructible(GameState state)
		{
			super(state, "(G): Thornling is indestructible this turn.");

			this.setManaCost(new ManaPool("G"));

			this.addEffect(createFloatingEffect("Thornling is indestructible this turn.", indestructible(ABILITY_SOURCE_OF_THIS)));
		}
	}

	public static final class PTChange extends ActivatedAbility
	{
		private static String effectName(int p, int t)
		{
			return "Thornling gets " + (p > 0 ? "+" : "") + p + "/" + (t > 0 ? "+" : "") + t + " until end of turn.";
		}

		private final int p;
		private final int t;

		public PTChange(GameState state, int p, int t)
		{
			super(state, "(1): " + effectName(p, t));
			this.setManaCost(new ManaPool("(1)"));

			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, p, t, effectName(p, t)));

			this.p = p;
			this.t = t;
		}

		@Override
		public PTChange create(Game game)
		{
			return new PTChange(game.physicalState, this.p, this.t);
		}
	}

	public Thornling(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// (G): Thornling gains haste until end of turn.
		this.addAbility(new GainAbility(state, org.rnd.jmagic.abilities.keywords.Haste.class));

		// (G): Thornling gains trample until end of turn.
		this.addAbility(new GainAbility(state, org.rnd.jmagic.abilities.keywords.Trample.class));

		// (G): Thornling is indestructible this turn.
		this.addAbility(new BecomeIndestructible(state));

		// (1): Thornling gets +1/-1 until end of turn.
		this.addAbility(new PTChange(state, +1, -1));

		// (1): Thornling gets -1/+1 until end of turn.
		this.addAbility(new PTChange(state, -1, +1));
	}
}
