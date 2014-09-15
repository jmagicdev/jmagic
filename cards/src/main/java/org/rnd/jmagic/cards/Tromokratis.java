package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Tromokratis")
@SuperTypes({SuperType.LEGENDARY})
@Types({Type.CREATURE})
@SubTypes({SubType.KRAKEN})
@ManaCost("5UU")
@ColorIdentity({Color.BLUE})
public final class Tromokratis extends Card
{
	public static final class TromokratisAbility0 extends StaticAbility
	{
		public TromokratisAbility0(GameState state)
		{
			super(state, "Tromokratis has hexproof unless it's attacking or blocking.");

			SetGenerator inCombat = Union.instance(Attacking.instance(), Blocking.instance());
			SetGenerator thisInCombat = Intersect.instance(This.instance(), inCombat);
			this.canApply = Both.instance(this.canApply, Not.instance(thisInCombat));

			this.addEffectPart(addAbilityToObject(This.instance(), org.rnd.jmagic.abilities.keywords.Hexproof.class));
		}
	}

	public static final class TromokratisAbility1 extends StaticAbility
	{
		public TromokratisAbility1(GameState state)
		{
			super(state, "Tromokratis can't be blocked unless all creatures defending player controls block it.");

			SetGenerator blocked = Intersect.instance(Blocked.instance(), This.instance());

			SetGenerator defender = DefendingPlayer.instance(This.instance());
			SetGenerator theirCreatures = Intersect.instance(ControlledBy.instance(defender), HasType.instance(Type.CREATURE));
			SetGenerator anyNotBlocking = RelativeComplement.instance(theirCreatures, Blocking.instance(This.instance()));

			SetGenerator illegalBlock = Both.instance(blocked, anyNotBlocking);

			ContinuousEffect.Part part = new ContinuousEffect.Part(ContinuousEffectType.BLOCKING_RESTRICTION);
			part.parameters.put(ContinuousEffectType.Parameter.RESTRICTION, Identity.instance(illegalBlock));
			this.addEffectPart(part);
		}
	}

	public Tromokratis(GameState state)
	{
		super(state);

		this.setPower(8);
		this.setToughness(8);

		// Tromokratis has hexproof unless it's attacking or blocking.
		this.addAbility(new TromokratisAbility0(state));

		// Tromokratis can't be blocked unless all creatures defending player
		// controls block it. (If any creature that player controls doesn't
		// block this creature, it can't be blocked.)
		this.addAbility(new TromokratisAbility1(state));
	}
}
