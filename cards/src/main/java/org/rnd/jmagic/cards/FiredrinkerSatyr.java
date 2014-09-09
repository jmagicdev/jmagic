package org.rnd.jmagic.cards;

import static org.rnd.jmagic.Convenience.*;
import org.rnd.jmagic.engine.*;
import org.rnd.jmagic.engine.generators.*;

@Name("Firedrinker Satyr")
@Types({Type.CREATURE})
@SubTypes({SubType.SATYR, SubType.SHAMAN})
@ManaCost("R")
@ColorIdentity({Color.RED})
public final class FiredrinkerSatyr extends Card
{
	public static final class FiredrinkerSatyrAbility0 extends EventTriggeredAbility
	{
		public FiredrinkerSatyrAbility0(GameState state)
		{
			super(state, "Whenever Firedrinker Satyr is dealt damage, it deals that much damage to you.");
			this.addPattern(whenThisIsDealtDamage());

			SetGenerator thatMuch = Count.instance(TriggerDamage.instance(This.instance()));
			this.addEffect(permanentDealDamage(thatMuch, You.instance(), "It deals that much damage to you."));
		}
	}

	public static final class FiredrinkerSatyrAbility1 extends ActivatedAbility
	{
		public FiredrinkerSatyrAbility1(GameState state)
		{
			super(state, "(1)(R): Firedrinker Satyr gets +1/+0 until end of turn and deals 1 damage to you.");
			this.setManaCost(new ManaPool("1R"));
			this.addEffect(ptChangeUntilEndOfTurn(ABILITY_SOURCE_OF_THIS, +1, +0, "Firedrinker Satyr gets +1/+0 until end of turn"));
			this.addEffect(permanentDealDamage(1, You.instance(), "and deals 1 damage to you."));
		}
	}

	public FiredrinkerSatyr(GameState state)
	{
		super(state);

		this.setPower(2);
		this.setToughness(1);

		// Whenever Firedrinker Satyr is dealt damage, it deals that much damage
		// to you.
		this.addAbility(new FiredrinkerSatyrAbility0(state));

		// {1}{R}: Firedrinker Satyr gets +1/+0 until end of turn and deals 1
		// damage to you.
		this.addAbility(new FiredrinkerSatyrAbility1(state));
	}
}
