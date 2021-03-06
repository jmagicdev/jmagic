package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;

import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Hero of Oxid Ridge")
@Types({Type.CREATURE})
@SubTypes({SubType.HUMAN, SubType.KNIGHT})
@ManaCost("2RR")
@ColorIdentity({Color.RED})
public final class HeroofOxidRidge extends Card
{
	public static final class HeroofOxidRidgeAbility2 extends EventTriggeredAbility
	{
		public HeroofOxidRidgeAbility2(GameState state)
		{
			super(state, "Whenever Hero of Oxid Ridge attacks, creatures with power 1 or less can't block this turn.");
			this.addPattern(whenThisAttacks());

			SetGenerator small = HasPower.instance(Between.instance(null, 1));
			this.addEffect(cantBlockThisTurn(small, "Creatures with power 1 or less can't block this turn."));
		}
	}

	public HeroofOxidRidge(GameState state)
	{
		super(state);

		this.setPower(4);
		this.setToughness(2);

		// Haste
		this.addAbility(new org.rnd.jmagic.abilities.keywords.Haste(state));

		// Battle cry (Whenever this creature attacks, each other attacking
		// creature gets +1/+0 until end of turn.)
		this.addAbility(new org.rnd.jmagic.abilities.keywords.BattleCry(state));

		// Whenever Hero of Oxid Ridge attacks, creatures with power 1 or less
		// can't block this turn.
		this.addAbility(new HeroofOxidRidgeAbility2(state));
	}
}
