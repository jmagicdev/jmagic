package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tidewater Minion")
@Types({Type.CREATURE})
@SubTypes({SubType.MINION, SubType.ELEMENTAL})
@ManaCost("3UU")
@Printings({@Printings.Printed(ex = Expansion.RAVNICA, r = Rarity.COMMON)})
@ColorIdentity({Color.BLUE})
public final class TidewaterMinion extends Card
{
	public static final class CanAttack extends ActivatedAbility
	{
		public CanAttack(GameState state)
		{
			super(state, "(4): Tidewater Minion loses defender until end of turn.");
			this.setManaCost(new ManaPool("4"));

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.REMOVE_ABILITY_FROM_OBJECT);
			part.parameters.put(ContinuousEffectType.Parameter.OBJECT, ABILITY_SOURCE_OF_THIS);
			part.parameters.put(ContinuousEffectType.Parameter.ABILITY, Identity.instance(org.rnd.jmagic.abilities.keywords.Defender.class));

			this.addEffect(createFloatingEffect("Tidewater Minion loses defender until end of turn.", part));
		}
	}

	public static final class UntapStuff extends ActivatedAbility
	{
		public UntapStuff(GameState state)
		{
			super(state, "(T): Untap target permanent.");
			this.costsTap = true;

			Target target = this.addTarget(Permanents.instance(), "target player");

			this.addEffect(untap(targetedBy(target), "Untap target permanent."));
		}
	}

	public TidewaterMinion(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(4);

		// Defender (This creature can't attack.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Defender(state));

		// (4): Tidewater Minion loses defender until end of turn.
		this.addAbility(new CanAttack(state));

		// (T): Untap target permanent.
		this.addAbility(new UntapStuff(state));
	}
}
