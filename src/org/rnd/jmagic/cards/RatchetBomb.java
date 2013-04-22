package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Ratchet Bomb")
@Types({Type.ARTIFACT})
@ManaCost("2")
@Printings({@Printings.Printed(ex = Expansion.SCARS_OF_MIRRODIN, r = Rarity.RARE)})
@ColorIdentity({})
public final class RatchetBomb extends Card
{
	public static final class RatchetBombAbility0 extends ActivatedAbility
	{
		public RatchetBombAbility0(GameState state)
		{
			super(state, "(T): Put a charge counter on Ratchet Bomb.");
			this.costsTap = true;
			this.addEffect(putCounters(1, Counter.CounterType.CHARGE, ABILITY_SOURCE_OF_THIS, "Put a charge counter on Ratchet Bomb."));
		}
	}

	public static final class RatchetBombAbility1 extends ActivatedAbility
	{
		public RatchetBombAbility1(GameState state)
		{
			super(state, "(T), Sacrifice Ratchet Bomb: Destroy each nonland permanent with converted mana cost equal to the number of charge counters on Ratchet Bomb.");
			this.costsTap = true;
			this.addCost(sacrificeThis("Ratchet Bomb"));
			SetGenerator destroy = Intersect.instance(HasConvertedManaCost.instance(Count.instance(CountersOn.instance(ABILITY_SOURCE_OF_THIS, Counter.CounterType.CHARGE))), RelativeComplement.instance(Permanents.instance(), HasType.instance(Type.LAND)));
			this.addEffect(destroy(destroy, "Destroy each nonland permanent with converted mana cost equal to the number of charge counters on Ratchet Bomb."));
		}
	}

	public RatchetBomb(GameState state)
	{
		super(state);

		// (T): Put a charge counter on Ratchet Bomb.
		this.addAbility(new RatchetBombAbility0(state));

		// (T), Sacrifice Ratchet Bomb: Destroy each nonland permanent with
		// converted mana cost equal to the number of charge counters on Ratchet
		// Bomb.
		this.addAbility(new RatchetBombAbility1(state));
	}
}
